
define i32 @foo(i32 %x, i32 %y) { 
foo_entry:
	%tmp. = add i32 %x, %y
	%tmp..4 = icmp sgt i32 %x, 0
	br i1 %tmp..4, label %foo.1_if.true, label %foo.2_if.end
foo.1_if.true:
	br label %foo.2_if.end
foo.2_if.end:
	%a = phi i32 [%tmp., %foo_entry], [1, %foo.1_if.true]
	ret i32 %a
}

define i32 @main() { 
main_entry:
	%tmp..2 = call i32 @foo(i32 5, i32 6)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

