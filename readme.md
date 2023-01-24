Oddaje kompilator llvm. Bede jeszcze oddawal rozszerzenie kompilatora.

TODO:
 - mozna usunac final z kazdego pola w ABSYN
 - tablice
 - vtable
 - ustawienie obiektow odpowiednio w pamieci - najpierw najwyzszego rodzica, potem dziedziczace dzieci
 - wirtualne metody sa usuwane, przez to ze nie sa uzywane?
 - zobaczyc na czym polega odsmiecanie pamieci - moze proste trzymanie ilosci referencji do obiektu - mallokowanie wszystkiego.
 - moze sprobwoac optymalizacje petli

Function pointers are expressed almost like in C and C++:
         int (*Function)(char *buffer);
Becomes: @Function = global i32(i8*)* null

%p = getelementptr %T, %T* null, i32 1
%s = ptrtoint %T* %p to i32


+ frontend 4
+ backend 7 (8 + 2 - 2 - 1 = 11)
+ 9 quizy
+ 5.8 projekt 1
+ -35
+ = -9.2

Co zrobilem z optymalizacji:
- zwijanie stalych (3 + 5) (5<3)
- propagacja stalych
- propagacja kopii
- postac ssa
- usuwanie martwego kodu
- usuwanie pustych blokow
- gcse
- klasy:
  - struktury
  - dziedzieczenie
  - wirualne metody


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