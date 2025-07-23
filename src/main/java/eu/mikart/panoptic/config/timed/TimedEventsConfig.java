package eu.mikart.panoptic.config.timed;

import java.util.ArrayList;
import java.util.List;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import lombok.Getter;

/**
 * Configuration class for timed events.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
@Configuration
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class TimedEventsConfig {
    
    public static final String CONFIG_HEADER = """
            ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
            ┃    Panoptic Timed Events     ┃
            ┃    Developed by ArikSquad    ┃
            ┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
            ┗╸ Schedule Types:
            ┗╸ - INTERVAL: Fixed interval in ticks (20 ticks = 1 second)
            ┗╸ - CRON: Cron expression (e.g., '0 0 * * *' for daily at midnight)
            ┗╸ - ONCE: One-time execution after delay in ticks
            ┗╸ Commands are executed as console commands""";
    
    @Comment({
        "List of timed events to execute",
        "Each event can be scheduled using different methods:",
        "- INTERVAL: Execute every X ticks (20 ticks = 1 second)",
        "- CRON: Use cron expressions for complex scheduling",
        "- ONCE: Execute once after a delay"
    })
    private List<TimedEventData> events = new ArrayList<>(List.of(
        new TimedEventData(
            "welcome_message",
            "1200", 
            TimedEvent.ScheduleType.INTERVAL,
            List.of("broadcast Welcome to our server!"),
            false,
            false
        ),
        new TimedEventData(
            "daily_restart_warning", 
            "0 3 * * *", 
            TimedEvent.ScheduleType.CRON,
            List.of("broadcast Server will restart in 1 hour!", "broadcast Please save your progress!"),
            false,
            false
        )
    ));
    
    /**
     * Data class for timed event configuration
     */
    @Getter
    @Configuration
    public static class TimedEventData {
        @Comment("Unique name for this timed event")
        private String name;
        
        @Comment({
            "Schedule expression:",
            "- For INTERVAL: number of ticks (e.g., '1200' for 60 seconds)",
            "- For CRON: cron expression (e.g., '0 0 * * *' for daily at midnight)",
            "- For ONCE: delay in ticks before execution"
        })
        private String schedule;
        
        @Comment("Schedule type: INTERVAL, CRON, or ONCE")
        private TimedEvent.ScheduleType scheduleType;
        
        @Comment("List of commands to execute (without leading slash)")
        private List<String> commands;
        
        @Comment("Whether to execute commands asynchronously")
        private boolean async;
        
        @Comment("Whether this timed event is enabled")
        private boolean enabled;
        
        public TimedEventData(String name, String schedule, TimedEvent.ScheduleType scheduleType, 
                             List<String> commands, boolean async, boolean enabled) {
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
        public TimedEventData() {
            this("example_event", "1200", TimedEvent.ScheduleType.INTERVAL, 
                 List.of("say Hello World!"), false, true);
        }
        
        /**
         * Convert to TimedEvent object
         */
        public TimedEvent toTimedEvent() {
            return new TimedEvent(name, schedule, scheduleType, commands, async, enabled);
        }
    }
}
