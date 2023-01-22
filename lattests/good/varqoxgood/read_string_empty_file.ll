
define i32 @main() { 
main_entry:
	call void @printInt(i32 1)
	%tmp..2 = call i8* @readString()
	call void @printString(i8* %tmp..2)
	call void @printInt(i32 2)
	%tmp..6 = call i8* @readString()
	call void @printString(i8* %tmp..6)
	call void @printInt(i32 3)
	%tmp..10 = call i8* @readString()
	call void @printString(i8* %tmp..10)
	call void @printInt(i32 4)
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

