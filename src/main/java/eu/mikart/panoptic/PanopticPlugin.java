package eu.mikart.panoptic;

import org.bukkit.plugin.java.JavaPlugin;

public class PanopticPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		// Plugin startup logic
		getLogger().info("Panoptic plugin has been enabled.");
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		getLogger().info("Panoptic plugin has been disabled.");
	}
}
