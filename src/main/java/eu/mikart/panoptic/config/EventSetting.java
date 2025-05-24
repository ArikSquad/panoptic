package eu.mikart.panoptic.config;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.ActionData;
import eu.mikart.panoptic.event.Condition;
import eu.mikart.panoptic.event.ConditionData;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class EventSetting<T extends EventSetting.EventData> {
    public record EventData(
            List<ConditionData> conditions,
            List<ActionData> actions) {

        public List<Condition> resolveConditions() {
            return conditions == null ? List.of() :
                    conditions.stream()
                            .map(eu.mikart.panoptic.event.registry.ConditionRegistry::create)
                            .toList();
        }

        public List<Action> resolveActions() {
            return actions == null ? List.of() :
                    actions.stream()
                            .map(eu.mikart.panoptic.event.registry.ActionRegistry::create)
                            .toList();
        }
    }
}

