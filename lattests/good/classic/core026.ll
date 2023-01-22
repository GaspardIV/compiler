
define i32 @s(i32 %x) { 
s_entry:
	%tmp..1 = add i32 %x, 1
	ret i32 %tmp..1
}

define i32 @d() { 
d_entry:
	ret i32 0
}

define i32 @main() { 
main_entry:
	%tmp. = call i32 @d()
	%tmp..1 = call i32 @s(i32 %tmp.)
	%tmp..2 = call i32 @s(i32 %tmp..1)
	%tmp..3 = call i32 @s(i32 %tmp..2)
	%tmp..4 = call i32 @s(i32 %tmp..3)
	%tmp..5 = call i32 @s(i32 %tmp..4)
	%tmp..6 = call i32 @s(i32 %tmp..5)
	%tmp..7 = call i32 @s(i32 %tmp..6)
	%tmp..8 = call i32 @s(i32 %tmp..7)
	%tmp..9 = call i32 @s(i32 %tmp..8)
	%tmp..10 = call i32 @s(i32 %tmp..9)
	%tmp..11 = call i32 @s(i32 %tmp..10)
	%tmp..12 = call i32 @s(i32 %tmp..11)
	%tmp..13 = call i32 @s(i32 %tmp..12)
	%tmp..14 = call i32 @s(i32 %tmp..13)
	%tmp..15 = call i32 @s(i32 %tmp..14)
	%tmp..16 = call i32 @s(i32 %tmp..15)
	%tmp..17 = call i32 @s(i32 %tmp..16)
	%tmp..18 = call i32 @s(i32 %tmp..17)
	%tmp..19 = call i32 @s(i32 %tmp..18)
	%tmp..20 = call i32 @s(i32 %tmp..19)
	%tmp..21 = call i32 @s(i32 %tmp..20)
	%tmp..22 = call i32 @s(i32 %tmp..21)
	%tmp..23 = call i32 @s(i32 %tmp..22)
	%tmp..24 = call i32 @s(i32 %tmp..23)
	%tmp..25 = call i32 @s(i32 %tmp..24)
	%tmp..26 = call i32 @s(i32 %tmp..25)
	%tmp..27 = call i32 @s(i32 %tmp..26)
	%tmp..28 = call i32 @s(i32 %tmp..27)
	%tmp..29 = call i32 @s(i32 %tmp..28)
	%tmp..30 = call i32 @s(i32 %tmp..29)
	%tmp..31 = call i32 @s(i32 %tmp..30)
	%tmp..32 = call i32 @s(i32 %tmp..31)
	%tmp..33 = call i32 @s(i32 %tmp..32)
	%tmp..34 = call i32 @s(i32 %tmp..33)
	%tmp..35 = call i32 @s(i32 %tmp..34)
	%tmp..36 = call i32 @s(i32 %tmp..35)
	%tmp..37 = call i32 @s(i32 %tmp..36)
	%tmp..38 = call i32 @s(i32 %tmp..37)
	%tmp..39 = call i32 @s(i32 %tmp..38)
	%tmp..40 = call i32 @s(i32 %tmp..39)
	%tmp..41 = call i32 @s(i32 %tmp..40)
	%tmp..42 = call i32 @s(i32 %tmp..41)
	%tmp..43 = call i32 @s(i32 %tmp..42)
	%tmp..44 = call i32 @s(i32 %tmp..43)
	%tmp..45 = call i32 @s(i32 %tmp..44)
	%tmp..46 = call i32 @s(i32 %tmp..45)
	%tmp..47 = call i32 @s(i32 %tmp..46)
	%tmp..48 = call i32 @s(i32 %tmp..47)
	%tmp..49 = call i32 @s(i32 %tmp..48)
	%tmp..50 = call i32 @s(i32 %tmp..49)
	%tmp..51 = call i32 @s(i32 %tmp..50)
	%tmp..52 = call i32 @s(i32 %tmp..51)
	%tmp..53 = call i32 @s(i32 %tmp..52)
	%tmp..54 = call i32 @s(i32 %tmp..53)
	%tmp..55 = call i32 @s(i32 %tmp..54)
	%tmp..56 = call i32 @s(i32 %tmp..55)
	%tmp..57 = call i32 @s(i32 %tmp..56)
	%tmp..58 = call i32 @s(i32 %tmp..57)
	%tmp..59 = call i32 @s(i32 %tmp..58)
	%tmp..60 = call i32 @s(i32 %tmp..59)
	%tmp..61 = call i32 @s(i32 %tmp..60)
	%tmp..62 = call i32 @s(i32 %tmp..61)
	%tmp..63 = call i32 @s(i32 %tmp..62)
	%tmp..64 = call i32 @s(i32 %tmp..63)
	%tmp..65 = call i32 @s(i32 %tmp..64)
	%tmp..66 = call i32 @s(i32 %tmp..65)
	%tmp..67 = call i32 @s(i32 %tmp..66)
	%tmp..68 = call i32 @s(i32 %tmp..67)
	%tmp..69 = call i32 @s(i32 %tmp..68)
	%tmp..70 = call i32 @s(i32 %tmp..69)
	%tmp..71 = call i32 @s(i32 %tmp..70)
	%tmp..72 = call i32 @s(i32 %tmp..71)
	%tmp..73 = call i32 @s(i32 %tmp..72)
	%tmp..74 = call i32 @s(i32 %tmp..73)
	%tmp..75 = call i32 @s(i32 %tmp..74)
	%tmp..76 = call i32 @s(i32 %tmp..75)
	%tmp..77 = call i32 @s(i32 %tmp..76)
	%tmp..78 = call i32 @s(i32 %tmp..77)
	%tmp..79 = call i32 @s(i32 %tmp..78)
	%tmp..80 = call i32 @s(i32 %tmp..79)
	call void @printInt(i32 %tmp..80)
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

