package nationalid;

import org.junit.Test;

import java.util.List;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegment;
import nationalid.models.Segments.Specific.BirthDateSegment;
import nationalid.models.Segments.Specific.ControlNumberSegment;
import nationalid.models.Segments.Specific.GenderSegment;
import nationalid.models.Segments.Specific.RandomNumberSegment;

@RunWith(Theories.class)
public class SegmentedNationalIDTest {

    @DataPoints("goodValues")
    public static long[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("badValues")
    public static long[] badValues() {
        return new long[] { -421454, 99999, 7012324099L, 97012324099L, 17012324099L };
    }

    @Theory
    public void testVerifyIntegrityWithGoodValues(@FromDataPoints("goodValues") long ID) {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(ID);
        Assert.assertTrue(segmentedNationalID.VerifyIntegrity());
        Assert.assertTrue(segmentedNationalID.getProblemList().isEmpty());
    }

    @Theory
    public void testVerifyIntegrityWithBadValues(@FromDataPoints("badValues") long ID) {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(ID);
        Assert.assertTrue(!segmentedNationalID.VerifyIntegrity());
    }

    @Test
    public void testGetID() {
        long TestingID = 39904261075L;
        NationalID nationalID = new NationalID(TestingID);
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(TestingID);
        Assert.assertEquals(nationalID.getID(), segmentedNationalID.getID().getID());
    }

    private record ProblematicCodes(long ID, int ProblemCount, String Explanation) {
    }

    @DataPoints("problematicCodes")
    public static ProblematicCodes[] getProblematicCodes() {
        return new ProblematicCodes[] {
                new ProblematicCodes(29504246872L, 1, "Gender should be incorrect"),
                new ProblematicCodes(39504246874L, 1, "Control Number should be incorrect"),
                new ProblematicCodes(39513246872L, 1, "Birth Date should be incorrect"),
                new ProblematicCodes(39513246873L, 2, "Birth Date and Control Number should be incorrect"),
                new ProblematicCodes(29504246873L, 2, "Gender and Control Number should be incorrect"),
                new ProblematicCodes(89513246877L, 2, "Gender and Birth Date should be incorrect"),
                new ProblematicCodes(89513246878L, 3, "Gender, BirthDate and Control should be incorrect")
        };
    }

    @Theory
    public void testGettingProblemList(@FromDataPoints("problematicCodes") ProblematicCodes problematicCode) {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(problematicCode.ID);
        List<String> problemList = segmentedNationalID.getProblemList();
        Assert.assertEquals(problematicCode.Explanation, problematicCode.ProblemCount, problemList.size());
    }

    @Test
    public void testGetSegment() throws Exception {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(0);

        NationalIDSegment genderSegment = segmentedNationalID.getSegment(NationalIDSegmentType.GENDER);
        NationalIDSegment birthDateSegment = segmentedNationalID.getSegment(NationalIDSegmentType.BIRTH_DATE);
        NationalIDSegment randomSegment = segmentedNationalID.getSegment(NationalIDSegmentType.RANDOM_DIGITS);
        NationalIDSegment controlSegment = segmentedNationalID.getSegment(NationalIDSegmentType.CONTROL_DIGIT);

        Assert.assertTrue(genderSegment instanceof GenderSegment);
        Assert.assertTrue(birthDateSegment instanceof BirthDateSegment);
        Assert.assertTrue(randomSegment instanceof RandomNumberSegment);
        Assert.assertTrue(controlSegment instanceof ControlNumberSegment);
    }
}
