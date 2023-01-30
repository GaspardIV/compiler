
define i32 @main() { 
main_entry:
	%tmp. = call i1 @btrue()
	br i1 %tmp., label %main.4_and, label %main.2_expr.false
main.4_and:
	br label %main.2_expr.false
main.2_expr.false:
	br label %main.3_expr.end
main.3_expr.end:
	ret i32 0
}

define i1 @btrue() { 
btrue_entry:
	ret i1 true
}


; ====================================================
; ====================================================
; ====================================================

