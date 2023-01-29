@.str.str0 = private unnamed_addr constant [1 x i8] c"\00", align 1
define i32 @main() { 
main_entry:
	%tmp. = getelementptr [1 x i8], [1 x i8]* @.str.str0, i32 0, i32 0
	%tmp..4 = getelementptr i8, i8* %tmp., i32 -1
	%tmp..5 = load i32, i32* %tmp..4
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

