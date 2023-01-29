@.str.str0 = private unnamed_addr constant [12 x i8] c"bla bla bla\00", align 1
define i32 @main() { 
main_entry:
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

declare i8* @calloc(i32, i32)
@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

