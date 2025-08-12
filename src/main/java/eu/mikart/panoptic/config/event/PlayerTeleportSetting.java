package eu.mikart.panoptic.config.event;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import eu.mikart.panoptic.config.ConditionEvaluationMode;
import eu.mikart.panoptic.config.EventSetting;
import lombok.Getter;

import java.util.List;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class PlayerTeleportSetting extends EventSetting<EventSetting.EventData> {

  @Comment("If true, the event will be listened to. If false, it will not be listened to.")
  protected boolean listen = false;
  protected List<EventData> events = List.of(new EventData(
        List.of(),
        List.of(),
        ConditionEvaluationMode.REQUIRE_ALL,
        0L,
        0L
  ));

}
