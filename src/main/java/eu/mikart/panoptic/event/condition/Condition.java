package eu.mikart.panoptic.event.condition;

import eu.mikart.panoptic.util.BukkitEventParser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public interface Condition {
    boolean evaluate(Event event);

    @Nullable
    default Player ripPlayerOffEvent(Event event) {
        return BukkitEventParser.getPlayerFromEvent(event);
    }

    default boolean allowsLists() {
        return false;
    }

}
