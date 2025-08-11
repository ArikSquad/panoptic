package eu.mikart.panoptic.event.action.impl;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.event.action.Action;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import java.util.HashMap;
import java.util.Map;

public class JavaScriptAction implements Action {
    private final String script;
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

    public JavaScriptAction(String script) {
        this.script = script;
    }

    @Override
    public void execute(Event event) {
        Player player = ripPlayerOffEvent(event);
        Map<String, Object> ctx = new HashMap<>();
        ctx.put("plugin", PanopticPlugin.getInstance());
        ctx.put("event", event);
        if (player != null) ctx.put("player", player);
        try {
            engine.eval(script, new SimpleBindings(ctx));
        } catch (Exception e) {
            PanopticPlugin.getInstance().getLogger().warning("[JavaScriptAction] Script error: " + e);
        }
    }
}
