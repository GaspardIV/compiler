@.str.str0 = private unnamed_addr constant [4 x i8] c"foo\00", align 1
define i32 @main() { 
main_entry:
	call void @printInt(i32 1)
	call void @printInt(i32 78)
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [78, %main_entry], [%tmp..9, %main.2_while.body]
	%tmp..7 = icmp sgt i32 %i, 76
	br i1 %tmp..7, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..9 = sub i32 %i, 1
	call void @printInt(i32 %tmp..9)
	%tmp..12 = add i32 %tmp..9, 7
	call void @printInt(i32 %tmp..12)
	br label %main.1_while.cond
main.3_while.end:
	call void @printInt(i32 %i)
	%tmp..18 = icmp sgt i32 %i, 4
	br i1 %tmp..18, label %main.4_if.true, label %main.5_if.false
main.4_if.true:
	call void @printInt(i32 4)
	br label %main.6_if.end
main.5_if.false:
	%tmp..21 = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..21)
	br label %main.6_if.end
main.6_if.end:
	%i.1 = phi i32 [%i, %main.4_if.true], [%i, %main.5_if.false]
	call void @printInt(i32 %i.1)
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

