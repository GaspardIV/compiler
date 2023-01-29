 ; --- Class X ---
%X = type { 
}
define void @X.constructor(%X* %this) {
	ret void
}
 ; --- Class Y ---
%Y = type { 
}
define void @Y.constructor(%Y* %this) {
	ret void
}

define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 0, 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 0, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	%tmp..8 = mul i32 1, 8
	%tmp..9 = add i32 %tmp..8, 4
	%tmp..10 = call i8* @calloc(i32 1, i32 %tmp..9)
	%tmp..11 = bitcast i8* %tmp..10 to i32*
	store i32 1, i32* %tmp..11
	%tmp..12 = getelementptr i8, i8* %tmp..10, i32 4
	%tmp..13 = bitcast i8* %tmp..12 to i8**
	%tmp..15 = mul i32 2, 1
	%tmp..16 = add i32 %tmp..15, 4
	%tmp..17 = call i8* @calloc(i32 1, i32 %tmp..16)
	%tmp..18 = bitcast i8* %tmp..17 to i32*
	store i32 2, i32* %tmp..18
	%tmp..19 = getelementptr i8, i8* %tmp..17, i32 4
	%tmp..20 = bitcast i8* %tmp..19 to i1*
	%tmp..22 = mul i32 3, 8
	%tmp..23 = add i32 %tmp..22, 4
	%tmp..24 = call i8* @calloc(i32 1, i32 %tmp..23)
	%tmp..25 = bitcast i8* %tmp..24 to i32*
	store i32 3, i32* %tmp..25
	%tmp..26 = getelementptr i8, i8* %tmp..24, i32 4
	%tmp..27 = bitcast i8* %tmp..26 to i32**
	%tmp..29 = mul i32 4, 8
	%tmp..30 = add i32 %tmp..29, 4
	%tmp..31 = call i8* @calloc(i32 1, i32 %tmp..30)
	%tmp..32 = bitcast i8* %tmp..31 to i32*
	store i32 4, i32* %tmp..32
	%tmp..33 = getelementptr i8, i8* %tmp..31, i32 4
	%tmp..34 = bitcast i8* %tmp..33 to %X**
	%tmp..36 = mul i32 5, 8
	%tmp..37 = add i32 %tmp..36, 4
	%tmp..38 = call i8* @calloc(i32 1, i32 %tmp..37)
	%tmp..39 = bitcast i8* %tmp..38 to i32*
	store i32 5, i32* %tmp..39
	%tmp..40 = getelementptr i8, i8* %tmp..38, i32 4
	%tmp..41 = bitcast i8* %tmp..40 to %Y***
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i8* @calloc(i32, i32)
