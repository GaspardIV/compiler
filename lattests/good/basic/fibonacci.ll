@.str.str0 = private unnamed_addr constant [42 x i8] c"Expected a non-negative integer, but got:\00", align 1
define i32 @fibonacci(i32 %n) { 
fibonacci_entry:
	%tmp..3 = icmp sle i32 %n, 1
	br i1 %tmp..3, label %fibonacci.1_if.true, label %fibonacci.2_if.end
fibonacci.1_if.true:
	ret i32 %n
fibonacci.2_if.end:
	br label %fibonacci.3_while.cond
fibonacci.3_while.cond:
	%fib_a = phi i32 [0, %fibonacci.2_if.end], [%fib_b, %fibonacci.4_while.body]
	%fib_b = phi i32 [1, %fibonacci.2_if.end], [%tmp..10, %fibonacci.4_while.body]
	%i = phi i32 [2, %fibonacci.2_if.end], [%tmp..12, %fibonacci.4_while.body]
	%tmp..9 = icmp sle i32 %i, %n
	br i1 %tmp..9, label %fibonacci.4_while.body, label %fibonacci.5_while.end
fibonacci.4_while.body:
	%tmp..10 = add i32 %fib_b, %fib_a
	%tmp..12 = add i32 %i, 1
	br label %fibonacci.3_while.cond
fibonacci.5_while.end:
	ret i32 %fib_b
}

define i32 @main() { 
main_entry:
	%tmp. = call i32 @readInt()
	%tmp..4 = icmp sge i32 %tmp., 0
	br i1 %tmp..4, label %main.1_if.true, label %main.2_if.false
main.1_if.true:
	%tmp..5 = call i32 @fibonacci(i32 %tmp.)
	call void @printInt(i32 %tmp..5)
	ret i32 0
main.2_if.false:
	%tmp..8 = getelementptr [42 x i8], [42 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..8)
	call void @printInt(i32 %tmp.)
	ret i32 1
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

@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

