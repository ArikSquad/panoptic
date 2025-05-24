package eu.mikart.panoptic.config;

import de.exlll.configlib.*;
import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.event.*;

import eu.mikart.panoptic.event.action.value.ActionValue;
import eu.mikart.panoptic.event.condition.params.ConditionParams;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;

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
  }

  /**
   * Loads an event setting from a YAML configuration file
   *
   * @param relativePath Path to the config file relative to config directory
   * @param settingClass Class of the event setting to load
   * @param setter Method reference to the appropriate setter
   * @param <T> Type of event setting
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
