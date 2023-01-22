@dnl = internal constant [4 x i8] c"%d\0A\00"
@d   = internal constant [3 x i8] c"%d\00"
@runtime_error = internal constant [15 x i8] c"runtime error\0A\00"

declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
declare i8* @readline(i8*)
declare i32 @puts(i8*)
declare void @exit(i32)

define void @printInt(i32 %x) {
       %t0 = getelementptr [4 x i8], [4 x i8]* @dnl, i32 0, i32 0
       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)
       ret void
}


define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [3 x i8], [3 x i8]* @d, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

define i8* @readString() {
entry:	%t1 = alloca i8
    %t2 = call i8* @readline(i8* %t1)
    ret i8* %t2
}


define void @error() {
    %t0 = getelementptr [15 x i8], [15 x i8]* @runtime_error, i32 0, i32 0
    call void @printString(i8* %t0)
    call void @exit(i32 1)
    ret void
}

@.str.str0 = private unnamed_addr constant [12 x i8] c"bla bla bla\00", align 1
define i32 @test_while_true() { 
entry: 
 ; whilebr label %block1

block1: 
%tmp5 = call i32 @readInt()
br label %block2

block2: 
%tmp9 = getelementptr [12 x i8], [12 x i8]* @.str.str0, i32 0, i32 0
call void @printString(i8* %tmp9)
br label %block3

block3: 
 ; 42
ret i32 42
}

define i32 @main() { 
entry: 
 ; 0
ret i32 0
}
