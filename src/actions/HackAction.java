package src.actions;

import src.entities.Entity;
import src.game.Battle;
import src.game.GameRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HackAction extends BattleAction {
    private Random random;

    public HackAction(Entity actor) {
        super("Hack", actor);
        this.random = GameRandom.getInstance();
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

        // TODO: Use entity level to reduce success rate
        int successRate = Math.max(0, 100);
        int roll = random.nextInt(100);

        if (roll < successRate) {
            System.out.println("✅ " + this.actor.getName() + " has been able to hack " + target.getName() + "!");
            battle.moveToAllies(target);
            // Disable the target for 1 turn
            System.out.println(target.getName() + " won't be able to attack this turn.");
            target.disable(1);
        } else {
            System.out.println("❌ " + this.actor.getName() + " failed to hack " + target.getName() + "!");
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
