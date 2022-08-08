package nationalid.models.Calculators;

import nationalid.exceptions.LoggedException;
import nationalid.interfaces.CodeCalculationStrategy;

/**
 * This class hosts a static instance of globally accessible
 * CodeCalculationStrategy
 * 
 * @see nationalid.interfaces.CodeCalculationStrategy
 */
public class GlobalCodeCalculator {

    private static CodeCalculationStrategy globalInstace;
    private static Boolean globalInstanceIsAssigned = false;

    /**
     * Gets an assigned CodeCalculationStrategy. If it is not assigned, will lof an
     * Exception and apply a default strategy
     * 
     * @see nationalid.interfaces.CodeCalculationStrategy
     * @return
     */
    public static CodeCalculationStrategy getGlobalInstance() {
        if (!globalInstanceIsAssigned) {
            new LoggedException("Global Calculation Strategy has not been assigned");
            AssignDefaultStrategy();
        }
        return globalInstace;
    }

    /**
     * Resets the globalc instance
     */
    public static void Reset() {
        globalInstace = null;
        globalInstanceIsAssigned = false;
    }

    /**
     * Sets the global CodeCalculationStragy
     * 
     * @param strategy to use globally
     * @see nationalid.interfaces.CodeCalculationStrategy
     */
    public static void setGlobalInstance(CodeCalculationStrategy strategy) {
        globalInstanceIsAssigned = true;
        globalInstace = strategy;
    }

    /**
     * Methd for assigning a default strategy
     */
    private static void AssignDefaultStrategy() {
        globalInstace = new DefaultCodeCalculator();
    }

}
