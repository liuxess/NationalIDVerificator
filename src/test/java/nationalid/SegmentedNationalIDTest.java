package nationalid;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Calculators.GlobalCodeCalculator;
import nationalid.models.Calculators.LithuanianCodeCalculator;
import nationalid.models.Segments.NationalIDSegmentBase;
import nationalid.models.Segments.Specific.BirthDateSegment;
import nationalid.models.Segments.Specific.ControlNumberSegment;
import nationalid.models.Segments.Specific.GenderSegment;
import nationalid.models.Segments.Specific.RandomNumberSegment;

@RunWith(Theories.class)
public class SegmentedNationalIDTest {

    @BeforeClass
    public static void AssingGlobalCalculationInstace() {
        GlobalCodeCalculator.setGlobalInstance(new LithuanianCodeCalculator());
    }

    @DataPoints("goodValues")
    public static String[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("badValues")
    public static String[] badValues() {
        return new String[] { "-421454", "99999", "7012324099", "97012324099", "17012324099" };
    }

    @Theory
    public void testVerifyIntegrityWithGoodValues(@FromDataPoints("goodValues") String ID) {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(ID);
        Assert.assertTrue(segmentedNationalID.VerifyIntegrity());
        Assert.assertTrue(segmentedNationalID.getProblemList().isEmpty());
    }

    @Theory
    public void testVerifyIntegrityWithBadValues(@FromDataPoints("badValues") String ID) {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(ID);
        Assert.assertTrue(!segmentedNationalID.VerifyIntegrity());
    }

    @Test
    public void testGetID() {
        String TestingID = "39904261075";
        NationalID nationalID = new NationalID(TestingID);
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(TestingID);
        Assert.assertEquals(nationalID.getID(), segmentedNationalID.getNationalID().getID());
    }

    private record ProblematicCodes(String ID, int ProblemCount, String Explanation) {
    }

    @DataPoints("problematicCodes")
    public static ProblematicCodes[] getProblematicCodes() {
        return new ProblematicCodes[] {
                new ProblematicCodes("29504246872", 1, "Gender should be incorrect"),
                new ProblematicCodes("39504246874", 1, "Control Number should be incorrect"),
                new ProblematicCodes("39513246872", 1, "Birth Date should be incorrect"),
                new ProblematicCodes("39513246873", 2, "Birth Date and Control Number should be incorrect"),
                new ProblematicCodes("29504246873", 2, "Gender and Control Number should be incorrect"),
                new ProblematicCodes("89513246877", 2, "Gender and Birth Date should be incorrect"),
                new ProblematicCodes("89513246878", 3, "Gender, BirthDate and Control should be incorrect")
        };
    }

    @Theory
    public void testGettingProblemList(@FromDataPoints("problematicCodes") ProblematicCodes problematicCode) {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID(problematicCode.ID);
        List<String> problemList = segmentedNationalID.getProblemList();
        Assert.assertEquals(problematicCode.Explanation, problematicCode.ProblemCount, problemList.size());
    }

    @Theory
    public void testGetSegment() {
        SegmentedNationalID segmentedNationalID = new SegmentedNationalID("0");

        Optional<NationalIDSegmentBase> genderSegment = segmentedNationalID.getSegment(NationalIDSegmentType.GENDER);
        Optional<NationalIDSegmentBase> birthDateSegment = segmentedNationalID
                .getSegment(NationalIDSegmentType.BIRTH_DATE);
        Optional<NationalIDSegmentBase> randomSegment = segmentedNationalID
                .getSegment(NationalIDSegmentType.RANDOM_DIGITS);
        Optional<NationalIDSegmentBase> controlSegment = segmentedNationalID
                .getSegment(NationalIDSegmentType.CONTROL_DIGIT);

        Assert.assertTrue(genderSegment.isPresent());
        Assert.assertTrue(birthDateSegment.isPresent());
        Assert.assertTrue(randomSegment.isPresent());
        Assert.assertTrue(controlSegment.isPresent());

        Assert.assertTrue(genderSegment.get() instanceof GenderSegment);
        Assert.assertTrue(birthDateSegment.get() instanceof BirthDateSegment);
        Assert.assertTrue(randomSegment.get() instanceof RandomNumberSegment);
        Assert.assertTrue(controlSegment.get() instanceof ControlNumberSegment);
    }
}
