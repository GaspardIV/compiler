 ; --- Class X ---
%X = type { 
	i32; x 
}
define void @X.constructor(%X* %this) {
	%x = getelementptr %X, %X* %this, i32 0, i32 0
	store i32 0, i32* %x
	ret void
}

define i1 @X.bfalse(%X* %self, i32 %a) { 
X.bfalse_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 0
	%tmp..2 = load i32, i32* %tmp.
	%tmp..3 = add i32 %tmp..2, %a
	store i32 %tmp..3, i32* %tmp.
	ret i1 false
}

define i1 @X.btrue(%X* %self, i32 %a) { 
X.btrue_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 0
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
	%tmp..4 = call i1 @X.btrue(%X* %tmp..1, i32 1)
	br i1 %tmp..4, label %test_and.4_and, label %test_and.3_expr.end
test_and.4_and:
	%tmp..6 = call i1 @X.btrue(%X* %tmp..1, i32 2)
	br label %test_and.3_expr.end
test_and.3_expr.end:
	%tmp..9 = call i1 @X.btrue(%X* %tmp..1, i32 4)
	br i1 %tmp..9, label %test_and.8_and, label %test_and.7_expr.end
test_and.8_and:
	%tmp..11 = call i1 @X.bfalse(%X* %tmp..1, i32 8)
	br label %test_and.7_expr.end
test_and.7_expr.end:
	%tmp..14 = call i1 @X.bfalse(%X* %tmp..1, i32 16)
	br i1 %tmp..14, label %test_and.12_and, label %test_and.11_expr.end
test_and.12_and:
	%tmp..16 = call i1 @X.btrue(%X* %tmp..1, i32 32)
	br label %test_and.11_expr.end
test_and.11_expr.end:
	%tmp..19 = call i1 @X.bfalse(%X* %tmp..1, i32 64)
	br i1 %tmp..19, label %test_and.16_and, label %test_and.15_expr.end
test_and.16_and:
	%tmp..21 = call i1 @X.bfalse(%X* %tmp..1, i32 128)
	br label %test_and.15_expr.end
test_and.15_expr.end:
	%tmp..27 = getelementptr %X, %X* %tmp..1, i32 0, i32 0
	%tmp..28 = load i32, i32* %tmp..27
	%tmp..30 = icmp ne i32 %tmp..28, 95
	br i1 %tmp..30, label %test_and.17_if.true, label %test_and.19_if.end
test_and.17_if.true:
	call void @error()
	ret void
test_and.19_if.end:
	ret void
}

define void @test_or() { 
test_or_entry:
	%tmp. = call i8* @malloc(i32 32)
	%tmp..1 = bitcast i8* %tmp. to %X*
	call void @X.constructor(%X* %tmp..1)
	%tmp..4 = call i1 @X.btrue(%X* %tmp..1, i32 1)
	br i1 %tmp..4, label %test_or.3_expr.end, label %test_or.4_or
test_or.4_or:
	%tmp..6 = call i1 @X.btrue(%X* %tmp..1, i32 2)
	br label %test_or.3_expr.end
test_or.3_expr.end:
	%tmp..9 = call i1 @X.btrue(%X* %tmp..1, i32 4)
	br i1 %tmp..9, label %test_or.7_expr.end, label %test_or.8_or
test_or.8_or:
	%tmp..11 = call i1 @X.bfalse(%X* %tmp..1, i32 8)
	br label %test_or.7_expr.end
test_or.7_expr.end:
	%tmp..14 = call i1 @X.bfalse(%X* %tmp..1, i32 16)
	br i1 %tmp..14, label %test_or.11_expr.end, label %test_or.12_or
test_or.12_or:
	%tmp..16 = call i1 @X.btrue(%X* %tmp..1, i32 32)
	br label %test_or.11_expr.end
test_or.11_expr.end:
	%tmp..19 = call i1 @X.bfalse(%X* %tmp..1, i32 64)
	br i1 %tmp..19, label %test_or.15_expr.end, label %test_or.16_or
test_or.16_or:
	%tmp..21 = call i1 @X.bfalse(%X* %tmp..1, i32 128)
	br label %test_or.15_expr.end
test_or.15_expr.end:
	%tmp..27 = getelementptr %X, %X* %tmp..1, i32 0, i32 0
	%tmp..28 = load i32, i32* %tmp..27
	%tmp..30 = icmp ne i32 %tmp..28, 245
	br i1 %tmp..30, label %test_or.17_if.true, label %test_or.19_if.end
test_or.17_if.true:
	call void @error()
	ret void
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
