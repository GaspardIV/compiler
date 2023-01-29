
define i32 @main() { 
main_entry:
	%tmp..5 = mul i32 -2, 4
	%tmp..6 = add i32 %tmp..5, 4
	%tmp..7 = call i8* @calloc(i32 1, i32 %tmp..6)
	%tmp..8 = bitcast i8* %tmp..7 to i32*
	store i32 -2, i32* %tmp..8
	%tmp..9 = getelementptr i8, i8* %tmp..7, i32 4
	%tmp..10 = bitcast i8* %tmp..9 to i32*
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @calloc(i32, i32)
