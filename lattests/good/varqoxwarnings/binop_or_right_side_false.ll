
define i1 @bfalse() { 
bfalse_entry:
	ret i1 false
}

define i32 @main() { 
main_entry:
	%tmp. = call i1 @bfalse()
	br i1 %tmp., label %main.3_expr.end, label %main.2_expr.false
main.3_expr.end:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

