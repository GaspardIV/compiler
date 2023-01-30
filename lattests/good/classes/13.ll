 ; --- Class Counter ---
@Counter.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Counter*)* @Counter.incr to void (...)*) , ; incr 
	void (...)* bitcast (i32 (%Counter*)* @Counter.value to void (...)*) ; value 
]

%Counter = type { 
	void (...)**; vtable
	,i32; val 
	}
 ; --- Class Counter methods ---
define void @Counter.constructor(%Counter* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Counter.vtable to void (...)**
	%this.vtable = getelementptr %Counter, %Counter* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%val = getelementptr %Counter, %Counter* %this, i32 0, i32 1
	store i32 0, i32* %val
	ret void
}

define void @Counter.incr(%Counter* %self) { 
Counter.incr_entry:
	%tmp..2 = getelementptr %Counter, %Counter* %self, i32 0, i32 1
	%tmp..4 = load i32, i32* %tmp..2
	%tmp..6 = add i32 %tmp..4, 1
	store i32 %tmp..6, i32* %tmp..2
	%tmp..1 = load i32, i32* %tmp..2
	ret void
}

define i32 @Counter.value(%Counter* %self) { 
Counter.value_entry:
	%tmp. = getelementptr %Counter, %Counter* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i8* @malloc(i32 32)
	%tmp..2 = bitcast i8* %tmp..1 to %Counter*
	call void @Counter.constructor(%Counter* %tmp..2)
	%tmp..4 = getelementptr %Counter, %Counter* %tmp..2, i32 0, i32 0
	%tmp..5 = load void (...)**, void (...)*** %tmp..4
	%tmp..6 = getelementptr void (...)*, void (...)** %tmp..5, i32 0
	%tmp..7 = bitcast void (...)** %tmp..6 to void (%Counter*)**
	%tmp..8 = load void (%Counter*)*, void (%Counter*)** %tmp..7
	call void %tmp..8(%Counter* %tmp..2)
	%tmp..11 = load void (...)**, void (...)*** %tmp..4
	%tmp..12 = getelementptr void (...)*, void (...)** %tmp..11, i32 0
	%tmp..13 = bitcast void (...)** %tmp..12 to void (%Counter*)**
	%tmp..14 = load void (%Counter*)*, void (%Counter*)** %tmp..13
	call void %tmp..14(%Counter* %tmp..2)
	%tmp..17 = load void (...)**, void (...)*** %tmp..4
	%tmp..18 = getelementptr void (...)*, void (...)** %tmp..17, i32 0
	%tmp..19 = bitcast void (...)** %tmp..18 to void (%Counter*)**
	%tmp..20 = load void (%Counter*)*, void (%Counter*)** %tmp..19
	call void %tmp..20(%Counter* %tmp..2)
	%tmp..23 = load void (...)**, void (...)*** %tmp..4
	%tmp..24 = getelementptr void (...)*, void (...)** %tmp..23, i32 1
	%tmp..25 = bitcast void (...)** %tmp..24 to i32 (%Counter*)**
	%tmp..26 = load i32 (%Counter*)*, i32 (%Counter*)** %tmp..25
	%tmp..27 = call i32 %tmp..26(%Counter* %tmp..2)
	call void @printInt(i32 %tmp..27)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

@._dnl = internal constant [4 x i8] c"%d\0A\00"
declare i32 @printf(i8*, ...)
define void @printInt(i32 %x) {
       %t0 = getelementptr [4 x i8], [4 x i8]* @._dnl, i32 0, i32 0
       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)
       ret void
}

declare i8* @malloc(i32)
