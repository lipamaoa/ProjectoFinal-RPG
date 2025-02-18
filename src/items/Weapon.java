package src.items;

import java.util.Set;

import src.entities.HeroClass;

/**
 * Represents a weapon that a hero can equip.
 */
public class Weapon extends Item {
    private int attackPower;
    private int durability;

    /**
     * Constructs a weapon.
     *
     * @param name        The name of the weapon.
     * @param attackPower The amount of extra damage it deals.
     * @param durability  How many uses the weapon has.
     * @param price       The price of the weapon.
     */
    public Weapon(String name, String description, int attackPower, int durability, int price,
            Set<HeroClass> allowedClasses) {
        super(name, description, price, allowedClasses);
        this.attackPower = attackPower;
        this.durability = durability;
    }

    // Getters
    public int getAttackPower() {
        return attackPower;
    }

    public int getDurability() {
        return durability;
    }
}
