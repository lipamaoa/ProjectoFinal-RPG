package src.items;

/**
 * Represents a weapon that a hero can use.
 */
public class Weapon extends ItemHero {
    private int attack;
    private int specialAttack;

    public Weapon(String name, int price, int attack, int specialAttack) {
        super(name, price);
        this.attack = attack;
        this.specialAttack = specialAttack;
    }

    public int getAttack() {
        return attack;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }
}


