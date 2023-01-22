
define i1 @bar() { 
bar_entry:
	ret i1 true
}

define i32 @foo(i32 %a, i32 %b) { 
foo_entry:
	%tmp. = add i32 %a, %b
	%tmp..1 = add i32 %tmp., %b
	%tmp..2 = add i32 %a, %tmp..1
	call void @printInt(i32 %tmp..2)
	ret i32 %tmp..2
}

define i1 @baz() { 
baz_entry:
	ret i1 false
}

define i32 @main() { 
main_entry:
	%tmp..2 = call i32 @foo(i32 42, i32 17)
	%tmp..4 = call i1 @bar()
	br i1 %tmp..4, label %main.1_if.true, label %main.2_if.end
main.1_if.true:
	call void @printInt(i32 %tmp..2)
	br label %main.2_if.end
main.2_if.end:
	%tmp..7 = call i1 @bar()
	br i1 %tmp..7, label %main.3_if.true, label %main.4_if.false
main.3_if.true:
	%tmp..9 = add i32 %tmp..2, 1000
	call void @printInt(i32 %tmp..9)
	br label %main.5_if.end
main.4_if.false:
	%tmp..12 = add i32 %tmp..2, 2000
	call void @printInt(i32 %tmp..12)
	br label %main.5_if.end
main.5_if.end:
	%tmp..15 = call i1 @baz()
	br i1 %tmp..15, label %main.6_if.true, label %main.7_if.false
main.6_if.true:
	%tmp..17 = add i32 %tmp..2, 3000
	call void @printInt(i32 %tmp..17)
	br label %main.8_if.end
main.7_if.false:
	%tmp..20 = add i32 %tmp..2, 4000
	call void @printInt(i32 %tmp..20)
	br label %main.8_if.end
main.8_if.end:
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

