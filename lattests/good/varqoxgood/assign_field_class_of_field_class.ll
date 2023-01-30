 ; --- Class C ---
@C.vtable = global [0 x void (...)*] [
]

%C = type { 
	void (...)**; vtable
	,%Y*; fld 
	}
 ; --- Class D ---
@D.vtable = global [0 x void (...)*] [
]

%D = type { 
	void (...)**; vtable
	,%C*; c 
	}
 ; --- Class X ---
@X.vtable = global [0 x void (...)*] [
]

%X = type { 
	void (...)**; vtable
	}
 ; --- Class Y ---
@Y.vtable = global [0 x void (...)*] [
]

%Y = type { 
	void (...)**; vtable
	}
 ; --- Class C methods ---
define void @C.constructor(%C* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @C.vtable to void (...)**
	%this.vtable = getelementptr %C, %C* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%fld = getelementptr %C, %C* %this, i32 0, i32 1
	%fldtmp = bitcast i32* null to %Y*
	store %Y* %fldtmp, %Y** %fld
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
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}
 ; --- Class Y methods ---
define void @Y.constructor(%Y* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @Y.vtable to void (...)**
	%this.vtable = getelementptr %Y, %Y* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
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
