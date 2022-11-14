import org.junit.jupiter.api.Test;

class BadTestsMyTests extends CompilerInputOutputErrTest {
    @Test void testMyTestTest() {
        testUtils.standardTestInputOutput("lattests/bad/my_tests/test.lat", "", "lattests/bad/my_tests/test.err", 1);
    }
    @Test void testMyTestTestAlreadyDefined() {
        testUtils.standardTestInputOutput("lattests/bad/my_tests/test_already_defined.lat", "", "lattests/bad/my_tests/test_already_defined.err", 1);
    }
    @Test void testMyTestTestNoMain() {
        testUtils.standardTestInputOutput("lattests/bad/my_tests/test_no_main.lat", "", "lattests/bad/my_tests/test_no_main.err", 1);
    }
    @Test void testMyTestTestReturnInIfNotInElse() {
        testUtils.standardTestInputOutput("lattests/bad/my_tests/test_return_in_if_not_in_else.lat", "", "lattests/bad/my_tests/test_return_in_if_not_in_else.err", 1);
    }
}