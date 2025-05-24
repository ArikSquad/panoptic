package eu.mikart.panoptic.listener;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.Condition;
import java.util.List;

public class EventfulEvent {
    private final List<Condition> conditions;
    private final List<Action> actions;

    public EventfulEvent(List<Condition> conditions, List<Action> actions) {
        this.conditions = conditions;
        this.actions = actions;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public List<Action> getActions() {
        return actions;
    }
}

