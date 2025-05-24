package eu.mikart.panoptic.event.action;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.ActionContextParser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public class RunCommandAsPlayerAction implements Action {
    private final String command;

    public RunCommandAsPlayerAction(String command) {
        this.command = command;
    }

    @Override
    public void execute(Event event) {
        if (event instanceof PlayerEvent playerEvent) {
            Player player = playerEvent.getPlayer();
            String parsedCommand = ActionContextParser.parse(command, player, event);
            player.performCommand(parsedCommand);
        }
    }
}

