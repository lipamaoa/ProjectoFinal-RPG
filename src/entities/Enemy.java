package src.entities;

import java.util.Random;

import src.game.GameRandom;

/**
 * This class is used for enemies that the player will encounter in battles.
 */
public class Enemy extends Entity {
    protected int gold;
    protected int attackPower;
    protected int specialAttackPower;
    protected boolean canHeal;
    private int disabledTurns = 0;
    private final Random random;
    private final int difficultyLevel;
    private boolean isHacked = false;

    /**
     * Constructs an NPC with scaled attributes based on difficulty level.
     *
     * @param name            The name of the NPC.
     * @param baseHp          The base health of the NPC.
     * @param baseAttack      The base attack power of the NPC.
     * @param baseGold        The base gold reward upon defeat.
     * @param difficultyLevel The difficulty scaling factor.
     */
    public Enemy(String name, int baseHp, int baseAttack, int baseGold, int difficultyLevel) {
        super(name, scaleValue(baseHp, difficultyLevel), scaleValue(baseAttack, difficultyLevel));
        this.gold = scaleValue(baseGold, difficultyLevel);
        this.attackPower = this.strength;
        this.specialAttackPower = attackPower * 2;
        this.random = GameRandom.getInstance();
        this.canHeal = this.random.nextBoolean();
        this.difficultyLevel = difficultyLevel;
    }

    /**
     * Scales a given stat based on the difficulty level.
     *
     * @param baseValue       The base stat value.
     * @param difficultyLevel The difficulty level (higher means stronger enemies).
     * @return The scaled value.
     */
    private static int scaleValue(int baseValue, int difficultyLevel) {
        return baseValue + (difficultyLevel * 5); // Increases stats as difficulty increases
    }

    /**
     * Disables the enemy for a given number of turns (EMP Effect).
     *
     * @param turns The number of turns the enemy is disabled.
     */
    public void disable(int turns) {
        if (isElectronic()) {
            this.disabledTurns = turns;
            System.out.println("⚡ " + getName() + " is hit by an EMP! Disabled for " + turns + " turns.");
        } else {
            System.out.println("❌ The EMP has no effect on " + getName() + "!");
        }
    }

    /**
     * Tries to hack the enemy. The success rate decreases with higher difficulty
     * levels.
     *
     * @return true if the hack is successful, false otherwise.
     */
    public boolean tryToHack() {
        if (!isElectronic()) {
            System.out.println("❌ " + getName() + " cannot be hacked!");
            return false;
        }

        int successRate = Math.max(0, 100 - (difficultyLevel * 10)); // Decrease success rate with higher difficulty
        int roll = random.nextInt(100);

        if (roll < successRate) {
            System.out.println("✅ " + getName() + " has been successfully hacked!");
            isHacked = true;
            return true;
        } else {
            System.out.println("❌ Hacking attempt on " + getName() + " failed!");
            return false;
        }
    }

    /**
     * Checks if the enemy is currently disabled (EMP effect).
     *
     * @return true if the enemy is disabled, false otherwise.
     */
    public boolean isDisabled() {
        return disabledTurns > 0;
    }

    /**
     * NPC chooses an action: Normal attack, Special attack, or Heal.
     *
     * @param target The entity being attacked (can be a Hero or another NPC.
     */
    @Override
    public void attack(Entity target) {
        System.out.println("🗡️ " + getName() + " attacks " + target.getName() + "!");

        if (isDisabled()) {
            System.out.println("⚡ " + getName() + " is disabled and cannot attack!");
            disabledTurns--; // Reduce disable duration
            return;
        }

        int action = this.random.nextInt(100);
        if (action < 60) { // 60% chance for normal attack
            normalAttack(target);
        } else if (action < 85) { // 25% chance for special attack
            specialAttack(target);
        } else if (canHeal && currentHp <= maxHp / 2) { // 15% chance to heal (only if HP is low)
            heal();
        } else { // Default to normal attack if no other option is valid
            normalAttack(target);
        }
    }

    public boolean isHacked() {
        return this.isHacked;
    }

    /**
     * Executes a normal attack.
     *
     * @param target The entity being attacked.
     */
    private void normalAttack(Entity target) {
        System.out.println("🗡️ " + name + " attacks " + target.getName() + " for " + attackPower + " damage.");
        target.takeDamage(attackPower);
    }

    /**
     * Executes a powerful special attack.
     *
     * @param target The entity being attacked.
     */
    private void specialAttack(Entity target) {
        System.out.println("💥 " + name + " unleashes a devastating special attack on " + target.getName() + " for "
                + specialAttackPower + " damage!");
        target.takeDamage(specialAttackPower);
    }

    /**
     * NPC heals itself when its HP is low.
     */
    private void heal() {
        int healAmount = this.random.nextInt(15) + 10; // Restore between 10 and 25 HP
        currentHp = Math.min(currentHp + healAmount, maxHp);
        System.out.println("🩹 " + name + " heals itself for " + healAmount + " HP!");
    }

    /**
     * Gets the gold reward given when this NPC is defeated.
     *
     * @return The amount of gold.
     */
    public int getGold() {
        return gold;
    }
}
