
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
	call void @printInt(i32 %tmp..2)
	call void @printInt(i32 %tmp..2)
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

