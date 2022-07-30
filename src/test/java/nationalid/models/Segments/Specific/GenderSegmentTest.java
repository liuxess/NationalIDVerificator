package nationalid.models.Segments.Specific;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.models.NationalID;

@RunWith(Theories.class)
public class GenderSegmentTest {

    @DataPoints("goodValues")
    public static long[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("badValues")
    public static long[] badValues() {
        return new long[] { -421454, 99999, 7012324099L, 97012324099L, 17012324099L };
    }

    @Theory
    public void testVerifyWithGoodIDs(@FromDataPoints("goodValues") long ID) {
        GenderSegment segment = new GenderSegment(new NationalID(ID));
        Assert.assertTrue(segment.Verify());
    }

    @Theory
    public void testVerifyWithBadIDs(@FromDataPoints("badValues") long ID) {
        GenderSegment segment = new GenderSegment(new NationalID(ID));
        Assert.assertTrue(!segment.Verify());
    }

}
