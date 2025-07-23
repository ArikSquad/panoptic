package eu.mikart.panoptic.metrics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import eu.mikart.panoptic.PanopticPlugin;

/**
 * Professional metrics collection system for tracking plugin performance and usage.
 * Provides thread-safe counters and performance monitoring for production environments.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public class EventMetrics {
    
    private final PanopticPlugin plugin;
    private final Map<String, LongAdder> eventCounts = new ConcurrentHashMap<>();
    private final Map<String, LongAdder> actionExecutions = new ConcurrentHashMap<>();
    private final Map<String, LongAdder> conditionEvaluations = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> lastEventTime = new ConcurrentHashMap<>();
    private final AtomicLong totalProcessingTime = new AtomicLong(0);
    
    /**
     * Creates a new EventMetrics instance.
     * 
     * @param plugin The plugin instance for logging and scheduling
     */
    public EventMetrics(PanopticPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
     * Records an event occurrence for metrics tracking.
     * 
     * @param eventType The type of event that occurred
     */
    public void recordEvent(String eventType) {
        eventCounts.computeIfAbsent(eventType, k -> new LongAdder()).increment();
        lastEventTime.put(eventType, new AtomicLong(System.currentTimeMillis()));
    }
    
    /**
     * Records an action execution for metrics tracking.
     * 
     * @param actionType The type of action that was executed
     */
    public void recordActionExecution(String actionType) {
        actionExecutions.computeIfAbsent(actionType, k -> new LongAdder()).increment();
    }
    
    /**
     * Records a condition evaluation for metrics tracking.
     * 
     * @param conditionType The type of condition that was evaluated
     */
    public void recordConditionEvaluation(String conditionType) {
        conditionEvaluations.computeIfAbsent(conditionType, k -> new LongAdder()).increment();
    }
    
    /**
     * Records processing time for performance monitoring.
     * 
     * @param processingTimeNanos The processing time in nanoseconds
     */
    public void recordProcessingTime(long processingTimeNanos) {
        totalProcessingTime.addAndGet(processingTimeNanos);
    }
    
    /**
     * Gets the total count for a specific event type.
     * 
     * @param eventType The event type to query
     * @return The total count of events
     */
    public long getEventCount(String eventType) {
        return eventCounts.getOrDefault(eventType, new LongAdder()).sum();
    }
    
    /**
     * Gets the total count for a specific action type.
     * 
     * @param actionType The action type to query
     * @return The total count of action executions
     */
    public long getActionExecutionCount(String actionType) {
        return actionExecutions.getOrDefault(actionType, new LongAdder()).sum();
    }
    
    /**
     * Gets the total processing time in milliseconds.
     * 
     * @return The cumulative processing time
     */
    public long getTotalProcessingTimeMs() {
        return totalProcessingTime.get() / 1_000_000;
    }
    
    /**
     * Generates a comprehensive metrics report.
     * 
     * @return A formatted string containing all metrics data
     */
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== Panoptic Event Metrics Report ===\n");
        
        // Event counts
        report.append("\nEvent Occurrences:\n");
        eventCounts.entrySet().stream()
            .sorted(Map.Entry.<String, LongAdder>comparingByValue((a, b) -> Long.compare(b.sum(), a.sum())))
            .forEach(entry -> report.append(String.format("  %s: %d\n", entry.getKey(), entry.getValue().sum())));
        
        // Action executions
        report.append("\nAction Executions:\n");
        actionExecutions.entrySet().stream()
            .sorted(Map.Entry.<String, LongAdder>comparingByValue((a, b) -> Long.compare(b.sum(), a.sum())))
            .forEach(entry -> report.append(String.format("  %s: %d\n", entry.getKey(), entry.getValue().sum())));
        
        // Condition evaluations
        report.append("\nCondition Evaluations:\n");
        conditionEvaluations.entrySet().stream()
            .sorted(Map.Entry.<String, LongAdder>comparingByValue((a, b) -> Long.compare(b.sum(), a.sum())))
            .forEach(entry -> report.append(String.format("  %s: %d\n", entry.getKey(), entry.getValue().sum())));
        
        // Performance metrics
        report.append("\nPerformance Metrics:\n");
        report.append(String.format("  Total Processing Time: %d ms\n", getTotalProcessingTimeMs()));
        report.append(String.format("  Average Event Processing: %.2f ms\n", getAverageProcessingTime()));
        
        return report.toString();
    }
    
    /**
     * Resets all metrics counters.
     */
    public void reset() {
        eventCounts.clear();
        actionExecutions.clear();
        conditionEvaluations.clear();
        lastEventTime.clear();
        totalProcessingTime.set(0);
    }
    
    /**
     * Calculates the average processing time per event.
     * 
     * @return The average processing time in milliseconds
     */
    private double getAverageProcessingTime() {
        long totalEvents = eventCounts.values().stream().mapToLong(LongAdder::sum).sum();
        if (totalEvents == 0) return 0.0;
        return (double) getTotalProcessingTimeMs() / totalEvents;
    }
    
}
