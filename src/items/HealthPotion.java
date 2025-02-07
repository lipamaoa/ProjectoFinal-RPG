package src.items;

import src.entities.Hero;

/**
 * Represents a health potion that restores HP when consumed.
 */
public class HealthPotion extends ItemHero {
    private int healingAmount;

    /**
     * Constructs a health potion.
     *
     * @param healingAmount The amount of HP the potion restores.
     * @param value         The price of the potion.
     */
    public HealthPotion(int healingAmount, int value) {
        super("Health Potion", "Restores " + healingAmount + " HP.", value);
        this.healingAmount = healingAmount;
    }


    

    /**
     * Uses the potion to heal the hero.
     *
     * @param player The hero consuming the potion.
     */
    @Override
    public void use(Hero player) {
        System.out.println("💊 " + player.getName() + " drinks a Health Potion and recovers " + healingAmount + " HP!");
        player.heal(healingAmount);
    }




    public int getEffectValue() {
        return healingAmount;
    }
}
