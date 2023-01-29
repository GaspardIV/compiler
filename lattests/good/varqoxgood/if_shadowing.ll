
define i32 @foo(i32 %x) { 
foo_entry:
	%tmp..3 = icmp ne i32 %x, 44
	br i1 %tmp..3, label %foo.1_if.true, label %foo.3_if.end
foo.1_if.true:
	call void @error()
	ret i32 0
foo.3_if.end:
	ret i32 0
}

define i32 @main() { 
main.1_if.true:
	%tmp..5 = call i32 @foo(i32 44)
	br label %main.7_if.true
main.7_if.true:
	%tmp..12 = call i32 @foo(i32 44)
	br label %main.9_if.end
main.9_if.end:
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

