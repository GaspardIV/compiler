@.str.str0 = private unnamed_addr constant [4 x i8] c"foo\00", align 1 ; --- Class X ---
@X.vtable = global [1 x void (...)*] [
	void (...)* bitcast (%X* (%X*)* @X.foo to void (...)*) ; foo 
]

%X = type { 
	void (...)**; vtable
	,i32; x 
	,i32; y 
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %X, %X* %this, i32 0, i32 1
	store i32 0, i32* %x
	%y = getelementptr %X, %X* %this, i32 0, i32 2
	store i32 0, i32* %y
	ret void
}

define %X* @X.foo(%X* %self) { 
X.foo_entry:
	%tmp. = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = getelementptr %X, %X* %self, i32 0, i32 1
	store i32 42, i32* %tmp..2
	ret %X* %self
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 64)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	%tmp..3 = getelementptr %X, %X* %tmp..1, i32 0, i32 1
	%tmp..4 = load i32, i32* %tmp..3
	call void @printInt(i32 %tmp..4)
	%tmp..6 = call i8* @malloc(i32 64)
	%tmp..7 = bitcast i8* %tmp..6 to %X*
	call void @X.constructor(%X* %tmp..7)
	%tmp..9 = getelementptr %X, %X* %tmp..7, i32 0, i32 2
	%tmp..10 = load i32, i32* %tmp..9
	call void @printInt(i32 %tmp..10)
	%tmp..12 = call i8* @malloc(i32 64)
	%tmp..13 = bitcast i8* %tmp..12 to %X*
	call void @X.constructor(%X* %tmp..13)
	%tmp..15 = getelementptr %X, %X* %tmp..13, i32 0, i32 0
	%tmp..16 = load void (...)**, void (...)*** %tmp..15
	%tmp..17 = getelementptr void (...)*, void (...)** %tmp..16, i32 0
	%tmp..18 = bitcast void (...)** %tmp..17 to %X* (%X*)**
	%tmp..19 = load %X* (%X*)*, %X* (%X*)** %tmp..18
	%tmp..20 = call %X* %tmp..19(%X* %tmp..13)
	%tmp..21 = call i8* @malloc(i32 64)
	%tmp..22 = bitcast i8* %tmp..21 to %X*
	call void @X.constructor(%X* %tmp..22)
	%tmp..24 = getelementptr %X, %X* %tmp..22, i32 0, i32 0
	%tmp..25 = load void (...)**, void (...)*** %tmp..24
	%tmp..26 = getelementptr void (...)*, void (...)** %tmp..25, i32 0
	%tmp..27 = bitcast void (...)** %tmp..26 to %X* (%X*)**
	%tmp..28 = load %X* (%X*)*, %X* (%X*)** %tmp..27
	%tmp..29 = call %X* %tmp..28(%X* %tmp..22)
	%tmp..30 = getelementptr %X, %X* %tmp..29, i32 0, i32 1
	%tmp..31 = load i32, i32* %tmp..30
	call void @printInt(i32 %tmp..31)
	%tmp..33 = call i8* @malloc(i32 64)
	%tmp..34 = bitcast i8* %tmp..33 to %X*
	call void @X.constructor(%X* %tmp..34)
	%tmp..36 = getelementptr %X, %X* %tmp..34, i32 0, i32 0
	%tmp..37 = load void (...)**, void (...)*** %tmp..36
	%tmp..38 = getelementptr void (...)*, void (...)** %tmp..37, i32 0
	%tmp..39 = bitcast void (...)** %tmp..38 to %X* (%X*)**
	%tmp..40 = load %X* (%X*)*, %X* (%X*)** %tmp..39
	%tmp..41 = call %X* %tmp..40(%X* %tmp..34)
	%tmp..42 = getelementptr %X, %X* %tmp..41, i32 0, i32 2
	%tmp..43 = load i32, i32* %tmp..42
	call void @printInt(i32 %tmp..43)
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
