Oprocz plikow projektowych dolaczam `testy_phi` i `zmiany_po_mailu.md`, o ktore Pani Doktor prosiła podczas omawiania pierwszej wersji backendu.
Testy te bardzo mi pomogły w pozbyciu sie błędów z instrukcji phi - zwłaszcza jeśli chodzi o zagnieżdżone pętle i ify.

# Konflikty

Gramatyka ma 6 konfliktow shift/reduce. 1 konflikt przyszedl z wraz z gramatyka źródłową.

5 konfliktow bierze sie z nastepujacych linijek i chodzi w nich o te kolizje ident-ident i ident-Expr6.
- EArrayElem. Expr7 ::= Expr6 "[" Expr "]";
- EArrayElemR. Expr7 ::= Ident "[" Expr "]";
- ENull.      Expr6 ::= "(" Ident ")" "null";
- EVar.      Expr6 ::= Ident ; 


# Optymalizacje uwzględniają:
- zwijanie stalych
- propagacja stalych
- propagacja kopii
- postac ssa 
- usuwanie martwego kodu
  - martwe zmienne
  - kod po returnach
  - if(const), while(const)
- usuwanie pustych i niepotrzebnych blokow
  - (uznalem za potrzebne te co biorą udział w jakichs phi)
  - daloby sie usunąć ich jeszcze więcej, ale wolałem mieć pewność, że nie usuwam za dużo.
- GCSE (5)

# Rozszerzenia uwzgledniaja
 - tablice (1)
 - struktury (2)
 - obiekty (atrybuty, metody, dziedziczenie) (3)
 - metody wirtualne (3)


Optymalizacje GCSE, usuwanie martwego kodu(zmiennych), usuwanie niepotrzebnych blokow sa realizowane na wygenerowanym juz kodzie czworkowym w klasie `PostProcessor`. 
Propagacja kopii, stałych i zwijanie sa na etapie generacji kodu w backendzie.
Kod realizujacy klasy w wiekszosci znajduje sie w folderze `latte.backend.program.global.classes`, oraz klasie `RegisterExprVisitor`.
Kod implementujacy tablice w calosci znajduje sie w `RegisterExprVisitor`.

Gdybym mógł zrobić coś inaczej w projekcie to na pewno bym wykonał zwijanie stałych i ich propagacje, tłumaczenie niektórych wyrażeń (np. for) na etapie frontendu. 
Było to bardzo utrudnione przez generowanie javowych klas wprost z gramatyki, gdyz posiadaly niemodyfikowalne zmienne typu final.
Ale mimo wszystko wykonywalne - należałoby zbudować od nowa swoje drzewo AST. Kurczowe trzymanie się wygenerowanej przez bnfc postaci drzewa przysporzyło mi to bardzo dużo problemów i oszpeciło kod. 

W tablicy na ujemnych bajtach od wskaznika trzymam length. Tablice sa wielowymiarowe.

Jestem zadowolony z GCSE i kodu realizujacego wirtualne metody - były to wyzwania dające dużo satysfakcji i zrozumienia tematu. 

Nie jestem zadowolony z jakości kodu jaki oddaje - niestety czas naglił i odpuszczałem sobie miejscami refaktoryzacje. 

W petli for, iterator jest prawostronny - nie jest możliwe zatem coś takiego:
```
    int [][]x;
    x = new int[][2];
    for (int[] y : x) {
        y = new int[3];
    }
```
natomiast nastepujacy kod jest w porządku:
```
    int [][]x;
    x = new int[][2];
    x[0]= new int[3];
    x[1]= new int[3];
    
    x[0][0]=1; x[0][1]=2; x[0][2]=3;
    x[1][0]=4; x[1][1]=5; x[1][2]=6;
    
    for (int[] y : x) {
        y[0] = 10;
        for (int z : y) {
            printInt(z);
        }
    }
```


Kompliacja kompilatora za pomoca polecenia: `make`

Uzycie: `./latc nazwa_pliku.lat`

Struktura:

W katalogu `third_party` znajduja sie paczki jar bibliotek java_cup i jlex, wykorzystywane przez parser. W katalogu `src` znajduja
sie pliki zrodlowe z podzialem na paczki:
- `latte` - Latte. Plik Compiler, to kompilator.
  - `latte.Absyn` - Abstract syntax. Skladniki syntaktyczne jezyka latte. Pliki wygenerowane przez bnfc.
  - `latte.errors` - Definicje bledow
  - `latte.frontend` - zawiera klase SemanticAnalyst bedaca glownym plikiem frontendu.
      - `visitors` - implementacje visitor'ow.
      - `environment` - opisuje srodowisko wykononia SemanticAnalyst'y.
  - `latte.parser` - Parser. Wszystkie pliki wygenerowane przez bnfc.
  - `latte.internal` - Typy wewnętrzne kompilatora. 
  - `latte.utils` - pomocnicze funkcje tostring.
  - `latte.backend` - zawiera implementacje backendu.
    - `program` - elementy programu takie jak funkcje, zmienne, scope'y(zakres widocznosci)
      - `global` - definicje globalnych skladnikow programu: klasy, funkcje, scope'y
        - `classes` - folder zawiera  klasy odpowiadajace poszczegolnym skladnikom realizacji klas: Klasa, Typ klasowy, Metoda, Konstruktor, VTable
      - `Program` - reprezentacja programu w postaci ssa.
    - `programvisitors` - implementacje visitor'ow dla Programu i PostProcessor'a. 
      - `PostProcessor` - przejscie jeszcze raz wygenerowanego juz kodu czworkowego celem optymalizacji kodu
    - `quadruple` - reprezentacja instrukcji LLVM w postaci ssa.

Zmienne inicjalizowane są na domyślne wartości - int -> 0, bool -> false, string -> "".
Funkcja error jest traktowana jako poprawne wyjście z funkcji (tez takiej zwracającej coś innego niż void).
Frontend nie pozwala na field shadowing, natomiast pisząc backend brałem pod uwage taka ewentualność.

Zapozyczenia:

Niektóre funkcje runtime (readInt, printInt, printString) są wzięte z pliku `runtime.ll` z katalogu `/home/students/inf/PUBLIC/MRJP/Llvm`.
