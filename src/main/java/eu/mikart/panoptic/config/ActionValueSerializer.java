package eu.mikart.panoptic.config;

import de.exlll.configlib.Serializer;
import eu.mikart.panoptic.event.action.value.ActionValue;
import eu.mikart.panoptic.event.action.value.BooleanActionValue;
import eu.mikart.panoptic.event.action.value.StringActionValue;

import java.util.Map;

public class ActionValueSerializer implements Serializer<ActionValue, Object> {
    @Override
    public Object serialize(ActionValue value) {
        if (value instanceof BooleanActionValue(Boolean value1)) {
            return Map.of("type", "boolean", "value", value1);
        } else if (value instanceof StringActionValue(String value1)) {
            return value1;
        }
        throw new IllegalArgumentException("Unknown ActionValue type: " + value.getClass());
    }

    @Override
    public ActionValue deserialize(Object obj) {
        if (obj instanceof Map<?, ?> map) {
            String type = (String) map.get("type");
            Object value = map.get("value");
            return switch (type) {
                case "boolean" -> new BooleanActionValue((Boolean) value);
                case "string" -> new StringActionValue((String) value);
                default -> throw new IllegalArgumentException("Unknown ActionValue type: " + type);
            };
        } else if (obj instanceof String str) {
            return new StringActionValue(str);
        }
        throw new IllegalArgumentException("Cannot deserialize ActionValue from: " + obj);
    }
}
