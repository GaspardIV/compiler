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
	call void %tmp..13(%Point3* %tmp..4, i32 2, i32 4)
	%tmp..18 = load void (...)**, void (...)*** %tmp..9
	%tmp..19 = getelementptr void (...)*, void (...)** %tmp..18, i32 3
	%tmp..20 = bitcast void (...)** %tmp..19 to void (%Point3*, i32)**
	%tmp..21 = load void (%Point3*, i32)*, void (%Point3*, i32)** %tmp..20
	call void %tmp..21(%Point3* %tmp..4, i32 7)
	%tmp..25 = load void (...)**, void (...)*** %tmp..9
	%tmp..26 = getelementptr void (...)*, void (...)** %tmp..25, i32 0
	%tmp..27 = bitcast void (...)** %tmp..26 to void (%Point2*, i32, i32)**
	%tmp..28 = load void (%Point2*, i32, i32)*, void (%Point2*, i32, i32)** %tmp..27
	call void %tmp..28(%Point3* %tmp..4, i32 3, i32 5)
	%tmp..32 = getelementptr %Point4, %Point4* %tmp..7, i32 0, i32 0
	%tmp..33 = load void (...)**, void (...)*** %tmp..32
	%tmp..34 = getelementptr void (...)*, void (...)** %tmp..33, i32 0
	%tmp..35 = bitcast void (...)** %tmp..34 to void (%Point2*, i32, i32)**
	%tmp..36 = load void (%Point2*, i32, i32)*, void (%Point2*, i32, i32)** %tmp..35
	call void %tmp..36(%Point4* %tmp..7, i32 1, i32 3)
	%tmp..41 = load void (...)**, void (...)*** %tmp..32
	%tmp..42 = getelementptr void (...)*, void (...)** %tmp..41, i32 3
	%tmp..43 = bitcast void (...)** %tmp..42 to void (%Point3*, i32)**
	%tmp..44 = load void (%Point3*, i32)*, void (%Point3*, i32)** %tmp..43
	call void %tmp..44(%Point4* %tmp..7, i32 6)
	%tmp..48 = load void (...)**, void (...)*** %tmp..32
	%tmp..49 = getelementptr void (...)*, void (...)** %tmp..48, i32 5
	%tmp..50 = bitcast void (...)** %tmp..49 to void (%Point4*, i32)**
	%tmp..51 = load void (%Point4*, i32)*, void (%Point4*, i32)** %tmp..50
	call void %tmp..51(%Point4* %tmp..7, i32 2)
	%tmp..55 = load void (...)**, void (...)*** %tmp..9
	%tmp..56 = getelementptr void (...)*, void (...)** %tmp..55, i32 1
	%tmp..57 = bitcast void (...)** %tmp..56 to i32 (%Point2*)**
	%tmp..58 = load i32 (%Point2*)*, i32 (%Point2*)** %tmp..57
	%tmp..59 = call i32 %tmp..58(%Point3* %tmp..4)
	call void @printInt(i32 %tmp..59)
	%tmp..62 = load void (...)**, void (...)*** %tmp..9
	%tmp..63 = getelementptr void (...)*, void (...)** %tmp..62, i32 2
	%tmp..64 = bitcast void (...)** %tmp..63 to i32 (%Point2*)**
	%tmp..65 = load i32 (%Point2*)*, i32 (%Point2*)** %tmp..64
	%tmp..66 = call i32 %tmp..65(%Point3* %tmp..4)
	call void @printInt(i32 %tmp..66)
	%tmp..69 = load void (...)**, void (...)*** %tmp..9
	%tmp..70 = getelementptr void (...)*, void (...)** %tmp..69, i32 4
	%tmp..71 = bitcast void (...)** %tmp..70 to i32 (%Point3*)**
	%tmp..72 = load i32 (%Point3*)*, i32 (%Point3*)** %tmp..71
	%tmp..73 = call i32 %tmp..72(%Point3* %tmp..4)
	call void @printInt(i32 %tmp..73)
	%tmp..76 = load void (...)**, void (...)*** %tmp..32
	%tmp..77 = getelementptr void (...)*, void (...)** %tmp..76, i32 6
	%tmp..78 = bitcast void (...)** %tmp..77 to i32 (%Point4*)**
	%tmp..79 = load i32 (%Point4*)*, i32 (%Point4*)** %tmp..78
	%tmp..80 = call i32 %tmp..79(%Point4* %tmp..7)
	call void @printInt(i32 %tmp..80)
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
