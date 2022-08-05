package nationalid.models.Segments.Specific;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.interfaces.CodeCalculationStrategy;
import nationalid.models.NationalID;
import nationalid.models.Calculators.LithuanianCodeCalculator;
import nationalid.models.Segments.NationalIDSegmentBase;

@RunWith(Theories.class)
public class BirthDateSegmentTest {

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
        BirthDateSegment segment = new BirthDateSegment(new NationalID(ID));
        Assert.assertTrue(segment.Verify());
    }

    @Theory
    public void testVerifyWithBadIDs(@FromDataPoints("badValues") String ID) {
        BirthDateSegment segment = new BirthDateSegment(new NationalID(ID));
        Assert.assertTrue(!segment.Verify());
    }

    @Test
    public void testInheritence() {
        NationalIDSegmentBase segment = new RandomNumberSegment(new NationalID("3555870596"));
        Assert.assertNotNull(segment);
    }

}
