
define i32 @foo() { 
foo_entry:
	ret i32 42
}

define i32 @main() { 
main_entry:
	%tmp..5 = call i32 @foo()
	%tmp..6 = add i32 %tmp..5, 3
	%tmp..8 = icmp ne i32 %tmp..6, 45
	br i1 %tmp..8, label %main.1_if.true, label %main.2_if.end
main.1_if.true:
	call void @error()
	ret i32 0
main.2_if.end:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

@._runtime_error = internal constant [15 x i8] c"runtime error\0A\00"
declare void @exit(i32)
define void @error() {
entry:  %t0 = getelementptr [15 x i8], [15 x i8]* @._runtime_error, i32 0, i32 0
call void @printString(i8* %t0)

call void @exit(i32 1)
	ret void
}

