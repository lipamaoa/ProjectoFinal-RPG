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

        System.out.println(actor.getName() + " attacks " + target.getName() + "!");
        int damage = actor.getStrength();
        target.takeDamage(damage);
        System.out.println(target.getName() + " took " + damage + " damage. Remaining HP: " + target.getCurrentHp());
        return true;
    }

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        return battle.getEnemies(this.actor);
    }
}
