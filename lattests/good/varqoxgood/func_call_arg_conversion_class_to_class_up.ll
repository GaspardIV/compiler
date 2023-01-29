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

define void @foo(%X* %a) { 
foo_entry:
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %Y*
	call void @Y.constructor(%Y* %tmp..1)
	call void @foo(%Y* %tmp..1)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @malloc(i32)
