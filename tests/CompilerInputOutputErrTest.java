import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CompilerInputOutputErrTest {
    private static final boolean SHOULD_GENERATE_EXPECTED_OUTPUT = false;
    public final PrintStream standardOut = System.out;
    public final PrintStream standardErr = System.err;
    protected final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    protected final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();

    protected final TestUtils testUtils = new TestUtils(SHOULD_GENERATE_EXPECTED_OUTPUT, outputStreamCaptor, errStreamCaptor);

    @BeforeEach
    void setUp() {
        System.setProperty("run_bytecode", "true");
        System.setOut(new PrintStream(outputStreamCaptor));
        System.setErr(new PrintStream(errStreamCaptor));
        String overwrite = System.getenv("overwrite");
        if (overwrite != null && overwrite.equals("1") ) {
            testUtils.setShouldGenerateExpectedOutput(true);
        }
    }

    @AfterEach
    void tearDown() {
        standardOut.println(outputStreamCaptor.toString());
        standardErr.println(errStreamCaptor.toString());
        System.setOut(standardOut);
        System.setErr(standardErr);
        testUtils.setShouldGenerateExpectedOutput(false);
    }
}
