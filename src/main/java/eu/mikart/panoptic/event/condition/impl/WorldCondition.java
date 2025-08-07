package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.world.WorldEvent;

public class WorldCondition implements Condition {
	private final String world;

	public WorldCondition(String world) {
		this.world = world;
	}

	@Override
	public boolean evaluate(Event event) {
		Player player = ripPlayerOffEvent(event);
		if (player != null) {
			return player.getWorld().getName().equalsIgnoreCase(world);
		}

		if (event instanceof WorldEvent e) {
			return e.getWorld().getName().equalsIgnoreCase(world);
		}
		return false;
	}

}