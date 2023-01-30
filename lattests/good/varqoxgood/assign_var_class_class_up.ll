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
