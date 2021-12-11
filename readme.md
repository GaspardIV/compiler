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

Do wygenerowania parsera uzylem bnfc 2.9.3:

`    bnfc-2.9.3-linux-x86_64.binary --java -l LatteLang.cf -m`

Bnfc wykorzystuje bibloteki java_cup do parsera, i jlex jako lekser.

`java_cup 11b`
`jlex 1.2.6`

