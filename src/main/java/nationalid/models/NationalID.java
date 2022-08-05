package nationalid.models;

import java.util.Optional;

import nationalid.SegmentedNationalID;
import nationalid.helpers.NumberManager;

/**
 * Storage and verification class for the National ID
 * To verify seperate segments, use SegmentedNationalID
 * 
 * @see nationalid.SegmentedNationalID
 */
public class NationalID {

    private String ID;
    private boolean incorrect;
    private String problemMessage;
    // TODO: maybe switch to a strategy interface
    private static final int expectedLength = 11;

    /**
     * @param ID to store and verify
     */
    public NationalID(String ID) {
        setIncorrect(false);
        setID(ID);
    }

    /**
     * @return a SegmentedNationalID based on this NationalID
     * @see nationalid.SegmentedNationalID
     */
    public SegmentedNationalID Segment() {
        return new SegmentedNationalID(this);
    }

    public String getID() {
        return ID;
    }
    // region: Getters & Setters

    /**
     * @return the problem that this ID has; Otherwise empty
     */
    public Optional<String> getProblemMessage() {
        Validate();

        if (getIncorrect())
            return Optional.of(problemMessage);

        return Optional.empty();
    }

    private boolean getIncorrect() {
        return incorrect;
    }

    private void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Will Set the NationalID as incorrect and store the problem
     * 
     * @param problem of this ID
     */
    private void setProblemMessage(String problem) {
        setIncorrect(true);
        problemMessage = problem;
    }

    private void setIncorrect(boolean incorrect) {
        this.incorrect = incorrect;
    }

    // endregion

    // region: Validation

    /**
     * @return whether or not this NationalID meets standarts to be parsed
     */
    public boolean Validate() {
        if (getIncorrect())
            return false;

        if (!VerifyIsPositiveNumber()) {
            return false;
        }

        return VerifyLength();
    }

    /**
     * @return whether the length of the ID is as expected
     */
    private boolean VerifyLength() {
        int lengthOfValue = getID().length();
        boolean isCorrectLength = (lengthOfValue == expectedLength);

        if (isCorrectLength)
            return true;

        setProblemMessage(
                String.format("The ID %s has length of %d, but should be %d", getID(), lengthOfValue, expectedLength));
        return false;
    }

    /**
     * @return that the ID is an acutal positive number
     */
    private boolean VerifyIsPositiveNumber() {
        Optional<Long> parsedValue = NumberManager.tryUnsignedParseFromString(ID);
        // if value present, means it can be parsed
        if (parsedValue.isEmpty()) {
            setProblemMessage(String.format("Could not parse a valid number from ID %s", ID));
        }

        return parsedValue.isPresent();
    }

    // endregion

}
