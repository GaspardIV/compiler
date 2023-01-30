@.str.str4 = private unnamed_addr constant [20 x i8] c"I'm really a square\00", align 1@.str.str0 = private unnamed_addr constant [20 x i8] c"I'm really a circle\00", align 1@.str.str1 = private unnamed_addr constant [17 x i8] c"I'm just a shape\00", align 1@.str.str3 = private unnamed_addr constant [23 x i8] c"I'm really a rectangle\00", align 1@.str.str2 = private unnamed_addr constant [12 x i8] c"I'm a shape\00", align 1 ; --- Class Circle ---
@Circle.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Circle*)* @Circle.tellAgain to void (...)*) ; tellAgain 
]

%Circle = type { 
	void (...)**; vtable
	}
 ; --- Class Shape ---
@Shape.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Shape*)* @Shape.tellAgain to void (...)*) ; tellAgain 
]

%Shape = type { 
	void (...)**; vtable
	}
 ; --- Class Node ---
@Node.vtable = global [4 x void (...)*] [
	void (...)* bitcast (void (%Node*, %Shape*)* @Node.setElem to void (...)*) , ; setElem 
	void (...)* bitcast (void (%Node*, %Node*)* @Node.setNext to void (...)*) , ; setNext 
	void (...)* bitcast (%Shape* (%Node*)* @Node.getElem to void (...)*) , ; getElem 
	void (...)* bitcast (%Node* (%Node*)* @Node.getNext to void (...)*) ; getNext 
]

%Node = type { 
	void (...)**; vtable
	,%Shape*; elem 
	,%Node*; next 
	}
 ; --- Class Rectangle ---
@Rectangle.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Rectangle*)* @Rectangle.tellAgain to void (...)*) ; tellAgain 
]

%Rectangle = type { 
	void (...)**; vtable
	}
 ; --- Class Square ---
@Square.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Square*)* @Square.tellAgain to void (...)*) ; tellAgain 
]

%Square = type { 
	void (...)**; vtable
	}
 ; --- Class Stack ---
@Stack.vtable = global [4 x void (...)*] [
	void (...)* bitcast (void (%Stack*, %Shape*)* @Stack.push to void (...)*) , ; push 
	void (...)* bitcast (i1 (%Stack*)* @Stack.isEmpty to void (...)*) , ; isEmpty 
	void (...)* bitcast (%Shape* (%Stack*)* @Stack.top to void (...)*) , ; top 
	void (...)* bitcast (void (%Stack*)* @Stack.pop to void (...)*) ; pop 
]

%Stack = type { 
	void (...)**; vtable
	,%Node*; head 
	}
 ; --- Class Circle methods ---
define void @Circle.constructor(%Circle* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Circle.vtable to void (...)**
	%this.vtable = getelementptr %Circle, %Circle* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @Circle.tellAgain(%Circle* %self) { 
Circle.tellAgain_entry:
	%tmp. = getelementptr [20 x i8], [20 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class Shape methods ---
define void @Shape.constructor(%Shape* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Shape.vtable to void (...)**
	%this.vtable = getelementptr %Shape, %Shape* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @Shape.tellAgain(%Shape* %self) { 
Shape.tellAgain_entry:
	%tmp. = getelementptr [17 x i8], [17 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @Shape.tell(%Shape* %self) { 
Shape.tell_entry:
	%tmp. = getelementptr [12 x i8], [12 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class Node methods ---
define void @Node.constructor(%Node* %this) {
	%this.class.vtable = bitcast [4 x void (...)*]* @Node.vtable to void (...)**
	%this.vtable = getelementptr %Node, %Node* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%elem = getelementptr %Node, %Node* %this, i32 0, i32 1
	%elemtmp = bitcast i32* null to %Shape*
	store %Shape* %elemtmp, %Shape** %elem
	%next = getelementptr %Node, %Node* %this, i32 0, i32 2
	%nexttmp = bitcast i32* null to %Node*
	store %Node* %nexttmp, %Node** %next
	ret void
}

define void @Node.setElem(%Node* %self, %Shape* %c) { 
Node.setElem_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	store %Shape* %c, %Shape** %tmp.
	ret void
}

define void @Node.setNext(%Node* %self, %Node* %n) { 
Node.setNext_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 2
	store %Node* %n, %Node** %tmp.
	ret void
}

define %Shape* @Node.getElem(%Node* %self) { 
Node.getElem_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 1
	%tmp..1 = load %Shape*, %Shape** %tmp.
	ret %Shape* %tmp..1
}

define %Node* @Node.getNext(%Node* %self) { 
Node.getNext_entry:
	%tmp. = getelementptr %Node, %Node* %self, i32 0, i32 2
	%tmp..1 = load %Node*, %Node** %tmp.
	ret %Node* %tmp..1
}
 ; --- Class Rectangle methods ---
define void @Rectangle.constructor(%Rectangle* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Rectangle.vtable to void (...)**
	%this.vtable = getelementptr %Rectangle, %Rectangle* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @Rectangle.tellAgain(%Rectangle* %self) { 
Rectangle.tellAgain_entry:
	%tmp. = getelementptr [23 x i8], [23 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class Square methods ---
define void @Square.constructor(%Square* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Square.vtable to void (...)**
	%this.vtable = getelementptr %Square, %Square* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @Square.tellAgain(%Square* %self) { 
Square.tellAgain_entry:
	%tmp. = getelementptr [20 x i8], [20 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
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

define %Shape* @Stack.top(%Stack* %self) { 
Stack.top_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = getelementptr %Node, %Node* %tmp..1, i32 0, i32 0
	%tmp..3 = load void (...)**, void (...)*** %tmp..2
	%tmp..4 = getelementptr void (...)*, void (...)** %tmp..3, i32 2
	%tmp..5 = bitcast void (...)** %tmp..4 to %Shape* (%Node*)**
	%tmp..6 = load %Shape* (%Node*)*, %Shape* (%Node*)** %tmp..5
	%tmp..7 = call %Shape* %tmp..6(%Node* %tmp..1)
	ret %Shape* %tmp..7
}

define i1 @Stack.isEmpty(%Stack* %self) { 
Stack.isEmpty_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = bitcast i32* null to %Node*
	%tmp..3 = icmp eq %Node* %tmp..1, %tmp..2
	ret i1 %tmp..3
}

define void @Stack.push(%Stack* %self, %Shape* %c) { 
Stack.push_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Node*
	call void @Node.constructor(%Node* %tmp..1)
	%tmp..3 = getelementptr %Node, %Node* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%Node*, %Shape*)**
	%tmp..7 = load void (%Node*, %Shape*)*, void (%Node*, %Shape*)** %tmp..6
	call void %tmp..7(%Node* %tmp..1, %Shape* %c)
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
	%tmp..3 = call i8* @malloc(i32 0)
	%tmp..4 = bitcast i8* %tmp..3 to %Shape*
	call void @Shape.constructor(%Shape* %tmp..4)
	%tmp..6 = getelementptr %Stack, %Stack* %tmp..1, i32 0, i32 0
	%tmp..7 = load void (...)**, void (...)*** %tmp..6
	%tmp..8 = getelementptr void (...)*, void (...)** %tmp..7, i32 0
	%tmp..9 = bitcast void (...)** %tmp..8 to void (%Stack*, %Shape*)**
	%tmp..10 = load void (%Stack*, %Shape*)*, void (%Stack*, %Shape*)** %tmp..9
	call void %tmp..10(%Stack* %tmp..1, %Shape* %tmp..4)
	%tmp..12 = call i8* @malloc(i32 0)
	%tmp..13 = bitcast i8* %tmp..12 to %Rectangle*
	call void @Rectangle.constructor(%Rectangle* %tmp..13)
	%tmp..15 = bitcast %Rectangle* %tmp..13 to %Shape*
	%tmp..17 = load void (...)**, void (...)*** %tmp..6
	%tmp..18 = getelementptr void (...)*, void (...)** %tmp..17, i32 0
	%tmp..19 = bitcast void (...)** %tmp..18 to void (%Stack*, %Shape*)**
	%tmp..20 = load void (%Stack*, %Shape*)*, void (%Stack*, %Shape*)** %tmp..19
	call void %tmp..20(%Stack* %tmp..1, %Shape* %tmp..15)
	%tmp..22 = call i8* @malloc(i32 0)
	%tmp..23 = bitcast i8* %tmp..22 to %Square*
	call void @Square.constructor(%Square* %tmp..23)
	%tmp..25 = bitcast %Square* %tmp..23 to %Shape*
	%tmp..27 = load void (...)**, void (...)*** %tmp..6
	%tmp..28 = getelementptr void (...)*, void (...)** %tmp..27, i32 0
	%tmp..29 = bitcast void (...)** %tmp..28 to void (%Stack*, %Shape*)**
	%tmp..30 = load void (%Stack*, %Shape*)*, void (%Stack*, %Shape*)** %tmp..29
	call void %tmp..30(%Stack* %tmp..1, %Shape* %tmp..25)
	%tmp..32 = call i8* @malloc(i32 0)
	%tmp..33 = bitcast i8* %tmp..32 to %Circle*
	call void @Circle.constructor(%Circle* %tmp..33)
	%tmp..35 = bitcast %Circle* %tmp..33 to %Shape*
	%tmp..37 = load void (...)**, void (...)*** %tmp..6
	%tmp..38 = getelementptr void (...)*, void (...)** %tmp..37, i32 0
	%tmp..39 = bitcast void (...)** %tmp..38 to void (%Stack*, %Shape*)**
	%tmp..40 = load void (%Stack*, %Shape*)*, void (%Stack*, %Shape*)** %tmp..39
	call void %tmp..40(%Stack* %tmp..1, %Shape* %tmp..35)
	br label %main.1_while.cond
main.1_while.cond:
	%tmp..50 = load void (...)**, void (...)*** %tmp..6
	%tmp..51 = getelementptr void (...)*, void (...)** %tmp..50, i32 1
	%tmp..52 = bitcast void (...)** %tmp..51 to i1 (%Stack*)**
	%tmp..53 = load i1 (%Stack*)*, i1 (%Stack*)** %tmp..52
	%tmp..54 = call i1 %tmp..53(%Stack* %tmp..1)
	br i1 %tmp..54, label %main.3_while.end, label %main.2_while.body
main.2_while.body:
	%tmp..56 = load void (...)**, void (...)*** %tmp..6
	%tmp..57 = getelementptr void (...)*, void (...)** %tmp..56, i32 2
	%tmp..58 = bitcast void (...)** %tmp..57 to %Shape* (%Stack*)**
	%tmp..59 = load %Shape* (%Stack*)*, %Shape* (%Stack*)** %tmp..58
	%tmp..60 = call %Shape* %tmp..59(%Stack* %tmp..1)
	%tmp..61 = getelementptr %Shape, %Shape* %tmp..60, i32 0, i32 0
	%tmp..62 = load void (...)**, void (...)*** %tmp..61
	%tmp..63 = getelementptr void (...)*, void (...)** %tmp..62, i32 0
	%tmp..64 = bitcast void (...)** %tmp..63 to void (%Shape*)**
	%tmp..65 = load void (%Shape*)*, void (%Shape*)** %tmp..64
	call void %tmp..65(%Shape* %tmp..60)
	%tmp..68 = load void (...)**, void (...)*** %tmp..61
	%tmp..69 = getelementptr void (...)*, void (...)** %tmp..68, i32 1
	%tmp..70 = bitcast void (...)** %tmp..69 to void (%Shape*)**
	%tmp..71 = load void (%Shape*)*, void (%Shape*)** %tmp..70
	call void %tmp..71(%Shape* %tmp..60)
	%tmp..74 = load void (...)**, void (...)*** %tmp..6
	%tmp..75 = getelementptr void (...)*, void (...)** %tmp..74, i32 3
	%tmp..76 = bitcast void (...)** %tmp..75 to void (%Stack*)**
	%tmp..77 = load void (%Stack*)*, void (%Stack*)** %tmp..76
	call void %tmp..77(%Stack* %tmp..1)
	br label %main.1_while.cond
main.3_while.end:
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

declare i8* @malloc(i32)
