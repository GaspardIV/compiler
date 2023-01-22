@.str.str0 = private unnamed_addr constant [9 x i8] c"========\00", align 1
define void @funkcja6() { 
funkcja6_entry:
	%tmp. = getelementptr [9 x i8], [9 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %funkcja6.1_while.cond
funkcja6.1_while.cond:
	%i = phi i32 [0, %funkcja6_entry], [%tmp..19, %funkcja6.6_while.end]
	%j = phi i32 [0, %funkcja6_entry], [%j.1, %funkcja6.6_while.end]
	%tmp..7 = icmp slt i32 %i, 5
	br i1 %tmp..7, label %funkcja6.2_while.body, label %funkcja6.3_while.end
funkcja6.2_while.body:
	br label %funkcja6.4_while.cond
funkcja6.4_while.cond:
	%j.1 = phi i32 [%j, %funkcja6.2_while.body], [%tmp..15, %funkcja6.5_while.body]
	%tmp..11 = icmp slt i32 %j.1, 5
	br i1 %tmp..11, label %funkcja6.5_while.body, label %funkcja6.6_while.end
funkcja6.5_while.body:
	call void @printInt(i32 %j.1)
	call void @printInt(i32 %i)
	%tmp..15 = add i32 %j.1, 1
	br label %funkcja6.4_while.cond
funkcja6.6_while.end:
	call void @printInt(i32 %i)
	call void @printInt(i32 %j.1)
	%tmp..19 = add i32 %i, 1
	br label %funkcja6.1_while.cond
funkcja6.3_while.end:
	ret void
}

define i32 @main() { 
main_entry:
	call void @funkcja2()
	call void @funkcja3(i32 0)
	call void @funkcja4()
	call void @funkcja5()
	call void @funkcja6()
	ret i32 0
}

define void @funkcja2() { 
funkcja2_entry:
	br label %funkcja2.1_while.cond
funkcja2.1_while.cond:
	%a = phi i32 [0, %funkcja2_entry], [%a.1, %funkcja2.5_if.end]
	%b = phi i32 [10, %funkcja2_entry], [%b.3, %funkcja2.5_if.end]
	%i = phi i32 [0, %funkcja2_entry], [%tmp..9, %funkcja2.5_if.end]
	%j = phi i32 [0, %funkcja2_entry], [%j.2, %funkcja2.5_if.end]
	%tmp..7 = icmp slt i32 %i, 5
	br i1 %tmp..7, label %funkcja2.2_while.body, label %funkcja2.3_while.end
funkcja2.2_while.body:
	%tmp..9 = add i32 %i, 1
	%tmp..15 = srem i32 %tmp..9, 2
	%tmp..17 = icmp eq i32 %tmp..15, 0
	br i1 %tmp..17, label %funkcja2.4_if.true, label %funkcja2.5_if.end
funkcja2.4_if.true:
	%tmp..19 = add i32 %a, 1
	br label %funkcja2.6_while.cond
funkcja2.6_while.cond:
	%b.1 = phi i32 [%b, %funkcja2.4_if.true], [%b.2, %funkcja2.10_if.end]
	%j.1 = phi i32 [%j, %funkcja2.4_if.true], [%tmp..25, %funkcja2.10_if.end]
	%tmp..23 = icmp slt i32 %j.1, 5
	br i1 %tmp..23, label %funkcja2.7_while.body, label %funkcja2.8_while.end
funkcja2.7_while.body:
	%tmp..25 = add i32 %j.1, 1
	%tmp..29 = icmp slt i32 %tmp..25, 5
	br i1 %tmp..29, label %funkcja2.9_if.true, label %funkcja2.10_if.end
funkcja2.9_if.true:
	%tmp..31 = sub i32 %b.1, 1
	call void @printInt(i32 %tmp..25)
	call void @printInt(i32 %tmp..31)
	br label %funkcja2.10_if.end
funkcja2.10_if.end:
	%b.2 = phi i32 [%b.1, %funkcja2.7_while.body], [%tmp..31, %funkcja2.9_if.true]
	call void @printInt(i32 %b.2)
	br label %funkcja2.6_while.cond
funkcja2.8_while.end:
	call void @printInt(i32 %tmp..19)
	call void @printInt(i32 %b.1)
	br label %funkcja2.5_if.end
funkcja2.5_if.end:
	%a.1 = phi i32 [%a, %funkcja2.2_while.body], [%tmp..19, %funkcja2.8_while.end]
	%b.3 = phi i32 [%b, %funkcja2.2_while.body], [%b.1, %funkcja2.8_while.end]
	%j.2 = phi i32 [%j, %funkcja2.2_while.body], [%j.1, %funkcja2.8_while.end]
	br label %funkcja2.1_while.cond
funkcja2.3_while.end:
	call void @printInt(i32 %a)
	call void @printInt(i32 %b)
	ret void
}

define void @funkcja3(i32 %j) { 
funkcja3_entry:
	%tmp..1 = add i32 %j, 1
	%tmp..8 = srem i32 %tmp..1, 2
	%tmp..10 = icmp ne i32 %tmp..8, 0
	br i1 %tmp..10, label %funkcja3.1_if.true, label %funkcja3.2_if.end
funkcja3.1_if.true:
	call void @printInt(i32 %tmp..1)
	call void @printInt(i32 9)
	br label %funkcja3.2_if.end
funkcja3.2_if.end:
	%b = phi i32 [10, %funkcja3_entry], [9, %funkcja3.1_if.true]
	call void @printInt(i32 %b)
	ret void
}

define void @funkcja4() { 
funkcja4_entry:
	br label %funkcja4.1_if.true
funkcja4.1_if.true:
	br label %funkcja4.3_while.cond
funkcja4.3_while.cond:
	%b = phi i32 [10, %funkcja4.1_if.true], [%b.1, %funkcja4.7_if.end]
	%j = phi i32 [0, %funkcja4.1_if.true], [%tmp..15, %funkcja4.7_if.end]
	%tmp..13 = icmp slt i32 %j, 5
	br i1 %tmp..13, label %funkcja4.4_while.body, label %funkcja4.5_while.end
funkcja4.4_while.body:
	%tmp..15 = add i32 %j, 1
	%tmp..19 = icmp slt i32 %tmp..15, 5
	br i1 %tmp..19, label %funkcja4.6_if.true, label %funkcja4.7_if.end
funkcja4.6_if.true:
	%tmp..21 = sub i32 %b, 1
	call void @printInt(i32 %tmp..15)
	call void @printInt(i32 %tmp..21)
	br label %funkcja4.7_if.end
funkcja4.7_if.end:
	%b.1 = phi i32 [%b, %funkcja4.4_while.body], [%tmp..21, %funkcja4.6_if.true]
	call void @printInt(i32 %b.1)
	br label %funkcja4.3_while.cond
funkcja4.5_while.end:
	call void @printInt(i32 1)
	call void @printInt(i32 %b)
	br label %funkcja4.2_if.end
funkcja4.2_if.end:
	call void @printInt(i32 1)
	call void @printInt(i32 %b)
	ret void
}

define void @funkcja5() { 
funkcja5_entry:
	br label %funkcja5.1_if.true
funkcja5.1_if.true:
	br label %funkcja5.3_while.cond
funkcja5.3_while.cond:
	%b = phi i32 [10, %funkcja5.1_if.true], [%b.1, %funkcja5.7_if.end]
	%j = phi i32 [0, %funkcja5.1_if.true], [%tmp..19, %funkcja5.7_if.end]
	%tmp..15 = srem i32 %j, 2
	%tmp..17 = icmp ne i32 %tmp..15, 0
	br i1 %tmp..17, label %funkcja5.4_while.body, label %funkcja5.5_while.end
funkcja5.4_while.body:
	%tmp..19 = add i32 %j, 1
	%tmp..23 = icmp slt i32 %tmp..19, 5
	br i1 %tmp..23, label %funkcja5.6_if.true, label %funkcja5.7_if.end
funkcja5.6_if.true:
	%tmp..25 = sub i32 %b, 1
	call void @printInt(i32 %tmp..19)
	call void @printInt(i32 %tmp..25)
	br label %funkcja5.7_if.end
funkcja5.7_if.end:
	%b.1 = phi i32 [%b, %funkcja5.4_while.body], [%tmp..25, %funkcja5.6_if.true]
	call void @printInt(i32 %b.1)
	br label %funkcja5.3_while.cond
funkcja5.5_while.end:
	call void @printInt(i32 1)
	call void @printInt(i32 %b)
	br label %funkcja5.2_if.end
funkcja5.2_if.end:
	call void @printInt(i32 1)
	call void @printInt(i32 %b)
	ret void
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

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

