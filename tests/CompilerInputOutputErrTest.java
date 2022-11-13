import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CompilerInputOutputErrTest {
    private static final boolean SHOULD_GENERATE_EXPECTED_OUTPUT = false;
    private final PrintStream standardOut = System.out;
    private final PrintStream standardErr = System.err;
    protected final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    protected final ByteArrayOutputStream errStreamCaptor = new ByteArrayOutputStream();

    protected final TestUtils testUtils = new TestUtils(SHOULD_GENERATE_EXPECTED_OUTPUT, outputStreamCaptor, errStreamCaptor);

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
}
