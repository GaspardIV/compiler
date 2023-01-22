
define void @test() { 
test_1_entry:




br i1 true, label %test_2_null, label %test_3_null
test_2_null:
call void @error()
br label %test_3_null
test_3_null:
ret void
}

define i32 @main() { 
main_1_entry:
call void @test()

ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare void @exit(i32)
define void @error() {
entry:  call void @exit(i32 1)
	ret void
}

