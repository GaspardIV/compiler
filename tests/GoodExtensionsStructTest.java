import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodExtensionsStructTest extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testList() { testUtils.standardTestInputOutput("lattests/good/extensions/struct/list.lat", "lattests/good/extensions/struct/list.output", "lattests/good/extensions/struct/list.err", 0);}
}
