package eu.mikart.panoptic.event.condition.params;

/**
 * Condition parameters for numeric values used in conditions that require decimal precision.
 * This record is primarily used for probability-based conditions like RandomCondition.
 * 
 * @param value The numeric value (typically a probability between 0.0 and 1.0)
 * @author ArikSquad
 * @since 1.1.0
 */
public record DoubleConditionParams(Double value) implements ConditionParams {
}
