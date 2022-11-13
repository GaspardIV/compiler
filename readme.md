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
- ToString() dla klas z Absyn
- Usuniecie coponiektorych komunikatow o bledach z parsera, w celu uzyskania zgodnosci z wyjściem z testów ("Error" w pierwszej linii)
Pozbyłem się również zbędnych plikow pośrednich generowanych przez bnfc.


Do wygenerowania parsera uzylem bnfc 2.9.3:
`    bnfc-2.9.3-linux-x86_64.binary --java -l LatteLang.cf -m`
Opcja -l dla javy odpowiada za dodanie numeru linii do tokenow. Numer kolumny nie jest poprawnie generowany przez wykorzystywaną przez bnfc biblioteke java_cup. 
W przypadku niewystarczajacej ilosci informacji o lokalizacji błedu, moge sprobowac wykorzystać antlr4, zamiast java_cup i jlex w nastepnej iteracji projektu.


Bnfc wykorzystuje bibloteki java_cup do parsera, i jlex jako lekser.

`java_cup 11b`
`jlex 1.2.6`

=============================== O czym warto wspomnieć ===============================
Zmienne inicjalizowane są na domyślne wartości - int -> 0, bool -> false, string -> "".
(?) W szczegolnosci w przypadku stringow, nie mozna przypisac do nich null, poniewaz jest to typ prosty.
W przypadku uzycia zmiennej niezainicjalizowanej przy jej inicializacji, zostanie ona zainicjalizowana na domyslne wartosci. -> jeszcze moze sie to zmienic, ale na razie tak jest.



todo:
- dodac offset do komunikatow o bledach
- dodac buff do komunikatow o bledach