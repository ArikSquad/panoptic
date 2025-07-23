package eu.mikart.panoptic.config;

import java.util.Map;

import de.exlll.configlib.Serializer;
import eu.mikart.panoptic.event.condition.params.BooleanConditionParams;
import eu.mikart.panoptic.event.condition.params.ConditionParams;
import eu.mikart.panoptic.event.condition.params.DoubleConditionParams;
import eu.mikart.panoptic.event.condition.params.StringConditionParams;

/**
 * Serializer for condition parameters to support YAML configuration serialization.
 * Handles conversion between ConditionParams objects and their Map representations.
 * 
 * @author ArikSquad
 * @since 1.0.0
 */
public class ConditionParamsSerializer implements Serializer<ConditionParams, Map<String, Object>> {
    
    @Override
    public Map<String, Object> serialize(ConditionParams params) {
        if (params instanceof BooleanConditionParams(Boolean value)) {
            return Map.of("type", "boolean", "value", value);
        } else if (params instanceof StringConditionParams(String value)) {
            return Map.of("type", "string", "value", value);
        } else if (params instanceof DoubleConditionParams(Double value)) {
            return Map.of("type", "double", "value", value);
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
            case "double" -> new DoubleConditionParams(((Number) value).doubleValue());
            default -> throw new IllegalArgumentException("Unknown ConditionParams type: " + type);
        };
    }
}

