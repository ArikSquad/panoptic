package eu.mikart.panoptic.config;

import java.util.List;

import eu.mikart.panoptic.event.ActionRegistry;
import eu.mikart.panoptic.event.ConditionRegistry;
import eu.mikart.panoptic.event.action.Action;
import eu.mikart.panoptic.event.action.ActionData;
import eu.mikart.panoptic.event.condition.Condition;
import eu.mikart.panoptic.event.condition.ConditionData;
import lombok.Getter;

@Getter
public abstract class EventSetting<T extends EventSetting.EventData> {
    public record EventData(
            List<ConditionData> conditions,
            List<ActionData> actions,
            ConditionEvaluationMode conditionEvaluationMode) {

        public List<Condition> resolveConditions() {
            return conditions == null ? List.of() :
                    conditions.stream()
                            .map(ConditionRegistry::create)
                            .toList();
        }

        public List<Action> resolveActions() {
            return actions == null ? List.of() :
                    actions.stream()
                            .map(ActionRegistry::create)
                            .toList();
        }

        /**
         * Gets the condition evaluation mode, defaulting to REQUIRE_ALL if not specified.
         * @return The condition evaluation mode to use
         */
        public ConditionEvaluationMode getConditionEvaluationMode() {
            return conditionEvaluationMode != null ? conditionEvaluationMode : ConditionEvaluationMode.REQUIRE_ALL;
        }
    }
}

