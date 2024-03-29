
define i32 @f(i32 %n) { 
f_entry:
	br label %f.1_while.cond
f.1_while.cond:
	%i = phi i32 [2, %f_entry], [%tmp..8, %f.2_while.body]
	%u = phi i32 [0, %f_entry], [%v, %f.2_while.body]
	%v = phi i32 [1, %f_entry], [%tmp..6, %f.2_while.body]
	%tmp..5 = icmp slt i32 %i, %n
	br i1 %tmp..5, label %f.2_while.body, label %f.3_while.end
f.2_while.body:
	%tmp..6 = add i32 %u, %v
	%tmp..8 = add i32 %i, 1
	br label %f.1_while.cond
f.3_while.end:
	ret i32 %v
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @f(i32 20)
	call void @printInt(i32 %tmp..1)
	%tmp..4 = call i32 @f(i32 21)
	call void @printInt(i32 %tmp..4)
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

