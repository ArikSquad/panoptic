package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.PanopticPlugin;
import eu.mikart.panoptic.event.condition.Condition;
import eu.mikart.panoptic.script.AviatorScriptEngine;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;

public class AviatorCondition implements Condition {
    private final String script;

    public AviatorCondition(String script) {
        this.script = script;
    }

    @Override
    public boolean evaluate(Event event) {
        Player player = ripPlayerOffEvent(event);
        Map<String, Object> env = new HashMap<>();
        env.put("plugin", PanopticPlugin.getInstance());
        env.put("event", event);
        if (player != null) env.put("player", player);
        // Add more player context if available
        if (player != null) {
            env.put("health", player.getHealth());
            env.put("maxHealth", player.getMaxHealth());
            env.put("location", player.getLocation());
            env.put("world", player.getWorld().getName());
            env.put("uuid", player.getUniqueId().toString());
            env.put("name", player.getName());
            env.put("isOp", player.isOp());
            env.put("isSneaking", player.isSneaking());
            env.put("isSprinting", player.isSprinting());
            env.put("isFlying", player.isFlying());
            env.put("gamemode", player.getGameMode().name());
            env.put("exp", player.getExp());
            env.put("level", player.getLevel());
            env.put("food", player.getFoodLevel());
            env.put("saturation", player.getSaturation());
            env.put("inventory", player.getInventory());
        }
        // Add event type
        env.put("eventType", event.getEventName());
        PanopticPlugin.getInstance().debug("[Aviator] Evaluating condition script: " + script);
        PanopticPlugin.getInstance().debug("[Aviator] Env: " + env);
        try {
            Object result = AviatorScriptEngine.executeInline(script, env);
            PanopticPlugin.getInstance().debug("[Aviator] Condition result: " + result);
            if (result instanceof Boolean b) return b;
            if (result instanceof Number n) return n.doubleValue() != 0d;
            if (result instanceof CharSequence cs) return !cs.isEmpty();
            return result != null;
        } catch (Throwable t) {
            PanopticPlugin.getInstance().getLogger().warning("[Aviator] Condition script error: " + t);
            return false;
        }
    }
}
