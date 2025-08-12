package eu.mikart.panoptic.config;

/**
 * Enumeration defining how multiple conditions should be evaluated for event triggers.
 * This determines the logical relationship between conditions in an event configuration.
 *
 * @author ArikSquad
 * @since 1.1.0
 */
public enum ConditionEvaluationMode {
    REQUIRE_ALL,
    REQUIRE_ANY,
    REQUIRE_SINGLE,
    IGNORE_CONDITIONS
}
