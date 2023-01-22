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


define void @f30( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f10( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f32( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f31( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f12( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f34( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f11( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f33( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f14( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f13( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f0( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f16( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define i32 @main() { 
[]
}

define void @f1( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f15( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f2( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f18( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f3( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f17( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f4( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f5( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f19( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f6( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f7( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f8( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f9( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f21( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f20( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f23( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f22( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f25( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f24( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f27( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f26( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f29( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @f28( %i0 %i1 %i2 %i3 %i4 %i5 %i6 %i7 %i8 %i9 %i10 %i11 %i12 %i13 %i14 %i15 %i16 %i17 %i18 %i19 %i20 %i21 %i22 %i23 %i24 %i25 %i26 %i27 %i28 %i29 %i30 %i31 %i32 %i33 %i34) { 
[]
}

define void @printArray( %a) { 
[]
}
