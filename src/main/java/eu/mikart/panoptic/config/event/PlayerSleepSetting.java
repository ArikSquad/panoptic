package eu.mikart.panoptic.config.event;

import de.exlll.configlib.Configuration;
import lombok.Getter;

import java.util.List;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class PlayerSleepSetting extends EventSetting<EventSetting.EventData> {
    public PlayerSleepSetting() {
        this.events = List.of(
                new EventData(
                        List.of(),
                        List.of()
                )
        );
    }
}

