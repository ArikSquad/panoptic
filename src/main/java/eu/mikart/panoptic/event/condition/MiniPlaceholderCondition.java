package eu.mikart.panoptic.event.condition;

import eu.mikart.panoptic.event.Condition;
import eu.mikart.panoptic.event.MiniPlaceholderContextParser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MiniPlaceholderCondition implements Condition {
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile(
            "(.+?)(>=|<=|!=|=|>|<)(.+)");
    private final String expression;

    public MiniPlaceholderCondition(String expression) {
        this.expression = expression;
    }

    private static Double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean compareNumbers(double left, double right, String op) {
        return switch (op) {
            case ">" -> left > right;
            case "<" -> left < right;
            case ">=" -> left >= right;
            case "<=" -> left <= right;
            case "!=" -> left != right;
            case "=" -> left == right;
            default -> false;
        };
    }

    private static boolean compareStrings(String left, String right, String op) {
        return switch (op) {
            case "=" -> left.equals(right);
            case "!=" -> !left.equals(right);
            default -> false;
        };
    }

    @Override
    public boolean evaluate(Event event) {
        Player player = ripPlayerOffEvent(event);
        if (player == null) return false;

        String parsed = MiniPlaceholderContextParser.parse(expression, player);
        Matcher matcher = EXPRESSION_PATTERN.matcher(parsed);
        if (!matcher.matches()) return false;
        String left = matcher.group(1).trim();
        String operator = matcher.group(2);
        String right = matcher.group(3).trim();
        Double leftNum = tryParseDouble(left);
        Double rightNum = tryParseDouble(right);
        if (leftNum != null && rightNum != null) {
            return compareNumbers(leftNum, rightNum, operator);
        } else {
            return compareStrings(left, right, operator);
        }
    }
}
