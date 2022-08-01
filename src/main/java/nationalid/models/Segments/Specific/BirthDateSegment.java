package nationalid.models.Segments.Specific;

import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import nationalid.enums.NationalIDSegmentType;
import nationalid.models.NationalID;
import nationalid.models.Segments.NationalIDSegmentBase;

public class BirthDateSegment extends NationalIDSegmentBase {

    final String dateFormatPattern = "yyMMdd";
    SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);

    public BirthDateSegment(NationalID ID) {
        super(ID, NationalIDSegmentType.BIRTH_DATE);
        Verify();
    }

    @Override
    public Boolean Verify() {
        // If there were problems in previous steps, no need to verify
        if (IsIncorrect())
            return false;

        // If the value is an exception, we can verify immediately
        if (isException())
            return true;

        if (!VerifyLength())
            return false;

        return VerifyIsValidDate();

    }

    private boolean isException() {
        return getSegmentValue() == 0; // If the segment values were all 0s for the date, we can ignore that
    }

    private boolean VerifyLength() {
        int segmentValue = getSegmentValue();
        // Verifying the length by basing it on the fact that
        // maximum would be 99 12 31
        if (segmentValue > 991232) {
            setProblemMessage(String.format("Birth Date is not recognized %d", segmentValue));
            return false;
        }

        return true;
    }

    private boolean VerifyIsValidDate() {
        try {
            strictToDate();
        } catch (Exception e) {
            setProblemMessage(String.format("Could not convert to a valid date: %s", e.getMessage()));
            return false;
        }

        return true;
    }

    private Date strictToDate() throws Exception {

        String stringExpression = getFullDateExpression();
        Date dateExpression = toDate();

        // Since date expression of 70/12/32 evaluates to 71/01/01, need to verify that
        // the date does not go out of the index and stays the same
        String stringExpressionAfterConversion = dateFormat.format(dateExpression);
        if (!stringExpressionAfterConversion.equals(stringExpression)) {
            throw new Exception("Converting to date resulted in a different value");
        }

        return dateExpression;
    }

    public Date toDate() throws ParseException {
        String stringExpression = getFullDateExpression();

        return dateFormat.parse(stringExpression);
    }

    public Date toDate(int Century) throws ParseException {

        String stringExpression = getFullDateExpression();
        stringExpression = String.format("%d%s", Century - 1, stringExpression);
        dateFormat.applyPattern("yy" + dateFormatPattern);
        return dateFormat.parse(stringExpression);
    }

    private String getFullDateExpression() {

        String stringExpression = String.valueOf(getSegmentValue());

        // Add leading 0s so there is 6 symbols
        stringExpression = "0".repeat(6 - stringExpression.length()).concat(stringExpression);
        return stringExpression;
    }

}
