package eu.mikart.panoptic.timed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.timed.TimedEvent;
import eu.mikart.panoptic.config.timed.TimedEventsConfig;

/**
 * Manages the execution of timed events based on configuration.
 * Supports interval-based, cron-based, and one-time scheduled events.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public class TimedEventsManager {
    
    private final PanopticPlugin plugin;
    private final Map<String, BukkitTask> runningTasks = new ConcurrentHashMap<>();
    private final Map<String, TimedEvent> events = new HashMap<>();
    private final CronScheduler cronScheduler;
    
    public TimedEventsManager(PanopticPlugin plugin) {
        this.plugin = plugin;
        this.cronScheduler = new CronScheduler(plugin);
    }
    
    public void initialize() {
        if (!plugin.getSettings().isTimedEvents()) {
            plugin.getLogger().info("Timed events are disabled in config.yml");
            return;
        }
        
        TimedEventsConfig config = plugin.getTimedEventsConfig();
        if (config == null || config.getEvents() == null) {
            plugin.getLogger().warning("Timed events config is null or has no events");
            return;
        }
        
        int enabledCount = 0;
        for (TimedEventsConfig.TimedEventData eventData : config.getEvents()) {
            if (eventData.isEnabled()) {
                TimedEvent event = eventData.toTimedEvent();
                events.put(event.getName(), event);
                scheduleEvent(event);
                enabledCount++;
            }
        }
        
        boolean hasCronEvents = events.values().stream()
            .anyMatch(event -> event.getScheduleType() == TimedEvent.ScheduleType.CRON);
        if (hasCronEvents) {
            cronScheduler.start();
        }
        
        plugin.getLogger().info("TimedEventsManager initialized with " + enabledCount + " enabled events.");
    }
    
    public void shutdown() {
        cronScheduler.stop();
        runningTasks.values().forEach(BukkitTask::cancel);
        runningTasks.clear();
        events.clear();
        plugin.getLogger().info("TimedEventsManager shutdown complete.");
    }
    
    /**
     * Reload the timed events configuration and restart all events
     */
    public void reload() {
        shutdown();
        initialize();
    }
    
    /**
     * Schedule a single timed event based on its configuration
     */
    private void scheduleEvent(TimedEvent event) {
        try {
            switch (event.getScheduleType()) {
                case INTERVAL -> scheduleIntervalEvent(event);
                case CRON -> scheduleCronEvent(event);
                case ONCE -> scheduleOnceEvent(event);
                default -> plugin.getLogger().warning("Unknown schedule type for event: " + event.getName());
            }
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to schedule timed event: " + event.getName(), e);
        }
    }
    
    /**
     * Schedule an interval-based repeating event
     */
    private void scheduleIntervalEvent(TimedEvent event) {
        try {
            long intervalTicks = Long.parseLong(event.getSchedule());
            if (intervalTicks <= 0) {
                plugin.getLogger().warning("Invalid interval for event " + event.getName() + ": " + event.getSchedule());
                return;
            }
            
            BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, 
                () -> executeCommands(event), 
                intervalTicks, 
                intervalTicks
            );
            
            runningTasks.put(event.getName(), task);
            plugin.getLogger().info("Scheduled interval event '" + event.getName() + "' every " + intervalTicks + " ticks");
            
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("Invalid interval format for event " + event.getName() + ": " + event.getSchedule());
        }
    }
    
    /**
     * Schedule a cron-based event using the advanced CronScheduler
     */
    private void scheduleCronEvent(TimedEvent event) {
        // Validate the cron expression first
        if (!CronParser.isValid(event.getSchedule())) {
            plugin.getLogger().warning("Invalid cron expression for event '" + event.getName() + "': " + event.getSchedule());
            return;
        }
        
        cronScheduler.addCronJob(event);
        
        // Add a dummy task to track the event as "running"
        BukkitTask dummyTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            // This task does nothing - it's just for tracking purposes
        }, Long.MAX_VALUE, Long.MAX_VALUE);
        
        runningTasks.put(event.getName(), dummyTask);
        
        String description = CronParser.describe(event.getSchedule());
        plugin.getLogger().info("Scheduled cron event '" + event.getName() + "': " + description + 
                               " (" + event.getSchedule() + ")");
    }
    
    /**
     * Schedule a one-time event
     */
    private void scheduleOnceEvent(TimedEvent event) {
        try {
            long delayTicks = Long.parseLong(event.getSchedule());
            if (delayTicks < 0) {
                plugin.getLogger().warning("Invalid delay for event " + event.getName() + ": " + event.getSchedule());
                return;
            }
            
            BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, 
                () -> {
                    executeCommands(event);
                    runningTasks.remove(event.getName());
                }, 
                delayTicks
            );
            
            runningTasks.put(event.getName(), task);
            plugin.getLogger().info("Scheduled one-time event '" + event.getName() + "' in " + delayTicks + " ticks");
            
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("Invalid delay format for event " + event.getName() + ": " + event.getSchedule());
        }
    }
    
    /**
     * Execute the commands for a timed event
     */
    private void executeCommands(TimedEvent event) {
        List<String> commands = event.getCommands();
        if (commands == null || commands.isEmpty()) {
            return;
        }
        
        if (plugin.getSettings().isDebug())
            plugin.getLogger().info("Executing timed event: " + event.getName() + 
                               " (" + commands.size() + " commands)");
        
        for (String command : commands) {
            if (command == null || command.trim().isEmpty()) {
                continue;
            }
            
            try {
                if (event.isAsync()) {
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> 
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
                } else {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, 
                    "Failed to execute command '" + command + "' for event " + event.getName(), e);
            }
        }
    }
    
    /**
     * Get information about currently running timed events
     */
    public Map<String, String> getEventInfo() {
        Map<String, String> info = new HashMap<>();
    
        for (Map.Entry<String, TimedEvent> entry : events.entrySet()) {
            String name = entry.getKey();
            TimedEvent event = entry.getValue();
            BukkitTask task = runningTasks.get(name);
            
            String status = task != null && !task.isCancelled() ? "Running" : "Stopped";
            if (event.getScheduleType() == TimedEvent.ScheduleType.CRON) {
                String description = CronParser.describe(event.getSchedule());
                info.put(name, status + " - " + description + " (" + event.getSchedule() + ")");
            } else {
                info.put(name, status + " (" + event.getScheduleType() + ": " + event.getSchedule() + ")");
            }
        }
        
        return info;
    }
    
    /**
     * Manually trigger a timed event
     */
    public boolean triggerEvent(String eventName) {
        TimedEvent event = events.get(eventName);
        if (event == null) {
            return false;
        }
        
        plugin.getLogger().info("Manually triggering timed event: " + eventName);
        
        // For cron events, use the cron scheduler's trigger method
        if (event.getScheduleType() == TimedEvent.ScheduleType.CRON) {
            return cronScheduler.triggerCronJob(eventName);
        } else {
            // For other events, execute directly
            executeCommands(event);
            return true;
        }
    }
}
