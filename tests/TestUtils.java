import com.github.stefanbirkner.systemlambda.SystemLambda;
import latte.Compiler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestUtils {
    private boolean shouldGenerateExpectedOutput;
    private final boolean skipCheckingOutput = true;
    protected final ByteArrayOutputStream outputStreamCaptor;

    public TestUtils(boolean shouldGenerateExpectedOutput, ByteArrayOutputStream outputStreamCaptor, ByteArrayOutputStream errStreamCaptor) {
        this.shouldGenerateExpectedOutput = shouldGenerateExpectedOutput;
        this.outputStreamCaptor = outputStreamCaptor;
        this.errStreamCaptor = errStreamCaptor;
    }

    protected final ByteArrayOutputStream errStreamCaptor;

    void standardTestInputOutput(boolean shouldGenerateExpectedOutput, String inputFileName, String outputFileName, String errOutputFileName, int exitCode) {
        boolean temp = this.shouldGenerateExpectedOutput;
        this.shouldGenerateExpectedOutput = shouldGenerateExpectedOutput;
        standardTestInputOutput(inputFileName, outputFileName, errOutputFileName, exitCode);
        this.shouldGenerateExpectedOutput = temp;
    }

    void standardTestInputOutput(String inputFileName, String outputFileName, String errOutputFileName, int exitCode) {
        System.out.println("Testing (/Users/kacperkonecki/IdeaProjects/codingame/compiler/" + inputFileName + ":0)");
        System.out.println("err (/Users/kacperkonecki/IdeaProjects/codingame/compiler/" + errOutputFileName + ":0)");
        System.out.println("output (/Users/kacperkonecki/IdeaProjects/codingame/compiler/" + outputFileName + ":0)");
        System.out.println("output (/Users/kacperkonecki/IdeaProjects/codingame/compiler/" + outputFileName.replace(".output", ".ll") + ":0)");

        String[] args = {inputFileName};

        int status;
        try {
            status = SystemLambda.catchSystemExit(() -> Compiler.main(args));
        } catch (AssertionError e) {
            status = 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        String expectedOutput;
        if (!skipCheckingOutput) {
            if (outputFileName != null && outputFileName.length() > 0) {
//                if (shouldGenerateExpectedOutput) {
//                    writeFileContent(outputFileName, outputStreamCaptor.toString());
//                }
                expectedOutput = getFileContent(outputFileName);
            } else {
                expectedOutput = "";
            }
        }

        String expectedErrOutput;
        if (errOutputFileName != null && errOutputFileName.length() > 0) {
            if (shouldGenerateExpectedOutput) {
                writeFileContent(errOutputFileName, errStreamCaptor.toString());
            }
            expectedErrOutput = getFileContent(errOutputFileName);
        } else {
            expectedErrOutput = "";
        }

        assertEquals(exitCode, status);
        assertEquals(expectedErrOutput, errStreamCaptor.toString());
        if (!skipCheckingOutput) {
            assertEquals(expectedOutput, outputStreamCaptor.toString());
        }

        assertFalse(shouldGenerateExpectedOutput, "This tests generates expected output. Please remove the flag.");
    }

    private void writeFileContent(String fileName, String content) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileContent(String fileName) {
        Path path = Paths.get(fileName);
        String str;
        try {
            str = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    public void setShouldGenerateExpectedOutput(boolean b) {
        shouldGenerateExpectedOutput = b;
    }
}
