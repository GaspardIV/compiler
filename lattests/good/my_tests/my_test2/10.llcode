
define i32 @function2(i32 %c, i32 %d) { 
function2_entry:
	%tmp. = add i32 %c, %d
	%tmp..2 = add i32 %tmp., %tmp.
	ret i32 %tmp..2
}

define i32 @function1(i32 %x, i32 %y) { 
function1_entry:
	%tmp. = mul i32 %x, %y
	ret i32 %tmp.
}

define i32 @main() { 
main_entry:
	%tmp..3 = call i32 @funkcja(i32 5, i32 10, i32 15)
	call void @printInt(i32 %tmp..3)
	ret i32 0
}

define i32 @funkcja(i32 %x, i32 %y, i32 %z) { 
funkcja_entry:
	%tmp. = add i32 %x, %y
	%tmp..2 = add i32 %tmp., %z
	br label %funkcja.1_while.cond
funkcja.1_while.cond:
	%e = phi i32 [0, %funkcja_entry], [%tmp..11, %funkcja.2_while.body]
	%i = phi i32 [0, %funkcja_entry], [%tmp..13, %funkcja.2_while.body]
	%tmp..9 = icmp slt i32 %i, 5
	br i1 %tmp..9, label %funkcja.2_while.body, label %funkcja.3_while.end
funkcja.2_while.body:
	%tmp..10 = call i32 @function1(i32 %x, i32 %y)
	%tmp..11 = add i32 %e, %tmp..10
	%tmp..13 = add i32 %i, 1
	call void @printInt(i32 %tmp..2)
	br label %funkcja.1_while.cond
funkcja.3_while.end:
	%tmp..16 = call i32 @function2(i32 %tmp..2, i32 %tmp..2)
	call void @printInt(i32 %e)
	call void @printInt(i32 %tmp..16)
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

