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
	echo "#!/usr/bin/java -jar" > latc
	cat build/compiler.jar >> latc
	chmod a+x latc
	rm -r build > /dev/null 2>/dev/null || true;

recompileBNFCJava:
	rm -r bnfc-java > /dev/null 2>/dev/null || true;
	mkdir "bnfc-java" > /dev/null 2>/dev/null || true;
	cd bnfc-java && bnfc -m --java --jflex -l ../Latte.cf && sed  "s/-sourcepath ./-sourcepath . -cp ..\/third_party\/java-cup-11b-runtime.jar/" Makefile | sed "s/java_cup.Main/-cp ..\/third_party\/java-cup-11b.jar java_cup.Main/" | sed "s/jflex/java -jar ..\/third_party\/JFlex.jar/" > Makefile2
	cp bnfc-java/Makefile2 bnfc-java/Makefile && rm bnfc-java/Makefile2
	cd bnfc-java && make

	#bnfc --java -l -m ../Latte.cf

cleanBuild : clean all

clean :
	rm latc > /dev/null 2>/dev/null || true;
	rm -r build > /dev/null 2>/dev/null || true;
