 ; --- Class A ---
@A.vtable = global [1 x void (...)*] [
	void (...)* bitcast (i32 (%A*, i8*, i1)* @A.foo to void (...)*) ; foo 
]

%A = type { 
	void (...)**; vtable
	}
 ; --- Class B ---
@B.vtable = global [1 x void (...)*] [
	void (...)* bitcast (i32 (%B*, i8*, i1)* @B.foo to void (...)*) ; foo 
]

%B = type { 
	void (...)**; vtable
	}
 ; --- Class A methods ---
define void @A.constructor(%A* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @A.vtable to void (...)**
	%this.vtable = getelementptr %A, %A* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define i32 @A.foo(%A* %self, i8* %x, i1 %y) { 
A.foo_entry:
	ret i32 42
}
 ; --- Class B methods ---
define void @B.constructor(%B* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @B.vtable to void (...)**
	%this.vtable = getelementptr %B, %B* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define i32 @B.foo(%B* %self, i8* %x, i1 %y) { 
B.foo_entry:
	ret i32 13
}

define i32 @main() { 
main_entry:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

