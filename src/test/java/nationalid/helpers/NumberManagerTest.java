package nationalid.helpers;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
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

    @Test
    public void testGetDigitListFromNumber() {
        int testingNumber = 1223487569;
        int[] testingDigits = new int[] { 1, 2, 2, 3, 4, 8, 7, 5, 6, 9 };
        ArrayList<Integer> testingNumberDigitsList = NumberManager.getDigitListFromNumber(testingNumber);

        Assert.assertTrue(testingDigits.length == testingNumberDigitsList.size());

        for (int i = 0; i < testingDigits.length; i++) {
            Assert.assertTrue(testingDigits[i] == testingNumberDigitsList.get(i).intValue());
        }
    }

    @DataPoints("goodLongsAsStrings")
    public static String[] goodLongsAsStrings() {
        return new String[] {
                "55625218512338",
                "0224",
                "887789324",
                "+7445721",
                "-45755672",
                "-555",
                "-0"
        };
    }

    @DataPoints("badLongsAsStrings")
    public static String[] badLongsAsStrings() {
        return new String[] {
                "5562521/8512338",
                "0224-9",
                "8877+89324",
                "a7445721",
                "/45755672",
                "'555'",
                "",
                "-"
        };
    }

    @Theory
    public void testTryParseFromString_withGoodValues(@FromDataPoints("goodLongsAsStrings") String valueToParse) {
        Optional<Long> parsedValue = NumberManager.tryParseFromString(valueToParse);

        Assert.assertFalse(parsedValue.isEmpty());
    }

    @Theory
    public void testTryParseFromString_withBadValues(@FromDataPoints("badLongsAsStrings") String valueToParse) {
        Optional<Long> parsedValue = NumberManager.tryParseFromString(valueToParse);

        Assert.assertTrue(parsedValue.isEmpty());
    }

    @DataPoints("goodPositiveLongsAsStrings")
    public static String[] goodPositiveLongsAsStrings() {
        return new String[] {
                "55625218512338",
                "0224",
                "887789324",
                "+7445721",
                "45755672",
                "555",
                "0"
        };
    }

    @DataPoints("badPositiveLongsAsStrings")
    public static String[] badPositiveLongsAsStrings() {
        return new String[] {
                "5562521/8512338",
                "0224-9",
                "8877+89324",
                "a7445721",
                "/45755672",
                "'555'",
                "-222",
                "-"
        };
    }

    @Theory
    public void testTryUnsigedParseFromString_withGoodValues(
            @FromDataPoints("goodPositiveLongsAsStrings") String valueToParse) {
        Optional<Long> parsedValue = NumberManager.tryUnsignedParseFromString(valueToParse);

        Assert.assertFalse(parsedValue.isEmpty());
    }

    @Theory
    public void testTryUnsignedParseFromString_withBadValues(
            @FromDataPoints("badPositiveLongsAsStrings") String valueToParse) {
        Optional<Long> parsedValue = NumberManager.tryUnsignedParseFromString(valueToParse);

        Assert.assertTrue(parsedValue.isEmpty());
    }
}
