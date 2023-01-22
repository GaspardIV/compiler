
define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @funkcja(i32 5)
	call void @printInt(i32 %tmp..1)
	ret i32 0
}

define i32 @funkcja(i32 %x) { 
funkcja_entry:
	%tmp..4 = icmp eq i32 %x, 0
	br i1 %tmp..4, label %funkcja.1_if.true, label %funkcja.2_if.end
funkcja.1_if.true:
	ret i32 1
funkcja.2_if.end:
	%tmp..7 = sub i32 %x, 1
	%tmp..8 = call i32 @funkcja(i32 %tmp..7)
	%tmp..9 = mul i32 %x, %tmp..8
	ret i32 %tmp..9
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

