./bnfc-2.9.3-linux-x86_64.binary --java -l Latte.cf -m

java_cup 11b

jlex 1.2.6



```
#+TITLE: Mjrp


* Frontend <2021-11-30 wto>
** do zrobienia
- Analizator kontekstowy.
  + Dobre robienie łapania błędów
  + Można stracić za to, że nie raportujemy lokalizacji błędu.
  + Trzeba wyłapać WSZYSTKIE błędy
- to co jest trudne:
  + trzeba wymyślić regóły:
    to może zając trochę czasu
- zmienić gramatykę aby uwzględniała (potencjalne) rozszerzenia
- w testach nie będą sprawdzane rozszerzenia
** pytania:
- Co ma zwracać / czym jest frontend?
  + ma robić analizę semantyczną :: sprawdzać semantykę i wypisywać jaki błąd

* Generator kodu (backend)<2022-01-10 pon>
** do zrobienia
- 4 / 5 (może 3)
  - trudne do zrobienia
  - reprezentacja pośrednia programu:
    - należy zrobić sobie reprezentację w postaci kodu 4-kowego a z niego na assemblera(?)

- warto generować do assemblera i traktować go jako maszynę stosową
  - szczyt stosu w którymś rejestrze(EAX) i za to jest *10

* Rozszerzenia<2022-01-24 pon>
- 1-4 są łatwe
- 5. warto zrobić odśmiecanie jeżeli będzie brakowało pk.
  - Odśmiecanie tylko dla napisów?
  - reference counting
- 6 *NIE ROBIĆ*


quizy + programy / min 35
* Strategie
** łatwa x86 (26 pk)
- rozszerzenia 1-4 od początku
  - dodatnie ich do frontend
- backend x86 w postaci maszyny stosowej (ten asembler)
- rozszerzenia na siebie wpływają
  - musi być możliwe robienie tablic obiektów itd.
```