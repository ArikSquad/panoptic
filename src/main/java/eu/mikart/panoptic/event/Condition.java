package eu.mikart.panoptic.event;

import org.bukkit.event.Event;

public interface Condition {
    boolean evaluate(Event event);
}
