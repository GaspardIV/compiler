 ; --- Class C ---
@C.vtable = global [0 x void (...)*] [
]

%C = type { 
	void (...)**; vtable
	,i32*; fld 
	}
 ; --- Class C methods ---
define void @C.constructor(%C* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @C.vtable to void (...)**
	%this.vtable = getelementptr %C, %C* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%fld = getelementptr %C, %C* %this, i32 0, i32 1
	%fldtmp = bitcast i32* null to i32*
	store i32* %fldtmp, i32** %fld
	ret void
}

define i32 @main() { 
main_entry:
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

declare i8* @calloc(i32, i32)
declare i8* @malloc(i32)
