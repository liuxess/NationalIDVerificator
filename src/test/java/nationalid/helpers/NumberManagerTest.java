package nationalid.helpers;

import org.junit.Assert;
import org.junit.Test;

public class NumberManagerTest {
    @Test
    public void testRemoveDigits() {
        long testingNumber = 123456789L;

        long lastThreeDigits = NumberManager.RemoveDigits(testingNumber, 0, 3);
        Assert.assertTrue(lastThreeDigits == 789);

        long middleThreeDigits = NumberManager.RemoveDigits(testingNumber, 3, 3);
        Assert.assertTrue(middleThreeDigits == 456);

        long firstThreeDigits = NumberManager.RemoveDigits(testingNumber, 6, 3);
        Assert.assertTrue(firstThreeDigits == 123);

        long allNineDigits = NumberManager.RemoveDigits(testingNumber, 0, 9);
        Assert.assertTrue(allNineDigits == testingNumber);

        allNineDigits = NumberManager.RemoveDigits(testingNumber, 0, 0);
        Assert.assertTrue(allNineDigits == testingNumber);

        long noDigits = NumberManager.RemoveDigits(testingNumber, 9, 1);
        Assert.assertTrue(noDigits == 0);

    }
}
