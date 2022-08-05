package nationalid.models.Calculators;

import java.util.Optional;

import nationalid.interfaces.CodeCalculationStrategy;

/**
 * Serves as a simple default instance for global Calculator if strategy is not
 * assigned
 * 
 * @see nationalid.interfaces.CodeCalculationStrategy
 * 
 */
public class DefaultCodeCalculator implements CodeCalculationStrategy {

    @Override
    public Optional<Integer> CalculateCodeValue(long number) {
        return Optional.empty();
    }

}
