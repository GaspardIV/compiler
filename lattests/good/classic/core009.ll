
define i32 @foo() { 
foo_entry:
	ret i32 10
}

define i32 @main() { 
main_entry:
	%tmp. = call i32 @foo()
	call void @printInt(i32 %tmp.)
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

