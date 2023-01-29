
define i32 @main() { 
main_entry:
	%tmp. = call i32 @readInt()
	%tmp..1 = mul i32 %tmp., 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 %tmp., i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	%tmp..8 = bitcast i32* %tmp..6 to i8*
	%tmp..7 = getelementptr i8, i8* %tmp..8, i32 -4
	%tmp..9 = bitcast i8* %tmp..7 to i32*
	%tmp..10 = load i32, i32* %tmp..9
	br label %main.1_for.cond
main.1_for.cond:
	%ifres. = icmp slt i32 0, %tmp..10
	br i1 %ifres., label %main.2_for.body, label %main.3_for.end
main.2_for.body:
	%tmp..12 = call i32 @readInt()
	%tmp..13 = mul i32 %tmp..12, 4
	%tmp..14 = add i32 %tmp..13, 4
	%tmp..15 = call i8* @calloc(i32 1, i32 %tmp..14)
	%tmp..16 = bitcast i8* %tmp..15 to i32*
	store i32 %tmp..12, i32* %tmp..16
	%tmp..17 = getelementptr i8, i8* %tmp..15, i32 4
	%tmp..18 = bitcast i8* %tmp..17 to i32*
	%tmp..19 = getelementptr i32, i32* %tmp..18, i32 0
	%tmp..20 = load i32, i32* %tmp..19
	call void @printInt(i32 142)
	ret i32 0
main.3_for.end:
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
@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

