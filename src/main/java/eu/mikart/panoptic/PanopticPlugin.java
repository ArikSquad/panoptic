package eu.mikart.panoptic;

import eu.mikart.panoptic.command.PanopticCommand;
import eu.mikart.panoptic.config.ConfigProvider;
import eu.mikart.panoptic.config.Settings;
import eu.mikart.panoptic.config.event.BlockBreakSetting;
import eu.mikart.panoptic.listener.EventfulManager;
import lombok.Getter;
import lombok.Setter;
import net.william278.desertwell.util.Version;
import net.william278.uniform.Uniform;
import net.william278.uniform.paper.PaperUniform;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@Getter
public class PanopticPlugin extends JavaPlugin implements ConfigProvider {

  @Getter
  public static PanopticPlugin instance;

  @Setter
  private Settings settings;

  @Setter
  private BlockBreakSetting blockBreakSetting;

  private boolean placeholderAPIEnabled = false;
  private Version version;

  @Override
  public void onEnable() {
    instance = this;
    version = Version.fromString(getPluginMeta().getVersion());
    getLogger().info("Panoptic plugin has been enabled.");
    loadConfig();

    if (getSettings().isUsePlaceholderAPI() && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
      placeholderAPIEnabled = true;
    }

    new EventfulManager(this).initialize();
    PaperUniform uniform = PaperUniform.getInstance(this);
    uniform.register(new PanopticCommand());
  }

  @Override
  public void onDisable() {
    instance = null;
    getLogger().info("Panoptic plugin has been disabled.");
  }

  @Override
  public @NotNull Path getConfigDirectory() {
    return getDataFolder().toPath();
  }

  @Override
  public @NotNull PanopticPlugin getPlugin() {
    return this;
  }
}
