package src.actions;

import java.util.List;

import src.entities.Entity;
import src.game.Battle;
import src.status.Burning;

public class IncendiaryStrikeAction extends BattleAction {

    public IncendiaryStrikeAction(Entity actor) {
        super("Incendiary Strike", actor);
    }

    @Override
    public boolean execute(Battle battle, Entity target) {
        var enemies = battle.getEnemies(this.actor);

        System.out.println(this.actor.getName() + " unleashes a fiery attack on all enemies!");
        for (Entity entity : enemies) {
            entity.applyStatus(new Burning(3, 25));
        }

        // Disable action for 10 turns
        this.disable(10);
        return true;
    }

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        // Attack all enemies
        return null;
    }

}
