
define i1 @bfalse() { 
bfalse_entry:
	ret i1 false
}

define i32 @main() { 
main_entry:
	%tmp. = call i1 @bfalse()
	br label %main.3_expr.end
main.3_expr.end:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

