 ; --- Class X ---
@X.vtable = global [0 x void (...)*] [
]

%X = type { 
	void (...)**; vtable
	}
 ; --- Class Y ---
@Y.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%Y*)* @Y.foo to void (...)*) ; foo 
]

%Y = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}
 ; --- Class Y methods ---
define void @Y.constructor(%Y* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @Y.vtable to void (...)**
	%this.vtable = getelementptr %Y, %Y* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %Y, %Y* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define void @Y.foo(%Y* %self) { 
Y.foo_entry:
	%tmp. = getelementptr %Y, %Y* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	call void @printInt(i32 %tmp..1)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %Y*
	call void @Y.constructor(%Y* %tmp..1)
	%tmp..3 = getelementptr %Y, %Y* %tmp..1, i32 0, i32 1
	store i32 142, i32* %tmp..3
	%tmp..5 = getelementptr %Y, %Y* %tmp..1, i32 0, i32 0
	%tmp..6 = load void (...)**, void (...)*** %tmp..5
	%tmp..7 = getelementptr void (...)*, void (...)** %tmp..6, i32 0
	%tmp..8 = bitcast void (...)** %tmp..7 to void (%Y*)**
	%tmp..9 = load void (%Y*)*, void (%Y*)** %tmp..8
	call void %tmp..9(%Y* %tmp..1)
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

declare i8* @malloc(i32)
