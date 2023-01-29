 ; --- Class Node ---
@Node.vtable = global [4 x void (...)*] [
	void (...)* bitcast (void (%Node*, i32)* @Node.setElem to void (...)*) , ; setElem 
	void (...)* bitcast (void (%Node*, %Node*)* @Node.setNext to void (...)*) , ; setNext 
	void (...)* bitcast (i32 (%Node*)* @Node.getElem to void (...)*) , ; getElem 
	void (...)* bitcast (%Node* (%Node*)* @Node.getNext to void (...)*) ; getNext 
]

%Node = type { 
	void (...)**,
	i32; elem 
,
	%Node*; next 
}
define void @Node.constructor(%Node* %this) {
	%this.vtable = bitcast [4 x void (...)*]* @Node.vtable to void (...)**
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
 ; --- Class Stack ---
@Stack.vtable = global [4 x void (...)*] [
	void (...)* bitcast (void (%Stack*, i32)* @Stack.push to void (...)*) , ; push 
	void (...)* bitcast (i1 (%Stack*)* @Stack.isEmpty to void (...)*) , ; isEmpty 
	void (...)* bitcast (i32 (%Stack*)* @Stack.top to void (...)*) , ; top 
	void (...)* bitcast (void (%Stack*)* @Stack.pop to void (...)*) ; pop 
]

%Stack = type { 
	void (...)**,
	%Node*; head 
}
define void @Stack.constructor(%Stack* %this) {
	%this.vtable = bitcast [4 x void (...)*]* @Stack.vtable to void (...)**
	%head = getelementptr %Stack, %Stack* %this, i32 0, i32 1
	%headtmp = bitcast i32* null to %Node*
	store %Node* %headtmp, %Node** %head
	ret void
}

define void @Stack.pop(%Stack* %self) { 
Stack.pop_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..2 = load %Node*, %Node** %tmp.
	%tmp..3 = call %Node* @Node.getNext(%Node* %tmp..2)
	store %Node* %tmp..3, %Node** %tmp.
	ret void
}

define i32 @Stack.top(%Stack* %self) { 
Stack.top_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = call i32 @Node.getElem(%Node* %tmp..1)
	ret i32 %tmp..2
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
	call void @Node.setElem(%Node* %tmp..1, i32 %c)
	%tmp..4 = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..5 = load %Node*, %Node** %tmp..4
	call void @Node.setNext(%Node* %tmp..1, %Node* %tmp..5)
	store %Node* %tmp..1, %Node** %tmp..4
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 64)
	%tmp..1 = bitcast i8* %tmp. to %Stack*
	call void @Stack.constructor(%Stack* %tmp..1)
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [0, %main_entry], [%tmp..10, %main.2_while.body]
	%tmp..7 = icmp slt i32 %i, 10
	br i1 %tmp..7, label %main.2_while.body, label %main.4_while.cond
main.2_while.body:
	call void @Stack.push(%Stack* %tmp..1, i32 %i)
	%tmp..10 = add i32 %i, 1
	br label %main.1_while.cond
main.4_while.cond:
	%tmp..13 = call i1 @Stack.isEmpty(%Stack* %tmp..1)
	br i1 %tmp..13, label %main.6_while.end, label %main.5_while.body
main.5_while.body:
	%tmp..14 = call i32 @Stack.top(%Stack* %tmp..1)
	call void @printInt(i32 %tmp..14)
	call void @Stack.pop(%Stack* %tmp..1)
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
