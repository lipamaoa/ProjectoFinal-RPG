package src.status;

import src.entities.Entity;

/**
 * Represents a regeneration status effect that restores health to an entity at
 * the end of each turn.
 */
public class Regeneration extends EndOfTurnStatus {
    private final int healAmount;

    /**
     * Constructs a Regeneration status effect.
     *
     * @param duration   The number of turns the effect lasts.
     * @param healAmount The base amount of health restored per turn.
     */

    public Regeneration(int duration, int healAmount) {
        super("❤️‍🩹Regeneration", duration);
        this.healAmount = healAmount;
    }

    /**
     * Applies the regeneration effect by healing the entity at the end of each
     * turn.
     *
     * @param entity The entity affected by the regeneration status.
     */
    @Override
    public void applyEffect(Entity entity) {
        if (entity.getCurrentHp() == entity.getMaxHp()) {
            // Don't heal if the entity is already at full health
            return;
        }

        int adjustedHealAmount = (int) (healAmount * (0.5 + (random.nextDouble() * 0.7)));
        entity.heal(adjustedHealAmount);
        System.out.println(entity.getName() + " heals for " + adjustedHealAmount + " 💗!");
    }
}
