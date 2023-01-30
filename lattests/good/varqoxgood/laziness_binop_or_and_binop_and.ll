 ; --- Class X ---
@X.vtable = global [2 x void (...)*] [
	void (...)* bitcast (i1 (%X*, i32)* @X.btrue to void (...)*) , ; btrue 
	void (...)* bitcast (i1 (%X*, i32)* @X.bfalse to void (...)*) ; bfalse 
]

%X = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [2 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %X, %X* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define i1 @X.bfalse(%X* %self, i32 %a) { 
X.bfalse_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 1
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %a
	store i32 %tmp..3, i32* %tmp.
	ret i1 false
}

define i1 @X.btrue(%X* %self, i32 %a) { 
X.btrue_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 1
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %a
	store i32 %tmp..3, i32* %tmp.
	ret i1 true
}

define void @test_and() { 
test_and_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	%tmp..3 = getelementptr %X, %X* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to i1 (%X*, i32)**
	%tmp..7 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..6
	%tmp..9 = call i1 %tmp..7(%X* %tmp..1, i32 1)
	br i1 %tmp..9, label %test_and.4_and, label %test_and.2_expr.false
test_and.4_and:
	%tmp..11 = load void (...)**, void (...)*** %tmp..3
	%tmp..12 = getelementptr void (...)*, void (...)** %tmp..11, i32 0
	%tmp..13 = bitcast void (...)** %tmp..12 to i1 (%X*, i32)**
	%tmp..14 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..13
	%tmp..16 = call i1 %tmp..14(%X* %tmp..1, i32 2)
	br i1 %tmp..16, label %test_and.1_expr.true, label %test_and.2_expr.false
test_and.1_expr.true:
	br label %test_and.3_expr.end
test_and.2_expr.false:
	br label %test_and.3_expr.end
test_and.3_expr.end:
	%tmp..19 = load void (...)**, void (...)*** %tmp..3
	%tmp..20 = getelementptr void (...)*, void (...)** %tmp..19, i32 0
	%tmp..21 = bitcast void (...)** %tmp..20 to i1 (%X*, i32)**
	%tmp..22 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..21
	%tmp..24 = call i1 %tmp..22(%X* %tmp..1, i32 4)
	br i1 %tmp..24, label %test_and.8_and, label %test_and.6_expr.false
test_and.8_and:
	%tmp..26 = load void (...)**, void (...)*** %tmp..3
	%tmp..27 = getelementptr void (...)*, void (...)** %tmp..26, i32 1
	%tmp..28 = bitcast void (...)** %tmp..27 to i1 (%X*, i32)**
	%tmp..29 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..28
	%tmp..31 = call i1 %tmp..29(%X* %tmp..1, i32 8)
	br i1 %tmp..31, label %test_and.5_expr.true, label %test_and.6_expr.false
test_and.5_expr.true:
	br label %test_and.7_expr.end
test_and.6_expr.false:
	br label %test_and.7_expr.end
test_and.7_expr.end:
	%tmp..34 = load void (...)**, void (...)*** %tmp..3
	%tmp..35 = getelementptr void (...)*, void (...)** %tmp..34, i32 1
	%tmp..36 = bitcast void (...)** %tmp..35 to i1 (%X*, i32)**
	%tmp..37 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..36
	%tmp..39 = call i1 %tmp..37(%X* %tmp..1, i32 16)
	br i1 %tmp..39, label %test_and.12_and, label %test_and.10_expr.false
test_and.12_and:
	%tmp..41 = load void (...)**, void (...)*** %tmp..3
	%tmp..42 = getelementptr void (...)*, void (...)** %tmp..41, i32 0
	%tmp..43 = bitcast void (...)** %tmp..42 to i1 (%X*, i32)**
	%tmp..44 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..43
	%tmp..46 = call i1 %tmp..44(%X* %tmp..1, i32 32)
	br i1 %tmp..46, label %test_and.9_expr.true, label %test_and.10_expr.false
test_and.9_expr.true:
	br label %test_and.11_expr.end
test_and.10_expr.false:
	br label %test_and.11_expr.end
test_and.11_expr.end:
	%tmp..49 = load void (...)**, void (...)*** %tmp..3
	%tmp..50 = getelementptr void (...)*, void (...)** %tmp..49, i32 1
	%tmp..51 = bitcast void (...)** %tmp..50 to i1 (%X*, i32)**
	%tmp..52 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..51
	%tmp..54 = call i1 %tmp..52(%X* %tmp..1, i32 64)
	br i1 %tmp..54, label %test_and.16_and, label %test_and.14_expr.false
test_and.16_and:
	%tmp..56 = load void (...)**, void (...)*** %tmp..3
	%tmp..57 = getelementptr void (...)*, void (...)** %tmp..56, i32 1
	%tmp..58 = bitcast void (...)** %tmp..57 to i1 (%X*, i32)**
	%tmp..59 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..58
	%tmp..61 = call i1 %tmp..59(%X* %tmp..1, i32 128)
	br i1 %tmp..61, label %test_and.13_expr.true, label %test_and.14_expr.false
test_and.13_expr.true:
	br label %test_and.15_expr.end
test_and.14_expr.false:
	br label %test_and.15_expr.end
test_and.15_expr.end:
	%tmp..67 = getelementptr %X, %X* %tmp..1, i32 0, i32 1
	%tmp..68 = load i32, i32* %tmp..67
	%tmp..70 = icmp ne i32 %tmp..68, 95
	br i1 %tmp..70, label %test_and.17_if.true, label %test_and.18_if.false
test_and.17_if.true:
	call void @error()
	ret void
test_and.18_if.false:
	br label %test_and.19_if.end
test_and.19_if.end:
	ret void
}

define void @test_or() { 
test_or_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	%tmp..3 = getelementptr %X, %X* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 0
	%tmp..6 = bitcast void (...)** %tmp..5 to i1 (%X*, i32)**
	%tmp..7 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..6
	%tmp..9 = call i1 %tmp..7(%X* %tmp..1, i32 1)
	br i1 %tmp..9, label %test_or.1_expr.true, label %test_or.4_or
test_or.4_or:
	%tmp..11 = load void (...)**, void (...)*** %tmp..3
	%tmp..12 = getelementptr void (...)*, void (...)** %tmp..11, i32 0
	%tmp..13 = bitcast void (...)** %tmp..12 to i1 (%X*, i32)**
	%tmp..14 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..13
	%tmp..16 = call i1 %tmp..14(%X* %tmp..1, i32 2)
	br i1 %tmp..16, label %test_or.1_expr.true, label %test_or.2_expr.false
test_or.1_expr.true:
	br label %test_or.3_expr.end
test_or.2_expr.false:
	br label %test_or.3_expr.end
test_or.3_expr.end:
	%tmp..19 = load void (...)**, void (...)*** %tmp..3
	%tmp..20 = getelementptr void (...)*, void (...)** %tmp..19, i32 0
	%tmp..21 = bitcast void (...)** %tmp..20 to i1 (%X*, i32)**
	%tmp..22 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..21
	%tmp..24 = call i1 %tmp..22(%X* %tmp..1, i32 4)
	br i1 %tmp..24, label %test_or.5_expr.true, label %test_or.8_or
test_or.8_or:
	%tmp..26 = load void (...)**, void (...)*** %tmp..3
	%tmp..27 = getelementptr void (...)*, void (...)** %tmp..26, i32 1
	%tmp..28 = bitcast void (...)** %tmp..27 to i1 (%X*, i32)**
	%tmp..29 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..28
	%tmp..31 = call i1 %tmp..29(%X* %tmp..1, i32 8)
	br i1 %tmp..31, label %test_or.5_expr.true, label %test_or.6_expr.false
test_or.5_expr.true:
	br label %test_or.7_expr.end
test_or.6_expr.false:
	br label %test_or.7_expr.end
test_or.7_expr.end:
	%tmp..34 = load void (...)**, void (...)*** %tmp..3
	%tmp..35 = getelementptr void (...)*, void (...)** %tmp..34, i32 1
	%tmp..36 = bitcast void (...)** %tmp..35 to i1 (%X*, i32)**
	%tmp..37 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..36
	%tmp..39 = call i1 %tmp..37(%X* %tmp..1, i32 16)
	br i1 %tmp..39, label %test_or.9_expr.true, label %test_or.12_or
test_or.12_or:
	%tmp..41 = load void (...)**, void (...)*** %tmp..3
	%tmp..42 = getelementptr void (...)*, void (...)** %tmp..41, i32 0
	%tmp..43 = bitcast void (...)** %tmp..42 to i1 (%X*, i32)**
	%tmp..44 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..43
	%tmp..46 = call i1 %tmp..44(%X* %tmp..1, i32 32)
	br i1 %tmp..46, label %test_or.9_expr.true, label %test_or.10_expr.false
test_or.9_expr.true:
	br label %test_or.11_expr.end
test_or.10_expr.false:
	br label %test_or.11_expr.end
test_or.11_expr.end:
	%tmp..49 = load void (...)**, void (...)*** %tmp..3
	%tmp..50 = getelementptr void (...)*, void (...)** %tmp..49, i32 1
	%tmp..51 = bitcast void (...)** %tmp..50 to i1 (%X*, i32)**
	%tmp..52 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..51
	%tmp..54 = call i1 %tmp..52(%X* %tmp..1, i32 64)
	br i1 %tmp..54, label %test_or.13_expr.true, label %test_or.16_or
test_or.16_or:
	%tmp..56 = load void (...)**, void (...)*** %tmp..3
	%tmp..57 = getelementptr void (...)*, void (...)** %tmp..56, i32 1
	%tmp..58 = bitcast void (...)** %tmp..57 to i1 (%X*, i32)**
	%tmp..59 = load i1 (%X*, i32)*, i1 (%X*, i32)** %tmp..58
	%tmp..61 = call i1 %tmp..59(%X* %tmp..1, i32 128)
	br i1 %tmp..61, label %test_or.13_expr.true, label %test_or.14_expr.false
test_or.13_expr.true:
	br label %test_or.15_expr.end
test_or.14_expr.false:
	br label %test_or.15_expr.end
test_or.15_expr.end:
	%tmp..67 = getelementptr %X, %X* %tmp..1, i32 0, i32 1
	%tmp..68 = load i32, i32* %tmp..67
	%tmp..70 = icmp ne i32 %tmp..68, 245
	br i1 %tmp..70, label %test_or.17_if.true, label %test_or.18_if.false
test_or.17_if.true:
	call void @error()
	ret void
test_or.18_if.false:
	br label %test_or.19_if.end
test_or.19_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	call void @test_and()
	call void @test_or()
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

declare i8* @malloc(i32)
