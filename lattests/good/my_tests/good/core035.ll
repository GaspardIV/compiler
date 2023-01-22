
define i32 @main() { 
main_entry:
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [0, %main_entry], [%tmp..8, %main.2_while.body]
	%tmp..3 = icmp slt i32 %i, 100
	br i1 %tmp..3, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..5 = srem i32 %i, 32
	call void @printInt(i32 %tmp..5)
	%tmp..8 = add i32 %i, 1
	br label %main.1_while.cond
main.3_while.end:
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

