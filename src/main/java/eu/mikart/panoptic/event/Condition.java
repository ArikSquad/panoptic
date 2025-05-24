package eu.mikart.panoptic.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;

import javax.annotation.Nullable;

public interface Condition {
    boolean evaluate(Event event);

    @Nullable
    default Player ripPlayerOffEvent(Event event) {
        if (event instanceof PlayerEvent playerEvent) {
            return playerEvent.getPlayer();
        }

        if (event instanceof EntityEvent entityEvent) {
            if (entityEvent.getEntity() instanceof Player player) {
                return player;
            }
        }

        if (event instanceof BlockPlaceEvent blockPlaceEvent) {
            return blockPlaceEvent.getPlayer();
        }

        if (event instanceof BlockBreakEvent blockBreakEvent) {
            return blockBreakEvent.getPlayer();
        }
        return null;
    }
}
