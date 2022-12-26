TODO:
WAZNE W LLVM NIE DA SIE PRZYPISAC NA REJESTR STALA!!!!! - ALE CHYBA SIE DA PO RPSUT NP % = add 5 0xd
- wszystkie nazwy z kropkami np %r.1, %r.2, itp..
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
- template - strcat 
``zastnowanie sie nad tyym To concatenate two string variables in LLVM, you can use the strcat function. Here is an example of how to do it:

Copy code
%result = call i8* @strcat(i8* %str1, i8* %str2)
This will concatenate the two string variables %str1 and %str2, and store the result in the %result variable.

Note that strcat requires the two strings to be null-terminated, so make sure that both %str1 and %str2 are properly terminated.

If you want to concatenate two string literals instead of variables, you can use the getelementptr and store instructions to create a new string that combines the two literals. Here is an example:

Copy code
%str1 = constant [4 x i8] c"abc\00"
%str2 = constant [4 x i8] c"def\00"
%result_str = getelementptr [8 x i8], [8 x i8]* null, i32 0, i32 0
%result_str = call i8* @llvm.memcpy.p0i8.p0i8.i64(i8* %result_str, i8* %str1, i64 4, i32 4, i1 false)
%result_str = call i8* @llvm.memcpy.p0i8.p0i8.i64(i8* %result_str, i8* %str2, i64 4, i32 4, i1 false)
This will create a new string that combines the two string literals "abc" and "def", and store the result in the %result_str variable.

I hope this helps! Let me know if you have any questions.```