package eu.mikart.panoptic.config;

import java.util.List;
import java.util.Map;

import de.exlll.configlib.Serializer;
import eu.mikart.panoptic.event.condition.params.BooleanConditionParams;
import eu.mikart.panoptic.event.condition.params.ConditionParams;
import eu.mikart.panoptic.event.condition.params.DoubleConditionParams;
import eu.mikart.panoptic.event.condition.params.ListStringConditionParams;
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
        } else if (params instanceof ListStringConditionParams(List<String> values)) {
            return Map.of("type", "list_string", "value", values);
        }
        throw new IllegalArgumentException("Unknown ConditionParams type: " + params.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ConditionParams deserialize(Map<String, Object> map) {
        String type = (String) map.get("type");
        Object value = map.get("value");
        if (value instanceof List<?> list) {
            return new ListStringConditionParams(list.stream().map(Object::toString).toList());
        }
        return switch (type) {
            case "boolean" -> new BooleanConditionParams((Boolean) value);
            case "string" -> new StringConditionParams((String) value);
            case "double" -> new DoubleConditionParams(((Number) value).doubleValue());
            case "list_string", "string_list", "list" -> {
                List<String> values = value instanceof List<?> l ? (List<String>) l : List.of();
                yield new ListStringConditionParams(values);
            }
            default -> throw new IllegalArgumentException("Unknown ConditionParams type: " + type);
        };
    }
}
