
define i32 @main() { 
main_entry:
	%tmp..3 = call i32 @inc(i32 1, i32 1, i32 1)
	ret i32 0
}

define i32 @inc(i32 %k, i32 %l, i32 %m) { 
inc_entry:
	br label %inc.1_while.cond
inc.1_while.cond:
	%k.1 = phi i32 [%k, %inc_entry], [%tmp..53, %inc.6_while.end]
	%l.1 = phi i32 [%l, %inc_entry], [1, %inc.6_while.end]
	%m.1 = phi i32 [%m, %inc_entry], [%m.2, %inc.6_while.end]
	%tmp..3 = icmp sle i32 %k.1, 3
	br i1 %tmp..3, label %inc.2_while.body, label %inc.3_while.end
inc.2_while.body:
	br label %inc.4_while.cond
inc.4_while.cond:
	%l.2 = phi i32 [%l.1, %inc.2_while.body], [%tmp..50, %inc.9_while.end]
	%m.2 = phi i32 [%m.1, %inc.2_while.body], [1, %inc.9_while.end]
	%tmp..7 = icmp sle i32 %l.2, 4
	br i1 %tmp..7, label %inc.5_while.body, label %inc.6_while.end
inc.5_while.body:
	br label %inc.7_while.cond
inc.7_while.cond:
	%m.3 = phi i32 [%m.2, %inc.5_while.body], [%tmp..48, %inc.12_if.end]
	%tmp..11 = icmp sle i32 %m.3, 5
	br i1 %tmp..11, label %inc.8_while.body, label %inc.9_while.end
inc.8_while.body:
	%tmp..21 = icmp eq i32 %k.1, 2
	br i1 %tmp..21, label %inc.13_and, label %inc.11_if.false
inc.13_and:
	%tmp..23 = icmp eq i32 %l.2, 3
	br i1 %tmp..23, label %inc.14_and, label %inc.11_if.false
inc.14_and:
	%tmp..25 = icmp eq i32 %m.3, 4
	br i1 %tmp..25, label %inc.10_if.true, label %inc.11_if.false
inc.10_if.true:
	call void @printInt(i32 0)
	br label %inc.12_if.end
inc.11_if.false:
	%tmp..37 = icmp eq i32 %k.1, 3
	br i1 %tmp..37, label %inc.18_and, label %inc.16_if.false
inc.18_and:
	%tmp..39 = icmp eq i32 %l.2, 2
	br i1 %tmp..39, label %inc.19_and, label %inc.16_if.false
inc.19_and:
	%tmp..41 = icmp eq i32 %m.3, 1
	br i1 %tmp..41, label %inc.15_if.true, label %inc.16_if.false
inc.15_if.true:
	call void @printInt(i32 1)
	br label %inc.12_if.end
inc.16_if.false:
	%tmp..44 = mul i32 %k.1, %l.2
	%tmp..45 = mul i32 %tmp..44, %m.3
	call void @printInt(i32 %tmp..45)
	br label %inc.12_if.end
inc.12_if.end:
	%tmp..48 = add i32 %m.3, 1
	br label %inc.7_while.cond
inc.9_while.end:
	%tmp..50 = add i32 %l.2, 1
	br label %inc.4_while.cond
inc.6_while.end:
	%tmp..53 = add i32 %k.1, 1
	br label %inc.1_while.cond
inc.3_while.end:
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

