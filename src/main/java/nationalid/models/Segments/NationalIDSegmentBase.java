package nationalid.models.Segments;

import java.util.Optional;

import nationalid.enums.NationalIDSegmentType;
import nationalid.factories.NationalIDSegmentFactory;
import nationalid.models.NationalID;

/**
 * Base class for segments of NationalID
 * 
 * @see nationalid.SegmentedNationalID
 * @see nationalid.factories.NationalIDSegmentFactory
 */
public abstract class NationalIDSegmentBase {
    private NationalID basedOnID;
    private int segmentValue;
    private NationalIDSegmentType type;
    private Boolean isIncorrect;
    private String problemMessage;

    /**
     * Initializes segments based on ID and their type
     * 
     * @param ID          the current segment is based from
     * @param segmentType that this segment represents
     */
    protected NationalIDSegmentBase(NationalID ID, NationalIDSegmentType segmentType) {
        setIsIncorrect(false);
        setType(segmentType);
        setBasedOnID(ID);
        calculateSegmentValue();
    }

    /**
     * @return whether or not the segment value is correct for it's type
     */
    public abstract Boolean Verify();

    /**
     * calculates the segment value based on it's type
     * 
     * @see nationalid.factories.NationalIDSegmentFactory
     */
    private void calculateSegmentValue() {
        String segmentValue = NationalIDSegmentFactory.extractSegmentValue(getBasedOnID(), type);
        setSegmentValue(segmentValue);
    }

    /**
     * @param type of Segment to compare to
     * @return whether the current type is the same
     */
    public Boolean isOfType(NationalIDSegmentType type) {
        return this.type == type;
    }

    // region: Getters and Setters

    /**
     * @return gets value of the ID the segment is based on
     */
    protected NationalID getBasedOnID() {
        return basedOnID;
    }

    /**
     * @return current segment Value
     */
    public int getSegmentValue() {
        return segmentValue;
    }

    /**
     * @return Empty if no problems; Otherwise, the Problem as String
     */
    public Optional<String> getProblemMessage() {
        if (IsIncorrect())
            return Optional.of(problemMessage);

        return Optional.empty();
    }

    /**
     * @return whether the segment has been found to have problems
     */
    protected Boolean IsIncorrect() {
        return isIncorrect;
    }

    /**
     * Sets the segment as incorrect and saves it's problem
     * 
     * @param problemMessage that will be set
     */
    protected void setProblemMessage(String problemMessage) {
        setIsIncorrect(true);
        this.problemMessage = problemMessage;
    }

    /**
     * @param basedOnID value of the ID the segment is based on
     */
    private void setBasedOnID(NationalID basedOnID) {
        this.basedOnID = basedOnID;
    }

    /**
     * @param segmentValue which will be converted to a Number
     */
    private void setSegmentValue(String segmentValue) {
        int parsedSegmentValue = 0;
        try {
            parsedSegmentValue = Integer.parseUnsignedInt(segmentValue);
        } catch (NumberFormatException ex) {
            setProblemMessage(String.format("Could not parse a valid number from: %s", segmentValue));
        }
        this.segmentValue = parsedSegmentValue;
    }

    private void setIsIncorrect(Boolean isIncorrect) {
        this.isIncorrect = isIncorrect;
    }

    /**
     * @param type that this segment represents
     */
    private void setType(NationalIDSegmentType type) {
        this.type = type;
    }

    // endregion

}
