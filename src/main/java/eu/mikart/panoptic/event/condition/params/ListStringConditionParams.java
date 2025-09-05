package eu.mikart.panoptic.event.condition.params;

import java.util.List;

/**
 * Condition parameters representing a list of string values.
 * Used for conditions that can accept multiple acceptable values.
 *
 * @since 1.3.0
 */
public record ListStringConditionParams(List<String> value) implements ConditionParams {
}

