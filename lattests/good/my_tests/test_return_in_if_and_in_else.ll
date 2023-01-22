
define i32 @a() { 
a_entry:
	ret i32 5
}

define i32 @main() { 
main_entry:
	%tmp..3 = call i32 @a()
	%tmp..5 = icmp eq i32 %tmp..3, 5
	br i1 %tmp..5, label %main.1_if.true, label %main.2_if.false
main.1_if.true:
	call void @printInt(i32 1)
	ret i32 0
main.2_if.false:
	call void @printInt(i32 0)
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

