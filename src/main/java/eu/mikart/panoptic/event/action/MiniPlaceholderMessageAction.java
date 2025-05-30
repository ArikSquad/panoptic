package eu.mikart.panoptic.event.action;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.MiniPlaceholderContextParser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public class MiniPlaceholderMessageAction implements Action {
    private final String message;

    public MiniPlaceholderMessageAction(String message) {
        this.message = message;
    }

    @Override
    public void execute(Event event) {
        if (event instanceof PlayerEvent playerEvent) {
            Player player = playerEvent.getPlayer();
            player.sendMessage(MiniPlaceholderContextParser.parseToComponent(message, player));
        }
    }
}

