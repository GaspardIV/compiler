
define i1 @bfalse() { 
bfalse_entry:
	ret i1 false
}

define i32 @main() { 
main_entry:
	%tmp. = call i1 @bfalse()
	br i1 %tmp., label %main.1_expr.true, label %main.4_or
main.4_or:
	br label %main.1_expr.true
main.1_expr.true:
	br label %main.3_expr.end
main.3_expr.end:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

