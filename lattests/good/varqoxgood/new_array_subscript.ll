@.str.str0 = private unnamed_addr constant [2 x i8] c"x\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"y\00", align 1
define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 42, 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 42, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	%tmp..8 = getelementptr i32, i32* %tmp..6, i32 8
	%tmp..9 = load i32, i32* %tmp..8
	call void @printInt(i32 %tmp..9)
	%tmp..12 = mul i32 42, 8
	%tmp..13 = add i32 %tmp..12, 4
	%tmp..14 = call i8* @calloc(i32 1, i32 %tmp..13)
	%tmp..15 = bitcast i8* %tmp..14 to i32*
	store i32 42, i32* %tmp..15
	%tmp..16 = getelementptr i8, i8* %tmp..14, i32 4
	%tmp..17 = bitcast i8* %tmp..16 to i8**
	%tmp..19 = getelementptr i8*, i8** %tmp..17, i32 41
	%tmp..20 = load i8*, i8** %tmp..19
	call void @printString(i8* %tmp..20)
	%tmp..22 = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	%tmp..24 = mul i32 5, 8
	%tmp..25 = add i32 %tmp..24, 4
	%tmp..26 = call i8* @calloc(i32 1, i32 %tmp..25)
	%tmp..27 = bitcast i8* %tmp..26 to i32*
	store i32 5, i32* %tmp..27
	%tmp..28 = getelementptr i8, i8* %tmp..26, i32 4
	%tmp..29 = bitcast i8* %tmp..28 to i8**
	%tmp..31 = getelementptr i8*, i8** %tmp..29, i32 0
	%tmp..32 = load i8*, i8** %tmp..31
	%tmp..33 = call i8* @._concat(i8* %tmp..22, i8* %tmp..32)
	%tmp..34 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	%tmp..35 = call i8* @._concat(i8* %tmp..33, i8* %tmp..34)
	call void @printString(i8* %tmp..35)
	ret i32 0
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

declare i8* @calloc(i32, i32)
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

