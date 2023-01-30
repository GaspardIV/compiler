@.str.str0 = private unnamed_addr constant [12 x i8] c"bla lab abl\00", align 1 ; --- Class G ---
@G.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%G*, i8*)* @G.foo to void (...)*) ; foo 
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

define void @G.foo(%G* %self, i8* %s) { 
G.foo_entry:
	call void @printString(i8* %s)
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
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%G*, i8*)**
	%tmp..7 = load void (%G*, i8*)*, void (%G*, i8*)** %tmp..6
	%tmp..8 = getelementptr [12 x i8], [12 x i8]* @.str.str0, i32 0, i32 0
	call void %tmp..7(%G* %tmp..1, i8* %tmp..8)
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
