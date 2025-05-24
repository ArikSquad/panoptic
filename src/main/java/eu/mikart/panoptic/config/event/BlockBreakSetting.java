package eu.mikart.panoptic.config.event;

import java.util.List;

import de.exlll.configlib.Configuration;
import eu.mikart.panoptic.config.EventSetting;
import lombok.Getter;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class BlockBreakSetting extends EventSetting<EventSetting.EventData> {

  public BlockBreakSetting() {
    this.events = List.of(
      new EventData(
        List.of(),
        List.of()
      )
    );
  }
}
