package eu.mikart.panoptic.config;

import de.exlll.configlib.Serializer;
import eu.mikart.panoptic.event.condition.params.BooleanConditionParams;
import eu.mikart.panoptic.event.condition.params.ConditionParams;
import eu.mikart.panoptic.event.condition.params.StringConditionParams;

import java.util.Map;

public class ConditionParamsSerializer implements Serializer<ConditionParams, Map<String, Object>> {
    @Override
    public Map<String, Object> serialize(ConditionParams params) {
        if (params instanceof BooleanConditionParams(Boolean value)) {
            return Map.of("type", "boolean", "value", value);
        } else if (params instanceof StringConditionParams(String value)) {
            return Map.of("type", "string", "value", value);
        }
        throw new IllegalArgumentException("Unknown ConditionParams type: " + params.getClass());
    }

    @Override
    public ConditionParams deserialize(Map<String, Object> map) {
        String type = (String) map.get("type");
        Object value = map.get("value");
        return switch (type) {
            case "boolean" -> new BooleanConditionParams((Boolean) value);
            case "string" -> new StringConditionParams((String) value);
            default -> throw new IllegalArgumentException("Unknown ConditionParams type: " + type);
        };
    }
}

