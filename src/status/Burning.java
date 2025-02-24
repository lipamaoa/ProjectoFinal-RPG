package src.status;

import src.entities.Entity;

/**
 * Represents a status effect that causes an entity to take burn damage at the end of each turn.
 */
public class Burning extends EndOfTurnStatus {
    private final int damagePerTurn;

    /**
     * Constructs a Burning status effect.
     *
     * @param duration The number of turns the effect lasts.
     * @param damage The amount of damage inflicted per turn.
     */

    public Burning(int duration, int damage) {
        super("🔥Burning", duration, true);
        this.damagePerTurn = damage;
    }

    /**
     * Applies the burning effect by dealing damage to the entity at the end of each turn.
     *
     * @param entity The entity affected by the burning status.
     */
    @Override
    public void applyEffect(Entity entity) {
        int adjustedDamage = (int) (damagePerTurn * (0.8 + (random.nextDouble() * 0.2)));
        entity.takeDamage(adjustedDamage);
        System.out.println(entity.getName() + " takes " + adjustedDamage + " 🔥burn damage!");
    }

}
