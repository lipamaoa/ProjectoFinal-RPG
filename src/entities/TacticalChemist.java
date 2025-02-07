package src.entities;

import java.util.Random;

import src.items.Weapon;

/**
 * A hero specialized in chemical explosives and reactive combat.
 */
public class TacticalChemist extends Hero {

    private static final Random random = new Random();

    /**
     * Constructs a Tactical Chemist hero with base stats.
     *
     * @param name The hero's name.
     */
    public TacticalChemist(String name, int maxHp, int strength, int level, int gold, Weapon startingWeapon) {
        super(name, maxHp, strength, level, gold, startingWeapon);
    }

    /**
     * Tactical Chemist attacks by throwing a chemical grenade.
     * Special ability: Has a 30% chance to cause an explosion that damages all enemies.
     *
     * @param enemy The NPC being attacked.
     */
    @Override
    public void attack(NPC enemy) {
        System.out.println("💣 " + getName() + " throws a chemical grenade at " + enemy.getName() + "!");
        int damageDealt = strength + 4;
        enemy.takeDamage(damageDealt);

        // 30% chance for an explosive reaction affecting all nearby NPCs
        if (random.nextInt(100) < 30) { 
            System.out.println("🔥 The grenade explodes, dealing extra damage to all nearby enemies!");
            enemy.takeDamage(5); // Extra AoE damage
        }
    }

    /**
     * Implements the required attack(Entity target) method.
     * Ensures the Tactical Chemist can attack only NPCs.
     *
     * @param target The entity being attacked.
     */
    @Override
    public void attack(Entity target) {
        if (target instanceof NPC) {
            attack((NPC) target); // Call the NPC attack method
        } else {
            System.out.println("⚠️ Tactical Chemist cannot attack this target!");
        }
    }
}
