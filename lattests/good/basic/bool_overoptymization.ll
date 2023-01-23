@.str.str0 = private unnamed_addr constant [5 x i8] c"ahoj\00", align 1
define i1 @print() { 
print_entry:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret i1 true
}

define i32 @main() { 
main_entry:
	%tmp. = call i1 @print()
	br i1 %tmp., label %main.3_expr.end, label %main.3_expr.end
main.3_expr.end:
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

