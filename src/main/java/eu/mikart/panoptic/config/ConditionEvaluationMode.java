package eu.mikart.panoptic.config;

/**
 * Enumeration defining how multiple conditions should be evaluated for event triggers.
 * This determines the logical relationship between conditions in an event configuration.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public enum ConditionEvaluationMode {
    /**
     * All conditions must be true for the event actions to execute.
     * Uses logical AND operation between conditions.
     */
    REQUIRE_ALL,
    
    /**
     * At least one condition must be true for the event actions to execute.
     * Uses logical OR operation between conditions.
     */
    REQUIRE_ANY,
    
    /**
     * Exactly one condition must be true for the event actions to execute.
     * Uses logical XOR operation between conditions.
     */
    REQUIRE_SINGLE,
    
    /**
     * No conditions are required - actions will always execute.
     * Useful for events that should always trigger regardless of conditions.
     */
    IGNORE_CONDITIONS
}
