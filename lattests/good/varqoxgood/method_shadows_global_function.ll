 ; --- Class X ---
@X.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%X*)* @X.foo to void (...)*) ; foo 
]

%X = type { 
	void (...)**; vtable
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @X.foo(%X* %self) { 
X.foo_entry:
	ret void
}

define i32 @main() { 
main_entry:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

