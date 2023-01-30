
define i32 @main() { 
main_entry:
	%tmp..3 = mul i32 1, 4
	%tmp..4 = add i32 %tmp..3, 4
	%tmp..5 = call i8* @calloc(i32 1, i32 %tmp..4)
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	store i32 1, i32* %tmp..6
	%tmp..7 = getelementptr i8, i8* %tmp..5, i32 4
	%tmp..8 = bitcast i8* %tmp..7 to i32*
	%tmp..10 = bitcast i32* %tmp..8 to i8*
	%tmp..9 = getelementptr i8, i8* %tmp..10, i32 -4
	%tmp..11 = bitcast i8* %tmp..9 to i32*
	%tmp..12 = load i32, i32* %tmp..11
	br label %main.1_for.cond
main.1_for.cond:
	%i.d.x..1 = phi i32 [0, %main_entry], [%tmp..17, %main.9_if.end]
	%iter = phi i32 [0, %main_entry], [%tmp..25, %main.9_if.end]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..12
	br i1 %ifres., label %main.2_for.body, label %main.3_for.end
main.2_for.body:
	%tmp..14 = getelementptr i32, i32* %tmp..8, i32 %i.d.x..1
	%tmp..15 = load i32, i32* %tmp..14
	%tmp..17 = add i32 %i.d.x..1, 1
	%tmp..21 = icmp ne i32 %tmp..15, 0
	br i1 %tmp..21, label %main.4_if.true, label %main.5_if.false
main.4_if.true:
	call void @error()
	ret i32 0
main.5_if.false:
	br label %main.6_if.end
main.6_if.end:
	%tmp..25 = add i32 %iter, 1
	%tmp..27 = add i32 %tmp..15, 2
	%tmp..29 = add i32 %tmp..27, 3
	%tmp..33 = icmp ne i32 %tmp..29, 5
	br i1 %tmp..33, label %main.7_if.true, label %main.8_if.false
main.7_if.true:
	call void @error()
	ret i32 0
main.8_if.false:
	br label %main.9_if.end
main.9_if.end:
	br label %main.1_for.cond
main.3_for.end:
	%tmp..41 = icmp ne i32 %iter, 1
	br i1 %tmp..41, label %main.13_if.true, label %main.14_if.false
main.13_if.true:
	call void @error()
	ret i32 0
main.14_if.false:
	br label %main.15_if.end
main.15_if.end:
	%tmp..50 = getelementptr i32, i32* %tmp..8, i32 0
	%tmp..51 = load i32, i32* %tmp..50
	%tmp..53 = icmp ne i32 %tmp..51, 0
	br i1 %tmp..53, label %main.16_if.true, label %main.17_if.false
main.16_if.true:
	call void @error()
	ret i32 0
main.17_if.false:
	br label %main.18_if.end
main.18_if.end:
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
