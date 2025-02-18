package src.entities;

import java.util.Random;

import src.game.GameRandom;
import src.items.*;

/**
 * A hero specialized in hacking and chemical-based attacks.
 */
public class PharmacologistHacker extends Hero {

    private final Random random;
    private boolean hasUsedHack = false; // Can hack only once per battle

    /**
     * Constructs a Pharmacologist Hacker with user-defined stats and a default
     * weapon.
     *
     * @param name     The hero's name.
     * @param maxHp    The allocated health.
     * @param strength The allocated strength.
     * @param gold     The allocated gold.
     */
    public PharmacologistHacker(String name, int maxHp, int strength, int gold, Weapon startingWeapon) {
        super(name, maxHp, strength, gold, startingWeapon);
        this.random = GameRandom.getInstance();
    }

    /**
     * Pharmacologist Hacker attacks with a neurotoxin injection.
     * 30% chance to weaken the enemy and 40% chance to hack their system.
     *
     * @param enemy The NPC being attacked.
     */
    @Override
    public void attack(Enemy enemy) {
        System.out.println("🧪 " + getName() + " injects a neurotoxin into " + enemy.getName() + "!");
        int initialDamage = strength + 2;
        enemy.takeDamage(initialDamage);

        // Poison Effect: Deals extra damage over time
        int poisonDamage = 2;
        System.out.println("☠️ " + enemy.getName() + " is poisoned and takes " + poisonDamage + " extra damage!");
        enemy.takeDamage(poisonDamage);

        // Only apply the adrenaline boost if it was activated via item
        int damage = getAttackPower(); // Uses boosted attack if active
        enemy.takeDamage(damage);
        System.out.println("☠️ " + enemy.getName() + " is poisoned and takes " + damage + " damage!");

        // 30% chance to weaken the enemy's attack power
        if (random.nextInt(100) < 30) {
            enemy.setStrength(Math.max(1, enemy.getStrength() - 2)); // Reduce enemy strength safely
            System.out.println("💀 " + enemy.getName() + " is weakened by the toxin! Their attack power decreases.");
        }

        // Hacking Attempt (Only works on NPCs, once per battle)
        if (!hasUsedHack && random.nextInt(100) < 40) { // 40% success rate
            System.out.println("💻 " + getName() + " successfully hacks " + enemy.getName() + "'s system!");
            enemy.takeDamage(enemy.getCurrentHp()); // Instantly disables the enemy
            hasUsedHack = true; // Cannot hack again this battle
        }
    }

    @Override
    protected void initializeInventory() {
        // // ✅ Add hero-specific items, but DO NOT initialize inventory again
        // inventory.addItem(new ItemHero("Hacking Device", "Allows hacking security systems.", 50) {
        //     @Override
        //     public void use(Hero player) {
        //         System.out.println("💻 You hack a system, disabling security for a short time!");
        //     }
        // });

        inventory.addItem(new HealthPotion(HealthPotionSize.Medium));
    }

    public void useEmpGrenade(Enemy enemy) {
        System.out.println("💥 EMP Grenade activated! Attempting to disable " + enemy.getName() + "...");

        if (enemy.isElectronic()) { // Only affects electronic enemies
            enemy.disable(2); // Disable enemy for 2 turns
        } else {
            System.out.println("❌ The EMP Grenade has no effect on organic enemies!");
        }
    }
}
