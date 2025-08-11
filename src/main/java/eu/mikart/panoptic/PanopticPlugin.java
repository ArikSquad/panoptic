package eu.mikart.panoptic;

import java.nio.file.Path;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import eu.mikart.panoptic.command.PluginCommand;
import eu.mikart.panoptic.config.ConfigProvider;
import eu.mikart.panoptic.config.Settings;
import eu.mikart.panoptic.config.event.BlockBreakSetting;
import eu.mikart.panoptic.config.event.BlockPlaceSetting;
import eu.mikart.panoptic.config.event.CraftSetting;
import eu.mikart.panoptic.config.event.EntityInteractSetting;
import eu.mikart.panoptic.config.event.EntityKillSetting;
import eu.mikart.panoptic.config.event.FishCatchSetting;
import eu.mikart.panoptic.config.event.FishingSetting;
import eu.mikart.panoptic.config.event.FurnaceCookSetting;
import eu.mikart.panoptic.config.event.ItemConsumeSetting;
import eu.mikart.panoptic.config.event.ItemDropSetting;
import eu.mikart.panoptic.config.event.ItemEnchantSetting;
import eu.mikart.panoptic.config.event.ItemPickupSetting;
import eu.mikart.panoptic.config.event.ItemRepairSetting;
import eu.mikart.panoptic.config.event.MoveSetting;
import eu.mikart.panoptic.config.event.PlayerDamageSetting;
import eu.mikart.panoptic.config.event.PlayerDeathSetting;
import eu.mikart.panoptic.config.event.PlayerJoinSetting;
import eu.mikart.panoptic.config.event.PlayerLeaveSetting;
import eu.mikart.panoptic.config.event.PlayerSleepSetting;
import eu.mikart.panoptic.config.event.PlayerTeleportSetting;
import eu.mikart.panoptic.config.timed.TimedEventsConfig;
import eu.mikart.panoptic.integration.PanopticPlaceholderExpansion;
import eu.mikart.panoptic.listener.EventfulManager;
import eu.mikart.panoptic.timed.TimedEventsManager;
import eu.mikart.panoptic.script.AviatorScriptEngine;
import lombok.Getter;
import lombok.Setter;
import net.william278.desertwell.util.Version;
import net.william278.uniform.paper.PaperUniform;

@Getter
public class PanopticPlugin extends JavaPlugin implements ConfigProvider {

    @Getter
    public static PanopticPlugin instance;

    @Setter
    private Settings settings;

    @Setter
    private BlockBreakSetting blockBreakSetting;

    @Setter
    private BlockPlaceSetting blockPlaceSetting;

    @Setter
    private PlayerJoinSetting playerJoinSetting;

    @Setter
    private PlayerLeaveSetting playerLeaveSetting;

    @Setter
    private PlayerTeleportSetting playerTeleportSetting;

    @Setter
    private FishCatchSetting fishCatchSetting;

    @Setter
    private FishingSetting fishingSetting;

    @Setter
    private EntityKillSetting entityKillSetting;

    @Setter
    private FurnaceCookSetting furnaceCookSetting;

    @Setter
    private CraftSetting craftSetting;

    @Setter
    private MoveSetting moveSetting;

    @Setter
    private PlayerDeathSetting playerDeathSetting;

    @Setter
    private PlayerDamageSetting playerDamageSetting;

    @Setter
    private PlayerSleepSetting playerSleepSetting;

    @Setter
    private ItemConsumeSetting itemConsumeSetting;

    @Setter
    private ItemPickupSetting itemPickupSetting;

    @Setter
    private EntityInteractSetting entityInteractSetting;

    @Setter
    private ItemEnchantSetting itemEnchantSetting;

    @Setter
    private ItemRepairSetting itemRepairSetting;

    @Setter
    private ItemDropSetting itemDropSetting;

    @Setter
    private TimedEventsConfig timedEventsConfig;

    private boolean placeholderAPIEnabled = false;
    private boolean miniPlaceholdersEnabled = false;
    private EventfulManager eventfulManager;
    private TimedEventsManager timedEventsManager;
    private PanopticPlaceholderExpansion placeholderExpansion;
    private Version version;

    @Override
    public void onEnable() {
        instance = this;
        version = Version.fromString(getPluginMeta().getVersion());
        getLogger().info("Panoptic plugin has been enabled.");
        loadConfig();

        if (getSettings().isUsePlaceholderAPI() && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderAPIEnabled = true;
            placeholderExpansion = new PanopticPlaceholderExpansion(this);
            placeholderExpansion.register();
            getLogger().info("PlaceholderAPI integration enabled.");
        }

        if (getSettings().isUseMiniPlaceholders() && Bukkit.getPluginManager().getPlugin("MiniPlaceholders") != null) {
            miniPlaceholdersEnabled = true;
        }

        eventfulManager = new EventfulManager(this);
        eventfulManager.initialize();

        if (getSettings().isTimedEvents()) {
            timedEventsManager = new TimedEventsManager(this);
            timedEventsManager.initialize();
        }

        PaperUniform uniform = PaperUniform.getInstance(this);
        uniform.register(PluginCommand.Type.create(this));
    }

    @Override
    public void onDisable() {
        AviatorScriptEngine.clearCaches();
        if (placeholderExpansion != null) {
            placeholderExpansion.unregister();
        }
        if (timedEventsManager != null) {
            timedEventsManager.shutdown();
        }
        instance = null;
        getLogger().info("Panoptic plugin has been disabled.");
    }

    public void reload() {
        getLogger().info("Reloading Panoptic plugin...");

        AviatorScriptEngine.clearCaches();
        loadConfig();
        eventfulManager.unregisterAll();
        eventfulManager.initialize();

        if (timedEventsManager != null) {
            timedEventsManager.shutdown();
            timedEventsManager = null;
        }

        if (getSettings().isTimedEvents()) {
            timedEventsManager = new TimedEventsManager(this);
            timedEventsManager.initialize();
        }

        getLogger().info("Panoptic plugin has been reloaded.");
    }

    public void debug(String message) {
        if (getSettings().isDebug()) {
            getLogger().info("[DEBUG] " + message);
        }
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
