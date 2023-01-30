
define void @maxHeapify(i32* %a, i32 %p, i32 %r) { 
maxHeapify_entry:
	%tmp. = getelementptr i32, i32* %a, i32 %p
	%tmp..1 = load i32, i32* %tmp.
	br label %maxHeapify.1_while.cond
maxHeapify.1_while.cond:
	%s = phi i32 [%p, %maxHeapify_entry], [%s.1, %maxHeapify.12_if.end]
	%tmp..6 = mul i32 2, %s
	%tmp..7 = icmp sle i32 %tmp..6, %r
	br i1 %tmp..7, label %maxHeapify.2_while.body, label %maxHeapify.3_while.end
maxHeapify.2_while.body:
	%tmp..11 = icmp slt i32 %tmp..6, %r
	br i1 %tmp..11, label %maxHeapify.4_if.true, label %maxHeapify.5_if.false
maxHeapify.4_if.true:
	%tmp..20 = add i32 %tmp..6, 1
	%tmp..21 = getelementptr i32, i32* %a, i32 %tmp..20
	%tmp..22 = load i32, i32* %tmp..21
	%tmp..23 = getelementptr i32, i32* %a, i32 %tmp..6
	%tmp..24 = load i32, i32* %tmp..23
	%tmp..25 = icmp sgt i32 %tmp..22, %tmp..24
	br i1 %tmp..25, label %maxHeapify.7_if.true, label %maxHeapify.8_if.false
maxHeapify.7_if.true:
	br label %maxHeapify.9_if.end
maxHeapify.8_if.false:
	br label %maxHeapify.9_if.end
maxHeapify.9_if.end:
	%t = phi i32 [%tmp..20, %maxHeapify.7_if.true], [%tmp..6, %maxHeapify.8_if.false]
	br label %maxHeapify.6_if.end
maxHeapify.5_if.false:
	br label %maxHeapify.6_if.end
maxHeapify.6_if.end:
	%t.1 = phi i32 [%t, %maxHeapify.9_if.end], [%tmp..6, %maxHeapify.5_if.false]
	%tmp..31 = getelementptr i32, i32* %a, i32 %t.1
	%tmp..32 = load i32, i32* %tmp..31
	%tmp..33 = icmp sge i32 %tmp..1, %tmp..32
	br i1 %tmp..33, label %maxHeapify.10_if.true, label %maxHeapify.11_if.false
maxHeapify.10_if.true:
	%tmp..34 = getelementptr i32, i32* %a, i32 %s
	store i32 %tmp..1, i32* %tmp..34
	%tmp..36 = add i32 %r, 1
	br label %maxHeapify.12_if.end
maxHeapify.11_if.false:
	%tmp..37 = getelementptr i32, i32* %a, i32 %s
	%tmp..39 = load i32, i32* %tmp..31
	store i32 %tmp..39, i32* %tmp..37
	br label %maxHeapify.12_if.end
maxHeapify.12_if.end:
	%s.1 = phi i32 [%tmp..36, %maxHeapify.10_if.true], [%t.1, %maxHeapify.11_if.false]
	br label %maxHeapify.1_while.cond
maxHeapify.3_while.end:
	%tmp..41 = icmp sle i32 %s, %r
	br i1 %tmp..41, label %maxHeapify.13_if.true, label %maxHeapify.14_if.false
maxHeapify.13_if.true:
	%tmp..42 = getelementptr i32, i32* %a, i32 %s
	store i32 %tmp..1, i32* %tmp..42
	br label %maxHeapify.15_if.end
maxHeapify.14_if.false:
	br label %maxHeapify.15_if.end
maxHeapify.15_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	%tmp..2 = call i32 @readInt()
	%tmp..3 = mul i32 %tmp..2, 4
	%tmp..4 = add i32 %tmp..3, 4
	%tmp..5 = call i8* @calloc(i32 1, i32 %tmp..4)
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	store i32 %tmp..2, i32* %tmp..6
	%tmp..7 = getelementptr i8, i8* %tmp..5, i32 4
	%tmp..8 = bitcast i8* %tmp..7 to i32*
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [0, %main_entry], [%tmp..23, %main.2_while.body]
	%tmp..16 = bitcast i32* %tmp..8 to i8*
	%tmp..15 = getelementptr i8, i8* %tmp..16, i32 -4
	%tmp..17 = bitcast i8* %tmp..15 to i32*
	%tmp..18 = load i32, i32* %tmp..17
	%tmp..19 = icmp slt i32 %i, %tmp..18
	br i1 %tmp..19, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..20 = getelementptr i32, i32* %tmp..8, i32 %i
	%tmp..21 = call i32 @readInt()
	store i32 %tmp..21, i32* %tmp..20
	%tmp..23 = add i32 %i, 1
	br label %main.1_while.cond
main.3_while.end:
	%tmp..25 = sub i32 %tmp..2, 1
	%tmp..27 = sdiv i32 %tmp..25, 2
	br label %main.4_while.cond
main.4_while.cond:
	%i.1 = phi i32 [%tmp..27, %main.3_while.end], [%tmp..36, %main.5_while.body]
	%tmp..31 = icmp sge i32 %i.1, 0
	br i1 %tmp..31, label %main.5_while.body, label %main.6_while.end
main.5_while.body:
	call void @maxHeapify(i32* %tmp..8, i32 %i.1, i32 %tmp..25)
	%tmp..36 = sub i32 %i.1, 1
	br label %main.4_while.cond
main.6_while.end:
	br label %main.7_while.cond
main.7_while.cond:
	%i.2 = phi i32 [%tmp..25, %main.6_while.end], [%tmp..53, %main.8_while.body]
	%tmp..42 = icmp sge i32 %i.2, 1
	br i1 %tmp..42, label %main.8_while.body, label %main.9_while.end
main.8_while.body:
	%tmp..43 = getelementptr i32, i32* %tmp..8, i32 %i.2
	%tmp..44 = load i32, i32* %tmp..43
	%tmp..47 = getelementptr i32, i32* %tmp..8, i32 0
	%tmp..48 = load i32, i32* %tmp..47
	store i32 %tmp..48, i32* %tmp..43
	store i32 %tmp..44, i32* %tmp..47
	%tmp..53 = sub i32 %i.2, 1
	call void @maxHeapify(i32* %tmp..8, i32 0, i32 %tmp..53)
	br label %main.7_while.cond
main.9_while.end:
	br label %main.10_while.cond
main.10_while.cond:
	%i.3 = phi i32 [0, %main.9_while.end], [%tmp..74, %main.15_if.end]
	%tmp..63 = icmp slt i32 %i.3, %tmp..25
	br i1 %tmp..63, label %main.11_while.body, label %main.12_while.end
main.11_while.body:
	%tmp..71 = getelementptr i32, i32* %tmp..8, i32 %i.3
	%tmp..72 = load i32, i32* %tmp..71
	%tmp..74 = add i32 %i.3, 1
	%tmp..75 = getelementptr i32, i32* %tmp..8, i32 %tmp..74
	%tmp..76 = load i32, i32* %tmp..75
	%tmp..77 = icmp sgt i32 %tmp..72, %tmp..76
	br i1 %tmp..77, label %main.13_if.true, label %main.14_if.false
main.13_if.true:
	call void @error()
	ret i32 0
main.14_if.false:
	br label %main.15_if.end
main.15_if.end:
	br label %main.10_while.cond
main.12_while.end:
	%tmp..83 = bitcast i32* %tmp..8 to i8*
	%tmp..82 = getelementptr i8, i8* %tmp..83, i32 -4
	%tmp..84 = bitcast i8* %tmp..82 to i32*
	%tmp..85 = load i32, i32* %tmp..84
	br label %main.16_for.cond
main.16_for.cond:
	%i.d.x..1 = phi i32 [0, %main.12_while.end], [%tmp..90, %main.17_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..85
	br i1 %ifres., label %main.17_for.body, label %main.18_for.end
main.17_for.body:
	%tmp..87 = getelementptr i32, i32* %tmp..8, i32 %i.d.x..1
	%tmp..88 = load i32, i32* %tmp..87
	%tmp..90 = add i32 %i.d.x..1, 1
	call void @printInt(i32 %tmp..88)
	br label %main.16_for.cond
main.18_for.end:
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

@._runtime_error = internal constant [15 x i8] c"runtime error\0A\00"
declare void @exit(i32)
define void @error() {
entry:  %t0 = getelementptr [15 x i8], [15 x i8]* @._runtime_error, i32 0, i32 0
call void @printString(i8* %t0)

call void @exit(i32 1)
	ret void
}

declare i8* @calloc(i32, i32)
@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

