
define i32 @main() { 
main_entry:
	call void @funkcja(i32 3333)
	call void @funkcja(i32 9999)
	ret i32 0
}

define void @funkcja(i32 %x) { 
funkcja_entry:
	%tmp..3 = icmp sgt i32 %x, 5555
	br i1 %tmp..3, label %funkcja.1_if.true, label %funkcja.2_if.false
funkcja.1_if.true:
	call void @printInt(i32 7777)
	br label %funkcja.3_if.end
funkcja.2_if.false:
	call void @printInt(i32 1111)
	br label %funkcja.3_if.end
funkcja.3_if.end:
	%x.1 = phi i32 [%x, %funkcja.1_if.true], [%x, %funkcja.2_if.false]
	call void @printInt(i32 %x.1)
	ret void
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

