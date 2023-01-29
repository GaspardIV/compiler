
define void @swap(i32* %tab, i32 %x, i32 %y) { 
swap_entry:
	%tmp. = getelementptr i32, i32* %tab, i32 %x
	%tmp..1 = load i32, i32* %tmp.
	%tmp..3 = getelementptr i32, i32* %tab, i32 %y
	%tmp..4 = load i32, i32* %tmp..3
	store i32 %tmp..4, i32* %tmp.
	store i32 %tmp..1, i32* %tmp..3
	ret void
}

define i32 @extractMax(i32* %heap, i32 %heapSize) { 
extractMax_entry:
	%tmp..1 = getelementptr i32, i32* %heap, i32 0
	%tmp..2 = load i32, i32* %tmp..1
	%tmp..6 = sub i32 %heapSize, 1
	%tmp..7 = getelementptr i32, i32* %heap, i32 %tmp..6
	%tmp..8 = load i32, i32* %tmp..7
	store i32 %tmp..8, i32* %tmp..1
	call void @heapDown(i32* %heap, i32 0, i32 %tmp..6)
	ret i32 %tmp..2
}

define i32 @main() { 
main_entry:
	%tmp. = call i32 @readInt()
	%tmp..1 = mul i32 %tmp., 4
	%tmp..2 = add i32 %tmp..1, 4
	%tmp..3 = call i8* @calloc(i32 1, i32 %tmp..2)
	%tmp..4 = bitcast i8* %tmp..3 to i32*
	store i32 %tmp., i32* %tmp..4
	%tmp..5 = getelementptr i8, i8* %tmp..3, i32 4
	%tmp..6 = bitcast i8* %tmp..5 to i32*
	br label %main.1_while.cond
main.1_while.cond:
	%i = phi i32 [0, %main_entry], [%tmp..13, %main.2_while.body]
	%tmp..9 = icmp slt i32 %i, %tmp.
	br i1 %tmp..9, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..10 = getelementptr i32, i32* %tmp..6, i32 %i
	%tmp..11 = call i32 @readInt()
	store i32 %tmp..11, i32* %tmp..10
	%tmp..13 = add i32 %i, 1
	br label %main.1_while.cond
main.3_while.end:
	call void @heapSort(i32* %tmp..6)
	%tmp..16 = bitcast i32* %tmp..6 to i8*
	%tmp..15 = getelementptr i8, i8* %tmp..16, i32 -4
	%tmp..17 = bitcast i8* %tmp..15 to i32*
	%tmp..18 = load i32, i32* %tmp..17
	br label %main.4_for.cond
main.4_for.cond:
	%i.d.x..1 = phi i32 [0, %main.3_while.end], [%tmp..23, %main.5_for.body]
	%ifres. = icmp slt i32 %i.d.x..1, %tmp..18
	br i1 %ifres., label %main.5_for.body, label %main.6_for.end
main.5_for.body:
	%tmp..20 = getelementptr i32, i32* %tmp..6, i32 %i.d.x..1
	%tmp..21 = load i32, i32* %tmp..20
	%tmp..23 = add i32 %i.d.x..1, 1
	call void @printInt(i32 %tmp..21)
	br label %main.4_for.cond
main.6_for.end:
	ret i32 0
}

define void @heapSort(i32* %heap) { 
heapSort_entry:
	%tmp..1 = bitcast i32* %heap to i8*
	%tmp. = getelementptr i8, i8* %tmp..1, i32 -4
	%tmp..2 = bitcast i8* %tmp. to i32*
	%tmp..3 = load i32, i32* %tmp..2
	%tmp..5 = sdiv i32 %tmp..3, 2
	br label %heapSort.1_while.cond
heapSort.1_while.cond:
	%i = phi i32 [%tmp..5, %heapSort_entry], [%tmp..16, %heapSort.2_while.body]
	%tmp..9 = icmp sge i32 %i, 0
	br i1 %tmp..9, label %heapSort.2_while.body, label %heapSort.3_while.end
heapSort.2_while.body:
	%tmp..11 = bitcast i32* %heap to i8*
	%tmp..10 = getelementptr i8, i8* %tmp..11, i32 -4
	%tmp..12 = bitcast i8* %tmp..10 to i32*
	%tmp..13 = load i32, i32* %tmp..12
	call void @heapDown(i32* %heap, i32 %i, i32 %tmp..13)
	%tmp..16 = sub i32 %i, 1
	br label %heapSort.1_while.cond
heapSort.3_while.end:
	%tmp..18 = bitcast i32* %heap to i8*
	%tmp..17 = getelementptr i8, i8* %tmp..18, i32 -4
	%tmp..19 = bitcast i8* %tmp..17 to i32*
	%tmp..20 = load i32, i32* %tmp..19
	%tmp..22 = sub i32 %tmp..20, 1
	br label %heapSort.4_while.cond
heapSort.4_while.cond:
	%i.1 = phi i32 [%tmp..22, %heapSort.3_while.end], [%tmp..32, %heapSort.5_while.body]
	%tmp..26 = icmp sge i32 %i.1, 0
	br i1 %tmp..26, label %heapSort.5_while.body, label %heapSort.6_while.end
heapSort.5_while.body:
	%tmp..27 = getelementptr i32, i32* %heap, i32 %i.1
	%tmp..29 = add i32 %i.1, 1
	%tmp..30 = call i32 @extractMax(i32* %heap, i32 %tmp..29)
	store i32 %tmp..30, i32* %tmp..27
	%tmp..32 = sub i32 %i.1, 1
	br label %heapSort.4_while.cond
heapSort.6_while.end:
	ret void
}

define void @heapDown(i32* %heap, i32 %index, i32 %heapSize) { 
heapDown_entry:
	br label %heapDown.1_while.cond
heapDown.1_while.cond:
	%index.1 = phi i32 [%index, %heapDown_entry], [%max, %heapDown.10_if.end]
	%tmp..6 = mul i32 %index.1, 2
	%tmp..8 = sub i32 %heapSize, 1
	%tmp..9 = icmp slt i32 %tmp..6, %tmp..8
	br i1 %tmp..9, label %heapDown.2_while.body, label %heapDown.3_while.end
heapDown.2_while.body:
	%tmp..13 = add i32 %tmp..6, 1
	%tmp..15 = add i32 %tmp..13, 1
	%tmp..23 = icmp slt i32 %tmp..15, %heapSize
	br i1 %tmp..23, label %heapDown.7_and, label %heapDown.5_if.false
heapDown.7_and:
	%tmp..24 = getelementptr i32, i32* %heap, i32 %tmp..15
	%tmp..25 = load i32, i32* %tmp..24
	%tmp..26 = getelementptr i32, i32* %heap, i32 %tmp..13
	%tmp..27 = load i32, i32* %tmp..26
	%tmp..28 = icmp sgt i32 %tmp..25, %tmp..27
	br i1 %tmp..28, label %heapDown.4_if.true, label %heapDown.5_if.false
heapDown.4_if.true:
	br label %heapDown.6_if.end
heapDown.5_if.false:
	br label %heapDown.6_if.end
heapDown.6_if.end:
	%max = phi i32 [%tmp..15, %heapDown.4_if.true], [%tmp..13, %heapDown.5_if.false]
	%tmp..34 = getelementptr i32, i32* %heap, i32 %max
	%tmp..35 = load i32, i32* %tmp..34
	%tmp..36 = getelementptr i32, i32* %heap, i32 %index.1
	%tmp..37 = load i32, i32* %tmp..36
	%tmp..38 = icmp sgt i32 %tmp..35, %tmp..37
	br i1 %tmp..38, label %heapDown.8_if.true, label %heapDown.9_if.false
heapDown.8_if.true:
	call void @swap(i32* %heap, i32 %max, i32 %index.1)
	br label %heapDown.10_if.end
heapDown.9_if.false:
	ret void
heapDown.10_if.end:
	br label %heapDown.1_while.cond
heapDown.3_while.end:
	ret void
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

