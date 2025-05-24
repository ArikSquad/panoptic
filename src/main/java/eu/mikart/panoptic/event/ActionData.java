package eu.mikart.panoptic.event;

import eu.mikart.panoptic.event.action.value.ActionValue;

public record ActionData(String type, ActionValue value) {}
