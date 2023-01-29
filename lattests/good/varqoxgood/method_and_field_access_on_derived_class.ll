 ; --- Class X ---
%X = type { 
}
define void @X.constructor(%X* %this) {
	ret void
}
 ; --- Class Y ---
%Y = type { 
	i32; x 
}
define void @Y.constructor(%Y* %this) {
	%x = getelementptr %Y, %Y* %this, i32 0, i32 0
	store i32 0, i32* %x
	ret void
}

define void @Y.foo(%Y* %self) { 
Y.foo_entry:
	%tmp. = getelementptr %Y, %Y* %self, i32 0, i32 0
	%tmp..1 = load i32, i32* %tmp.
	call void @printInt(i32 %tmp..1)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %Y*
	call void @Y.constructor(%Y* %tmp..1)
	%tmp..3 = getelementptr %Y, %Y* %tmp..1, i32 0, i32 0
	store i32 142, i32* %tmp..3
	call void @Y.foo(%Y* %tmp..1)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

@._dnl = internal constant [4 x i8] c"%d\0A\00"
declare i32 @printf(i8*, ...)
define void @printInt(i32 %x) {
       %t0 = getelementptr [4 x i8], [4 x i8]* @._dnl, i32 0, i32 0
       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)
       ret void
}

declare i8* @malloc(i32)
