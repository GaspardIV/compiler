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
@Test void testShapes() { testUtils.standardTestInputOutput("lattests/good/objects2/Extensions/shapes.lat", "lattests/good/objects2/Extensions/shapes.output", "lattests/good/objects2/Extensions/shapes.err", 0);}
}
