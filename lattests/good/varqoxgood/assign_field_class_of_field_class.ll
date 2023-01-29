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
 ; --- Class D ---
%D = type { 
	%C*; c 
}
define void @D.constructor(%D* %this) {
	%c = getelementptr %D, %D* %this, i32 0, i32 0
	%ctmp = bitcast i32* null to %C*
	store %C* %ctmp, %C** %c
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
