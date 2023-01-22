@._dnl = internal constant [4 x i8] c"%d\0A\00"
@._runtime_error = internal constant [15 x i8] c"runtime error\0A\00"


declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
declare i8* @strcat(i8*, i8*)
declare i8* @strcpy(i8*, i8*)
declare i8* @malloc(i32)
declare i32 @strlen(i8*)
declare i32 @puts(i8*)
declare i8* @gets(i8*)
declare void @exit(i32)
declare i32 @strcmp(i8*, i8*)

define void @printInt(i32 %x) {
       %t0 = getelementptr [4 x i8], [4 x i8]* @._dnl, i32 0, i32 0
       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)
       ret void
}


define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

define i8* @readString() {
entry:
    %t1 = call i8* @malloc(i32 4096)
    %t2 = call i8* @gets(i8* %t1)
    ret i8* %t1
}


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


define void @error() {
    %t0 = getelementptr [15 x i8], [15 x i8]* @._runtime_error, i32 0, i32 0
    call void @printString(i8* %t0)
    call void @exit(i32 1)
    ret void
}

@.str.str0 = private unnamed_addr constant [10 x i8] c"---------\00", align 1
define void @test_rotate() { 
%tmp____1 = getelementptr [10 x i8], [10 x i8]* @.str.str0, i32 0, i32 0

call void @printString(i8* %tmp____1)

%tmp____3 = call i32 @i1()

%tmp____4 = call i32 @i22()

%tmp____5 = call i32 @i333()

 ; 4

ret void

}

define i32 @i1() { 
 ; 1

ret i32 1

}

define i32 @i22() { 
 ; 22

ret i32 22

}

define void @test_braid() { 
%tmp____9 = getelementptr [10 x i8], [10 x i8]* @.str.str0, i32 0, i32 0

call void @printString(i8* %tmp____9)

%tmp____11 = call i32 @i1()

%tmp____12 = call i32 @i22()

%tmp____13 = call i32 @i333()

 ; 6

ret void

}

define i32 @main() { 
call void @test_simple_swap()

call void @test_braid()

call void @test_rotate()

 ; 0

ret i32 0

}

define void @test_simple_swap() { 
%tmp____19 = call i32 @i111()

%tmp____20 = call i32 @i888()

 ; 4

%tmp____22 = getelementptr [10 x i8], [10 x i8]* @.str.str0, i32 0, i32 0

call void @printString(i8* %tmp____22)

 ; 888

 ; 111

 ; 4

ret void

}

define i32 @i111() { 
 ; 111

ret i32 111

}

define i32 @i333() { 
 ; 333

ret i32 333

}

define i32 @i888() { 
 ; 888

ret i32 888

}
