package nationalid.loggers;

import org.junit.Assert;
import org.junit.Test;

public class FileLoggerTest {
    @Test
    public void testCompareInstance() {
        FileLogger firstInstance = new FileLogger();
        FileLogger secondInstance = new FileLogger();
        Assert.assertTrue(firstInstance.CompareInstance(secondInstance));
    }

    @Test
    public void testFailingCompareInstance() {
        FileLogger firstInstance = new FileLogger();
        TestingLogger secondInstance = new TestingLogger();
        Assert.assertFalse(firstInstance.CompareInstance(secondInstance));
    }

    @Test
    public void testLogMessage() {

    }
}
