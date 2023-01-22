@.str.str1 = private unnamed_addr constant [2 x i8] c" \00", align 1@.str.str2 = private unnamed_addr constant [14 x i8] c"concatenation\00", align 1@.str.str0 = private unnamed_addr constant [7 x i8] c"string\00", align 1@.str.str3 = private unnamed_addr constant [5 x i8] c"true\00", align 1@.str.str4 = private unnamed_addr constant [6 x i8] c"false\00", align 1
define i32 @main() { 
main_entry:
	call void @printInt(i32 33)
	call void @printInt(i32 79)
	call void @printInt(i32 -1288)
	call void @printInt(i32 22)
	call void @printInt(i32 0)
	call void @printBool(i1 true)
	call void @printBool(i1 false)
	%tmp..25 = getelementptr [7 x i8], [7 x i8]* @.str.str0, i32 0, i32 0
	%tmp..26 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	%tmp..27 = call i8* @._concat(i8* %tmp..25, i8* %tmp..26)
	%tmp..28 = getelementptr [14 x i8], [14 x i8]* @.str.str2, i32 0, i32 0
	%tmp..29 = call i8* @._concat(i8* %tmp..27, i8* %tmp..28)
	call void @printString(i8* %tmp..29)
	ret i32 0
}

define void @printBool(i1 %b) { 
printBool_entry:
	br i1 %b, label %printBool.1_if.true, label %printBool.2_if.false
printBool.1_if.true:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp.)
	ret void
printBool.2_if.false:
	%tmp..2 = getelementptr [6 x i8], [6 x i8]* @.str.str4, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	ret void
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

