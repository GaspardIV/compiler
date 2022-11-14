import org.junit.jupiter.api.Test;

class BadTestsRuntimeTest extends CompilerInputOutputErrTest {
    @Test void testBadRuntime01() {
        testUtils.standardTestInputOutput(true,"lattests/bad/runtime/error.lat", "", "lattests/bad/runtime/error.err", 1);
    }
}