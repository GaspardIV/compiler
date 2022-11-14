import org.junit.jupiter.api.Test;

class GoodTestsMyTests extends CompilerInputOutputErrTest {
    @Test void testMyTestsTestReturnInIfAndInElse() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test_return_in_if_and_in_else.lat", "lattests/good/my_tests/test_return_in_if_and_in_else.output", "lattests/good/my_tests/test_return_in_if_and_in_else.err", 0);
    }
    @Test void testMyTestsTestReturnInIfAndInError() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test_return_in_if_and_in_error.lat", "lattests/good/my_tests/test_return_in_if_and_in_error.output", "lattests/good/my_tests/test_return_in_if_and_in_error.err", 0);
    }
}