 ; --- Class list ---
@list.vtable = global [0 x void (...)*] [
]

%list = type { 
	void (...)**; vtable
	}
 ; --- Class list methods ---
define void @list.constructor(%list* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @list.vtable to void (...)**
	%this.vtable = getelementptr %list, %list* %this, i32 0, i32 0
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

