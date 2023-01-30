 ; --- Class X ---
@X.vtable = global [0 x void (...)*] [
]

%X = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %X, %X* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define i32 @incr_x(%X* %x) { 
incr_x_entry:
	%tmp..2 = getelementptr %X, %X* %x, i32 0, i32 1
	%tmp..4 = load i32, i32* %tmp..2
	%tmp..6 = add i32 %tmp..4, 1
	store i32 %tmp..6, i32* %tmp..2
	%tmp..1 = load i32, i32* %tmp..2
	%tmp..8 = load i32, i32* %tmp..2
	ret i32 %tmp..8
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	br label %main.1_while.cond
main.1_while.cond:
	%tmp..7 = getelementptr %X, %X* %tmp..1, i32 0, i32 1
	%tmp..8 = load i32, i32* %tmp..7
	%tmp..10 = icmp slt i32 %tmp..8, 10
	br i1 %tmp..10, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..11 = call i32 @incr_x(%X* %tmp..1)
	br label %main.1_while.cond
main.3_while.end:
	%tmp..17 = load i32, i32* %tmp..7
	%tmp..19 = icmp ne i32 %tmp..17, 10
	br i1 %tmp..19, label %main.4_if.true, label %main.5_if.false
main.4_if.true:
	ret i32 0
main.5_if.false:
	br label %main.6_if.end
main.6_if.end:
	br label %main.7_while.cond
main.7_while.cond:
	%tmp..26 = load i32, i32* %tmp..7
	%tmp..28 = icmp slt i32 %tmp..26, 20
	br i1 %tmp..28, label %main.8_while.body, label %main.9_while.end
main.8_while.body:
	%tmp..31 = load i32, i32* %tmp..7
	%tmp..33 = add i32 %tmp..31, 7
	store i32 %tmp..33, i32* %tmp..7
	br label %main.7_while.cond
main.9_while.end:
	%tmp..39 = load i32, i32* %tmp..7
	%tmp..41 = icmp ne i32 %tmp..39, 24
	br i1 %tmp..41, label %main.10_if.true, label %main.11_if.false
main.10_if.true:
	ret i32 0
main.11_if.false:
	br label %main.12_if.end
main.12_if.end:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @malloc(i32)
