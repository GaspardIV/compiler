# Poprawka zwiazana z testem  z maila.
```
int i=0;
int x=i+0;
i=x+1;
printInt(i);
```
getVariable z rejestru jest wykorzystywane tylko i wylacznie w tym miejscu i po to powstalo
```
List<Quadruple> left = p.expr_1.accept(new RegisterExprVisitor(), block);
List<Quadruple> right = new RegisterExprVisitor().generateExprCode(p.expr_2, block);

Register leftLastRegister = left.get(left.size() - 1).result;
Variable variable = leftLastRegister.getLastVariableUsedThisRegister();
```
vs.
```
List<Quadruple> left = p.expr_1.accept(new RegisterExprVisitor(), block);
Register leftLastRegister = left.get(left.size() - 1).result;
Variable variable = leftLastRegister.getLastVariableUsedThisRegister();
List<Quadruple> right = new RegisterExprVisitor().generateExprCode(p.expr_2, block);
```

# Poprawki zwiazane z testami z poprzedniego maila.

### 1.  Zły wynik, np  dla programu
```
    int main() {
        int a = readInt();
        int b;
        if (a < 0) {
            b = 0;
        } else {
            b = 1;
        }
        printInt(b);
        return 0;
    }
```
Wynikowy kod llvm:
```
    define i32 @main() {
        main_entry:
            %tmp. = call i32 @readInt()
            %tmp..5 = icmp slt i32 %tmp., 0
            br i1 %tmp..5, label %main.1_if.true, label %main.2_if.false
        main.1_if.true:
            br label %main.3_if.end
        main.2_if.false:
            br label %main.3_if.end
        main.3_if.end:
            call void @printInt(i32 1)
            ret i32 0
    }
```
Gdzie jest phi????

```
[...]
getRedefinedVariables() {
    // return memoryLocations.keySet() 
    return lastRegisterOfVariable.keySet()
[...]
```

### 2. Zły llvm, np dla programu
```
int funkcja (int x, int n) {
    int i=0;
    int m=0;
    int t;
    while (i<n) {
        t=x;
        if (m<t) m=t;
        t=x;
        if (m<t) m=t;
        x++;
        i++;
    }
    return m;
}
```
```
define i32 @funkcja(i32 %x, i32 %n) {
funkcja_entry:
    br label %funkcja.1_while.cond
funkcja.2_while.body:
    %tmp..6 = icmp slt i32 %m, %x.1
    br i1 %tmp..6, label %funkcja.4_if.true, label %funkcja.5_if.end

-- tu powinny byc nextblocki od body, ale ustawilem body.setNextBlock(cond)

funkcja.1_while.cond:
    %t = phi i32 [0, %funkcja_entry], [%x.1, %funkcja.2_while.body]
    %x.1 = phi i32 [%x, %funkcja_entry], [%tmp..10, %funkcja.2_while.body]
    %i = phi i32 [0, %funkcja_entry], [%tmp..12, %funkcja.2_while.body]
    %m = phi i32 [0, %funkcja_entry], [%m.2, %funkcja.2_while.body]
    %n.1 = phi i32 [%n, %funkcja_entry], [%n.1, %funkcja.2_while.body]
    %tmp..4 = icmp slt i32 %i, %n.1
    br i1 %tmp..4, label %funkcja.2_while.body, label %funkcja.3_while.end
funkcja.3_while.end:
    ret i32 %m
}
```

bylo tak:

`entry.addLastBlock(cond);`

`List<Quadruple> exprs = p.expr_.accept(new RegisterExprVisitor(), cond);`

`cond.addLastBlock(body);`

`p.stmt_.accept(this, body);`

`body.addLastBlock(bend);`
```
    entry.setNextBlock(body); // usuniecie tych 3 linijek
    body.setNextBlock(cond); // usuniecie tych 3  linijek
    cond.setNextBlock(bend); // usuniecie tych 3 linijek
```


oprocz tego inny blad w tym tescie:

`%t = phi i32 [ 0, %funkcja_entry ], [ %x.1, %funkcja.2_while.body]`

vs.

`%t = phi i32 [ 0, %funkcja_entry ], [ %x.1, %ostatni_blok_w_while_body]`

poprawka:

``List<Quadruple> phi1 = cond.getPhiVariables(..., entry, body.getLastBlock());``


### 3. Błąd kompilatora, np dla programu
```
void funkcja(int x) {
    if (x > 5555) {
        int x = 7777;
        printInt(x);
    } else {
        int x = 8888;
        printInt(x);
    }
}

int main() {
    funkcja(3333);
}
```
llvm:
```
define void @funkcja(i32 %x) {
    funkcja_entry:
        %tmp..3 = icmp sgt i32 %x, 5555
        br i1 %tmp..3, label %funkcja.1_if.true, label %funkcja.2_if.false
    funkcja.1_if.true:
        call void @printInt(i32 7777)
        br label %funkcja.3_if.end
    funkcja.2_if.false:
        call void @printInt(i32 8888)
        br label %funkcja.3_if.end
    funkcja.3_if.end:
        %x.1 = phi i32 [7777, %funkcja.1_if.true], [8888, %funkcja.2_if.false]
        %x.2 = phi i32 [%x.1, %funkcja.1_if.true], [8888, %funkcja.2_if.false]
    ret void
}
```
Jakie bledy:
* x.1 i x.2
  - powod: zduplikowane zmienne (bralem liste zmiennych redefiniowanych z true i false i dla tych zmiennych tworzylem phi)
  - → rozwiaznie: usuniecie duplikatow (np uzycie set zmaiast list)
```
/*List*/ Set<String> variableNames = bfalse.getRedefinedVariables();
variableNames.addAll(btrue.getRedefinedVariables());
```
* to nie sa zmienne redefiniowane, wiec czemu wgl sa tu phi?
  - powod: niewykrycie zwiazane z 1.
  - → rozwiazanie: dodanie sprawdzenia czy zmienna jest w zasiegu bloku ifend, przy tworzeniu phi 
  - Variable variable = scope.getVariable(variableName); if (variable == null) continue;

# Zmiany nie z maila
- zagniezdzone while!! phi - wiele poziomow phi variables
- if w while kiedy zmienna phi z if’ajest rowniez zmienna w phi dla while
- omylkowo uzywalem zlej funkcji wniektorych miejscach, ktora bierze zmienna po nazwie, a nie po obiekcie zmiennej(dwie funkcje o identycznej nazwie…), dziwne bledy w phi
- funkcja error
     - nie dodawalem return po wywolaniu jej, przez co zle branczowalo (llv nie traktowal tego jak wyjscia)
     - nie wypisywalem runtime error
       
- PostProcessor -> removeJumpsToNonExistentBlocks & removePhiFromNonPredecessorsBlocks

+ Dodatkowo refactor 
+ przygotowanie pod lcse 





