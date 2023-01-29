 ; --- Class C ---
%C = type { 
	%Y*; fld 
}
define void @C.constructor(%C* %this) {
	%fld = getelementptr %C, %C* %this, i32 0, i32 0
	%fldtmp = bitcast i32* null to %Y*
	store %Y* %fldtmp, %Y** %fld
	ret void
}
 ; --- Class X ---
%X = type { 
}
define void @X.constructor(%X* %this) {
	ret void
}
 ; --- Class Y ---
%Y = type { 
}
define void @Y.constructor(%Y* %this) {
	ret void
}

define i32 @main() { 
main_entry:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @malloc(i32)
