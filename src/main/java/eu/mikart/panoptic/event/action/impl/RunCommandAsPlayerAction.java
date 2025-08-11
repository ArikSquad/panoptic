package eu.mikart.panoptic.event.action.impl;

import eu.mikart.panoptic.event.action.Action;
import eu.mikart.panoptic.event.context.ActionContextParser;
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
        Player player = ripPlayerOffEvent(event);
        if (player == null) {
            return;
        }

        String parsedCommand = ActionContextParser.parse(command, player, event);
        player.performCommand(parsedCommand);
    }
}

