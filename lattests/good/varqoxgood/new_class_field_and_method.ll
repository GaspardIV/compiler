@.str.str0 = private unnamed_addr constant [4 x i8] c"foo\00", align 1 ; --- Class X ---
%X = type { 
	i32, ; x 
	i32; y 
}
define void @X.constructor(%X* %this) {
	%x = getelementptr %X, %X* %this, i32 0, i32 0
	store i32 0, i32* %x
	%y = getelementptr %X, %X* %this, i32 0, i32 1
	store i32 0, i32* %y
	ret void
}

define %X* @X.foo(%X* %self) { 
X.foo_entry:
	%tmp. = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = getelementptr %X, %X* %self, i32 0, i32 0
	store i32 42, i32* %tmp..2
	ret %X* %self
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 64)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	%tmp..3 = getelementptr %X, %X* %tmp..1, i32 0, i32 0
	%tmp..4 = load i32, i32* %tmp..3
	call void @printInt(i32 %tmp..4)
	%tmp..6 = call i8* @malloc(i32 64)
	%tmp..7 = bitcast i8* %tmp..6 to %X*
	call void @X.constructor(%X* %tmp..7)
	%tmp..9 = getelementptr %X, %X* %tmp..7, i32 0, i32 1
	%tmp..10 = load i32, i32* %tmp..9
	call void @printInt(i32 %tmp..10)
	%tmp..12 = call i8* @malloc(i32 64)
	%tmp..13 = bitcast i8* %tmp..12 to %X*
	call void @X.constructor(%X* %tmp..13)
	%tmp..15 = call %X* @X.foo(%X* %tmp..13)
	%tmp..16 = call i8* @malloc(i32 64)
	%tmp..17 = bitcast i8* %tmp..16 to %X*
	call void @X.constructor(%X* %tmp..17)
	%tmp..19 = call %X* @X.foo(%X* %tmp..17)
	%tmp..20 = getelementptr %X, %X* %tmp..19, i32 0, i32 0
	%tmp..21 = load i32, i32* %tmp..20
	call void @printInt(i32 %tmp..21)
	%tmp..23 = call i8* @malloc(i32 64)
	%tmp..24 = bitcast i8* %tmp..23 to %X*
	call void @X.constructor(%X* %tmp..24)
	%tmp..26 = call %X* @X.foo(%X* %tmp..24)
	%tmp..27 = getelementptr %X, %X* %tmp..26, i32 0, i32 1
	%tmp..28 = load i32, i32* %tmp..27
	call void @printInt(i32 %tmp..28)
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
