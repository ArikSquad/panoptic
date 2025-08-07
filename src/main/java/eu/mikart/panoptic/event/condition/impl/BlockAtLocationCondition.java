package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class BlockAtLocationCondition implements Condition {
	private final String blockName;

	public BlockAtLocationCondition(String blockName) {
		this.blockName = blockName;
	}

	@Override
	public boolean evaluate(Event event) {
		Player player = ripPlayerOffEvent(event);
		if (player == null) {
			return false;
		}

		Location location = player.getLocation();
		Material blockAtLocation = player.getWorld().getBlockAt(location).getType();
		return blockAtLocation.toString().equalsIgnoreCase(blockName);
	}

}

