 ; --- Class list ---
@list.vtable = global [0 x void (...)*] [
]

%list = type { 
	void (...)**; vtable
	,i32; elem 
	,%list*; next 
	}
 ; --- Class list methods ---
define void @list.constructor(%list* %this) {
	%this.class.vtable = bitcast [0 x void (...)*]* @list.vtable to void (...)**
	%this.vtable = getelementptr %list, %list* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%elem = getelementptr %list, %list* %this, i32 0, i32 1
	store i32 0, i32* %elem
	%next = getelementptr %list, %list* %this, i32 0, i32 2
	%nexttmp = bitcast i32* null to %list*
	store %list* %nexttmp, %list** %next
	ret void
}

define i32 @length2(%list* %xs) { 
length2_entry:
	br label %length2.1_while.cond
length2.1_while.cond:
	%res = phi i32 [0, %length2_entry], [%tmp..6, %length2.2_while.body]
	%xs.1 = phi %list* [%xs, %length2_entry], [%tmp..8, %length2.2_while.body]
	%tmp..3 = bitcast i32* null to %list*
	%tmp..4 = icmp ne %list* %xs.1, %tmp..3
	br i1 %tmp..4, label %length2.2_while.body, label %length2.3_while.end
length2.2_while.body:
	%tmp..6 = add i32 %res, 1
	%tmp..7 = getelementptr %list, %list* %xs.1, i32 0, i32 2
	%tmp..8 = load %list*, %list** %tmp..7
	br label %length2.1_while.cond
length2.3_while.end:
	ret i32 %res
}

define i32 @length(%list* %xs) { 
length_entry:
	%tmp..2 = bitcast i32* null to %list*
	%tmp..3 = icmp eq %list* %xs, %tmp..2
	br i1 %tmp..3, label %length.1_if.true, label %length.2_if.false
length.1_if.true:
	ret i32 0
length.2_if.false:
	%tmp..6 = getelementptr %list, %list* %xs, i32 0, i32 2
	%tmp..7 = load %list*, %list** %tmp..6
	%tmp..8 = call i32 @length(%list* %tmp..7)
	%tmp..9 = add i32 1, %tmp..8
	ret i32 %tmp..9
}

define i32 @main() { 
main_entry:
	%tmp..2 = call %list* @fromTo(i32 1, i32 50)
	%tmp..3 = call i32 @length(%list* %tmp..2)
	call void @printInt(i32 %tmp..3)
	%tmp..7 = call %list* @fromTo(i32 1, i32 100)
	%tmp..8 = call i32 @length2(%list* %tmp..7)
	call void @printInt(i32 %tmp..8)
	ret i32 0
}

define %list* @fromTo(i32 %m, i32 %n) { 
fromTo_entry:
	%tmp..1 = icmp sgt i32 %m, %n
	br i1 %tmp..1, label %fromTo.1_if.true, label %fromTo.2_if.false
fromTo.1_if.true:
	%tmp..2 = bitcast i32* null to %list*
	ret %list* %tmp..2
fromTo.2_if.false:
	%tmp..4 = add i32 %m, 1
	%tmp..5 = call %list* @fromTo(i32 %tmp..4, i32 %n)
	%tmp..6 = call %list* @cons(i32 %m, %list* %tmp..5)
	ret %list* %tmp..6
}

define %list* @cons(i32 %x, %list* %xs) { 
cons_entry:
	%tmp..1 = call i8* @malloc(i32 96)
	%tmp..2 = bitcast i8* %tmp..1 to %list*
	call void @list.constructor(%list* %tmp..2)
	%tmp..4 = getelementptr %list, %list* %tmp..2, i32 0, i32 1
	store i32 %x, i32* %tmp..4
	%tmp..5 = getelementptr %list, %list* %tmp..2, i32 0, i32 2
	store %list* %xs, %list** %tmp..5
	ret %list* %tmp..2
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
