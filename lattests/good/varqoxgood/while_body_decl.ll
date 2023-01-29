 ; --- Class X ---
%X = type { 
	i32; x 
}
define void @X.constructor(%X* %this) {
	%x = getelementptr %X, %X* %this, i32 0, i32 0
	store i32 0, i32* %x
	ret void
}

define i32 @incr_x(%X* %x) { 
incr_x_entry:
	%tmp..2 = getelementptr %X, %X* %x, i32 0, i32 0
	%tmp..4 = load i32, i32* %tmp..2
	%tmp..6 = add i32 %tmp..4, 1
	store i32 %tmp..6, i32* %tmp..2
	%tmp..1 = load i32, i32* %tmp..2
	%tmp..8 = load i32, i32* %tmp..2
	ret i32 %tmp..8
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	br label %main.1_while.cond
main.1_while.cond:
	%tmp..7 = getelementptr %X, %X* %tmp..1, i32 0, i32 0
	%tmp..8 = load i32, i32* %tmp..7
	%tmp..10 = icmp slt i32 %tmp..8, 10
	br i1 %tmp..10, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..11 = call i32 @incr_x(%X* %tmp..1)
	br label %main.1_while.cond
main.3_while.end:
	%tmp..17 = load i32, i32* %tmp..7
	%tmp..19 = icmp ne i32 %tmp..17, 10
	br i1 %tmp..19, label %main.4_if.true, label %main.6_if.end
main.4_if.true:
	call void @error()
	ret i32 0
main.7_while.cond:
	%tmp..27 = load i32, i32* %tmp..7
	%tmp..29 = icmp slt i32 %tmp..27, 20
	br i1 %tmp..29, label %main.8_while.body, label %main.9_while.end
main.8_while.body:
	%tmp..32 = load i32, i32* %tmp..7
	%tmp..34 = add i32 %tmp..32, 7
	store i32 %tmp..34, i32* %tmp..7
	br label %main.7_while.cond
main.9_while.end:
	%tmp..40 = load i32, i32* %tmp..7
	%tmp..42 = icmp ne i32 %tmp..40, 24
	br i1 %tmp..42, label %main.10_if.true, label %main.12_if.end
main.10_if.true:
	call void @error()
	ret i32 0
main.12_if.end:
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

declare i8* @malloc(i32)
