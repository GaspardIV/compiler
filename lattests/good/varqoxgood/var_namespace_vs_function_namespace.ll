
define i32 @foo() { 
foo_entry:
	ret i32 42
}

define i32 @main() { 
main_entry:
	%tmp..5 = call i32 @foo()
	%tmp..6 = add i32 %tmp..5, 3
	%tmp..8 = icmp ne i32 %tmp..6, 45
	br i1 %tmp..8, label %main.1_if.true, label %main.2_if.end
main.1_if.true:
	call void @error()
	br label %main.2_if.end
main.2_if.end:
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

