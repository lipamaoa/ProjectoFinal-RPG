package src.actions;

import src.entities.Entity;
import src.game.Battle;

import java.util.List;

public class AttackAction extends BattleAction {

    public AttackAction(Entity actor) {
        super("Attack", actor);
    }

    @Override
    public boolean execute(Battle battle, Entity target) {
        if (target == null) {
            return false;
        }

        var equippedWeapon = actor.getEquipedWeapon();

        // Randomized strength contribution (80% - 100% of base)
        int strength = actor.getStrength();
        int strengthContribution = (int) (strength * (0.8 + (random.nextDouble() * 0.2)));

        // Randomized weapon damage contribution (90% - 110% of base)
        int weaponDamage = equippedWeapon != null ? equippedWeapon.getAttackPower() : 0;
        int weaponContribution = (int) (weaponDamage * (0.9 + (random.nextDouble() * 0.2)));

        // Total randomized damage
        int totalDamage = strengthContribution + weaponContribution;

        target.takeDamage(totalDamage);
        System.out
                .println(actor.getName() + " attacks " + target.getName() + " with " + totalDamage
                        + " total damage!");

        return true;
    }

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        return battle.getEnemies(this.actor);
    }
}
