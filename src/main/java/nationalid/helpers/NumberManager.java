package nationalid.helpers;

import java.util.ArrayList;
import java.util.Optional;

/*
 * NumberManager class contains static functions that wrap
 * common provesses for number based algorythms used in the solution
 */
public class NumberManager {
    /**
     * Will conduct mathemathical operations to strip the required digits out of the
     * provided number
     * 
     * @param number                number to take the digits out from
     * @param removeDigitsFromLeft  How many digits to remove from the left side of
     *                              the number
     * @param amountOfDigitsToLeave How many digits to leave after the left side is
     *                              removed
     * @return a stripped number
     * 
     */
    public static long RemoveDigits(long number, int removeDigitsFromLeft, int amountOfDigitsToLeave) {
        if (removeDigitsFromLeft > 0) // Removing the last digits
            number /= (long) Math.pow(10, removeDigitsFromLeft);

        if (amountOfDigitsToLeave > 0) // If provided, removing the unneeded first digits
            number %= (long) Math.pow(10, amountOfDigitsToLeave);

        return number;
    }

    /**
     * Will attempt to parse a number out of String
     * 
     * @param value String to be parsed
     * @return Will return empty if an exception is encountered; Will return value
     *         if parsing is a success
     */
    public static Optional<Long> tryParseFromString(String value) {
        Long parsedSegmentValue = 0L;
        try {
            parsedSegmentValue = Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
        return Optional.of(parsedSegmentValue);
    }

    /**
     * Will attempt to parse a non-negative number out of String
     * 
     * @param value String to be parsed
     * @return Will return empty if an exception is encountered; Will return value
     *         if parsing is a success
     */
    public static Optional<Long> tryUnsignedParseFromString(String value) {
        Long parsedSegmentValue = 0L;
        try {
            parsedSegmentValue = Long.parseUnsignedLong(value);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }
        return Optional.of(parsedSegmentValue);
    }

    /**
     * Parses a number into it's digit List.
     * 
     * @param number to be parsed
     * @return List of Digits
     */
    public static ArrayList<Integer> getDigitListFromNumber(long number) {
        ArrayList<Integer> digitList = new ArrayList<>();
        String numberAsString = String.valueOf(number);
        char[] digitCharArray = numberAsString.toCharArray();

        for (char c : digitCharArray) {
            // Converting each character to a seperate digit
            digitList.add(Character.getNumericValue(c));
        }

        return digitList;
    }
}
