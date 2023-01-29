 ; --- Class Node ---
@Node.vtable = global [6 x void (...)*] [
	void (...)* bitcast (void (%Node*, i32)* @Node.init to void (...)*) , ; init 
	void (...)* bitcast (i1 (%Node*)* @Node.isVisited to void (...)*) , ; isVisited 
	void (...)* bitcast (void (%Node*)* @Node.markAsVisited to void (...)*) , ; markAsVisited 
	void (...)* bitcast (i32 (%Node*)* @Node.getValue to void (...)*) , ; getValue 
	void (...)* bitcast (%List* (%Node*)* @Node.getNeighbours to void (...)*) , ; getNeighbours 
	void (...)* bitcast (void (%Node*, %Node*)* @Node.addNeighbour to void (...)*) ; addNeighbour 
]

%Node = type { 
	void (...)**,
	i1; visited 
,
	i32; value 
,
	%List*; neighbours 
}
define void @Node.constructor(%Node* %this) {
	%this.vtable = bitcast [6 x void (...)*]* @Node.vtable to void (...)**
	%visited = getelementptr %Node, %Node* %this, i32 0, i32 1
	store i1 false, i1* %visited
	%value = getelementptr %Node, %Node* %this, i32 0, i32 2
	store i32 0, i32* %value
	%neighbours = getelementptr %Node, %Node* %this, i32 0, i32 3
	%neighbourstmp = bitcast i32* null to %List*
	store %List* %neighbourstmp, %List** %neighbours
	ret void
}

define void @Node.init(%Node* %self, i32 %val) { 
Node.init_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	store i1 false, i1* %tmp.
	%tmp..2 = getelementptr %Node, %Node* %self, i32 0, i32 2
	store i32 %val, i32* %tmp..2
	%tmp..3 = getelementptr %Node, %Node* %self, i32 0, i32 3
	%tmp..4 = bitcast i32* null to %List*
	store %List* %tmp..4, %List** %tmp..3
	ret void
}

define i32 @Node.getValue(%Node* %self) { 
Node.getValue_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 2
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define %List* @Node.getNeighbours(%Node* %self) { 
Node.getNeighbours_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 3
	%tmp..1 = load %List*, %List** %tmp.
	ret %List* %tmp..1
}

define i1 @Node.isVisited(%Node* %self) { 
Node.isVisited_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	%tmp..1 = load i1, i1* %tmp.
	ret i1 %tmp..1
}

define void @Node.addNeighbour(%Node* %self, %Node* %n) { 
Node.addNeighbour_entry:
	%tmp..4 = getelementptr %Node, %Node* %self, i32 0, i32 3
	%tmp..5 = load %List*, %List** %tmp..4
	%tmp..6 = bitcast i32* null to %List*
	%tmp..7 = icmp eq %List* %tmp..5, %tmp..6
	br i1 %tmp..7, label %Node.addNeighbour.1_if.true, label %Node.addNeighbour.2_if.false
Node.addNeighbour.1_if.true:
	%tmp..9 = call i8* @malloc(i32 128)
	%tmp..10 = bitcast i8* %tmp..9 to %List*
	call void @List.constructor(%List* %tmp..10)
	store %List* %tmp..10, %List** %tmp..4
	%tmp..13 = load %List*, %List** %tmp..4
	call void @List.makeSingleton(%List* %tmp..13, %Node* %n)
	br label %Node.addNeighbour.3_if.end
Node.addNeighbour.2_if.false:
	%tmp..15 = call i8* @malloc(i32 128)
	%tmp..16 = bitcast i8* %tmp..15 to %List*
	call void @List.constructor(%List* %tmp..16)
	%tmp..19 = load %List*, %List** %tmp..4
	call void @List.cons(%List* %tmp..16, %Node* %n, %List* %tmp..19)
	store %List* %tmp..16, %List** %tmp..4
	br label %Node.addNeighbour.3_if.end
Node.addNeighbour.3_if.end:
	ret void
}

define void @Node.markAsVisited(%Node* %self) { 
Node.markAsVisited_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	store i1 true, i1* %tmp.
	ret void
}
 ; --- Class List ---
@List.vtable = global [4 x void (...)*] [
	void (...)* bitcast (void (%List*, %Node*)* @List.makeSingleton to void (...)*) , ; makeSingleton 
	void (...)* bitcast (%Node* (%List*)* @List.getHead to void (...)*) , ; getHead 
	void (...)* bitcast (%List* (%List*)* @List.getTail to void (...)*) , ; getTail 
	void (...)* bitcast (void (%List*, %Node*, %List*)* @List.cons to void (...)*) ; cons 
]

%List = type { 
	void (...)**,
	%Node*; head 
,
	%List*; tail 
}
define void @List.constructor(%List* %this) {
	%this.vtable = bitcast [4 x void (...)*]* @List.vtable to void (...)**
	%head = getelementptr %List, %List* %this, i32 0, i32 1
	%headtmp = bitcast i32* null to %Node*
	store %Node* %headtmp, %Node** %head
	%tail = getelementptr %List, %List* %this, i32 0, i32 2
	%tailtmp = bitcast i32* null to %List*
	store %List* %tailtmp, %List** %tail
	ret void
}

define %List* @List.getTail(%List* %self) { 
List.getTail_entry:
	%tmp. = getelementptr %List, %List* %self, i32 0, i32 2
	%tmp..1 = load %List*, %List** %tmp.
	ret %List* %tmp..1
}

define %Node* @List.getHead(%List* %self) { 
List.getHead_entry:
	%tmp. = getelementptr %List, %List* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	ret %Node* %tmp..1
}

define void @List.makeSingleton(%List* %self, %Node* %node) { 
List.makeSingleton_entry:
	%tmp. = getelementptr %List, %List* %self, i32 0, i32 1
	store %Node* %node, %Node** %tmp.
	%tmp..1 = getelementptr %List, %List* %self, i32 0, i32 2
	%tmp..2 = bitcast i32* null to %List*
	store %List* %tmp..2, %List** %tmp..1
	ret void
}

define void @List.cons(%List* %self, %Node* %newHead, %List* %newTail) { 
List.cons_entry:
	%tmp. = getelementptr %List, %List* %self, i32 0, i32 1
	store %Node* %newHead, %Node** %tmp.
	%tmp..1 = getelementptr %List, %List* %self, i32 0, i32 2
	store %List* %newTail, %List** %tmp..1
	ret void
}
 ; --- Class Queue ---
@Queue.vtable = global [3 x void (...)*] [
	void (...)* bitcast (%Node* (%Queue*)* @Queue.get to void (...)*) , ; get 
	void (...)* bitcast (void (%Queue*, %Node*)* @Queue.put to void (...)*) , ; put 
	void (...)* bitcast (i1 (%Queue*)* @Queue.isEmpty to void (...)*) ; isEmpty 
]

%Queue = type { 
	void (...)**,
	%List*; first 
,
	%List*; last 
}
define void @Queue.constructor(%Queue* %this) {
	%this.vtable = bitcast [3 x void (...)*]* @Queue.vtable to void (...)**
	%first = getelementptr %Queue, %Queue* %this, i32 0, i32 1
	%firsttmp = bitcast i32* null to %List*
	store %List* %firsttmp, %List** %first
	%last = getelementptr %Queue, %Queue* %this, i32 0, i32 2
	%lasttmp = bitcast i32* null to %List*
	store %List* %lasttmp, %List** %last
	ret void
}

define %Node* @Queue.get(%Queue* %self) { 
Queue.get_entry:
	%tmp..4 = getelementptr %Queue, %Queue* %self, i32 0, i32 1
	%tmp..5 = load %List*, %List** %tmp..4
	%tmp..6 = bitcast i32* null to %List*
	%tmp..7 = icmp eq %List* %tmp..5, %tmp..6
	br i1 %tmp..7, label %Queue.get.1_if.true, label %Queue.get.3_if.end
Queue.get.1_if.true:
	%tmp..8 = bitcast i32* null to %Node*
	ret %Node* %tmp..8
Queue.get.3_if.end:
	%tmp..10 = load %List*, %List** %tmp..4
	%tmp..11 = getelementptr %List, %List* %tmp..10, i32 0, i32 1
	%tmp..12 = load %Node*, %Node** %tmp..11
	%tmp..15 = load %List*, %List** %tmp..4
	%tmp..16 = getelementptr %List, %List* %tmp..15, i32 0, i32 2
	%tmp..17 = load %List*, %List** %tmp..16
	store %List* %tmp..17, %List** %tmp..4
	%tmp..23 = load %List*, %List** %tmp..4
	%tmp..25 = icmp eq %List* %tmp..23, %tmp..6
	br i1 %tmp..25, label %Queue.get.4_if.true, label %Queue.get.6_if.end
Queue.get.4_if.true:
	%tmp..26 = getelementptr %Queue, %Queue* %self, i32 0, i32 2
	store %List* %tmp..6, %List** %tmp..26
	br label %Queue.get.6_if.end
Queue.get.6_if.end:
	ret %Node* %tmp..12
}

define i1 @Queue.isEmpty(%Queue* %self) { 
Queue.isEmpty_entry:
	%tmp. = getelementptr %Queue, %Queue* %self, i32 0, i32 1
	%tmp..1 = load %List*, %List** %tmp.
	%tmp..2 = bitcast i32* null to %List*
	%tmp..3 = icmp eq %List* %tmp..1, %tmp..2
	ret i1 %tmp..3
}

define void @Queue.put(%Queue* %self, %Node* %n) { 
Queue.put_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %List*
	call void @List.constructor(%List* %tmp..1)
	call void @List.makeSingleton(%List* %tmp..1, %Node* %n)
	%tmp..8 = getelementptr %Queue, %Queue* %self, i32 0, i32 1
	%tmp..9 = load %List*, %List** %tmp..8
	%tmp..10 = bitcast i32* null to %List*
	%tmp..11 = icmp eq %List* %tmp..9, %tmp..10
	br i1 %tmp..11, label %Queue.put.1_if.true, label %Queue.put.2_if.false
Queue.put.1_if.true:
	store %List* %tmp..1, %List** %tmp..8
	%tmp..13 = getelementptr %Queue, %Queue* %self, i32 0, i32 2
	store %List* %tmp..1, %List** %tmp..13
	br label %Queue.put.3_if.end
Queue.put.2_if.false:
	%tmp..14 = getelementptr %Queue, %Queue* %self, i32 0, i32 2
	%tmp..15 = load %List*, %List** %tmp..14
	%tmp..17 = load %List*, %List** %tmp..14
	%tmp..18 = call %Node* @List.getHead(%List* %tmp..17)
	call void @List.cons(%List* %tmp..15, %Node* %tmp..18, %List* %tmp..1)
	store %List* %tmp..1, %List** %tmp..14
	br label %Queue.put.3_if.end
Queue.put.3_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call %Node* @prepareData()
	call void @Node.markAsVisited(%Node* %tmp.)
	%tmp..2 = call i8* @malloc(i32 128)
	%tmp..3 = bitcast i8* %tmp..2 to %Queue*
	call void @Queue.constructor(%Queue* %tmp..3)
	call void @Queue.put(%Queue* %tmp..3, %Node* %tmp.)
	call void @bfs(%Queue* %tmp..3)
	ret i32 0
}

define %Node* @prepareData() { 
prepareData_entry:
	%tmp. = call i8* @malloc(i32 104)
	%tmp..1 = bitcast i8* %tmp. to %Node*
	call void @Node.constructor(%Node* %tmp..1)
	call void @Node.init(%Node* %tmp..1, i32 1)
	%tmp..5 = call i8* @malloc(i32 104)
	%tmp..6 = bitcast i8* %tmp..5 to %Node*
	call void @Node.constructor(%Node* %tmp..6)
	call void @Node.init(%Node* %tmp..6, i32 2)
	%tmp..10 = call i8* @malloc(i32 104)
	%tmp..11 = bitcast i8* %tmp..10 to %Node*
	call void @Node.constructor(%Node* %tmp..11)
	call void @Node.init(%Node* %tmp..11, i32 3)
	%tmp..15 = call i8* @malloc(i32 104)
	%tmp..16 = bitcast i8* %tmp..15 to %Node*
	call void @Node.constructor(%Node* %tmp..16)
	call void @Node.init(%Node* %tmp..16, i32 4)
	%tmp..20 = call i8* @malloc(i32 104)
	%tmp..21 = bitcast i8* %tmp..20 to %Node*
	call void @Node.constructor(%Node* %tmp..21)
	call void @Node.init(%Node* %tmp..21, i32 5)
	%tmp..25 = call i8* @malloc(i32 104)
	%tmp..26 = bitcast i8* %tmp..25 to %Node*
	call void @Node.constructor(%Node* %tmp..26)
	call void @Node.init(%Node* %tmp..26, i32 6)
	%tmp..30 = call i8* @malloc(i32 104)
	%tmp..31 = bitcast i8* %tmp..30 to %Node*
	call void @Node.constructor(%Node* %tmp..31)
	call void @Node.init(%Node* %tmp..31, i32 7)
	%tmp..35 = call i8* @malloc(i32 104)
	%tmp..36 = bitcast i8* %tmp..35 to %Node*
	call void @Node.constructor(%Node* %tmp..36)
	call void @Node.init(%Node* %tmp..36, i32 8)
	%tmp..40 = call i8* @malloc(i32 104)
	%tmp..41 = bitcast i8* %tmp..40 to %Node*
	call void @Node.constructor(%Node* %tmp..41)
	call void @Node.init(%Node* %tmp..41, i32 9)
	call void @Node.addNeighbour(%Node* %tmp..1, %Node* %tmp..11)
	call void @Node.addNeighbour(%Node* %tmp..1, %Node* %tmp..6)
	call void @Node.addNeighbour(%Node* %tmp..6, %Node* %tmp..11)
	call void @Node.addNeighbour(%Node* %tmp..11, %Node* %tmp..26)
	call void @Node.addNeighbour(%Node* %tmp..11, %Node* %tmp..21)
	call void @Node.addNeighbour(%Node* %tmp..11, %Node* %tmp..16)
	call void @Node.addNeighbour(%Node* %tmp..16, %Node* %tmp..6)
	call void @Node.addNeighbour(%Node* %tmp..21, %Node* %tmp..31)
	call void @Node.addNeighbour(%Node* %tmp..31, %Node* %tmp..36)
	call void @Node.addNeighbour(%Node* %tmp..36, %Node* %tmp..41)
	call void @Node.addNeighbour(%Node* %tmp..41, %Node* %tmp..21)
	ret %Node* %tmp..1
}

define void @bfs(%Queue* %q) { 
bfs_entry:
	br label %bfs.1_while.cond
bfs.1_while.cond:
	%tmp..2 = call i1 @Queue.isEmpty(%Queue* %q)
	br i1 %tmp..2, label %bfs.3_while.end, label %bfs.2_while.body
bfs.2_while.body:
	%tmp..3 = call %Node* @Queue.get(%Queue* %q)
	%tmp..4 = call i32 @Node.getValue(%Node* %tmp..3)
	call void @printInt(i32 %tmp..4)
	%tmp..6 = call %List* @Node.getNeighbours(%Node* %tmp..3)
	br label %bfs.4_while.cond
bfs.4_while.cond:
	%neigh = phi %List* [%tmp..6, %bfs.2_while.body], [%tmp..17, %bfs.9_if.end]
	%tmp..9 = bitcast i32* null to %List*
	%tmp..10 = icmp ne %List* %neigh, %tmp..9
	br i1 %tmp..10, label %bfs.5_while.body, label %bfs.1_while.cond
bfs.5_while.body:
	%tmp..11 = call %Node* @List.getHead(%List* %neigh)
	%tmp..14 = call i1 @Node.isVisited(%Node* %tmp..11)
	br i1 %tmp..14, label %bfs.9_if.end, label %bfs.7_if.true
bfs.7_if.true:
	call void @Node.markAsVisited(%Node* %tmp..11)
	call void @Queue.put(%Queue* %q, %Node* %tmp..11)
	br label %bfs.9_if.end
bfs.9_if.end:
	%tmp..17 = call %List* @List.getTail(%List* %neigh)
	br label %bfs.4_while.cond
bfs.3_while.end:
	ret void
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
