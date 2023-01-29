@.str.str0 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str6 = private unnamed_addr constant [4 x i8] c"abc\00", align 1@.str.str4 = private unnamed_addr constant [7 x i8] c"\0Atest2\00", align 1@.str.str5 = private unnamed_addr constant [7 x i8] c"\0Atest3\00", align 1@.str.str1 = private unnamed_addr constant [7 x i8] c"\0Atest1\00", align 1@.str.str7 = private unnamed_addr constant [5 x i8] c";efg\00", align 1@.str.str2 = private unnamed_addr constant [7 x i8] c"\0Atest4\00", align 1@.str.str3 = private unnamed_addr constant [7 x i8] c"\0Atest5\00", align 1 ; --- Class C ---
%C = type { 
	i8*; s 
}
define void @C.constructor(%C* %this) {
	%s = getelementptr %C, %C* %this, i32 0, i32 0
	%stmp = getelementptr [1 x i8], [1 x i8]* @.str.str0, i32 0, i32 0
	store i8* %stmp, i8** %s
	ret void
}

define void @C.extend(%C* %self, i8* %s) { 
C.extend_entry:
	%tmp. = getelementptr %C, %C* %self, i32 0, i32 0
	%tmp..2 = load i8*, i8** %tmp.
	%tmp..3 = call i8* @._concat(i8* %tmp..2, i8* %s)
	store i8* %tmp..3, i8** %tmp.
	ret void
}

define void @C.print(%C* %self) { 
C.print_entry:
	%tmp. = getelementptr %C, %C* %self, i32 0, i32 0
	%tmp..1 = load i8*, i8** %tmp.
	call void @printString(i8* %tmp..1)
	ret void
}

define i1 @C.empty(%C* %self) { 
C.empty_entry:
	%tmp. = getelementptr %C, %C* %self, i32 0, i32 0
	%tmp..1 = load i8*, i8** %tmp.
	%tmp..2 = getelementptr [1 x i8], [1 x i8]* @.str.str0, i32 0, i32 0
	%tmp..3 = call i32 @._strcmp(i8* %tmp..1, i8* %tmp..2)
	%tmp..4 = icmp eq i32 %tmp..3, 0
	ret i1 %tmp..4
}

define i32 @main() { 
main_entry:
	call void @test1()
	call void @test2()
	call void @test3()
	call void @test4()
	call void @test5()
	call void @test6()
	ret i32 0
}

define void @test1() { 
test1_entry:
	%tmp. = getelementptr [7 x i8], [7 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = call i32 @i111()
	%tmp..3 = call i32 @i888()
	%tmp..4 = sub i32 %tmp..2, %tmp..3
	%tmp..5 = mul i32 %tmp..3, %tmp..4
	%tmp..6 = add i32 %tmp..2, %tmp..5
	call void @printInt(i32 %tmp..6)
	%tmp..9 = icmp sgt i32 %tmp..2, %tmp..3
	br i1 %tmp..9, label %test1.1_if.true, label %test1.2_if.false
test1.1_if.true:
	call void @printInt(i32 %tmp..6)
	br label %test1.3_if.end
test1.2_if.false:
	%tmp..16 = add i32 %tmp..5, %tmp..2
	call void @printInt(i32 %tmp..16)
	%tmp..20 = add i32 %tmp..3, %tmp..5
	call void @printInt(i32 %tmp..20)
	br label %test1.3_if.end
test1.3_if.end:
	call void @printInt(i32 %tmp..6)
	ret void
}

define void @test4() { 
test4_entry:
	%tmp. = getelementptr [7 x i8], [7 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..3 = call i32 @i111()
	%tmp..4 = call i32 @i888()
	%tmp..5 = sub i32 %tmp..3, %tmp..4
	%tmp..6 = mul i32 %tmp..4, %tmp..5
	%tmp..7 = add i32 %tmp..3, %tmp..6
	call void @printInt(i32 %tmp..7)
	br label %test4.1_while.cond
test4.1_while.cond:
	%i = phi i32 [3, %test4_entry], [%tmp..14, %test4.6_if.end]
	%tmp..12 = icmp sgt i32 %i, 0
	br i1 %tmp..12, label %test4.2_while.body, label %test4.3_while.end
test4.2_while.body:
	%tmp..14 = sub i32 %i, 1
	%tmp..16 = icmp sgt i32 %tmp..3, %tmp..4
	br i1 %tmp..16, label %test4.4_if.true, label %test4.5_if.false
test4.4_if.true:
	call void @printInt(i32 %tmp..7)
	br label %test4.6_if.end
test4.5_if.false:
	%tmp..23 = add i32 %tmp..6, %tmp..3
	call void @printInt(i32 %tmp..23)
	%tmp..27 = add i32 %tmp..4, %tmp..6
	call void @printInt(i32 %tmp..27)
	br label %test4.6_if.end
test4.6_if.end:
	br label %test4.1_while.cond
test4.3_while.end:
	call void @printInt(i32 %tmp..7)
	ret void
}

define void @test5() { 
test5_entry:
	%tmp. = getelementptr [7 x i8], [7 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..3 = call i32 @i111()
	%tmp..4 = call i32 @i888()
	br label %test5.1_while.cond
test5.1_while.cond:
	%i = phi i32 [3, %test5_entry], [%tmp..10, %test5.6_if.end]
	%tmp..8 = icmp sgt i32 %i, 0
	br i1 %tmp..8, label %test5.2_while.body, label %test5.3_while.end
test5.2_while.body:
	%tmp..10 = sub i32 %i, 1
	%tmp..12 = icmp sgt i32 %tmp..3, %tmp..4
	br i1 %tmp..12, label %test5.4_if.true, label %test5.5_if.false
test5.4_if.true:
	%tmp..13 = sub i32 %tmp..3, %tmp..4
	%tmp..14 = mul i32 %tmp..4, %tmp..13
	%tmp..15 = add i32 %tmp..3, %tmp..14
	call void @printInt(i32 %tmp..15)
	br label %test5.6_if.end
test5.5_if.false:
	%tmp..17 = sub i32 %tmp..3, %tmp..4
	%tmp..18 = mul i32 %tmp..4, %tmp..17
	%tmp..19 = add i32 %tmp..18, %tmp..3
	call void @printInt(i32 %tmp..19)
	%tmp..23 = add i32 %tmp..4, %tmp..18
	call void @printInt(i32 %tmp..23)
	br label %test5.6_if.end
test5.6_if.end:
	br label %test5.1_while.cond
test5.3_while.end:
	%tmp..25 = sub i32 %tmp..3, %tmp..4
	%tmp..26 = mul i32 %tmp..4, %tmp..25
	%tmp..27 = add i32 %tmp..3, %tmp..26
	call void @printInt(i32 %tmp..27)
	ret void
}

define void @test2() { 
test2_entry:
	%tmp. = getelementptr [7 x i8], [7 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = call i32 @i111()
	%tmp..3 = call i32 @i888()
	%tmp..4 = sub i32 %tmp..2, %tmp..3
	%tmp..5 = mul i32 %tmp..3, %tmp..4
	%tmp..6 = add i32 %tmp..2, %tmp..5
	call void @printInt(i32 %tmp..6)
	%tmp..9 = icmp sgt i32 %tmp..2, %tmp..3
	br i1 %tmp..9, label %test2.1_if.true, label %test2.2_if.false
test2.1_if.true:
	call void @printInt(i32 %tmp..6)
	br label %test2.3_if.end
test2.2_if.false:
	%tmp..16 = add i32 %tmp..5, %tmp..2
	call void @printInt(i32 %tmp..16)
	%tmp..20 = add i32 %tmp..3, %tmp..5
	call void @printInt(i32 %tmp..20)
	br label %test2.3_if.end
test2.3_if.end:
	call void @printInt(i32 %tmp..6)
	ret void
}

define void @test3() { 
test3_entry:
	%tmp. = getelementptr [7 x i8], [7 x i8]* @.str.str5, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = call i32 @i111()
	%tmp..3 = call i32 @i888()
	%tmp..5 = add i32 %tmp..3, 5
	%tmp..6 = add i32 %tmp..2, %tmp..5
	call void @printInt(i32 %tmp..6)
	%tmp..8 = add i32 %tmp..5, %tmp..2
	call void @printInt(i32 %tmp..8)
	%tmp..10 = sub i32 %tmp..2, %tmp..5
	call void @printInt(i32 %tmp..10)
	%tmp..12 = sub i32 %tmp..5, %tmp..2
	call void @printInt(i32 %tmp..12)
	%tmp..14 = mul i32 %tmp..2, %tmp..5
	call void @printInt(i32 %tmp..14)
	%tmp..16 = mul i32 %tmp..5, %tmp..2
	call void @printInt(i32 %tmp..16)
	%tmp..18 = sdiv i32 %tmp..2, %tmp..5
	call void @printInt(i32 %tmp..18)
	%tmp..20 = sdiv i32 %tmp..5, %tmp..2
	call void @printInt(i32 %tmp..20)
	%tmp..22 = srem i32 %tmp..2, %tmp..5
	call void @printInt(i32 %tmp..22)
	%tmp..24 = srem i32 %tmp..5, %tmp..2
	call void @printInt(i32 %tmp..24)
	ret void
}

define void @assert(i1 %b) { 
assert_entry:
	br i1 %b, label %assert.3_if.end, label %assert.1_if.true
assert.1_if.true:
	call void @error()
	ret void
assert.3_if.end:
	ret void
}

define void @test6() { 
test6_entry:
	%tmp. = call i8* @malloc(i32 64)
	%tmp..1 = bitcast i8* %tmp. to %C*
	call void @C.constructor(%C* %tmp..1)
	%tmp..3 = call i1 @C.empty(%C* %tmp..1)
	call void @assert(i1 %tmp..3)
	call void @C.print(%C* %tmp..1)
	%tmp..6 = getelementptr [4 x i8], [4 x i8]* @.str.str6, i32 0, i32 0
	call void @C.extend(%C* %tmp..1, i8* %tmp..6)
	%tmp..8 = call i1 @C.empty(%C* %tmp..1)
	%tmp..9 = xor i1 1, %tmp..8
	call void @assert(i1 %tmp..9)
	call void @C.print(%C* %tmp..1)
	%tmp..12 = getelementptr [5 x i8], [5 x i8]* @.str.str7, i32 0, i32 0
	call void @C.extend(%C* %tmp..1, i8* %tmp..12)
	%tmp..14 = call i1 @C.empty(%C* %tmp..1)
	%tmp..15 = xor i1 1, %tmp..14
	call void @assert(i1 %tmp..15)
	call void @C.print(%C* %tmp..1)
	ret void
}

define i32 @i111() { 
i111_entry:
	ret i32 111
}

define i32 @i888() { 
i888_entry:
	ret i32 888
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

declare i8* @strcat(i8*, i8*)
declare i8* @strcpy(i8*, i8*)
declare i32 @strlen(i8*)
define i8* @._concat(i8* %s1, i8* %s2) {
%1 = call i32 @strlen(i8* %s1)
%2 = call i32 @strlen(i8* %s2)
%3 = add i32 %1, %2
%4 = add i32 %3, 1
%5 = call i8* @malloc(i32 %4)
%6 = call i8* @strcpy(i8* %5, i8* %s1)
%7 = call i8* @strcat(i8* %5, i8* %s2)
ret i8* %7
}

declare i32 @strcmp(i8*, i8*)
define i32 @._strcmp(i8* %str1, i8* %str2) {
       %t0 = call i32 @strcmp(i8* %str1, i8* %str2)
       ret i32 %t0
}

