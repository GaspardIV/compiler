import com.github.stefanbirkner.systemlambda.SystemLambda;
import latte.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MyTest extends CompilerInputOutputErrTest {
    @Test
    void testBackend() {
        testUtils.standardTestInputOutput("tests/MyTest.java", "tests/MyTest.java", "tests/MyTest.java", 0);
    }
}
