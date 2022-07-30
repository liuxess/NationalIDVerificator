package nationalid.models.Segments;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;

@RunWith(Theories.class)
public class NationalIDSegmentTest {

    static class TestingInheritor extends NationalIDSegment {
        public TestingInheritor(long ID, NationalIDSegmentType segmentType) {
            super(new NationalID(ID), segmentType);
        }

        @Override
        public Boolean Verify() {
            return getBasedOnID().getID() > 0;
        }
    }

    @DataPoints("goodValues")
    public static ArrayList<NationalIDSegment> getGoodSegments() {
        ArrayList<NationalIDSegment> nationalIDSegmentArray = new ArrayList<>();
        long[] IDList = NationalIDDataPoints.getGoodNationalIDs();

        for (long ID : IDList) {
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.GENDER));
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.BIRTH_DATE));
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.RANDOM_DIGITS));
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.CONTROL_DIGIT));
        }

        return nationalIDSegmentArray;
    }

    @Theory
    public void testGetBasedOnID(@FromDataPoints("goodValues") NationalIDSegment segment) {
        NationalID nationalID = segment.getBasedOnID();
        Assert.assertTrue(nationalID.getID() > 0);
    }

    @Test
    public void testGetProblemMessage() {

    }

    @Theory
    public void testIsOfType(@FromDataPoints("goodValues") NationalIDSegment segment) {

    }

    @Test
    public void testSetProblemMessage() {

    }
}
