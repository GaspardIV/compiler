Kompilator na dzien dzisiejszy sklada sie jedynie z frontendu.

Kompliacja kompilatora za pomoca polecenia: `make`

Uzycie: `./compiler nazwa_pliku.lat`

Struktura:

W katalogu `third_party` znajduje sie jar biblioteki java_cup, wykorzystywany przez parser. W katalogu `src` znajduja
sie pliki zrodlowe z podzialem na paczki:
- `latte` - Latte. Plik Compiler, to kompilator.
  - `latte.Absyn` - Abstract syntax. Skladniki syntaktyczne jezyka latte. Wiecej o kazdym z nich mozna sie dowiedziec z
  pliku gramatyki jezyka `LatteLang.cf`. Pliki wygenerowane przez bnfc, 
  ale z drobnymi modyfikacjami wprowadzonymi przeze mnie.
  - `latte.errors` - Bledy
  - `latte.frontend` - zawiera klase SemanticAnalyst bedaca glownym plikiem frontendu.
      - `latte.frontend.visitors` - implementacje visitor'ow.
      - `latte.frontend.environment` - opisuje srodowisko wykononia SemanticAnalyst'y.
  - `parser` - Parser. Wszystkie pliki wygenerowane przez bnfc.

Parser nie jest w make'u genererowany automatycznie z pliku z gramatyką języką, ze wzgledu na to że wprowadziłęm w nim drobne modyfikacje:
- Rozne metody i pola dla klas z Absyn (Przykldowo )
- - ClDef i ClDefExtends maja rozne pola i metody wzgledem oryginalnej gramatyki
- - Modyfikacja klasy TopDef, ze wzgledu na to ze w gramatyce nie ma mozliwosci rozroznienia czy metoda jest statyczna czy nie
- - ListClMember nie miala metod
- - 
- - dodanie throws Semnatic error, do metod visit i accept
- Usuniecie coponiektorych komunikatow o bledach z parsera, w celu uzyskania zgodnosci z wyjściem z testów ("Error" w pierwszej linii)
Pozbyłem się również zbędnych plikow pośrednich generowanych przez bnfc.


Do wygenerowania parsera uzylem bnfc 2.9.4:
`    bnfc-2.9.3-linux-x86_64.binary --java -l LatteLang.cf -m`
Opcja -l dla javy odpowiada za dodanie numeru linii do tokenow. Numer kolumny nie jest poprawnie generowany przez wykorzystywaną przez bnfc biblioteke java_cup, dlatego go nie wypisuje. 
W przypadku niewystarczajacej ilosci informacji o lokalizacji błedu, moge sprobowac wykorzystać antlr4, zamiast java_cup i jlex w nastepnej iteracji projektu.


Bnfc wykorzystuje bibloteki java_cup do parsera, i jlex jako lekser.

`java_cup 11b`
`jlex 1.2.6`

=============================== O czym warto wspomnieć ===============================
Zmienne inicjalizowane są na domyślne wartości - int -> 0, bool -> false, string -> "".
(?) W szczegolnosci w przypadku stringow, nie mozna przypisac do nich null, poniewaz jest to typ prosty.
W przypadku uzycia zmiennej niezainicjalizowanej przy jej inicializacji, zostanie ona zainicjalizowana na domyslne wartosci. -> jeszcze moze sie to zmienic, ale na razie tak jest.
Metody nie moga miec takiej samej nazwy jak funkcje globalne, w szczegolnosci funkcje wbudowane w jezyk i main.
funkcja error jest traktowana jako poprawne wyjście z funkcji (tez takiej zwracającej coś innego niż void).

Z optymalizacji wykonalem:
- Usnuniecie martwego kodu:
  - Usuniecie deklaracji zmiennych, ktore nie sa uzywane
  - Usuniecie deklaracji funkcji, ktore nie sa uzywane
  - Usuniecie deklaracji klas, ktore nie sa uzywane
  - Usuniecie deklaracji metod, ktore nie sa uzywane
  - Usuniecie deklaracji pol, ktore nie sa uzywane
  - Usuniecie deklaracji argumentow, ktore nie sa uzywane
  - Usuniecie deklaracji zmiennych lokalnych, ktore nie sa uzywane
  - Usuniecie kodu po returnach
  - if (true) { ... } -> { ... } -> jezeli return jest w srodku if'a to usuniecie kodu po returnie
  - if (false) { ... } -> {} -> usuniecie warunku
  - if() else { ... } -> {} -> z returnami -> usuniecie kodu po ifie
  - while (true) { ... } -> { ... } -> usuniecie kodu po, i wziecie uwagi jezeli return jest w srodku while'a
  - nieskonczona petla jest dozwolona, alle musi byc return za nia i tak
  - while (false) { ... } -> {} -> usuniecie petli
  - for (i = 0; i < 0; i++) { ... } -> {} -> usuniecie petli
- zwijanie stalych i wyrazen logicznych
  - 1 + 2 -> 3
  - 1 - 2 -> -1
  - 1 * 2 -> 2
  - 1 / 2 -> 0
  - 1 % 2 -> 1
  - 1 == 2 -> false
  - 1 != 2 -> true
  - 1 < 2 -> true
  - 1 <= 2 -> true
  - 1 > 2 -> false
  - 1 >= 2 -> false
  - 1 && 2 -> 2
  - 1 || 2 -> 1
  - !1 -> false
  - !0 -> true
  - 1 + 2 * 3 -> 7
  - (1 + 2) * 3 -> 9
  - 1 + 2 * 3 + 4 -> 11
  - (1 + 2) * 3 + 4 -> 13
  - 1 + (2 * 3) + 4 -> 11
  - 1 + 2 * (3 + 4) -> 15
  - 1 + 2 * (3 + 4) + 5 -> 20
  - (1 + 2) * (3 + 4) -> 21
  - (1 + 2) * (3 + 4) + 5 -> 26
  - 1 + (2 * (3 + 4)) -> 15
  - 1 + (2 * (3 + 4)) + 5 -> 20
  - 1 + (2 * (3 + 4)) + 5 * 6 -> 80
  - 1 + (2 * (3 + 4)) + 5 * 6 + 7 -> 87
  - 1 + (2 * (3 + 4)) + 5 * 6 + 7 * 8 -> 695
  - 1 + (2 * (3 + 4)) + 5 * 6 + 7 * 8 + 9 -> 704
  - 1 + (2 * (3 + 4)) + 5 * 6 + 7 * 8 + 9 * 10 ->
  - 1 + (2 * (3 + 4)) + 5 * 6 + 7 * 8 + 9 * 10 + 11 -> 815
  - 1 + (2 * (3 + 4)) + 5 * 6 + 7 * 8 + 9 * 10 + 11 * 12 -> 9795
- Usuniecie niepotrzebnych konwersji typow
  - int a = 1; a = (int) a; -> int a = 1; a = a;
  - int a = 1; a = (int) (a + 1); -> int a = 1; a = a + 1;
  - int a = 1; a = (int) (a + 1) + 2; -> int a = 1; a = a + 1 + 2;
  - int a = 1; a = (int) (a + 1) + 2 * 3; -> int a = 1; a = a + 1 + 2 * 3;
  - int a = 1; a = (int) (a + 1) + 2 * 3 + 4; -> int a = 1; a = a + 1 + 2 * 3 + 4;
  - int a = 1; a = (int) (a + 1) + 2 * 3 + 4 * 5; -> int a = 1; a = a + 1 + 2 * 3 + 4 * 5;
  - int a = 1; a = (int) (a + 1) + 2 * 3 + 4 * 5 + 6; -> int a = 1; a = a + 1 + 2 * 3 + 4 * 5 + 6;
  - int a = 1; a = (int) (a + 1) + 2 * 3 + 4 * 5 + 6 * 7; -> int a = 1; a = a + 1 + 2 * 3 + 4 * 5 + 6 * 7;
  - int a = 1; a = (int) (a + 1) + 2 * 3 + 4 * 5 + 6 * 7 + 8; -> int a = 1; a = a + 1 + 2 * 3 + 4 * 5 + 6 * 7 + 8;
  - int a = 1; a = (int) (a + 1) +
- Inlineowanie funkcji
- Inlineowanie metod
- Inlineowanie stalych
- Usuwanie martwego kodu za 1 punkt
- Zawijanie stalych
- Modyfikacja drzewa
- variables namespace is independent of the functions (and methods) namespace, e.g. (accepted code):?
- field shadowing is not allowed, e.g. (rejected code):?
TODO:
  Mapping high level constructs to llvm IR
- https://github.com/prosyslab-classroom/llvm-primer
- https://mapping-high-level-constructs-to-llvm-ir.readthedocs.io/en/latest/object-oriented-constructs/single-inheritance.html

Uwagi do frontendu:


[//]: # (0 Nie ma pliku latc &#40;a jedynie plik compiler&#41;. Tym razem nie obniżam,)
[//]: # (ale proszę to poprawić zgodnie ze specyfikacją.)
[//]: # (- Wywołanie error&#40;&#41; nie jest traktowane jako poprawne wyjście z)
[//]: # (  funkcji zwracającej coś innego niż void.)
- Nie da się porównywać (równość i nierówność) wyrażeń typu bool (i być
  może innych typów nienumerycznych).
- Argumenty i typ wyniku funkcji main nie są sprawdzane.
- Argument funkcji może być typu void a nie powinien.
- Zmienna może być typu void a nie powinna.
- W deklaracji zmiennej w wyrażeniu inicjalizującym dopuszczane
  jest wykorzystanie jej wartości, nawet jeśli nie jest zdefiniowana.
- usunac dead code (if else -> return return -> then wyrzucenie reszty ast i komunikat o nieosiagalnosci kodu)
- if true if false -> obslugiwac w semantic analyst
- infinite loop -> obslugiwac w semantic analyst
- todo:
  jezeli jest if(false){}else{return} to jest dobry program
- w while jak jest return to nie wiemy czy jest return
- w while false -> mozna wyjebac
- w while true -> probujemy rozne rzeczy ale generalnie zakladamy ze w while nie chcemy sprawdzic ze while to taki blackbox.
- zwijanie wyrazen stalych, usuwanie kodu martwego!!!
- wyrazenia stale: (wszystkich wyrazen bez zmiennych do literalow (int, true i false)).
- 3 + 5 -> 8
- !false = true
- null nie jest parsowany !!!
- rzutowanie obiektow nie dziala!!!
- todo koniecznie!!!! kompilacja od etapu pliku ze skladdnia!!!!!
- poprawic gramatyke zeby moze tego varkoxa byla bo ona spoko ????
- null i castowanie nie dziala i w sensie expr nie moze byc lewostronne (np. (new arr[10])[0] nie dziala) 
- martwy kod:
   po returnie, if false, while false
- https://mapping-high-level-constructs-to-llvm-ir.readthedocs.io/en/latest/object-oriented-constructs/single-inheritance.html
- 
+ Poza tym wdaje się działać.


todo:
- zaczac implementacje kompilatora od convertowania enivroment i ast do compilator environment i compilator ast(w szczegolnosci znacznik deadcode) ???
- dodac offset do komunikatow o bledach
- dodac buff do komunikatow o bledach
- usunac testy z rzutowan -> to bedzie traktowane jako bledy
- zadac pytanie o internal fun
INFO: 
-  pliki jlex i java_cup i makefile sa w commicie restructure fix cl member init bug



varqox readme:
````
## Language extensions
- arrays
- classes with inheritance and virtual methods
- null implicitly converts to any array and to any class

## Language semantics
- variables namespace is independent of the functions (and methods) namespace, e.g. (accepted code):
```latte
int foo() { return 42; }
int main() {
    int foo = 3;
    if (foo() + foo != 45) error();
    return 0;
}
```
- field shadowing is not possible, e.g. (rejected code):
```latte
class X {
    int x;
}
class Y extends X {
    string x;
}
int main() {
    return 0;
}
```

## Tests
Tests comprehensively cover language construct, type conversions and required static analysis. Some tests reflect chosen language semantics, thus compilers implementing other interpretations may not pass them.

Tests are not divided into ones categories based on which extensions they require.
Test folder structure:
- `good/` -- tests that present correct programs
- `bad/` -- tests that present incorrect programs
- `warnings/` -- tests that present programs producing diagnostic warnings
````