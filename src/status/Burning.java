package src.status;

import src.entities.Entity;

public class Burning extends EndOfTurnStatus {
    private int damagePerTurn;

    public Burning(int duration, int damage) {
        super("🔥Burning", duration);
        this.damagePerTurn = damage;
    }

    @Override
    public void applyEffect(Entity entity) {
        var adjustedDamage = (int) (damagePerTurn * (0.8 + (random.nextDouble() * 0.2)));
        entity.takeDamage(adjustedDamage);
        System.out.println(entity.getName() + " takes " + adjustedDamage + " 🔥burn damage!");
    }

}
