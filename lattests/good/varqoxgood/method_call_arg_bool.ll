@.str.str0 = private unnamed_addr constant [5 x i8] c"true\00", align 1@.str.str1 = private unnamed_addr constant [6 x i8] c"false\00", align 1 ; --- Class G ---
%G = type { 
}
define void @G.constructor(%G* %this) {
	ret void
}

define void @G.foo(%G* %self, i1 %b) { 
G.foo_entry:
	br i1 %b, label %G.foo.1_if.true, label %G.foo.2_if.false
G.foo.1_if.true:
	%tmp. = getelementptr [5 x i8], [5 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp.)
	br label %G.foo.3_if.end
G.foo.2_if.false:
	%tmp..2 = getelementptr [6 x i8], [6 x i8]* @.str.str1, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	br label %G.foo.3_if.end
G.foo.3_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %G*
	call void @G.constructor(%G* %tmp..1)
	call void @G.foo(%G* %tmp..1, i1 true)
	%tmp..5 = call i8* @malloc(i32 0)
	%tmp..6 = bitcast i8* %tmp..5 to %G*
	call void @G.constructor(%G* %tmp..6)
	call void @G.foo(%G* %tmp..6, i1 false)
	ret i32 0
}


; ====================================================
; ====================================================
; ====================================================

declare i32 @puts(i8*)
define void @printString(i8* %s) {
entry:  call i32 @puts(i8* %s)
	ret void
}

declare i8* @malloc(i32)
