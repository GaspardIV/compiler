@.str.str0 = private unnamed_addr constant [5 x i8] c"true\00", align 1@.str.str1 = private unnamed_addr constant [6 x i8] c"false\00", align 1
define void @foo(i1 %b) { 
foo_entry:
	br i1 %b, label %foo.1_if.true, label %foo.2_if.false
foo.1_if.true:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %foo.3_if.end
foo.2_if.false:
	%tmp..2 = getelementptr [6 x i8], [6 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	br label %foo.3_if.end
foo.3_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	call void @foo(i1 true)
	call void @foo(i1 false)
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

