package eu.mikart.panoptic.config;

import de.exlll.configlib.Configuration;
import lombok.Getter;

@Getter
@Configuration
@SuppressWarnings("FieldMayBeFinal")
public class Settings {

  protected static final String CONFIG_HEADER = """
      ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
      ┃       Panoptic Config        ┃
      ┃    Developed by ArikSquad    ┃
      ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
      ┗╸ Documentation: No Documentation Yet""";

  private boolean debug = false;
  private boolean usePlaceholderAPI = true;
  private boolean useMiniPlaceholders = true;

}
