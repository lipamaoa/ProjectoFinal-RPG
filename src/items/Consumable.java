package src.items;

/**
 * Abstract class for consumable items (like potions).
 */
public abstract class Consumable extends ItemHero {
    public Consumable(String name, int price) {
        super(name, price);
    }

    public abstract int getHealingAmount();
}

