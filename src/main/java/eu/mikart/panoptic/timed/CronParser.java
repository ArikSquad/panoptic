package eu.mikart.panoptic.timed;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * A comprehensive cron expression parser and scheduler.
 * Supports standard cron format: minute hour day month dayOfWeek
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public class CronParser {
    
    // Day of week mapping (case insensitive)
    private static final String[] DAY_NAMES = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    // Month mapping (case insensitive)  
    private static final String[] MONTH_NAMES = {"", "JAN", "FEB", "MAR", "APR", "MAY", "JUN", 
                                                  "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
    
    /**
     * Check if the given time matches the cron expression
     */
    public static boolean matches(String cronExpression, LocalDateTime dateTime) {
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            return false;
        }
        
        String[] parts = cronExpression.trim().split("\\s+");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid cron expression: " + cronExpression + 
                                             " (expected 5 parts: minute hour day month dayOfWeek)");
        }
        
        try {
            boolean minuteMatch = matchField(parts[0], dateTime.getMinute(), 0, 59);
            boolean hourMatch = matchField(parts[1], dateTime.getHour(), 0, 23);
            boolean dayMatch = matchField(parts[2], dateTime.getDayOfMonth(), 1, 31);
            boolean monthMatch = matchField(parts[3], dateTime.getMonthValue(), 1, 12);
            boolean dayOfWeekMatch = matchDayOfWeek(parts[4], dateTime.getDayOfWeek());
            
            // Special handling for day and dayOfWeek - if both are specified (not *), 
            // the condition is met if EITHER matches (OR logic)
            boolean dayCondition;
            if (!"*".equals(parts[2]) && !"*".equals(parts[4])) {
                dayCondition = dayMatch || dayOfWeekMatch;
            } else {
                dayCondition = dayMatch && dayOfWeekMatch;
            }
            
            return minuteMatch && hourMatch && dayCondition && monthMatch;
            
        } catch (Exception e) {
            throw new IllegalArgumentException("Error parsing cron expression: " + cronExpression, e);
        }
    }
    
    /**
     * Calculate the next execution time for a cron expression
     */
    public static LocalDateTime getNextExecution(String cronExpression, LocalDateTime from) {
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            return null;
        }
        
        // Start checking from the next minute
        LocalDateTime candidate = from.truncatedTo(ChronoUnit.MINUTES).plusMinutes(1);
        
        // Prevent infinite loops by limiting search to 4 years
        LocalDateTime maxTime = from.plusYears(4);
        
        while (candidate.isBefore(maxTime)) {
            if (matches(cronExpression, candidate)) {
                return candidate;
            }
            candidate = candidate.plusMinutes(1);
        }
        
        return null; // No valid execution time found within 4 years
    }
    
    /**
     * Get seconds until next execution
     */
    public static long getSecondsUntilNext(String cronExpression, LocalDateTime from) {
        LocalDateTime next = getNextExecution(cronExpression, from);
        if (next == null) {
            return -1;
        }
        return ChronoUnit.SECONDS.between(from, next);
    }
    
    /**
     * Get ticks until next execution (20 ticks = 1 second)
     */
    public static long getTicksUntilNext(String cronExpression, LocalDateTime from) {
        long seconds = getSecondsUntilNext(cronExpression, from);
        return seconds == -1 ? -1 : seconds * 20;
    }
    
    /**
     * Validate a cron expression
     */
    public static boolean isValid(String cronExpression) {
        try {
            // Try to parse and get next execution time
            LocalDateTime now = LocalDateTime.now();
            return getNextExecution(cronExpression, now) != null;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Match a cron field against a value
     */
    private static boolean matchField(String fieldExpression, int value, int min, int max) {
        if ("*".equals(fieldExpression)) {
            return true;
        }
        
        // Handle lists (e.g., "1,3,5")
        if (fieldExpression.contains(",")) {
            String[] values = fieldExpression.split(",");
            for (String val : values) {
                if (matchSingleField(val.trim(), value, min, max)) {
                    return true;
                }
            }
            return false;
        }
        
        return matchSingleField(fieldExpression, value, min, max);
    }
    
    /**
     * Match a single cron field value
     */
    private static boolean matchSingleField(String fieldExpression, int value, int min, int max) {
        // Handle step values (e.g., "*/5", "1-10/2")
        if (fieldExpression.contains("/")) {
            String[] parts = fieldExpression.split("/");
            if (parts.length != 2) {
                return false;
            }
            
            int step;
            try {
                step = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                return false;
            }
            
            if (step <= 0) {
                return false;
            }
            
            List<Integer> validValues = new ArrayList<>();
            
            if ("*".equals(parts[0])) {
                // */step
                for (int i = min; i <= max; i += step) {
                    validValues.add(i);
                }
            } else if (parts[0].contains("-")) {
                // range/step
                String[] range = parts[0].split("-");
                if (range.length != 2) {
                    return false;
                }
                
                try {
                    int start = Integer.parseInt(range[0]);
                    int end = Integer.parseInt(range[1]);
                    
                    for (int i = start; i <= end; i += step) {
                        if (i >= min && i <= max) {
                            validValues.add(i);
                        }
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            } else {
                // single value/step (e.g., "5/10" means "5,15,25,...")
                try {
                    int start = Integer.parseInt(parts[0]);
                    for (int i = start; i <= max; i += step) {
                        if (i >= min) {
                            validValues.add(i);
                        }
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
            
            return validValues.contains(value);
        }
        
        // Handle ranges (e.g., "1-5")
        if (fieldExpression.contains("-")) {
            String[] parts = fieldExpression.split("-");
            if (parts.length != 2) {
                return false;
            }
            
            try {
                int start = Integer.parseInt(parts[0]);
                int end = Integer.parseInt(parts[1]);
                return value >= start && value <= end;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
        // Handle single numeric value
        try {
            int expectedValue = Integer.parseInt(fieldExpression);
            return value == expectedValue;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Match day of week with support for day names
     */
    private static boolean matchDayOfWeek(String fieldExpression, DayOfWeek dayOfWeek) {
        if ("*".equals(fieldExpression)) {
            return true;
        }
        
        // Convert day of week to cron format (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
        int cronDayOfWeek = dayOfWeek.getValue() % 7; // Convert from ISO (1=Mon) to cron (0=Sun)
        
        // Replace day names with numbers
        String normalizedExpression = fieldExpression.toUpperCase();
        for (int i = 0; i < DAY_NAMES.length; i++) {
            normalizedExpression = normalizedExpression.replace(DAY_NAMES[i], String.valueOf(i));
        }
        
        return matchField(normalizedExpression, cronDayOfWeek, 0, 6);
    }
    
    /**
     * Get a human-readable description of the cron expression
     */
    public static String describe(String cronExpression) {
        if (cronExpression == null || cronExpression.trim().isEmpty()) {
            return "Invalid cron expression";
        }
        
        String[] parts = cronExpression.trim().split("\\s+");
        if (parts.length != 5) {
            return "Invalid cron expression format";
        }
        
        StringBuilder description = new StringBuilder();
        
        // Common patterns
        if ("0 0 * * *".equals(cronExpression)) {
            return "Daily at midnight";
        } else if ("0 12 * * *".equals(cronExpression)) {
            return "Daily at noon";
        } else if ("0 0 * * 0".equals(cronExpression)) {
            return "Weekly on Sunday at midnight";
        } else if ("0 0 1 * *".equals(cronExpression)) {
            return "Monthly on the 1st at midnight";
        }
        
        // Build description
        description.append("At ");
        
        if (!"*".equals(parts[1])) {
            description.append(parts[1]).append(":");
        }
        
        if (!"*".equals(parts[0])) {
            if (description.toString().endsWith(":")) {
                description.append(String.format("%02d", Integer.parseInt(parts[0])));
            } else {
                description.append("minute ").append(parts[0]);
            }
        } else if (description.toString().endsWith(":")) {
            description.append("00");
        }
        
        if (!"*".equals(parts[2])) {
            description.append(" on day ").append(parts[2]);
        }
        
        if (!"*".equals(parts[3])) {
            description.append(" of month ").append(parts[3]);
        }
        
        if (!"*".equals(parts[4])) {
            description.append(" on ").append(parts[4]);
        }
        
        return description.toString();
    }
}
