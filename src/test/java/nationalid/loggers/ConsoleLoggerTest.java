package nationalid.loggers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConsoleLoggerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testCompareInstance() {
        ConsoleLogger firstInstance = new ConsoleLogger();
        ConsoleLogger secondInstance = new ConsoleLogger();
        Assert.assertTrue(firstInstance.CompareInstance(secondInstance));
    }

    @Test
    public void testFailingCompareInstance() {
        ConsoleLogger firstInstance = new ConsoleLogger();
        TestingLogger secondInstance = new TestingLogger();
        Assert.assertFalse(firstInstance.CompareInstance(secondInstance));
    }

    @Test
    public void testLogMessage() throws Exception {
        String message = "Testing Message";
        ConsoleLogger logger = new ConsoleLogger();

        logger.LogMessage(message);

        Assert.assertEquals(message, errContent.toString());

    }
}
