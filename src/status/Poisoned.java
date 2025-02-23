package src.status;

import src.entities.Entity;

public class Poisoned extends EndOfTurnStatus {
    private int damagePerTurn;

    public Poisoned(int duration, int damage) {
        super("☠️Poison", duration, true);
        this.damagePerTurn = damage;
    }

    @Override
    public void applyEffect(Entity entity) {
        var adjustedDamage = (int) (damagePerTurn * (0.8 + (random.nextDouble() * 0.2)));
        entity.takeDamage(adjustedDamage);
        System.out.println(entity.getName() + " takes " + adjustedDamage + " ☠️poison damage!");
    }
}
