 ; --- Class X ---
%X = type { 
	i32; a 
}
define void @X.constructor(%X* %this) {
	%a = getelementptr %X, %X* %this, i32 0, i32 0
	store i32 0, i32* %a
	ret void
}

define i32 @main() { 
main_entry:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

