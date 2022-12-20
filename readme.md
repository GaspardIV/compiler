TODO:
WAZNE W LLVM NIE DA SIE PRZYPISAC NA REJESTR STALA!!!!! - ALE CHYBA SIE DA PO RPSUT NP % = add 5 0xd
- usuwanie martwego kodu - jak jest t1 = ..... to pozniej jak nie ma go nigdzie po prawej stronie np. x = t1 to znaczy ze mozna go wywalic
- zwijanie stalych (3 + 5)
- propagacja stalych !!!! (x = 1; y = x + x; ) + (usuwanie martwego kodu)
- propagacja kopii (zmienna ktora ma taka sama wartosc jak druga zmienna) + usuwanie martwego kodu (u_-> v1; v-> t')
- lcse
- gcse
- usuwanie maertwego kodu


Kompilator na dzien dzisiejszy sklada sie jedynie z frontendu.

Kompliacja kompilatora za pomoca polecenia: `make`

Uzycie: `./latc nazwa_pliku.lat`

Struktura:

W katalogu `third_party` znajduja sie paczki jar bibliotek java_cup i jlex, wykorzystywane przez parser. W katalogu `src` znajduja
sie pliki zrodlowe z podzialem na paczki:
- `latte` - Latte. Plik Compiler, to kompilator.
  - `latte.Absyn` - Abstract syntax. Skladniki syntaktyczne jezyka latte. Pliki wygenerowane przez bnfc.
  - `latte.errors` - Definicje bledow
  - `latte.frontend` - zawiera klase SemanticAnalyst bedaca glownym plikiem frontendu.
      - `latte.frontend.visitors` - implementacje visitor'ow.
      - `latte.frontend.environment` - opisuje srodowisko wykononia SemanticAnalyst'y.
  - `parser` - Parser. Wszystkie pliki wygenerowane przez bnfc.
  - `internal` - Typy wewnętrzne kompilatora. 
  - `utils` - pomocnicze funkcje tostring.


Zmienne inicjalizowane są na domyślne wartości - int -> 0, bool -> false, string -> "".
`null` mozna przypisac do obiektu typu array lub do obiektu typu class. Rzutowanie null na te typy jest dozwolone.
`null` nie mozna przypisac do zmiennych typu int, bool, string. Rzutowanie null na te typy jest niedozwolone.
Funkcja error jest traktowana jako poprawne wyjście z funkcji (tez takiej zwracającej coś innego niż void).
Frontend uwzglednia rozszerzenia: tablice, klasy + dziedziczenie + wirtualne metody.

Nie zdążyłem zaimplementować  sugerowanych przez Panią Doktor następujących optymalizacji, wykonam je w kolejnej wersji kompilatora:
- Usnuniecie martwego kodu
- zwijanie stalych i wyrazen logicznych

Zaporzyczenia:
Niektóre funkcje runtime (readInt, printInt, printString) są wzięte z pliku `runtime.ll` z katalogu `/home/students/inf/PUBLIC/MRJP/Llvm`.
````


todo:
- choice w template - jak nie korzystamy z danej builtin to usuwamy z template