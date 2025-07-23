package eu.mikart.panoptic.config;

import java.awt.Color;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.logging.Level;

import org.jetbrains.annotations.NotNull;

import de.exlll.configlib.ConfigurationElementFilter;
import de.exlll.configlib.NameFormatters;
import de.exlll.configlib.YamlConfigurationProperties;
import de.exlll.configlib.YamlConfigurations;
import eu.mikart.panoptic.PanopticPlugin;
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
import eu.mikart.panoptic.event.action.value.ActionValue;
import eu.mikart.panoptic.event.condition.params.ConditionParams;

public interface ConfigProvider {

    @NotNull
    YamlConfigurationProperties.Builder<?> YAML_CONFIGURATION_PROPERTIES = YamlConfigurationProperties.newBuilder()
            .addSerializer(Color.class, new ColorSerializer())
            .addSerializer(ConditionParams.class, new ConditionParamsSerializer())
            .addSerializer(ActionValue.class, new ActionValueSerializer())
            .addPostProcessor(ConfigurationElementFilter.byPostProcessKey("lowercase"), (String value) -> value.toLowerCase())
            .charset(StandardCharsets.UTF_8)
            .setNameFormatter(NameFormatters.LOWER_UNDERSCORE);

    default void loadConfig() {
        loadSettings();
        loadEventSetting();
        loadTimedEventsConfig();
    }

    @NotNull
    Settings getSettings();

    void setSettings(@NotNull Settings settings);

    @NotNull
    BlockBreakSetting getBlockBreakSetting();

    void setBlockBreakSetting(@NotNull BlockBreakSetting setting);

    @NotNull
    BlockPlaceSetting getBlockPlaceSetting();

    void setBlockPlaceSetting(@NotNull BlockPlaceSetting setting);

    @NotNull
    PlayerTeleportSetting getPlayerTeleportSetting();

    void setPlayerTeleportSetting(@NotNull PlayerTeleportSetting setting);

    @NotNull
    PlayerJoinSetting getPlayerJoinSetting();

    void setPlayerJoinSetting(@NotNull PlayerJoinSetting setting);

    @NotNull
    PlayerLeaveSetting getPlayerLeaveSetting();

    void setPlayerLeaveSetting(@NotNull PlayerLeaveSetting setting);

    @NotNull
    FishCatchSetting getFishCatchSetting();

    void setFishCatchSetting(@NotNull FishCatchSetting setting);

    @NotNull
    FishingSetting getFishingSetting();

    void setFishingSetting(@NotNull FishingSetting setting);

    @NotNull
    EntityKillSetting getEntityKillSetting();

    void setEntityKillSetting(@NotNull EntityKillSetting setting);

    @NotNull
    FurnaceCookSetting getFurnaceCookSetting();

    void setFurnaceCookSetting(@NotNull FurnaceCookSetting setting);

    @NotNull
    CraftSetting getCraftSetting();

    void setCraftSetting(@NotNull CraftSetting setting);

    @NotNull
    MoveSetting getMoveSetting();

    void setMoveSetting(@NotNull MoveSetting setting);

    @NotNull
    PlayerDeathSetting getPlayerDeathSetting();

    void setPlayerDeathSetting(@NotNull PlayerDeathSetting setting);

    @NotNull
    PlayerDamageSetting getPlayerDamageSetting();

    void setPlayerDamageSetting(@NotNull PlayerDamageSetting setting);

    @NotNull
    PlayerSleepSetting getPlayerSleepSetting();

    void setPlayerSleepSetting(@NotNull PlayerSleepSetting setting);

    @NotNull
    ItemConsumeSetting getItemConsumeSetting();

    void setItemConsumeSetting(@NotNull ItemConsumeSetting setting);

    @NotNull
    ItemPickupSetting getItemPickupSetting();

    void setItemPickupSetting(@NotNull ItemPickupSetting setting);

    @NotNull
    EntityInteractSetting getEntityInteractSetting();

    void setEntityInteractSetting(@NotNull EntityInteractSetting setting);

    @NotNull
    ItemEnchantSetting getItemEnchantSetting();

    void setItemEnchantSetting(@NotNull ItemEnchantSetting setting);

    @NotNull
    ItemRepairSetting getItemRepairSetting();

    void setItemRepairSetting(@NotNull ItemRepairSetting setting);

    @NotNull
    ItemDropSetting getItemDropSetting();

    void setItemDropSetting(@NotNull ItemDropSetting setting);

    @NotNull
    TimedEventsConfig getTimedEventsConfig();

    void setTimedEventsConfig(@NotNull TimedEventsConfig config);

    default void loadSettings() {
        setSettings(YamlConfigurations.update(
                getConfigDirectory().resolve("config.yml"),
                Settings.class,
                YAML_CONFIGURATION_PROPERTIES.header(Settings.CONFIG_HEADER).build()));
    }

    default void loadEventSetting() {
        loadEventConfig("events/block_break.yml", BlockBreakSetting.class, this::setBlockBreakSetting);
        loadEventConfig("events/block_place.yml", BlockPlaceSetting.class, this::setBlockPlaceSetting);
        loadEventConfig("events/player_teleport.yml", PlayerTeleportSetting.class, this::setPlayerTeleportSetting);
        loadEventConfig("events/player_join.yml", PlayerJoinSetting.class, this::setPlayerJoinSetting);
        loadEventConfig("events/player_leave.yml", PlayerLeaveSetting.class, this::setPlayerLeaveSetting);
        loadEventConfig("events/fish_catch.yml", FishCatchSetting.class, this::setFishCatchSetting);
        loadEventConfig("events/fishing.yml", FishingSetting.class, this::setFishingSetting);
        loadEventConfig("events/entity_kill.yml", EntityKillSetting.class, this::setEntityKillSetting);
        loadEventConfig("events/furnace_cook.yml", FurnaceCookSetting.class, this::setFurnaceCookSetting);
        loadEventConfig("events/craft.yml", CraftSetting.class, this::setCraftSetting);
        loadEventConfig("events/move.yml", MoveSetting.class, this::setMoveSetting);
        loadEventConfig("events/player_death.yml", PlayerDeathSetting.class, this::setPlayerDeathSetting);
        loadEventConfig("events/player_damage.yml", PlayerDamageSetting.class, this::setPlayerDamageSetting);
        loadEventConfig("events/player_sleep.yml", PlayerSleepSetting.class, this::setPlayerSleepSetting);
        loadEventConfig("events/item_consume.yml", ItemConsumeSetting.class, this::setItemConsumeSetting);
        loadEventConfig("events/item_pickup.yml", ItemPickupSetting.class, this::setItemPickupSetting);
        loadEventConfig("events/entity_interact.yml", EntityInteractSetting.class, this::setEntityInteractSetting);
        loadEventConfig("events/item_enchant.yml", ItemEnchantSetting.class, this::setItemEnchantSetting);
        loadEventConfig("events/item_repair.yml", ItemRepairSetting.class, this::setItemRepairSetting);
        loadEventConfig("events/item_drop.yml", ItemDropSetting.class, this::setItemDropSetting);
    }

    default void loadTimedEventsConfig() {
        if (getSettings().isTimedEvents()) {
            setTimedEventsConfig(YamlConfigurations.update(
                    getConfigDirectory().resolve("timed.yml"),
                    TimedEventsConfig.class,
                    YAML_CONFIGURATION_PROPERTIES.header(TimedEventsConfig.CONFIG_HEADER).build()));
        }
    }

    /**
     * Loads an event setting from a YAML configuration file
     *
     * @param relativePath Path to the config file relative to config directory
     * @param settingClass Class of the event setting to load
     * @param setter       Method reference to the appropriate setter
     * @param <T>          Type of event setting
     */
    private <T> void loadEventConfig(String relativePath, Class<T> settingClass, java.util.function.Consumer<T> setter) {
        Path configPath = getConfigDirectory().resolve(relativePath);
        try {
            T setting = YamlConfigurations.update(
                    configPath,
                    settingClass,
                    YAML_CONFIGURATION_PROPERTIES.build());
            setter.accept(setting);
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.SEVERE, "Failed to load config: " + relativePath, e);
        }
    }

    /**
     * Get a plugin resource
     *
     * @param name The name of the resource
     * @return the resource, if found
     * @since 1.0
     */
    InputStream getResource(@NotNull String name);

    /**
     * Get the plugin config directory
     *
     * @return the plugin config directory
     * @since 1.0
     */
    @NotNull
    Path getConfigDirectory();

    @NotNull
    PanopticPlugin getPlugin();

}
