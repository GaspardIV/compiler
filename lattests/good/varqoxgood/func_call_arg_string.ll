@.str.str0 = private unnamed_addr constant [12 x i8] c"bla lab abl\00", align 1
define void @foo(i8* %s) { 
foo_entry:
	call void @printString(i8* %s)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = getelementptr [12 x i8], [12 x i8]* @.str.str0, i32 0, i32 0
	call void @foo(i8* %tmp.)
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

