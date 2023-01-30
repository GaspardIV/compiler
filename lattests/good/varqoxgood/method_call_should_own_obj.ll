@.str.str1 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str0 = private unnamed_addr constant [9 x i8] c"ho ho ho\00", align 1 ; --- Class A ---
@A.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%A*, %B*)* @A.foo to void (...)*) ; foo 
]

%A = type { 
	void (...)**; vtable
	,i8*; x 
	}
 ; --- Class B ---
@B.vtable = global [0 x void (...)*] [
]

%B = type { 
	void (...)**; vtable
	,%A*; a 
	}
 ; --- Class A methods ---
define void @A.constructor(%A* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @A.vtable to void (...)**
	%this.vtable = getelementptr %A, %A* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %A, %A* %this, i32 0, i32 1
	%xtmp = getelementptr [1 x i8], [1 x i8]* @.str.str1, i32 0, i32 0
	store i8* %xtmp, i8** %x
	ret void
}

define void @A.foo(%A* %self, %B* %b) { 
A.foo_entry:
	%tmp. = getelementptr %B, %B* %b, i32 0, i32 1
	%tmp..1 = bitcast i32* null to %A*
	store %A* %tmp..1, %A** %tmp.
	%tmp..2 = getelementptr %A, %A* %self, i32 0, i32 1
	%tmp..3 = getelementptr [9 x i8], [9 x i8]* @.str.str0, i32 0, i32 0
	store i8* %tmp..3, i8** %tmp..2
	%tmp..5 = load i8*, i8** %tmp..2
	call void @printString(i8* %tmp..5)
	ret void
}
 ; --- Class B methods ---
define void @B.constructor(%B* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @B.vtable to void (...)**
	%this.vtable = getelementptr %B, %B* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%a = getelementptr %B, %B* %this, i32 0, i32 1
	%atmp = bitcast i32* null to %A*
	store %A* %atmp, %A** %a
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 64)
	%tmp..1 = bitcast i8* %tmp. to %B*
	call void @B.constructor(%B* %tmp..1)
	%tmp..3 = getelementptr %B, %B* %tmp..1, i32 0, i32 1
	%tmp..4 = call i8* @malloc(i32 64)
	%tmp..5 = bitcast i8* %tmp..4 to %A*
	call void @A.constructor(%A* %tmp..5)
	store %A* %tmp..5, %A** %tmp..3
	%tmp..8 = load %A*, %A** %tmp..3
	%tmp..9 = getelementptr %A, %A* %tmp..8, i32 0, i32 0
	%tmp..10 = load void (...)**, void (...)*** %tmp..9
	%tmp..11 = getelementptr void (...)*, void (...)** %tmp..10, i32 0
	%tmp..12 = bitcast void (...)** %tmp..11 to void (%A*, %B*)**
	%tmp..13 = load void (%A*, %B*)*, void (%A*, %B*)** %tmp..12
	call void %tmp..13(%A* %tmp..8, %B* %tmp..1)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

declare i8* @malloc(i32)
