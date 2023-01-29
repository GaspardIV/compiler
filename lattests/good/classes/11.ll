@.str.str1 = private unnamed_addr constant [41 x i8] c"po spacerku wartosc pierwszego elementu:\00", align 1@.str.str3 = private unnamed_addr constant [19 x i8] c"Krotki test listy:\00", align 1@.str.str6 = private unnamed_addr constant [26 x i8] c"idziemy na koniec listy: \00", align 1@.str.str2 = private unnamed_addr constant [39 x i8] c"po spacerku wartosc drugiego elementu:\00", align 1@.str.str0 = private unnamed_addr constant [28 x i8] c"wracamy na poczatek listy: \00", align 1@.str.str4 = private unnamed_addr constant [41 x i8] c"wygenerowal liste 2kierunkowa dlugosci :\00", align 1@.str.str5 = private unnamed_addr constant [11 x i8] c"__________\00", align 1 ; --- Class Lista ---
@Lista.vtable = global [0 x void (...)*] [
]

%Lista = type { 
	void (...)**; vtable
	,%Lista*; poprzedni 
	,%Lista*; nastepny 
	,i32; wartosc 
	}
define void @Lista.constructor(%Lista* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @Lista.vtable to void (...)**
	%this.vtable = getelementptr %Lista, %Lista* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%poprzedni = getelementptr %Lista, %Lista* %this, i32 0, i32 1
	%poprzednitmp = bitcast i32* null to %Lista*
	store %Lista* %poprzednitmp, %Lista** %poprzedni
	%nastepny = getelementptr %Lista, %Lista* %this, i32 0, i32 2
	%nastepnytmp = bitcast i32* null to %Lista*
	store %Lista* %nastepnytmp, %Lista** %nastepny
	%wartosc = getelementptr %Lista, %Lista* %this, i32 0, i32 3
	store i32 0, i32* %wartosc
	ret void
}

define %Lista* @wrocNaPoczatekIWypisuj(%Lista* %odKonca, i32 %dl) { 
wrocNaPoczatekIWypisuj_entry:
	%tmp. = bitcast i32* null to %Lista*
	%tmp..1 = getelementptr [28 x i8], [28 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..1)
	br label %wrocNaPoczatekIWypisuj.1_while.cond
wrocNaPoczatekIWypisuj.1_while.cond:
	%a = phi %Lista* [%odKonca, %wrocNaPoczatekIWypisuj_entry], [%tmp..11, %wrocNaPoczatekIWypisuj.2_while.body]
	%b = phi %Lista* [%tmp., %wrocNaPoczatekIWypisuj_entry], [%a, %wrocNaPoczatekIWypisuj.2_while.body]
	%w = phi i32 [%dl, %wrocNaPoczatekIWypisuj_entry], [%tmp..13, %wrocNaPoczatekIWypisuj.2_while.body]
	%tmp..6 = icmp sgt i32 %w, 0
	br i1 %tmp..6, label %wrocNaPoczatekIWypisuj.2_while.body, label %wrocNaPoczatekIWypisuj.3_while.end
wrocNaPoczatekIWypisuj.2_while.body:
	%tmp..7 = getelementptr %Lista, %Lista* %a, i32 0, i32 3
	%tmp..8 = load i32, i32* %tmp..7
	call void @printInt(i32 %tmp..8)
	%tmp..10 = getelementptr %Lista, %Lista* %a, i32 0, i32 1
	%tmp..11 = load %Lista*, %Lista** %tmp..10
	%tmp..13 = sub i32 %w, 1
	br label %wrocNaPoczatekIWypisuj.1_while.cond
wrocNaPoczatekIWypisuj.3_while.end:
	ret %Lista* %b
}

define void @listaTest(i32 %dlugoscListy) { 
listaTest_entry:
	%tmp..1 = call %Lista* @zwrocListeDlugosci(i32 %dlugoscListy)
	%tmp..2 = call %Lista* @przejdzSieNaKoniecIWypisuj(%Lista* %tmp..1, i32 %dlugoscListy)
	%tmp..3 = call %Lista* @wrocNaPoczatekIWypisuj(%Lista* %tmp..2, i32 %dlugoscListy)
	%tmp..4 = getelementptr [41 x i8], [41 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..4)
	%tmp..6 = getelementptr %Lista, %Lista* %tmp..3, i32 0, i32 3
	%tmp..7 = load i32, i32* %tmp..6
	call void @printInt(i32 %tmp..7)
	%tmp..12 = icmp sge i32 %dlugoscListy, 5
	br i1 %tmp..12, label %listaTest.1_if.true, label %listaTest.2_if.false
listaTest.1_if.true:
	%tmp..13 = getelementptr %Lista, %Lista* %tmp..3, i32 0, i32 2
	%tmp..14 = load %Lista*, %Lista** %tmp..13
	%tmp..15 = getelementptr [39 x i8], [39 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp..15)
	%tmp..18 = load %Lista*, %Lista** %tmp..13
	%tmp..19 = getelementptr %Lista, %Lista* %tmp..18, i32 0, i32 3
	%tmp..20 = load i32, i32* %tmp..19
	call void @printInt(i32 %tmp..20)
	br label %listaTest.3_if.end
listaTest.2_if.false:
	ret void
listaTest.3_if.end:
	ret void
}

define %Lista* @zwrocListeDlugosci(i32 %dlugoscListy) { 
zwrocListeDlugosci_entry:
	%tmp. = getelementptr [19 x i8], [19 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = call i8* @malloc(i32 160)
	%tmp..3 = bitcast i8* %tmp..2 to %Lista*
	call void @Lista.constructor(%Lista* %tmp..3)
	%tmp..5 = bitcast i32* null to %Lista*
	%tmp..8 = getelementptr %Lista, %Lista* %tmp..3, i32 0, i32 3
	store i32 0, i32* %tmp..8
	br label %zwrocListeDlugosci.1_while.cond
zwrocListeDlugosci.1_while.cond:
	%a = phi %Lista* [%tmp..3, %zwrocListeDlugosci_entry], [%tmp..19, %zwrocListeDlugosci.2_while.body]
	%wsk = phi i32 [1, %zwrocListeDlugosci_entry], [%tmp..23, %zwrocListeDlugosci.2_while.body]
	%tmp..11 = icmp ne i32 %wsk, %dlugoscListy
	br i1 %tmp..11, label %zwrocListeDlugosci.2_while.body, label %zwrocListeDlugosci.3_while.end
zwrocListeDlugosci.2_while.body:
	%tmp..12 = getelementptr %Lista, %Lista* %tmp..3, i32 0, i32 1
	store %Lista* %tmp..5, %Lista** %tmp..12
	%tmp..14 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	%tmp..15 = call i8* @malloc(i32 160)
	%tmp..16 = bitcast i8* %tmp..15 to %Lista*
	call void @Lista.constructor(%Lista* %tmp..16)
	store %Lista* %tmp..16, %Lista** %tmp..14
	%tmp..19 = load %Lista*, %Lista** %tmp..14
	%tmp..20 = getelementptr %Lista, %Lista* %tmp..19, i32 0, i32 3
	store i32 %wsk, i32* %tmp..20
	%tmp..21 = getelementptr %Lista, %Lista* %tmp..19, i32 0, i32 1
	store %Lista* %a, %Lista** %tmp..21
	%tmp..23 = add i32 %wsk, 1
	br label %zwrocListeDlugosci.1_while.cond
zwrocListeDlugosci.3_while.end:
	%tmp..24 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	store %Lista* %tmp..5, %Lista** %tmp..24
	%tmp..26 = getelementptr [41 x i8], [41 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp..26)
	call void @printInt(i32 %dlugoscListy)
	%tmp..29 = getelementptr [11 x i8], [11 x i8]* @.str.str5, i32 0, i32 0
	call void @printString(i8* %tmp..29)
	ret %Lista* %tmp..3
}

define %Lista* @przejdzSieNaKoniecIWypisuj(%Lista* %start, i32 %dl) { 
przejdzSieNaKoniecIWypisuj_entry:
	%tmp. = getelementptr [26 x i8], [26 x i8]* @.str.str6, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %przejdzSieNaKoniecIWypisuj.1_while.cond
przejdzSieNaKoniecIWypisuj.1_while.cond:
	%a = phi %Lista* [%start, %przejdzSieNaKoniecIWypisuj_entry], [%tmp..9, %przejdzSieNaKoniecIWypisuj.2_while.body]
	%b = phi %Lista* [%start, %przejdzSieNaKoniecIWypisuj_entry], [%a, %przejdzSieNaKoniecIWypisuj.2_while.body]
	%i = phi i32 [0, %przejdzSieNaKoniecIWypisuj_entry], [%tmp..11, %przejdzSieNaKoniecIWypisuj.2_while.body]
	%tmp..4 = icmp slt i32 %i, %dl
	br i1 %tmp..4, label %przejdzSieNaKoniecIWypisuj.2_while.body, label %przejdzSieNaKoniecIWypisuj.3_while.end
przejdzSieNaKoniecIWypisuj.2_while.body:
	%tmp..5 = getelementptr %Lista, %Lista* %a, i32 0, i32 3
	%tmp..6 = load i32, i32* %tmp..5
	call void @printInt(i32 %tmp..6)
	%tmp..8 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	%tmp..9 = load %Lista*, %Lista** %tmp..8
	%tmp..11 = add i32 %i, 1
	br label %przejdzSieNaKoniecIWypisuj.1_while.cond
przejdzSieNaKoniecIWypisuj.3_while.end:
	ret %Lista* %b
}

define i32 @main() { 
main_entry:
	call void @listaTest(i32 30)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

@._dnl = internal constant [4 x i8] c"%d\0A\00"
declare i32 @printf(i8*, ...)
define void @printInt(i32 %x) {
       %t0 = getelementptr [4 x i8], [4 x i8]* @._dnl, i32 0, i32 0
       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)
       ret void
}

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

declare i8* @malloc(i32)
