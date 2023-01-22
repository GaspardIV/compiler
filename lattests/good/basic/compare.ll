@.str.str0 = private unnamed_addr constant [2 x i8] c"4\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"6\00", align 1
define i32 @main() { 
main_entry:
	br label %main.1_if.true
main.1_if.true:
	%tmp..3 = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..3)
	br label %main.2_if.end
main.2_if.end:
	br label %main.3_if.true
main.3_if.true:
	%tmp..8 = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..8)
	br label %main.4_if.end
main.4_if.end:
	br label %main.9_if.true
main.9_if.true:
	%tmp..19 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..19)
	br label %main.10_if.end
main.10_if.end:
	br label %main.11_if.true
main.11_if.true:
	%tmp..24 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..24)
	br label %main.12_if.end
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

