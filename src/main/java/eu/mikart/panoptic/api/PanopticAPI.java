package eu.mikart.panoptic.api;

import java.util.function.Function;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.ActionData;
import eu.mikart.panoptic.event.Condition;
import eu.mikart.panoptic.event.ConditionData;
import eu.mikart.panoptic.event.registry.ActionRegistry;
import eu.mikart.panoptic.event.registry.ConditionRegistry;

/**
 * Public API for extending Panoptic with custom conditions and actions.
 * This API allows other plugins to register their own event processing logic
 * without modifying the core Panoptic plugin.
 * 
 * <p>Example usage:</p>
 * <pre>{@code
 * // Register a custom condition
 * PanopticAPI.registerCondition("my_condition", data -> new MyCustomCondition(data));
 * 
 * // Register a custom action
 * PanopticAPI.registerAction("my_action", data -> new MyCustomAction(data));
 * }</pre>
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public final class PanopticAPI {
    
    private PanopticAPI() {
        
    }
    
    /**
     * Registers a custom condition type with Panoptic.
     * The condition will be available for use in all event configurations.
     * 
     * @param type The unique identifier for the condition type (case-insensitive)
     * @param factory Factory function that creates condition instances from configuration data
     * @throws IllegalArgumentException if the type is already registered
     */
    public static void registerCondition(String type, Function<ConditionData, Condition> factory) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Condition type cannot be null or empty");
        }
        
        if (factory == null) {
            throw new IllegalArgumentException("Condition factory cannot be null");
        }
        
        ConditionRegistry.register(type, factory);
    }
    
    /**
     * Registers a custom action type with Panoptic.
     * The action will be available for use in all event configurations.
     * 
     * @param type The unique identifier for the action type (case-insensitive)
     * @param factory Factory function that creates action instances from configuration data
     * @throws IllegalArgumentException if the type is already registered
     */
    public static void registerAction(String type, Function<ActionData, Action> factory) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Action type cannot be null or empty");
        }
        
        if (factory == null) {
            throw new IllegalArgumentException("Action factory cannot be null");
        }
        
        ActionRegistry.register(type, factory);
    }
    
    /**
     * Creates a condition instance from configuration data.
     * This is useful for testing custom conditions or creating them programmatically.
     * 
     * @param data The condition configuration data
     * @return A new condition instance
     * @throws IllegalArgumentException if the condition type is unknown
     */
    public static Condition createCondition(ConditionData data) {
        return ConditionRegistry.create(data);
    }
    
    /**
     * Creates an action instance from configuration data.
     * This is useful for testing custom actions or creating them programmatically.
     * 
     * @param data The action configuration data
     * @return A new action instance
     * @throws IllegalArgumentException if the action type is unknown
     */
    public static Action createAction(ActionData data) {
        return ActionRegistry.create(data);
    }
}
