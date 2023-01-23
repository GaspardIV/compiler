
define i32 @main() { 
main_entry:
	%tmp..2 = call i32 @funkcja(i32 5, i32 20)
	call void @printInt(i32 %tmp..2)
	ret i32 0
}

define i32 @funkcja(i32 %x, i32 %h) { 
funkcja_entry:
	br label %funkcja.1_while.cond
funkcja.1_while.cond:
	%c = phi i32 [0, %funkcja_entry], [%tmp..8, %funkcja.2_while.body]
	%h.1 = phi i32 [%h, %funkcja_entry], [%tmp..10, %funkcja.2_while.body]
	%tmp..7 = icmp sgt i32 %h.1, 0
	br i1 %tmp..7, label %funkcja.2_while.body, label %funkcja.3_while.end
funkcja.2_while.body:
	%tmp..8 = add i32 %c, %x
	%tmp..10 = sub i32 %h.1, 1
	br label %funkcja.1_while.cond
funkcja.3_while.end:
	ret i32 1
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

