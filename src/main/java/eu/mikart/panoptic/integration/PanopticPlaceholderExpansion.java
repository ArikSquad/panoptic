package eu.mikart.panoptic.integration;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import eu.mikart.panoptic.PanopticPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PanopticPlaceholderExpansion extends PlaceholderExpansion {

    private final PanopticPlugin plugin;

    public PanopticPlaceholderExpansion(PanopticPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "panoptic";
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return null;
        }

        if (params.startsWith("cooldown_")) {
            String[] parts = params.split("_");
            if (parts.length >= 3) {
                try {
                    String eventType = parts[1];
                    int eventIndex = Integer.parseInt(parts[2]);

                    if (parts.length > 3) {
                        eventType = parts[1] + "_" + parts[2];
                        eventIndex = Integer.parseInt(parts[3]);
                    }

                    long remainingCooldown = plugin.getEventfulManager().getRemainingCooldown(eventType, eventIndex, player.getUniqueId());

                    if (remainingCooldown > 0) {
                        return String.valueOf(remainingCooldown / 1000);
                    } else {
                        return "0";
                    }
                } catch (NumberFormatException e) {
                    return "Invalid format";
                }
            }
        }

        return null;
    }
}
