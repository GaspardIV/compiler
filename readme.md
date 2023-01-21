Oddaje kompilator llvm. Bede jeszcze oddawal rozszerzenie kompilatora.

4 + 8 + 2 - 2 - 1 = 11

Co zrobilem z optymalizacji:
- usuwanie martwego kodu (np. if (false) { ... }, kod po returnie, nieużywane zmienne)
- zwijanie stalych (3 + 5)
- propagacja stalych
- czesciowa propagacja kopii
- ssa postac kodu

Czego nie zrobilem a chcialbym w nastepnej wersji
- lcse
- gcse (?)
- klasy
- tablice
- usuwanie zbednych phi - bardzo brzydko teraz to wyglada 
- bezpieczne readstring (?) - uzywam niebezpiecznej funkcji gets()
- mozna jeszcze poprawic kod skaczacy
- duzo jeszcze mozna poprawic


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
  - `parser` - Parser. Wszystkie pliki wygenerowane przez bnfc.
  - `internal` - Typy wewnętrzne kompilatora. 
  - `utils` - pomocnicze funkcje tostring.
  - `latte.backend` - zawiera implementacje backendu.
    - `program` - elementy programu takie jak funkcje, zmienne, scope'y(zakres widocznosci)
    - `IsExprBoolTypeManager` - sprawdza czy wyrazenie jest typu bool wymagajace skaczacego kodu.
    - `Program` - reprezentacja programu w postaci ssa.
    - `programvisitors` - implementacje visitor'ow dla Programu.
    - `quadruple` - reprezentacja instrukcji LLVM w postaci ssa.



Zmienne inicjalizowane są na domyślne wartości - int -> 0, bool -> false, string -> "".
`null` mozna przypisac do obiektu typu array lub do obiektu typu class. Rzutowanie null na te typy jest dozwolone.
`null` nie mozna przypisac do zmiennych typu int, bool, string. Rzutowanie null na te typy jest niedozwolone.
Funkcja error jest traktowana jako poprawne wyjście z funkcji (tez takiej zwracającej coś innego niż void).
Frontend uwzglednia rozszerzenia: tablice, klasy + dziedziczenie + wirtualne metody.

Zaporzyczenia:
Niektóre funkcje runtime (readInt, printInt, printString) są wzięte z pliku `runtime.ll` z katalogu `/home/students/inf/PUBLIC/MRJP/Llvm`.
````