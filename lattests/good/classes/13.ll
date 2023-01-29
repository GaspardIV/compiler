 ; --- Class Counter ---
@Counter.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Counter*)* @Counter.incr to void (...)*) , ; incr 
	void (...)* bitcast (i32 (%Counter*)* @Counter.value to void (...)*) ; value 
]

%Counter = type { 
	void (...)**; vtable
	,i32; val 
	}
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
	call void @Counter.incr(%Counter* %tmp..2)
	call void @Counter.incr(%Counter* %tmp..2)
	call void @Counter.incr(%Counter* %tmp..2)
	%tmp..7 = call i32 @Counter.value(%Counter* %tmp..2)
	call void @printInt(i32 %tmp..7)
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
