@.str.str0 = private unnamed_addr constant [5 x i8] c"true\00", align 1@.str.str1 = private unnamed_addr constant [6 x i8] c"false\00", align 1
define i1 @dontCallMe(i32 %x) { 
dontCallMe_entry:
	call void @printInt(i32 %x)
	ret i1 true
}

define i32 @main() { 
main_entry:
	br label %main.1_if.true
main.1_if.true:
	call void @printBool(i1 true)
	br label %main.3_if.end
main.3_if.end:
	br i1 true, label %main.4_expr.true, label %main.7_or
main.7_or:
	%tmp..15 = call i1 @dontCallMe(i32 1)
	br i1 %tmp..15, label %main.4_expr.true, label %main.5_expr.false
main.4_expr.true:
	br label %main.6_expr.end
main.5_expr.false:
	br label %main.6_expr.end
main.6_expr.end:
	%tmp..16 = phi i1 [true, %main.4_expr.true], [false, %main.5_expr.false]
	call void @printBool(i1 %tmp..16)
	br i1 false, label %main.11_and, label %main.9_expr.false
main.11_and:
	%tmp..23 = call i1 @dontCallMe(i32 2)
	br i1 %tmp..23, label %main.8_expr.true, label %main.9_expr.false
main.8_expr.true:
	br label %main.10_expr.end
main.9_expr.false:
	br label %main.10_expr.end
main.10_expr.end:
	%tmp..24 = phi i1 [true, %main.8_expr.true], [false, %main.9_expr.false]
	call void @printBool(i1 %tmp..24)
	br i1 true, label %main.15_and, label %main.13_expr.false
main.15_and:
	br i1 true, label %main.16_and, label %main.13_expr.false
main.16_and:
	br label %main.12_expr.true
main.12_expr.true:
	br label %main.14_expr.end
main.13_expr.false:
	br label %main.14_expr.end
main.14_expr.end:
	%tmp..32 = phi i1 [true, %main.12_expr.true], [false, %main.13_expr.false]
	call void @printBool(i1 %tmp..32)
	%tmp..36 = call i1 @implies(i1 false, i1 false)
	call void @printBool(i1 %tmp..36)
	%tmp..40 = call i1 @implies(i1 false, i1 true)
	call void @printBool(i1 %tmp..40)
	%tmp..44 = call i1 @implies(i1 true, i1 false)
	call void @printBool(i1 %tmp..44)
	%tmp..48 = call i1 @implies(i1 true, i1 true)
	call void @printBool(i1 %tmp..48)
	ret i32 0
}

define void @printBool(i1 %b) { 
printBool_entry:
	br i1 %b, label %printBool.1_if.true, label %printBool.2_if.false
printBool.1_if.true:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %printBool.3_if.end
printBool.2_if.false:
	%tmp..2 = getelementptr [6 x i8], [6 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	br label %printBool.3_if.end
printBool.3_if.end:
	ret void
}

define i1 @implies(i1 %x, i1 %y) { 
implies_entry:
	br i1 %x, label %implies.4_or, label %implies.1_expr.true
implies.4_or:
	%tmp. = icmp eq i1 %x, %y
	br i1 %tmp., label %implies.1_expr.true, label %implies.2_expr.false
implies.1_expr.true:
	br label %implies.3_expr.end
implies.2_expr.false:
	br label %implies.3_expr.end
implies.3_expr.end:
	%tmp..1 = phi i1 [true, %implies.1_expr.true], [false, %implies.2_expr.false]
	ret i1 %tmp..1
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

