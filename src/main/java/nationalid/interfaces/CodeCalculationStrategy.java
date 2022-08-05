package nationalid.interfaces;

import java.util.Optional;

/**
 * This strategy is defining the algorythm
 * for calculating various codes for segments
 */
public interface CodeCalculationStrategy {

    /**
     * Calculates a code based on current strategy
     * and provided number
     * 
     * @param number the calculation will be based on
     * @return Emprty if a problem is encountered; otherweise the calculated value
     */
    public Optional<Integer> CalculateCodeValue(long number);

}
