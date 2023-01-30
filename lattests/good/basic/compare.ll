@.str.str0 = private unnamed_addr constant [2 x i8] c"4\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"6\00", align 1
define i32 @main() { 
main_entry:
	br label %main.1_if.true
main.1_if.true:
	%tmp..3 = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..3)
	br label %main.3_if.end
main.3_if.end:
	br label %main.4_if.true
main.4_if.true:
	call void @printString(i8* %tmp..3)
	br label %main.6_if.end
main.6_if.end:
	br label %main.13_if.true
main.13_if.true:
	%tmp..19 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..19)
	br label %main.15_if.end
main.15_if.end:
	br label %main.16_if.true
main.16_if.true:
	call void @printString(i8* %tmp..19)
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

