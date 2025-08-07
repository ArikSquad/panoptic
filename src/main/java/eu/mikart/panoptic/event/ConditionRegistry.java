package eu.mikart.panoptic.event;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import eu.mikart.panoptic.event.condition.Condition;
import eu.mikart.panoptic.event.condition.ConditionData;
import eu.mikart.panoptic.event.condition.impl.*;
import eu.mikart.panoptic.event.condition.params.DoubleConditionParams;
import eu.mikart.panoptic.event.condition.params.StringConditionParams;

/**
 * Registry for managing condition types and their factory methods.
 * This registry allows for dynamic creation of conditions based on configuration data.
 *
 * @author ArikSquad
 * @since 1.0.0
 */
public class ConditionRegistry {
    private static final Map<String, Function<ConditionData, Condition>> registry = new HashMap<>();

    static {
        register("sneaking", data -> new SneakingCondition());
        register("placeholder", data -> new PlaceholderCondition(((StringConditionParams) data.params()).value()));
        register("miniplaceholder", data -> new MiniPlaceholderCondition(((StringConditionParams) data.params()).value()));
        register("entity_type", data -> new EntityTypeCondition(((StringConditionParams) data.params()).value()));
        register("random", data -> new RandomCondition(((DoubleConditionParams) data.params()).value()));
        register("block_location", data -> new BlockLocationCondition(((StringConditionParams) data.params()).value()));
        register("block_at_location", data -> new BlockAtLocationCondition(((StringConditionParams) data.params()).value()));
        register("world", data -> new WorldCondition(((StringConditionParams) data.params()).value()));
    }

    /**
     * Registers a new condition type with its factory method.
     *
     * @param type The unique identifier for the condition type
     * @param factory The factory method that creates instances of the condition
     */
    public static void register(String type, Function<ConditionData, Condition> factory) {
        registry.put(type.toLowerCase(), factory);
    }

    /**
     * Creates a condition instance from configuration data.
     *
     * @param data The condition configuration data
     * @return A new condition instance
     * @throws IllegalArgumentException if the condition type is unknown
     */
    public static Condition create(ConditionData data) {
        Function<ConditionData, Condition> factory = registry.get(data.type().toLowerCase());
        if (factory == null) throw new IllegalArgumentException("Unknown condition type: " + data.type());
        return factory.apply(data);
    }
}

