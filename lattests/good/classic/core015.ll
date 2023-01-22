
define i32 @ev(i32 %y) { 
ev_entry:
	%tmp..3 = icmp sgt i32 %y, 0
	br i1 %tmp..3, label %ev.1_if.true, label %ev.2_if.false
ev.1_if.true:
	%tmp..5 = sub i32 %y, 2
	%tmp..6 = call i32 @ev(i32 %tmp..5)
	ret i32 %tmp..6
ev.2_if.false:
	%tmp..10 = icmp slt i32 %y, 0
	br i1 %tmp..10, label %ev.4_if.true, label %ev.5_if.false
ev.4_if.true:
	ret i32 0
ev.5_if.false:
	ret i32 1
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @ev(i32 17)
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

