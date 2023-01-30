 ; --- Class X ---
@X.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%X*)* @X.foo to void (...)*) , ; foo 
	void (...)* bitcast (void (%X*, i32)* @X.bar to void (...)*) ; bar 
]

%X = type { 
	void (...)**; vtable
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @X.bar(%X* %self, i32 %x) { 
X.bar_entry:
	ret void
}

define void @X.foo(%X* %self) { 
X.foo_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 0
	%tmp..1 = load void (...)**, void (...)*** %tmp.
	%tmp..2 = getelementptr void (...)*, void (...)** %tmp..1, i32 1
	%tmp..3 = bitcast void (...)** %tmp..2 to void (%X*, i32)**
	%tmp..4 = load void (%X*, i32)*, void (%X*, i32)** %tmp..3
	call void %tmp..4(%X* %self, i32 42)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	%tmp..3 = getelementptr %X, %X* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%X*)**
	%tmp..7 = load void (%X*)*, void (%X*)** %tmp..6
	call void %tmp..7(%X* %tmp..1)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @malloc(i32)
