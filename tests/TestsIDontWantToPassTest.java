import org.junit.jupiter.api.Test;

public class TestsIDontWantToPassTest extends CompilerInputOutputErrTest {
    @Test
    void testSemanticDeclarationInIf() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/declaration_in_if.lat", "", "lattests/bad/semantic/declaration_in_if.err", 1);
    }
    @Test void testSemanticDeclarationInWhile() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/declaration_in_while.lat", "", "lattests/bad/semantic/declaration_in_while.err", 1);
    }
    @Test void testSemanticVariableWithVoidType() {
        testUtils.standardTestInputOutput("lattests/bad/semantic/variable_with_void_type.lat", "", "lattests/bad/semantic/variable_with_void_type.err", 1);
    }
}
