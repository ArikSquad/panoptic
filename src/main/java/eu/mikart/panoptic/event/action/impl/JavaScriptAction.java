package eu.mikart.panoptic.event.action.impl;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.event.action.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import java.util.HashMap;
import java.util.Map;

public record JavaScriptAction(String script) implements Action {

    private static final Engine ENGINE = Engine.newBuilder()
            .option("engine.WarnInterpreterOnly", "false")
            .build();

    @Override
    public void execute(Event event) {
        Player player = ripPlayerOffEvent(event);
        Map<String, Object> ctx = new HashMap<>();
        ctx.put("plugin", PanopticPlugin.getInstance());
        ctx.put("event", event);
        if (player != null) ctx.put("player", player);

        try (Context jsContext = Context.newBuilder("js")
                .engine(ENGINE)
                .allowAllAccess(true)
                .build()) {

            Value bindings = jsContext.getBindings("js");
            for (Map.Entry<String, Object> entry : ctx.entrySet()) {
                bindings.putMember(entry.getKey(), entry.getValue());
            }

            jsContext.eval("js", script);
        } catch (Exception e) {
            PanopticPlugin.getInstance().getLogger().warning("[JavaScriptAction] Script error: " + e);
        }
    }

}
