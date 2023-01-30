@.str.str0 = private unnamed_addr constant [2 x i8] c"A\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"B\00", align 1@.str.str2 = private unnamed_addr constant [2 x i8] c"C\00", align 1@.str.str3 = private unnamed_addr constant [2 x i8] c"D\00", align 1@.str.str5 = private unnamed_addr constant [4 x i8] c"nie\00", align 1@.str.str4 = private unnamed_addr constant [4 x i8] c"tak\00", align 1 ; --- Class A ---
@A.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%A*)* @A.print to void (...)*) ; print 
]

%A = type { 
	void (...)**; vtable
	}
 ; --- Class B ---
@B.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%B*)* @B.print to void (...)*) ; print 
]

%B = type { 
	void (...)**; vtable
	}
 ; --- Class C ---
@C.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%C*)* @C.print to void (...)*) ; print 
]

%C = type { 
	void (...)**; vtable
	}
 ; --- Class D ---
@D.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%D*)* @D.print to void (...)*) ; print 
]

%D = type { 
	void (...)**; vtable
	}
 ; --- Class A methods ---
define void @A.constructor(%A* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @A.vtable to void (...)**
	%this.vtable = getelementptr %A, %A* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @A.print(%A* %self) { 
A.print_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class B methods ---
define void @B.constructor(%B* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @B.vtable to void (...)**
	%this.vtable = getelementptr %B, %B* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @B.print(%B* %self) { 
B.print_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class C methods ---
define void @C.constructor(%C* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @C.vtable to void (...)**
	%this.vtable = getelementptr %C, %C* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @C.print(%C* %self) { 
C.print_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class D methods ---
define void @D.constructor(%D* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @D.vtable to void (...)**
	%this.vtable = getelementptr %D, %D* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
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
	%tmp. = bitcast %B* %param to %A*
	ret %A* %tmp.
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %B*
	call void @B.constructor(%B* %tmp..1)
	%tmp..3 = getelementptr %B, %B* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%B*)**
	%tmp..7 = load void (%B*)*, void (%B*)** %tmp..6
	call void %tmp..7(%B* %tmp..1)
	%tmp..9 = call %B* @fun()
	%tmp..10 = getelementptr %B, %B* %tmp..9, i32 0, i32 0
	%tmp..11 = load void (...)**, void (...)*** %tmp..10
	%tmp..12 = getelementptr void (...)*, void (...)** %tmp..11, i32 0
	%tmp..13 = bitcast void (...)** %tmp..12 to void (%B*)**
	%tmp..14 = load void (%B*)*, void (%B*)** %tmp..13
	call void %tmp..14(%B* %tmp..9)
	%tmp..16 = call i8* @malloc(i32 0)
	%tmp..17 = bitcast i8* %tmp..16 to %B*
	call void @B.constructor(%B* %tmp..17)
	%tmp..19 = getelementptr %B, %B* %tmp..17, i32 0, i32 0
	%tmp..20 = load void (...)**, void (...)*** %tmp..19
	%tmp..21 = getelementptr void (...)*, void (...)** %tmp..20, i32 0
	%tmp..22 = bitcast void (...)** %tmp..21 to void (%B*)**
	%tmp..23 = load void (%B*)*, void (%B*)** %tmp..22
	call void %tmp..23(%B* %tmp..17)
	%tmp..25 = call i8* @malloc(i32 0)
	%tmp..26 = bitcast i8* %tmp..25 to %C*
	call void @C.constructor(%C* %tmp..26)
	%tmp..28 = bitcast %C* %tmp..26 to %B*
	%tmp..29 = call %A* @fun2(%B* %tmp..28)
	%tmp..30 = getelementptr %A, %A* %tmp..29, i32 0, i32 0
	%tmp..31 = load void (...)**, void (...)*** %tmp..30
	%tmp..32 = getelementptr void (...)*, void (...)** %tmp..31, i32 0
	%tmp..33 = bitcast void (...)** %tmp..32 to void (%A*)**
	%tmp..34 = load void (%A*)*, void (%A*)** %tmp..33
	call void %tmp..34(%A* %tmp..29)
	%tmp..38 = mul i32 3, 8
	%tmp..39 = add i32 %tmp..38, 4
	%tmp..40 = call i8* @calloc(i32 1, i32 %tmp..39)
	%tmp..41 = bitcast i8* %tmp..40 to i32*
	store i32 3, i32* %tmp..41
	%tmp..42 = getelementptr i8, i8* %tmp..40, i32 4
	%tmp..43 = bitcast i8* %tmp..42 to %C**
	%tmp..45 = getelementptr %C*, %C** %tmp..43, i32 0
	%tmp..46 = call i8* @malloc(i32 0)
	%tmp..47 = bitcast i8* %tmp..46 to %C*
	call void @C.constructor(%C* %tmp..47)
	store %C* %tmp..47, %C** %tmp..45
	%tmp..50 = getelementptr %C*, %C** %tmp..43, i32 1
	%tmp..51 = call i8* @malloc(i32 0)
	%tmp..52 = bitcast i8* %tmp..51 to %D*
	call void @D.constructor(%D* %tmp..52)
	%tmp..54 = bitcast %D* %tmp..52 to %C*
	store %C* %tmp..54, %C** %tmp..50
	%tmp..56 = getelementptr %C*, %C** %tmp..43, i32 2
	%tmp..57 = call i8* @malloc(i32 0)
	%tmp..58 = bitcast i8* %tmp..57 to %D*
	call void @D.constructor(%D* %tmp..58)
	%tmp..60 = bitcast %D* %tmp..58 to %C*
	store %C* %tmp..60, %C** %tmp..56
	%tmp..62 = bitcast %C** %tmp..43 to i8*
	%tmp..61 = getelementptr i8, i8* %tmp..62, i32 -4
	%tmp..63 = bitcast i8* %tmp..61 to i32*
	%tmp..64 = load i32, i32* %tmp..63
	br label %main.1_for.cond
main.1_for.cond:
	%i.d.x..1 = phi i32 [0, %main_entry], [%tmp..69, %main.2_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..64
	br i1 %ifres., label %main.2_for.body, label %main.3_for.end
main.2_for.body:
	%tmp..66 = getelementptr %C*, %C** %tmp..43, i32 %i.d.x..1
	%tmp..67 = load %C*, %C** %tmp..66
	%tmp..69 = add i32 %i.d.x..1, 1
	%tmp..70 = bitcast %C* %tmp..67 to %B*
	%tmp..71 = call %A* @fun2(%B* %tmp..70)
	%tmp..72 = getelementptr %A, %A* %tmp..71, i32 0, i32 0
	%tmp..73 = load void (...)**, void (...)*** %tmp..72
	%tmp..74 = getelementptr void (...)*, void (...)** %tmp..73, i32 0
	%tmp..75 = bitcast void (...)** %tmp..74 to void (%A*)**
	%tmp..76 = load void (%A*)*, void (%A*)** %tmp..75
	call void %tmp..76(%A* %tmp..71)
	br label %main.1_for.cond
main.3_for.end:
	%tmp..78 = call i8* @malloc(i32 0)
	%tmp..79 = bitcast i8* %tmp..78 to %B*
	call void @B.constructor(%B* %tmp..79)
	%tmp..82 = icmp eq %B* %tmp..79, %tmp..79
	br i1 %tmp..82, label %main.4_if.true, label %main.6_if.end
main.4_if.true:
	%tmp..83 = getelementptr [4 x i8], [4 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp..83)
	br label %main.6_if.end
main.6_if.end:
	%tmp..86 = icmp ne %B* %tmp..79, %tmp..79
	br i1 %tmp..86, label %main.7_if.true, label %main.9_if.end
main.7_if.true:
	%tmp..87 = getelementptr [4 x i8], [4 x i8]* @.str.str5, i32 0, i32 0
	call void @printString(i8* %tmp..87)
	br label %main.9_if.end
main.9_if.end:
	ret i32 0
}

define %B* @fun() { 
fun_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %C*
	call void @C.constructor(%C* %tmp..1)
	%tmp..3 = bitcast %C* %tmp..1 to %B*
	ret %B* %tmp..3
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
