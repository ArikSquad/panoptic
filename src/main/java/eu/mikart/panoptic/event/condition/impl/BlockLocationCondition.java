package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;

public class BlockLocationCondition implements Condition {
	private final String blockLocation;

	public BlockLocationCondition(String blockLocation) {
		this.blockLocation = blockLocation;
	}

	@Override
	public boolean evaluate(Event event) {
		if (event instanceof BlockEvent e) {
			return locationToString(e.getBlock().getLocation()).equalsIgnoreCase(blockLocation);
		}
		return false;
	}

	// turns a Location to a string representation (e.g. "world,100,64,200")
	private String locationToString(Location location) {
		if (location == null) {
			return "null";
		}
		return String.format("%s,%d,%d,%d", location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
}

