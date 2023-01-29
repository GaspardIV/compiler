 ; --- Class Operator ---
@Operator.vtable = global [2 x void (...)*] [
	void (...)* bitcast (i32 (%Operator*)* @Operator.value to void (...)*) , ; value 
	void (...)* bitcast (i32 (%Operator*, i32, i32)* @Operator.operator to void (...)*) ; operator 
]

%Operator = type { 
	void (...)**; vtable
	,%Node*; left 
	,%Node*; right 
	}
define void @Operator.constructor(%Operator* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Operator.vtable to void (...)**
	%this.vtable = getelementptr %Operator, %Operator* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%left = getelementptr %Operator, %Operator* %this, i32 0, i32 1
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Operator, %Operator* %this, i32 0, i32 2
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}

define i32 @Operator.value(%Operator* %self) { 
Operator.value_entry:
	%tmp. = getelementptr %Operator, %Operator* %self, i32 0, i32 0
	%tmp..1 = load void (...)**, void (...)*** %tmp.
	%tmp..2 = getelementptr void (...)*, void (...)** %tmp..1, i32 1
	%tmp..3 = bitcast void (...)** %tmp..2 to i32 (%Operator*, i32, i32)**
	%tmp..4 = load i32 (%Operator*, i32, i32)*, i32 (%Operator*, i32, i32)** %tmp..3
	%tmp..5 = getelementptr %Operator, %Operator* %self, i32 0, i32 1
	%tmp..6 = load %Node*, %Node** %tmp..5
	%tmp..7 = getelementptr %Node, %Node* %tmp..6, i32 0, i32 0
	%tmp..8 = load void (...)**, void (...)*** %tmp..7
	%tmp..9 = getelementptr void (...)*, void (...)** %tmp..8, i32 0
	%tmp..10 = bitcast void (...)** %tmp..9 to i32 (%Node*)**
	%tmp..11 = load i32 (%Node*)*, i32 (%Node*)** %tmp..10
	%tmp..12 = call i32 %tmp..11(%Node* %tmp..6)
	%tmp..13 = getelementptr %Operator, %Operator* %self, i32 0, i32 2
	%tmp..14 = load %Node*, %Node** %tmp..13
	%tmp..15 = getelementptr %Node, %Node* %tmp..14, i32 0, i32 0
	%tmp..16 = load void (...)**, void (...)*** %tmp..15
	%tmp..17 = getelementptr void (...)*, void (...)** %tmp..16, i32 0
	%tmp..18 = bitcast void (...)** %tmp..17 to i32 (%Node*)**
	%tmp..19 = load i32 (%Node*)*, i32 (%Node*)** %tmp..18
	%tmp..20 = call i32 %tmp..19(%Node* %tmp..14)
	%tmp..21 = call i32 %tmp..4(%Operator* %self, i32 %tmp..12, i32 %tmp..20)
	ret i32 %tmp..21
}

define i32 @Operator.operator(%Operator* %self, i32 %n1, i32 %n2) { 
Operator.operator_entry:
	call void @error()
	ret i32 0
}
 ; --- Class Liczba ---
@Liczba.vtable = global [1 x void (...)*] [
	void (...)* bitcast (i32 (%Liczba*)* @Liczba.value to void (...)*) ; value 
]

%Liczba = type { 
	void (...)**; vtable
	,i32; v 
	}
define void @Liczba.constructor(%Liczba* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @Liczba.vtable to void (...)**
	%this.vtable = getelementptr %Liczba, %Liczba* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%v = getelementptr %Liczba, %Liczba* %this, i32 0, i32 1
	store i32 0, i32* %v
	ret void
}

define i32 @Liczba.value(%Liczba* %self) { 
Liczba.value_entry:
	%tmp. = getelementptr %Liczba, %Liczba* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}
 ; --- Class Node ---
@Node.vtable = global [1 x void (...)*] [
	void (...)* bitcast (i32 (%Node*)* @Node.value to void (...)*) ; value 
]

%Node = type { 
	void (...)**; vtable
	}
define void @Node.constructor(%Node* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @Node.vtable to void (...)**
	%this.vtable = getelementptr %Node, %Node* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define i32 @Node.value(%Node* %self) { 
Node.value_entry:
	call void @error()
	ret i32 0
}
 ; --- Class Razy ---
@Razy.vtable = global [2 x void (...)*] [
	void (...)* bitcast (i32 (%Operator*)* @Operator.value to void (...)*) , ; value 
	void (...)* bitcast (i32 (%Razy*, i32, i32)* @Razy.operator to void (...)*) ; operator 
]

%Razy = type { 
	void (...)**; vtable
	,%Node*; left 
	,%Node*; right 
	}
define void @Razy.constructor(%Razy* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Razy.vtable to void (...)**
	%this.vtable = getelementptr %Razy, %Razy* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%left = getelementptr %Razy, %Razy* %this, i32 0, i32 1
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Razy, %Razy* %this, i32 0, i32 2
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}

define i32 @Razy.operator(%Razy* %self, i32 %a, i32 %b) { 
Razy.operator_entry:
	%tmp. = mul i32 %a, %b
	ret i32 %tmp.
}
 ; --- Class Plus ---
@Plus.vtable = global [2 x void (...)*] [
	void (...)* bitcast (i32 (%Operator*)* @Operator.value to void (...)*) , ; value 
	void (...)* bitcast (i32 (%Plus*, i32, i32)* @Plus.operator to void (...)*) ; operator 
]

%Plus = type { 
	void (...)**; vtable
	,%Node*; left 
	,%Node*; right 
	}
define void @Plus.constructor(%Plus* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Plus.vtable to void (...)**
	%this.vtable = getelementptr %Plus, %Plus* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%left = getelementptr %Plus, %Plus* %this, i32 0, i32 1
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Plus, %Plus* %this, i32 0, i32 2
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}

define i32 @Plus.operator(%Plus* %self, i32 %a, i32 %b) { 
Plus.operator_entry:
	%tmp. = add i32 %a, %b
	ret i32 %tmp.
}
 ; --- Class Podziel ---
@Podziel.vtable = global [2 x void (...)*] [
	void (...)* bitcast (i32 (%Operator*)* @Operator.value to void (...)*) , ; value 
	void (...)* bitcast (i32 (%Podziel*, i32, i32)* @Podziel.operator to void (...)*) ; operator 
]

%Podziel = type { 
	void (...)**; vtable
	,%Node*; left 
	,%Node*; right 
	}
define void @Podziel.constructor(%Podziel* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Podziel.vtable to void (...)**
	%this.vtable = getelementptr %Podziel, %Podziel* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%left = getelementptr %Podziel, %Podziel* %this, i32 0, i32 1
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Podziel, %Podziel* %this, i32 0, i32 2
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}

define i32 @Podziel.operator(%Podziel* %self, i32 %a, i32 %b) { 
Podziel.operator_entry:
	%tmp. = sdiv i32 %a, %b
	ret i32 %tmp.
}
 ; --- Class Minus ---
@Minus.vtable = global [2 x void (...)*] [
	void (...)* bitcast (i32 (%Operator*)* @Operator.value to void (...)*) , ; value 
	void (...)* bitcast (i32 (%Minus*, i32, i32)* @Minus.operator to void (...)*) ; operator 
]

%Minus = type { 
	void (...)**; vtable
	,%Node*; left 
	,%Node*; right 
	}
define void @Minus.constructor(%Minus* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @Minus.vtable to void (...)**
	%this.vtable = getelementptr %Minus, %Minus* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%left = getelementptr %Minus, %Minus* %this, i32 0, i32 1
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Minus, %Minus* %this, i32 0, i32 2
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}

define i32 @Minus.operator(%Minus* %self, i32 %a, i32 %b) { 
Minus.operator_entry:
	%tmp. = sub i32 %a, %b
	ret i32 %tmp.
}

define %Node* @minus(%Node* %n1, %Node* %n2) { 
minus_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Minus*
	call void @Minus.constructor(%Minus* %tmp..1)
	%tmp..3 = getelementptr %Minus, %Minus* %tmp..1, i32 0, i32 1
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Minus, %Minus* %tmp..1, i32 0, i32 2
	store %Node* %n2, %Node** %tmp..4
	ret %Minus* %tmp..1
}

define %Node* @liczba(i32 %l) { 
liczba_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %Liczba*
	call void @Liczba.constructor(%Liczba* %tmp..1)
	%tmp..3 = getelementptr %Liczba, %Liczba* %tmp..1, i32 0, i32 1
	store i32 %l, i32* %tmp..3
	ret %Liczba* %tmp..1
}

define %Node* @podziel(%Node* %n1, %Node* %n2) { 
podziel_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Podziel*
	call void @Podziel.constructor(%Podziel* %tmp..1)
	%tmp..3 = getelementptr %Podziel, %Podziel* %tmp..1, i32 0, i32 1
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Podziel, %Podziel* %tmp..1, i32 0, i32 2
	store %Node* %n2, %Node** %tmp..4
	ret %Podziel* %tmp..1
}

define i32 @main() { 
main_entry:
	%tmp..1 = call %Node* @liczba(i32 4)
	%tmp..3 = call %Node* @liczba(i32 3)
	%tmp..4 = call %Node* @minus(%Node* %tmp..1, %Node* %tmp..3)
	%tmp..6 = call %Node* @liczba(i32 2)
	%tmp..8 = call %Node* @liczba(i32 4)
	%tmp..10 = call %Node* @liczba(i32 2)
	%tmp..11 = call %Node* @podziel(%Node* %tmp..8, %Node* %tmp..10)
	%tmp..12 = call %Node* @razy(%Node* %tmp..6, %Node* %tmp..11)
	%tmp..13 = call %Node* @plus(%Node* %tmp..4, %Node* %tmp..12)
	%tmp..14 = getelementptr %Node, %Node* %tmp..13, i32 0, i32 0
	%tmp..15 = load void (...)**, void (...)*** %tmp..14
	%tmp..16 = getelementptr void (...)*, void (...)** %tmp..15, i32 0
	%tmp..17 = bitcast void (...)** %tmp..16 to i32 (%Node*)**
	%tmp..18 = load i32 (%Node*)*, i32 (%Node*)** %tmp..17
	%tmp..19 = call i32 %tmp..18(%Node* %tmp..13)
	call void @printInt(i32 %tmp..19)
	ret i32 0
}

define %Node* @razy(%Node* %n1, %Node* %n2) { 
razy_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Razy*
	call void @Razy.constructor(%Razy* %tmp..1)
	%tmp..3 = getelementptr %Razy, %Razy* %tmp..1, i32 0, i32 1
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Razy, %Razy* %tmp..1, i32 0, i32 2
	store %Node* %n2, %Node** %tmp..4
	ret %Razy* %tmp..1
}

define %Node* @plus(%Node* %n1, %Node* %n2) { 
plus_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Plus*
	call void @Plus.constructor(%Plus* %tmp..1)
	%tmp..3 = getelementptr %Plus, %Plus* %tmp..1, i32 0, i32 1
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Plus, %Plus* %tmp..1, i32 0, i32 2
	store %Node* %n2, %Node** %tmp..4
	ret %Plus* %tmp..1
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

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

@._runtime_error = internal constant [15 x i8] c"runtime error\0A\00"
declare void @exit(i32)
define void @error() {
entry:  %t0 = getelementptr [15 x i8], [15 x i8]* @._runtime_error, i32 0, i32 0
call void @printString(i8* %t0)

call void @exit(i32 1)
	ret void
}

declare i8* @malloc(i32)
