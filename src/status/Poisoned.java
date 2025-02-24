package src.status;

import src.entities.Entity;

/**
 * Represents a poisoned status effect that deals damage to an entity at the end of each turn.
 */
public class Poisoned extends EndOfTurnStatus {
    private final int damagePerTurn;

    /**
     * Constructs a Poisoned status effect.
     *
     * @param duration The number of turns the effect lasts.
     * @param damage The amount of damage inflicted per turn.
     */
    public Poisoned(int duration, int damage) {
        super("☠️Poison", duration, true);
        this.damagePerTurn = damage;
    }

    /**
     * Applies the poison effect by dealing damage to the entity at the end of each turn.
     *
     * @param entity The entity affected by the poisoned status.
     */
    @Override
    public void applyEffect(Entity entity) {
        var adjustedDamage = (int) (damagePerTurn * (0.8 + (random.nextDouble() * 0.2)));
        entity.takeDamage(adjustedDamage);
        System.out.println(entity.getName() + " takes " + adjustedDamage + " ☠️poison damage!");
    }
}
