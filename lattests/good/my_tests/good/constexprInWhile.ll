
define i32 @main() { 
main_entry:
	br label %main.1_while.cond
main.1_while.cond:
	br label %main.4_and
main.4_and:
	br label %main.5_and
main.5_and:
	br i1 false, label %main.2_while.body, label %main.6_or
main.6_or:
	br label %main.2_while.body
main.2_while.body:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

