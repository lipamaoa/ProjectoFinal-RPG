package src.entities;

import src.items.Weapon;

/**
 * A hero specialized in bioengineering and self-healing.
 */
public class Bioengineer extends Hero {

      /**
     * Constructs a Bioengineer with user-defined stats and a default weapon.
     *
     * @param name     The hero's name.
     * @param maxHp    The allocated health.
     * @param strength The allocated strength.
     * @param level    The starting level.
     * @param gold     The allocated gold.
     */
    public Bioengineer(String name, int maxHp, int strength, int level, int gold, Weapon startingWeapon) {
        super(name, maxHp, strength, level, gold, startingWeapon);
    }

    /**
     * Bioengineer attacks with a DNA destabilizing pulse.
     * Special ability: Passively heals 3 HP after every attack.
     *
     * @param enemy The NPC being attacked.
     */
    @Override
    public void attack(NPC enemy) {
        System.out.println("🧬 " + getName() + " releases a DNA destabilizing pulse at " + enemy.getName() + "!");
        int damageDealt = strength + 3;
        enemy.takeDamage(damageDealt);

        // Passive healing after each attack
        int healAmount = 3;
        heal(healAmount);
        System.out.println("🩹 Bioengineer’s passive ability restores " + healAmount + " HP!");
    }

    /**
     * Implements the required attack(Entity target) method.
     * This ensures the Bioengineer can attack any Entity, not just NPCs.
     *
     * @param target The entity being attacked.
     */
    @Override
    public void attack(Entity target) {
        if (target instanceof NPC) {
            attack((NPC) target); // Call the NPC attack method
        } else {
            System.out.println("⚠️ Bioengineer cannot attack this target!");
        }
    }
}
