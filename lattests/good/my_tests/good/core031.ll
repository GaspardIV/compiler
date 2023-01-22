
define i32 @f(i32 %a, i32 %b) { 
f_entry:
	%tmp..12 = icmp sgt i32 %a, 0
	br i1 %tmp..12, label %f.5_and, label %f.4_or
f.5_and:
	%tmp..14 = icmp sgt i32 %b, 0
	br i1 %tmp..14, label %f.1_if.true, label %f.4_or
f.4_or:
	%tmp..16 = icmp slt i32 %a, 0
	br i1 %tmp..16, label %f.6_and, label %f.2_if.false
f.6_and:
	%tmp..18 = icmp slt i32 %b, 0
	br i1 %tmp..18, label %f.1_if.true, label %f.2_if.false
f.1_if.true:
	ret i32 7
f.2_if.false:
	ret i32 42
}

define i32 @main() { 
main_entry:
	%tmp..3 = call i32 @f(i32 1, i32 -1)
	call void @printInt(i32 %tmp..3)
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

