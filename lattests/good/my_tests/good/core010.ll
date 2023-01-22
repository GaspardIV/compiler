
define i32 @fac(i32 %a) { 
fac_entry:
	br label %fac.1_while.cond
fac.1_while.cond:
	%n = phi i32 [%a, %fac_entry], [%tmp..9, %fac.2_while.body]
	%r = phi i32 [1, %fac_entry], [%tmp..7, %fac.2_while.body]
	%tmp..6 = icmp sgt i32 %n, 0
	br i1 %tmp..6, label %fac.2_while.body, label %fac.3_while.end
fac.2_while.body:
	%tmp..7 = mul i32 %r, %n
	%tmp..9 = sub i32 %n, 1
	br label %fac.1_while.cond
fac.3_while.end:
	ret i32 %r
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @fac(i32 5)
	call void @printInt(i32 %tmp..1)
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

