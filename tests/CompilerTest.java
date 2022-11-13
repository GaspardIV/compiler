import com.github.stefanbirkner.systemlambda.SystemLambda;
import latte.Compiler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {

    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(errStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        System.setErr(standardErr);
    }

    @Test
    void testBad01() {
        standardTestInputOutput("lattests/bad/bad001.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad02() {
        standardTestInputOutput("lattests/bad/bad002.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad03() {
        standardTestInputOutput("lattests/bad/bad003.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad04() {
        standardTestInputOutput("lattests/bad/bad004.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad05() {
        standardTestInputOutput("lattests/bad/bad005.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad06() {
        standardTestInputOutput("lattests/bad/bad006.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad07() {
        standardTestInputOutput("lattests/bad/bad007.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad08() {
        standardTestInputOutput("lattests/bad/bad008.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad09() {
        standardTestInputOutput("lattests/bad/bad009.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad10() {
        standardTestInputOutput("lattests/bad/bad010.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad11() {
        standardTestInputOutput("lattests/bad/bad011.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad12() {
        standardTestInputOutput("lattests/bad/bad012.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad13() {
        standardTestInputOutput("lattests/bad/bad013.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad14() {
        standardTestInputOutput("lattests/bad/bad014.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad15() {
        standardTestInputOutput("lattests/bad/bad015.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad16() {
        standardTestInputOutput("lattests/bad/bad016.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad17() {
        standardTestInputOutput("lattests/bad/bad017.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad18() {
        standardTestInputOutput("lattests/bad/bad018.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad19() {
        standardTestInputOutput("lattests/bad/bad019.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad20() {
        standardTestInputOutput("lattests/bad/bad020.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad21() {
        standardTestInputOutput("lattests/bad/bad021.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad22() {
        standardTestInputOutput("lattests/bad/bad022.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad23() {
        standardTestInputOutput("lattests/bad/bad023.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad24() {
        standardTestInputOutput("lattests/bad/bad024.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad25() {
        standardTestInputOutput("lattests/bad/bad025.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad26() {
        standardTestInputOutput("lattests/bad/bad026.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    @Test
    void testBad27() {
        standardTestInputOutput("lattests/bad/bad027.lat", "", 1, "Syntax Error, trying to recover and continue parse... for input symbol \"EOF\" spanning from unknown:2/-1(3) to unknown:2/-1(3)\n" +
                "ERROR\n" +
                "Parser error at line 2, near \"*\" :\n" +
                "     Unrecoverable Syntax Error");
    }

    void standardTestInputOutput(String inputFileName, String outputFileName, int exitCode, String err) {
        String[] args = {inputFileName};
        int status;
        try {
            status = SystemLambda.catchSystemExit(() -> {
                Compiler.main(args);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(exitCode, status);
        String expectedOutput;
        if (outputFileName != null && outputFileName.length() > 0) {
            expectedOutput = getFileContent(outputFileName);
        } else {
            expectedOutput = "";
        }
        assertEquals(err, errStreamCaptor.toString().trim());
        assertEquals(expectedOutput, outputStreamCaptor.toString().trim());
        standardOut.println("Test passed");
    }

    private String getFileContent(String fileName) {
        Path path = Paths.get(fileName);
        StringBuilder sb = new StringBuilder();
        try {
            Files.readAllLines(path).forEach(line -> sb.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}