package nationalid.models.Segments.Specific;

import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegment;

public class RandomNumberSegment extends NationalIDSegment {

    public RandomNumberSegment(NationalID ID) {
        super(ID, NationalIDSegmentType.RANDOM_DIGITS);
    }

    @Override
    public Boolean Verify() {
        return !IsIncorrect();
    }

}
