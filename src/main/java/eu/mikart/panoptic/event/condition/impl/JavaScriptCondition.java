package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import java.util.HashMap;
import java.util.Map;

public class JavaScriptCondition implements Condition {
    private final String script;

    private static final Engine ENGINE = Engine.newBuilder()
            .option("engine.WarnInterpreterOnly", "false")
            .build();

    public JavaScriptCondition(String script) {
        this.script = script;
    }

    @Override
    public boolean evaluate(Event event) {
        Player player = ripPlayerOffEvent(event);
        Map<String, Object> ctx = new HashMap<>();
        ctx.put("plugin", PanopticPlugin.getInstance());
        ctx.put("event", event);
        if (player != null) ctx.put("player", player);

        PanopticPlugin.getInstance().debug("[JavaScriptCondition] Evaluating condition script: " + script);
        try (Context jsContext = Context.newBuilder("js")
                .engine(ENGINE)
                .allowAllAccess(true)
                .build()) {

            Value bindings = jsContext.getBindings("js");
            for (Map.Entry<String, Object> entry : ctx.entrySet()) {
                bindings.putMember(entry.getKey(), entry.getValue());
            }

            Value result = jsContext.eval("js", script);
            if (result == null || result.isNull()) return false;
            if (result.isBoolean()) return result.asBoolean();
            if (result.fitsInDouble()) return result.asDouble() != 0d;
            if (result.isString()) return !result.asString().isEmpty();
            return true;
        } catch (Throwable t) {
            PanopticPlugin.getInstance().getLogger().warning("[JavaScriptCondition] Script error: " + t);
            return false;
        }
    }
}

