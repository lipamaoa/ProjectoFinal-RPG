package src.items;

import java.util.ArrayList;

/**
 * Manages the player's inventory.
 */
public class Inventory {
    private ArrayList<ItemHero> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds an item to the inventory.
     */
    public void addItem(ItemHero item) {
        items.add(item);
        System.out.println("📦 Added " + item.getName() + " to inventory.");
    }

    /**
     * Removes an item from the inventory.
     */
    public void removeItem(ItemHero item) {
        items.remove(item);
        System.out.println("🗑️ Used " + item.getName());
    }


    public int getSize() {
        return items.size();
    }

    /**
     * Displays all items in the inventory.
     */
    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("⚠️ Inventory is empty.");
        } else {
            System.out.println("\n🎒 Inventory:");
            for (int i = 0; i < items.size(); i++) {
                System.out.println((i + 1) + "️⃣ " + items.get(i).getName());
            }
        }
    }

    /**
     * Returns the item at a specific index.
     */
    public ItemHero getItem(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    /**
     * Checks if the inventory contains a specific item.
     */
    public boolean hasItem(String itemName) {
        for (ItemHero item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }
}

