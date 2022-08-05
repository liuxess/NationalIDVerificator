package nationalid.models.Calculators;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.exceptions.LoggedException;

public class CodeCalculatorTest {

    @DataPoints("goodIDValues")
    public static String[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("goodIDValues")
    public static String[] calculableInputs() {
        return new String[] {
                "00000000000",
                "10101010103",
                "1010101010",
                "1235678999"
        };
    }

    /*
     * @Theory
     * public void testVerifyWithGoodIDs(@FromDataPoints("goodIDValues") String ID)
     * {
     * int calculation = LithuanianCodeCalculator.CalculateCodeValue(ID);
     * Assert.assertTrue(getCodeFromID(ID) == calculation);
     * }
     */

    private int getCodeFromID(long ID) {
        return (int) (ID % 10);
    }
    /*
     * @Test
     * public void testCalculateCodeValue() throws LoggedException {
     * int calculationForZero = LithuanianCodeCalculator.CalculateCodeValue(0);
     * Assert.assertEquals(0, calculationForZero);
     * }
     */
}
