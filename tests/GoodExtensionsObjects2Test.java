import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodExtensionsObjects2Test extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testShapes() { testUtils.standardTestInputOutput("lattests/good/extensions/objects2/shapes.lat", "lattests/good/extensions/objects2/shapes.output", "lattests/good/extensions/objects2/shapes.err", 0);}
}
