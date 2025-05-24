package eu.mikart.panoptic.config;

import de.exlll.configlib.*;
import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.event.BlockBreakSetting;

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

  default void loadSettings() {
    setSettings(YamlConfigurations.update(
        getConfigDirectory().resolve("config.yml"),
        Settings.class,
        YAML_CONFIGURATION_PROPERTIES.header(Settings.CONFIG_HEADER).build()));
  }

  default void loadEventSetting() {
    Path eventConfig = getConfigDirectory().resolve("events/block_break.yml");
    setBlockBreakSetting(YamlConfigurations.update(
        eventConfig,
        BlockBreakSetting.class,
        YAML_CONFIGURATION_PROPERTIES.build()));
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
