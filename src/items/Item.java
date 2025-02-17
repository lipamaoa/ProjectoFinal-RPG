package src.items;

/**
 * Represents a base abstract class for all types of items.
 */
public abstract class Item {
    protected String name;
    protected String description;
    protected int price;

    /**
     * Constructs an item.
     *
     * @param name        The name of the item.
     * @param description The description of what the item does.
     * @param price       The item's value (cost or selling price).
     */
    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

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
