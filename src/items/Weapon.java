package src.items;

import java.util.Set;

import src.entities.HeroClass;

/**
 * Represents a weapon that a hero can equip.
 */
public class Weapon extends Item {
    private int attackPower;

    /**
     * Constructs a weapon.
     *
     * @param name        The name of the weapon.
     * @param attackPower The amount of extra damage it deals.
     * @param price       The price of the weapon.
     */
    public Weapon(String name, String description, int attackPower, int price,
            Set<HeroClass> allowedClasses) {
        super(name, description, price, allowedClasses);
        this.attackPower = attackPower;
    }

    // Getters
    public int getAttackPower() {
        return attackPower;
    }
}
