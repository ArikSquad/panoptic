package eu.mikart.panoptic.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import eu.mikart.panoptic.command.PluginCommand;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    @Comment("Add Panoptic commands to this list to prevent them from being registered (e.g. ['panoptic'])")
    @Getter(AccessLevel.NONE)
    private List<String> disabledCommands = new ArrayList<>();

    public boolean isCommandDisabled(@NotNull PluginCommand command) {
        return disabledCommands.stream().map(c -> c.startsWith("/") ? c.substring(1) : c).anyMatch(c -> c.equalsIgnoreCase(command.getName()) || command.getAliases().contains(c));
    }

}
