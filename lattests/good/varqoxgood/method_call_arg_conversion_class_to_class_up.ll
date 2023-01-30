 ; --- Class G ---
@G.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%G*, %X*)* @G.foo to void (...)*) ; foo 
]

%G = type { 
	void (...)**; vtable
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
 ; --- Class G methods ---
define void @G.constructor(%G* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @G.vtable to void (...)**
	%this.vtable = getelementptr %G, %G* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @G.foo(%G* %self, %X* %a) { 
G.foo_entry:
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
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %G*
	call void @G.constructor(%G* %tmp..1)
	%tmp..3 = getelementptr %G, %G* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%G*, %X*)**
	%tmp..7 = load void (%G*, %X*)*, void (%G*, %X*)** %tmp..6
	%tmp..8 = call i8* @malloc(i32 0)
	%tmp..9 = bitcast i8* %tmp..8 to %Y*
	call void @Y.constructor(%Y* %tmp..9)
	%tmp..11 = bitcast %Y* %tmp..9 to %X*
	call void %tmp..7(%G* %tmp..1, %X* %tmp..11)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @malloc(i32)
