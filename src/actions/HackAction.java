package src.actions;

import src.entities.Entity;
import src.game.Battle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hacking action that can be performed on electronic entities during battle.
 */
public class HackAction extends BattleAction {
    private static final int SUCCESS_RATE = 33;

    /**
     * Constructs a HackAction for a given entity.
     *
     * @param actor The entity performing the hack.
     */

    public HackAction(Entity actor) {
        super("Hack", actor);
    }

    /**
     * Executes the hacking attempt on a target entity.
     *
     * @param battle The current battle instance.
     * @param target The target entity being hacked.
     * @return True if the hacking attempt was made, otherwise false.
     */

    @Override
    public boolean execute(Battle battle, Entity target) {
        if (target == null) {
            System.out.println("❌ No target selected for hacking!");
            return false;
        }

        if (!target.isElectronic()) {
            System.out.println("❌ " + target.getName() + " cannot be hacked!");
            return false;
        }

        int roll = random.nextInt(100);

        if (roll < SUCCESS_RATE) {
            System.out.println("✅ " + this.actor.getName() + " has been able to hack " + target.getName() + "!");
            battle.moveToAllies(target);
            // Disable the target for 1 turn
            System.out.println(target.getName() + " won't be able to attack this turn.");
            target.disable(1);

            // Disable action for 5 turns
            this.disable(5);
        } else {
            System.out.println("❌ " + this.actor.getName() + " failed to hack " + target.getName() + "!");
            this.disable(1);
        }

        return true;
    }

    /**
     * Retrieves valid hackable targets from the battle.
     *
     * @param battle The current battle instance.
     * @return A list of valid electronic target entities.
     */

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        List<Entity> enemies = battle.getEnemies(this.actor);

        List<Entity> validTargets = new ArrayList<>();

        for (Entity entity : enemies) {
            if (entity.isElectronic()) {
                validTargets.add(entity);
            }
        }

        return validTargets;
    }
}
