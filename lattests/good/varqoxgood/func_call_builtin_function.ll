@.str.str0 = private unnamed_addr constant [8 x i8] c"abc\0Axyz\00", align 1 ; --- Class X ---
@X.vtable = global [1 x void (...)*] [
	void (...)* bitcast (void (%X*)* @X.foo to void (...)*) ; foo 
]

%X = type { 
	void (...)**; vtable
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	ret void
}

define void @X.foo(%X* %self) { 
X.foo_entry:
	call void @printInt(i32 42)
	%tmp..2 = getelementptr [8 x i8], [8 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	%tmp..4 = call i32 @readInt()
	%tmp..5 = call i8* @readString()
	call void @printInt(i32 %tmp..4)
	call void @printString(i8* %tmp..5)
	%tmp..11 = icmp eq i32 %tmp..4, 0
	br i1 %tmp..11, label %X.foo.1_if.true, label %X.foo.3_if.end
X.foo.1_if.true:
	call void @error()
	ret void
X.foo.3_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	call void @printInt(i32 42)
	%tmp..2 = getelementptr [8 x i8], [8 x i8]* @.str.str0, i32 0, i32 0
	call void @printString(i8* %tmp..2)
	%tmp..4 = call i32 @readInt()
	%tmp..5 = call i8* @readString()
	call void @printInt(i32 %tmp..4)
	call void @printString(i8* %tmp..5)
	%tmp..11 = icmp eq i32 %tmp..4, 0
	br i1 %tmp..11, label %main.1_if.true, label %main.3_if.end
main.1_if.true:
	call void @error()
	ret i32 0
main.3_if.end:
	%tmp..14 = call i8* @malloc(i32 0)
	%tmp..15 = bitcast i8* %tmp..14 to %X*
	call void @X.constructor(%X* %tmp..15)
	%tmp..17 = getelementptr %X, %X* %tmp..15, i32 0, i32 0
	%tmp..18 = load void (...)**, void (...)*** %tmp..17
	%tmp..19 = getelementptr void (...)*, void (...)** %tmp..18, i32 0
	%tmp..20 = bitcast void (...)** %tmp..19 to void (%X*)**
	%tmp..21 = load void (%X*)*, void (%X*)** %tmp..20
	call void %tmp..21(%X* %tmp..15)
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

declare i8* @malloc(i32)
@._dnl2 = internal constant [4 x i8] c"%d\0A\00"
declare i32 @scanf(i8*, ...)
define i32 @readInt() {
entry:	%res = alloca i32
        %t1 = getelementptr [4 x i8], [4 x i8]* @._dnl2, i32 0, i32 0
	call i32 (i8*, ...) @scanf(i8* %t1, i32* %res)
	%t2 = load i32, i32* %res
	ret i32 %t2
}


declare i8* @gets(i8*)
define i8* @readString() {
entry:
    %t1 = call i8* @malloc(i32 4096)
    %t2 = call i8* @gets(i8* %t1)
    ret i8* %t1
}

