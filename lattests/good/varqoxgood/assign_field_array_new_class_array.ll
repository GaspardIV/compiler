@.str.str0 = private unnamed_addr constant [1 x i8] c"\00", align 1 ; --- Class C ---
%C = type { 
	i32*; fld 
}
define void @C.constructor(%C* %this) {
	%fld = getelementptr %C, %C* %this, i32 0, i32 0
	%fldtmp = getelementptr [1 x i8], [1 x i8]* @.str.str0, i32 0, i32 0
	store i8* %fldtmp, i8** %fld
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
