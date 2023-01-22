
define i32 @main() { 
main_entry:
	br label %main.1_while.cond
main.1_while.cond:
	%y = phi i32 [17, %main_entry], [%tmp..6, %main.2_while.body]
	%tmp..4 = icmp sgt i32 %y, 0
	br i1 %tmp..4, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..6 = sub i32 %y, 2
	br label %main.1_while.cond
main.3_while.end:
	%tmp..10 = icmp slt i32 %y, 0
	br i1 %tmp..10, label %main.4_if.true, label %main.5_if.false
main.4_if.true:
	call void @printInt(i32 0)
	ret i32 0
main.5_if.false:
	call void @printInt(i32 1)
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

