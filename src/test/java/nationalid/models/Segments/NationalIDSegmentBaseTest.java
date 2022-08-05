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
public class NationalIDSegmentBaseTest {

    static class TestingInheritor extends NationalIDSegmentBase {
        public TestingInheritor(String ID, NationalIDSegmentType segmentType) {
            super(new NationalID(ID), segmentType);
        }

        @Override
        public Boolean Verify() {
            return !getBasedOnID().getID().isBlank();
        }

        public void setTestingProblemMessage(String message) {
            setProblemMessage(message);
        }
    }

    @DataPoints("goodValues")
    public static ArrayList<NationalIDSegmentBase> getGoodSegments() {
        ArrayList<NationalIDSegmentBase> nationalIDSegmentArray = new ArrayList<>();
        String[] IDList = NationalIDDataPoints.getGoodNationalIDs();

        for (String ID : IDList) {
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.GENDER));
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.BIRTH_DATE));
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.RANDOM_DIGITS));
            nationalIDSegmentArray.add(new TestingInheritor(ID, NationalIDSegmentType.CONTROL_DIGIT));
        }

        return nationalIDSegmentArray;
    }

    @Theory
    public void testGetBasedOnID(@FromDataPoints("goodValues") NationalIDSegmentBase segment) {
        NationalID nationalID = segment.getBasedOnID();
        Assert.assertFalse(nationalID.getID().isBlank());
    }

    @Test
    public void testGetSetProblemMessage() {
        TestingInheritor testingSegment = new TestingInheritor("000", NationalIDSegmentType.RANDOM_DIGITS);
        String problemMessage = "Testing message";
        testingSegment.setTestingProblemMessage(problemMessage);
        Assert.assertTrue(problemMessage.equals(testingSegment.getProblemMessage().get()));
    }

}