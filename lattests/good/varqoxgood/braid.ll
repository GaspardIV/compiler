@.str.str1 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str0 = private unnamed_addr constant [10 x i8] c"---------\00", align 1
define void @test_rotate() { 
test_rotate_entry:
	%tmp. = getelementptr [10 x i8], [10 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = call i32 @i1()
	%tmp..3 = call i32 @i22()
	%tmp..4 = call i32 @i333()
	br label %test_rotate.1_while.cond
test_rotate.1_while.cond:
	%a = phi i32 [%tmp..2, %test_rotate_entry], [%b, %test_rotate.2_while.body]
	%b = phi i32 [%tmp..3, %test_rotate_entry], [%c, %test_rotate.2_while.body]
	%c = phi i32 [%tmp..4, %test_rotate_entry], [%a, %test_rotate.2_while.body]
	%i = phi i32 [4, %test_rotate_entry], [%tmp..11, %test_rotate.2_while.body]
	%tmp..9 = icmp sgt i32 %i, 0
	br i1 %tmp..9, label %test_rotate.2_while.body, label %test_rotate.3_while.end
test_rotate.2_while.body:
	%tmp..11 = sub i32 %i, 1
	call void @printInt(i32 %a)
	call void @printInt(i32 %b)
	call void @printInt(i32 %c)
	%tmp..15 = getelementptr [1 x i8], [1 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..15)
	br label %test_rotate.1_while.cond
test_rotate.3_while.end:
	ret void
}

define i32 @i1() { 
i1_entry:
	ret i32 1
}

define i32 @i22() { 
i22_entry:
	ret i32 22
}

define void @test_braid() { 
test_braid_entry:
	%tmp. = getelementptr [10 x i8], [10 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	%tmp..2 = call i32 @i1()
	%tmp..3 = call i32 @i22()
	%tmp..4 = call i32 @i333()
	br label %test_braid.1_while.cond
test_braid.1_while.cond:
	%a = phi i32 [%tmp..2, %test_braid_entry], [%a.1, %test_braid.6_if.end]
	%b = phi i32 [%tmp..3, %test_braid_entry], [%b.1, %test_braid.6_if.end]
	%c = phi i32 [%tmp..4, %test_braid_entry], [%c.1, %test_braid.6_if.end]
	%i = phi i32 [6, %test_braid_entry], [%tmp..11, %test_braid.6_if.end]
	%tmp..9 = icmp sge i32 %i, 0
	br i1 %tmp..9, label %test_braid.2_while.body, label %test_braid.3_while.end
test_braid.2_while.body:
	%tmp..11 = sub i32 %i, 1
	call void @printInt(i32 %a)
	call void @printInt(i32 %b)
	call void @printInt(i32 %c)
	%tmp..15 = getelementptr [1 x i8], [1 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..15)
	%tmp..22 = srem i32 %tmp..11, 2
	%tmp..24 = icmp eq i32 %tmp..22, 1
	br i1 %tmp..24, label %test_braid.4_if.true, label %test_braid.5_if.false
test_braid.4_if.true:
	br label %test_braid.6_if.end
test_braid.5_if.false:
	br label %test_braid.6_if.end
test_braid.6_if.end:
	%a.1 = phi i32 [%b, %test_braid.4_if.true], [%a, %test_braid.5_if.false]
	%b.1 = phi i32 [%a, %test_braid.4_if.true], [%c, %test_braid.5_if.false]
	%c.1 = phi i32 [%c, %test_braid.4_if.true], [%b, %test_braid.5_if.false]
	br label %test_braid.1_while.cond
test_braid.3_while.end:
	ret void
}

define i32 @main() { 
main_entry:
	call void @test_simple_swap()
	call void @test_braid()
	call void @test_rotate()
	ret i32 0
}

define void @test_simple_swap() { 
test_simple_swap_entry:
	%tmp. = call i32 @i111()
	%tmp..1 = call i32 @i888()
	br label %test_simple_swap.1_while.cond
test_simple_swap.1_while.cond:
	%x = phi i32 [%tmp., %test_simple_swap_entry], [%y, %test_simple_swap.2_while.body]
	%y = phi i32 [%tmp..1, %test_simple_swap_entry], [%x, %test_simple_swap.2_while.body]
	%z = phi i32 [4, %test_simple_swap_entry], [%tmp..10, %test_simple_swap.2_while.body]
	%tmp..6 = icmp sgt i32 %z, 0
	br i1 %tmp..6, label %test_simple_swap.2_while.body, label %test_simple_swap.3_while.end
test_simple_swap.2_while.body:
	call void @printInt(i32 %x)
	call void @printInt(i32 %y)
	%tmp..10 = sub i32 %z, 1
	br label %test_simple_swap.1_while.cond
test_simple_swap.3_while.end:
	%tmp..11 = getelementptr [10 x i8], [10 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..11)
	br label %test_simple_swap.4_while.cond
test_simple_swap.4_while.cond:
	%xx = phi i32 [111, %test_simple_swap.3_while.end], [%yy, %test_simple_swap.5_while.body]
	%yy = phi i32 [888, %test_simple_swap.3_while.end], [%xx, %test_simple_swap.5_while.body]
	%zz = phi i32 [4, %test_simple_swap.3_while.end], [%tmp..23, %test_simple_swap.5_while.body]
	%tmp..19 = icmp sgt i32 %zz, 0
	br i1 %tmp..19, label %test_simple_swap.5_while.body, label %test_simple_swap.6_while.end
test_simple_swap.5_while.body:
	call void @printInt(i32 %xx)
	call void @printInt(i32 %yy)
	%tmp..23 = sub i32 %zz, 1
	br label %test_simple_swap.4_while.cond
test_simple_swap.6_while.end:
	ret void
}

define i32 @i111() { 
i111_entry:
	ret i32 111
}

define i32 @i333() { 
i333_entry:
	ret i32 333
}

define i32 @i888() { 
i888_entry:
	ret i32 888
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

