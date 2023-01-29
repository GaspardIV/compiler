 ; --- Class IntQueue ---
%IntQueue = type { 
	%Node*, ; front 
	%Node*; rear 
}
define void @IntQueue.constructor(%IntQueue* %this) {
	%front = getelementptr %IntQueue, %IntQueue* %this, i32 0, i32 0
	%fronttmp = bitcast i32* null to %Node*
	store %Node* %fronttmp, %Node** %front
	%rear = getelementptr %IntQueue, %IntQueue* %this, i32 0, i32 1
	%reartmp = bitcast i32* null to %Node*
	store %Node* %reartmp, %Node** %rear
	ret void
}

define i32 @IntQueue.size(%IntQueue* %self) { 
IntQueue.size_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 0
	%tmp..1 = load %Node*, %Node** %tmp.
	br label %IntQueue.size.1_while.cond
IntQueue.size.1_while.cond:
	%n = phi %Node* [%tmp..1, %IntQueue.size_entry], [%tmp..7, %IntQueue.size.2_while.body]
	%res = phi i32 [0, %IntQueue.size_entry], [%tmp..9, %IntQueue.size.2_while.body]
	%tmp..5 = bitcast i32* null to %Node*
	%tmp..6 = icmp ne %Node* %n, %tmp..5
	br i1 %tmp..6, label %IntQueue.size.2_while.body, label %IntQueue.size.3_while.end
IntQueue.size.2_while.body:
	%tmp..7 = call %Node* @Node.getNext(%Node* %n)
	%tmp..9 = add i32 %res, 1
	br label %IntQueue.size.1_while.cond
IntQueue.size.3_while.end:
	ret i32 %res
}

define void @IntQueue.rmFirst(%IntQueue* %self) { 
IntQueue.rmFirst_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 0
	%tmp..2 = load %Node*, %Node** %tmp.
	%tmp..3 = call %Node* @Node.getNext(%Node* %tmp..2)
	store %Node* %tmp..3, %Node** %tmp.
	ret void
}

define i1 @IntQueue.isEmpty(%IntQueue* %self) { 
IntQueue.isEmpty_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 0
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = bitcast i32* null to %Node*
	%tmp..3 = icmp eq %Node* %tmp..1, %tmp..2
	ret i1 %tmp..3
}

define void @IntQueue.insert(%IntQueue* %self, i32 %x) { 
IntQueue.insert_entry:
	%tmp. = call i8* @malloc(i32 96)
	%tmp..1 = bitcast i8* %tmp. to %Node*
	call void @Node.constructor(%Node* %tmp..1)
	call void @Node.setElem(%Node* %tmp..1, i32 %x)
	%tmp..5 = call i1 @IntQueue.isEmpty(%IntQueue* %self)
	br i1 %tmp..5, label %IntQueue.insert.1_if.true, label %IntQueue.insert.2_if.false
IntQueue.insert.1_if.true:
	%tmp..6 = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 0
	store %Node* %tmp..1, %Node** %tmp..6
	br label %IntQueue.insert.3_if.end
IntQueue.insert.2_if.false:
	%tmp..7 = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 1
	%tmp..8 = load %Node*, %Node** %tmp..7
	call void @Node.setNext(%Node* %tmp..8, %Node* %tmp..1)
	br label %IntQueue.insert.3_if.end
IntQueue.insert.3_if.end:
	%tmp..10 = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 1
	store %Node* %tmp..1, %Node** %tmp..10
	ret void
}

define i32 @IntQueue.first(%IntQueue* %self) { 
IntQueue.first_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 0
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = call i32 @Node.getElem(%Node* %tmp..1)
	ret i32 %tmp..2
}
 ; --- Class Node ---
%Node = type { 
	i32, ; elem 
	%Node*; next 
}
define void @Node.constructor(%Node* %this) {
	%elem = getelementptr %Node, %Node* %this, i32 0, i32 0
	store i32 0, i32* %elem
	%next = getelementptr %Node, %Node* %this, i32 0, i32 1
	%nexttmp = bitcast i32* null to %Node*
	store %Node* %nexttmp, %Node** %next
	ret void
}

define void @Node.setElem(%Node* %self, i32 %e) { 
Node.setElem_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 0
	store i32 %e, i32* %tmp.
	ret void
}

define void @Node.setNext(%Node* %self, %Node* %n) { 
Node.setNext_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	store %Node* %n, %Node** %tmp.
	ret void
}

define i32 @Node.getElem(%Node* %self) { 
Node.getElem_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 0
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define %Node* @Node.getNext(%Node* %self) { 
Node.getNext_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	ret %Node* %tmp..1
}

define i32 @f(i32 %x) { 
f_entry:
	%tmp. = mul i32 %x, %x
	%tmp..2 = add i32 %tmp., 3
	ret i32 %tmp..2
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %IntQueue*
	call void @IntQueue.constructor(%IntQueue* %tmp..1)
	%tmp..4 = call i32 @f(i32 3)
	call void @IntQueue.insert(%IntQueue* %tmp..1, i32 %tmp..4)
	call void @IntQueue.insert(%IntQueue* %tmp..1, i32 5)
	call void @IntQueue.insert(%IntQueue* %tmp..1, i32 7)
	%tmp..10 = call i32 @IntQueue.first(%IntQueue* %tmp..1)
	call void @printInt(i32 %tmp..10)
	call void @IntQueue.rmFirst(%IntQueue* %tmp..1)
	%tmp..13 = call i32 @IntQueue.size(%IntQueue* %tmp..1)
	call void @printInt(i32 %tmp..13)
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
