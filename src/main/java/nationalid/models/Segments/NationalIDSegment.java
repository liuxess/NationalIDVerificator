package nationalid.models.Segments;

import nationalid.enums.NationalIDSegmentType;
import nationalid.helpers.NumberManager;
import nationalid.models.NationalID;

public abstract class NationalIDSegment {
    NationalID basedOnID;
    int segmentValue;
    NationalIDSegmentType type;
    Boolean isIncorrect;
    String problemMessage;

    public NationalIDSegment(NationalID ID, NationalIDSegmentType segmentType) {
        setIsIncorrect(false);
        setType(segmentType);
        setBasedOnID(ID);
        CalculateSegmentValue();
    }

    private void CalculateSegmentValue() {
        long ID = getBasedOnID().getID();
        long calculatedSegmentValue = -1; // Setting to not have an unitialized value
        switch (type) {
            // Take off the last 10 symbols
            case GENDER ->
                calculatedSegmentValue = RemoveDigits(ID, 10, 1);
            // Take off the last 4 symbols, and remove the first one
            case BIRTH_DATE ->
                calculatedSegmentValue = RemoveDigits(ID, 4, 6);
            // Take off the last symbol, and remove the 7 in front
            case RANDOM_DIGITS ->
                calculatedSegmentValue = RemoveDigits(ID, 1, 3);
            // Leave only the last symbol
            case CONTROL_DIGIT ->
                calculatedSegmentValue = RemoveDigits(ID, 0, 1);

            default ->
                setProblemMessage(String.format("Could not recognize the requested Segment type (%s)", type));
        }
        setSegmentValue((int) calculatedSegmentValue);
    }

    private long RemoveDigits(long number, int removeDigitsFromLeft, int amountOfDigitsToLeave) {
        return NumberManager.RemoveDigits(number, removeDigitsFromLeft, amountOfDigitsToLeave);
    }

    public abstract Boolean Verify();

    protected NationalID getBasedOnID() {
        return basedOnID;
    }

    private void setBasedOnID(NationalID basedOnID) {
        this.basedOnID = basedOnID;
    }

    protected int getSegmentValue() {
        return segmentValue;
    }

    private void setSegmentValue(int segmentValue) {
        this.segmentValue = segmentValue;
    }

    protected Boolean IsIncorrect() {
        return isIncorrect;
    }

    private void setIsIncorrect(Boolean isIncorrect) {
        this.isIncorrect = isIncorrect;
    }

    public String getProblemMessage() {
        return problemMessage;
    }

    protected void setProblemMessage(String problemMessage) {
        setIsIncorrect(true);
        this.problemMessage = problemMessage;
    }

    private void setType(NationalIDSegmentType type) {
        this.type = type;
    }

    public Boolean isOfType(NationalIDSegmentType type) {
        return this.type == type;
    }
}
