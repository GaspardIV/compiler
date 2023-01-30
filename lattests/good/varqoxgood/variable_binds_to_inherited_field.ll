 ; --- Class A ---
@A.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%A*)* @A.foo to void (...)*) ; foo 
]

%A = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class B ---
@B.vtable = global [0 x void (...)*] [
]

%B = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class A methods ---
define void @A.constructor(%A* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @A.vtable to void (...)**
	%this.vtable = getelementptr %A, %A* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %A, %A* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define void @A.foo(%A* %self) { 
A.foo_entry:
	%tmp. = getelementptr %A, %A* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret void
}
 ; --- Class B methods ---
define void @B.constructor(%B* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @B.vtable to void (...)**
	%this.vtable = getelementptr %B, %B* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %B, %B* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define i32 @main() { 
main_entry:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

