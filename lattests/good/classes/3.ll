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
	void (...)**; vtable
	,i1; visited 
	,i32; value 
	,%List*; neighbours 
	}
define void @Node.constructor(%Node* %this) {
	%this.class.vtable = bitcast [6 x void (...)*]* @Node.vtable to void (...)**
	%this.vtable = getelementptr %Node, %Node* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
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
	%tmp..14 = getelementptr %List, %List* %tmp..13, i32 0, i32 0
	%tmp..15 = load void (...)**, void (...)*** %tmp..14
	%tmp..16 = getelementptr void (...)*, void (...)** %tmp..15, i32 0
	%tmp..17 = bitcast void (...)** %tmp..16 to void (%List*, %Node*)**
	%tmp..18 = load void (%List*, %Node*)*, void (%List*, %Node*)** %tmp..17
	call void %tmp..18(%List* %tmp..13, %Node* %n)
	br label %Node.addNeighbour.3_if.end
Node.addNeighbour.2_if.false:
	%tmp..20 = call i8* @malloc(i32 128)
	%tmp..21 = bitcast i8* %tmp..20 to %List*
	call void @List.constructor(%List* %tmp..21)
	%tmp..23 = getelementptr %List, %List* %tmp..21, i32 0, i32 0
	%tmp..24 = load void (...)**, void (...)*** %tmp..23
	%tmp..25 = getelementptr void (...)*, void (...)** %tmp..24, i32 3
	%tmp..26 = bitcast void (...)** %tmp..25 to void (%List*, %Node*, %List*)**
	%tmp..27 = load void (%List*, %Node*, %List*)*, void (%List*, %Node*, %List*)** %tmp..26
	%tmp..29 = load %List*, %List** %tmp..4
	call void %tmp..27(%List* %tmp..21, %Node* %n, %List* %tmp..29)
	store %List* %tmp..21, %List** %tmp..4
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
	void (...)**; vtable
	,%Node*; head 
	,%List*; tail 
	}
define void @List.constructor(%List* %this) {
	%this.class.vtable = bitcast [4 x void (...)*]* @List.vtable to void (...)**
	%this.vtable = getelementptr %List, %List* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
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
	void (...)**; vtable
	,%List*; first 
	,%List*; last 
	}
define void @Queue.constructor(%Queue* %this) {
	%this.class.vtable = bitcast [3 x void (...)*]* @Queue.vtable to void (...)**
	%this.vtable = getelementptr %Queue, %Queue* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
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
	%tmp..3 = getelementptr %List, %List* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%List*, %Node*)**
	%tmp..7 = load void (%List*, %Node*)*, void (%List*, %Node*)** %tmp..6
	call void %tmp..7(%List* %tmp..1, %Node* %n)
	%tmp..13 = getelementptr %Queue, %Queue* %self, i32 0, i32 1
	%tmp..14 = load %List*, %List** %tmp..13
	%tmp..15 = bitcast i32* null to %List*
	%tmp..16 = icmp eq %List* %tmp..14, %tmp..15
	br i1 %tmp..16, label %Queue.put.1_if.true, label %Queue.put.2_if.false
Queue.put.1_if.true:
	store %List* %tmp..1, %List** %tmp..13
	%tmp..18 = getelementptr %Queue, %Queue* %self, i32 0, i32 2
	store %List* %tmp..1, %List** %tmp..18
	br label %Queue.put.3_if.end
Queue.put.2_if.false:
	%tmp..19 = getelementptr %Queue, %Queue* %self, i32 0, i32 2
	%tmp..20 = load %List*, %List** %tmp..19
	%tmp..21 = getelementptr %List, %List* %tmp..20, i32 0, i32 0
	%tmp..22 = load void (...)**, void (...)*** %tmp..21
	%tmp..23 = getelementptr void (...)*, void (...)** %tmp..22, i32 3
	%tmp..24 = bitcast void (...)** %tmp..23 to void (%List*, %Node*, %List*)**
	%tmp..25 = load void (%List*, %Node*, %List*)*, void (%List*, %Node*, %List*)** %tmp..24
	%tmp..27 = load %List*, %List** %tmp..19
	%tmp..28 = getelementptr %List, %List* %tmp..27, i32 0, i32 0
	%tmp..29 = load void (...)**, void (...)*** %tmp..28
	%tmp..30 = getelementptr void (...)*, void (...)** %tmp..29, i32 1
	%tmp..31 = bitcast void (...)** %tmp..30 to %Node* (%List*)**
	%tmp..32 = load %Node* (%List*)*, %Node* (%List*)** %tmp..31
	%tmp..33 = call %Node* %tmp..32(%List* %tmp..27)
	call void %tmp..25(%List* %tmp..20, %Node* %tmp..33, %List* %tmp..1)
	store %List* %tmp..1, %List** %tmp..19
	br label %Queue.put.3_if.end
Queue.put.3_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call %Node* @prepareData()
	%tmp..1 = getelementptr %Node, %Node* %tmp., i32 0, i32 0
	%tmp..2 = load void (...)**, void (...)*** %tmp..1
	%tmp..3 = getelementptr void (...)*, void (...)** %tmp..2, i32 2
	%tmp..4 = bitcast void (...)** %tmp..3 to void (%Node*)**
	%tmp..5 = load void (%Node*)*, void (%Node*)** %tmp..4
	call void %tmp..5(%Node* %tmp.)
	%tmp..7 = call i8* @malloc(i32 128)
	%tmp..8 = bitcast i8* %tmp..7 to %Queue*
	call void @Queue.constructor(%Queue* %tmp..8)
	%tmp..10 = getelementptr %Queue, %Queue* %tmp..8, i32 0, i32 0
	%tmp..11 = load void (...)**, void (...)*** %tmp..10
	%tmp..12 = getelementptr void (...)*, void (...)** %tmp..11, i32 1
	%tmp..13 = bitcast void (...)** %tmp..12 to void (%Queue*, %Node*)**
	%tmp..14 = load void (%Queue*, %Node*)*, void (%Queue*, %Node*)** %tmp..13
	call void %tmp..14(%Queue* %tmp..8, %Node* %tmp.)
	call void @bfs(%Queue* %tmp..8)
	ret i32 0
}

define %Node* @prepareData() { 
prepareData_entry:
	%tmp. = call i8* @malloc(i32 104)
	%tmp..1 = bitcast i8* %tmp. to %Node*
	call void @Node.constructor(%Node* %tmp..1)
	%tmp..3 = getelementptr %Node, %Node* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%Node*, i32)**
	%tmp..7 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..6
	call void %tmp..7(%Node* %tmp..1, i32 1)
	%tmp..10 = call i8* @malloc(i32 104)
	%tmp..11 = bitcast i8* %tmp..10 to %Node*
	call void @Node.constructor(%Node* %tmp..11)
	%tmp..13 = getelementptr %Node, %Node* %tmp..11, i32 0, i32 0
	%tmp..14 = load void (...)**, void (...)*** %tmp..13
	%tmp..15 = getelementptr void (...)*, void (...)** %tmp..14, i32 0
	%tmp..16 = bitcast void (...)** %tmp..15 to void (%Node*, i32)**
	%tmp..17 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..16
	call void %tmp..17(%Node* %tmp..11, i32 2)
	%tmp..20 = call i8* @malloc(i32 104)
	%tmp..21 = bitcast i8* %tmp..20 to %Node*
	call void @Node.constructor(%Node* %tmp..21)
	%tmp..23 = getelementptr %Node, %Node* %tmp..21, i32 0, i32 0
	%tmp..24 = load void (...)**, void (...)*** %tmp..23
	%tmp..25 = getelementptr void (...)*, void (...)** %tmp..24, i32 0
	%tmp..26 = bitcast void (...)** %tmp..25 to void (%Node*, i32)**
	%tmp..27 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..26
	call void %tmp..27(%Node* %tmp..21, i32 3)
	%tmp..30 = call i8* @malloc(i32 104)
	%tmp..31 = bitcast i8* %tmp..30 to %Node*
	call void @Node.constructor(%Node* %tmp..31)
	%tmp..33 = getelementptr %Node, %Node* %tmp..31, i32 0, i32 0
	%tmp..34 = load void (...)**, void (...)*** %tmp..33
	%tmp..35 = getelementptr void (...)*, void (...)** %tmp..34, i32 0
	%tmp..36 = bitcast void (...)** %tmp..35 to void (%Node*, i32)**
	%tmp..37 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..36
	call void %tmp..37(%Node* %tmp..31, i32 4)
	%tmp..40 = call i8* @malloc(i32 104)
	%tmp..41 = bitcast i8* %tmp..40 to %Node*
	call void @Node.constructor(%Node* %tmp..41)
	%tmp..43 = getelementptr %Node, %Node* %tmp..41, i32 0, i32 0
	%tmp..44 = load void (...)**, void (...)*** %tmp..43
	%tmp..45 = getelementptr void (...)*, void (...)** %tmp..44, i32 0
	%tmp..46 = bitcast void (...)** %tmp..45 to void (%Node*, i32)**
	%tmp..47 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..46
	call void %tmp..47(%Node* %tmp..41, i32 5)
	%tmp..50 = call i8* @malloc(i32 104)
	%tmp..51 = bitcast i8* %tmp..50 to %Node*
	call void @Node.constructor(%Node* %tmp..51)
	%tmp..53 = getelementptr %Node, %Node* %tmp..51, i32 0, i32 0
	%tmp..54 = load void (...)**, void (...)*** %tmp..53
	%tmp..55 = getelementptr void (...)*, void (...)** %tmp..54, i32 0
	%tmp..56 = bitcast void (...)** %tmp..55 to void (%Node*, i32)**
	%tmp..57 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..56
	call void %tmp..57(%Node* %tmp..51, i32 6)
	%tmp..60 = call i8* @malloc(i32 104)
	%tmp..61 = bitcast i8* %tmp..60 to %Node*
	call void @Node.constructor(%Node* %tmp..61)
	%tmp..63 = getelementptr %Node, %Node* %tmp..61, i32 0, i32 0
	%tmp..64 = load void (...)**, void (...)*** %tmp..63
	%tmp..65 = getelementptr void (...)*, void (...)** %tmp..64, i32 0
	%tmp..66 = bitcast void (...)** %tmp..65 to void (%Node*, i32)**
	%tmp..67 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..66
	call void %tmp..67(%Node* %tmp..61, i32 7)
	%tmp..70 = call i8* @malloc(i32 104)
	%tmp..71 = bitcast i8* %tmp..70 to %Node*
	call void @Node.constructor(%Node* %tmp..71)
	%tmp..73 = getelementptr %Node, %Node* %tmp..71, i32 0, i32 0
	%tmp..74 = load void (...)**, void (...)*** %tmp..73
	%tmp..75 = getelementptr void (...)*, void (...)** %tmp..74, i32 0
	%tmp..76 = bitcast void (...)** %tmp..75 to void (%Node*, i32)**
	%tmp..77 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..76
	call void %tmp..77(%Node* %tmp..71, i32 8)
	%tmp..80 = call i8* @malloc(i32 104)
	%tmp..81 = bitcast i8* %tmp..80 to %Node*
	call void @Node.constructor(%Node* %tmp..81)
	%tmp..83 = getelementptr %Node, %Node* %tmp..81, i32 0, i32 0
	%tmp..84 = load void (...)**, void (...)*** %tmp..83
	%tmp..85 = getelementptr void (...)*, void (...)** %tmp..84, i32 0
	%tmp..86 = bitcast void (...)** %tmp..85 to void (%Node*, i32)**
	%tmp..87 = load void (%Node*, i32)*, void (%Node*, i32)** %tmp..86
	call void %tmp..87(%Node* %tmp..81, i32 9)
	%tmp..91 = load void (...)**, void (...)*** %tmp..3
	%tmp..92 = getelementptr void (...)*, void (...)** %tmp..91, i32 5
	%tmp..93 = bitcast void (...)** %tmp..92 to void (%Node*, %Node*)**
	%tmp..94 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..93
	call void %tmp..94(%Node* %tmp..1, %Node* %tmp..21)
	%tmp..97 = load void (...)**, void (...)*** %tmp..3
	%tmp..98 = getelementptr void (...)*, void (...)** %tmp..97, i32 5
	%tmp..99 = bitcast void (...)** %tmp..98 to void (%Node*, %Node*)**
	%tmp..100 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..99
	call void %tmp..100(%Node* %tmp..1, %Node* %tmp..11)
	%tmp..103 = load void (...)**, void (...)*** %tmp..13
	%tmp..104 = getelementptr void (...)*, void (...)** %tmp..103, i32 5
	%tmp..105 = bitcast void (...)** %tmp..104 to void (%Node*, %Node*)**
	%tmp..106 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..105
	call void %tmp..106(%Node* %tmp..11, %Node* %tmp..21)
	%tmp..109 = load void (...)**, void (...)*** %tmp..23
	%tmp..110 = getelementptr void (...)*, void (...)** %tmp..109, i32 5
	%tmp..111 = bitcast void (...)** %tmp..110 to void (%Node*, %Node*)**
	%tmp..112 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..111
	call void %tmp..112(%Node* %tmp..21, %Node* %tmp..51)
	%tmp..115 = load void (...)**, void (...)*** %tmp..23
	%tmp..116 = getelementptr void (...)*, void (...)** %tmp..115, i32 5
	%tmp..117 = bitcast void (...)** %tmp..116 to void (%Node*, %Node*)**
	%tmp..118 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..117
	call void %tmp..118(%Node* %tmp..21, %Node* %tmp..41)
	%tmp..121 = load void (...)**, void (...)*** %tmp..23
	%tmp..122 = getelementptr void (...)*, void (...)** %tmp..121, i32 5
	%tmp..123 = bitcast void (...)** %tmp..122 to void (%Node*, %Node*)**
	%tmp..124 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..123
	call void %tmp..124(%Node* %tmp..21, %Node* %tmp..31)
	%tmp..127 = load void (...)**, void (...)*** %tmp..33
	%tmp..128 = getelementptr void (...)*, void (...)** %tmp..127, i32 5
	%tmp..129 = bitcast void (...)** %tmp..128 to void (%Node*, %Node*)**
	%tmp..130 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..129
	call void %tmp..130(%Node* %tmp..31, %Node* %tmp..11)
	%tmp..133 = load void (...)**, void (...)*** %tmp..43
	%tmp..134 = getelementptr void (...)*, void (...)** %tmp..133, i32 5
	%tmp..135 = bitcast void (...)** %tmp..134 to void (%Node*, %Node*)**
	%tmp..136 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..135
	call void %tmp..136(%Node* %tmp..41, %Node* %tmp..61)
	%tmp..139 = load void (...)**, void (...)*** %tmp..63
	%tmp..140 = getelementptr void (...)*, void (...)** %tmp..139, i32 5
	%tmp..141 = bitcast void (...)** %tmp..140 to void (%Node*, %Node*)**
	%tmp..142 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..141
	call void %tmp..142(%Node* %tmp..61, %Node* %tmp..71)
	%tmp..145 = load void (...)**, void (...)*** %tmp..73
	%tmp..146 = getelementptr void (...)*, void (...)** %tmp..145, i32 5
	%tmp..147 = bitcast void (...)** %tmp..146 to void (%Node*, %Node*)**
	%tmp..148 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..147
	call void %tmp..148(%Node* %tmp..71, %Node* %tmp..81)
	%tmp..151 = load void (...)**, void (...)*** %tmp..83
	%tmp..152 = getelementptr void (...)*, void (...)** %tmp..151, i32 5
	%tmp..153 = bitcast void (...)** %tmp..152 to void (%Node*, %Node*)**
	%tmp..154 = load void (%Node*, %Node*)*, void (%Node*, %Node*)** %tmp..153
	call void %tmp..154(%Node* %tmp..81, %Node* %tmp..41)
	ret %Node* %tmp..1
}

define void @bfs(%Queue* %q) { 
bfs_entry:
	br label %bfs.1_while.cond
bfs.1_while.cond:
	%tmp..7 = getelementptr %Queue, %Queue* %q, i32 0, i32 0
	%tmp..8 = load void (...)**, void (...)*** %tmp..7
	%tmp..9 = getelementptr void (...)*, void (...)** %tmp..8, i32 2
	%tmp..10 = bitcast void (...)** %tmp..9 to i1 (%Queue*)**
	%tmp..11 = load i1 (%Queue*)*, i1 (%Queue*)** %tmp..10
	%tmp..12 = call i1 %tmp..11(%Queue* %q)
	br i1 %tmp..12, label %bfs.3_while.end, label %bfs.2_while.body
bfs.2_while.body:
	%tmp..14 = load void (...)**, void (...)*** %tmp..7
	%tmp..15 = getelementptr void (...)*, void (...)** %tmp..14, i32 0
	%tmp..16 = bitcast void (...)** %tmp..15 to %Node* (%Queue*)**
	%tmp..17 = load %Node* (%Queue*)*, %Node* (%Queue*)** %tmp..16
	%tmp..18 = call %Node* %tmp..17(%Queue* %q)
	%tmp..19 = getelementptr %Node, %Node* %tmp..18, i32 0, i32 0
	%tmp..20 = load void (...)**, void (...)*** %tmp..19
	%tmp..21 = getelementptr void (...)*, void (...)** %tmp..20, i32 3
	%tmp..22 = bitcast void (...)** %tmp..21 to i32 (%Node*)**
	%tmp..23 = load i32 (%Node*)*, i32 (%Node*)** %tmp..22
	%tmp..24 = call i32 %tmp..23(%Node* %tmp..18)
	call void @printInt(i32 %tmp..24)
	%tmp..27 = load void (...)**, void (...)*** %tmp..19
	%tmp..28 = getelementptr void (...)*, void (...)** %tmp..27, i32 4
	%tmp..29 = bitcast void (...)** %tmp..28 to %List* (%Node*)**
	%tmp..30 = load %List* (%Node*)*, %List* (%Node*)** %tmp..29
	%tmp..31 = call %List* %tmp..30(%Node* %tmp..18)
	br label %bfs.4_while.cond
bfs.4_while.cond:
	%neigh = phi %List* [%tmp..31, %bfs.2_while.body], [%tmp..72, %bfs.9_if.end]
	%tmp..34 = bitcast i32* null to %List*
	%tmp..35 = icmp ne %List* %neigh, %tmp..34
	br i1 %tmp..35, label %bfs.5_while.body, label %bfs.1_while.cond
bfs.5_while.body:
	%tmp..36 = getelementptr %List, %List* %neigh, i32 0, i32 0
	%tmp..37 = load void (...)**, void (...)*** %tmp..36
	%tmp..38 = getelementptr void (...)*, void (...)** %tmp..37, i32 1
	%tmp..39 = bitcast void (...)** %tmp..38 to %Node* (%List*)**
	%tmp..40 = load %Node* (%List*)*, %Node* (%List*)** %tmp..39
	%tmp..41 = call %Node* %tmp..40(%List* %neigh)
	%tmp..49 = getelementptr %Node, %Node* %tmp..41, i32 0, i32 0
	%tmp..50 = load void (...)**, void (...)*** %tmp..49
	%tmp..51 = getelementptr void (...)*, void (...)** %tmp..50, i32 1
	%tmp..52 = bitcast void (...)** %tmp..51 to i1 (%Node*)**
	%tmp..53 = load i1 (%Node*)*, i1 (%Node*)** %tmp..52
	%tmp..54 = call i1 %tmp..53(%Node* %tmp..41)
	br i1 %tmp..54, label %bfs.9_if.end, label %bfs.7_if.true
bfs.7_if.true:
	%tmp..56 = load void (...)**, void (...)*** %tmp..49
	%tmp..57 = getelementptr void (...)*, void (...)** %tmp..56, i32 2
	%tmp..58 = bitcast void (...)** %tmp..57 to void (%Node*)**
	%tmp..59 = load void (%Node*)*, void (%Node*)** %tmp..58
	call void %tmp..59(%Node* %tmp..41)
	%tmp..62 = load void (...)**, void (...)*** %tmp..7
	%tmp..63 = getelementptr void (...)*, void (...)** %tmp..62, i32 1
	%tmp..64 = bitcast void (...)** %tmp..63 to void (%Queue*, %Node*)**
	%tmp..65 = load void (%Queue*, %Node*)*, void (%Queue*, %Node*)** %tmp..64
	call void %tmp..65(%Queue* %q, %Node* %tmp..41)
	br label %bfs.9_if.end
bfs.9_if.end:
	%tmp..68 = load void (...)**, void (...)*** %tmp..36
	%tmp..69 = getelementptr void (...)*, void (...)** %tmp..68, i32 2
	%tmp..70 = bitcast void (...)** %tmp..69 to %List* (%List*)**
	%tmp..71 = load %List* (%List*)*, %List* (%List*)** %tmp..70
	%tmp..72 = call %List* %tmp..71(%List* %neigh)
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
