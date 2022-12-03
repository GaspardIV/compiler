JAVAC=javac
JAVA_CUP=java-cup-11b-runtime.jar
JAVA_CUP_PATH=third_party/${JAVA_CUP}
SOURCE_DIR=src
DST_DIR=build
JAVA=java

all: recompile_BNFC_java build_compiler

build_compiler:
	mkdir ${DST_DIR}  > /dev/null 2>/dev/null || true;
	cp ${JAVA_CUP_PATH} ${DST_DIR};
	${JAVAC} src/latte/Compiler.java -sourcepath ${SOURCE_DIR} -cp ${JAVA_CUP_PATH} -d ${DST_DIR}
	echo "Main-Class: latte.Compiler" > ${DST_DIR}/manifest.mf
	cd ${DST_DIR} && jar xvf ${JAVA_CUP} java_cup > /dev/null && jar cfm compiler.jar manifest.mf latte java_cup && cd ..
	echo "#!/usr/bin/java -jar" > latc
	cat build/compiler.jar >> latc
	chmod a+x latc
	rm -r build > /dev/null 2>/dev/null || true;

recompile_BNFC_java:
	mkdir ${DST_DIR}  > /dev/null 2>/dev/null || true;
	rm -r build/bnfc-java > /dev/null 2>/dev/null || true;
	mkdir "build/bnfc-java" > /dev/null 2>/dev/null || true;
	cd build/bnfc-java && bnfc -m --java --jflex -l ../../Latte.cf && sed  "s/-sourcepath ./-sourcepath . -cp ..\/..\/third_party\/java-cup-11b-runtime.jar/" Makefile | sed "s/java_cup.Main/-cp ..\/..\/third_party\/java-cup-11b.jar java_cup.Main/" | sed "s/jflex/java -jar ..\/..\/third_party\/JFlex.jar/" > Makefile2
	cp build/bnfc-java/Makefile2 build/bnfc-java/Makefile && rm build/bnfc-java/Makefile2
	cd build/bnfc-java && make
	#for f in ./build/bnfc-java/latte/Absyn/*.java; do [[ -f "./src/latte/Absyn/$$(basename $$f)" ]] || cp "$$f" "./src/latte/Absyn/$$(basename $$f)"; done
	for f in ./build/bnfc-java/latte/Absyn/*.java; do cp "$$f" "./src/latte/Absyn/$$(basename $$f)"; done
	sed  "s/package latte;/package latte.parser;/" ./build/bnfc-java/latte/parser.java | sed  "s/report_error/\/\/report_error/" > ./src/latte/parser/parser.java
	sed  "s/package latte;/package latte.parser;/" ./build/bnfc-java/latte/Yylex.java > ./src/latte/parser/Yylex.java
	sed  "s/package latte;/package latte.parser;/" ./build/bnfc-java/latte/sym.java > ./src/latte/parser/sym.java
	sed  "s/package latte;/package latte.parser;/" ./build/bnfc-java/latte/PrettyPrinter.java > ./src/latte/parser/PrettyPrinter.java

cleanBuild : clean all

clean :
	rm latc > /dev/null 2>/dev/null || true;
	rm -r build > /dev/null 2>/dev/null || true;
