package eu.mikart.panoptic.event.context;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ActionContextParser {
    private static final Map<String, BiFunction<Player, Event, String>> PLACEHOLDER_MAP = new HashMap<>();

    static {
        PLACEHOLDER_MAP.put("%player%", (player, event) -> player != null ? player.getName() : "");
        PLACEHOLDER_MAP.put("%player_uuid%", (player, event) -> player != null ? player.getUniqueId().toString() : "");
        PLACEHOLDER_MAP.put("%block_type%", (player, event) -> {
            if (event instanceof BlockEvent blockEvent) {
                return blockEvent.getBlock().getType().toString();
            }
            return "";
        });
        PLACEHOLDER_MAP.put("%block_location%", (player, event) -> {
            if (event instanceof BlockEvent blockEvent) {
                return blockEvent.getBlock().getLocation().toString();
            }
            return "";
        });
        PLACEHOLDER_MAP.put("%event_name%", (player, event) -> event != null ? event.getEventName() : "");
    }

    public static String parse(String input, Player player, Event event) {
        String result = input;
        for (Map.Entry<String, BiFunction<Player, Event, String>> entry : PLACEHOLDER_MAP.entrySet()) {
            if (result.contains(entry.getKey())) {
                result = result.replace(entry.getKey(), entry.getValue().apply(player, event));
            }
        }
        return result;
    }
}

