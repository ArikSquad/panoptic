package eu.mikart.panoptic.config;

import java.util.List;
import java.util.Map;

import de.exlll.configlib.Serializer;
import eu.mikart.panoptic.event.ConditionRegistry;
import eu.mikart.panoptic.event.condition.ConditionData;
import eu.mikart.panoptic.event.condition.params.BooleanConditionParams;
import eu.mikart.panoptic.event.condition.params.ConditionParams;
import eu.mikart.panoptic.event.condition.params.DoubleConditionParams;
import eu.mikart.panoptic.event.condition.params.ListStringConditionParams;
import eu.mikart.panoptic.event.condition.params.StringConditionParams;

/**
 * Serializer for ConditionData to handle various parameter types.
 *
 * @since 1.3.2
 */
public class ConditionDataSerializer implements Serializer<ConditionData, Map<String, Object>> {

    @Override
    public Map<String, Object> serialize(ConditionData data) {
        Object serializedParams = data.params() == null ? null : switch (data.params()) {
            case BooleanConditionParams(Boolean b) -> b;
            case StringConditionParams(String s) -> s;
            case DoubleConditionParams(Double d) -> d;
            case ListStringConditionParams(List<String> list) -> list;
		};

        if (serializedParams == null) {
            return Map.of("type", data.type());
        }
        return Map.of("type", data.type(), "value", serializedParams);
    }

    @Override
    public ConditionData deserialize(Map<String, Object> map) {
        Object typeObj = map.get("type");
        if (!(typeObj instanceof String type)) {
            throw new IllegalArgumentException("Condition 'type' must be a string");
        }

        Object rawParams = map.get("params");
        if (rawParams == null && map.containsKey("value")) {
            rawParams = map.get("value");
        } else if (rawParams == null && map.containsKey("values")) {
            rawParams = map.get("values");
        }

        if (rawParams == null) {
            return new ConditionData(type, null);
        }

        ConditionParams params = toParams(type, rawParams);
        return new ConditionData(type, params);
    }

    private ConditionParams toParams(String type, Object raw) {
        boolean allowsList = ConditionRegistry.allowsLists(type);

        if (allowsList && raw instanceof List<?> list) {
            return new ListStringConditionParams(list.stream().map(Object::toString).toList());
        }

		// hardcoded for now
        if (type.equalsIgnoreCase("random")) {
            if (raw instanceof Number n) {
                return new DoubleConditionParams(n.doubleValue());
            }
            try {
                return new DoubleConditionParams(Double.parseDouble(raw.toString()));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Condition '" + type + "' expects a number param");
            }
        }

        if (raw instanceof Boolean b) {
            return new BooleanConditionParams(b);
        }

        if (raw instanceof List<?> list) {
            return new ListStringConditionParams(list.stream().map(Object::toString).toList());
        }

        return new StringConditionParams(raw == null ? null : raw.toString());
    }
}