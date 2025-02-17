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
     * @param price       The price of the weapon.
     */
    public Weapon(String name, String description, int attackPower, int durability, int price) {
        super(name, description, price, null);
        this.attackPower = attackPower;
        this.durability = durability;

        this.effect = (Hero player) -> {
            if (this.durability > 0) {
                System.out.println("🔪 " + name + " is used in battle, dealing +" + attackPower + " damage!");
                this.durability--;
            } else {
                System.out.println("❌ " + name + " is broken and cannot be used!");
            }
        };
    }

    // Getters
    public int getAttackPower() {
        return attackPower;
    }

    public int getDurability() {
        return durability;
    }
}
