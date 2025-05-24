package eu.mikart.panoptic.event.condition;

import eu.mikart.panoptic.event.Condition;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityTypeCondition implements Condition {
    private final String entityType;

    public EntityTypeCondition(String entityType) {
        this.entityType = entityType;
    }

    @Override
    public boolean evaluate(Event event) {
        if (!(event instanceof EntityDeathEvent)) {
            return true;
        }
        return ((EntityDeathEvent) event).getEntity().getType().name().equalsIgnoreCase(entityType);
    }
}

