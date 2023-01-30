@.str.str0 = private unnamed_addr constant [1 x i8] c"\00", align 1 ; --- Class C ---
@C.vtable = global [0 x void (...)*] [
]

%C = type { 
	void (...)**; vtable
	,i8*; fld 
	}
 ; --- Class D ---
@D.vtable = global [0 x void (...)*] [
]

%D = type { 
	void (...)**; vtable
	,%C*; c 
	}
 ; --- Class C methods ---
define void @C.constructor(%C* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @C.vtable to void (...)**
	%this.vtable = getelementptr %C, %C* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%fld = getelementptr %C, %C* %this, i32 0, i32 1
	%fldtmp = getelementptr [1 x i8], [1 x i8]* @.str.str0, i32 0, i32 0
	store i8* %fldtmp, i8** %fld
	ret void
}
 ; --- Class D methods ---
define void @D.constructor(%D* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @D.vtable to void (...)**
	%this.vtable = getelementptr %D, %D* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%c = getelementptr %D, %D* %this, i32 0, i32 1
	%ctmp = bitcast i32* null to %C*
	store %C* %ctmp, %C** %c
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
