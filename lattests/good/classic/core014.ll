
define i32 @main() { 
main_entry:
	call void @printInt(i32 1)
	br label %main.1_while.cond
main.1_while.cond:
	%hi = phi i32 [1, %main_entry], [%tmp..9, %main.2_while.body]
	%lo = phi i32 [1, %main_entry], [%tmp..10, %main.2_while.body]
	%tmp..7 = icmp slt i32 %hi, 5000000
	br i1 %tmp..7, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	call void @printInt(i32 %hi)
	%tmp..9 = add i32 %lo, %hi
	%tmp..10 = sub i32 %tmp..9, %lo
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

