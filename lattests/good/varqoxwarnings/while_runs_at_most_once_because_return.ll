@.str.str0 = private unnamed_addr constant [2 x i8] c"x\00", align 1
define i32 @main() { 
main.1_while.cond:
	%tmp..5 = call i8* @readString()
	%tmp..6 = getelementptr [2 x i8], [2 x i8]* @.str.str0, i32 0, i32 0
	%tmp..7 = call i32 @._strcmp(i8* %tmp..5, i8* %tmp..6)
	%tmp..8 = icmp eq i32 %tmp..7, 0
	br i1 %tmp..8, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	call void @printInt(i32 142)
	ret i32 0
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

declare i32 @strcmp(i8*, i8*)
define i32 @._strcmp(i8* %str1, i8* %str2) {
       %t0 = call i32 @strcmp(i8* %str1, i8* %str2)
       ret i32 %t0
}

