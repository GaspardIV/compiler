import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BadTestsSemanticTest extends CompilerInputOutputErrTest {
//    @BeforeEach void setUp() {
//        super.setUp();
//        testUtils.setShouldGenerateExpectedOutput(true);
//    }
//    @AfterEach void tearDown() {
//        testUtils.setShouldGenerateExpectedOutput(false);
//        super.tearDown();
//    }
    @Test void testSemanticBigNumber() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/big_number.lat", "", "lattests/bad/semantic/big_number.err", 1);
    }
//    @Test void testSemanticDeclarationInIf() {
//        testUtils.standardTestInputOutput("lattests/bad/semantic/declaration_in_if.lat", "", "lattests/bad/semantic/declaration_in_if.err", 1);
//    }
    @Test void testSemanticDeclarationInIfCondition() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/declaration_in_if_condition.lat", "", "lattests/bad/semantic/declaration_in_if_condition.err", 1);
    }
//    @Test void testSemanticDeclarationInWhile() {
//        testUtils.standardTestInputOutput("lattests/bad/semantic/declaration_in_while.lat", "", "lattests/bad/semantic/declaration_in_while.err", 1);
//    }
    @Test void testSemanticFunctionWithVoidArgument() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/function_with_void_argument.lat", "", "lattests/bad/semantic/function_with_void_argument.err", 1);
    }
    @Test void testSemanticMainWithArgument() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/main_with_argument.lat", "", "lattests/bad/semantic/main_with_argument.err", 1);
    }
    @Test void testSemanticMainWithVoidType() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/main_with_void_type.lat", "", "lattests/bad/semantic/main_with_void_type.err", 1);
    }
    @Test void testSemanticNegation() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/negation.lat", "", "lattests/bad/semantic/negation.err", 1);
    }
    @Test void testSemanticNoMain() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/no_main.lat", "", "lattests/bad/semantic/no_main.err", 1);
    }
    @Test void testSemanticRedefinedFunctions() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/redefined_functions.lat", "", "lattests/bad/semantic/redefined_functions.err", 1);
    }
    @Test void testSemanticRedefinitionOfPrintInt() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/redefinition_of_printInt.lat", "", "lattests/bad/semantic/redefinition_of_printInt.err", 1);
    }
    @Test void testSemanticReturnVoidResult() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/return_void_result.lat", "", "lattests/bad/semantic/return_void_result.err", 1);
    }
    @Test void testSemanticStringDecrementation() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/string_decrementation.lat", "", "lattests/bad/semantic/string_decrementation.err", 1);
    }
    @Test void testSemanticStringIncrementation() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/string_incrementation.lat", "", "lattests/bad/semantic/string_incrementation.err", 1);
    }
    @Test void testSemanticStringSub() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/string_sub.lat", "", "lattests/bad/semantic/string_sub.err", 1);
    }
    @Test void testSemanticUndeclaredVariableAsInstruction() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/undeclared_variable_as_instruction.lat", "", "lattests/bad/semantic/undeclared_variable_as_instruction.err", 1);
    }
    @Test void testSemanticVariableWithVoidType() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/variable_with_void_type.lat", "", "lattests/bad/semantic/variable_with_void_type.err", 1);
    }
    @Test void testSemanticWhileTrueBadReturnType() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/while_true_bad_return_type.lat", "", "lattests/bad/semantic/while_true_bad_return_type.err", 1);
    }
}