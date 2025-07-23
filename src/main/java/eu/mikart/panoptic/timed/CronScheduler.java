package eu.mikart.panoptic.timed;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.config.timed.TimedEvent;

/**
 * Advanced cron scheduler that uses the CronParser for accurate scheduling.
 * Handles multiple cron events with precise timing and efficient scheduling.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public class CronScheduler {
    
    private final PanopticPlugin plugin;
    private final Map<String, CronJob> cronJobs = new ConcurrentHashMap<>();
    private BukkitTask schedulerTask;
    
    public CronScheduler(PanopticPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void start() {
        if (schedulerTask != null && !schedulerTask.isCancelled()) {
            return; // Already running
        }
        
        schedulerTask = Bukkit.getScheduler().runTaskTimer(plugin, this::checkCronJobs, 20L, 20L);
        
        if (plugin.getSettings().isDebug()) {
            plugin.getLogger().info("CronScheduler started");
        }
    }
    
    /**
     * Stop the cron scheduler
     */
    public void stop() {
        if (schedulerTask != null) {
            schedulerTask.cancel();
            schedulerTask = null;
        }
        
        cronJobs.clear();
        
        if (plugin.getSettings().isDebug()) {
            plugin.getLogger().info("CronScheduler stopped");
        }
    }
    
    public void addCronJob(TimedEvent event) {
        if (event == null || event.getSchedule() == null) {
            return;
        }
        
        // Validate cron expression
        if (!CronParser.isValid(event.getSchedule())) {
            plugin.getLogger().warning("Invalid cron expression for event '" + event.getName() + "': " + event.getSchedule());
            return;
        }
        
        CronJob cronJob = new CronJob(event);
        cronJobs.put(event.getName(), cronJob);
        
        String description = CronParser.describe(event.getSchedule());
        plugin.getLogger().info("Scheduled cron event '" + event.getName() + "': " + description + 
                               " (" + event.getSchedule() + ")");
    }
    
    /**
     * Remove a cron job
     */
    public void removeCronJob(String eventName) {
        CronJob removed = cronJobs.remove(eventName);
        if (removed != null && plugin.getSettings().isDebug()) {
            plugin.getLogger().info("Removed cron job: " + eventName);
        }
    }
    
    /**
     * Get information about all cron jobs
     */
    public Map<String, String> getCronJobInfo() {
        Map<String, String> info = new ConcurrentHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (Map.Entry<String, CronJob> entry : cronJobs.entrySet()) {
            String name = entry.getKey();
            CronJob job = entry.getValue();
            TimedEvent event = job.getEvent();
            
            LocalDateTime nextRun = CronParser.getNextExecution(event.getSchedule(), now);
            String nextRunStr = nextRun != null ? nextRun.toString() : "Unknown";
            
            String description = CronParser.describe(event.getSchedule());
            info.put(name, description + " (Next: " + nextRunStr + ")");
        }
        
        return info;
    }
    
    public boolean triggerCronJob(String eventName) {
        CronJob cronJob = cronJobs.get(eventName);
        if (cronJob == null) {
            return false;
        }
        
        plugin.getLogger().info("Manually triggering cron job: " + eventName);
        executeCronJob(cronJob);
        return true;
    }
    
    /**
     * Check all cron jobs and execute those that should run
     */
    private void checkCronJobs() {
        if (cronJobs.isEmpty()) {
            return;
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        for (CronJob cronJob : cronJobs.values()) {
            try {
                if (shouldExecute(cronJob, now)) {
                    executeCronJob(cronJob);
                    cronJob.updateLastExecution(now);
                }
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, 
                    "Error checking cron job: " + cronJob.getEvent().getName(), e);
            }
        }
    }
    
    /**
     * Check if a cron job should execute at the given time
     */
    private boolean shouldExecute(CronJob cronJob, LocalDateTime now) {
        TimedEvent event = cronJob.getEvent();
        
        // Check if the cron expression matches current time
        if (!CronParser.matches(event.getSchedule(), now)) {
            return false;
        }
        
        // Prevent executing multiple times in the same minute
        LocalDateTime lastExecution = cronJob.getLastExecution();
        if (lastExecution != null) {
            // If we already executed this minute, don't execute again
            if (lastExecution.getYear() == now.getYear() &&
                lastExecution.getMonthValue() == now.getMonthValue() &&
                lastExecution.getDayOfMonth() == now.getDayOfMonth() &&
                lastExecution.getHour() == now.getHour() &&
                lastExecution.getMinute() == now.getMinute()) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Execute a cron job
     */
    private void executeCronJob(CronJob cronJob) {
        TimedEvent event = cronJob.getEvent();
        
        if (event.getCommands() == null || event.getCommands().isEmpty()) {
            return;
        }
        
        if (plugin.getSettings().isDebug()) {
            plugin.getLogger().info("Executing cron job: " + event.getName() + 
                                  " (" + event.getCommands().size() + " commands)");
        }
        
        for (String command : event.getCommands()) {
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
                    "Failed to execute command '" + command + "' for cron job " + event.getName(), e);
            }
        }
    }
    
    /**
     * Inner class to represent a cron job with execution tracking
     */
    private static class CronJob {
        private final TimedEvent event;
        private LocalDateTime lastExecution;
        
        public CronJob(TimedEvent event) {
            this.event = event;
        }
        
        public TimedEvent getEvent() {
            return event;
        }
        
        public LocalDateTime getLastExecution() {
            return lastExecution;
        }
        
        public void updateLastExecution(LocalDateTime time) {
            this.lastExecution = time;
        }
    }
}
