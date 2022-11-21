import org.junit.jupiter.api.Test;

class GoodMyTestsTest extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testTest_Return_In_If_And_In_Else() { testUtils.standardTestInputOutput("lattests/good/my_tests/test_return_in_if_and_in_else.lat", "lattests/good/my_tests/test_return_in_if_and_in_else.output", "lattests/good/my_tests/test_return_in_if_and_in_else.err", 0);}
@Test void testTest_Return_In_If_And_In_Error() { testUtils.standardTestInputOutput("lattests/good/my_tests/test_return_in_if_and_in_error.lat", "lattests/good/my_tests/test_return_in_if_and_in_error.output", "lattests/good/my_tests/test_return_in_if_and_in_error.err", 0);}
}
