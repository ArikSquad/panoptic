package eu.mikart.panoptic.event.condition.params;

/**
 * Sealed interface for condition parameters that can be used in event conditions.
 * This interface ensures type safety for configuration serialization.
 * 
 * @author ArikSquad
 * @since 1.0.0
 */
public sealed interface ConditionParams permits BooleanConditionParams, StringConditionParams, DoubleConditionParams {}

