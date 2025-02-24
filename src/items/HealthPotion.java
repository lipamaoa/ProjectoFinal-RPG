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
        super(getPotionName(size), "Heals for " + getPotionHealing(size) + " ❤️HP", getPotionPrice(size), null,
                null);
        this.healingAmount = getPotionHealing(size);

        this.effect = (Hero player) -> {
            System.out.println(
                    "💊 " + player.getName() + " drinks " + getPotionName(size) + " and recovers " + healingAmount
                            + " HP!");
            player.heal(healingAmount);
            System.out.println("💊 " + player.getName() + " now has " + player.getCurrentHp() + " HP now.");
        };
    }

    /**
     * Retrieves the name of the potion based on its size.
     *
     * @param size The size of the potion.
     * @return The corresponding name of the potion.
     */
    private static String getPotionName(HealthPotionSize size) {
        return switch (size) {
            case Small -> "Small Health Potion";
            case Medium -> "Medium Health Potion";
            case Large -> "Large Health Potion";
        };
    }

    /**
     * Determines the amount of HP restored based on potion size.
     *
     * @param size The size of the potion.
     * @return The healing value.
     */

    private static int getPotionHealing(HealthPotionSize size) {
        return switch (size) {
            case Small -> 50;
            case Medium -> 100;
            case Large -> 150;
        };
    }

    /**
     * Determines the price of the potion based on its size.
     *
     * @param size The size of the potion.
     * @return The price of the potion.
     */
    private static int getPotionPrice(HealthPotionSize size) {
        return switch (size) {
            case Small -> 25;
            case Medium -> 40;
            case Large -> 60;
        };
    }

    /**
     * Retrieves the healing effect value of the potion.
     *
     * @return The amount of HP the potion restores.
     */

    public int getEffectValue() {
        return healingAmount;
    }
}
