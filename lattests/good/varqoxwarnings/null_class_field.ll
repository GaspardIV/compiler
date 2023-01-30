 ; --- Class X ---
@X.vtable = global [0 x void (...)*] [
]

%X = type { 
	void (...)**; vtable
	,i32; a 
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%a = getelementptr %X, %X* %this, i32 0, i32 1
	store i32 0, i32* %a
	ret void
}

define i32 @main() { 
main_entry:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

