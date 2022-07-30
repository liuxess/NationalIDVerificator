package nationalid.models.Segments.Specific;

import nationalid.enums.NationalIDSegmentType;
import nationalid.exceptions.LoggedException;
import nationalid.models.NationalID;
import nationalid.models.Calculators.LithuanianCodeCalculator;
import nationalid.models.Segments.NationalIDSegment;

public class ControlNumberSegment extends NationalIDSegment {

    public ControlNumberSegment(NationalID ID) {
        super(ID, NationalIDSegmentType.CONTROL_DIGIT);
        Verify();
    }

    @Override
    public Boolean Verify() {
        if (IsIncorrect())
            return false;

        return VerifyCode();
    }

    private Boolean VerifyCode() {
        int calculatedCode;
        try {
            calculatedCode = LithuanianCodeCalculator.CalculateCodeValue(getBasedOnID().getID());
        } catch (LoggedException ex) {
            return false;
        }
        Boolean calculationMisMatch = calculatedCode != getSegmentValue();

        if (calculationMisMatch) {
            String unformatedMessage = "The provided Control value of %d does not match the calculated value of %d";
            setProblemMessage(
                    String.format(unformatedMessage, getSegmentValue(), calculatedCode));
        }

        return true;
    }
}
