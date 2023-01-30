 ; --- Class Node ---
@Node.vtable = global [4 x void (...)*] [
	void (...)* bitcast (void (%Node*, i32)* @Node.setElem to void (...)*) , ; setElem 
	void (...)* bitcast (void (%Node*, %Node*)* @Node.setNext to void (...)*) , ; setNext 
	void (...)* bitcast (i32 (%Node*)* @Node.getElem to void (...)*) , ; getElem 
	void (...)* bitcast (%Node* (%Node*)* @Node.getNext to void (...)*) ; getNext 
]

%Node = type { 
	void (...)**; vtable
	,i32; elem 
	,%Node*; next 
	}
 ; --- Class Stack ---
@Stack.vtable = global [4 x void (...)*] [
	void (...)* bitcast (void (%Stack*, i32)* @Stack.push to void (...)*) , ; push 
	void (...)* bitcast (i1 (%Stack*)* @Stack.isEmpty to void (...)*) , ; isEmpty 
	void (...)* bitcast (i32 (%Stack*)* @Stack.top to void (...)*) , ; top 
	void (...)* bitcast (void (%Stack*)* @Stack.pop to void (...)*) ; pop 
]

%Stack = type { 
	void (...)**; vtable
	,%Node*; head 
	}
 ; --- Class Node methods ---
define void @Node.constructor(%Node* %this) {
	%this.class.vtable = bitcast [4 x void (...)*]* @Node.vtable to void (...)**
	%this.vtable = getelementptr %Node, %Node* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%elem = getelementptr %Node, %Node* %this, i32 0, i32 1
	store i32 0, i32* %elem
	%next = getelementptr %Node, %Node* %this, i32 0, i32 2
	%nexttmp = bitcast i32* null to %Node*
	store %Node* %nexttmp, %Node** %next
	ret void
}

define void @Node.setElem(%Node* %self, i32 %c) { 
Node.setElem_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	store i32 %c, i32* %tmp.
	ret void
}

define void @Node.setNext(%Node* %self, %Node* %n) { 
Node.setNext_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 2
	store %Node* %n, %Node** %tmp.
	ret void
}

define i32 @Node.getElem(%Node* %self) { 
Node.getElem_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define %Node* @Node.getNext(%Node* %self) { 
Node.getNext_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 2
	%tmp..1 = load %Node*, %Node** %tmp.
	ret %Node* %tmp..1
}
 ; --- Class Stack methods ---
define void @Stack.constructor(%Stack* %this) {
	%this.class.vtable = bitcast [4 x void (...)*]* @Stack.vtable to void (...)**
	%this.vtable = getelementptr %Stack, %Stack* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%head = getelementptr %Stack, %Stack* %this, i32 0, i32 1
	%headtmp = bitcast i32* null to %Node*
	store %Node* %headtmp, %Node** %head
	ret void
}

define void @Stack.pop(%Stack* %self) { 
Stack.pop_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..2 = load %Node*, %Node** %tmp.
	%tmp..3 = getelementptr %Node, %Node* %tmp..2, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 3
	%tmp..6 = bitcast void (...)** %tmp..5 to %Node* (%Node*)**
	%tmp..7 = load %Node* (%Node*)*, %Node* (%Node*)** %tmp..6
	%tmp..8 = call %Node* %tmp..7(%Node* %tmp..2)
	store %Node* %tmp..8, %Node** %tmp.
	ret void
}

define i32 @Stack.top(%Stack* %self) { 
Stack.top_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = getelementptr %Node, %Node* %tmp..1, i32 0, i32 0
	%tmp..3 = load void (...)**, void (...)*** %tmp..2
	%tmp..4 = getelementptr void (...)*, void (...)** %tmp..3, i32 2
	%tmp..5 = bitcast void (...)** %tmp..4 to i32 (%Node*)**
	%tmp..6 = load i32 (%Node*)*, i32 (%Node*)** %tmp..5
	%tmp..7 = call i32 %tmp..6(%Node* %tmp..1)
	ret i32 %tmp..7
}

define i1 @Stack.isEmpty(%Stack* %self) { 
Stack.isEmpty_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = bitcast i32* null to %Node*
	%tmp..3 = icmp eq %Node* %tmp..1, %tmp..2
	ret i1 %tmp..3
}

define void @Stack.push(%Stack* %self, i32 %c) { 
Stack.push_entry:
	%tmp. = call i8* @malloc(i32 96)
	%tmp..1 = bitcast i8* %tmp. to %Node*
	call void @Node.constructor(%Node* %tmp..1)
	%tmp..3 = getelementptr %Node, %Node* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%Node*, i32)**
	%tmp..7 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..6
	call void %tmp..7(%Node* %tmp..1, i32 %c)
	%tmp..10 = load void (...)**, void (...)*** %tmp..3
	%tmp..11 = getelementptr void (...)*, void (...)** %tmp..10, i32 1
	%tmp..12 = bitcast void (...)** %tmp..11 to void (%Node*, %Node*)**
	%tmp..13 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..12
	%tmp..14 = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..15 = load %Node*, %Node** %tmp..14
	call void %tmp..13(%Node* %tmp..1, %Node* %tmp..15)
	store %Node* %tmp..1, %Node** %tmp..14
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 64)
	%tmp..1 = bitcast i8* %tmp. to %Stack*
	call void @Stack.constructor(%Stack* %tmp..1)
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [0, %main_entry], [%tmp..15, %main.2_while.body]
	%tmp..7 = icmp slt i32 %i, 10
	br i1 %tmp..7, label %main.2_while.body, label %main.4_while.cond
main.2_while.body:
	%tmp..8 = getelementptr %Stack, %Stack* %tmp..1, i32 0, i32 0
	%tmp..9 = load void (...)**, void (...)*** %tmp..8
	%tmp..10 = getelementptr void (...)*, void (...)** %tmp..9, i32 0
	%tmp..11 = bitcast void (...)** %tmp..10 to void (%Stack*, i32)**
	%tmp..12 = load void (%Stack*, i32)*, void (%Stack*, i32)** %tmp..11
	call void %tmp..12(%Stack* %tmp..1, i32 %i)
	%tmp..15 = add i32 %i, 1
	br label %main.1_while.cond
main.4_while.cond:
	%tmp..23 = getelementptr %Stack, %Stack* %tmp..1, i32 0, i32 0
	%tmp..24 = load void (...)**, void (...)*** %tmp..23
	%tmp..25 = getelementptr void (...)*, void (...)** %tmp..24, i32 1
	%tmp..26 = bitcast void (...)** %tmp..25 to i1 (%Stack*)**
	%tmp..27 = load i1 (%Stack*)*, i1 (%Stack*)** %tmp..26
	%tmp..28 = call i1 %tmp..27(%Stack* %tmp..1)
	br i1 %tmp..28, label %main.6_while.end, label %main.5_while.body
main.5_while.body:
	%tmp..30 = load void (...)**, void (...)*** %tmp..23
	%tmp..31 = getelementptr void (...)*, void (...)** %tmp..30, i32 2
	%tmp..32 = bitcast void (...)** %tmp..31 to i32 (%Stack*)**
	%tmp..33 = load i32 (%Stack*)*, i32 (%Stack*)** %tmp..32
	%tmp..34 = call i32 %tmp..33(%Stack* %tmp..1)
	call void @printInt(i32 %tmp..34)
	%tmp..37 = load void (...)**, void (...)*** %tmp..23
	%tmp..38 = getelementptr void (...)*, void (...)** %tmp..37, i32 3
	%tmp..39 = bitcast void (...)** %tmp..38 to void (%Stack*)**
	%tmp..40 = load void (%Stack*)*, void (%Stack*)** %tmp..39
	call void %tmp..40(%Stack* %tmp..1)
	br label %main.4_while.cond
main.6_while.end:
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
