package eu.mikart.panoptic.event.action.impl;

import eu.mikart.panoptic.event.action.Action;
import eu.mikart.panoptic.event.context.PlaceholderContextParser;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public class PlaceholderMessageAction implements Action {
    private final String message;

    public PlaceholderMessageAction(String message) {
        this.message = message;
    }

    @Override
    public void execute(Event event) {
        if (event instanceof PlayerEvent playerEvent) {
            Player player = playerEvent.getPlayer();
            String parsedMessage = PlaceholderContextParser.parse(message, player);
            player.sendMessage(MiniMessage.miniMessage().deserialize(parsedMessage));
        }
    }
}

