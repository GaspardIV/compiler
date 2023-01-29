 ; --- Class Point3 ---
%Point3 = type { 
	i32, ; x 
	i32, ; y 
	i32; z 
}
define void @Point3.constructor(%Point3* %this) {
	%x = getelementptr %Point3, %Point3* %this, i32 0, i32 0
	store i32 0, i32* %x
	%y = getelementptr %Point3, %Point3* %this, i32 0, i32 1
	store i32 0, i32* %y
	%z = getelementptr %Point3, %Point3* %this, i32 0, i32 2
	store i32 0, i32* %z
	ret void
}

define void @Point3.move(%Point3* %self, i32 %dx, i32 %dy) { 
Point3.move_entry:
	%tmp. = getelementptr %Point3, %Point3* %self, i32 0, i32 0
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dx
	store i32 %tmp..3, i32* %tmp.
	%tmp..4 = getelementptr %Point3, %Point3* %self, i32 0, i32 1
	%tmp..6 = load i32, i32* %tmp..4
	%tmp..7 = add i32 %tmp..6, %dy
	store i32 %tmp..7, i32* %tmp..4
	ret void
}

define i32 @Point3.getX(%Point3* %self) { 
Point3.getX_entry:
	%tmp. = getelementptr %Point3, %Point3* %self, i32 0, i32 0
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define i32 @Point3.getY(%Point3* %self) { 
Point3.getY_entry:
	%tmp. = getelementptr %Point3, %Point3* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define i32 @Point3.getZ(%Point3* %self) { 
Point3.getZ_entry:
	%tmp. = getelementptr %Point3, %Point3* %self, i32 0, i32 2
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define void @Point3.moveZ(%Point3* %self, i32 %dz) { 
Point3.moveZ_entry:
	%tmp. = getelementptr %Point3, %Point3* %self, i32 0, i32 2
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dz
	store i32 %tmp..3, i32* %tmp.
	ret void
}
 ; --- Class Point2 ---
%Point2 = type { 
	i32, ; x 
	i32; y 
}
define void @Point2.constructor(%Point2* %this) {
	%x = getelementptr %Point2, %Point2* %this, i32 0, i32 0
	store i32 0, i32* %x
	%y = getelementptr %Point2, %Point2* %this, i32 0, i32 1
	store i32 0, i32* %y
	ret void
}

define void @Point2.move(%Point2* %self, i32 %dx, i32 %dy) { 
Point2.move_entry:
	%tmp. = getelementptr %Point2, %Point2* %self, i32 0, i32 0
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dx
	store i32 %tmp..3, i32* %tmp.
	%tmp..4 = getelementptr %Point2, %Point2* %self, i32 0, i32 1
	%tmp..6 = load i32, i32* %tmp..4
	%tmp..7 = add i32 %tmp..6, %dy
	store i32 %tmp..7, i32* %tmp..4
	ret void
}

define i32 @Point2.getX(%Point2* %self) { 
Point2.getX_entry:
	%tmp. = getelementptr %Point2, %Point2* %self, i32 0, i32 0
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define i32 @Point2.getY(%Point2* %self) { 
Point2.getY_entry:
	%tmp. = getelementptr %Point2, %Point2* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}
 ; --- Class Point4 ---
%Point4 = type { 
	i32, ; x 
	i32, ; y 
	i32, ; z 
	i32; w 
}
define void @Point4.constructor(%Point4* %this) {
	%x = getelementptr %Point4, %Point4* %this, i32 0, i32 0
	store i32 0, i32* %x
	%y = getelementptr %Point4, %Point4* %this, i32 0, i32 1
	store i32 0, i32* %y
	%z = getelementptr %Point4, %Point4* %this, i32 0, i32 2
	store i32 0, i32* %z
	%w = getelementptr %Point4, %Point4* %this, i32 0, i32 3
	store i32 0, i32* %w
	ret void
}

define i32 @Point4.getW(%Point4* %self) { 
Point4.getW_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 3
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define void @Point4.move(%Point4* %self, i32 %dx, i32 %dy) { 
Point4.move_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 0
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dx
	store i32 %tmp..3, i32* %tmp.
	%tmp..4 = getelementptr %Point4, %Point4* %self, i32 0, i32 1
	%tmp..6 = load i32, i32* %tmp..4
	%tmp..7 = add i32 %tmp..6, %dy
	store i32 %tmp..7, i32* %tmp..4
	ret void
}

define i32 @Point4.getX(%Point4* %self) { 
Point4.getX_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 0
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define void @Point4.moveW(%Point4* %self, i32 %dw) { 
Point4.moveW_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 3
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dw
	store i32 %tmp..3, i32* %tmp.
	ret void
}

define i32 @Point4.getY(%Point4* %self) { 
Point4.getY_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define i32 @Point4.getZ(%Point4* %self) { 
Point4.getZ_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 2
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define void @Point4.moveZ(%Point4* %self, i32 %dz) { 
Point4.moveZ_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 2
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dz
	store i32 %tmp..3, i32* %tmp.
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 96)
	%tmp..1 = bitcast i8* %tmp. to %Point3*
	call void @Point3.constructor(%Point3* %tmp..1)
	%tmp..3 = call i8* @malloc(i32 96)
	%tmp..4 = bitcast i8* %tmp..3 to %Point3*
	call void @Point3.constructor(%Point3* %tmp..4)
	%tmp..6 = call i8* @malloc(i32 128)
	%tmp..7 = bitcast i8* %tmp..6 to %Point4*
	call void @Point4.constructor(%Point4* %tmp..7)
	call void @Point3.move(%Point3* %tmp..4, i32 2, i32 4)
	call void @Point3.moveZ(%Point3* %tmp..4, i32 7)
	call void @Point3.move(%Point3* %tmp..4, i32 3, i32 5)
	call void @Point4.move(%Point4* %tmp..7, i32 1, i32 3)
	call void @Point4.moveZ(%Point4* %tmp..7, i32 6)
	call void @Point4.moveW(%Point4* %tmp..7, i32 2)
	%tmp..24 = call i32 @Point3.getX(%Point3* %tmp..4)
	call void @printInt(i32 %tmp..24)
	%tmp..26 = call i32 @Point3.getY(%Point3* %tmp..4)
	call void @printInt(i32 %tmp..26)
	%tmp..28 = call i32 @Point3.getZ(%Point3* %tmp..4)
	call void @printInt(i32 %tmp..28)
	%tmp..30 = call i32 @Point4.getW(%Point4* %tmp..7)
	call void @printInt(i32 %tmp..30)
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
