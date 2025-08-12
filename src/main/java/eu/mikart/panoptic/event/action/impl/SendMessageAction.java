package eu.mikart.panoptic.event.action.impl;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.event.action.Action;
import eu.mikart.panoptic.event.context.ActionContextParser;
import eu.mikart.panoptic.event.context.MiniPlaceholderContextParser;
import eu.mikart.panoptic.event.context.PlaceholderContextParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class SendMessageAction implements Action {
    private final String message;

    public SendMessageAction(String message) {
        this.message = message;
    }

    @Override
    public void execute(Event event) {
        Player player = ripPlayerOffEvent(event);
		if (player == null) {
			return;
		}
        sendMessage(player, event);
	}

    private void sendMessage(Player player, Event event) {
        String parsedMessage = ActionContextParser.parse(message, player, event);
        if (PanopticPlugin.getInstance().isPlaceholderAPIEnabled()) {
            parsedMessage = PlaceholderContextParser.parse(parsedMessage, player);
        }
        if (PanopticPlugin.getInstance().isMiniPlaceholdersEnabled()) {
            parsedMessage = MiniPlaceholderContextParser.parse(parsedMessage, player);
        }
        Component component = MiniMessage.miniMessage().deserialize(parsedMessage);
        player.sendMessage(component);
    }
}
