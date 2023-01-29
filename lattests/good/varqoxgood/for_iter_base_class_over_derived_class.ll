 ; --- Class X ---
%X = type { 
	i32; x 
}
define void @X.constructor(%X* %this) {
	%x = getelementptr %X, %X* %this, i32 0, i32 0
	store i32 0, i32* %x
	ret void
}

define void @X.foo(%X* %self) { 
X.foo_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 0
	store i32 42, i32* %tmp.
	ret void
}
 ; --- Class Y ---
%Y = type { 
	i32; x 
}
define void @Y.constructor(%Y* %this) {
	%x = getelementptr %Y, %Y* %this, i32 0, i32 0
	store i32 0, i32* %x
	ret void
}

define void @Y.foo(%Y* %self) { 
Y.foo_entry:
	%tmp. = getelementptr %Y, %Y* %self, i32 0, i32 0
	store i32 42, i32* %tmp.
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
	store %Y* %tmp..15, %Y** %tmp..13
	%tmp..19 = bitcast %X** %tmp..6 to i8*
	%tmp..18 = getelementptr i8, i8* %tmp..19, i32 -4
	%tmp..20 = bitcast i8* %tmp..18 to i32*
	%tmp..21 = load i32, i32* %tmp..20
	br label %main.1_for.cond
main.1_for.cond:
	%i.d.x..1 = phi i32 [0, %main_entry], [%tmp..26, %main.6_if.end]
	%iter = phi i32 [0, %main_entry], [%tmp..53, %main.6_if.end]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..21
	br i1 %ifres., label %main.2_for.body, label %main.3_for.end
main.2_for.body:
	%tmp..23 = getelementptr %X*, %X** %tmp..6, i32 %i.d.x..1
	%tmp..24 = load %X*, %X** %tmp..23
	%tmp..26 = add i32 %i.d.x..1, 1
	call void @X.foo(%X* %tmp..24)
	%tmp..31 = icmp eq i32 %iter, 0
	br i1 %tmp..31, label %main.4_if.true, label %main.5_if.false
main.4_if.true:
	%tmp..36 = getelementptr %X, %X* %tmp..24, i32 0, i32 0
	%tmp..37 = load i32, i32* %tmp..36
	%tmp..39 = icmp ne i32 %tmp..37, 42
	br i1 %tmp..39, label %main.7_if.true, label %main.9_if.end
main.7_if.true:
	call void @error()
	ret i32 0
main.5_if.false:
	%tmp..46 = getelementptr %X, %X* %tmp..24, i32 0, i32 0
	%tmp..47 = load i32, i32* %tmp..46
	%tmp..49 = icmp ne i32 %tmp..47, 142
	br i1 %tmp..49, label %main.10_if.true, label %main.12_if.end
main.10_if.true:
	call void @error()
	ret i32 0
main.6_if.end:
	%tmp..53 = add i32 %iter, 1
	br label %main.1_for.cond
main.3_for.end:
	%tmp..55 = mul i32 1, 8
	%tmp..56 = add i32 %tmp..55, 4
	%tmp..57 = call i8* @calloc(i32 1, i32 %tmp..56)
	%tmp..58 = bitcast i8* %tmp..57 to i32*
	store i32 1, i32* %tmp..58
	%tmp..59 = getelementptr i8, i8* %tmp..57, i32 4
	%tmp..60 = bitcast i8* %tmp..59 to %Y**
	%tmp..62 = getelementptr %Y*, %Y** %tmp..60, i32 0
	%tmp..63 = call i8* @malloc(i32 32)
	%tmp..64 = bitcast i8* %tmp..63 to %Y*
	call void @Y.constructor(%Y* %tmp..64)
	store %Y* %tmp..64, %Y** %tmp..62
	%tmp..67 = bitcast %Y** %tmp..60 to i8*
	%tmp..66 = getelementptr i8, i8* %tmp..67, i32 -4
	%tmp..68 = bitcast i8* %tmp..66 to i32*
	%tmp..69 = load i32, i32* %tmp..68
	br label %main.13_for.cond
main.13_for.cond:
	%i.d.x..2 = phi i32 [0, %main.3_for.end], [%tmp..74, %main.18_if.end]
	%ifres..1 = icmp slt i32 %i.d.x..2, %tmp..69
	br i1 %ifres..1, label %main.14_for.body, label %main.15_for.end
main.14_for.body:
	%tmp..71 = getelementptr %Y*, %Y** %tmp..60, i32 %i.d.x..2
	%tmp..72 = load %Y*, %Y** %tmp..71
	%tmp..74 = add i32 %i.d.x..2, 1
	call void @Y.foo(%Y* %tmp..72)
	%tmp..80 = getelementptr %Y, %Y* %tmp..72, i32 0, i32 0
	%tmp..81 = load i32, i32* %tmp..80
	%tmp..83 = icmp ne i32 %tmp..81, 142
	br i1 %tmp..83, label %main.16_if.true, label %main.18_if.end
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
