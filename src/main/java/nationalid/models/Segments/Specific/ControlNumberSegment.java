package nationalid.models.Segments.Specific;

import java.util.Optional;

import nationalid.enums.NationalIDSegmentType;
import nationalid.exceptions.LoggedException;
import nationalid.helpers.NumberManager;
import nationalid.interfaces.CodeCalculationStrategy;
import nationalid.models.NationalID;
import nationalid.models.Calculators.GlobalCodeCalculator;
import nationalid.models.Segments.NationalIDSegmentBase;

/**
 * Segment that represents The Control Number for National ID
 * 
 * @see nationalid.models.Segments.NationalIDSegmentBase
 * @see nationalid.SegmentedNationalID
 */
public class ControlNumberSegment extends NationalIDSegmentBase {

    CodeCalculationStrategy currentStrategy;

    /**
     * @param ID                  that this segment is based against
     * @param calculationStrategy strategy to use for calculating the code in
     *                            verification
     * @see nationalid.interfaces.CodeCalculationStrategy
     */
    public ControlNumberSegment(NationalID ID, CodeCalculationStrategy calculationStrategy) {
        super(ID, NationalIDSegmentType.CONTROL_DIGIT);
        setStrategy(calculationStrategy);
        Verify();
    }

    /**
     * Creates the segment with Global instance of CodeCalculationStrategy
     * 
     * @param ID that this segment is based against
     * @see nationalid.interfaces.CodeCalculationStrategy
     */
    public ControlNumberSegment(NationalID ID) {
        this(ID, GlobalCodeCalculator.getGlobalInstance());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.models.Segments.NationalIDSegmentBase#Verify()
     */
    @Override
    public Boolean Verify() {
        if (IsIncorrect())
            return false;

        return VerifyCode();
    }

    /**
     * @return whether the code is the same as calculated by the strategy
     */
    private Boolean VerifyCode() {
        Optional<Integer> calculatedCode;
        Optional<Long> stringifiedID = NumberManager.tryParseFromString(getBasedOnID().getID());
        if (stringifiedID.isEmpty()) {
            setProblemMessage("The ID is not a number");
            return false;
        }

        calculatedCode = getStrategy().CalculateCodeValue(stringifiedID.get());

        if (calculatedCode.isEmpty()) {
            setProblemMessage("Could not calculate a value based on the ID");
            return false;
        }

        Boolean calculationMisMatch = calculatedCode.get().intValue() != getSegmentValue();

        if (calculationMisMatch) {
            String unformatedMessage = "The provided Control value of %d does not match the calculated value of %d";
            setProblemMessage(
                    String.format(unformatedMessage, getSegmentValue(), calculatedCode.get()));
        }

        return true;
    }

    /**
     * @param calculationStrategy to set strategy for calculations
     */
    private void setStrategy(CodeCalculationStrategy calculationStrategy) {
        this.currentStrategy = calculationStrategy;
    }

    /**
     * @return currently assigned strategy
     */
    private CodeCalculationStrategy getStrategy() {
        return currentStrategy;
    }
}
