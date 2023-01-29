@.str.str0 = private unnamed_addr constant [12 x i8] c"bla lab abl\00", align 1 ; --- Class G ---
%G = type { 
}
define void @G.constructor(%G* %this) {
	ret void
}

define void @G.foo(%G* %self, i8* %s) { 
G.foo_entry:
	call void @printString(i8* %s)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %G*
	call void @G.constructor(%G* %tmp..1)
	%tmp..3 = getelementptr [12 x i8], [12 x i8]* @.str.str0, i32 0, i32 0
	call void @G.foo(%G* %tmp..1, i8* %tmp..3)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

declare i8* @malloc(i32)
