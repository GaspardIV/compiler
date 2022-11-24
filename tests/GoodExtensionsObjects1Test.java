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
@Test void testPoints() { testUtils.standardTestInputOutput("lattests/good/extensions/objects1/points.lat", "lattests/good/extensions/objects1/points.output", "lattests/good/extensions/objects1/points.err", 0);}
@Test void testLinked() { testUtils.standardTestInputOutput("lattests/good/extensions/objects1/linked.lat", "lattests/good/extensions/objects1/linked.output", "lattests/good/extensions/objects1/linked.err", 0);}
@Test void testQueue() { testUtils.standardTestInputOutput("lattests/good/extensions/objects1/queue.lat", "lattests/good/extensions/objects1/queue.output", "lattests/good/extensions/objects1/queue.err", 0);}
@Test void testCounter() { testUtils.standardTestInputOutput("lattests/good/extensions/objects1/counter.lat", "lattests/good/extensions/objects1/counter.output", "lattests/good/extensions/objects1/counter.err", 0);}
}
