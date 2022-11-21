import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodExtensionsObjects1Test extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testPoints() { testUtils.standardTestInputOutput("lattests/good/objects1/Extensions/points.lat", "lattests/good/objects1/Extensions/points.output", "lattests/good/objects1/Extensions/points.err", 0);}
@Test void testLinked() { testUtils.standardTestInputOutput("lattests/good/objects1/Extensions/linked.lat", "lattests/good/objects1/Extensions/linked.output", "lattests/good/objects1/Extensions/linked.err", 0);}
@Test void testQueue() { testUtils.standardTestInputOutput("lattests/good/objects1/Extensions/queue.lat", "lattests/good/objects1/Extensions/queue.output", "lattests/good/objects1/Extensions/queue.err", 0);}
@Test void testCounter() { testUtils.standardTestInputOutput("lattests/good/objects1/Extensions/counter.lat", "lattests/good/objects1/Extensions/counter.output", "lattests/good/objects1/Extensions/counter.err", 0);}
}
