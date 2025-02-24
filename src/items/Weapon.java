package src.items;

import src.entities.HeroClass;

import java.util.Set;

/**
 * Represents a weapon that a hero can equip.
 */
public class Weapon extends Item {
    private final int attackPower;

    /**
     * Constructs a weapon.
     *
     * @param name        The name of the weapon.
     * @param description A brief description of the weapon.
     * @param attackPower The amount of extra damage it deals.
     * @param price       The price of the weapon.
     * @param allowedClasses The hero classes that can use this weapon.
     */
    public Weapon(String name, String description, int attackPower, int price,
            Set<HeroClass> allowedClasses) {
        super(name, description, price, allowedClasses);
        this.attackPower = attackPower;
    }

    /**
     * Retrieves the attack power of the weapon.
     *
     * @return The attack power value.
     */
    public int getAttackPower() {
        return attackPower;
    }
}
