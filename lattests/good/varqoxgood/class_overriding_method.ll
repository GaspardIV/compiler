 ; --- Class A ---
%A = type { 
}
define void @A.constructor(%A* %this) {
	ret void
}

define i32 @A.foo(%A* %self, i8* %x, i1 %y) { 
A.foo_entry:
	ret i32 42
}
 ; --- Class B ---
%B = type { 
}
define void @B.constructor(%B* %this) {
	ret void
}

define i32 @B.foo(%B* %self, i8* %x, i1 %y) { 
B.foo_entry:
	ret i32 13
}

define i32 @main() { 
main_entry:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

