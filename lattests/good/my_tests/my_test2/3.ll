
define i32 @main() { 
main_entry:
	%tmp..3 = call i32 @funkcja(i32 4, i32 20)
	%tmp..4 = add i32 0, %tmp..3
	call void @printInt(i32 %tmp..4)
	ret i32 0
}

define i32 @funkcja(i32 %x, i32 %n) { 
funkcja_entry:
	br label %funkcja.1_while.cond
funkcja.1_while.cond:
	%i = phi i32 [0, %funkcja_entry], [%tmp..12, %funkcja.7_if.end]
	%m = phi i32 [0, %funkcja_entry], [%m.2, %funkcja.7_if.end]
	%x.1 = phi i32 [%x, %funkcja_entry], [%tmp..10, %funkcja.7_if.end]
	%tmp..4 = icmp slt i32 %m, %n
	br i1 %tmp..4, label %funkcja.2_while.body, label %funkcja.3_while.end
funkcja.2_while.body:
	%tmp..6 = icmp slt i32 %m, %x.1
	br i1 %tmp..6, label %funkcja.4_if.true, label %funkcja.5_if.end
funkcja.4_if.true:
	br label %funkcja.5_if.end
funkcja.5_if.end:
	%m.1 = phi i32 [%m, %funkcja.2_while.body], [%x.1, %funkcja.4_if.true]
	%tmp..8 = icmp slt i32 %m.1, %x.1
	br i1 %tmp..8, label %funkcja.6_if.true, label %funkcja.7_if.end
funkcja.6_if.true:
	br label %funkcja.7_if.end
funkcja.7_if.end:
	%m.2 = phi i32 [%m.1, %funkcja.5_if.end], [%x.1, %funkcja.6_if.true]
	%tmp..10 = add i32 %x.1, 1
	%tmp..12 = add i32 %i, 1
	br label %funkcja.1_while.cond
funkcja.3_while.end:
	ret i32 %m
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

