
define void @test() { 
test_entry:
	%tmp..2 = mul i32 3, 4
	%tmp..3 = add i32 %tmp..2, 4
	%tmp..4 = call i8* @calloc(i32 1, i32 %tmp..3)
	%tmp..5 = bitcast i8* %tmp..4 to i32*
	store i32 3, i32* %tmp..5
	%tmp..6 = getelementptr i8, i8* %tmp..4, i32 4
	%tmp..7 = bitcast i8* %tmp..6 to i32*
	%tmp..9 = bitcast i32* %tmp..7 to i8*
	%tmp..8 = getelementptr i8, i8* %tmp..9, i32 -4
	%tmp..10 = bitcast i8* %tmp..8 to i32*
	%tmp..11 = load i32, i32* %tmp..10
	br label %test.1_for.cond
test.1_for.cond:
	%i.d.x..1 = phi i32 [0, %test_entry], [%tmp..23, %test.2_for.body]
	%y = phi i32 [0, %test_entry], [%tmp..25, %test.2_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..11
	br i1 %ifres., label %test.2_for.body, label %test.3_for.end
test.2_for.body:
	%tmp..16 = call i8* @calloc(i32 1, i32 %tmp..3)
	%tmp..17 = bitcast i8* %tmp..16 to i32*
	store i32 3, i32* %tmp..17
	%tmp..18 = getelementptr i8, i8* %tmp..16, i32 4
	%tmp..19 = bitcast i8* %tmp..18 to i32*
	%tmp..20 = getelementptr i32, i32* %tmp..19, i32 %i.d.x..1
	%tmp..21 = load i32, i32* %tmp..20
	%tmp..23 = add i32 %i.d.x..1, 1
	%tmp..25 = add i32 %y, 1
	br label %test.1_for.cond
test.3_for.end:
	%tmp..29 = icmp ne i32 %y, 3
	br i1 %tmp..29, label %test.4_if.true, label %test.5_if.false
test.4_if.true:
	call void @error()
	ret void
test.5_if.false:
	br label %test.6_if.end
test.6_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	call void @test()
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
