package eu.mikart.panoptic.config.event;

import de.exlll.configlib.Configuration;
import eu.mikart.panoptic.config.EventSetting;
import lombok.Getter;

import java.util.List;

@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class ItemEnchantSetting extends EventSetting<EventSetting.EventData> {
    public ItemEnchantSetting() {
        this.events = List.of(
                new EventData(
                        List.of(),
                        List.of()
                )
        );
    }
}

