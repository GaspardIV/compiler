 ; --- Class G ---
%G = type { 
}
define void @G.constructor(%G* %this) {
	ret void
}

define void @G.foo(%G* %self, %X* %x) { 
G.foo_entry:
	%tmp..2 = bitcast i32* null to %X*
	%tmp..3 = icmp eq %X* %x, %tmp..2
	br i1 %tmp..3, label %G.foo.1_if.true, label %G.foo.3_if.end
G.foo.1_if.true:
	call void @error()
	ret void
G.foo.3_if.end:
	ret void
}
 ; --- Class X ---
%X = type { 
}
define void @X.constructor(%X* %this) {
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 0)
	%tmp..1 = bitcast i8* %tmp. to %G*
	call void @G.constructor(%G* %tmp..1)
	%tmp..3 = call i8* @malloc(i32 0)
	%tmp..4 = bitcast i8* %tmp..3 to %X*
	call void @X.constructor(%X* %tmp..4)
	call void @G.foo(%G* %tmp..1, %X* %tmp..4)
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

@._runtime_error = internal constant [15 x i8] c"runtime error\0A\00"
declare void @exit(i32)
define void @error() {
entry:  %t0 = getelementptr [15 x i8], [15 x i8]* @._runtime_error, i32 0, i32 0
call void @printString(i8* %t0)

call void @exit(i32 1)
	ret void
}

declare i8* @malloc(i32)
