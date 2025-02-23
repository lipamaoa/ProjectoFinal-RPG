package src.status;

import src.entities.Entity;

public class Regeneration extends EndOfTurnStatus {
    private int healAmount;

    public Regeneration(int duration, int healAmount) {
        super("❤️‍🩹Regeneration", duration);
        this.healAmount = healAmount;
    }

    @Override
    public void applyEffect(Entity entity) {
        if (entity.getCurrentHp() == entity.getMaxHp()) {
            // Don't heal if the entity is already at full health
            return;
        }

        int adjustedHealAmount = (int) (healAmount * (0.5 + (random.nextDouble() * 0.7)));
        entity.heal(adjustedHealAmount);
        System.out.println(entity.getName() + " heals for " + adjustedHealAmount + " ❤\\\\uFE0FHP!");
    }
}
