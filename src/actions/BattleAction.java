package src.actions;

import src.entities.Entity;
import src.game.Battle;
import src.game.GameRandom;

import java.util.List;
import java.util.Random;


/**
 * Represents an abstract battle action that an entity can perform.
 */
public abstract class BattleAction {
    protected final String name;
    protected final Entity actor;
    protected final Random random;
    private int disabledTurns = 0;

    /**
     * Constructs a BattleAction with a specified name and actor.
     *
     * @param name  The name of the action.
     * @param actor The entity performing the action.
     * @throws IllegalArgumentException if the actor is null.
     */

    public BattleAction(String name, Entity actor) {
        this.actor = actor;
        this.name = name;

        if (actor == null) {
            throw new IllegalArgumentException("Actor cannot be null.");
        }

        this.random = GameRandom.getInstance();
    }

    /**
     * Retrieves the name of the battle action.
     *
     * @return The action name.
     */
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
     *
     * @param battle The current battle instance.
     * @return A list of valid target entities.
     */
    public abstract List<Entity> getValidTargets(Battle battle);


    /**
     * Checks if the action is currently disabled.
     *
     * @return True if the action is disabled, false otherwise.
     */
    public boolean isDisabled() {
        return disabledTurns > 0;
    }

    /**
     * Disables the action for a specified number of turns.
     *
     * @param turns The number of turns the action remains disabled.
     */

    public void disable(int turns) {
        disabledTurns = turns;
    }

    /**
     * Reduces the disabled turns counter at the end of each turn.
     */

    public void endTurn() {
        if (disabledTurns > 0) {
            disabledTurns--;
        }
    }
}
