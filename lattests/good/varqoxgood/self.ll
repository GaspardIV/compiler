@.str.str1 = private unnamed_addr constant [1 x i8] c"\00", align 1@.str.str0 = private unnamed_addr constant [4 x i8] c"abc\00", align 1 ; --- Class X ---
@X.vtable = global [1 x void (...)*] [
	void (...)* bitcast (i32 (%X*)* @X.getX to void (...)*) ; getX 
]

%X = type { 
	void (...)**; vtable
	,i32; x 
	}
 ; --- Class Y ---
@Y.vtable = global [3 x void (...)*] [
	void (...)* bitcast (i32 (%X*)* @X.getX to void (...)*) , ; getX 
	void (...)* bitcast (i8* (%Y*)* @Y.getY to void (...)*) , ; getY 
	void (...)* bitcast (void (%Y*)* @Y.test to void (...)*) ; test 
]

%Y = type { 
	void (...)**; vtable
	,i32; x 
	,i8*; y 
	}
 ; --- Class X methods ---
define void @X.constructor(%X* %this) {
	%this.class.vtable = bitcast [1 x void (...)*]* @X.vtable to void (...)**
	%this.vtable = getelementptr %X, %X* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %X, %X* %this, i32 0, i32 1
	store i32 0, i32* %x
	ret void
}

define i32 @X.getX(%X* %self) { 
X.getX_entry:
	%tmp. = getelementptr %X, %X* %self, i32 0, i32 1
	%tmp..1 = load i32, i32* %tmp.
	ret i32 %tmp..1
}
 ; --- Class Y methods ---
define void @Y.constructor(%Y* %this) {
	%this.class.vtable = bitcast [3 x void (...)*]* @Y.vtable to void (...)**
	%this.vtable = getelementptr %Y, %Y* %this, i32 0, i32 0
	store void (...)** %this.class.vtable, void (...)*** %this.vtable
	%x = getelementptr %Y, %Y* %this, i32 0, i32 1
	store i32 0, i32* %x
	%y = getelementptr %Y, %Y* %this, i32 0, i32 2
	%ytmp = getelementptr [1 x i8], [1 x i8]* @.str.str1, i32 0, i32 0
	store i8* %ytmp, i8** %y
	ret void
}

define i8* @Y.getY(%Y* %self) { 
Y.getY_entry:
	%tmp. = getelementptr %Y, %Y* %self, i32 0, i32 2
	%tmp..1 = load i8*, i8** %tmp.
	ret i8* %tmp..1
}

define void @Y.test(%Y* %self) { 
Y.test_entry:
	%tmp. = getelementptr %Y, %Y* %self, i32 0, i32 1
	store i32 142, i32* %tmp.
	%tmp..2 = getelementptr %Y, %Y* %self, i32 0, i32 2
	%tmp..3 = getelementptr [4 x i8], [4 x i8]* @.str.str0, i32 0, i32 0
	store i8* %tmp..3, i8** %tmp..2
	%tmp..15 = load i32, i32* %tmp.
	%tmp..17 = load i32, i32* %tmp.
	%tmp..18 = icmp ne i32 %tmp..15, %tmp..17
	br i1 %tmp..18, label %Y.test.1_if.true, label %Y.test.4_or
Y.test.4_or:
	%tmp..20 = load i32, i32* %tmp.
	%tmp..22 = icmp ne i32 %tmp..20, 142
	br i1 %tmp..22, label %Y.test.1_if.true, label %Y.test.3_if.end
Y.test.1_if.true:
	call void @error()
	ret void
Y.test.3_if.end:
	%tmp..43 = getelementptr %Y, %Y* %self, i32 0, i32 0
	%tmp..44 = load void (...)**, void (...)*** %tmp..43
	%tmp..45 = getelementptr void (...)*, void (...)** %tmp..44, i32 0
	%tmp..46 = bitcast void (...)** %tmp..45 to i32 (%X*)**
	%tmp..47 = load i32 (%X*)*, i32 (%X*)** %tmp..46
	%tmp..48 = bitcast %Y* %self to %X*
	%tmp..49 = call i32 %tmp..47(%X* %tmp..48)
	%tmp..51 = icmp ne i32 %tmp..49, 142
	br i1 %tmp..51, label %Y.test.5_if.true, label %Y.test.8_or
Y.test.8_or:
	%tmp..53 = load void (...)**, void (...)*** %tmp..43
	%tmp..54 = getelementptr void (...)*, void (...)** %tmp..53, i32 0
	%tmp..55 = bitcast void (...)** %tmp..54 to i32 (%X*)**
	%tmp..56 = load i32 (%X*)*, i32 (%X*)** %tmp..55
	%tmp..57 = bitcast %Y* %self to %X*
	%tmp..58 = call i32 %tmp..56(%X* %tmp..57)
	%tmp..60 = icmp ne i32 %tmp..58, 142
	br i1 %tmp..60, label %Y.test.5_if.true, label %Y.test.7_if.end
Y.test.5_if.true:
	call void @error()
	ret void
Y.test.7_if.end:
	%tmp..77 = load i8*, i8** %tmp..2
	%tmp..79 = load i8*, i8** %tmp..2
	%tmp..80 = call i32 @._strcmp(i8* %tmp..77, i8* %tmp..79)
	%tmp..81 = icmp ne i32 %tmp..80, 0
	br i1 %tmp..81, label %Y.test.9_if.true, label %Y.test.12_or
Y.test.12_or:
	%tmp..84 = load i8*, i8** %tmp..2
	%tmp..86 = call i32 @._strcmp(i8* %tmp..84, i8* %tmp..3)
	%tmp..87 = icmp ne i32 %tmp..86, 0
	br i1 %tmp..87, label %Y.test.9_if.true, label %Y.test.11_if.end
Y.test.9_if.true:
	call void @error()
	ret void
Y.test.11_if.end:
	%tmp..112 = load void (...)**, void (...)*** %tmp..43
	%tmp..113 = getelementptr void (...)*, void (...)** %tmp..112, i32 1
	%tmp..114 = bitcast void (...)** %tmp..113 to i8* (%Y*)**
	%tmp..115 = load i8* (%Y*)*, i8* (%Y*)** %tmp..114
	%tmp..116 = call i8* %tmp..115(%Y* %self)
	%tmp..118 = call i32 @._strcmp(i8* %tmp..116, i8* %tmp..3)
	%tmp..119 = icmp ne i32 %tmp..118, 0
	br i1 %tmp..119, label %Y.test.13_if.true, label %Y.test.16_or
Y.test.16_or:
	%tmp..122 = load void (...)**, void (...)*** %tmp..43
	%tmp..123 = getelementptr void (...)*, void (...)** %tmp..122, i32 1
	%tmp..124 = bitcast void (...)** %tmp..123 to i8* (%Y*)**
	%tmp..125 = load i8* (%Y*)*, i8* (%Y*)** %tmp..124
	%tmp..126 = call i8* %tmp..125(%Y* %self)
	%tmp..128 = call i32 @._strcmp(i8* %tmp..126, i8* %tmp..3)
	%tmp..129 = icmp ne i32 %tmp..128, 0
	br i1 %tmp..129, label %Y.test.13_if.true, label %Y.test.15_if.end
Y.test.13_if.true:
	call void @error()
	ret void
Y.test.15_if.end:
	%tmp..133 = icmp ne %Y* %self, %self
	br i1 %tmp..133, label %Y.test.17_if.true, label %Y.test.19_if.end
Y.test.17_if.true:
	call void @error()
	ret void
Y.test.19_if.end:
	%tmp..141 = load i32, i32* %tmp.
	%tmp..143 = load i32, i32* %tmp.
	%tmp..144 = icmp ne i32 %tmp..141, %tmp..143
	br i1 %tmp..144, label %Y.test.20_if.true, label %Y.test.22_if.end
Y.test.20_if.true:
	call void @error()
	ret void
Y.test.22_if.end:
	%tmp..154 = load i8*, i8** %tmp..2
	%tmp..156 = load i8*, i8** %tmp..2
	%tmp..157 = call i32 @._strcmp(i8* %tmp..154, i8* %tmp..156)
	%tmp..158 = icmp ne i32 %tmp..157, 0
	br i1 %tmp..158, label %Y.test.23_if.true, label %Y.test.25_if.end
Y.test.23_if.true:
	call void @error()
	ret void
Y.test.25_if.end:
	ret void
}

define i32 @main() { 
main_entry:
	%tmp. = call i8* @malloc(i32 96)
	%tmp..1 = bitcast i8* %tmp. to %Y*
	call void @Y.constructor(%Y* %tmp..1)
	%tmp..3 = getelementptr %Y, %Y* %tmp..1, i32 0, i32 0
	%tmp..4 = load void (...)**, void (...)*** %tmp..3
	%tmp..5 = getelementptr void (...)*, void (...)** %tmp..4, i32 2
	%tmp..6 = bitcast void (...)** %tmp..5 to void (%Y*)**
	%tmp..7 = load void (%Y*)*, void (%Y*)** %tmp..6
	call void %tmp..7(%Y* %tmp..1)
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
declare i32 @strcmp(i8*, i8*)
define i32 @._strcmp(i8* %str1, i8* %str2) {
       %t0 = call i32 @strcmp(i8* %str1, i8* %str2)
       ret i32 %t0
}

