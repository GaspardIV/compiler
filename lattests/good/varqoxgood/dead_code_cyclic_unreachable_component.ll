
define i32 @main() { 
main_entry:
	br label %main.4_while.cond
main.4_while.cond:
	%x = phi i32 [0, %main_entry], [%x, %main.5_while.body]
	%c = phi i32 [42, %main_entry], [%tmp..7, %main.5_while.body]
	%tmp..5 = icmp slt i32 %x, %c
	br i1 %tmp..5, label %main.5_while.body, label %main.6_while.end
main.5_while.body:
	%tmp..7 = sub i32 %c, 1
	br label %main.4_while.cond
main.6_while.end:
	call void @printInt(i32 %c)
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

