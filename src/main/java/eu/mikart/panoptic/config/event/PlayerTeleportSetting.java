package eu.mikart.panoptic.config.event;

import java.util.List;

import de.exlll.configlib.Configuration;
import lombok.Getter;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class PlayerTeleportSetting extends EventSetting<EventSetting.EventData> {

  public PlayerTeleportSetting() {
    this.events = List.of(
      new EventData(
        List.of(),
        List.of()
      )
    );
  }
}

