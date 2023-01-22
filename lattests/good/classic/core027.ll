@.str.str1 = private unnamed_addr constant [4 x i8] c"bad\00", align 1@.str.str0 = private unnamed_addr constant [5 x i8] c"good\00", align 1
define void @f(i8* %arg) { 
f_entry:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = getelementptr [4 x i8], [4 x i8]* @.str.str1, i32 0, i32 0
	call void @f(i8* %tmp.)
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

