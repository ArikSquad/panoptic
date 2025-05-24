package eu.mikart.panoptic.event.action;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.ActionContextParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public class SendMessageAction implements Action {
    private final String message;

    public SendMessageAction(String message) {
        this.message = message;
    }

    @Override
    public void execute(Event event) {
        if (event instanceof PlayerEvent playerEvent) {
            Player player = playerEvent.getPlayer();
            String parsedMessage = ActionContextParser.parse(message, player, event);
            Component component = MiniMessage.miniMessage().deserialize(parsedMessage);
            player.sendMessage(component);
        }
    }
}
