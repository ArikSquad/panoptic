package eu.mikart.panoptic.event.registry;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.ActionData;
import eu.mikart.panoptic.event.action.CancelAction;
import eu.mikart.panoptic.event.action.RunCommandAsConsoleAction;
import eu.mikart.panoptic.event.action.RunCommandAsPlayerAction;
import eu.mikart.panoptic.event.action.value.StringActionValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ActionRegistry {
    private static final Map<String, Function<ActionData, Action>> registry = new HashMap<>();

    static {
        register("cancel", data -> new CancelAction());
        register("run_command_as_player", data -> new RunCommandAsPlayerAction(((StringActionValue) data.value()).value()));
        register("run_command_as_console", data -> new RunCommandAsConsoleAction(((StringActionValue) data.value()).value()));
    }

    public static void register(String type, Function<ActionData, Action> factory) {
        registry.put(type.toLowerCase(), factory);
    }

    public static Action create(ActionData data) {
        Function<ActionData, Action> factory = registry.get(data.type().toLowerCase());
        if (factory == null) throw new IllegalArgumentException("Unknown action type: " + data.type());
        return factory.apply(data);
    }
}

