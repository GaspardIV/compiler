import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoodBasicTest extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
@Test void testEscaped_String() { testUtils.standardTestInputOutput("lattests/good/basic/escaped_string.lat", "lattests/good/basic/escaped_string.output", "lattests/good/basic/escaped_string.err", 0);}
@Test void testMod() { testUtils.standardTestInputOutput("lattests/good/basic/mod.lat", "lattests/good/basic/mod.output", "lattests/good/basic/mod.err", 0);}
@Test void testBool_Operations() { testUtils.standardTestInputOutput("lattests/good/basic/bool_operations.lat", "lattests/good/basic/bool_operations.output", "lattests/good/basic/bool_operations.err", 0);}
@Test void testFibonacci() { testUtils.standardTestInputOutput("lattests/good/basic/fibonacci.lat", "lattests/good/basic/fibonacci.output", "lattests/good/basic/fibonacci.err", 0);}
@Test void testWhile_True() { testUtils.standardTestInputOutput("lattests/good/basic/while_true.lat", "lattests/good/basic/while_true.output", "lattests/good/basic/while_true.err", 0);}
@Test void testCompare() { testUtils.standardTestInputOutput("lattests/good/basic/compare.lat", "lattests/good/basic/compare.output", "lattests/good/basic/compare.err", 0);}
@Test void testBool_Overoptymization() { testUtils.standardTestInputOutput("lattests/good/basic/bool_overoptymization.lat", "lattests/good/basic/bool_overoptymization.output", "lattests/good/basic/bool_overoptymization.err", 0);}
@Test void testFine_Ident() { testUtils.standardTestInputOutput("lattests/good/basic/fine_ident.lat", "lattests/good/basic/fine_ident.output", "lattests/good/basic/fine_ident.err", 0);}
@Test void testPrint_String() { testUtils.standardTestInputOutput("lattests/good/basic/print_string.lat", "lattests/good/basic/print_string.output", "lattests/good/basic/print_string.err", 0);}
@Test void testPrint_Int() { testUtils.standardTestInputOutput("lattests/good/basic/print_int.lat", "lattests/good/basic/print_int.output", "lattests/good/basic/print_int.err", 0);}
@Test void testPrint_Complicated_String() { testUtils.standardTestInputOutput("lattests/good/basic/print_complicated_string.lat", "lattests/good/basic/print_complicated_string.output", "lattests/good/basic/print_complicated_string.err", 0);}
@Test void testAdd() { testUtils.standardTestInputOutput("lattests/good/basic/add.lat", "lattests/good/basic/add.output", "lattests/good/basic/add.err", 0);}
@Test void testEmpty_Instructions() { testUtils.standardTestInputOutput("lattests/good/basic/empty_instructions.lat", "lattests/good/basic/empty_instructions.output", "lattests/good/basic/empty_instructions.err", 0);}
@Test void testEmpty_While() { testUtils.standardTestInputOutput("lattests/good/basic/empty_while.lat", "lattests/good/basic/empty_while.output", "lattests/good/basic/empty_while.err", 0);}
@Test void testVoid_Return() { testUtils.standardTestInputOutput("lattests/good/basic/void_return.lat", "lattests/good/basic/void_return.output", "lattests/good/basic/void_return.err", 0);}
@Test void testEmpty_If() { testUtils.standardTestInputOutput("lattests/good/basic/empty_if.lat", "lattests/good/basic/empty_if.output", "lattests/good/basic/empty_if.err", 0);}
@Test void testConcatenation() { testUtils.standardTestInputOutput("lattests/good/basic/concatenation.lat", "lattests/good/basic/concatenation.output", "lattests/good/basic/concatenation.err", 0);}
@Test void testNegation() { testUtils.standardTestInputOutput("lattests/good/basic/negation.lat", "lattests/good/basic/negation.output", "lattests/good/basic/negation.err", 0);}
@Test void testWhile_True2() { testUtils.standardTestInputOutput("lattests/good/basic/while_true2.lat", "lattests/good/basic/while_true2.output", "lattests/good/basic/while_true2.err", 0);}
@Test void testScopes() { testUtils.standardTestInputOutput("lattests/good/basic/scopes.lat", "lattests/good/basic/scopes.output", "lattests/good/basic/scopes.err", 0);}
}
