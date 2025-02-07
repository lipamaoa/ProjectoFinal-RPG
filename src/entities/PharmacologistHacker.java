package src.entities;

/**
 * A hero specialized in hacking and chemical attacks.
 */
public class PharmacologistHacker extends Hero {
    
    public PharmacologistHacker(String name) {
        super(name, 100, 10, 1, 20, null); // Default stats
    }

    @Override
    public void attack(NPC enemy) {
        System.out.println(name + " injects a neurotoxin into " + enemy.name + "!");
        enemy.currentHp -= (strength + 5);
    }
}

