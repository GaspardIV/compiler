import org.junit.jupiter.api.Test;

class BadTestsInfiniteLoopTest extends CompilerInputOutputErrTest {
    @Test void testBadInfiniteWhile() {
        testUtils.standardTestInputOutput("lattests/bad/infinite_loop/infinite_while.lat", "", "lattests/bad/infinite_loop/infinite_while.err", 1);
    }
}