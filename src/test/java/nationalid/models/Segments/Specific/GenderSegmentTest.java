package nationalid.models.Segments.Specific;

import java.util.Optional;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.enums.Gender;
import nationalid.models.NationalID;

@RunWith(Theories.class)
public class GenderSegmentTest {

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
        GenderSegment segment = new GenderSegment(new NationalID(ID));
        Assert.assertTrue(segment.Verify());
    }

    @Theory
    public void testVerifyWithBadIDs(@FromDataPoints("badValues") String ID) {
        GenderSegment segment = new GenderSegment(new NationalID(ID));
        Assert.assertTrue(!segment.Verify());
    }

    public static record IDsAndGenders(NationalID nationalID, Gender gender) {
    };

    @DataPoints("testingIDsAndGenders")
    public static IDsAndGenders[] testingIDsAndGenders() {
        return new IDsAndGenders[] {
                new IDsAndGenders(new NationalID("30000000000"), Gender.MALE),
                new IDsAndGenders(new NationalID("40000000000"), Gender.FEMALE),
                new IDsAndGenders(new NationalID("50000000000"), Gender.MALE),
                new IDsAndGenders(new NationalID("60000000000"), Gender.FEMALE),
        };
    }

    @Theory
    public void testGetGender(@FromDataPoints("testingIDsAndGenders") IDsAndGenders testingTarget) {
        GenderSegment segment = new GenderSegment(testingTarget.nationalID);
        Optional<Gender> parsedGender = segment.getGender();
        Assert.assertTrue(testingTarget.gender == parsedGender.get());
    }

}
