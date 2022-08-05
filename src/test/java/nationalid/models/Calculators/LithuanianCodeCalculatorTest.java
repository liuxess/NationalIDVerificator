package nationalid.models.Calculators;

import java.util.Optional;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.helpers.NumberManager;

@RunWith(Theories.class)
public class LithuanianCodeCalculatorTest {

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

    @Theory
    public void testVerifyWithGoodIDs(@FromDataPoints("goodIDValues") String ID) {
        LithuanianCodeCalculator codeCalculator = new LithuanianCodeCalculator();

        long IDvalue = NumberManager.tryUnsignedParseFromString(ID).get();

        Optional<Integer> calculation = codeCalculator.CalculateCodeValue(IDvalue);
        Assert.assertTrue(getCodeFromID(IDvalue) == calculation.get().intValue());
    }

    private int getCodeFromID(long ID) {
        return (int) (ID % 10);
    }

}
