
define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 10, 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 10, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	br label %main.1_while.cond
main.1_while.cond:
	%j = phi i32 [0, %main_entry], [%tmp..20, %main.2_while.body]
	%tmp..14 = bitcast i32* %tmp..6 to i8*
	%tmp..13 = getelementptr i8, i8* %tmp..14, i32 -4
	%tmp..15 = bitcast i8* %tmp..13 to i32*
	%tmp..16 = load i32, i32* %tmp..15
	%tmp..17 = icmp slt i32 %j, %tmp..16
	br i1 %tmp..17, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..18 = getelementptr i32, i32* %tmp..6, i32 %j
	store i32 %j, i32* %tmp..18
	%tmp..20 = add i32 %j, 1
	br label %main.1_while.cond
main.3_while.end:
	%tmp..22 = bitcast i32* %tmp..6 to i8*
	%tmp..21 = getelementptr i8, i8* %tmp..22, i32 -4
	%tmp..23 = bitcast i8* %tmp..21 to i32*
	%tmp..24 = load i32, i32* %tmp..23
	br label %main.4_for.cond
main.4_for.cond:
	%i.d.x..1 = phi i32 [0, %main.3_while.end], [%tmp..29, %main.5_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..24
	br i1 %ifres., label %main.5_for.body, label %main.6_for.end
main.5_for.body:
	%tmp..26 = getelementptr i32, i32* %tmp..6, i32 %i.d.x..1
	%tmp..27 = load i32, i32* %tmp..26
	%tmp..29 = add i32 %i.d.x..1, 1
	call void @printInt(i32 %tmp..27)
	br label %main.4_for.cond
main.6_for.end:
	call void @printInt(i32 45)
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

declare i8* @calloc(i32, i32)
