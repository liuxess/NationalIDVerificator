package nationalid.models.Segments.Specific;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

/**
 * Segment that represents BirthDate on NationalID
 * 
 * @see nationalid.models.Segments.NationalIDSegmentBase
 * @see nationalid.SegmentedNationalID
 */
public class BirthDateSegment extends NationalIDSegmentBase {

    // TODO: Replace by a strategy?
    final String dateFormatPattern = "yyMMdd";
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(dateFormatPattern);

    /**
     * @param ID that this Segment is based against
     */
    public BirthDateSegment(NationalID ID) {
        super(ID, NationalIDSegmentType.BIRTH_DATE);
        Verify();
    }

    // region: public methods

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

        // If the value is an exception, we can verify immediately
        if (isException())
            return true;

        if (!VerifyValue())
            return false;

        return VerifyIsValidDate();

    }

    /**
     * Tries to parse the value into LocaDate. Century should not be trusted
     * 
     * @return Empty if an error was encountered; Otherwise - the parsed date
     */
    public Optional<LocalDate> toDate() {
        String stringExpression = getFullDateExpression();
        return ToDate(stringExpression, dateFormat);
    }

    /**
     * Tries to parse the value into LocaDate.
     * 
     * @param Century What Century will be assigned to the parsed date
     * @return Empty if an error was encountered; Otherwise - the parsed date
     */
    public Optional<LocalDate> toDate(int Century) {
        String stringExpression = getFullDateExpression();
        stringExpression = String.format("%d%s", Century - 1, stringExpression);
        DateTimeFormatter fullDateFormat = DateTimeFormatter.ofPattern("yy" + dateFormatPattern);
        return ToDate(stringExpression, fullDateFormat);
    }

    /**
     * @return whether or not the current value qualifies as an exception
     */
    public boolean isException() {
        return getSegmentValue() == 0; // If the segment values were all 0s for the date, we can ignore that
    }

    // endregion

    // region: private methods

    /**
     * Verifies that the segment Value does not exceed maximum possible value
     * 
     * @return true if there's no problem
     */
    private boolean VerifyValue() {
        int segmentValue = getSegmentValue();
        // Verifying the length by basing it on the fact that
        // maximum would be 99 12 31
        if (segmentValue > 991232) {
            setProblemMessage(String.format("Birth Date is not recognized %d", segmentValue));
            return false;
        }

        return true;
    }

    /**
     * 
     * @return whether the current value is a valid date
     */
    private boolean VerifyIsValidDate() {
        return ConvertAndCompare();
    }

    /**
     * Converts value to LocalDate and back to String to compare.
     * Since DateTime works on timestamp basis, day 32 just means day 1 of next
     * month
     * 
     * @return whether converting back and forth results in the same dates and
     *         values
     */
    private boolean ConvertAndCompare() {

        String stringExpression = getFullDateExpression();
        Optional<LocalDate> dateExpression = toDate();

        if (dateExpression.isEmpty()) {
            setProblemMessage(String.format("Could not parse into a valid Date value %s", stringExpression));
            return false;
        }

        // Since date expression of 70/12/32 evaluates to 71/01/01, need to verify that
        // the date does not go out of the index and stays the same
        String stringExpressionAfterConversion = dateFormat.format(dateExpression.get());

        if (!stringExpressionAfterConversion.equals(stringExpression)) {
            setProblemMessage(String.format("Converting to date resulted in a different value: From %s got %s",
                    stringExpression, stringExpressionAfterConversion));
            return false;
        }

        return true;
    }

    /**
     * @return string expressions of the value that always has at least 6 symbols
     */
    private String getFullDateExpression() {
        String stringExpression = String.valueOf(getSegmentValue());

        // Add leading 0s so there is 6 symbols
        stringExpression = "0".repeat(6 - stringExpression.length()).concat(stringExpression);
        return stringExpression;
    }

    /**
     * Attempts to convert To date and processes Exceptions
     * 
     * @param stringExpression expressionToConvert
     * @param dateFormatter    format for the conversion
     * @return Empty if there was a failure; Value if parsed correctly
     */
    private static Optional<LocalDate> ToDate(String stringExpression, DateTimeFormatter dateFormatter) {
        try {
            LocalDate valueAsDate = LocalDate.parse(stringExpression, dateFormatter);
            return Optional.of(valueAsDate);
        } catch (DateTimeParseException ex) {
            return Optional.empty();
        }
    }
    // endregion
}
