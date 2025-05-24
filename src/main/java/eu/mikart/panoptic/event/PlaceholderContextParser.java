package eu.mikart.panoptic.event;

import eu.mikart.panoptic.PanopticPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderContextParser {
    public static String parse(String input, Player player) {
        String result = input;
        if (player != null && PanopticPlugin.getInstance().isPlaceholderAPIEnabled()) {
            result = PlaceholderAPI.setPlaceholders(player, result);
        }
        return result;
    }
}

