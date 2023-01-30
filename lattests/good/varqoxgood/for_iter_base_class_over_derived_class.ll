 ; --- Class X ---
@X.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%X*)* @X.foo to void (...)*) ; foo 
]

%X = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class Y ---
@Y.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%Y*)* @Y.foo to void (...)*) ; foo 
]

%Y = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %X, %X* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define void @X.foo(%X* %self) { 
X.foo_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 1
	store i32 42, i32* %tmp.
	ret void
}
 ; --- Class Y methods ---
define void @Y.constructor(%Y* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @Y.vtable to void (...)**
	%this.vtable = getelementptr %Y, %Y* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %Y, %Y* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define void @Y.foo(%Y* %self) { 
Y.foo_entry:
	%tmp. = getelementptr %Y, %Y* %self, i32 0, i32 1
	store i32 142, i32* %tmp.
	ret void
}

define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 2, 8
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 2, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to %X**
	%tmp..8 = getelementptr %X*, %X** %tmp..6, i32 0
	%tmp..9 = call i8* @malloc(i32 32)
	%tmp..10 = bitcast i8* %tmp..9 to %X*
	call void @X.constructor(%X* %tmp..10)
	store %X* %tmp..10, %X** %tmp..8
	%tmp..13 = getelementptr %X*, %X** %tmp..6, i32 1
	%tmp..14 = call i8* @malloc(i32 32)
	%tmp..15 = bitcast i8* %tmp..14 to %Y*
	call void @Y.constructor(%Y* %tmp..15)
	%tmp..17 = bitcast %Y* %tmp..15 to %X*
	store %X* %tmp..17, %X** %tmp..13
	%tmp..20 = bitcast %X** %tmp..6 to i8*
	%tmp..19 = getelementptr i8, i8* %tmp..20, i32 -4
	%tmp..21 = bitcast i8* %tmp..19 to i32*
	%tmp..22 = load i32, i32* %tmp..21
	br label %main.1_for.cond
main.1_for.cond:
	%i.d.x..1 = phi i32 [0, %main_entry], [%tmp..27, %main.6_if.end]
	%iter = phi i32 [0, %main_entry], [%tmp..59, %main.6_if.end]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..22
	br i1 %ifres., label %main.2_for.body, label %main.3_for.end
main.2_for.body:
	%tmp..24 = getelementptr %X*, %X** %tmp..6, i32 %i.d.x..1
	%tmp..25 = load %X*, %X** %tmp..24
	%tmp..27 = add i32 %i.d.x..1, 1
	%tmp..28 = getelementptr %X, %X* %tmp..25, i32 0, i32 0
	%tmp..29 = load void (...)**, void (...)*** %tmp..28
	%tmp..30 = getelementptr void (...)*, void (...)** %tmp..29, i32 0
	%tmp..31 = bitcast void (...)** %tmp..30 to void (%X*)**
	%tmp..32 = load void (%X*)*, void (%X*)** %tmp..31
	call void %tmp..32(%X* %tmp..25)
	%tmp..37 = icmp eq i32 %iter, 0
	br i1 %tmp..37, label %main.4_if.true, label %main.5_if.false
main.4_if.true:
	%tmp..42 = getelementptr %X, %X* %tmp..25, i32 0, i32 1
	%tmp..43 = load i32, i32* %tmp..42
	%tmp..45 = icmp ne i32 %tmp..43, 42
	br i1 %tmp..45, label %main.7_if.true, label %main.6_if.end
main.7_if.true:
	call void @error()
	ret i32 0
main.5_if.false:
	%tmp..52 = getelementptr %X, %X* %tmp..25, i32 0, i32 1
	%tmp..53 = load i32, i32* %tmp..52
	%tmp..55 = icmp ne i32 %tmp..53, 142
	br i1 %tmp..55, label %main.10_if.true, label %main.6_if.end
main.10_if.true:
	call void @error()
	ret i32 0
main.6_if.end:
	%tmp..59 = add i32 %iter, 1
	br label %main.1_for.cond
main.3_for.end:
	%tmp..61 = mul i32 1, 8
	%tmp..62 = add i32 %tmp..61, 4
	%tmp..63 = call i8* @calloc(i32 1, i32 %tmp..62)
	%tmp..64 = bitcast i8* %tmp..63 to i32*
	store i32 1, i32* %tmp..64
	%tmp..65 = getelementptr i8, i8* %tmp..63, i32 4
	%tmp..66 = bitcast i8* %tmp..65 to %Y**
	%tmp..68 = getelementptr %Y*, %Y** %tmp..66, i32 0
	%tmp..69 = call i8* @malloc(i32 32)
	%tmp..70 = bitcast i8* %tmp..69 to %Y*
	call void @Y.constructor(%Y* %tmp..70)
	store %Y* %tmp..70, %Y** %tmp..68
	%tmp..73 = bitcast %Y** %tmp..66 to i8*
	%tmp..72 = getelementptr i8, i8* %tmp..73, i32 -4
	%tmp..74 = bitcast i8* %tmp..72 to i32*
	%tmp..75 = load i32, i32* %tmp..74
	br label %main.13_for.cond
main.13_for.cond:
	%i.d.x..2 = phi i32 [0, %main.3_for.end], [%tmp..80, %main.18_if.end]
	%ifres..1 = icmp slt i32 %i.d.x..2, %tmp..75
	br i1 %ifres..1, label %main.14_for.body, label %main.15_for.end
main.14_for.body:
	%tmp..77 = getelementptr %Y*, %Y** %tmp..66, i32 %i.d.x..2
	%tmp..78 = load %Y*, %Y** %tmp..77
	%tmp..80 = add i32 %i.d.x..2, 1
	%tmp..81 = getelementptr %Y, %Y* %tmp..78, i32 0, i32 0
	%tmp..82 = load void (...)**, void (...)*** %tmp..81
	%tmp..83 = getelementptr void (...)*, void (...)** %tmp..82, i32 0
	%tmp..84 = bitcast void (...)** %tmp..83 to void (%Y*)**
	%tmp..85 = load void (%Y*)*, void (%Y*)** %tmp..84
	call void %tmp..85(%Y* %tmp..78)
	%tmp..91 = getelementptr %Y, %Y* %tmp..78, i32 0, i32 1
	%tmp..92 = load i32, i32* %tmp..91
	%tmp..94 = icmp ne i32 %tmp..92, 142
	br i1 %tmp..94, label %main.16_if.true, label %main.18_if.end
main.16_if.true:
	call void @error()
	ret i32 0
main.18_if.end:
	br label %main.13_for.cond
main.15_for.end:
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

@._runtime_error = internal constant [15 x i8] c"runtime error\0A\00"
declare void @exit(i32)
define void @error() {
entry:  %t0 = getelementptr [15 x i8], [15 x i8]* @._runtime_error, i32 0, i32 0
call void @printString(i8* %t0)

call void @exit(i32 1)
	ret void
}

declare i8* @calloc(i32, i32)
declare i8* @malloc(i32)
