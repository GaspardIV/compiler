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

@.str.str0 = private unnamed_addr constant [12 x i8] c"bla bla bla\00", align 1
define i32 @main() { 
 ; 44

 ; 42

 ; 2

 ; 44

 ; true

br i1 true, label %main_1_1, label %main_1_2

main_1_1:

br label %main_1_3

main_1_2:

%tmp____6 = call i32 @readInt()

%tmp____7 = getelementptr [12 x i8], [12 x i8]* @.str.str0, i32 0, i32 0

call void @printString(i8* %tmp____7)

br label %main_1_3

main_1_3:

 ; 0

ret i32 0

}
