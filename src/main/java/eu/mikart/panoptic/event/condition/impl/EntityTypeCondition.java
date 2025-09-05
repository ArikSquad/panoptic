package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class EntityTypeCondition implements Condition {
    private final Set<String> allowed;

    public EntityTypeCondition(String entityType) {
        this(entityType == null ? List.of() : List.of(entityType));
    }

    public EntityTypeCondition(List<String> entityTypes) {
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
            return allowed.contains(damageByEntityEvent.getDamager().getType().name().toUpperCase(Locale.ROOT));
        }
        if (!(event instanceof EntityDeathEvent deathEvent)) {
            return true;
        }
        return allowed.contains(deathEvent.getEntity().getType().name().toUpperCase(Locale.ROOT));
    }
}
