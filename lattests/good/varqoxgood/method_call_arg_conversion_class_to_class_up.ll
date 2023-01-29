 ; --- Class G ---
%G = type { 
}
define void @G.constructor(%G* %this) {
	ret void
}

define void @G.foo(%G* %self, %X* %a) { 
G.foo_entry:
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
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %G*
	call void @G.constructor(%G* %tmp..1)
	%tmp..3 = call i8* @malloc(i32 0)
	%tmp..4 = bitcast i8* %tmp..3 to %Y*
	call void @Y.constructor(%Y* %tmp..4)
	call void @G.foo(%G* %tmp..1, %Y* %tmp..4)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @malloc(i32)
