@.str.str0 = private unnamed_addr constant [157 x i8] c"\22\0Apop\0Apowrot:\0Agetstatic java/lang/System/out Ljava/io/PrintStream;\0Aldc \22zle \22\0Ainvokevirtual java/io/PrintStream/print(Ljava/lang/String;)V\0Agoto powrot\0Aldc \22\00", align 1
define i32 @f(i32 %p) { 
f_entry:
	%tmp..1 = mul i32 2, %p
	%tmp..2 = add i32 %p, %tmp..1
	%tmp..3 = getelementptr [157 x i8], [157 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..3)
	ret i32 %tmp..2
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @f(i32 1)
	%tmp..3 = sub i32 %tmp..1, 3
	ret i32 %tmp..3
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

