package nationalid.exceptions;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nationalid.loggers.LogManager;
import nationalid.loggers.TestingLogger;

public class LoggedExceptionTest {

    public static TestingLogger logger;

    @Before
    public void assignLoggers() {
        logger = new TestingLogger();
        LogManager.getGlobalInstance().addLogger(logger);
    }

    @Test
    public void testConstruction() {
        new LoggedException("Conducting Tests");
        Assert.assertTrue(logger.IsMessageLogged());
    }
}
