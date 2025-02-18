package src.items;

import src.entities.Hero;

/**
 * Represents a health potion that restores HP when consumed.
 */
public class HealthPotion extends ItemHero {
    private final int healingAmount;

    /**
     * Constructs a health potion.
     *
     * @param size - Size of the health potion
     */
    public HealthPotion(HealthPotionSize size) {
        super(getPotionName(size), "Heals for " + getPotionHealing(size) + " ❤\uFE0FHP", getPotionPrice(size), null,
                null);
        this.healingAmount = getPotionHealing(size);

        this.effect = (Hero player) -> {
            System.out.println(
                    "💊 " + player.getName() + " drinks a Health Potion and recovers " + healingAmount + " HP!");
            player.heal(healingAmount);
        };
    }

    private static String getPotionName(HealthPotionSize size) {
        return switch (size) {
            case Small -> "Small Health Potion";
            case Medium -> "Medium Health Potion";
            case Large -> "Large Health Potion";
        };
    }

    private static int getPotionHealing(HealthPotionSize size) {
        return switch (size) {
            case Small -> 50;
            case Medium -> 100;
            case Large -> 150;
        };
    }

    private static int getPotionPrice(HealthPotionSize size) {
        return switch (size) {
            case Small -> 25;
            case Medium -> 40;
            case Large -> 60;
        };
    }

    public int getEffectValue() {
        return healingAmount;
    }
}
