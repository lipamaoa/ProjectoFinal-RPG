package src.actions;

import src.entities.Entity;
import src.game.Battle;

import java.util.List;

public class SpecialAttackAction extends BattleAction {

    public SpecialAttackAction(Entity actor) {
        super("Special Attack", actor);
    }

    @Override
    public boolean execute(Battle battle, Entity target) {
        if (target == null) {
            return false;
        }

        var equippedWeapon = actor.getEquipedWeapon();

        // Randomized strength contribution (100% - 120% of base)
        int strength = actor.getStrength();
        int strengthContribution = (int) (strength * (1 + (random.nextDouble() * 0.2)));

        // Randomized weapon damage contribution (100% - 150% of base)
        int weaponDamage = equippedWeapon != null ? equippedWeapon.getAttackPower() : 0;
        int weaponContribution = (int) (weaponDamage * (1 + (random.nextDouble() * 0.5)));

        // Total randomized damage
        int totalDamage = strengthContribution + weaponContribution;
        target.takeDamage(totalDamage);

        System.out.println(actor.getName() + " releases a devastating attack on " + target.getName() + " with "
                + totalDamage + " total damage!🙀");

        // Disable action for 5 turns
        this.disable(5);
        return true;
    }

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        return battle.getEnemies(this.actor);
    }
}
