package nationalid.factories;

import java.util.ArrayList;
import java.util.List;

import nationalid.enums.NationalIDSegmentType;
import nationalid.helpers.NumberManager;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;
import nationalid.models.Segments.Specific.BirthDateSegment;
import nationalid.models.Segments.Specific.ControlNumberSegment;
import nationalid.models.Segments.Specific.GenderSegment;
import nationalid.models.Segments.Specific.RandomNumberSegment;

public class NationalIDSegmentFactory {
    static final int numberOfSegments = 4;

    public static List<NationalIDSegmentBase> GenerateListOfIDSegments(NationalID ID) {
        List<NationalIDSegmentBase> segmentList = new ArrayList<NationalIDSegmentBase>();

        segmentList.add(CreateSegment(ID, NationalIDSegmentType.GENDER));
        segmentList.add(CreateSegment(ID, NationalIDSegmentType.BIRTH_DATE));
        segmentList.add(CreateSegment(ID, NationalIDSegmentType.RANDOM_DIGITS));
        segmentList.add(CreateSegment(ID, NationalIDSegmentType.CONTROL_DIGIT));

        return segmentList;
    }

    public static int getNumberOfSegments() {
        return numberOfSegments;
    }

    public static NationalIDSegmentBase CreateSegment(NationalID ID, NationalIDSegmentType type) {
        return switch (type) {
            // Take off the last 10 symbols
            case GENDER -> createGenderSegment(ID);
            // Take off the last 4 symbols, and remove the first one
            case BIRTH_DATE -> createBirthDateSegment(ID);
            // Take off the last symbol, and remove the 7 in front
            case RANDOM_DIGITS -> createRandomNumberSegment(ID);
            // Leave only the last symbol
            case CONTROL_DIGIT -> createControlNumberSegment(ID);
        };
    }

    private static GenderSegment createGenderSegment(NationalID ID) {
        return new GenderSegment(ID);
    }

    private static BirthDateSegment createBirthDateSegment(NationalID ID) {
        return new BirthDateSegment(ID);
    }

    private static RandomNumberSegment createRandomNumberSegment(NationalID ID) {
        return new RandomNumberSegment(ID);
    }

    private static ControlNumberSegment createControlNumberSegment(NationalID ID) {
        return new ControlNumberSegment(ID);
    }

    public static long extractSegmentValue(NationalID ID, NationalIDSegmentType type) {
        return switch (type) {
            // Take off the last 10 symbols
            case GENDER -> NumberManager.RemoveDigits(ID.getID(), 10, 1);
            // Take off the last 4 symbols, and remove the first one
            case BIRTH_DATE -> NumberManager.RemoveDigits(ID.getID(), 4, 6);
            // Take off the last symbol, and remove the 7 in front
            case RANDOM_DIGITS -> NumberManager.RemoveDigits(ID.getID(), 1, 3);
            // Leave only the last symbol
            case CONTROL_DIGIT -> NumberManager.RemoveDigits(ID.getID(), 0, 1);
        };
    }
}
