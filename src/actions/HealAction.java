package src.actions;

import src.entities.Entity;
import src.game.Battle;

import java.util.ArrayList;
import java.util.List;

public class HealAction extends BattleAction {

    public HealAction(Entity actor) {
        super("Heal", actor);
    }

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

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        var alliesThatNeedHealth = new ArrayList<>(battle.getAllies(this.actor));

        alliesThatNeedHealth.removeIf(entity -> entity.getCurrentHp() == entity.getMaxHp());
        return alliesThatNeedHealth;
    }
}
