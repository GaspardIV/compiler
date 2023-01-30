@.str.str2 = private unnamed_addr constant [6 x i8] c"A::m3\00", align 1@.str.str26 = private unnamed_addr constant [7 x i8] c"D as D\00", align 1@.str.str1 = private unnamed_addr constant [6 x i8] c"A::m2\00", align 1@.str.str17 = private unnamed_addr constant [7 x i8] c"A as A\00", align 1@.str.str0 = private unnamed_addr constant [6 x i8] c"A::m1\00", align 1@.str.str24 = private unnamed_addr constant [7 x i8] c"D as B\00", align 1@.str.str25 = private unnamed_addr constant [7 x i8] c"D as C\00", align 1@.str.str4 = private unnamed_addr constant [6 x i8] c"B::m4\00", align 1@.str.str21 = private unnamed_addr constant [7 x i8] c"C as B\00", align 1@.str.str20 = private unnamed_addr constant [7 x i8] c"C as A\00", align 1@.str.str23 = private unnamed_addr constant [7 x i8] c"D as A\00", align 1@.str.str5 = private unnamed_addr constant [6 x i8] c"B::m5\00", align 1@.str.str22 = private unnamed_addr constant [7 x i8] c"C as C\00", align 1@.str.str3 = private unnamed_addr constant [6 x i8] c"B::m2\00", align 1@.str.str8 = private unnamed_addr constant [6 x i8] c"C::m5\00", align 1@.str.str18 = private unnamed_addr constant [7 x i8] c"B as A\00", align 1@.str.str19 = private unnamed_addr constant [7 x i8] c"B as B\00", align 1@.str.str7 = private unnamed_addr constant [6 x i8] c"C::m2\00", align 1@.str.str11 = private unnamed_addr constant [6 x i8] c"C::m8\00", align 1@.str.str10 = private unnamed_addr constant [6 x i8] c"C::m7\00", align 1@.str.str9 = private unnamed_addr constant [6 x i8] c"C::m6\00", align 1@.str.str16 = private unnamed_addr constant [6 x i8] c"D::m9\00", align 1@.str.str6 = private unnamed_addr constant [6 x i8] c"C::m1\00", align 1@.str.str12 = private unnamed_addr constant [6 x i8] c"D::m1\00", align 1@.str.str13 = private unnamed_addr constant [6 x i8] c"D::m4\00", align 1@.str.str14 = private unnamed_addr constant [6 x i8] c"D::m6\00", align 1@.str.str15 = private unnamed_addr constant [6 x i8] c"D::m7\00", align 1 ; --- Class A ---
@A.vtable = global [3 x void (...)*] [
	void (...)* bitcast (void (%A*)* @A.m1 to void (...)*) , ; m1 
	void (...)* bitcast (void (%A*)* @A.m2 to void (...)*) , ; m2 
	void (...)* bitcast (void (%A*)* @A.m3 to void (...)*) ; m3 
]

%A = type { 
	void (...)**; vtable
	}
 ; --- Class B ---
@B.vtable = global [5 x void (...)*] [
	void (...)* bitcast (void (%A*)* @A.m1 to void (...)*) , ; m1 
	void (...)* bitcast (void (%B*)* @B.m2 to void (...)*) , ; m2 
	void (...)* bitcast (void (%A*)* @A.m3 to void (...)*) , ; m3 
	void (...)* bitcast (void (%B*)* @B.m4 to void (...)*) , ; m4 
	void (...)* bitcast (void (%B*)* @B.m5 to void (...)*) ; m5 
]

%B = type { 
	void (...)**; vtable
	}
 ; --- Class C ---
@C.vtable = global [8 x void (...)*] [
	void (...)* bitcast (void (%C*)* @C.m1 to void (...)*) , ; m1 
	void (...)* bitcast (void (%C*)* @C.m2 to void (...)*) , ; m2 
	void (...)* bitcast (void (%A*)* @A.m3 to void (...)*) , ; m3 
	void (...)* bitcast (void (%B*)* @B.m4 to void (...)*) , ; m4 
	void (...)* bitcast (void (%C*)* @C.m5 to void (...)*) , ; m5 
	void (...)* bitcast (void (%C*)* @C.m6 to void (...)*) , ; m6 
	void (...)* bitcast (void (%C*)* @C.m7 to void (...)*) , ; m7 
	void (...)* bitcast (void (%C*)* @C.m8 to void (...)*) ; m8 
]

%C = type { 
	void (...)**; vtable
	}
 ; --- Class D ---
@D.vtable = global [9 x void (...)*] [
	void (...)* bitcast (void (%D*)* @D.m1 to void (...)*) , ; m1 
	void (...)* bitcast (void (%C*)* @C.m2 to void (...)*) , ; m2 
	void (...)* bitcast (void (%A*)* @A.m3 to void (...)*) , ; m3 
	void (...)* bitcast (void (%D*)* @D.m4 to void (...)*) , ; m4 
	void (...)* bitcast (void (%C*)* @C.m5 to void (...)*) , ; m5 
	void (...)* bitcast (void (%D*)* @D.m6 to void (...)*) , ; m6 
	void (...)* bitcast (void (%D*)* @D.m7 to void (...)*) , ; m7 
	void (...)* bitcast (void (%C*)* @C.m8 to void (...)*) , ; m8 
	void (...)* bitcast (void (%D*)* @D.m9 to void (...)*) ; m9 
]

%D = type { 
	void (...)**; vtable
	}
 ; --- Class A methods ---
define void @A.constructor(%A* %this) {
	%this.class.vtable = bitcast [3 x void (...)*]* @A.vtable to void (...)**
	%this.vtable = getelementptr %A, %A* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @A.m1(%A* %self) { 
A.m1_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @A.m2(%A* %self) { 
A.m2_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @A.m3(%A* %self) { 
A.m3_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class B methods ---
define void @B.constructor(%B* %this) {
	%this.class.vtable = bitcast [5 x void (...)*]* @B.vtable to void (...)**
	%this.vtable = getelementptr %B, %B* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @B.m2(%B* %self) { 
B.m2_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @B.m4(%B* %self) { 
B.m4_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @B.m5(%B* %self) { 
B.m5_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str5, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class C methods ---
define void @C.constructor(%C* %this) {
	%this.class.vtable = bitcast [8 x void (...)*]* @C.vtable to void (...)**
	%this.vtable = getelementptr %C, %C* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @C.m1(%C* %self) { 
C.m1_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str6, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @C.m2(%C* %self) { 
C.m2_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str7, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @C.m5(%C* %self) { 
C.m5_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str8, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @C.m6(%C* %self) { 
C.m6_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str9, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @C.m7(%C* %self) { 
C.m7_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str10, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @C.m8(%C* %self) { 
C.m8_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str11, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class D methods ---
define void @D.constructor(%D* %this) {
	%this.class.vtable = bitcast [9 x void (...)*]* @D.vtable to void (...)**
	%this.vtable = getelementptr %D, %D* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @D.m1(%D* %self) { 
D.m1_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str12, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m4(%D* %self) { 
D.m4_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str13, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m6(%D* %self) { 
D.m6_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str14, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m7(%D* %self) { 
D.m7_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str15, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m9(%D* %self) { 
D.m9_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str16, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @printC(%C* %c) { 
printC_entry:
	%tmp. = getelementptr %C, %C* %c, i32 0, i32 0
	%tmp..1 = load void (...)**, void (...)*** %tmp.
	%tmp..2 = getelementptr void (...)*, void (...)** %tmp..1, i32 0
	%tmp..3 = bitcast void (...)** %tmp..2 to void (%C*)**
	%tmp..4 = load void (%C*)*, void (%C*)** %tmp..3
	call void %tmp..4(%C* %c)
	%tmp..7 = load void (...)**, void (...)*** %tmp.
	%tmp..8 = getelementptr void (...)*, void (...)** %tmp..7, i32 1
	%tmp..9 = bitcast void (...)** %tmp..8 to void (%C*)**
	%tmp..10 = load void (%C*)*, void (%C*)** %tmp..9
	call void %tmp..10(%C* %c)
	%tmp..13 = load void (...)**, void (...)*** %tmp.
	%tmp..14 = getelementptr void (...)*, void (...)** %tmp..13, i32 2
	%tmp..15 = bitcast void (...)** %tmp..14 to void (%A*)**
	%tmp..16 = load void (%A*)*, void (%A*)** %tmp..15
	%tmp..17 = bitcast %C* %c to %A*
	call void %tmp..16(%A* %tmp..17)
	%tmp..20 = load void (...)**, void (...)*** %tmp.
	%tmp..21 = getelementptr void (...)*, void (...)** %tmp..20, i32 3
	%tmp..22 = bitcast void (...)** %tmp..21 to void (%B*)**
	%tmp..23 = load void (%B*)*, void (%B*)** %tmp..22
	%tmp..24 = bitcast %C* %c to %B*
	call void %tmp..23(%B* %tmp..24)
	%tmp..27 = load void (...)**, void (...)*** %tmp.
	%tmp..28 = getelementptr void (...)*, void (...)** %tmp..27, i32 4
	%tmp..29 = bitcast void (...)** %tmp..28 to void (%C*)**
	%tmp..30 = load void (%C*)*, void (%C*)** %tmp..29
	call void %tmp..30(%C* %c)
	%tmp..33 = load void (...)**, void (...)*** %tmp.
	%tmp..34 = getelementptr void (...)*, void (...)** %tmp..33, i32 5
	%tmp..35 = bitcast void (...)** %tmp..34 to void (%C*)**
	%tmp..36 = load void (%C*)*, void (%C*)** %tmp..35
	call void %tmp..36(%C* %c)
	%tmp..39 = load void (...)**, void (...)*** %tmp.
	%tmp..40 = getelementptr void (...)*, void (...)** %tmp..39, i32 6
	%tmp..41 = bitcast void (...)** %tmp..40 to void (%C*)**
	%tmp..42 = load void (%C*)*, void (%C*)** %tmp..41
	call void %tmp..42(%C* %c)
	%tmp..45 = load void (...)**, void (...)*** %tmp.
	%tmp..46 = getelementptr void (...)*, void (...)** %tmp..45, i32 7
	%tmp..47 = bitcast void (...)** %tmp..46 to void (%C*)**
	%tmp..48 = load void (%C*)*, void (%C*)** %tmp..47
	call void %tmp..48(%C* %c)
	ret void
}

define void @printD(%D* %d) { 
printD_entry:
	%tmp. = getelementptr %D, %D* %d, i32 0, i32 0
	%tmp..1 = load void (...)**, void (...)*** %tmp.
	%tmp..2 = getelementptr void (...)*, void (...)** %tmp..1, i32 0
	%tmp..3 = bitcast void (...)** %tmp..2 to void (%D*)**
	%tmp..4 = load void (%D*)*, void (%D*)** %tmp..3
	call void %tmp..4(%D* %d)
	%tmp..7 = load void (...)**, void (...)*** %tmp.
	%tmp..8 = getelementptr void (...)*, void (...)** %tmp..7, i32 1
	%tmp..9 = bitcast void (...)** %tmp..8 to void (%C*)**
	%tmp..10 = load void (%C*)*, void (%C*)** %tmp..9
	%tmp..11 = bitcast %D* %d to %C*
	call void %tmp..10(%C* %tmp..11)
	%tmp..14 = load void (...)**, void (...)*** %tmp.
	%tmp..15 = getelementptr void (...)*, void (...)** %tmp..14, i32 2
	%tmp..16 = bitcast void (...)** %tmp..15 to void (%A*)**
	%tmp..17 = load void (%A*)*, void (%A*)** %tmp..16
	%tmp..18 = bitcast %D* %d to %A*
	call void %tmp..17(%A* %tmp..18)
	%tmp..21 = load void (...)**, void (...)*** %tmp.
	%tmp..22 = getelementptr void (...)*, void (...)** %tmp..21, i32 3
	%tmp..23 = bitcast void (...)** %tmp..22 to void (%D*)**
	%tmp..24 = load void (%D*)*, void (%D*)** %tmp..23
	call void %tmp..24(%D* %d)
	%tmp..27 = load void (...)**, void (...)*** %tmp.
	%tmp..28 = getelementptr void (...)*, void (...)** %tmp..27, i32 4
	%tmp..29 = bitcast void (...)** %tmp..28 to void (%C*)**
	%tmp..30 = load void (%C*)*, void (%C*)** %tmp..29
	%tmp..31 = bitcast %D* %d to %C*
	call void %tmp..30(%C* %tmp..31)
	%tmp..34 = load void (...)**, void (...)*** %tmp.
	%tmp..35 = getelementptr void (...)*, void (...)** %tmp..34, i32 5
	%tmp..36 = bitcast void (...)** %tmp..35 to void (%D*)**
	%tmp..37 = load void (%D*)*, void (%D*)** %tmp..36
	call void %tmp..37(%D* %d)
	%tmp..40 = load void (...)**, void (...)*** %tmp.
	%tmp..41 = getelementptr void (...)*, void (...)** %tmp..40, i32 6
	%tmp..42 = bitcast void (...)** %tmp..41 to void (%D*)**
	%tmp..43 = load void (%D*)*, void (%D*)** %tmp..42
	call void %tmp..43(%D* %d)
	%tmp..46 = load void (...)**, void (...)*** %tmp.
	%tmp..47 = getelementptr void (...)*, void (...)** %tmp..46, i32 7
	%tmp..48 = bitcast void (...)** %tmp..47 to void (%C*)**
	%tmp..49 = load void (%C*)*, void (%C*)** %tmp..48
	%tmp..50 = bitcast %D* %d to %C*
	call void %tmp..49(%C* %tmp..50)
	%tmp..53 = load void (...)**, void (...)*** %tmp.
	%tmp..54 = getelementptr void (...)*, void (...)** %tmp..53, i32 8
	%tmp..55 = bitcast void (...)** %tmp..54 to void (%D*)**
	%tmp..56 = load void (%D*)*, void (%D*)** %tmp..55
	call void %tmp..56(%D* %d)
	ret void
}

define void @printA(%A* %a) { 
printA_entry:
	%tmp. = getelementptr %A, %A* %a, i32 0, i32 0
	%tmp..1 = load void (...)**, void (...)*** %tmp.
	%tmp..2 = getelementptr void (...)*, void (...)** %tmp..1, i32 0
	%tmp..3 = bitcast void (...)** %tmp..2 to void (%A*)**
	%tmp..4 = load void (%A*)*, void (%A*)** %tmp..3
	call void %tmp..4(%A* %a)
	%tmp..7 = load void (...)**, void (...)*** %tmp.
	%tmp..8 = getelementptr void (...)*, void (...)** %tmp..7, i32 1
	%tmp..9 = bitcast void (...)** %tmp..8 to void (%A*)**
	%tmp..10 = load void (%A*)*, void (%A*)** %tmp..9
	call void %tmp..10(%A* %a)
	%tmp..13 = load void (...)**, void (...)*** %tmp.
	%tmp..14 = getelementptr void (...)*, void (...)** %tmp..13, i32 2
	%tmp..15 = bitcast void (...)** %tmp..14 to void (%A*)**
	%tmp..16 = load void (%A*)*, void (%A*)** %tmp..15
	call void %tmp..16(%A* %a)
	ret void
}

define void @printB(%B* %b) { 
printB_entry:
	%tmp. = getelementptr %B, %B* %b, i32 0, i32 0
	%tmp..1 = load void (...)**, void (...)*** %tmp.
	%tmp..2 = getelementptr void (...)*, void (...)** %tmp..1, i32 0
	%tmp..3 = bitcast void (...)** %tmp..2 to void (%A*)**
	%tmp..4 = load void (%A*)*, void (%A*)** %tmp..3
	%tmp..5 = bitcast %B* %b to %A*
	call void %tmp..4(%A* %tmp..5)
	%tmp..8 = load void (...)**, void (...)*** %tmp.
	%tmp..9 = getelementptr void (...)*, void (...)** %tmp..8, i32 1
	%tmp..10 = bitcast void (...)** %tmp..9 to void (%B*)**
	%tmp..11 = load void (%B*)*, void (%B*)** %tmp..10
	call void %tmp..11(%B* %b)
	%tmp..14 = load void (...)**, void (...)*** %tmp.
	%tmp..15 = getelementptr void (...)*, void (...)** %tmp..14, i32 2
	%tmp..16 = bitcast void (...)** %tmp..15 to void (%A*)**
	%tmp..17 = load void (%A*)*, void (%A*)** %tmp..16
	%tmp..18 = bitcast %B* %b to %A*
	call void %tmp..17(%A* %tmp..18)
	%tmp..21 = load void (...)**, void (...)*** %tmp.
	%tmp..22 = getelementptr void (...)*, void (...)** %tmp..21, i32 3
	%tmp..23 = bitcast void (...)** %tmp..22 to void (%B*)**
	%tmp..24 = load void (%B*)*, void (%B*)** %tmp..23
	call void %tmp..24(%B* %b)
	%tmp..27 = load void (...)**, void (...)*** %tmp.
	%tmp..28 = getelementptr void (...)*, void (...)** %tmp..27, i32 4
	%tmp..29 = bitcast void (...)** %tmp..28 to void (%B*)**
	%tmp..30 = load void (%B*)*, void (%B*)** %tmp..29
	call void %tmp..30(%B* %b)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %A*
	call void @A.constructor(%A* %tmp..1)
	%tmp..3 = getelementptr [7 x i8], [7 x i8]* @.str.str17, i32 0, i32 0
	call void @printString(i8* %tmp..3)
	call void @printA(%A* %tmp..1)
	%tmp..6 = call i8* @malloc(i32 0)
	%tmp..7 = bitcast i8* %tmp..6 to %B*
	call void @B.constructor(%B* %tmp..7)
	%tmp..9 = getelementptr [7 x i8], [7 x i8]* @.str.str18, i32 0, i32 0
	call void @printString(i8* %tmp..9)
	%tmp..11 = bitcast %B* %tmp..7 to %A*
	call void @printA(%A* %tmp..11)
	%tmp..13 = getelementptr [7 x i8], [7 x i8]* @.str.str19, i32 0, i32 0
	call void @printString(i8* %tmp..13)
	call void @printB(%B* %tmp..7)
	%tmp..16 = call i8* @malloc(i32 0)
	%tmp..17 = bitcast i8* %tmp..16 to %C*
	call void @C.constructor(%C* %tmp..17)
	%tmp..19 = getelementptr [7 x i8], [7 x i8]* @.str.str20, i32 0, i32 0
	call void @printString(i8* %tmp..19)
	%tmp..21 = bitcast %C* %tmp..17 to %A*
	call void @printA(%A* %tmp..21)
	%tmp..23 = getelementptr [7 x i8], [7 x i8]* @.str.str21, i32 0, i32 0
	call void @printString(i8* %tmp..23)
	%tmp..25 = bitcast %C* %tmp..17 to %B*
	call void @printB(%B* %tmp..25)
	%tmp..27 = getelementptr [7 x i8], [7 x i8]* @.str.str22, i32 0, i32 0
	call void @printString(i8* %tmp..27)
	call void @printC(%C* %tmp..17)
	%tmp..30 = call i8* @malloc(i32 0)
	%tmp..31 = bitcast i8* %tmp..30 to %D*
	call void @D.constructor(%D* %tmp..31)
	%tmp..33 = getelementptr [7 x i8], [7 x i8]* @.str.str23, i32 0, i32 0
	call void @printString(i8* %tmp..33)
	%tmp..35 = bitcast %D* %tmp..31 to %A*
	call void @printA(%A* %tmp..35)
	%tmp..37 = getelementptr [7 x i8], [7 x i8]* @.str.str24, i32 0, i32 0
	call void @printString(i8* %tmp..37)
	%tmp..39 = bitcast %D* %tmp..31 to %B*
	call void @printB(%B* %tmp..39)
	%tmp..41 = getelementptr [7 x i8], [7 x i8]* @.str.str25, i32 0, i32 0
	call void @printString(i8* %tmp..41)
	%tmp..43 = bitcast %D* %tmp..31 to %C*
	call void @printC(%C* %tmp..43)
	%tmp..45 = getelementptr [7 x i8], [7 x i8]* @.str.str26, i32 0, i32 0
	call void @printString(i8* %tmp..45)
	call void @printD(%D* %tmp..31)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

declare i8* @malloc(i32)
