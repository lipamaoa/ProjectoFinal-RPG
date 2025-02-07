package src.items;

/**
 * Represents a base class for items that a hero can use or equip.
 */
public abstract class ItemHero {
    protected String name;
    protected String description;
    protected int price; // Cost or sell price

    /**
     * Constructs a hero item.
     *
     * @param name        The name of the item.
     * @param description The description of what the item does.
     * @param price       The item's value (cost or selling price).
     */
    public ItemHero(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Applies the item's effect when used by the hero.
     */
    public abstract void use(src.entities.Hero player);

    /**
     * Displays item details.
     */
    public void showDetails() {
        System.out.println("🛠️ Item: " + this.name);
        System.out.println("ℹ️ " + this.description);
        System.out.println("💰 Value: " + this.price + " gold");
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
