
define i32 @foo(i32 %one, i32 %two, i32 %three, i32 %four, i32 %five, i32 %six, i32 %seven, i32 %eight, i32 %nine) { 
foo_entry:
	%tmp. = add i32 %one, %two
	%tmp..1 = add i32 %tmp., %three
	%tmp..2 = add i32 %tmp..1, %four
	%tmp..3 = add i32 %tmp..2, %five
	%tmp..4 = add i32 %tmp..3, %six
	%tmp..5 = add i32 %tmp..4, %seven
	%tmp..6 = add i32 %tmp..5, %eight
	%tmp..7 = add i32 %tmp..6, %nine
	ret i32 %tmp..7
}

define i32 @main() { 
main_entry:
	call void @printInt(i32 521)
	%tmp..18 = call i32 @foo(i32 42, i32 17, i32 -1, i32 59, i32 101, i32 101, i32 202, i32 1000000, i32 -1000000)
	call void @printInt(i32 %tmp..18)
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

