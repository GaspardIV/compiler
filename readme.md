Konflikty shift/reduce w gramatyce biorą się z:
 - ENullArr.      Expr6 ::= "(" Type "[]" ")" "null" ;
 - ENull.      Expr6 ::= "(" Ident ")" "null" ;


 - EArrayElem. Expr7 ::= Expr6 "[" Expr "]";
 - EArrayElemR. Expr7 ::= Ident "[" Expr "]";
  

 - ENewArray.  Expr6 ::= "new" Type "[" Expr "]" ;
 - ENew.       Expr6 ::= "new" Type ;



Optymalizacje uwzgledniaja:
- zwijanie stalych
- propagacja stalych
- propagacja kopii
- postac ssa
- usuwanie martwego kodu
- usuwanie pustych i niepotrzebnych blokow(uznalem za potrzebne te co biorą udział w jakichs phi)
- GCSE (5)

Rozszerzenia uwzgledniaja
- obiekty z metodami wirtualnymi (8)
- tablice (1)



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
Frontend uwzglednia rozszerzenia: tablice, klasy + dziedziczenie + wirtualne metody.
Frontend nie pozwala na field shadowing, natomiast pisząc backend brałem pod uwage taka ewentualność.
Zapozyczenia:
Niektóre funkcje runtime (readInt, printInt, printString) są wzięte z pliku `runtime.ll` z katalogu `/home/students/inf/PUBLIC/MRJP/Llvm`.
````