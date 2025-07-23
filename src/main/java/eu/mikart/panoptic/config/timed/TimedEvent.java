package eu.mikart.panoptic.config.timed;

import java.util.List;

import lombok.Getter;

/**
 * Represents a single timed event with scheduling information and commands to execute.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
@Getter
public class TimedEvent {
    private final String name;
    private final String schedule;
    private final ScheduleType scheduleType;
    private final List<String> commands;
    private final boolean async;
    private final boolean enabled;
    
    public TimedEvent(String name, String schedule, ScheduleType scheduleType, List<String> commands, boolean async, boolean enabled) {
        this.name = name;
        this.schedule = schedule;
        this.scheduleType = scheduleType;
        this.commands = commands;
        this.async = async;
        this.enabled = enabled;
    }
    
    /**
     * Default constructor for ConfigLib serialization
     */
    public TimedEvent() {
        this("example_event", "0 0 * * *", ScheduleType.CRON, List.of("say Hello World!"), false, true);
    }
    
    public enum ScheduleType {
        /**
         * Fixed interval in ticks (20 ticks = 1 second)
         */
        INTERVAL,
        
        /**
         * Cron expression for complex scheduling
         */
        CRON,
        
        /**
         * One-time execution after delay in ticks
         */
        ONCE
    }
}
