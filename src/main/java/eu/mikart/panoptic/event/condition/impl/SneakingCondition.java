package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SneakingCondition implements Condition {
    @Override
    public boolean evaluate(Event event) {
        Player player = ripPlayerOffEvent(event);
        if (player != null) {
            return player.isSneaking();
        }
        return false;
    }
}

