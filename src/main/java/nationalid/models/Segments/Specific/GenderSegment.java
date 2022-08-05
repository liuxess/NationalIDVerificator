package nationalid.models.Segments.Specific;

import java.util.Optional;

import nationalid.enums.Gender;
import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

/**
 * Segment that represents the Gender digit for National ID
 * 
 * @see nationalid.models.Segments.NationalIDSegmentBase
 * @see nationalid.SegmentedNationalID
 */
public class GenderSegment extends NationalIDSegmentBase {

    /**
     * @param ID that this segment is based against
     */
    public GenderSegment(NationalID ID) {
        super(ID, NationalIDSegmentType.GENDER);
        Verify();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.models.Segments.NationalIDSegmentBase#Verify()
     */
    @Override
    public Boolean Verify() {
        // If there were problems in previous steps, no need to verify
        if (IsIncorrect())
            return false;

        return VerifyAllowedValues();
    }

    /**
     * @return the gender based on segment's value;
     *         Empty if the segment is
     *         incorrect
     */
    public Optional<Gender> getGender() {
        if (IsIncorrect())
            return Optional.empty();

        int segmentValue = getSegmentValue();
        return switch (segmentValue) {
            case 3, 5 -> Optional.of(Gender.MALE);
            case 4, 6 -> Optional.of(Gender.FEMALE);
            default -> Optional.empty();
        };
    }

    /**
     * @return Century that the person would be born in based on the ID; Empty if
     *         the segment is incorrect
     */
    public Optional<Integer> getCentury() {
        if (IsIncorrect()) {
            return Optional.empty();
        }

        int segmentValue = getSegmentValue();

        return switch (segmentValue) {
            case 3, 4 -> Optional.of(20);
            case 5, 6 -> Optional.of(21);
            default -> Optional.empty();
        };
    }

    /**
     * @return whether the value falls within allowed margins
     */
    private Boolean VerifyAllowedValues() {
        int segmentValue = getSegmentValue();
        Boolean correctRange = 2 < segmentValue && segmentValue < 7;

        if (!correctRange)
            setProblemMessage(String.format("The value for Gender (%d) is not within defined range", segmentValue));

        return correctRange;
    }

}
