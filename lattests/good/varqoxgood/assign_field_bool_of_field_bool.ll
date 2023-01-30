 ; --- Class C ---
@C.vtable = global [0 x void (...)*] [
]

%C = type { 
	void (...)**; vtable
	,i1; fld 
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
	store i1 false, i1* %fld
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
