import org.junit.jupiter.api.Test;

class BadTestsSemanticTest extends CompilerInputOutputErrTest {
    @Test void testSemanticBigNumber() {
        testUtils.standardTestInputOutput("lattests/semantic/big_number.lat", "", "lattests/semantic/big_number.err", 1);
    }
    @Test void testSemanticDeclarationInIf() {
        testUtils.standardTestInputOutput("lattests/semantic/declaration_in_if.lat", "", "lattests/semantic/declaration_in_if.err", 1);
    }
    @Test void testSemanticDeclarationInIfCondition() {
        testUtils.standardTestInputOutput("lattests/semantic/declaration_in_if_condition.lat", "", "lattests/semantic/declaration_in_if_condition.err", 1);
    }
    @Test void testSemanticDeclarationInWhile() {
        testUtils.standardTestInputOutput("lattests/semantic/declaration_in_while.lat", "", "lattests/semantic/declaration_in_while.err", 1);
    }
    @Test void testSemanticFunctionWithVoidArgument() {
        testUtils.standardTestInputOutput("lattests/semantic/function_with_void_argument.lat", "", "lattests/semantic/function_with_void_argument.err", 1);
    }
    @Test void testSemanticMainWithArgument() {
        testUtils.standardTestInputOutput("lattests/semantic/main_with_argument.lat", "", "lattests/semantic/main_with_argument.err", 1);
    }
    @Test void testSemanticMainWithVoidType() {
        testUtils.standardTestInputOutput("lattests/semantic/main_with_void_type.lat", "", "lattests/semantic/main_with_void_type.err", 1);
    }
    @Test void testSemanticNegation() {
        testUtils.standardTestInputOutput("lattests/semantic/negation.lat", "", "lattests/semantic/negation.err", 1);
    }
    @Test void testSemanticNoMain() {
        testUtils.standardTestInputOutput("lattests/semantic/no_main.lat", "", "lattests/semantic/no_main.err", 1);
    }
    @Test void testSemanticRedefinedFunctions() {
        testUtils.standardTestInputOutput("lattests/semantic/redefined_functions.lat", "", "lattests/semantic/redefined_functions.err", 1);
    }
    @Test void testSemanticRedefinitionOfPrintInt() {
        testUtils.standardTestInputOutput("lattests/semantic/redefinition_of_printInt.lat", "", "lattests/semantic/redefinition_of_printInt.err", 1);
    }
    @Test void testSemanticReturnVoidResult() {
        testUtils.standardTestInputOutput("lattests/semantic/return_void_result.lat", "", "lattests/semantic/return_void_result.err", 1);
    }
    @Test void testSemanticStringDecrementation() {
        testUtils.standardTestInputOutput("lattests/semantic/string_decrementation.lat", "", "lattests/semantic/string_decrementation.err", 1);
    }
    @Test void testSemanticStringIncrementation() {
        testUtils.standardTestInputOutput("lattests/semantic/string_incrementation.lat", "", "lattests/semantic/string_incrementation.err", 1);
    }
    @Test void testSemanticStringSub() {
        testUtils.standardTestInputOutput("lattests/semantic/string_sub.lat", "", "lattests/semantic/string_sub.err", 1);
    }
    @Test void testSemanticUndeclaredVariableAsInstruction() {
        testUtils.standardTestInputOutput("lattests/semantic/undeclared_variable_as_instruction.lat", "", "lattests/semantic/undeclared_variable_as_instruction.err", 1);
    }
    @Test void testSemanticVariableWithVoidType() {
        testUtils.standardTestInputOutput("lattests/semantic/variable_with_void_type.lat", "", "lattests/semantic/variable_with_void_type.err", 1);
    }
    @Test void testSemanticWhileTrueBadReturnType() {
        testUtils.standardTestInputOutput("lattests/semantic/while_true_bad_return_type.lat", "", "lattests/semantic/while_true_bad_return_type.err", 1);
    }
    @Test void testSemanticWhileTrue() {
        testUtils.standardTestInputOutput("lattests/semantic/while_true.lat", "", "lattests/semantic/while_true.out", 0);
    }
    @Test void testSemanticWhileTrue2() {
        testUtils.standardTestInputOutput("lattests/semantic/while_true2.lat", "", "lattests/semantic/while_true2.out", 0);
    }
}