JAVAC=javac
JAVA_CUP=java-cup-11b-runtime.jar
JAVA_CUP_PATH=third_party/${JAVA_CUP}
SOURCE_DIR=src
DST_DIR=build
JAVA=java
JAVA_FLAGS=

all :
	mkdir ${DST_DIR};
	cp ${JAVA_CUP_PATH} ${DST_DIR};
	${JAVAC} src/latte/Compiler.java -sourcepath ${SOURCE_DIR} -cp ${JAVA_CUP_PATH} -d ${DST_DIR}
	echo "Main-Class: latte.Compiler" > ${DST_DIR}/manifest.mf
	cd ${DST_DIR} && jar xvf ${JAVA_CUP} java_cup > /dev/null && jar cfm compiler.jar manifest.mf latte java_cup && cd ..
	echo "#!/usr/bin/java -jar" > compiler
	cat build/compiler.jar >> compiler
	chmod a+x compiler

cleanBuild : clean all

clean :
	rm compiler > /dev/null 2>/dev/null || true;
	rm compiler.jar > /dev/null 2>/dev/null || true;
	rm -r build > /dev/null 2>/dev/null || true;
