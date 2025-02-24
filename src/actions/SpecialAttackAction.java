package src.actions;

import src.entities.Entity;
import src.game.Battle;
import src.items.Weapon;

import java.util.List;

/**
 * Represents a special attack action that deals increased damage to a single target.
 */
public class SpecialAttackAction extends BattleAction {

    /**
     * Constructs a SpecialAttackAction for a given entity.
     *
     * @param actor The entity performing the special attack.
     */
    public SpecialAttackAction(Entity actor) {
        super("Special Attack", actor);
    }


    /**
     * Executes the special attack action, dealing enhanced damage to a target entity.
     *
     * @param battle The current battle instance.
     * @param target The target entity being attacked.
     * @return True if the attack was executed successfully, otherwise false.
     */

    @Override
    public boolean execute(Battle battle, Entity target) {
        if (target == null) {
            return false;
        }

        Weapon equippedWeapon = actor.getEquipedWeapon();

        // Randomized strength contribution (100% - 120% of base)
        int strength = actor.getStrength();
        int strengthContribution = (int) (strength * (1 + (random.nextDouble() * 0.2)));

        // Randomized weapon damage contribution (100% - 150% of base)
        int weaponDamage = equippedWeapon != null ? equippedWeapon.getAttackPower() : 0;
        int weaponContribution = (int) (weaponDamage * (1 + (random.nextDouble() * 0.5)));

        // Total randomized damage
        int totalDamage = strengthContribution + weaponContribution;
        target.takeDamage(totalDamage);

        System.out.println("\n" + actor.getName() + " releases a devastating attack on " + target.getName() + " with "
                + totalDamage + " total damage!🙀");

        // Disable action for 5 turns
        this.disable(5);
        return true;
    }

    /**
     * Retrieves valid targets for the special attack action.
     *
     * @param battle The current battle instance.
     * @return A list of enemy entities that can be attacked.
     */
    @Override
    public List<Entity> getValidTargets(Battle battle) {
        return battle.getEnemies(this.actor);
    }
}
