@.str.str0 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"a\00", align 1@.str.str2 = private unnamed_addr constant [5 x i8] c"Done\00", align 1
define i32 @main() { 
main_entry:
	%tmp. = getelementptr [1 x i8], [1 x i8]* @.str.str0, i32 0, i32 0
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [0, %main_entry], [%tmp..24, %main.6_if.end]
	%s1 = phi i8* [%tmp., %main_entry], [%tmp..12, %main.6_if.end]
	%s2 = phi i8* [%tmp., %main_entry], [%s2.1, %main.6_if.end]
	%tmp..10 = icmp slt i32 %i, 102400
	br i1 %tmp..10, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..11 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	%tmp..12 = call i8* @._concat(i8* %s1, i8* %tmp..11)
	%tmp..18 = srem i32 %i, 2
	%tmp..20 = icmp eq i32 %tmp..18, 0
	br i1 %tmp..20, label %main.4_if.true, label %main.5_if.false
main.4_if.true:
	%tmp..22 = call i8* @._concat(i8* %s2, i8* %tmp..11)
	br label %main.6_if.end
main.5_if.false:
	br label %main.6_if.end
main.6_if.end:
	%s2.1 = phi i8* [%tmp..22, %main.4_if.true], [%s2, %main.5_if.false]
	%tmp..24 = add i32 %i, 1
	br label %main.1_while.cond
main.3_while.end:
	%tmp..25 = getelementptr [5 x i8], [5 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp..25)
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

