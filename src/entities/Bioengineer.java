package src.entities;

import src.entities.NPC;

/**
 * A hero specialized in bioengineering and self-healing.
 */
public class Bioengineer extends Hero {

    public Bioengineer(String name) {
        super(name, 120, 8, 1, 20, null); // Ensure null is allowed or assign a default weapon
    }

    @Override
    public void attack(NPC enemy) {
        System.out.println(this.getName() + " releases a DNA destabilizing pulse at " + enemy.getName() + "!");
        enemy.takeDamage(strength + 3); // Ensure takeDamage() exists in NPC
    }
}

