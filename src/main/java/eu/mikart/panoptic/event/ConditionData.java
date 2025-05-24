package eu.mikart.panoptic.event;

import eu.mikart.panoptic.event.condition.params.ConditionParams;

public record ConditionData(String type, ConditionParams params) {}
