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

declare i8* @calloc(i32, i32)
declare i8* @malloc(i32)
