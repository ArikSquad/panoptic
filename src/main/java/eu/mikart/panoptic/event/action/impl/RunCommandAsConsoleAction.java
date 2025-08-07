package eu.mikart.panoptic.event.action.impl;

import eu.mikart.panoptic.event.action.Action;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class RunCommandAsConsoleAction implements Action {
    private final String command;

    public RunCommandAsConsoleAction(String command) {
        this.command = command;
    }

    @Override
    public void execute(Event event) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }
}

