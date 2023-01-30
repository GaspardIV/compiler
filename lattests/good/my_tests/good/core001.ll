@.str.str0 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str3 = private unnamed_addr constant [9 x i8] c"/* world\00", align 1@.str.str2 = private unnamed_addr constant [9 x i8] c"hello */\00", align 1@.str.str1 = private unnamed_addr constant [2 x i8] c"=\00", align 1
define i32 @rfac(i32 %n) { 
rfac_entry:
	%tmp..3 = icmp eq i32 %n, 0
	br i1 %tmp..3, label %rfac.1_if.true, label %rfac.2_if.false
rfac.1_if.true:
	ret i32 1
rfac.2_if.false:
	%tmp..6 = sub i32 %n, 1
	%tmp..7 = call i32 @rfac(i32 %tmp..6)
	%tmp..8 = mul i32 %n, %tmp..7
	ret i32 %tmp..8
}

define i32 @ifac2f(i32 %l, i32 %h) { 
ifac2f_entry:
	%tmp..1 = icmp eq i32 %l, %h
	br i1 %tmp..1, label %ifac2f.1_if.true, label %ifac2f.2_if.false
ifac2f.1_if.true:
	ret i32 %l
ifac2f.2_if.false:
	br label %ifac2f.3_if.end
ifac2f.3_if.end:
	%tmp..3 = icmp sgt i32 %l, %h
	br i1 %tmp..3, label %ifac2f.4_if.true, label %ifac2f.5_if.false
ifac2f.4_if.true:
	ret i32 1
ifac2f.5_if.false:
	br label %ifac2f.6_if.end
ifac2f.6_if.end:
	%tmp..6 = add i32 %l, %h
	%tmp..8 = sdiv i32 %tmp..6, 2
	%tmp..9 = call i32 @ifac2f(i32 %l, i32 %tmp..8)
	%tmp..11 = add i32 %tmp..8, 1
	%tmp..12 = call i32 @ifac2f(i32 %tmp..11, i32 %h)
	%tmp..13 = mul i32 %tmp..9, %tmp..12
	ret i32 %tmp..13
}

define i32 @mfac(i32 %n) { 
mfac_entry:
	%tmp..3 = icmp eq i32 %n, 0
	br i1 %tmp..3, label %mfac.1_if.true, label %mfac.2_if.false
mfac.1_if.true:
	ret i32 1
mfac.2_if.false:
	%tmp..6 = sub i32 %n, 1
	%tmp..7 = call i32 @nfac(i32 %tmp..6)
	%tmp..8 = mul i32 %n, %tmp..7
	ret i32 %tmp..8
}

define i32 @fac(i32 %a) { 
fac_entry:
	br label %fac.1_while.cond
fac.1_while.cond:
	%n = phi i32 [%a, %fac_entry], [%tmp..9, %fac.2_while.body]
	%r = phi i32 [1, %fac_entry], [%tmp..7, %fac.2_while.body]
	%tmp..6 = icmp sgt i32 %n, 0
	br i1 %tmp..6, label %fac.2_while.body, label %fac.3_while.end
fac.2_while.body:
	%tmp..7 = mul i32 %r, %n
	%tmp..9 = sub i32 %n, 1
	br label %fac.1_while.cond
fac.3_while.end:
	ret i32 %r
}

define i32 @nfac(i32 %n) { 
nfac_entry:
	%tmp..3 = icmp ne i32 %n, 0
	br i1 %tmp..3, label %nfac.1_if.true, label %nfac.2_if.false
nfac.1_if.true:
	%tmp..5 = sub i32 %n, 1
	%tmp..6 = call i32 @mfac(i32 %tmp..5)
	%tmp..7 = mul i32 %tmp..6, %n
	ret i32 %tmp..7
nfac.2_if.false:
	ret i32 1
}

define i32 @main() { 
main_entry:
	%tmp..1 = call i32 @fac(i32 10)
	call void @printInt(i32 %tmp..1)
	%tmp..4 = call i32 @rfac(i32 10)
	call void @printInt(i32 %tmp..4)
	%tmp..7 = call i32 @mfac(i32 10)
	call void @printInt(i32 %tmp..7)
	%tmp..10 = call i32 @ifac(i32 10)
	call void @printInt(i32 %tmp..10)
	br label %main.1_while.cond
main.1_while.cond:
	%n = phi i32 [10, %main_entry], [%tmp..21, %main.2_while.body]
	%r = phi i32 [1, %main_entry], [%tmp..19, %main.2_while.body]
	%tmp..18 = icmp sgt i32 %n, 0
	br i1 %tmp..18, label %main.2_while.body, label %main.3_while.end
main.2_while.body:
	%tmp..19 = mul i32 %r, %n
	%tmp..21 = sub i32 %n, 1
	br label %main.1_while.cond
main.3_while.end:
	call void @printInt(i32 %r)
	%tmp..23 = getelementptr [2 x i8], [2 x i8]* @.str.str1, i32 0, i32 0
	%tmp..25 = call i8* @repStr(i8* %tmp..23, i32 60)
	call void @printString(i8* %tmp..25)
	%tmp..27 = getelementptr [9 x i8], [9 x i8]* @.str.str2, i32 0, i32 0
	call void @printString(i8* %tmp..27)
	%tmp..29 = getelementptr [9 x i8], [9 x i8]* @.str.str3, i32 0, i32 0
	call void @printString(i8* %tmp..29)
	ret i32 0
}

define i32 @ifac(i32 %n) { 
ifac_entry:
	%tmp..1 = call i32 @ifac2f(i32 1, i32 %n)
	ret i32 %tmp..1
}

define i8* @repStr(i8* %s, i32 %n) { 
repStr_entry:
	%tmp. = getelementptr [1 x i8], [1 x i8]* @.str.str0, i32 0, i32 0
	br label %repStr.1_while.cond
repStr.1_while.cond:
	%i = phi i32 [0, %repStr_entry], [%tmp..6, %repStr.2_while.body]
	%r = phi i8* [%tmp., %repStr_entry], [%tmp..4, %repStr.2_while.body]
	%tmp..3 = icmp slt i32 %i, %n
	br i1 %tmp..3, label %repStr.2_while.body, label %repStr.3_while.end
repStr.2_while.body:
	%tmp..4 = call i8* @._concat(i8* %r, i8* %s)
	%tmp..6 = add i32 %i, 1
	br label %repStr.1_while.cond
repStr.3_while.end:
	ret i8* %r
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

declare i8* @malloc(i32)

declare i8* @strcat(i8*, i8*)
declare i8* @strcpy(i8*, i8*)
declare i32 @strlen(i8*)
define i8* @._concat(i8* %s1, i8* %s2) {
%1 = call i32 @strlen(i8* %s1)
%2 = call i32 @strlen(i8* %s2)
%3 = add i32 %1, %2
%4 = add i32 %3, 1
%5 = call i8* @malloc(i32 %4)
%6 = call i8* @strcpy(i8* %5, i8* %s1)
%7 = call i8* @strcat(i8* %5, i8* %s2)
ret i8* %7
}

