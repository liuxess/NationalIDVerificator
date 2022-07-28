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

@RunWith(Theories.class)
public class CodeCalculatorTest {

    @DataPoints("goodIDValues")
    public static long[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("goodIDValues")
    public static long[] calculableInputs() {
        return new long[] {
                00000000000L,
                10101010103L,
                1010101010L,
                1235678999L
        };
    }

    @Theory
    public void testVerifyWithGoodIDs(@FromDataPoints("goodIDValues") long ID) throws LoggedException {
        int calculation = LithuanianCodeCalculator.CalculateCodeValue(ID);
        Assert.assertTrue(getCodeFromID(ID) == calculation);
    }

    private int getCodeFromID(long ID) {
        return (int) (ID % 10);
    }

    @Test
    public void testCalculateCodeValue() throws LoggedException {
        int calculationForZero = LithuanianCodeCalculator.CalculateCodeValue(0);
        Assert.assertEquals(calculationForZero, 0);
    }
}
