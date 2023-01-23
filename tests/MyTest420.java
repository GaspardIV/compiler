import org.junit.jupiter.api.Test;

public class MyTest420 extends CompilerInputOutputErrTest {
    void test(int i) {
        String code_input = String.format("lattests/good/my_tests/my_test2/%d.lat", i);
        String code_output = String.format("lattests/good/my_tests/my_test2/%d.llcode", i);
        String output = String.format("lattests/good/my_tests/my_test2/%d.output", i);
        if (testUtils.fileExists(code_input)) {
            System.out.println(i);
            if (!testUtils.fileExists(code_output)) {
                testUtils.OkTestCodeQuality(code_input, code_output, output, true, true);
            } else {
                testUtils.OkTestCodeQuality(code_input, code_output, output, false, true);
            }
        }
    }

    @Test void test_01() {
        test(1);
    }

    @Test void test_02() {
        test(2);
    }

    @Test void test_03() {
        test(3);
    }

    @Test void test_04() {
        test(4);
    }

    @Test void test_05() {
        test(5);
    }

    @Test void test_06() {
        test(6);
    }

    @Test void test_07() {
        test(7);
    }

    @Test void test_08() {
        test(8);
    }

    @Test void test_09() {
        test(9);
    }

    @Test void test_10() {
        test(10);
    }

    @Test void test_11() {
        test(11);
    }

    @Test void test_12() {
        test(12);
    }

    @Test void test_13() {
        test(13);
    }

    @Test void test_14() {
        test(14);
    }

    @Test void test_15() {
        test(15);
    }

    @Test void test_16() {
        test(16);
    }

    @Test void test_17() {
        test(17);
    }

    @Test void test_18() {
        test(18);
    }

    @Test void test_19() {
        test(19);
    }

    @Test void test_20() {
        test(20);
    }

    @Test void test_21() {
        test(21);
    }

    @Test void test_22() {
        test(22);
    }

    @Test void test_23() {
        test(23);
    }

    @Test void test_24() {
        test(24);
    }

    @Test void test_25() {
        test(25);
    }

    @Test void test_26() {
        test(26);
    }

    @Test void test_27() {
        test(27);
    }

    @Test void test_28() {
        test(28);
    }

    @Test void test_29() {
        test(29);
    }

    @Test void test_30() {
        test(30);
    }
}
