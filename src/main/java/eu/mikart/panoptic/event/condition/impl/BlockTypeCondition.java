package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockTypeCondition implements Condition {
	private final Set<Material> allowed;

	public BlockTypeCondition(String blockType) {
		this(blockType == null ? List.of() : List.of(blockType));
	}

	public BlockTypeCondition(List<String> blockTypes) {
		this.allowed = new HashSet<>();
		if (blockTypes != null) {
			for (String s : blockTypes) {
				if (s == null) continue;
				try {
					Material m = Material.valueOf(s);
					allowed.add(m);
				} catch (IllegalArgumentException ignored) {

				}
			}
		}
	}

	@Override
	public boolean evaluate(Event event) {
		if (event instanceof BlockEvent e) {
			if (allowed.isEmpty()) return false;
			return allowed.contains(e.getBlock().getType());
		}
		return false;
	}
}
