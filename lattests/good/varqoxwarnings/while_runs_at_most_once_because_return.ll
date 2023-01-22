@.str.str0 = private unnamed_addr constant [2 x i8] c"x\00", align 1
define i32 @main() { 
main_entry:
	br label %main.1_while.cond
main.2_while.body:
	call void @printInt(i32 142)
	ret i32 0
main.1_while.cond:
	%tmp..3 = call i8* @readString()
	%tmp..4 = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	%tmp..5 = icmp eq i8* %tmp..3, %tmp..4
	br i1 %tmp..5, label %main.2_while.body, label %main.3_while.end
main.3_while.end:
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

declare i8* @malloc(i32)

declare i8* @gets(i8*)
define i8* @readString() {
entry:
    %t1 = call i8* @malloc(i32 4096)
    %t2 = call i8* @gets(i8* %t1)
    ret i8* %t1
}

