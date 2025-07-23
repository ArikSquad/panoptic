package eu.mikart.panoptic.event.action;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitTask;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.event.Action;

/**
 * An action that executes another action after a specified delay.
 * This is useful for creating timed effects, delayed responses, or scheduled events.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public class DelayedAction implements Action {
    
    private final Action wrappedAction;
    private final long delayTicks;
    private final boolean async;
    
    /**
     * Creates a new DelayedAction that executes synchronously.
     * 
     * @param wrappedAction The action to execute after the delay
     * @param delayTicks The delay in server ticks (20 ticks = 1 second)
     */
    public DelayedAction(Action wrappedAction, long delayTicks) {
        this(wrappedAction, delayTicks, false);
    }
    
    /**
     * Creates a new DelayedAction with optional async execution.
     * 
     * @param wrappedAction The action to execute after the delay
     * @param delayTicks The delay in server ticks (20 ticks = 1 second)
     * @param async Whether to execute the action asynchronously
     */
    public DelayedAction(Action wrappedAction, long delayTicks, boolean async) {
        this.wrappedAction = wrappedAction;
        this.delayTicks = delayTicks;
        this.async = async;
    }
    
    @Override
    public void execute(Event event) {
        PanopticPlugin plugin = PanopticPlugin.getInstance();
        
        BukkitTask task;
        if (async) {
            task = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, 
                () -> wrappedAction.execute(event), delayTicks);
        } else {
            task = Bukkit.getScheduler().runTaskLater(plugin, 
                () -> wrappedAction.execute(event), delayTicks);
        }
        
        // Log the scheduled action
        plugin.getLogger().info("Scheduled " + (async ? "async " : "") + 
            "action " + wrappedAction.getClass().getSimpleName() + 
            " to execute in " + delayTicks + " ticks");
    }
}
