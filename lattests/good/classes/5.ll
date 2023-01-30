 ; --- Class Point3 ---
@Point3.vtable = global [5 x void (...)*] [
	void (...)* bitcast (void (%Point2*, i32, i32)* @Point2.move to void (...)*) , ; move 
	void (...)* bitcast (i32 (%Point2*)* @Point2.getX to void (...)*) , ; getX 
	void (...)* bitcast (i32 (%Point2*)* @Point2.getY to void (...)*) , ; getY 
	void (...)* bitcast (void (%Point3*, i32)* @Point3.moveZ to void (...)*) , ; moveZ 
	void (...)* bitcast (i32 (%Point3*)* @Point3.getZ to void (...)*) ; getZ 
]

%Point3 = type { 
	void (...)**; vtable
	,i32; x 
	,i32; y 
	,i32; z 
	}
 ; --- Class Point2 ---
@Point2.vtable = global [3 x void (...)*] [
	void (...)* bitcast (void (%Point2*, i32, i32)* @Point2.move to void (...)*) , ; move 
	void (...)* bitcast (i32 (%Point2*)* @Point2.getX to void (...)*) , ; getX 
	void (...)* bitcast (i32 (%Point2*)* @Point2.getY to void (...)*) ; getY 
]

%Point2 = type { 
	void (...)**; vtable
	,i32; x 
	,i32; y 
	}
 ; --- Class Point4 ---
@Point4.vtable = global [7 x void (...)*] [
	void (...)* bitcast (void (%Point2*, i32, i32)* @Point2.move to void (...)*) , ; move 
	void (...)* bitcast (i32 (%Point2*)* @Point2.getX to void (...)*) , ; getX 
	void (...)* bitcast (i32 (%Point2*)* @Point2.getY to void (...)*) , ; getY 
	void (...)* bitcast (void (%Point3*, i32)* @Point3.moveZ to void (...)*) , ; moveZ 
	void (...)* bitcast (i32 (%Point3*)* @Point3.getZ to void (...)*) , ; getZ 
	void (...)* bitcast (void (%Point4*, i32)* @Point4.moveW to void (...)*) , ; moveW 
	void (...)* bitcast (i32 (%Point4*)* @Point4.getW to void (...)*) ; getW 
]

%Point4 = type { 
	void (...)**; vtable
	,i32; x 
	,i32; y 
	,i32; z 
	,i32; w 
	}
 ; --- Class Point3 methods ---
define void @Point3.constructor(%Point3* %this) {
	%this.class.vtable = bitcast [5 x void (...)*]* @Point3.vtable to void (...)**
	%this.vtable = getelementptr %Point3, %Point3* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %Point3, %Point3* %this, i32 0, i32 1
	store i32 0, i32* %x
	%y = getelementptr %Point3, %Point3* %this, i32 0, i32 2
	store i32 0, i32* %y
	%z = getelementptr %Point3, %Point3* %this, i32 0, i32 3
	store i32 0, i32* %z
	ret void
}

define i32 @Point3.getZ(%Point3* %self) { 
Point3.getZ_entry:
	%tmp. = getelementptr %Point3, %Point3* %self, i32 0, i32 3
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define void @Point3.moveZ(%Point3* %self, i32 %dz) { 
Point3.moveZ_entry:
	%tmp. = getelementptr %Point3, %Point3* %self, i32 0, i32 3
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dz
	store i32 %tmp..3, i32* %tmp.
	ret void
}
 ; --- Class Point2 methods ---
define void @Point2.constructor(%Point2* %this) {
	%this.class.vtable = bitcast [3 x void (...)*]* @Point2.vtable to void (...)**
	%this.vtable = getelementptr %Point2, %Point2* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %Point2, %Point2* %this, i32 0, i32 1
	store i32 0, i32* %x
	%y = getelementptr %Point2, %Point2* %this, i32 0, i32 2
	store i32 0, i32* %y
	ret void
}

define void @Point2.move(%Point2* %self, i32 %dx, i32 %dy) { 
Point2.move_entry:
	%tmp. = getelementptr %Point2, %Point2* %self, i32 0, i32 1
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dx
	store i32 %tmp..3, i32* %tmp.
	%tmp..4 = getelementptr %Point2, %Point2* %self, i32 0, i32 2
	%tmp..6 = load i32, i32* %tmp..4
	%tmp..7 = add i32 %tmp..6, %dy
	store i32 %tmp..7, i32* %tmp..4
	ret void
}

define i32 @Point2.getX(%Point2* %self) { 
Point2.getX_entry:
	%tmp. = getelementptr %Point2, %Point2* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define i32 @Point2.getY(%Point2* %self) { 
Point2.getY_entry:
	%tmp. = getelementptr %Point2, %Point2* %self, i32 0, i32 2
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}
 ; --- Class Point4 methods ---
define void @Point4.constructor(%Point4* %this) {
	%this.class.vtable = bitcast [7 x void (...)*]* @Point4.vtable to void (...)**
	%this.vtable = getelementptr %Point4, %Point4* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %Point4, %Point4* %this, i32 0, i32 1
	store i32 0, i32* %x
	%y = getelementptr %Point4, %Point4* %this, i32 0, i32 2
	store i32 0, i32* %y
	%z = getelementptr %Point4, %Point4* %this, i32 0, i32 3
	store i32 0, i32* %z
	%w = getelementptr %Point4, %Point4* %this, i32 0, i32 4
	store i32 0, i32* %w
	ret void
}

define i32 @Point4.getW(%Point4* %self) { 
Point4.getW_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 4
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}

define void @Point4.moveW(%Point4* %self, i32 %dw) { 
Point4.moveW_entry:
	%tmp. = getelementptr %Point4, %Point4* %self, i32 0, i32 4
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %dw
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
	%tmp..9 = getelementptr %Point3, %Point3* %tmp..4, i32 0, i32 0
	%tmp..10 = load void (...)**, void (...)*** %tmp..9
	%tmp..11 = getelementptr void (...)*, void (...)** %tmp..10, i32 0
	%tmp..12 = bitcast void (...)** %tmp..11 to void (%Point2*, i32, i32)**
	%tmp..13 = load void (%Point2*, i32, i32)*, void (%Point2*, i32, i32)** %tmp..12
	%tmp..14 = bitcast %Point3* %tmp..4 to %Point2*
	call void %tmp..13(%Point2* %tmp..14, i32 2, i32 4)
	%tmp..19 = load void (...)**, void (...)*** %tmp..9
	%tmp..20 = getelementptr void (...)*, void (...)** %tmp..19, i32 3
	%tmp..21 = bitcast void (...)** %tmp..20 to void (%Point3*, i32)**
	%tmp..22 = load void (%Point3*, i32)*, void (%Point3*, i32)** %tmp..21
	call void %tmp..22(%Point3* %tmp..4, i32 7)
	%tmp..26 = load void (...)**, void (...)*** %tmp..9
	%tmp..27 = getelementptr void (...)*, void (...)** %tmp..26, i32 0
	%tmp..28 = bitcast void (...)** %tmp..27 to void (%Point2*, i32, i32)**
	%tmp..29 = load void (%Point2*, i32, i32)*, void (%Point2*, i32, i32)** %tmp..28
	%tmp..30 = bitcast %Point3* %tmp..4 to %Point2*
	call void %tmp..29(%Point2* %tmp..30, i32 3, i32 5)
	%tmp..34 = getelementptr %Point4, %Point4* %tmp..7, i32 0, i32 0
	%tmp..35 = load void (...)**, void (...)*** %tmp..34
	%tmp..36 = getelementptr void (...)*, void (...)** %tmp..35, i32 0
	%tmp..37 = bitcast void (...)** %tmp..36 to void (%Point2*, i32, i32)**
	%tmp..38 = load void (%Point2*, i32, i32)*, void (%Point2*, i32, i32)** %tmp..37
	%tmp..39 = bitcast %Point4* %tmp..7 to %Point2*
	call void %tmp..38(%Point2* %tmp..39, i32 1, i32 3)
	%tmp..44 = load void (...)**, void (...)*** %tmp..34
	%tmp..45 = getelementptr void (...)*, void (...)** %tmp..44, i32 3
	%tmp..46 = bitcast void (...)** %tmp..45 to void (%Point3*, i32)**
	%tmp..47 = load void (%Point3*, i32)*, void (%Point3*, i32)** %tmp..46
	%tmp..48 = bitcast %Point4* %tmp..7 to %Point3*
	call void %tmp..47(%Point3* %tmp..48, i32 6)
	%tmp..52 = load void (...)**, void (...)*** %tmp..34
	%tmp..53 = getelementptr void (...)*, void (...)** %tmp..52, i32 5
	%tmp..54 = bitcast void (...)** %tmp..53 to void (%Point4*, i32)**
	%tmp..55 = load void (%Point4*, i32)*, void (%Point4*, i32)** %tmp..54
	call void %tmp..55(%Point4* %tmp..7, i32 2)
	%tmp..59 = load void (...)**, void (...)*** %tmp..9
	%tmp..60 = getelementptr void (...)*, void (...)** %tmp..59, i32 1
	%tmp..61 = bitcast void (...)** %tmp..60 to i32 (%Point2*)**
	%tmp..62 = load i32 (%Point2*)*, i32 (%Point2*)** %tmp..61
	%tmp..63 = bitcast %Point3* %tmp..4 to %Point2*
	%tmp..64 = call i32 %tmp..62(%Point2* %tmp..63)
	call void @printInt(i32 %tmp..64)
	%tmp..67 = load void (...)**, void (...)*** %tmp..9
	%tmp..68 = getelementptr void (...)*, void (...)** %tmp..67, i32 2
	%tmp..69 = bitcast void (...)** %tmp..68 to i32 (%Point2*)**
	%tmp..70 = load i32 (%Point2*)*, i32 (%Point2*)** %tmp..69
	%tmp..71 = bitcast %Point3* %tmp..4 to %Point2*
	%tmp..72 = call i32 %tmp..70(%Point2* %tmp..71)
	call void @printInt(i32 %tmp..72)
	%tmp..75 = load void (...)**, void (...)*** %tmp..9
	%tmp..76 = getelementptr void (...)*, void (...)** %tmp..75, i32 4
	%tmp..77 = bitcast void (...)** %tmp..76 to i32 (%Point3*)**
	%tmp..78 = load i32 (%Point3*)*, i32 (%Point3*)** %tmp..77
	%tmp..79 = call i32 %tmp..78(%Point3* %tmp..4)
	call void @printInt(i32 %tmp..79)
	%tmp..82 = load void (...)**, void (...)*** %tmp..34
	%tmp..83 = getelementptr void (...)*, void (...)** %tmp..82, i32 6
	%tmp..84 = bitcast void (...)** %tmp..83 to i32 (%Point4*)**
	%tmp..85 = load i32 (%Point4*)*, i32 (%Point4*)** %tmp..84
	%tmp..86 = call i32 %tmp..85(%Point4* %tmp..7)
	call void @printInt(i32 %tmp..86)
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
