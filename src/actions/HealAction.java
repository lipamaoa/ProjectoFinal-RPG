package src.actions;

import src.entities.Entity;
import src.game.Battle;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a healing action that can be performed on allies during battle.
 */
public class HealAction extends BattleAction {

    /**
     * Constructs a HealAction for a given entity.
     *
     * @param actor The entity performing the heal.
     */

    public HealAction(Entity actor) {
        super("Heal", actor);
    }

    /**
     * Executes the healing action on a target entity.
     *
     * @param battle The current battle instance.
     * @param target The target entity being healed.
     * @return True if the healing action was executed, otherwise false.
     */

    @Override
    public boolean execute(Battle battle, Entity target) {
        if (target == null) {
            System.out.println("❌ No target selected for healing!");
            return false;
        }

        int healAmount = this.random.nextInt(actor.getStrength()) + 15;
        target.heal(healAmount);
        System.out.println(actor.getName() + " heals " + target.getName() + " for " + healAmount + " HP! "
                + target.getName() + " now has " + target.getCurrentHp() + " HP.");
        // Disable action for 2 turns
        this.disable(2);
        return true;
    }

    /**
     * Retrieves valid healable targets from the battle.
     *
     * @param battle The current battle instance.
     * @return A list of allies that need healing.
     */

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        var alliesThatNeedHealth = new ArrayList<>(battle.getAllies(this.actor));

        alliesThatNeedHealth.removeIf(entity -> entity.getCurrentHp() == entity.getMaxHp());
        return alliesThatNeedHealth;
    }
}
