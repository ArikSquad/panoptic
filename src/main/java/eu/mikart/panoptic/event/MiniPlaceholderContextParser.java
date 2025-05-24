package eu.mikart.panoptic.event;

import eu.mikart.panoptic.PanopticPlugin;
import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

public class MiniPlaceholderContextParser {
    public static String parse(String input, Player player) {
        String result = input;
        if (player != null && PanopticPlugin.getInstance().isMiniPlaceholdersEnabled()) {
            PlainTextComponentSerializer serializer = PlainTextComponentSerializer.plainText();
            result = serializer.serialize(MiniMessage.miniMessage().deserialize(result, MiniPlaceholders.getAudiencePlaceholders(player)));
        }
        return result;
    }
}

