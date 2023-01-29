
define void @foo(i32* %a) { 
foo_entry:
	%tmp..1 = getelementptr i32, i32* %a, i32 0
	%tmp..2 = load i32, i32* %tmp..1
	call void @printInt(i32 %tmp..2)
	%tmp..5 = bitcast i32* %a to i8*
	%tmp..4 = getelementptr i8, i8* %tmp..5, i32 -4
	%tmp..6 = bitcast i8* %tmp..4 to i32*
	%tmp..7 = load i32, i32* %tmp..6
	call void @printInt(i32 %tmp..7)
	ret void
}

define i32 @main() { 
main_entry:
	%tmp..1 = mul i32 42, 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 42, i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	call void @foo(i32* %tmp..6)
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
