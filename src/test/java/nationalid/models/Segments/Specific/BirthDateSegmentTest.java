package nationalid.models.Segments.Specific;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.junit.Test;

import nationalid.datapoints.NationalIDDataPoints;
import nationalid.models.NationalID;
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
        NationalIDSegmentBase segment = new BirthDateSegment(new NationalID("3555870596"));
        Assert.assertNotNull(segment);
    }

    public static record DatesWithoutCenturies(NationalID nationalID, LocalDate expectedDate) {
    };

    @DataPoints("datesWithoutCenturies")
    public static DatesWithoutCenturies[] datesWithoutCenturies() {
        return new DatesWithoutCenturies[] {
                new DatesWithoutCenturies(new NationalID("30003240000"), LocalDate.of(2000, 3, 24)),
                new DatesWithoutCenturies(new NationalID("57101290000"), LocalDate.of(2071, 1, 29)),
                new DatesWithoutCenturies(new NationalID("12112210000"), LocalDate.of(2021, 12, 21)),
                new DatesWithoutCenturies(new NationalID("01010101010"), LocalDate.of(2010, 10, 10)),
        };
    }

    @Theory
    public void testToDate(@FromDataPoints("datesWithoutCenturies") DatesWithoutCenturies dateWithoutCenturies) {
        BirthDateSegment segment = new BirthDateSegment(dateWithoutCenturies.nationalID);
        LocalDate parsedDate = segment.toDate().get();
        assertTrue(dateWithoutCenturies.expectedDate.isEqual(parsedDate));
    }

    public static record DatesWithCenturies(NationalID nationalID, int Century, LocalDate expectedDate) {
    };

    @DataPoints("datesWithCenturies")
    public static DatesWithCenturies[] datesWithCenturies() {
        return new DatesWithCenturies[] {
                new DatesWithCenturies(new NationalID("30003240000"), 21, LocalDate.of(2000, 3, 24)),
                new DatesWithCenturies(new NationalID("57101290000"), 20, LocalDate.of(1971, 1, 29)),
                new DatesWithCenturies(new NationalID("12112210000"), 21, LocalDate.of(2021, 12, 21)),
                new DatesWithCenturies(new NationalID("01010101010"), 20, LocalDate.of(1910, 10, 10)),
        };
    }

    @Theory
    public void testToDate_WtihCenturyProvided(
            @FromDataPoints("datesWithCenturies") DatesWithCenturies dateWithCentury) {
        BirthDateSegment segment = new BirthDateSegment(dateWithCentury.nationalID);
        LocalDate parsedDate = segment.toDate(dateWithCentury.Century).get();
        assertTrue(dateWithCentury.expectedDate.isEqual(parsedDate));

    }

}
