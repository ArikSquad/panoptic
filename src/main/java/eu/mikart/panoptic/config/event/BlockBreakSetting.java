package eu.mikart.panoptic.config.event;

import java.util.List;

import de.exlll.configlib.Configuration;
import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.ActionData;
import eu.mikart.panoptic.event.Condition;
import eu.mikart.panoptic.event.ConditionData;
import lombok.Getter;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class BlockBreakSetting {

  private List<Inner> events = List.of(
        new Inner(
            List.of(),
            List.of()
        )
  );

  public record Inner(
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
