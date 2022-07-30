package nationalid.loggers;

import org.junit.Assert;
import org.junit.Test;

public class LogManagerTest {

    @Test
    public void testLogMessage() {
        LogManager testingLogManager = new LogManager(false);

        TestingLogger testingLogger = new TestingLogger();
        testingLogManager.addLogger(testingLogger);
        testingLogManager.LogMessage("Testing adding");
        Assert.assertTrue(testingLogger.IsMessageLogged());

        testingLogManager.LogMessage("Testing for Failure");
        Assert.assertFalse(testingLogger.IsMessageLogged());
    }
}
