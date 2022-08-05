package nationalid.helpers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class StringManagerTest {

    public static record StringAndSubstringValues(String target, int startIndex, int endIndex, String expectedResult) {
    }

    @DataPoints("testingValuesForSubstring")
    public static StringAndSubstringValues[] testingValuesForSubstring() {
        return new StringAndSubstringValues[] {
                new StringAndSubstringValues("abcdefg", 0, 2, "ab"),
                new StringAndSubstringValues("abcdefg", 1, 7, "bcdefg"),
                new StringAndSubstringValues("abcdefg", 1, 1, ""),
                new StringAndSubstringValues("abcdefg", 5, 11, "fg"),
                new StringAndSubstringValues("abcdefg", 11, 3, ""),
                new StringAndSubstringValues("abcdefg", 0, 20, "abcdefg"),
        };
    }

    @Theory
    public void testSafeSubstring(@FromDataPoints("testingValuesForSubstring") StringAndSubstringValues testedValue) {
        String substringedValue = StringManager.SafeSubstring(testedValue.target, testedValue.startIndex,
                testedValue.endIndex);

        Assert.assertEquals(testedValue.expectedResult, substringedValue);
    }
}
