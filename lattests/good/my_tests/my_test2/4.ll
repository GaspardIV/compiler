
define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @funkcja(i32 77)
	call void @printInt(i32 77)
	call void @printInt(i32 %tmp..1)
	call void @printInt(i32 %tmp..1)
	call void @printInt(i32 %tmp..1)
	ret i32 0
}

define i32 @funkcja(i32 %x) { 
funkcja_entry:
	%tmp..1 = icmp slt i32 %x, %x
	br i1 %tmp..1, label %funkcja.1_if.true, label %funkcja.2_if.false
funkcja.1_if.true:
	call void @printInt(i32 %x)
	br label %funkcja.3_if.end
funkcja.2_if.false:
	br label %funkcja.3_if.end
funkcja.3_if.end:
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	call void @printInt(i32 %x)
	ret i32 %x
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

