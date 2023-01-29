
define void @shiftLeft(i32* %a) { 
shiftLeft_entry:
	%tmp..1 = getelementptr i32, i32* %a, i32 0
	%tmp..2 = load i32, i32* %tmp..1
	br label %shiftLeft.1_while.cond
shiftLeft.1_while.cond:
	%i = phi i32 [0, %shiftLeft_entry], [%tmp..20, %shiftLeft.2_while.body]
	%tmp..12 = bitcast i32* %a to i8*
	%tmp..11 = getelementptr i8, i8* %tmp..12, i32 -4
	%tmp..13 = bitcast i8* %tmp..11 to i32*
	%tmp..14 = load i32, i32* %tmp..13
	%tmp..16 = sub i32 %tmp..14, 1
	%tmp..17 = icmp slt i32 %i, %tmp..16
	br i1 %tmp..17, label %shiftLeft.2_while.body, label %shiftLeft.3_while.end
shiftLeft.2_while.body:
	%tmp..18 = getelementptr i32, i32* %a, i32 %i
	%tmp..20 = add i32 %i, 1
	%tmp..21 = getelementptr i32, i32* %a, i32 %tmp..20
	%tmp..22 = load i32, i32* %tmp..21
	store i32 %tmp..22, i32* %tmp..18
	br label %shiftLeft.1_while.cond
shiftLeft.3_while.end:
	%tmp..26 = bitcast i32* %a to i8*
	%tmp..25 = getelementptr i8, i8* %tmp..26, i32 -4
	%tmp..27 = bitcast i8* %tmp..25 to i32*
	%tmp..28 = load i32, i32* %tmp..27
	%tmp..30 = sub i32 %tmp..28, 1
	%tmp..31 = getelementptr i32, i32* %a, i32 %tmp..30
	store i32 %tmp..2, i32* %tmp..31
	ret void
}

define i32* @doubleArray(i32* %a) { 
doubleArray_entry:
	%tmp..1 = bitcast i32* %a to i8*
	%tmp. = getelementptr i8, i8* %tmp..1, i32 -4
	%tmp..2 = bitcast i8* %tmp. to i32*
	%tmp..3 = load i32, i32* %tmp..2
	%tmp..4 = mul i32 %tmp..3, 4
	%tmp..5 = add i32 %tmp..4, 4
	%tmp..6 = call i8* @calloc(i32 1, i32 %tmp..5)
	%tmp..7 = bitcast i8* %tmp..6 to i32*
	store i32 %tmp..3, i32* %tmp..7
	%tmp..8 = getelementptr i8, i8* %tmp..6, i32 4
	%tmp..9 = bitcast i8* %tmp..8 to i32*
	%tmp..12 = bitcast i32* %a to i8*
	%tmp..11 = getelementptr i8, i8* %tmp..12, i32 -4
	%tmp..13 = bitcast i8* %tmp..11 to i32*
	%tmp..14 = load i32, i32* %tmp..13
	br label %doubleArray.1_for.cond
doubleArray.1_for.cond:
	%i = phi i32 [0, %doubleArray_entry], [%tmp..24, %doubleArray.2_for.body]
	%i.d.x..1 = phi i32 [0, %doubleArray_entry], [%tmp..19, %doubleArray.2_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..14
	br i1 %ifres., label %doubleArray.2_for.body, label %doubleArray.3_for.end
doubleArray.2_for.body:
	%tmp..16 = getelementptr i32, i32* %a, i32 %i.d.x..1
	%tmp..17 = load i32, i32* %tmp..16
	%tmp..19 = add i32 %i.d.x..1, 1
	%tmp..20 = getelementptr i32, i32* %tmp..9, i32 %i
	%tmp..22 = mul i32 2, %tmp..17
	store i32 %tmp..22, i32* %tmp..20
	%tmp..24 = add i32 %i, 1
	br label %doubleArray.1_for.cond
doubleArray.3_for.end:
	ret i32* %tmp..9
}

define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 5, 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 5, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [0, %main_entry], [%tmp..20, %main.2_while.body]
	%tmp..14 = bitcast i32* %tmp..6 to i8*
	%tmp..13 = getelementptr i8, i8* %tmp..14, i32 -4
	%tmp..15 = bitcast i8* %tmp..13 to i32*
	%tmp..16 = load i32, i32* %tmp..15
	%tmp..17 = icmp slt i32 %i, %tmp..16
	br i1 %tmp..17, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..18 = getelementptr i32, i32* %tmp..6, i32 %i
	store i32 %i, i32* %tmp..18
	%tmp..20 = add i32 %i, 1
	br label %main.1_while.cond
main.3_while.end:
	call void @shiftLeft(i32* %tmp..6)
	%tmp..22 = call i32* @doubleArray(i32* %tmp..6)
	%tmp..24 = bitcast i32* %tmp..6 to i8*
	%tmp..23 = getelementptr i8, i8* %tmp..24, i32 -4
	%tmp..25 = bitcast i8* %tmp..23 to i32*
	%tmp..26 = load i32, i32* %tmp..25
	br label %main.4_for.cond
main.4_for.cond:
	%i.d.x..1 = phi i32 [0, %main.3_while.end], [%tmp..31, %main.5_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..26
	br i1 %ifres., label %main.5_for.body, label %main.6_for.end
main.5_for.body:
	%tmp..28 = getelementptr i32, i32* %tmp..6, i32 %i.d.x..1
	%tmp..29 = load i32, i32* %tmp..28
	%tmp..31 = add i32 %i.d.x..1, 1
	call void @printInt(i32 %tmp..29)
	br label %main.4_for.cond
main.6_for.end:
	%tmp..34 = bitcast i32* %tmp..22 to i8*
	%tmp..33 = getelementptr i8, i8* %tmp..34, i32 -4
	%tmp..35 = bitcast i8* %tmp..33 to i32*
	%tmp..36 = load i32, i32* %tmp..35
	br label %main.7_for.cond
main.7_for.cond:
	%i.d.x..2 = phi i32 [0, %main.6_for.end], [%tmp..41, %main.8_for.body]
	%ifres..1 = icmp slt i32 %i.d.x..2, %tmp..36
	br i1 %ifres..1, label %main.8_for.body, label %main.9_for.end
main.8_for.body:
	%tmp..38 = getelementptr i32, i32* %tmp..22, i32 %i.d.x..2
	%tmp..39 = load i32, i32* %tmp..38
	%tmp..41 = add i32 %i.d.x..2, 1
	call void @printInt(i32 %tmp..39)
	br label %main.7_for.cond
main.9_for.end:
	%tmp..43 = call i32 @scalProd(i32* %tmp..6, i32* %tmp..22)
	call void @printInt(i32 %tmp..43)
	ret i32 0
}

define i32 @scalProd(i32* %a, i32* %b) { 
scalProd_entry:
	br label %scalProd.1_while.cond
scalProd.1_while.cond:
	%i = phi i32 [0, %scalProd_entry], [%tmp..19, %scalProd.2_while.body]
	%res = phi i32 [0, %scalProd_entry], [%tmp..17, %scalProd.2_while.body]
	%tmp..8 = bitcast i32* %a to i8*
	%tmp..7 = getelementptr i8, i8* %tmp..8, i32 -4
	%tmp..9 = bitcast i8* %tmp..7 to i32*
	%tmp..10 = load i32, i32* %tmp..9
	%tmp..11 = icmp slt i32 %i, %tmp..10
	br i1 %tmp..11, label %scalProd.2_while.body, label %scalProd.3_while.end
scalProd.2_while.body:
	%tmp..12 = getelementptr i32, i32* %a, i32 %i
	%tmp..13 = load i32, i32* %tmp..12
	%tmp..14 = getelementptr i32, i32* %b, i32 %i
	%tmp..15 = load i32, i32* %tmp..14
	%tmp..16 = mul i32 %tmp..13, %tmp..15
	%tmp..17 = add i32 %res, %tmp..16
	%tmp..19 = add i32 %i, 1
	br label %scalProd.1_while.cond
scalProd.3_while.end:
	ret i32 %res
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

declare i8* @calloc(i32, i32)
