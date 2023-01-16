import org.junit.jupiter.api.BeforeEach;
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
//@Test void testTest_Return_In_If_And_In_Error() { testUtils.standardTestInputOutput("lattests/good/my_tests/test_return_in_if_and_in_error.lat", "lattests/good/my_tests/test_return_in_if_and_in_error.output", "lattests/good/my_tests/test_return_in_if_and_in_error.err", 0);}
    //test_tescik.lat
    @Test void testTest_Tescik() { testUtils.standardTestInputOutput("lattests/good/my_tests/test_tescik.lat", "lattests/good/my_tests/test_tescik.output", "lattests/good/my_tests/test_tescik.err", 0);}
//    test1.lat
//    test2.lat
//    test3.lat
//    test4.lat
//    test5.lat
//    test6.lat
//    test7.lat

    @Test void test1() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test1.lat", "lattests/good/my_tests/test1.output", "lattests/good/my_tests/test1.err", 0);
    }


    @Test void test2() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test2.lat", "lattests/good/my_tests/test2.output", "lattests/good/my_tests/test2.err", 0);
    }

    @Test void test22() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test22.lat", "lattests/good/my_tests/test22.output", "lattests/good/my_tests/test22.err", 0);
    }
    @Test void test223() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test223.lat", "lattests/good/my_tests/test223.output", "lattests/good/my_tests/test223.err", 0);
    }

    @Test void test3() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test3.lat", "lattests/good/my_tests/test3.output", "lattests/good/my_tests/test3.err", 0);
    }

    @Test void test4() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test4.lat", "lattests/good/my_tests/test4.output", "lattests/good/my_tests/test4.err", 0);
    }

    @Test void test5() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test5.lat", "lattests/good/my_tests/test5.output", "lattests/good/my_tests/test5.err", 0);
    }

    @Test void test6() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test6.lat", "lattests/good/my_tests/test6.output", "lattests/good/my_tests/test6.err", 0);
    }

    @Test void test7() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/test7.lat", "lattests/good/my_tests/test7.output", "lattests/good/my_tests/test7.err", 0);
    }

//    @Test void test1() {
//        testUtils.standardTestInputOutput("lattests/good/my_tests/test1.lat", "lattests/good/my_tests/test1.output", "lattests/good/my_tests/test1.err", 0);
//    }
//
//    @Test void test1() {
//        testUtils.standardTestInputOutput("lattests/good/my_tests/test1.lat", "lattests/good/my_tests/test1.output", "lattests/good/my_tests/test1.err", 0);
//    }


}
