package src.entities;

import src.items.HealthPotion;
import src.items.HealthPotionSize;
import src.items.Weapon;

/**
 * A hero specialized in bioengineering and self-healing.
 */
public class Bioengineer extends Hero {
    private int healPerTurn = 0; // HP restored per turn
    private int healDuration = 0; // Duration of healing effect

    /**
     * Constructs a Bioengineer with user-defined stats and a default weapon.
     *
     * @param name     The hero's name.
     * @param maxHp    The allocated health.
     * @param strength The allocated strength.
     * @param gold     The allocated gold.
     */
    public Bioengineer(String name, int maxHp, int strength, int gold, Weapon startingWeapon) {
        super(name, maxHp, strength, gold, startingWeapon);
    }

    /**
     * Bioengineer attacks with a DNA destabilizing pulse.
     * Special ability: Passively heals 3 HP after every attack.
     *
     * @param enemy The NPC being attacked.
     */
    @Override
    public void attack(Enemy enemy) {
        System.out.println("🧬 " + getName() + " releases a DNA destabilizing pulse at " + enemy.getName() + "!");
        int damageDealt = strength + 3;
        enemy.takeDamage(damageDealt);

        // Apply passive healing
        int healAmount = 3;
        heal(healAmount);
        System.out.println("🩹 Bioengineer’s passive ability restores " + healAmount + " HP!");

        // Apply ongoing healing if active
        applyHealing();
    }

    /**
     * Applies gradual HP regeneration over multiple turns.
     *
     * @param healAmount The amount of HP restored per turn.
     * @param duration   The number of turns the regeneration lasts.
     */
    public void healOverTime(int healAmount, int duration) {
        System.out.println("🧬 Regeneration activated! You will recover " + healAmount + " HP per turn for " + duration
                + " turns.");
        this.healPerTurn = healAmount;
        this.healDuration = duration;
    }

    /**
     * Applies healing at the beginning of each turn.
     * Should be called automatically every turn.
     */
    public void applyHealing() {
        if (healDuration > 0) {
            // Prevents overhealing
            int healedHp = Math.min(currentHp + healPerTurn, maxHp);
            // Calculates the real HP gained
            int actualHeal = healedHp - currentHp;

            currentHp = healedHp;
            // Reduces the duration of the healing effect
            healDuration--;

            System.out.println("💚 You regenerated " + actualHeal + " HP. (" + healDuration + " turns remaining)");

            if (healDuration == 0) {
                System.out.println("❌ The regeneration effect has ended!");
                healPerTurn = 0; // Resets healing after it ends
            }
        }
    }

    /**
     * Initializes the Bioengineer’s unique inventory.
     */
    @Override
    protected void initializeInventory() {
        inventory.addItem(new HealthPotion(HealthPotionSize.Large));
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.BIOENGINEER;
    }
}
