package eu.mikart.panoptic.event.action;

import eu.mikart.panoptic.event.Action;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class CancelAction implements Action {
    @Override
    public void execute(Event event) {
        if (event instanceof Cancellable cancellable) {
            cancellable.setCancelled(true);
        }
    }
}

