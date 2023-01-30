 ; --- Class G ---
@G.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%G*, i32*)* @G.foo to void (...)*) ; foo 
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

define void @G.foo(%G* %self, i32* %a) { 
G.foo_entry:
	%tmp..1 = getelementptr i32, i32* %a, i32 0
	%tmp..2 = load i32, i32* %tmp..1
	call void @printInt(i32 %tmp..2)
	%tmp..5 = bitcast i32* %a to i8*
	%tmp..4 = getelementptr i8, i8* %tmp..5, i32 -4
	%tmp..6 = bitcast i8* %tmp..4 to i32*
	%tmp..7 = load i32, i32* %tmp..6
	call void @printInt(i32 %tmp..7)
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
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%G*, i32*)**
	%tmp..7 = load void (%G*, i32*)*, void (%G*, i32*)** %tmp..6
	%tmp..9 = mul i32 42, 4
	%tmp..10 = add i32 %tmp..9, 4
	%tmp..11 = call i8* @calloc(i32 1, i32 %tmp..10)
	%tmp..12 = bitcast i8* %tmp..11 to i32*
	store i32 42, i32* %tmp..12
	%tmp..13 = getelementptr i8, i8* %tmp..11, i32 4
	%tmp..14 = bitcast i8* %tmp..13 to i32*
	call void %tmp..7(%G* %tmp..1, i32* %tmp..14)
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

declare i8* @calloc(i32, i32)
declare i8* @malloc(i32)
