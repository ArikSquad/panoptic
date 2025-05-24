package eu.mikart.panoptic.event.registry;

import eu.mikart.panoptic.event.Condition;
import eu.mikart.panoptic.event.ConditionData;
import eu.mikart.panoptic.event.condition.MiniPlaceholderCondition;
import eu.mikart.panoptic.event.condition.PlaceholderCondition;
import eu.mikart.panoptic.event.condition.SneakingCondition;
import eu.mikart.panoptic.event.condition.params.StringConditionParams;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ConditionRegistry {
    private static final Map<String, Function<ConditionData, Condition>> registry = new HashMap<>();

    static {
        register("sneaking", data -> new SneakingCondition());
        register("placeholder", data -> new PlaceholderCondition(((StringConditionParams) data.params()).value()));
        register("miniplaceholder", data -> new MiniPlaceholderCondition(((StringConditionParams) data.params()).value()));
    }

    public static void register(String type, Function<ConditionData, Condition> factory) {
        registry.put(type.toLowerCase(), factory);
    }

    public static Condition create(ConditionData data) {
        Function<ConditionData, Condition> factory = registry.get(data.type().toLowerCase());
        if (factory == null) throw new IllegalArgumentException("Unknown condition type: " + data.type());
        return factory.apply(data);
    }
}

