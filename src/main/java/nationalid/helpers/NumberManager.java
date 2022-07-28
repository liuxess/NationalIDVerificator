package nationalid.helpers;

public class NumberManager {
    public static long RemoveDigits(long number, int removeDigitsFromLeft, int amountOfDigitsToLeave) {
        if (removeDigitsFromLeft > 0) // Removing the last digits
            number /= (long) Math.pow(10, removeDigitsFromLeft);

        if (amountOfDigitsToLeave > 0) // If provided, removing the unneeded first digits
            number %= (long) Math.pow(10, amountOfDigitsToLeave);

        return number;
    }
}
