@.str.str0 = private unnamed_addr constant [5 x i8] c"true\00", align 1@.str.str1 = private unnamed_addr constant [6 x i8] c"false\00", align 1
define void @b(i1 %a) { 
b_entry:
	br i1 %a, label %b.1_if.true, label %b.2_if.false
b.1_if.true:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %b.3_if.end
b.2_if.false:
	%tmp..2 = getelementptr [6 x i8], [6 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	br label %b.3_if.end
b.3_if.end:
	ret void
}

define i1 @t(i32 %a) { 
t_entry:
	%tmp. = call i1 @f(i32 %a)
	%tmp..1 = xor i1 1, %tmp.
	ret i1 %tmp..1
}

define i1 @f(i32 %a) { 
f_entry:
	call void @printInt(i32 %a)
	ret i1 false
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i1 @t(i32 1)
	br i1 %tmp..1, label %main.4_and, label %main.2_expr.false
main.4_and:
	%tmp..3 = call i1 @f(i32 2)
	br i1 %tmp..3, label %main.1_expr.true, label %main.2_expr.false
main.1_expr.true:
	br label %main.3_expr.end
main.2_expr.false:
	br label %main.3_expr.end
main.3_expr.end:
	%tmp..4 = phi i1 [true, %main.1_expr.true], [false, %main.2_expr.false]
	call void @b(i1 %tmp..4)
	%tmp..7 = call i1 @t(i32 3)
	br i1 %tmp..7, label %main.8_and, label %main.6_expr.false
main.8_and:
	%tmp..9 = call i1 @t(i32 4)
	br i1 %tmp..9, label %main.5_expr.true, label %main.6_expr.false
main.5_expr.true:
	br label %main.7_expr.end
main.6_expr.false:
	br label %main.7_expr.end
main.7_expr.end:
	%tmp..10 = phi i1 [true, %main.5_expr.true], [false, %main.6_expr.false]
	call void @b(i1 %tmp..10)
	%tmp..13 = call i1 @t(i32 5)
	br i1 %tmp..13, label %main.9_expr.true, label %main.12_or
main.12_or:
	%tmp..15 = call i1 @t(i32 6)
	br i1 %tmp..15, label %main.9_expr.true, label %main.10_expr.false
main.9_expr.true:
	br label %main.11_expr.end
main.10_expr.false:
	br label %main.11_expr.end
main.11_expr.end:
	%tmp..16 = phi i1 [true, %main.9_expr.true], [false, %main.10_expr.false]
	call void @b(i1 %tmp..16)
	%tmp..19 = call i1 @f(i32 7)
	br i1 %tmp..19, label %main.16_and, label %main.14_expr.false
main.16_and:
	%tmp..21 = call i1 @t(i32 8)
	br i1 %tmp..21, label %main.13_expr.true, label %main.14_expr.false
main.13_expr.true:
	br label %main.15_expr.end
main.14_expr.false:
	br label %main.15_expr.end
main.15_expr.end:
	%tmp..22 = phi i1 [true, %main.13_expr.true], [false, %main.14_expr.false]
	call void @b(i1 %tmp..22)
	%tmp..25 = call i1 @t(i32 9)
	br i1 %tmp..25, label %main.20_and, label %main.18_expr.false
main.20_and:
	%tmp..27 = call i1 @t(i32 10)
	br i1 %tmp..27, label %main.21_and, label %main.18_expr.false
main.21_and:
	%tmp..29 = call i1 @t(i32 11)
	br i1 %tmp..29, label %main.17_expr.true, label %main.18_expr.false
main.17_expr.true:
	br label %main.19_expr.end
main.18_expr.false:
	br label %main.19_expr.end
main.19_expr.end:
	%tmp..30 = phi i1 [true, %main.17_expr.true], [false, %main.18_expr.false]
	call void @b(i1 %tmp..30)
	%tmp..33 = call i1 @f(i32 12)
	br i1 %tmp..33, label %main.22_expr.true, label %main.25_or
main.25_or:
	%tmp..35 = call i1 @f(i32 13)
	br i1 %tmp..35, label %main.26_and, label %main.23_expr.false
main.26_and:
	%tmp..37 = call i1 @t(i32 14)
	br i1 %tmp..37, label %main.22_expr.true, label %main.23_expr.false
main.22_expr.true:
	br label %main.24_expr.end
main.23_expr.false:
	br label %main.24_expr.end
main.24_expr.end:
	%tmp..38 = phi i1 [true, %main.22_expr.true], [false, %main.23_expr.false]
	call void @b(i1 %tmp..38)
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

