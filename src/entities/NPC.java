package src.entities;

import java.util.Random;

/**
 * Represents an enemy NPC with improved AI.
 */
public class NPC extends Entity {
    protected int gold;
    protected int attackPower;
    protected int specialAttackPower;
    protected boolean canHeal;
    private static Random random = new Random();

    public NPC(String name, int maxHp, int attackPower, int gold) {
        super(name, maxHp, attackPower);
        this.gold = gold;
        this.attackPower = attackPower;
        this.specialAttackPower = attackPower * 2; // Special attack is stronger
        this.canHeal = random.nextBoolean(); // Some enemies can heal
    }

        /**
     * Scales a given stat based on the difficulty level.
     */
    private static int scaleValue(int baseValue, int difficultyLevel) {
        return baseValue + (difficultyLevel * 5); // Increment stats by 5 per difficulty level
    }

    @Override
    public void attack(Entity target) {
        int action = random.nextInt(100); // Random chance to pick an action

        if (action < 60) { // 60% chance to use a normal attack
            normalAttack(target);
        } else if (action < 85) { // 25% chance to use a special attack
            specialAttack(target);
        } else if (canHeal && currentHp <= maxHp / 2) { // 15% chance to heal (only if health is low)
            heal();
        } else { // If healing isn't an option, default to normal attack
            normalAttack(target);
        }
    }

    /**
     * Executes a normal attack.
     */
    private void normalAttack(Entity target) {
        System.out.println("🗡️ " + name + " attacks " + target.getName() + " for " + attackPower + " damage.");
        target.takeDamage(attackPower);
    }

    /**
     * Executes a powerful special attack.
     */
    private void specialAttack(Entity target) {
        System.out.println("💥 " + name + " unleashes a special attack on " + target.getName() + " for " + specialAttackPower + " damage!");
        target.takeDamage(specialAttackPower);
    }

    /**
     * Heals the NPC for a random amount.
     */
    private void heal() {
        int healAmount = random.nextInt(15) + 10; // Restores 10-25 HP
        currentHp = Math.min(currentHp + healAmount, maxHp);
        System.out.println("🩹 " + name + " heals itself for " + healAmount + " HP!");
    }
}
