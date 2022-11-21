import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodArraysTest extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testHeapsort() { testUtils.standardTestInputOutput("lattests/good/arrays/heapsort.lat", "lattests/good/arrays/heapsort.output", "lattests/good/arrays/heapsort.err", 0);}
@Test void testArray_String() { testUtils.standardTestInputOutput("lattests/good/arrays/array_string.lat", "lattests/good/arrays/array_string.output", "lattests/good/arrays/array_string.err", 0);}
@Test void testArray_Read() { testUtils.standardTestInputOutput("lattests/good/arrays/array_read.lat", "lattests/good/arrays/array_read.output", "lattests/good/arrays/array_read.err", 0);}
@Test void testArray_New() { testUtils.standardTestInputOutput("lattests/good/arrays/array_new.lat", "lattests/good/arrays/array_new.output", "lattests/good/arrays/array_new.err", 0);}
@Test void testArray_Length() { testUtils.standardTestInputOutput("lattests/good/arrays/array_length.lat", "lattests/good/arrays/array_length.output", "lattests/good/arrays/array_length.err", 0);}
}
