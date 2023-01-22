@.str.str0 = private unnamed_addr constant [2 x i8] c"a\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"b\00", align 1
define i32 @main() { 
main_entry:
	%tmp. = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	%tmp..1 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	%tmp..2 = call i8* @._concat(i8* %tmp., i8* %tmp..1)
	call void @printString(i8* %tmp..2)
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

