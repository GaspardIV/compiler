
define i32 @main() { 
main_entry:
	%tmp. = call i32 @readInt()
	%tmp..5 = icmp slt i32 %tmp., 0
	br i1 %tmp..5, label %main.1_if.true, label %main.2_if.false
main.1_if.true:
	br label %main.3_if.end
main.2_if.false:
	br label %main.3_if.end
main.3_if.end:
	%b = phi i32 [0, %main.1_if.true], [1, %main.2_if.false]
	call void @printInt(i32 %b)
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

@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

