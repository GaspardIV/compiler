@.str.str0 = private unnamed_addr constant [46 x i8] c"idziemy na koniec listy (ma byc nierosnaco): \00", align 1@.str.str2 = private unnamed_addr constant [48 x i8] c"wygenerowal liste 2kierunkowa dziwna dlugosci :\00", align 1@.str.str3 = private unnamed_addr constant [11 x i8] c"__________\00", align 1@.str.str1 = private unnamed_addr constant [28 x i8] c"robimy liste do mergeSorta:\00", align 1 ; --- Class Lista ---
@Lista.vtable = global [0 x void (...)*] [
]

%Lista = type { 
	void (...)**,
	%Lista*; poprzedni 
,
	%Lista*; nastepny 
,
	i32; wartosc 
}
define void @Lista.constructor(%Lista* %this) {
	%this.vtable = bitcast [0 x void (...)*]* @Lista.vtable to void (...)**
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

define void @testMergeSort(i32 %dlugoscListy) { 
testMergeSort_entry:
	%tmp..1 = call %Lista* @generujTablicoListeDoSortowaniaMerge13co7Malejaco(i32 %dlugoscListy, i1 true)
	%tmp..3 = call %Lista* @mergeSort(%Lista* %tmp..1, i32 0, i32 %dlugoscListy)
	%tmp..4 = call %Lista* @przejdzSieNaKoniecIWypisuj(%Lista* %tmp..3, i32 %dlugoscListy)
	ret void
}

define void @ladujWartosc(%Lista* %start, i32 %poz, i32 %wartosc) { 
ladujWartosc_entry:
	br label %ladujWartosc.1_while.cond
ladujWartosc.1_while.cond:
	%a = phi %Lista* [%start, %ladujWartosc_entry], [%tmp..4, %ladujWartosc.2_while.body]
	%w = phi i32 [0, %ladujWartosc_entry], [%tmp..6, %ladujWartosc.2_while.body]
	%tmp..2 = icmp ne i32 %w, %poz
	br i1 %tmp..2, label %ladujWartosc.2_while.body, label %ladujWartosc.3_while.end
ladujWartosc.2_while.body:
	%tmp..3 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	%tmp..4 = load %Lista*, %Lista** %tmp..3
	%tmp..6 = add i32 %w, 1
	br label %ladujWartosc.1_while.cond
ladujWartosc.3_while.end:
	%tmp..7 = getelementptr %Lista, %Lista* %a, i32 0, i32 3
	store i32 %wartosc, i32* %tmp..7
	ret void
}

define %Lista* @przejdzSieNaKoniecIWypisuj(%Lista* %start, i32 %dl) { 
przejdzSieNaKoniecIWypisuj_entry:
	%tmp. = getelementptr [46 x i8], [46 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %przejdzSieNaKoniecIWypisuj.1_while.cond
przejdzSieNaKoniecIWypisuj.1_while.cond:
	%a = phi %Lista* [%start, %przejdzSieNaKoniecIWypisuj_entry], [%tmp..9, %przejdzSieNaKoniecIWypisuj.2_while.body]
	%b = phi %Lista* [%start, %przejdzSieNaKoniecIWypisuj_entry], [%a, %przejdzSieNaKoniecIWypisuj.2_while.body]
	%wsk = phi i32 [0, %przejdzSieNaKoniecIWypisuj_entry], [%tmp..11, %przejdzSieNaKoniecIWypisuj.2_while.body]
	%tmp..4 = icmp slt i32 %wsk, %dl
	br i1 %tmp..4, label %przejdzSieNaKoniecIWypisuj.2_while.body, label %przejdzSieNaKoniecIWypisuj.3_while.end
przejdzSieNaKoniecIWypisuj.2_while.body:
	%tmp..5 = getelementptr %Lista, %Lista* %a, i32 0, i32 3
	%tmp..6 = load i32, i32* %tmp..5
	call void @printInt(i32 %tmp..6)
	%tmp..8 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	%tmp..9 = load %Lista*, %Lista** %tmp..8
	%tmp..11 = add i32 %wsk, 1
	br label %przejdzSieNaKoniecIWypisuj.1_while.cond
przejdzSieNaKoniecIWypisuj.3_while.end:
	ret %Lista* %b
}

define i32 @main() { 
main_entry:
	call void @testMergeSort(i32 30)
	ret i32 0
}

define %Lista* @mergeSort(%Lista* %start, i32 %pocz, i32 %kon1Za) { 
mergeSort_entry:
	%tmp..5 = sub i32 %kon1Za, %pocz
	%tmp..7 = icmp sgt i32 %tmp..5, 1
	br i1 %tmp..7, label %mergeSort.1_if.true, label %mergeSort.3_if.end
mergeSort.1_if.true:
	%tmp..10 = sdiv i32 %tmp..5, 2
	%tmp..11 = add i32 %tmp..10, %pocz
	%tmp..12 = call %Lista* @mergeSort(%Lista* %start, i32 %pocz, i32 %tmp..11)
	%tmp..13 = call %Lista* @mergeSort(%Lista* %start, i32 %tmp..11, i32 %kon1Za)
	%tmp..14 = sub i32 %tmp..11, %pocz
	%tmp..15 = sub i32 %kon1Za, %tmp..11
	%tmp..16 = call %Lista* @scalaj(%Lista* %tmp..12, i32 %tmp..14, %Lista* %tmp..13, i32 %tmp..15)
	ret %Lista* %tmp..16
mergeSort.3_if.end:
	%tmp..17 = call i8* @malloc(i32 160)
	%tmp..18 = bitcast i8* %tmp..17 to %Lista*
	call void @Lista.constructor(%Lista* %tmp..18)
	%tmp..20 = getelementptr %Lista, %Lista* %tmp..18, i32 0, i32 3
	%tmp..21 = call i32 @pokazWartosc(%Lista* %start, i32 %pocz)
	store i32 %tmp..21, i32* %tmp..20
	ret %Lista* %tmp..18
}

define %Lista* @scalaj(%Lista* %lj, i32 %ljLength, %Lista* %ld, i32 %ldLength) { 
scalaj_entry:
	%tmp..3 = add i32 %ljLength, %ldLength
	%tmp..5 = call %Lista* @generujTablicoListeDoSortowaniaMerge13co7Malejaco(i32 %tmp..3, i1 false)
	%tmp..6 = call i32 @pokazWartosc(%Lista* %lj, i32 0)
	%tmp..7 = call i32 @pokazWartosc(%Lista* %ld, i32 0)
	br label %scalaj.1_while.cond
scalaj.1_while.cond:
	%w1 = phi i32 [0, %scalaj_entry], [%w1.1, %scalaj.12_if.end]
	%w2 = phi i32 [0, %scalaj_entry], [%w2.1, %scalaj.12_if.end]
	%ws = phi i32 [0, %scalaj_entry], [%tmp..31, %scalaj.12_if.end]
	%tmp..11 = icmp slt i32 %ws, %tmp..3
	br i1 %tmp..11, label %scalaj.2_while.body, label %scalaj.3_while.end
scalaj.2_while.body:
	%tmp..13 = icmp eq i32 %w1, %ljLength
	br i1 %tmp..13, label %scalaj.4_if.true, label %scalaj.5_if.false
scalaj.4_if.true:
	br label %scalaj.6_if.end
scalaj.5_if.false:
	%tmp..16 = call i32 @pokazWartosc(%Lista* %lj, i32 %w1)
	br label %scalaj.6_if.end
scalaj.6_if.end:
	%wart.1 = phi i32 [-1, %scalaj.4_if.true], [%tmp..16, %scalaj.5_if.false]
	%tmp..18 = icmp eq i32 %w2, %ldLength
	br i1 %tmp..18, label %scalaj.7_if.true, label %scalaj.8_if.false
scalaj.7_if.true:
	br label %scalaj.9_if.end
scalaj.8_if.false:
	%tmp..21 = call i32 @pokazWartosc(%Lista* %ld, i32 %w2)
	br label %scalaj.9_if.end
scalaj.9_if.end:
	%wart2.1 = phi i32 [-1, %scalaj.7_if.true], [%tmp..21, %scalaj.8_if.false]
	%tmp..23 = icmp sgt i32 %wart2.1, %wart.1
	br i1 %tmp..23, label %scalaj.10_if.true, label %scalaj.11_if.false
scalaj.10_if.true:
	%tmp..25 = add i32 %w2, 1
	call void @ladujWartosc(%Lista* %tmp..5, i32 %ws, i32 %wart2.1)
	br label %scalaj.12_if.end
scalaj.11_if.false:
	%tmp..28 = add i32 %w1, 1
	call void @ladujWartosc(%Lista* %tmp..5, i32 %ws, i32 %wart.1)
	br label %scalaj.12_if.end
scalaj.12_if.end:
	%w1.1 = phi i32 [%w1, %scalaj.10_if.true], [%tmp..28, %scalaj.11_if.false]
	%w2.1 = phi i32 [%tmp..25, %scalaj.10_if.true], [%w2, %scalaj.11_if.false]
	%tmp..31 = add i32 %ws, 1
	br label %scalaj.1_while.cond
scalaj.3_while.end:
	ret %Lista* %tmp..5
}

define %Lista* @generujTablicoListeDoSortowaniaMerge13co7Malejaco(i32 %dlugoscListy, i1 %pisz) { 
generujTablicoListeDoSortowaniaMerge13co7Malejaco_entry:
	br i1 %pisz, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.1_if.true, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.3_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.1_if.true:
	%tmp. = getelementptr [28 x i8], [28 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.3_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.3_if.end:
	%tmp..2 = call i8* @malloc(i32 160)
	%tmp..3 = bitcast i8* %tmp..2 to %Lista*
	call void @Lista.constructor(%Lista* %tmp..3)
	%tmp..5 = bitcast i32* null to %Lista*
	%tmp..8 = getelementptr %Lista, %Lista* %tmp..3, i32 0, i32 3
	store i32 0, i32* %tmp..8
	br i1 %pisz, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.4_if.true, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.6_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.4_if.true:
	%tmp..11 = load i32, i32* %tmp..8
	call void @printInt(i32 %tmp..11)
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.6_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.6_if.end:
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.7_while.cond
generujTablicoListeDoSortowaniaMerge13co7Malejaco.7_while.cond:
	%a = phi %Lista* [%tmp..3, %generujTablicoListeDoSortowaniaMerge13co7Malejaco.6_if.end], [%tmp..22, %generujTablicoListeDoSortowaniaMerge13co7Malejaco.15_if.end]
	%wsk = phi i32 [1, %generujTablicoListeDoSortowaniaMerge13co7Malejaco.6_if.end], [%tmp..43, %generujTablicoListeDoSortowaniaMerge13co7Malejaco.15_if.end]
	%tmp..14 = icmp ne i32 %wsk, %dlugoscListy
	br i1 %tmp..14, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.8_while.body, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.9_while.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.8_while.body:
	%tmp..15 = getelementptr %Lista, %Lista* %tmp..3, i32 0, i32 1
	store %Lista* %tmp..5, %Lista** %tmp..15
	%tmp..17 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	%tmp..18 = call i8* @malloc(i32 160)
	%tmp..19 = bitcast i8* %tmp..18 to %Lista*
	call void @Lista.constructor(%Lista* %tmp..19)
	store %Lista* %tmp..19, %Lista** %tmp..17
	%tmp..22 = load %Lista*, %Lista** %tmp..17
	%tmp..28 = srem i32 %wsk, 5
	%tmp..30 = icmp eq i32 %tmp..28, 3
	br i1 %tmp..30, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.10_if.true, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.11_if.false
generujTablicoListeDoSortowaniaMerge13co7Malejaco.10_if.true:
	%tmp..31 = getelementptr %Lista, %Lista* %tmp..22, i32 0, i32 3
	%tmp..33 = sdiv i32 %wsk, 2
	%tmp..34 = sub i32 %dlugoscListy, %tmp..33
	store i32 %tmp..34, i32* %tmp..31
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.12_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.11_if.false:
	%tmp..35 = getelementptr %Lista, %Lista* %tmp..22, i32 0, i32 3
	%tmp..37 = srem i32 %wsk, 13
	store i32 %tmp..37, i32* %tmp..35
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.12_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.12_if.end:
	%tmp..38 = getelementptr %Lista, %Lista* %tmp..22, i32 0, i32 1
	store %Lista* %a, %Lista** %tmp..38
	br i1 %pisz, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.13_if.true, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.15_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.13_if.true:
	%tmp..39 = getelementptr %Lista, %Lista* %tmp..22, i32 0, i32 3
	%tmp..40 = load i32, i32* %tmp..39
	call void @printInt(i32 %tmp..40)
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.15_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.15_if.end:
	%tmp..43 = add i32 %wsk, 1
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.7_while.cond
generujTablicoListeDoSortowaniaMerge13co7Malejaco.9_while.end:
	%tmp..44 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	store %Lista* %tmp..5, %Lista** %tmp..44
	br i1 %pisz, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.16_if.true, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.18_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.16_if.true:
	%tmp..46 = getelementptr [48 x i8], [48 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp..46)
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.18_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.18_if.end:
	br i1 %pisz, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.19_if.true, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.21_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.19_if.true:
	call void @printInt(i32 %dlugoscListy)
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.21_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.21_if.end:
	br i1 %pisz, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.22_if.true, label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.24_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.22_if.true:
	%tmp..49 = getelementptr [11 x i8], [11 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp..49)
	br label %generujTablicoListeDoSortowaniaMerge13co7Malejaco.24_if.end
generujTablicoListeDoSortowaniaMerge13co7Malejaco.24_if.end:
	ret %Lista* %tmp..3
}

define i32 @pokazWartosc(%Lista* %start, i32 %poz) { 
pokazWartosc_entry:
	br label %pokazWartosc.1_while.cond
pokazWartosc.1_while.cond:
	%a = phi %Lista* [%start, %pokazWartosc_entry], [%tmp..4, %pokazWartosc.2_while.body]
	%w = phi i32 [0, %pokazWartosc_entry], [%tmp..6, %pokazWartosc.2_while.body]
	%tmp..2 = icmp ne i32 %w, %poz
	br i1 %tmp..2, label %pokazWartosc.2_while.body, label %pokazWartosc.3_while.end
pokazWartosc.2_while.body:
	%tmp..3 = getelementptr %Lista, %Lista* %a, i32 0, i32 2
	%tmp..4 = load %Lista*, %Lista** %tmp..3
	%tmp..6 = add i32 %w, 1
	br label %pokazWartosc.1_while.cond
pokazWartosc.3_while.end:
	%tmp..7 = getelementptr %Lista, %Lista* %a, i32 0, i32 3
	%tmp..8 = load i32, i32* %tmp..7
	ret i32 %tmp..8
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
