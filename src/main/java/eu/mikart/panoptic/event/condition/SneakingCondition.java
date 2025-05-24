package eu.mikart.panoptic.event.condition;

import eu.mikart.panoptic.event.Condition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public class SneakingCondition implements Condition {
    @Override
    public boolean evaluate(Event event) {
        if (event instanceof PlayerEvent playerEvent) {
            Player player = playerEvent.getPlayer();
            return player.isSneaking();
        }
        return false;
    }
}

