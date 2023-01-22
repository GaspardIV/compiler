
define i32 @foo(i32 %x) { 
foo_entry:
	%tmp..3 = icmp ne i32 %x, 44
	br i1 %tmp..3, label %foo.1_if.true, label %foo.2_if.end
foo.1_if.true:
	call void @error()
	br label %foo.2_if.end
foo.2_if.end:
	ret i32 0
}

define i32 @main() { 
main_entry:
	%tmp..5 = call i32 @foo(i32 44)
	%tmp..12 = call i32 @foo(i32 44)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare void @exit(i32)
define void @error() {
entry:  call void @exit(i32 1)
	ret void
}

