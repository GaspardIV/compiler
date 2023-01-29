@.str.str4 = private unnamed_addr constant [20 x i8] c"I'm really a square\00", align 1@.str.str0 = private unnamed_addr constant [20 x i8] c"I'm really a circle\00", align 1@.str.str1 = private unnamed_addr constant [17 x i8] c"I'm just a shape\00", align 1@.str.str3 = private unnamed_addr constant [23 x i8] c"I'm really a rectangle\00", align 1@.str.str2 = private unnamed_addr constant [12 x i8] c"I'm a shape\00", align 1 ; --- Class Circle ---
@Circle.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Circle*)* @Circle.tellAgain to void (...)*) ; tellAgain 
]

%Circle = type { 
	void (...)**; vtable
	}
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
 ; --- Class Shape ---
@Shape.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Shape*)* @Shape.tellAgain to void (...)*) ; tellAgain 
]

%Shape = type { 
	void (...)**; vtable
	}
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
 ; --- Class Rectangle ---
@Rectangle.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Rectangle*)* @Rectangle.tellAgain to void (...)*) ; tellAgain 
]

%Rectangle = type { 
	void (...)**; vtable
	}
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
 ; --- Class Square ---
@Square.vtable = global [2 x void (...)*] [
	void (...)* bitcast (void (%Shape*)* @Shape.tell to void (...)*) , ; tell 
	void (...)* bitcast (void (%Square*)* @Square.tellAgain to void (...)*) ; tellAgain 
]

%Square = type { 
	void (...)**; vtable
	}
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
	%tmp..3 = call %Node* @Node.getNext(%Node* %tmp..2)
	store %Node* %tmp..3, %Node** %tmp.
	ret void
}

define %Shape* @Stack.top(%Stack* %self) { 
Stack.top_entry:
	%tmp. = getelementptr %Stack, %Stack* %self, i32 0, i32 1
	%tmp..1 = load %Node*, %Node** %tmp.
	%tmp..2 = call %Shape* @Node.getElem(%Node* %tmp..1)
	ret %Shape* %tmp..2
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
	call void @Node.setElem(%Node* %tmp..1, %Shape* %c)
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
	%tmp..3 = call i8* @malloc(i32 0)
	%tmp..4 = bitcast i8* %tmp..3 to %Shape*
	call void @Shape.constructor(%Shape* %tmp..4)
	call void @Stack.push(%Stack* %tmp..1, %Shape* %tmp..4)
	%tmp..7 = call i8* @malloc(i32 0)
	%tmp..8 = bitcast i8* %tmp..7 to %Rectangle*
	call void @Rectangle.constructor(%Rectangle* %tmp..8)
	call void @Stack.push(%Stack* %tmp..1, %Rectangle* %tmp..8)
	%tmp..11 = call i8* @malloc(i32 0)
	%tmp..12 = bitcast i8* %tmp..11 to %Square*
	call void @Square.constructor(%Square* %tmp..12)
	call void @Stack.push(%Stack* %tmp..1, %Square* %tmp..12)
	%tmp..15 = call i8* @malloc(i32 0)
	%tmp..16 = bitcast i8* %tmp..15 to %Circle*
	call void @Circle.constructor(%Circle* %tmp..16)
	call void @Stack.push(%Stack* %tmp..1, %Circle* %tmp..16)
	br label %main.1_while.cond
main.1_while.cond:
	%tmp..21 = call i1 @Stack.isEmpty(%Stack* %tmp..1)
	br i1 %tmp..21, label %main.3_while.end, label %main.2_while.body
main.2_while.body:
	%tmp..22 = call %Shape* @Stack.top(%Stack* %tmp..1)
	call void @Shape.tell(%Shape* %tmp..22)
	call void @Shape.tellAgain(%Shape* %tmp..22)
	call void @Stack.pop(%Stack* %tmp..1)
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
