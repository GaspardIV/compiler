@.str.str0 = private unnamed_addr constant [12 x i8] c"bla bla bla\00", align 1
define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 142, 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 142, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	%tmp..8 = bitcast i32* %tmp..6 to i8*
	%tmp..7 = getelementptr i8, i8* %tmp..8, i32 -4
	%tmp..9 = bitcast i8* %tmp..7 to i32*
	%tmp..10 = load i32, i32* %tmp..9
	br label %main.1_for.cond
main.1_for.cond:
	%i.d.x..1 = phi i32 [0, %main_entry], [%tmp..22, %main.5_while.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..10
	br i1 %ifres., label %main.2_for.body, label %main.3_for.end
main.2_for.body:
	%tmp..13 = mul i32 142, 4
	%tmp..14 = add i32 %tmp..13, 4
	%tmp..15 = call i8* @calloc(i32 1, i32 %tmp..14)
	%tmp..16 = bitcast i8* %tmp..15 to i32*
	store i32 142, i32* %tmp..16
	%tmp..17 = getelementptr i8, i8* %tmp..15, i32 4
	%tmp..18 = bitcast i8* %tmp..17 to i32*
	%tmp..19 = getelementptr i32, i32* %tmp..18, i32 %i.d.x..1
	%tmp..20 = load i32, i32* %tmp..19
	%tmp..22 = add i32 %i.d.x..1, 1
	br label %main.5_while.body
main.5_while.body:
	br label %main.5_while.body
	%tmp..24 = call i32 @readInt()
	%tmp..25 = getelementptr [12 x i8], [12 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..25)
	br label %main.1_for.cond
main.3_for.end:
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
@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

