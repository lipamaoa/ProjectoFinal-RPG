package src.actions;

import src.entities.Entity;
import src.game.Battle;

import java.util.ArrayList;
import java.util.List;

public class HackAction extends BattleAction {
    private static final int SUCCESS_RATE = 33;

    public HackAction(Entity actor) {
        super("Hack", actor);
    }

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

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        var enemies = battle.getEnemies(this.actor);

        List<Entity> validTargets = new ArrayList<>();

        for (Entity entity : enemies) {
            if (entity.isElectronic()) {
                validTargets.add(entity);
            }
        }

        return validTargets;
    }
}
