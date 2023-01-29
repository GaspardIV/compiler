@.str.str4 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str0 = private unnamed_addr constant [2 x i8] c"A\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"B\00", align 1@.str.str2 = private unnamed_addr constant [2 x i8] c"C\00", align 1@.str.str3 = private unnamed_addr constant [2 x i8] c"D\00", align 1@.str.str6 = private unnamed_addr constant [4 x i8] c"nie\00", align 1@.str.str5 = private unnamed_addr constant [4 x i8] c"tak\00", align 1 ; --- Class A ---
@A.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%A*)* @A.print to void (...)*) ; print 
]

%A = type { 
	void (...)**}
define void @A.constructor(%A* %this) {
	%this.vtable = bitcast [1 x void (...)*]* @A.vtable to void (...)**
	ret void
}

define void @A.print(%A* %self) { 
A.print_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class B ---
@B.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%B*)* @B.print to void (...)*) ; print 
]

%B = type { 
	void (...)**}
define void @B.constructor(%B* %this) {
	%this.vtable = bitcast [1 x void (...)*]* @B.vtable to void (...)**
	ret void
}

define void @B.print(%B* %self) { 
B.print_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class C ---
@C.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%C*)* @C.print to void (...)*) ; print 
]

%C = type { 
	void (...)**}
define void @C.constructor(%C* %this) {
	%this.vtable = bitcast [1 x void (...)*]* @C.vtable to void (...)**
	ret void
}

define void @C.print(%C* %self) { 
C.print_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class D ---
@D.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%D*)* @D.print to void (...)*) ; print 
]

%D = type { 
	void (...)**}
define void @D.constructor(%D* %this) {
	%this.vtable = bitcast [1 x void (...)*]* @D.vtable to void (...)**
	ret void
}

define void @D.print(%D* %self) { 
D.print_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define %A* @fun2(%B* %param) { 
fun2_entry:
	ret %B* %param
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %B*
	call void @B.constructor(%B* %tmp..1)
	call void @B.print(%B* %tmp..1)
	%tmp..4 = call %B* @fun()
	call void @B.print(%B* %tmp..4)
	%tmp..6 = call i8* @malloc(i32 0)
	%tmp..7 = bitcast i8* %tmp..6 to %B*
	call void @B.constructor(%B* %tmp..7)
	call void @B.print(%B* %tmp..7)
	%tmp..10 = call i8* @malloc(i32 0)
	%tmp..11 = bitcast i8* %tmp..10 to %C*
	call void @C.constructor(%C* %tmp..11)
	%tmp..13 = call %A* @fun2(%C* %tmp..11)
	call void @A.print(%A* %tmp..13)
	%tmp..17 = mul i32 3, 8
	%tmp..18 = add i32 %tmp..17, 4
	%tmp..19 = call i8* @calloc(i32 1, i32 %tmp..18)
	%tmp..20 = bitcast i8* %tmp..19 to i32*
	store i32 3, i32* %tmp..20
	%tmp..21 = getelementptr i8, i8* %tmp..19, i32 4
	%tmp..22 = bitcast i8* %tmp..21 to %C**
	%tmp..24 = getelementptr %C*, %C** %tmp..22, i32 0
	%tmp..25 = call i8* @malloc(i32 0)
	%tmp..26 = bitcast i8* %tmp..25 to %C*
	call void @C.constructor(%C* %tmp..26)
	store %C* %tmp..26, %C** %tmp..24
	%tmp..29 = getelementptr %C*, %C** %tmp..22, i32 1
	%tmp..30 = call i8* @malloc(i32 0)
	%tmp..31 = bitcast i8* %tmp..30 to %D*
	call void @D.constructor(%D* %tmp..31)
	store %D* %tmp..31, %D** %tmp..29
	%tmp..34 = getelementptr %C*, %C** %tmp..22, i32 2
	%tmp..35 = call i8* @malloc(i32 0)
	%tmp..36 = bitcast i8* %tmp..35 to %D*
	call void @D.constructor(%D* %tmp..36)
	store %D* %tmp..36, %D** %tmp..34
	%tmp..39 = bitcast %C** %tmp..22 to i8*
	%tmp..38 = getelementptr i8, i8* %tmp..39, i32 -4
	%tmp..40 = bitcast i8* %tmp..38 to i32*
	%tmp..41 = load i32, i32* %tmp..40
	br label %main.1_for.cond
main.1_for.cond:
	%i.d.x..1 = phi i32 [0, %main_entry], [%tmp..46, %main.2_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..41
	br i1 %ifres., label %main.2_for.body, label %main.3_for.end
main.2_for.body:
	%tmp..43 = getelementptr %C*, %C** %tmp..22, i32 %i.d.x..1
	%tmp..44 = load %C*, %C** %tmp..43
	%tmp..46 = add i32 %i.d.x..1, 1
	%tmp..47 = call %A* @fun2(%C* %tmp..44)
	call void @A.print(%A* %tmp..47)
	br label %main.1_for.cond
main.3_for.end:
	%tmp..49 = call i8* @malloc(i32 0)
	%tmp..50 = bitcast i8* %tmp..49 to %B*
	call void @B.constructor(%B* %tmp..50)
	%tmp..53 = icmp eq %B* %tmp..50, %tmp..50
	br i1 %tmp..53, label %main.4_if.true, label %main.6_if.end
main.4_if.true:
	%tmp..54 = getelementptr [4 x i8], [4 x i8]* @.str.str5, i32 0, i32 0
	call void @printString(i8* %tmp..54)
	br label %main.6_if.end
main.6_if.end:
	%tmp..57 = icmp ne %B* %tmp..50, %tmp..50
	br i1 %tmp..57, label %main.7_if.true, label %main.9_if.end
main.7_if.true:
	%tmp..58 = getelementptr [4 x i8], [4 x i8]* @.str.str6, i32 0, i32 0
	call void @printString(i8* %tmp..58)
	br label %main.9_if.end
main.9_if.end:
	ret i32 0
}

define %B* @fun() { 
fun_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %C*
	call void @C.constructor(%C* %tmp..1)
	ret %C* %tmp..1
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

declare i8* @calloc(i32, i32)
declare i8* @malloc(i32)
