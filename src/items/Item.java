package src.items;

import java.util.Set;
import src.entities.Hero;
import src.entities.HeroClass;

/**
 * Represents an item that can be used by heroes, including weapons, potions, and consumables.
 */
public abstract class Item {
    protected String name;
    protected String description;
    protected int price;
    protected Set<HeroClass> allowedClasses;

    /**
     * Constructs an item with its attributes.
     *
     * @param name The name of the item.
     * @param description A brief description of the item.
     * @param price The cost of the item in gold.
     * @param allowedClasses The hero classes that can use this item.
     */
    public Item(String name, String description, int price, Set<HeroClass> allowedClasses) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.allowedClasses = allowedClasses;
    }

    /**
     * Checks if the given hero can use this item.
     *
     * @param player The hero attempting to use the item.
     * @return True if the hero can use this item, false otherwise.
     */
    public boolean canBeUsedBy(Hero player) {
        return allowedClasses == null || allowedClasses.contains(player.getHeroClass());
    }

    /**
     * Displays the details of the item, including name, description, price, and allowed classes.
     */
    public void showDetails() {
        System.out.println("🛠️ Item: " + this.name);
        System.out.println("ℹ️ " + this.description);
        System.out.println("💰 Value: " + this.price + " gold");
        if (allowedClasses != null) {
            System.out.println("🎭 Usable by: " + allowedClasses);
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the item.
     *
     * @return The item's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the price of the item.
     *
     * @return The price of the item in gold.
     */
    public int getPrice() {
        return price;
    }
}
