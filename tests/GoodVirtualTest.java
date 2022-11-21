import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodVirtualTest extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testPolymorphism() { testUtils.standardTestInputOutput("lattests/good/virtual/polymorphism.lat", "lattests/good/virtual/polymorphism.output", "lattests/good/virtual/polymorphism.err", 0);}
}
