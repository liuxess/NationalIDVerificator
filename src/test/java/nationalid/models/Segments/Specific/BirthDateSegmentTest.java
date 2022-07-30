package nationalid.models.Segments.Specific;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegment;

@RunWith(Theories.class)
public class BirthDateSegmentTest {

    @DataPoints("goodValues")
    public static long[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("badValues")
    public static long[] badValues() {
        return new long[] { -421454, 99999, 788788, 122199, 37012324099L };
    }

    @Theory
    public void testVerifyWithGoodIDs(@FromDataPoints("goodValues") long ID) {
        BirthDateSegment segment = new BirthDateSegment(new NationalID(ID));
        Assert.assertTrue(segment.Verify());
    }

    @Theory
    public void testVerifyWithBadIDs(@FromDataPoints("badValues") long ID) {
        BirthDateSegment segment = new BirthDateSegment(new NationalID(ID));
        Assert.assertTrue(!segment.Verify());
    }

    @Test
    public void testInheritence() {
        NationalIDSegment segment = new RandomNumberSegment(new NationalID(3555870596L));
        Assert.assertNotNull(segment);
    }

}
