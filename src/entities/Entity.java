package src.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import src.game.GameRandom;

/**
 * Abstract class representing an entity in the game.
 * It serves as a base for both the player and NPCs.
 */
public abstract class Entity {
    private static final List<String> ELECTRONIC_KEYWORDS = Arrays.asList(
            "robot",
            "sentient",
            "drone",
            "data",
            "cybernetic",
            "engineered");

    protected final String name;
    protected final boolean canHeal;
    protected Random random;
    protected int maxHp;
    protected int currentHp;
    protected int strength;

    public Entity(String name, int maxHp, int strength, boolean canHeal) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.strength = strength;
        this.canHeal = canHeal;
        this.random = GameRandom.getInstance();
    }

    public Entity(String name, int maxHp, int strength) {
        this(name, maxHp, strength, false);
    }

    /**
     * Displays the entity's details
     */
    public void showDetails() {
        System.out.println("Name: " + name);
        System.out.println("HP: " + currentHp + "/" + maxHp);
        System.out.println("Strength: " + strength);
    }

    /**
     * Applies damage to the entity
     * Ensures that HP does not drop below 0.
     */
    public void takeDamage(int amount) {
        this.currentHp -= amount;

        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
        System.out.println(name + " took " + amount + " damage. Remaining HP: " + currentHp);
    }

    /**
     * Heals the entity for a specified amount.
     * Ensures that HP does not exceed maxHp
     */
    public void heal(int amount) {
        this.currentHp += amount;
        if (this.currentHp > maxHp) {
            this.currentHp = maxHp;
        }
        System.out.println(name + " healed " + amount + " HP. Current HP: " + currentHp);
    }

    public void heal(Entity target) {
        if (!this.canHeal) {
            return;
        }

        int healAmount = this.random.nextInt(strength) + 15;
        System.out.println("🩹 " + name + " heals " + target.getName() + " for " + healAmount + " HP!");
        target.heal(healAmount);
    }

    /**
     * Getters
     */

    public String getName() {
        return name;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getStrength() {
        return strength;
    }

    /**
     * Checks if this is an electronic-based entity.
     *
     * @return true if the entity is a robot.
     */
    public boolean isElectronic() {
        String lowerCaseName = getName().toLowerCase();
        return ELECTRONIC_KEYWORDS.stream().anyMatch(lowerCaseName::contains);
    }

    /**
     * Setters
     */

    public void setCurrentHp(int hp) {
        this.currentHp = Math.max(0, Math.min(hp, maxHp)); // Ensures HP is within valid range
    }

    public void setStrength(int strength) {
        this.strength = strength;

    }

    /**
     * Abstract method for attack behavior.
     * Must be implemented by subclasses (Hero and NPC).
     */

    public abstract void attack(Entity target);
}
