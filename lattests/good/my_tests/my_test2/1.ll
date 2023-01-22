
define i32 @func(i32 %b) { 
func_entry:
	%tmp..4 = add i32 %b, 36
	ret i32 %tmp..4
}

define i32 @func2(i32 %b) { 
func2_entry:
	%tmp..8 = add i32 %b, 36
	ret i32 %tmp..8
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @func(i32 20)
	call void @printInt(i32 %tmp..1)
	%tmp..4 = call i32 @func2(i32 21)
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

