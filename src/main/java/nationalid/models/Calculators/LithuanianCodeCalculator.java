package nationalid.models.Calculators;

import java.util.ArrayList;

import nationalid.exceptions.LoggedException;

public class LithuanianCodeCalculator {
    private final static int[] firstMultiplicationCoefficients = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 0 };
    private final static int[] secondMultiplicationCoefficients = { 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 0 };

    private final static int expectedSize = 11;

    public static int CalculateCodeValue(long number) throws LoggedException {
        ArrayList<Integer> digitList = getDigitListFromNumber(number);
        // Size has to be 11
        while (digitList.size() < expectedSize) {
            digitList.add(0, 0);
        }
        int calculatedValue;
        try {
            calculatedValue = Calculation(digitList, firstMultiplicationCoefficients);
            if (calculatedValue < 10) {
                return calculatedValue;
            }

            calculatedValue = Calculation(digitList, secondMultiplicationCoefficients);

            return calculatedValue == 10 ? 0 : calculatedValue;
        } catch (Exception ex) {
            throw new LoggedException(ex);
        }

    }

    private static ArrayList<Integer> getDigitListFromNumber(long number) {
        ArrayList<Integer> digitList = new ArrayList<>();
        String numberAsString = String.valueOf(number);
        char[] digitCharArray = numberAsString.toCharArray();

        for (char c : digitCharArray) {
            // Converting each character to a seperate digit
            digitList.add(Character.getNumericValue(c));
        }

        return digitList;
    }

    private static int Calculation(ArrayList<Integer> digitList, int[] coefficient) throws Exception {
        int sum = 0;

        if (digitList.size() != coefficient.length) {
            throw new Exception("The number of digits does not match provided integer array length");
        }

        for (int i = 0; i < coefficient.length; i++) {
            sum += digitList.get(i) * coefficient[i];
        }

        return sum % 11;
    }

}
