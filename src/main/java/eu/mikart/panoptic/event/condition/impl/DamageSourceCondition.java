package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class DamageSourceCondition implements Condition {
    private final Set<String> allowed;

    public DamageSourceCondition(String entityType) {
        this(entityType == null ? List.of() : List.of(entityType));
    }

    public DamageSourceCondition(List<String> entityTypes) {
        this.allowed = new HashSet<>();
        if (entityTypes != null) {
            for (String s : entityTypes) {
                if (s == null) continue;
                allowed.add(s.toUpperCase(Locale.ROOT));
            }
        }
    }

    @Override
    public boolean evaluate(Event event) {
        if (allowed.isEmpty()) return false;
        if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
            return allowed.contains(damageByEntityEvent.getDamageSource().getDamageType().toString().toUpperCase(Locale.ROOT));
        }
        if (!(event instanceof EntityDeathEvent deathEvent)) {
            return true;
        }
        return allowed.contains(deathEvent.getDamageSource().getDamageType().toString().toUpperCase(Locale.ROOT));
    }
}
