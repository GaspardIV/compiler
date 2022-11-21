import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodGr5Test extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testBfs() { testUtils.standardTestInputOutput("lattests/good/gr5/bfs.lat", "lattests/good/gr5/bfs.output", "lattests/good/gr5/bfs.err", 0);}
@Test void testCalculator() { testUtils.standardTestInputOutput("lattests/good/gr5/calculator.lat", "lattests/good/gr5/calculator.output", "lattests/good/gr5/calculator.err", 0);}
@Test void testHeapsort2() { testUtils.standardTestInputOutput("lattests/good/gr5/heapsort2.lat", "lattests/good/gr5/heapsort2.output", "lattests/good/gr5/heapsort2.err", 0);}
@Test void testMergesort() { testUtils.standardTestInputOutput("lattests/good/gr5/mergeSort.lat", "lattests/good/gr5/mergeSort.output", "lattests/good/gr5/mergeSort.err", 0);}
@Test void testLista2Kierunkowa() { testUtils.standardTestInputOutput("lattests/good/gr5/lista2Kierunkowa.lat", "lattests/good/gr5/lista2Kierunkowa.output", "lattests/good/gr5/lista2Kierunkowa.err", 0);}
}
