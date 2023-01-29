
define i32 @main() { 
main_entry:
	%tmp..2 = call i32 @sum(i32 1000000, i32 0)
	call void @printInt(i32 %tmp..2)
	ret i32 0
}

define i32 @sum(i32 %z, i32 %a) { 
sum_entry:
	%tmp..3 = icmp eq i32 %z, 0
	br i1 %tmp..3, label %sum.1_if.true, label %sum.3_if.end
sum.1_if.true:
	ret i32 %a
sum.3_if.end:
	%tmp..9 = srem i32 %z, 2
	%tmp..11 = icmp eq i32 %tmp..9, 1
	br i1 %tmp..11, label %sum.4_if.true, label %sum.5_if.false
sum.4_if.true:
	%tmp..14 = mul i32 -1, %z
	br label %sum.6_if.end
sum.5_if.false:
	br label %sum.6_if.end
sum.6_if.end:
	%y = phi i32 [%tmp..14, %sum.4_if.true], [%z, %sum.5_if.false]
	%tmp..16 = sub i32 %z, 1
	%tmp..17 = add i32 %a, %y
	%tmp..18 = call i32 @sum(i32 %tmp..16, i32 %tmp..17)
	ret i32 %tmp..18
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

