
define i32 @main() { 
main.1_while.cond:
	%tmp..3 = call i32 @readInt()
	%tmp..5 = icmp eq i32 %tmp..3, 42
	br i1 %tmp..5, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	call void @printInt(i32 -42)
	br label %main.5_while.body
main.5_while.body:
	ret i32 0
main.3_while.end:
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

