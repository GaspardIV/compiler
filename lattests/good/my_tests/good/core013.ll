@.str.str0 = private unnamed_addr constant [3 x i8] c"&&\00", align 1@.str.str1 = private unnamed_addr constant [3 x i8] c"||\00", align 1@.str.str2 = private unnamed_addr constant [2 x i8] c"!\00", align 1@.str.str3 = private unnamed_addr constant [6 x i8] c"false\00", align 1@.str.str4 = private unnamed_addr constant [5 x i8] c"true\00", align 1
define i1 @test(i32 %i) { 
test_entry:
	call void @printInt(i32 %i)
	%tmp..2 = icmp sgt i32 %i, 0
	ret i1 %tmp..2
}

define i32 @main() { 
main_entry:
	%tmp. = getelementptr [3 x i8], [3 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..4 = call i1 @test(i32 -1)
	br i1 %tmp..4, label %main.4_and, label %main.2_expr.false
main.4_and:
	%tmp..6 = call i1 @test(i32 0)
	br i1 %tmp..6, label %main.1_expr.true, label %main.2_expr.false
main.1_expr.true:
	br label %main.3_expr.end
main.2_expr.false:
	br label %main.3_expr.end
main.3_expr.end:
	%tmp..7 = phi i1 [true, %main.1_expr.true], [false, %main.2_expr.false]
	call void @printBool(i1 %tmp..7)
	%tmp..11 = call i1 @test(i32 -2)
	br i1 %tmp..11, label %main.8_and, label %main.6_expr.false
main.8_and:
	%tmp..13 = call i1 @test(i32 1)
	br i1 %tmp..13, label %main.5_expr.true, label %main.6_expr.false
main.5_expr.true:
	br label %main.7_expr.end
main.6_expr.false:
	br label %main.7_expr.end
main.7_expr.end:
	%tmp..14 = phi i1 [true, %main.5_expr.true], [false, %main.6_expr.false]
	call void @printBool(i1 %tmp..14)
	%tmp..17 = call i1 @test(i32 3)
	br i1 %tmp..17, label %main.12_and, label %main.10_expr.false
main.12_and:
	%tmp..20 = call i1 @test(i32 -5)
	br i1 %tmp..20, label %main.9_expr.true, label %main.10_expr.false
main.9_expr.true:
	br label %main.11_expr.end
main.10_expr.false:
	br label %main.11_expr.end
main.11_expr.end:
	%tmp..21 = phi i1 [true, %main.9_expr.true], [false, %main.10_expr.false]
	call void @printBool(i1 %tmp..21)
	%tmp..24 = call i1 @test(i32 234234)
	br i1 %tmp..24, label %main.16_and, label %main.14_expr.false
main.16_and:
	%tmp..26 = call i1 @test(i32 21321)
	br i1 %tmp..26, label %main.13_expr.true, label %main.14_expr.false
main.13_expr.true:
	br label %main.15_expr.end
main.14_expr.false:
	br label %main.15_expr.end
main.15_expr.end:
	%tmp..27 = phi i1 [true, %main.13_expr.true], [false, %main.14_expr.false]
	call void @printBool(i1 %tmp..27)
	%tmp..29 = getelementptr [3 x i8], [3 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..29)
	%tmp..33 = call i1 @test(i32 -1)
	br i1 %tmp..33, label %main.17_expr.true, label %main.20_or
main.20_or:
	%tmp..35 = call i1 @test(i32 0)
	br i1 %tmp..35, label %main.17_expr.true, label %main.18_expr.false
main.17_expr.true:
	br label %main.19_expr.end
main.18_expr.false:
	br label %main.19_expr.end
main.19_expr.end:
	%tmp..36 = phi i1 [true, %main.17_expr.true], [false, %main.18_expr.false]
	call void @printBool(i1 %tmp..36)
	%tmp..40 = call i1 @test(i32 -2)
	br i1 %tmp..40, label %main.21_expr.true, label %main.24_or
main.24_or:
	%tmp..42 = call i1 @test(i32 1)
	br i1 %tmp..42, label %main.21_expr.true, label %main.22_expr.false
main.21_expr.true:
	br label %main.23_expr.end
main.22_expr.false:
	br label %main.23_expr.end
main.23_expr.end:
	%tmp..43 = phi i1 [true, %main.21_expr.true], [false, %main.22_expr.false]
	call void @printBool(i1 %tmp..43)
	%tmp..46 = call i1 @test(i32 3)
	br i1 %tmp..46, label %main.25_expr.true, label %main.28_or
main.28_or:
	%tmp..49 = call i1 @test(i32 -5)
	br i1 %tmp..49, label %main.25_expr.true, label %main.26_expr.false
main.25_expr.true:
	br label %main.27_expr.end
main.26_expr.false:
	br label %main.27_expr.end
main.27_expr.end:
	%tmp..50 = phi i1 [true, %main.25_expr.true], [false, %main.26_expr.false]
	call void @printBool(i1 %tmp..50)
	%tmp..53 = call i1 @test(i32 234234)
	br i1 %tmp..53, label %main.29_expr.true, label %main.32_or
main.32_or:
	%tmp..55 = call i1 @test(i32 21321)
	br i1 %tmp..55, label %main.29_expr.true, label %main.30_expr.false
main.29_expr.true:
	br label %main.31_expr.end
main.30_expr.false:
	br label %main.31_expr.end
main.31_expr.end:
	%tmp..56 = phi i1 [true, %main.29_expr.true], [false, %main.30_expr.false]
	call void @printBool(i1 %tmp..56)
	%tmp..58 = getelementptr [2 x i8], [2 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp..58)
	call void @printBool(i1 true)
	call void @printBool(i1 false)
	ret i32 0
}

define void @printBool(i1 %b) { 
printBool_entry:
	br i1 %b, label %printBool.2_if.false, label %printBool.1_if.true
printBool.1_if.true:
	%tmp..1 = getelementptr [6 x i8], [6 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp..1)
	br label %printBool.3_if.end
printBool.2_if.false:
	%tmp..3 = getelementptr [5 x i8], [5 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp..3)
	br label %printBool.3_if.end
printBool.3_if.end:
	ret void
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

