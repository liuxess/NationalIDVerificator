package nationalid.exceptions;

import org.junit.Assert;
import org.junit.Test;

import nationalid.loggers.LogManager;
import nationalid.loggers.TestingLogger;

public class LoggedExceptionTest {

    @Test
    public void testConstructionWithMessage() {
        TestingLogger testingLogger = new TestingLogger();
        LogManager logManager = new LogManager(false, testingLogger);

        new LoggedException("Conducting Tests", logManager);
        Assert.assertTrue(testingLogger.IsMessageLogged());
    }

    @Test
    public void testConstructionWithThrowable() {
        TestingLogger testingLogger = new TestingLogger();
        LogManager logManager = new LogManager(false, testingLogger);
        Exception testingException = new Exception("Conducting Tests");
        new LoggedException(testingException, logManager);
        Assert.assertTrue(testingLogger.IsMessageLogged());
    }

    @Test
    public void testConstructionWithMessageAndThrowable() {
        TestingLogger testingLogger = new TestingLogger();
        LogManager logManager = new LogManager(false, testingLogger);
        Exception testingException = new Exception("Conducting Tests");
        new LoggedException("Conducting Test", testingException, logManager);
        Assert.assertTrue(testingLogger.IsMessageLogged());
    }

}
