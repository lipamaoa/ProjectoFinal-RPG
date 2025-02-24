package src.actions;

import src.entities.Entity;
import src.game.Battle;
import src.status.Burning;

import java.util.List;

/**
 * Represents an incendiary strike action that applies a burning status effect to all enemies.
 */
public class IncendiaryStrikeAction extends BattleAction {

    /**
     * Constructs an IncendiaryStrikeAction for a given entity.
     *
     * @param actor The entity performing the incendiary strike.
     */

    public IncendiaryStrikeAction(Entity actor) {
        super("Incendiary Strike", actor);
    }

    /**
     * Executes the incendiary strike action, applying a burning effect to all enemies.
     *
     * @param battle The current battle instance.
     * @param target The target entity (not used as the action affects all enemies).
     * @return True if the action was executed successfully.
     */

    @Override
    public boolean execute(Battle battle, Entity target) {
        List<Entity> enemies = battle.getEnemies(this.actor);

        System.out.println(this.actor.getName() + " unleashes a fiery attack on all enemies!");
        for (Entity entity : enemies) {
            entity.applyStatus(new Burning(3, 25));
        }

        // Disable action for 10 turns
        this.disable(10);
        return true;
    }

    /**
     * Retrieves valid targets for the incendiary strike action.
     * This action affects all enemies.
     *
     * @param battle The current battle instance.
     * @return Null as the action applies to all enemies.
     */

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        // Attack all enemies
        return null;
    }

}
