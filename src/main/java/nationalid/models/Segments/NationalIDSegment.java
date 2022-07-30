package nationalid.models.Segments;

import nationalid.enums.NationalIDSegmentType;
import nationalid.factories.NationalIDSegmentFactory;
import nationalid.models.NationalID;

public abstract class NationalIDSegment {
    private NationalID basedOnID;
    private int segmentValue;
    private NationalIDSegmentType type;
    private Boolean isIncorrect;
    private String problemMessage;

    public NationalIDSegment(NationalID ID, NationalIDSegmentType segmentType) {
        setIsIncorrect(false);
        setType(segmentType);
        setBasedOnID(ID);
        calculateSegmentValue();
    }

    private void calculateSegmentValue() {
        int segmentValue = (int) NationalIDSegmentFactory.extractSegmentValue(getBasedOnID(), type);
        setSegmentValue(segmentValue);
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
