@.str.str4 = private unnamed_addr constant [20 x i8] c"I'm really a square\00", align 1@.str.str2 = private unnamed_addr constant [17 x i8] c"I'm just a shape\00", align 1@.str.str3 = private unnamed_addr constant [23 x i8] c"I'm really a rectangle\00", align 1@.str.str0 = private unnamed_addr constant [24 x i8] c"I'm really a circle11`1\00", align 1@.str.str1 = private unnamed_addr constant [12 x i8] c"I'm a shape\00", align 1 ; --- Class Circle ---
%Circle = type { 
	i32; r 
}
define void @Circle.constructor(%Circle* %this) {
	%r = getelementptr %Circle, %Circle* %this, i32 0, i32 0
	store i32 0, i32* %r
	ret void
}

define void @Circle.tellAgain(%Circle* %self) { 
Circle.tellAgain_entry:
	%tmp. = getelementptr [24 x i8], [24 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @Circle.tell(%Circle* %self) { 
Circle.tell_entry:
	%tmp. = getelementptr [12 x i8], [12 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class Shape ---
%Shape = type { 
}
define void @Shape.constructor(%Shape* %this) {
	ret void
}

define void @Shape.tellAgain(%Shape* %self) { 
Shape.tellAgain_entry:
	%tmp. = getelementptr [17 x i8], [17 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @Shape.tell(%Shape* %self) { 
Shape.tell_entry:
	%tmp. = getelementptr [12 x i8], [12 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class Rectangle ---
%Rectangle = type { 
	i32, ; a 
	i32; b 
}
define void @Rectangle.constructor(%Rectangle* %this) {
	%a = getelementptr %Rectangle, %Rectangle* %this, i32 0, i32 0
	store i32 0, i32* %a
	%b = getelementptr %Rectangle, %Rectangle* %this, i32 0, i32 1
	store i32 0, i32* %b
	ret void
}

define void @Rectangle.tellAgain(%Rectangle* %self) { 
Rectangle.tellAgain_entry:
	%tmp. = getelementptr [23 x i8], [23 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @Rectangle.tell(%Rectangle* %self) { 
Rectangle.tell_entry:
	%tmp. = getelementptr [12 x i8], [12 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}
 ; --- Class Square ---
%Square = type { 
	i32, ; a 
	i32, ; b 
	i32; color 
}
define void @Square.constructor(%Square* %this) {
	%a = getelementptr %Square, %Square* %this, i32 0, i32 0
	store i32 0, i32* %a
	%b = getelementptr %Square, %Square* %this, i32 0, i32 1
	store i32 0, i32* %b
	%color = getelementptr %Square, %Square* %this, i32 0, i32 2
	store i32 0, i32* %color
	ret void
}

define void @Square.tellAgain(%Square* %self) { 
Square.tellAgain_entry:
	%tmp. = getelementptr [20 x i8], [20 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define void @Square.tell(%Square* %self) { 
Square.tell_entry:
	%tmp. = getelementptr [12 x i8], [12 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %Shape*
	call void @Shape.constructor(%Shape* %tmp..1)
	%tmp..3 = call i8* @malloc(i32 64)
	%tmp..4 = bitcast i8* %tmp..3 to %Rectangle*
	call void @Rectangle.constructor(%Rectangle* %tmp..4)
	%tmp..6 = call i8* @malloc(i32 96)
	%tmp..7 = bitcast i8* %tmp..6 to %Square*
	call void @Square.constructor(%Square* %tmp..7)
	%tmp..9 = call i8* @malloc(i32 32)
	%tmp..10 = bitcast i8* %tmp..9 to %Circle*
	call void @Circle.constructor(%Circle* %tmp..10)
	call void @Circle.tell(%Circle* %tmp..10)
	call void @Circle.tellAgain(%Circle* %tmp..10)
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
