
define i32 @i42() { 
i42_entry:
	ret i32 42
}

define i32 @check_constexpr() { 
check_constexpr_entry:
	ret i32 0
}

define i1 @bfalse() { 
bfalse_entry:
	ret i1 false
}

define i32 @main() { 
main_entry:
	%tmp. = call i32 @check_constexpr()
	call void @check_runtime()
	ret i32 0
}

define i1 @btrue() { 
btrue_entry:
	ret i1 true
}

define void @check_runtime() { 
check_runtime_entry:
	%tmp..2 = call i1 @btrue()
	br i1 %tmp..2, label %check_runtime.2_if.end, label %check_runtime.1_if.true
check_runtime.1_if.true:
	call void @error()
	br label %check_runtime.2_if.end
check_runtime.2_if.end:
	%tmp..7 = call i1 @bfalse()
	br i1 %tmp..7, label %check_runtime.3_if.true, label %check_runtime.4_if.end
check_runtime.3_if.true:
	call void @error()
	br label %check_runtime.4_if.end
check_runtime.4_if.end:
	%tmp..14 = call i32 @im42()
	%tmp..15 = call i32 @i100()
	%tmp..16 = call i32 @i142()
	%tmp..17 = sub i32 %tmp..15, %tmp..16
	%tmp..18 = icmp ne i32 %tmp..14, %tmp..17
	br i1 %tmp..18, label %check_runtime.5_if.true, label %check_runtime.6_if.end
check_runtime.5_if.true:
	call void @error()
	br label %check_runtime.6_if.end
check_runtime.6_if.end:
	%tmp..26 = call i32 @i42()
	%tmp..27 = call i32 @i100()
	%tmp..28 = call i32 @i142()
	%tmp..29 = sub i32 %tmp..27, %tmp..28
	%tmp..30 = sub i32 0, %tmp..29
	%tmp..31 = icmp ne i32 %tmp..26, %tmp..30
	br i1 %tmp..31, label %check_runtime.7_if.true, label %check_runtime.8_if.end
check_runtime.7_if.true:
	call void @error()
	br label %check_runtime.8_if.end
check_runtime.8_if.end:
	ret void
}

define i32 @im42() { 
im42_entry:
	ret i32 -42
}

define i32 @i142() { 
i142_entry:
	ret i32 142
}

define i32 @i100() { 
i100_entry:
	ret i32 100
}


; ====================================================
; ====================================================
; ====================================================

declare void @exit(i32)
define void @error() {
entry:  call void @exit(i32 1)
	ret void
}

