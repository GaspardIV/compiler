
define i32 @foo(i32 %a, i32 %b, i32 %c, i32 %d, i32 %e, i32 %f, i32 %g, i32 %h, i32 %i, i32 %j, i32 %k, i32 %l, i32 %m, i32 %n) { 
foo_entry:
	%tmp..1 = mul i32 2, %a
	%tmp..3 = sdiv i32 %b, 2
	%tmp..4 = add i32 %tmp..1, %tmp..3
	%tmp..5 = add i32 %tmp..4, %c
	%tmp..6 = add i32 %tmp..5, %d
	%tmp..7 = add i32 %tmp..6, %e
	%tmp..8 = add i32 %tmp..7, %f
	%tmp..9 = add i32 %tmp..8, %g
	%tmp..10 = add i32 %tmp..9, %h
	%tmp..11 = add i32 %tmp..10, %i
	%tmp..13 = sdiv i32 %j, 2
	%tmp..14 = add i32 %tmp..11, %tmp..13
	%tmp..15 = add i32 %tmp..14, %k
	%tmp..16 = add i32 %tmp..15, %l
	%tmp..17 = add i32 %tmp..16, %m
	%tmp..18 = add i32 %tmp..17, %n
	%tmp..20 = srem i32 %tmp..18, 10
	call void @printInt(i32 %tmp..20)
	ret i32 %tmp..20
}

define i32 @main() { 
main_entry:
	%tmp..14 = call i32 @foo(i32 1, i32 2, i32 1, i32 2, i32 1, i32 2, i32 1, i32 2, i32 1, i32 2, i32 1, i32 2, i32 1, i32 2)
	ret i32 %tmp..14
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

