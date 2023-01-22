@dnl = internal constant [4 x i8] c"%d\0A\00"
@d   = internal constant [3 x i8] c"%d\00"
@runtime_error = internal constant [15 x i8] c"runtime error\0A\00"

declare i32 @printf(i8*, ...)
declare i32 @scanf(i8*, ...)
declare i8* @readline(i8*)
declare i32 @puts(i8*)
declare void @exit(i32)

define void @printInt(i32 %x) {
       %t0 = getelementptr [4 x i8], [4 x i8]* @dnl, i32 0, i32 0
       call i32 (i8*, ...) @printf(i8* %t0, i32 %x)
       ret void
}


define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [3 x i8], [3 x i8]* @d, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}

define i8* @readString() {
entry:	%t1 = alloca i8
    %t2 = call i8* @readline(i8* %t1)
    ret i8* %t2
}


define void @error() {
    %t0 = getelementptr [15 x i8], [15 x i8]* @runtime_error, i32 0, i32 0
    call void @printString(i8* %t0)
    call void @exit(i32 1)
    ret void
}


define void @f30(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f10(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f32(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f31(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f12(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f34(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f11(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f33(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f14(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f13(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f0(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f16(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define i32 @main() { 
[]
}

define void @f1(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f15(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f2(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f18(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f3(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f17(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f4(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f5(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f19(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f6(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f7(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f8(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f9(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f21(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f20(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f23(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f22(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f25(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f24(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f27(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f26(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f29(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @f28(A %i0A %i1A %i2A %i3A %i4A %i5A %i6A %i7A %i8A %i9A %i10A %i11A %i12A %i13A %i14A %i15A %i16A %i17A %i18A %i19A %i20A %i21A %i22A %i23A %i24A %i25A %i26A %i27A %i28A %i29A %i30A %i31A %i32A %i33A %i34) { 
[]
}

define void @printClass(A %a) { 
[]
}
