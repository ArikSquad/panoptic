package eu.mikart.panoptic.util;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.Nullable;

public class BukkitEventParser {

	@Nullable
	public static Player getPlayerFromEvent(Event event) {
		return switch (event) {
			case PlayerEvent playerEvent -> playerEvent.getPlayer();
			case BlockBreakEvent blockBreakEvent -> blockBreakEvent.getPlayer();
			case EntityDeathEvent entityDeathEvent -> {
				if (entityDeathEvent.getDamageSource().getCausingEntity() instanceof Player p) {
					yield p;
				} else {
					yield null;
				}
			}
			case BlockPlaceEvent blockPlaceEvent -> blockPlaceEvent.getPlayer();
			case EntityEvent entityEvent -> {
				if (entityEvent.getEntity() instanceof Player player) {
					yield player;
				} else {
					yield null;
				}
			}
			case null, default -> {
				if (event == null) {
					yield null;
				}
				try {
					if (event.getClass().getMethod("getPlayer").getReturnType() == Player.class) {
						try {
							yield (Player) event.getClass().getMethod("getPlayer").invoke(event);
						} catch (Exception e) {
							yield null;
						}
					} else {
						yield null;
					}
				} catch (NoSuchMethodException e) {
					yield null;
				}
			}
		};
	}
}
