package nationalid.models.Calculators;

import java.util.ArrayList;
import java.util.Optional;

import nationalid.helpers.NumberManager;
import nationalid.interfaces.CodeCalculationStrategy;

/**
 * A CodeCalculationStrategy implementationg for Lithuanian IDs
 */
public class LithuanianCodeCalculator implements CodeCalculationStrategy {
    private final static int[] firstMultiplicationCoefficients = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 0 };
    private final static int[] secondMultiplicationCoefficients = { 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 0 };
    private final static int expectedSize = 11;

    /*
     * (non-Javadoc)
     * 
     * @see nationalid.interfaces.CodeCalculationStrategy#CalculateCodeValue(long)
     */
    public Optional<Integer> CalculateCodeValue(long number) {
        ArrayList<Integer> digitList = NumberManager.getDigitListFromNumber(number);
        // Size has to be 11
        while (digitList.size() < expectedSize) {
            digitList.add(0, 0);
        }
        Optional<Integer> calculatedValue;
        calculatedValue = Calculation(digitList, firstMultiplicationCoefficients);

        // If it's empty, retun the empty
        if (calculatedValue.isEmpty())
            return calculatedValue;

        if (calculatedValue.get() < 10) {
            return calculatedValue;
        }

        calculatedValue = Calculation(digitList, secondMultiplicationCoefficients);

        // If it's empty, retun the empty
        if (calculatedValue.isEmpty())
            return calculatedValue;

        return calculatedValue.get() == 10 ? Optional.of(0) : calculatedValue;
    }

    /**
     * Calculates the required code
     * 
     * @param digitList   Array of digits from the ID
     * @param coefficient to be applied to the digits
     * @return Empty if there's a problem; Value once the calculations are made
     */
    private static Optional<Integer> Calculation(ArrayList<Integer> digitList, int[] coefficient) {
        if (digitList.size() != coefficient.length) {
            return Optional.empty();
        }

        int sum = 0;

        for (int i = 0; i < coefficient.length; i++) {
            sum += digitList.get(i) * coefficient[i];
        }

        return Optional.of(sum % 11);
    }

}
