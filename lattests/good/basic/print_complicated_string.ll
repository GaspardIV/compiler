@.str.str0 = private unnamed_addr constant [9 x i8] c"\\a\\n\0A\09b\22\00", align 1
define i32 @main() { 
main_entry:
	%tmp. = getelementptr [9 x i8], [9 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
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

