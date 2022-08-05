package nationalid.models.Segments.Specific;

import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

/**
 * Segment that represents the Random Digits from NationalID
 * 
 * @see nationalid.models.Segments.NationalIDSegmentBase
 * @see nationalid.SegmentedNationalID
 */
public class RandomNumberSegment extends NationalIDSegmentBase {

    /**
     * @param ID this segment is extracted from
     */
    public RandomNumberSegment(NationalID ID) {
        super(ID, NationalIDSegmentType.RANDOM_DIGITS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.models.Segments.NationalIDSegmentBase#Verify()
     */
    @Override
    public Boolean Verify() {
        return !IsIncorrect();
    }

}
