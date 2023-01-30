@.str.str0 = private unnamed_addr constant [4 x i8] c"aaa\00", align 1@.str.str1 = private unnamed_addr constant [4 x i8] c"ccc\00", align 1@.str.str2 = private unnamed_addr constant [4 x i8] c"xxx\00", align 1
define i32 @main() { 
main_entry:
	%tmp. = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	%tmp..7 = call i32 @._strcmp(i8* %tmp., i8* %tmp.)
	%tmp..8 = icmp ne i32 %tmp..7, 0
	br i1 %tmp..8, label %main.1_if.true, label %main.2_if.false
main.1_if.true:
	call void @error()
	ret i32 0
main.2_if.false:
	br label %main.3_if.end
main.3_if.end:
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

declare i32 @strcmp(i8*, i8*)
define i32 @._strcmp(i8* %str1, i8* %str2) {
       %t0 = call i32 @strcmp(i8* %str1, i8* %str2)
       ret i32 %t0
}

