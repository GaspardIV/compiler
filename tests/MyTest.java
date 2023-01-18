import com.github.stefanbirkner.systemlambda.SystemLambda;
import latte.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MyTest extends CompilerInputOutputErrTest {
//    core001.err
//    core001.lat
//    core001.output
//    core002.err
//    core002.lat
//    core002.output
//    core003.err
//    core003.lat
//    core003.output
//    core004.err
//    core004.lat
//    core004.output
//    core005.err
//    core005.lat
//    core005.output
//    core006.err
//    core006.lat
//    core006.output
//    core007.err
//    core007.lat
//    core007.output
//    core008.err
//    core008.lat
//    core008.output
//    core009.err
//    core009.lat
//    core009.output
//    core010.err
//    core010.lat
//    core010.output
//    core011.err
//    core011.lat
//    core011.output
//    core012.err
//    core012.lat
//    core012.output
//    core013.err
//    core013.lat
//    core013.output
//    core014.err
//    core014.lat
//    core014.output
//    core015.err
//    core015.lat
//    core015.output
//    core016.err
//    core016.lat
//    core016.output
//    core017.err
//    core017.lat
//    core017.output
//    core018.err
//    core018.input
//    core018.lat
//    core018.output
//    core019.err
//    core019.lat
//    core019.output
//    core020.err
//    core020.lat
//    core020.output
//    core021.err
//    core021.lat
//    core021.output
//    core022.err
//    core022.lat
//    core022.output
//    core023.err
//    core023.lat
//    core023.output
//    core024.err
//    core024.lat
//    core024.output
//    core025.err
//    core025.lat
//    core025.output
//    core026.err
//    core026.lat
//    core026.output
//    core027.err
//    core027.lat
//    core027.output
//    core028.err
//    core028.lat
//    core028.output
//    core030.err
//    core031.err
//    core031.lat
//    core031.output
//    core032.err
//    core032.lat
//    core032.output
//    core033.err
//    core033.lat
//    core033.output
//    core034.err
//    core034.lat
//    core034.output
//    core035.err
//    core035.lat
//    core035.output
//    constexprInIf.err
//    constexprInIf.output
//    constexprInWhile.err
//    constexprInWhile.lat
//    constexprInWhile.output
@Test void testCore001() {
    testUtils.standardTestInputOutput("lattests/good/my_tests/good/core001.lat", "lattests/good/my_tests/good/core001.output", "lattests/good/my_tests/good/core001.err", 0);
}



    @Test void testCore002() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core002.lat", "lattests/good/my_tests/good/core002.output", "lattests/good/my_tests/good/core002.err", 0);
    }

    @Test void testCore003() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core003.lat", "lattests/good/my_tests/good/core003.output", "lattests/good/my_tests/good/core003.err", 0);
    }

    @Test void testCore004() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core004.lat", "lattests/good/my_tests/good/core004.output", "lattests/good/my_tests/good/core004.err", 0);
    }

    @Test void testCore005() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core005.lat", "lattests/good/my_tests/good/core005.output", "lattests/good/my_tests/good/core005.err", 0);
    }

    @Test void testCore006() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core006.lat", "lattests/good/my_tests/good/core006.output", "lattests/good/my_tests/good/core006.err", 0);
    }

    @Test void testCore007() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core007.lat", "lattests/good/my_tests/good/core007.output", "lattests/good/my_tests/good/core007.err", 0);
    }

    @Test void testCore008() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core008.lat", "lattests/good/my_tests/good/core008.output", "lattests/good/my_tests/good/core008.err", 0);
    }

    @Test void testCore009() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core009.lat", "lattests/good/my_tests/good/core009.output", "lattests/good/my_tests/good/core009.err", 0);
    }

    @Test void testCore010() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core010.lat", "lattests/good/my_tests/good/core010.output", "lattests/good/my_tests/good/core010.err", 0);
    }

    @Test void testCore011() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core011.lat", "lattests/good/my_tests/good/core011.output", "lattests/good/my_tests/good/core011.err", 0);
    }

    @Test void testCore012() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core012.lat", "lattests/good/my_tests/good/core012.output", "lattests/good/my_tests/good/core012.err", 0);
    }

    @Test void testCore013() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core013.lat", "lattests/good/my_tests/good/core013.output", "lattests/good/my_tests/good/core013.err", 0);
    }

    @Test void testCore014() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core014.lat", "lattests/good/my_tests/good/core014.output", "lattests/good/my_tests/good/core014.err", 0);
    }

    @Test void testCore015() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core015.lat", "lattests/good/my_tests/good/core015.output", "lattests/good/my_tests/good/core015.err", 0);
    }

    @Test void testCore016() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core016.lat", "lattests/good/my_tests/good/core016.output", "lattests/good/my_tests/good/core016.err", 0);
    }

    @Test void testCore017() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core017.lat", "lattests/good/my_tests/good/core017.output", "lattests/good/my_tests/good/core017.err", 0);
    }

    @Test void testCore018() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core018.lat", "lattests/good/my_tests/good/core018.output", "lattests/good/my_tests/good/core018.err", 0);
    }

    @Test void testCore019() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core019.lat", "lattests/good/my_tests/good/core019.output", "lattests/good/my_tests/good/core019.err", 0);
    }

    @Test void testCore020() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core020.lat", "lattests/good/my_tests/good/core020.output", "lattests/good/my_tests/good/core020.err", 0);
    }

    @Test void testCore021() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core021.lat", "lattests/good/my_tests/good/core021.output", "lattests/good/my_tests/good/core021.err", 0);
    }

    @Test void testCore022() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core022.lat", "lattests/good/my_tests/good/core022.output", "lattests/good/my_tests/good/core022.err", 0);
    }

    @Test void testCore023() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core023.lat", "lattests/good/my_tests/good/core023.output", "lattests/good/my_tests/good/core023.err", 0);
    }

    @Test void testCore024() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core024.lat", "lattests/good/my_tests/good/core024.output", "lattests/good/my_tests/good/core024.err", 0);
    }

    @Test void testCore025() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core025.lat", "lattests/good/my_tests/good/core025.output", "lattests/good/my_tests/good/core025.err", 0);
    }

    @Test void testCore026() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core026.lat", "lattests/good/my_tests/good/core026.output", "lattests/good/my_tests/good/core026.err", 0);
    }

    @Test void testCore027() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core027.lat", "lattests/good/my_tests/good/core027.output", "lattests/good/my_tests/good/core027.err", 0);
    }

    @Test void testCore028() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core028.lat", "lattests/good/my_tests/good/core028.output", "lattests/good/my_tests/good/core028.err", 0);
    }

    @Test void testCore029() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core029.lat", "lattests/good/my_tests/good/core029.output", "lattests/good/my_tests/good/core029.err", 0);
    }

    @Test void testCore030() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core030.lat", "lattests/good/my_tests/good/core030.output", "lattests/good/my_tests/good/core030.err", 0);
    }

    @Test void testCore031() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core031.lat", "lattests/good/my_tests/good/core031.output", "lattests/good/my_tests/good/core031.err", 0);
    }

    @Test void testCore032() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core032.lat", "lattests/good/my_tests/good/core032.output", "lattests/good/my_tests/good/core032.err", 0);
    }

    @Test void testCore033() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core033.lat", "lattests/good/my_tests/good/core033.output", "lattests/good/my_tests/good/core033.err", 0);
    }

    @Test void testCore034() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core034.lat", "lattests/good/my_tests/good/core034.output", "lattests/good/my_tests/good/core034.err", 0);
    }

    @Test void testCore035() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/core035.lat", "lattests/good/my_tests/good/core035.output", "lattests/good/my_tests/good/core035.err", 0);
    }

//    constexprInIf.lat
    @Test void testCore037() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/constexprInIf.lat", "lattests/good/my_tests/good/constexprInIf.output", "lattests/good/my_tests/good/constexprInIf.err", 0);
    }

    @Test void testCore038() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/constexprInWhile.lat", "lattests/good/my_tests/good/constexprInWhile.output", "lattests/good/my_tests/good/constexprInWhile.err", 0);
    }

    @Test void testCore039() {
        testUtils.standardTestInputOutput("lattests/good/my_tests/good/xd.lat", "lattests/good/my_tests/good/xd.output", "lattests/good/my_tests/good/xd.err", 0);
    }
}
