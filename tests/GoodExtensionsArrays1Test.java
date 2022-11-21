import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodExtensionsArrays1Test extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testArray001() { testUtils.standardTestInputOutput("lattests/good/arrays1/Extensions/array001.lat", "lattests/good/arrays1/Extensions/array001.output", "lattests/good/arrays1/Extensions/array001.err", 0);}
@Test void testArray002() { testUtils.standardTestInputOutput("lattests/good/arrays1/Extensions/array002.lat", "lattests/good/arrays1/Extensions/array002.output", "lattests/good/arrays1/Extensions/array002.err", 0);}
}
