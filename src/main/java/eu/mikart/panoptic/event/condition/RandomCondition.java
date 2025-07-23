package eu.mikart.panoptic.event.condition;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.event.Event;

import eu.mikart.panoptic.event.Condition;

/**
 * A condition that evaluates to true based on a random probability.
 * This condition is useful for creating random events, loot drops, or chance-based mechanics.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public class RandomCondition implements Condition {
    
    private final double probability;
    
    /**
     * Creates a new RandomCondition with the specified probability.
     * 
     * @param probability The probability of this condition evaluating to true (0.0 to 1.0)
     * @throws IllegalArgumentException if probability is not between 0.0 and 1.0
     */
    public RandomCondition(double probability) {
        if (probability < 0.0 || probability > 1.0) {
            throw new IllegalArgumentException("Probability must be between 0.0 and 1.0, got: " + probability);
        }
        this.probability = probability;
    }
    
    /**
     * Evaluates this condition based on random chance.
     * 
     * @param event The event to evaluate (not used in random evaluation)
     * @return true if the random number generated is less than the configured probability
     */
    @Override
    public boolean evaluate(Event event) {
        return ThreadLocalRandom.current().nextDouble() < probability;
    }
}
