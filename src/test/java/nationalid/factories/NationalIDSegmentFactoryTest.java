package nationalid.factories;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
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

@RunWith(Theories.class)
public class NationalIDSegmentFactoryTest {

    public static record IDsSegmentsValues(NationalID ID, NationalIDSegmentType type, int expectedValue) {
    };

    @BeforeClass
    public static void InstantiateGlobalInstances() {
        GlobalCodeCalculator.setGlobalInstance(new LithuanianCodeCalculator());
    }

    @DataPoints("goodIDValues")
    public static String[] goodValues() {
        return NationalIDDataPoints.getGoodNationalIDs();
    }

    @DataPoints("goodIDSegmentsValues")
    public static IDsSegmentsValues[] goodIDSegmentsValues() {
        return new IDsSegmentsValues[] {
                new IDsSegmentsValues(new NationalID("37306159113"), NationalIDSegmentType.GENDER, 3),
                new IDsSegmentsValues(new NationalID("40003179298"), NationalIDSegmentType.BIRTH_DATE, 317),
                new IDsSegmentsValues(new NationalID("35906018745"), NationalIDSegmentType.RANDOM_DIGITS, 874),
                new IDsSegmentsValues(new NationalID("51806239101"), NationalIDSegmentType.CONTROL_DIGIT, 1),
        };
    }

    @Theory
    public void testCreateSegment(@FromDataPoints("goodIDSegmentsValues") IDsSegmentsValues IDsAndSegments) {
        NationalIDSegmentBase segment = NationalIDSegmentFactory.CreateSegment(IDsAndSegments.ID, IDsAndSegments.type);
        Assert.assertTrue(segment.isOfType(IDsAndSegments.type));
    }

    @Theory
    public void testGenerateListOfIDSegments(@FromDataPoints("goodIDValues") String ID) {
        List<NationalIDSegmentBase> segmentList = NationalIDSegmentFactory.GenerateListOfIDSegments(new NationalID(ID));
        Assert.assertTrue(segmentList.size() == NationalIDSegmentFactory.getNumberOfSegments());

    }

    @Theory
    public void testExtractSegmentValue(@FromDataPoints("goodIDSegmentsValues") IDsSegmentsValues IDsAndSegments) {
        String extractedValue = NationalIDSegmentFactory.extractSegmentValue(IDsAndSegments.ID, IDsAndSegments.type);
        Assert.assertEquals(IDsAndSegments.expectedValue, Long.parseLong(extractedValue));
    }

}
