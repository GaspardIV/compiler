
define i32 @main() { 
main_entry:
	%tmp. = call i32 @readInt()
	%tmp..2 = icmp sgt i32 %tmp., 0
	br i1 %tmp..2, label %main.1_expr.true, label %main.4_or
main.4_or:
	br label %main.1_expr.true
main.1_expr.true:
	br label %main.3_expr.end
main.3_expr.end:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

