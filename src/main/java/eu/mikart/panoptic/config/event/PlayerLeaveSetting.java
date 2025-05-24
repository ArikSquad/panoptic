package eu.mikart.panoptic.config.event;

import java.util.List;

import de.exlll.configlib.Configuration;
import lombok.Getter;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class PlayerLeaveSetting extends EventSetting<EventSetting.EventData> {

  public PlayerLeaveSetting() {
    this.events = List.of(
      new EventData(
        List.of(),
        List.of()
      )
    );
  }
}

