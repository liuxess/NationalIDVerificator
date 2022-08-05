package nationalid.factories;

import java.util.ArrayList;
import java.util.List;

import nationalid.enums.NationalIDSegmentType;
import nationalid.helpers.StringManager;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;
import nationalid.models.Segments.Specific.BirthDateSegment;
import nationalid.models.Segments.Specific.ControlNumberSegment;
import nationalid.models.Segments.Specific.GenderSegment;
import nationalid.models.Segments.Specific.RandomNumberSegment;

/*
 * Factory object to manage creation and calculation of various segments
 */
public class NationalIDSegmentFactory {
    // TODO: Should be replaced by a calculation Strategy to support various ids
    private static final int numberOfSegments = 4;

    // region: Public methods

    /**
     * Will initialize and retun a list of segments based on the provided ID
     * 
     * @param ID NationalID object to segment
     * @return List of segments
     * @see nationalid.models.NationalID
     * @see nationalid.models.Segments.NationalIDSegmentBase
     */
    public static List<NationalIDSegmentBase> GenerateListOfIDSegments(NationalID ID) {
        List<NationalIDSegmentBase> segmentList = new ArrayList<NationalIDSegmentBase>();

        segmentList.add(CreateSegment(ID, NationalIDSegmentType.GENDER));
        segmentList.add(CreateSegment(ID, NationalIDSegmentType.BIRTH_DATE));
        segmentList.add(CreateSegment(ID, NationalIDSegmentType.RANDOM_DIGITS));
        segmentList.add(CreateSegment(ID, NationalIDSegmentType.CONTROL_DIGIT));

        return segmentList;
    }

    /**
     * @return number of segments in current Strategy
     */
    public static int getNumberOfSegments() {
        return numberOfSegments;
    }

    /**
     * Creates a specific segment Type from the provided National ID
     * 
     * @param ID   NationalID the segment is based against
     * @param type Type of Segment to Generate
     * @return Requested type of ID
     * @see nationalid.models.NationalID
     * @see nationalid.models.Segments.NationalIDSegmentBase
     */
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

    /**
     * Extracts Segment Value from the National ID based on requested Type
     * 
     * @param ID   NationalID for the value to be extracted from
     * @param type of Segment to be extracted
     * @return value as String
     * @see nationalid.models.NationalID
     */
    public static String extractSegmentValue(NationalID ID, NationalIDSegmentType type) {
        return switch (type) {
            // Take off the last 10 symbols
            case GENDER -> StringManager.SafeSubstring(ID.getID(), 0, 1);
            // Take off the last 4 symbols, and remove the first one
            case BIRTH_DATE -> StringManager.SafeSubstring(ID.getID(), 1, 7);
            // Take off the last symbol, and remove the 7 in front
            case RANDOM_DIGITS -> StringManager.SafeSubstring(ID.getID(), 7, 10);
            // Leave only the last symbol
            case CONTROL_DIGIT -> StringManager.SafeSubstring(ID.getID(), 10, 11);
        };
    }

    // endregion

    // region: Segment generations
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
    // endregion

}
