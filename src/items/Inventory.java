package src.items;

import java.util.ArrayList;

/**
 * Manages the hero's inventory, including weapons and health potions.
 */
public class Inventory {
    private ArrayList<ItemHero> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory.
     * @param item The item to be added.
     */
    public void addItem(ItemHero item) {
        if (item != null) {
            items.add(item);
            System.out.println("📦 Added " + item.getName() + " to inventory.");
        } else {
            System.out.println("⚠️ Cannot add a null item.");
        }
    }

    /**
     * Removes an item from the inventory.
     * @param item The item to remove.
     */
    public void removeItem(ItemHero item) {
        if (items.contains(item)) {
            items.remove(item);
            System.out.println("🗑️ Used " + item.getName() + ".");
        } else {
            System.out.println("⚠️ Item not found in inventory.");
        }
    }

    /**
     * Displays all items currently in the inventory.
     */
    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("🎒 Inventory is empty.");
        } else {
            System.out.println("\n🎒 **Inventory:**");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + "️⃣ " + items.get(i).getName());
            }
        }
    }

    /**
     * Returns the number of items in the inventory.
     * @return The size of the inventory.
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Returns the item at a specific index.
     * @param index The index of the item.
     * @return The item at the specified index or null if invalid.
     */
    public ItemHero getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    /**
     * Checks if the inventory contains any health potions.
     * @return True if there is at least one health potion.
     */
    public boolean hasPotion() {
        for (ItemHero item : items) {
            if (item instanceof HealthPotion) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the first health potion found in the inventory.
     * @return A health potion or null if none exist.
     */
    public HealthPotion getFirstPotion() {
        for (ItemHero item : items) {
            if (item instanceof HealthPotion) {
                return (HealthPotion) item;
            }
        }
        return null;
    }
}
