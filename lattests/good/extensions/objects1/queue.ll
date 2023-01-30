 ; --- Class IntQueue ---
@IntQueue.vtable = global [5 x void (...)*] [
	void (...)* bitcast (i1 (%IntQueue*)* @IntQueue.isEmpty to void (...)*) , ; isEmpty 
	void (...)* bitcast (void (%IntQueue*, i32)* @IntQueue.insert to void (...)*) , ; insert 
	void (...)* bitcast (i32 (%IntQueue*)* @IntQueue.first to void (...)*) , ; first 
	void (...)* bitcast (void (%IntQueue*)* @IntQueue.rmFirst to void (...)*) , ; rmFirst 
	void (...)* bitcast (i32 (%IntQueue*)* @IntQueue.size to void (...)*) ; size 
]

%IntQueue = type { 
	void (...)**; vtable
	,%Node*; front 
	,%Node*; rear 
	}
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
 ; --- Class IntQueue methods ---
define void @IntQueue.constructor(%IntQueue* %this) {
	%this.class.vtable = bitcast [5 x void (...)*]* @IntQueue.vtable to void (...)**
	%this.vtable = getelementptr %IntQueue, %IntQueue* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%front = getelementptr %IntQueue, %IntQueue* %this, i32 0, i32 1
	%fronttmp = bitcast i32* null to %Node*
	store %Node* %fronttmp, %Node** %front
	%rear = getelementptr %IntQueue, %IntQueue* %this, i32 0, i32 2
	%reartmp = bitcast i32* null to %Node*
	store %Node* %reartmp, %Node** %rear
	ret void
}

define i32 @IntQueue.size(%IntQueue* %self) { 
IntQueue.size_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	br label %IntQueue.size.1_while.cond
IntQueue.size.1_while.cond:
	%n = phi %Node* [%tmp..1, %IntQueue.size_entry], [%tmp..12, %IntQueue.size.2_while.body]
	%res = phi i32 [0, %IntQueue.size_entry], [%tmp..14, %IntQueue.size.2_while.body]
	%tmp..5 = bitcast i32* null to %Node*
	%tmp..6 = icmp ne %Node* %n, %tmp..5
	br i1 %tmp..6, label %IntQueue.size.2_while.body, label %IntQueue.size.3_while.end
IntQueue.size.2_while.body:
	%tmp..7 = getelementptr %Node, %Node* %n, i32 0, i32 0
	%tmp..8 = load void (...)**, void (...)*** %tmp..7
	%tmp..9 = getelementptr void (...)*, void (...)** %tmp..8, i32 3
	%tmp..10 = bitcast void (...)** %tmp..9 to %Node* (%Node*)**
	%tmp..11 = load %Node* (%Node*)*, %Node* (%Node*)** %tmp..10
	%tmp..12 = call %Node* %tmp..11(%Node* %n)
	%tmp..14 = add i32 %res, 1
	br label %IntQueue.size.1_while.cond
IntQueue.size.3_while.end:
	ret i32 %res
}

define void @IntQueue.rmFirst(%IntQueue* %self) { 
IntQueue.rmFirst_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 1
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

define i1 @IntQueue.isEmpty(%IntQueue* %self) { 
IntQueue.isEmpty_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 1
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
	%tmp..3 = getelementptr %Node, %Node* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%Node*, i32)**
	%tmp..7 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..6
	call void %tmp..7(%Node* %tmp..1, i32 %x)
	%tmp..15 = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 0
	%tmp..16 = load void (...)**, void (...)*** %tmp..15
	%tmp..17 = getelementptr void (...)*, void (...)** %tmp..16, i32 0
	%tmp..18 = bitcast void (...)** %tmp..17 to i1 (%IntQueue*)**
	%tmp..19 = load i1 (%IntQueue*)*, i1 (%IntQueue*)** %tmp..18
	%tmp..20 = call i1 %tmp..19(%IntQueue* %self)
	br i1 %tmp..20, label %IntQueue.insert.1_if.true, label %IntQueue.insert.2_if.false
IntQueue.insert.1_if.true:
	%tmp..21 = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 1
	store %Node* %tmp..1, %Node** %tmp..21
	br label %IntQueue.insert.3_if.end
IntQueue.insert.2_if.false:
	%tmp..22 = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 2
	%tmp..23 = load %Node*, %Node** %tmp..22
	%tmp..24 = getelementptr %Node, %Node* %tmp..23, i32 0, i32 0
	%tmp..25 = load void (...)**, void (...)*** %tmp..24
	%tmp..26 = getelementptr void (...)*, void (...)** %tmp..25, i32 1
	%tmp..27 = bitcast void (...)** %tmp..26 to void (%Node*, %Node*)**
	%tmp..28 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..27
	call void %tmp..28(%Node* %tmp..23, %Node* %tmp..1)
	br label %IntQueue.insert.3_if.end
IntQueue.insert.3_if.end:
	%tmp..30 = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 2
	store %Node* %tmp..1, %Node** %tmp..30
	ret void
}

define i32 @IntQueue.first(%IntQueue* %self) { 
IntQueue.first_entry:
	%tmp. = getelementptr %IntQueue, %IntQueue* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = getelementptr %Node, %Node* %tmp..1, i32 0, i32 0
	%tmp..3 = load void (...)**, void (...)*** %tmp..2
	%tmp..4 = getelementptr void (...)*, void (...)** %tmp..3, i32 2
	%tmp..5 = bitcast void (...)** %tmp..4 to i32 (%Node*)**
	%tmp..6 = load i32 (%Node*)*, i32 (%Node*)** %tmp..5
	%tmp..7 = call i32 %tmp..6(%Node* %tmp..1)
	ret i32 %tmp..7
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

define void @Node.setElem(%Node* %self, i32 %e) { 
Node.setElem_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	store i32 %e, i32* %tmp.
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
	%tmp..3 = getelementptr %IntQueue, %IntQueue* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 1
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%IntQueue*, i32)**
	%tmp..7 = load void (%IntQueue*, i32)*, void (%IntQueue*, i32)** %tmp..6
	%tmp..9 = call i32 @f(i32 3)
	call void %tmp..7(%IntQueue* %tmp..1, i32 %tmp..9)
	%tmp..12 = load void (...)**, void (...)*** %tmp..3
	%tmp..13 = getelementptr void (...)*, void (...)** %tmp..12, i32 1
	%tmp..14 = bitcast void (...)** %tmp..13 to void (%IntQueue*, i32)**
	%tmp..15 = load void (%IntQueue*, i32)*, void (%IntQueue*, i32)** %tmp..14
	call void %tmp..15(%IntQueue* %tmp..1, i32 5)
	%tmp..19 = load void (...)**, void (...)*** %tmp..3
	%tmp..20 = getelementptr void (...)*, void (...)** %tmp..19, i32 1
	%tmp..21 = bitcast void (...)** %tmp..20 to void (%IntQueue*, i32)**
	%tmp..22 = load void (%IntQueue*, i32)*, void (%IntQueue*, i32)** %tmp..21
	call void %tmp..22(%IntQueue* %tmp..1, i32 7)
	%tmp..26 = load void (...)**, void (...)*** %tmp..3
	%tmp..27 = getelementptr void (...)*, void (...)** %tmp..26, i32 2
	%tmp..28 = bitcast void (...)** %tmp..27 to i32 (%IntQueue*)**
	%tmp..29 = load i32 (%IntQueue*)*, i32 (%IntQueue*)** %tmp..28
	%tmp..30 = call i32 %tmp..29(%IntQueue* %tmp..1)
	call void @printInt(i32 %tmp..30)
	%tmp..33 = load void (...)**, void (...)*** %tmp..3
	%tmp..34 = getelementptr void (...)*, void (...)** %tmp..33, i32 3
	%tmp..35 = bitcast void (...)** %tmp..34 to void (%IntQueue*)**
	%tmp..36 = load void (%IntQueue*)*, void (%IntQueue*)** %tmp..35
	call void %tmp..36(%IntQueue* %tmp..1)
	%tmp..39 = load void (...)**, void (...)*** %tmp..3
	%tmp..40 = getelementptr void (...)*, void (...)** %tmp..39, i32 4
	%tmp..41 = bitcast void (...)** %tmp..40 to i32 (%IntQueue*)**
	%tmp..42 = load i32 (%IntQueue*)*, i32 (%IntQueue*)** %tmp..41
	%tmp..43 = call i32 %tmp..42(%IntQueue* %tmp..1)
	call void @printInt(i32 %tmp..43)
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
