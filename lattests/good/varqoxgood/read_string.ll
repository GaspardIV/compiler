@.str.str1 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str0 = private unnamed_addr constant [4 x i8] c"abc\00", align 1
define i32 @main() { 
main_entry:
	%tmp. = call i8* @readString()
	call void @printString(i8* %tmp.)
	%tmp..2 = call i8* @readString()
	call void @printString(i8* %tmp..2)
	%tmp..4 = call i8* @readString()
	call void @printString(i8* %tmp..4)
	%tmp..6 = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	br label %main.1_while.cond
main.1_while.cond:
	%s = phi i8* [%tmp..6, %main_entry], [%tmp..15, %main.2_while.body]
	%tmp..11 = getelementptr [1 x i8], [1 x i8]* @.str.str1, i32 0, i32 0
	%tmp..12 = call i32 @._strcmp(i8* %s, i8* %tmp..11)
	%tmp..13 = icmp ne i32 %tmp..12, 0
	br i1 %tmp..13, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..15 = call i8* @readString()
	call void @printString(i8* %tmp..15)
	br label %main.1_while.cond
main.3_while.end:
	call void @printInt(i32 1)
	%tmp..19 = call i8* @readString()
	call void @printString(i8* %tmp..19)
	call void @printInt(i32 2)
	%tmp..23 = call i8* @readString()
	call void @printString(i8* %tmp..23)
	call void @printInt(i32 3)
	%tmp..27 = call i8* @readString()
	call void @printString(i8* %tmp..27)
	call void @printInt(i32 4)
	%tmp..31 = call i8* @readString()
	call void @printString(i8* %tmp..31)
	call void @printInt(i32 5)
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

