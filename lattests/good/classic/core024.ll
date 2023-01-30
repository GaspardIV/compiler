@.str.str0 = private unnamed_addr constant [5 x i8] c"NOOO\00", align 1@.str.str1 = private unnamed_addr constant [4 x i8] c"yes\00", align 1
define i1 @e() { 
e_entry:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret i1 false
}

define void @f(i32 %x, i32 %y) { 
f_entry:
	%tmp..3 = icmp sgt i32 %y, %x
	br i1 %tmp..3, label %f.1_if.true, label %f.4_or
f.4_or:
	%tmp..4 = call i1 @e()
	br i1 %tmp..4, label %f.1_if.true, label %f.2_if.false
f.1_if.true:
	%tmp..5 = getelementptr [4 x i8], [4 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..5)
	br label %f.3_if.end
f.2_if.false:
	br label %f.3_if.end
f.3_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	call void @f(i32 1, i32 2)
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

