package eu.mikart.panoptic.event.action;

import eu.mikart.panoptic.util.BukkitEventParser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public interface Action {
    void execute(Event event);

    @Nullable
    default Player ripPlayerOffEvent(Event event) {
        return BukkitEventParser.getPlayerFromEvent(event);
    }

}
