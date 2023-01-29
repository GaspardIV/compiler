@.str.str0 = private unnamed_addr constant [4 x i8] c"abc\00", align 1
define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 1, 8
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 1, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i8**
	%tmp..8 = getelementptr i8*, i8** %tmp..6, i32 0
	%tmp..9 = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	store i8* %tmp..9, i8** %tmp..8
	%tmp..12 = load i8*, i8** %tmp..8
	call void @printString(i8* %tmp..12)
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

declare i8* @calloc(i32, i32)
