@.str.str2 = private unnamed_addr constant [6 x i8] c"A::m3\00", align 1@.str.str26 = private unnamed_addr constant [7 x i8] c"D as D\00", align 1@.str.str1 = private unnamed_addr constant [6 x i8] c"A::m2\00", align 1@.str.str17 = private unnamed_addr constant [7 x i8] c"A as A\00", align 1@.str.str0 = private unnamed_addr constant [6 x i8] c"A::m1\00", align 1@.str.str24 = private unnamed_addr constant [7 x i8] c"D as B\00", align 1@.str.str25 = private unnamed_addr constant [7 x i8] c"D as C\00", align 1@.str.str4 = private unnamed_addr constant [6 x i8] c"B::m4\00", align 1@.str.str21 = private unnamed_addr constant [7 x i8] c"C as B\00", align 1@.str.str20 = private unnamed_addr constant [7 x i8] c"C as A\00", align 1@.str.str23 = private unnamed_addr constant [7 x i8] c"D as A\00", align 1@.str.str5 = private unnamed_addr constant [6 x i8] c"B::m5\00", align 1@.str.str22 = private unnamed_addr constant [7 x i8] c"C as C\00", align 1@.str.str3 = private unnamed_addr constant [6 x i8] c"B::m2\00", align 1@.str.str8 = private unnamed_addr constant [6 x i8] c"C::m5\00", align 1@.str.str18 = private unnamed_addr constant [7 x i8] c"B as A\00", align 1@.str.str19 = private unnamed_addr constant [7 x i8] c"B as B\00", align 1@.str.str7 = private unnamed_addr constant [6 x i8] c"C::m2\00", align 1@.str.str11 = private unnamed_addr constant [6 x i8] c"C::m8\00", align 1@.str.str10 = private unnamed_addr constant [6 x i8] c"C::m7\00", align 1@.str.str9 = private unnamed_addr constant [6 x i8] c"C::m6\00", align 1@.str.str16 = private unnamed_addr constant [6 x i8] c"D::m9\00", align 1@.str.str6 = private unnamed_addr constant [6 x i8] c"C::m1\00", align 1@.str.str12 = private unnamed_addr constant [6 x i8] c"D::m1\00", align 1@.str.str13 = private unnamed_addr constant [6 x i8] c"D::m4\00", align 1@.str.str14 = private unnamed_addr constant [6 x i8] c"D::m6\00", align 1@.str.str15 = private unnamed_addr constant [6 x i8] c"D::m7\00", align 1 ; --- Class A ---
%A = type { 
}
define void @A.constructor(%A* %this) {
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
 ; --- Class B ---
%B = type { 
}
define void @B.constructor(%B* %this) {
	ret void
}

define void @B.m1(%B* %self) { 
B.m1_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @B.m2(%B* %self) { 
B.m2_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @B.m3(%B* %self) { 
B.m3_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str2, i32 0, i32 0
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
 ; --- Class C ---
%C = type { 
}
define void @C.constructor(%C* %this) {
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

define void @C.m3(%C* %self) { 
C.m3_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @C.m4(%C* %self) { 
C.m4_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str4, i32 0, i32 0
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
 ; --- Class D ---
%D = type { 
}
define void @D.constructor(%D* %this) {
	ret void
}

define void @D.m1(%D* %self) { 
D.m1_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str12, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m2(%D* %self) { 
D.m2_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str7, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m3(%D* %self) { 
D.m3_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m4(%D* %self) { 
D.m4_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str13, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @D.m5(%D* %self) { 
D.m5_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str8, i32 0, i32 0
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

define void @D.m8(%D* %self) { 
D.m8_entry:
	%tmp. = getelementptr [6 x i8], [6 x i8]* @.str.str11, i32 0, i32 0
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
	call void @C.m1(%C* %c)
	call void @C.m2(%C* %c)
	call void @C.m3(%C* %c)
	call void @C.m4(%C* %c)
	call void @C.m5(%C* %c)
	call void @C.m6(%C* %c)
	call void @C.m7(%C* %c)
	call void @C.m8(%C* %c)
	ret void
}

define void @printD(%D* %d) { 
printD_entry:
	call void @D.m1(%D* %d)
	call void @D.m2(%D* %d)
	call void @D.m3(%D* %d)
	call void @D.m4(%D* %d)
	call void @D.m5(%D* %d)
	call void @D.m6(%D* %d)
	call void @D.m7(%D* %d)
	call void @D.m8(%D* %d)
	call void @D.m9(%D* %d)
	ret void
}

define void @printA(%A* %a) { 
printA_entry:
	call void @A.m1(%A* %a)
	call void @A.m2(%A* %a)
	call void @A.m3(%A* %a)
	ret void
}

define void @printB(%B* %b) { 
printB_entry:
	call void @B.m1(%B* %b)
	call void @B.m2(%B* %b)
	call void @B.m3(%B* %b)
	call void @B.m4(%B* %b)
	call void @B.m5(%B* %b)
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
	call void @printA(%B* %tmp..7)
	%tmp..12 = getelementptr [7 x i8], [7 x i8]* @.str.str19, i32 0, i32 0
	call void @printString(i8* %tmp..12)
	call void @printB(%B* %tmp..7)
	%tmp..15 = call i8* @malloc(i32 0)
	%tmp..16 = bitcast i8* %tmp..15 to %C*
	call void @C.constructor(%C* %tmp..16)
	%tmp..18 = getelementptr [7 x i8], [7 x i8]* @.str.str20, i32 0, i32 0
	call void @printString(i8* %tmp..18)
	call void @printA(%C* %tmp..16)
	%tmp..21 = getelementptr [7 x i8], [7 x i8]* @.str.str21, i32 0, i32 0
	call void @printString(i8* %tmp..21)
	call void @printB(%C* %tmp..16)
	%tmp..24 = getelementptr [7 x i8], [7 x i8]* @.str.str22, i32 0, i32 0
	call void @printString(i8* %tmp..24)
	call void @printC(%C* %tmp..16)
	%tmp..27 = call i8* @malloc(i32 0)
	%tmp..28 = bitcast i8* %tmp..27 to %D*
	call void @D.constructor(%D* %tmp..28)
	%tmp..30 = getelementptr [7 x i8], [7 x i8]* @.str.str23, i32 0, i32 0
	call void @printString(i8* %tmp..30)
	call void @printA(%D* %tmp..28)
	%tmp..33 = getelementptr [7 x i8], [7 x i8]* @.str.str24, i32 0, i32 0
	call void @printString(i8* %tmp..33)
	call void @printB(%D* %tmp..28)
	%tmp..36 = getelementptr [7 x i8], [7 x i8]* @.str.str25, i32 0, i32 0
	call void @printString(i8* %tmp..36)
	call void @printC(%D* %tmp..28)
	%tmp..39 = getelementptr [7 x i8], [7 x i8]* @.str.str26, i32 0, i32 0
	call void @printString(i8* %tmp..39)
	call void @printD(%D* %tmp..28)
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
