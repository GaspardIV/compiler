 ; --- Class A ---
%A = type { 
	i32; x 
}
define void @A.constructor(%A* %this) {
	%x = getelementptr %A, %A* %this, i32 0, i32 0
	store i32 0, i32* %x
	ret void
}

define void @A.foo(%A* %self) { 
A.foo_entry:
	%tmp. = getelementptr %A, %A* %self, i32 0, i32 0
	%tmp..1 = load i32, i32* %tmp.
	ret void
}
 ; --- Class B ---
%B = type { 
	i32; x 
}
define void @B.constructor(%B* %this) {
	%x = getelementptr %B, %B* %this, i32 0, i32 0
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

