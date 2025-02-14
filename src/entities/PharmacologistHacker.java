package src.entities;

import java.util.Random;

import src.items.Weapon;

/**
 * A hero specialized in hacking and chemical-based attacks.
 */
public class PharmacologistHacker extends Hero {

    private static final Random random = new Random();
    private boolean hasUsedHack = false; // Can hack only once per battle

  /**
     * Constructs a Pharmacologist Hacker with user-defined stats and a default weapon.
     *
     * @param name     The hero's name.
     * @param maxHp    The allocated health.
     * @param strength The allocated strength.
     * @param level    The starting level.
     * @param gold     The allocated gold.
     */
    public PharmacologistHacker(String name, int maxHp, int strength, int level, int gold, Weapon startingWeapon) {
        super(name, maxHp, strength, level, gold, startingWeapon);
    }

    /**
     * Pharmacologist Hacker attacks with a neurotoxin injection.
     * 30% chance to weaken the enemy and 40% chance to hack their system.
     *
     * @param enemy The NPC being attacked.
     */
    @Override
    public void attack(NPC enemy) {
        System.out.println("🧪 " + getName() + " injects a neurotoxin into " + enemy.getName() + "!");
        int initialDamage = strength + 2;
        enemy.takeDamage(initialDamage);

        // Poison Effect: Deals extra damage over time
        int poisonDamage = 2;
        System.out.println("☠️ " + enemy.getName() + " is poisoned and takes " + poisonDamage + " extra damage!");
        enemy.takeDamage(poisonDamage);

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

    /**
     * Resets the hacking ability for the next battle.
     */
    public void resetHackAbility() {
        hasUsedHack = false;
    }

    /**
 * Allows the hero to hack an enemy, forcing them to attack another NPC.
 *
 * @param enemy  The enemy to hack.
 * @param target The NPC to be attacked by the hacked enemy.
 */
public void hackEnemy(NPC enemy, NPC target) {
    if (hasUsedHack) {
        System.out.println("⚠️ Hacking already used in this battle.");
        return;
    }

    System.out.println("💻 " + getName() + " attempts to hack " + enemy.getName() + "...");
    
    if (random.nextInt(100) < 50) { // 50% success rate
        System.out.println("✅ Hack successful! " + enemy.getName() + " will attack " + target.getName() + "!");
        enemy.setHacked(true);
        enemy.attack(target); // Hacked NPC attacks the chosen target
    } else {
        System.out.println("❌ Hack failed! " + enemy.getName() + " remains in control.");
    }

    hasUsedHack = true; // Hacking can only be used once per battle
}


    @Override
    public void attack(Entity target) {
        if (target instanceof NPC) {
            attack((NPC) target); // Call the NPC attack method
        } else {
            System.out.println("⚠️ Bioengineer cannot attack this target!");
        }
    }
}
