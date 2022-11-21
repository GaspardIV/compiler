import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodHardcoreTest extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testTail_Call_Optymization() { testUtils.standardTestInputOutput("lattests/good/hardcore/tail_call_optymization.lat", "lattests/good/hardcore/tail_call_optymization.output", "lattests/good/hardcore/tail_call_optymization.err", 0);}
@Test void testBig_String() { testUtils.standardTestInputOutput("lattests/good/hardcore/big_string.lat", "lattests/good/hardcore/big_string.output", "lattests/good/hardcore/big_string.err", 0);}
}
