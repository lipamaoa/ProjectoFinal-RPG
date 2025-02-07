package src.items;

import src.entities.Hero;

/**
 * Represents a weapon that a hero can equip.
 */
public class Weapon extends ItemHero {
    private int attackPower;
    private int durability;

    /**
     * Constructs a weapon.
     *
     * @param name        The name of the weapon.
     * @param attackPower The amount of extra damage it deals.
     * @param durability  How many uses the weapon has.
     * @param value       The price of the weapon.
     */
    public Weapon(String name, int attackPower, int durability, int value) {
        super(name, "A weapon that increases attack power.", value);
        this.attackPower = attackPower;
        this.durability = durability;
    }

    /**
     * Uses the weapon in battle.
     */
    @Override
    public void use(Hero player) {
        if (durability > 0) {
            System.out.println("🔪 " + name + " is used in battle, dealing +" + attackPower + " damage!");
            durability--;
        } else {
            System.out.println("❌ " + name + " is broken and cannot be used!");
        }
    }

    // Getters
    public int getAttackPower() {
        return attackPower;
    }

    public int getDurability() {
        return durability;
    }
}
