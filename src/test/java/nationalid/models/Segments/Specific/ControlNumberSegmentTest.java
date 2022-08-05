package nationalid.models.Segments.Specific;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.interfaces.CodeCalculationStrategy;
import nationalid.models.NationalID;
import nationalid.models.Calculators.LithuanianCodeCalculator;

@RunWith(Theories.class)
public class ControlNumberSegmentTest {
    private CodeCalculationStrategy strategyForTesting = new LithuanianCodeCalculator();

    @DataPoints("goodValues")
    public static String[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("badValues")
    public static String[] badValues() {
        return new String[] { "-421454", "99999", "7012324099", "97012324099", "17012324099" };
    }

    @Theory
    public void testVerifyWithGoodIDs(@FromDataPoints("goodValues") String ID) {
        ControlNumberSegment segment = new ControlNumberSegment(new NationalID(ID), strategyForTesting);
        Assert.assertTrue(segment.Verify());
    }

    @Theory
    public void testVerifyWithBadIDs(@FromDataPoints("badValues") String ID) {
        ControlNumberSegment segment = new ControlNumberSegment(new NationalID(ID), strategyForTesting);
        Assert.assertTrue(!segment.Verify());
    }

}
