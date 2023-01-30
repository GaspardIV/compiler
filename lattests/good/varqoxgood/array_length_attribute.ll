
define i32 @main() { 
main_entry:
	%tmp..14 = mul i32 4, 4
	%tmp..15 = add i32 %tmp..14, 4
	%tmp..16 = call i8* @calloc(i32 1, i32 %tmp..15)
	%tmp..17 = bitcast i8* %tmp..16 to i32*
	store i32 4, i32* %tmp..17
	%tmp..18 = getelementptr i8, i8* %tmp..16, i32 4
	%tmp..19 = bitcast i8* %tmp..18 to i32*
	%tmp..21 = bitcast i32* %tmp..19 to i8*
	%tmp..20 = getelementptr i8, i8* %tmp..21, i32 -4
	%tmp..22 = bitcast i8* %tmp..20 to i32*
	%tmp..23 = load i32, i32* %tmp..22
	%tmp..25 = icmp ne i32 %tmp..23, 4
	br i1 %tmp..25, label %main.1_if.true, label %main.2_if.false
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

declare i8* @calloc(i32, i32)
