package nationalid.exceptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nationalid.loggers.LogManager;
import nationalid.loggers.TestingLogger;

public class LoggedExceptionTest {

    @Test
    public void testConstruction() {
        TestingLogger testingLogger = new TestingLogger();
        LogManager logManager = new LogManager(false, testingLogger);
        new LoggedException("Conducting Tests", logManager);
        Assert.assertTrue(testingLogger.IsMessageLogged());
    }
}
