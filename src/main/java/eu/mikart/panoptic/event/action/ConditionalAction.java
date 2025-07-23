package eu.mikart.panoptic.event.action;

import java.util.List;

import org.bukkit.event.Event;

import eu.mikart.panoptic.event.Action;
import eu.mikart.panoptic.event.Condition;

/**
 * An action that conditionally executes other actions based on runtime conditions.
 * This allows for complex decision-making within action execution chains.
 * 
 * @author ArikSquad
 * @since 1.1.0
 */
public class ConditionalAction implements Action {
    
    private final List<Condition> conditions;
    private final List<Action> actionsIfTrue;
    private final List<Action> actionsIfFalse;
    
    /**
     * Creates a new ConditionalAction with actions for both true and false outcomes.
     * 
     * @param conditions The conditions to evaluate
     * @param actionsIfTrue Actions to execute if all conditions are true
     * @param actionsIfFalse Actions to execute if any condition is false
     */
    public ConditionalAction(List<Condition> conditions, List<Action> actionsIfTrue, List<Action> actionsIfFalse) {
        this.conditions = conditions;
        this.actionsIfTrue = actionsIfTrue;
        this.actionsIfFalse = actionsIfFalse;
    }
    
    /**
     * Creates a new ConditionalAction with actions only for true outcome.
     * 
     * @param conditions The conditions to evaluate
     * @param actionsIfTrue Actions to execute if all conditions are true
     */
    public ConditionalAction(List<Condition> conditions, List<Action> actionsIfTrue) {
        this(conditions, actionsIfTrue, List.of());
    }
    
    @Override
    public void execute(Event event) {
        boolean allConditionsMet = conditions.stream().allMatch(condition -> condition.evaluate(event));
        
        if (allConditionsMet) {
            actionsIfTrue.forEach(action -> action.execute(event));
        } else {
            actionsIfFalse.forEach(action -> action.execute(event));
        }
    }
}
