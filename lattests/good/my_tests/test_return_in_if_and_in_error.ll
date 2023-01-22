
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
	call void @error()
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

