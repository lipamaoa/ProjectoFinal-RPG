package src.actions;

import src.entities.Entity;
import src.game.Battle;
import java.util.List;

public abstract class BattleAction {
    protected String name;
    protected Entity actor;
    private int disabledTurns = 0;

    public BattleAction(String name, Entity actor) {
        this.actor = actor;
        this.name = name;

        if (actor == null) {
            throw new IllegalArgumentException("Actor cannot be null.");
        }
    }

    public String getName() {
        return this.name;
    }

    /**
     * Executes the action in battle.
     * 
     * @param battle The current battle instance.
     * @param target The target of the action.
     * @return True if the action was successful, false otherwise.
     */
    public abstract boolean execute(Battle battle, Entity target);

    /**
     * Defines what entities can be targeted by this action.
     */
    public abstract List<Entity> getValidTargets(Battle battle);

    public boolean isDisabled() {
        return disabledTurns > 0;
    }

    public void disable(int turns) {
        disabledTurns = turns;
    }

    public void endTurn() {
        if (disabledTurns > 0) {
            disabledTurns--;
        }
    };
}
