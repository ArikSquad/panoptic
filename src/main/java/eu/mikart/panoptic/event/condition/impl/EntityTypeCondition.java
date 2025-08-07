package eu.mikart.panoptic.event.condition.impl;

import eu.mikart.panoptic.event.condition.Condition;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityTypeCondition implements Condition {
    private final String entityType;

    public EntityTypeCondition(String entityType) {
        this.entityType = entityType;
    }

    @Override
    public boolean evaluate(Event event) {
        if (event instanceof EntityDamageByEntityEvent damageByEntityEvent) {
            return damageByEntityEvent.getDamager().getType().name().equalsIgnoreCase(entityType);
        }
        if (!(event instanceof EntityDeathEvent deathEvent)) {
            return true;
        }
        return deathEvent.getEntity().getType().name().equalsIgnoreCase(entityType);
    }
}

