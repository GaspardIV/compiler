@.str.str0 = private unnamed_addr constant [5 x i8] c"true\00", align 1@.str.str1 = private unnamed_addr constant [6 x i8] c"false\00", align 1 ; --- Class G ---
@G.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%G*, i1)* @G.foo to void (...)*) ; foo 
]

%G = type { 
	void (...)**; vtable
	}
 ; --- Class G methods ---
define void @G.constructor(%G* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @G.vtable to void (...)**
	%this.vtable = getelementptr %G, %G* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @G.foo(%G* %self, i1 %b) { 
G.foo_entry:
	br i1 %b, label %G.foo.1_if.true, label %G.foo.2_if.false
G.foo.1_if.true:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %G.foo.3_if.end
G.foo.2_if.false:
	%tmp..2 = getelementptr [6 x i8], [6 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	br label %G.foo.3_if.end
G.foo.3_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %G*
	call void @G.constructor(%G* %tmp..1)
	%tmp..3 = getelementptr %G, %G* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%G*, i1)**
	%tmp..7 = load void (%G*, i1)*, void (%G*, i1)** %tmp..6
	call void %tmp..7(%G* %tmp..1, i1 true)
	%tmp..10 = call i8* @malloc(i32 0)
	%tmp..11 = bitcast i8* %tmp..10 to %G*
	call void @G.constructor(%G* %tmp..11)
	%tmp..13 = getelementptr %G, %G* %tmp..11, i32 0, i32 0
	%tmp..14 = load void (...)**, void (...)*** %tmp..13
	%tmp..15 = getelementptr void (...)*, void (...)** %tmp..14, i32 0
	%tmp..16 = bitcast void (...)** %tmp..15 to void (%G*, i1)**
	%tmp..17 = load void (%G*, i1)*, void (%G*, i1)** %tmp..16
	call void %tmp..17(%G* %tmp..11, i1 false)
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
