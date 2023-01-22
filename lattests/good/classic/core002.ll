@.str.str0 = private unnamed_addr constant [4 x i8] c"foo\00", align 1
define i32 @main() { 
main_entry:
	call void @printf.user()
	ret i32 0
}

define void @printf.user() { 
printf_entry:
	%tmp. = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

