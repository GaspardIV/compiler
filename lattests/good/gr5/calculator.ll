 ; --- Class Operator ---
%Operator = type { 
	%Node*, ; left 
	%Node*; right 
}
define void @Operator.constructor(%Operator* %this) {
	%left = getelementptr %Operator, %Operator* %this, i32 0, i32 0
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Operator, %Operator* %this, i32 0, i32 1
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}
 ; --- Class Liczba ---
%Liczba = type { 
	i32; v 
}
define void @Liczba.constructor(%Liczba* %this) {
	%v = getelementptr %Liczba, %Liczba* %this, i32 0, i32 0
	store i32 0, i32* %v
	ret void
}
 ; --- Class Node ---
%Node = type { 
}
define void @Node.constructor(%Node* %this) {
	ret void
}

define i32 @Node.value(%Node* %self) { 
Node.value_entry:
	call void @error()
	ret i32 0
}
 ; --- Class Razy ---
%Razy = type { 
	%Node*, ; left 
	%Node*; right 
}
define void @Razy.constructor(%Razy* %this) {
	%left = getelementptr %Razy, %Razy* %this, i32 0, i32 0
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Razy, %Razy* %this, i32 0, i32 1
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}
 ; --- Class Plus ---
%Plus = type { 
	%Node*, ; left 
	%Node*; right 
}
define void @Plus.constructor(%Plus* %this) {
	%left = getelementptr %Plus, %Plus* %this, i32 0, i32 0
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Plus, %Plus* %this, i32 0, i32 1
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}
 ; --- Class Podziel ---
%Podziel = type { 
	%Node*, ; left 
	%Node*; right 
}
define void @Podziel.constructor(%Podziel* %this) {
	%left = getelementptr %Podziel, %Podziel* %this, i32 0, i32 0
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Podziel, %Podziel* %this, i32 0, i32 1
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}
 ; --- Class Minus ---
%Minus = type { 
	%Node*, ; left 
	%Node*; right 
}
define void @Minus.constructor(%Minus* %this) {
	%left = getelementptr %Minus, %Minus* %this, i32 0, i32 0
	%lefttmp = bitcast i32* null to %Node*
	store %Node* %lefttmp, %Node** %left
	%right = getelementptr %Minus, %Minus* %this, i32 0, i32 1
	%righttmp = bitcast i32* null to %Node*
	store %Node* %righttmp, %Node** %right
	ret void
}

define %Node* @minus(%Node* %n1, %Node* %n2) { 
minus_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Minus*
	call void @Minus.constructor(%Minus* %tmp..1)
	%tmp..3 = getelementptr %Minus, %Minus* %tmp..1, i32 0, i32 0
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Minus, %Minus* %tmp..1, i32 0, i32 1
	store %Node* %n2, %Node** %tmp..4
	ret %Minus* %tmp..1
}

define %Node* @liczba(i32 %l) { 
liczba_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %Liczba*
	call void @Liczba.constructor(%Liczba* %tmp..1)
	%tmp..3 = getelementptr %Liczba, %Liczba* %tmp..1, i32 0, i32 0
	store i32 %l, i32* %tmp..3
	ret %Liczba* %tmp..1
}

define %Node* @podziel(%Node* %n1, %Node* %n2) { 
podziel_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Podziel*
	call void @Podziel.constructor(%Podziel* %tmp..1)
	%tmp..3 = getelementptr %Podziel, %Podziel* %tmp..1, i32 0, i32 0
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Podziel, %Podziel* %tmp..1, i32 0, i32 1
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
	%tmp..14 = call i32 @Node.value(%Node* %tmp..13)
	call void @printInt(i32 %tmp..14)
	ret i32 0
}

define %Node* @razy(%Node* %n1, %Node* %n2) { 
razy_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Razy*
	call void @Razy.constructor(%Razy* %tmp..1)
	%tmp..3 = getelementptr %Razy, %Razy* %tmp..1, i32 0, i32 0
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Razy, %Razy* %tmp..1, i32 0, i32 1
	store %Node* %n2, %Node** %tmp..4
	ret %Razy* %tmp..1
}

define %Node* @plus(%Node* %n1, %Node* %n2) { 
plus_entry:
	%tmp. = call i8* @malloc(i32 128)
	%tmp..1 = bitcast i8* %tmp. to %Plus*
	call void @Plus.constructor(%Plus* %tmp..1)
	%tmp..3 = getelementptr %Plus, %Plus* %tmp..1, i32 0, i32 0
	store %Node* %n1, %Node** %tmp..3
	%tmp..4 = getelementptr %Plus, %Plus* %tmp..1, i32 0, i32 1
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
