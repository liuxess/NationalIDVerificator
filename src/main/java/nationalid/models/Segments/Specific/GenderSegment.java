package nationalid.models.Segments.Specific;

import javax.sound.sampled.BooleanControl;

import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

public class GenderSegment extends NationalIDSegmentBase {

    public GenderSegment(NationalID ID) {
        super(ID, NationalIDSegmentType.GENDER);
    }

    @Override
    public Boolean Verify() {
        // If there were problems in previous steps, no need to verify
        if (IsIncorrect())
            return false;

        return VerifyAllowedValues();
    }

    private Boolean VerifyAllowedValues() {
        int segmentValue = getSegmentValue();
        Boolean correctRange = 2 < segmentValue && segmentValue < 7;

        if (!correctRange)
            setProblemMessage(String.format("The value for Gender (%d) is not within defined range", segmentValue));

        return correctRange;
    }

    public Boolean IsMale() {
        if (IsIncorrect())
            return null;

        // Could simplify by getSegmentValue() % 2 == 1, but this should be faster
        int segmentValue = getSegmentValue();
        return segmentValue == 3 || segmentValue == 5;
    }
}
