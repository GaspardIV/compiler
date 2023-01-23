 ; --- Class A ---
%A = type { 
	i32; x 
}
define void @A.constructor(%A* %this) {
	%x = getelementptr %A, %A* %this, i32 0, i32 0
	store i32 0, i32* %x
	ret void
}

define i32 @main() { 
main_entry:
	ret i32 42
}


; ====================================================
; ====================================================
; ====================================================

