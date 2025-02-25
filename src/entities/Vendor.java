package src.entities;

import src.game.GameScanner;
import src.items.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vendor in the game where players can buy and sell items.
 */
public class Vendor {
    private final List<Item> storeInventory;

    /**
     * Constructs a Vendor with an empty inventory.
     */

    public Vendor() {
        this.storeInventory = new ArrayList<>();
    }

    /**
     * Generates store inventory dynamically from `ItemRegistry` based on player
     * type.
     *
     * @param player The player whose class determines the inventory items.
     */
    public void generateStoreInventory(Hero player) {
        storeInventory.clear();
        System.out.println("🛒 The vendor has restocked items based on your expertise...");

        // Get hero-specific items from the registry
        storeInventory.addAll(ItemRegistry.getItemsForHero(player));

        // Add common items for all hero types
        storeInventory.addAll(ItemRegistry.HEALING_ITEMS);
        storeInventory.addAll(ItemRegistry.WEAPONS);
    }

    /**
     * Displays the store inventory.
     */
    public void showStore() {
        System.out.println("\n🛒 Vendor Store - Available Items:");
        for (int i = 0; i < storeInventory.size(); i++) {
            Item item = storeInventory.get(i);
            System.out.println((i + 1) + "️⃣ " + item.getName() + " - " + item.getPrice() + " gold");
            System.out.println("   📜 " + item.getDescription()); // Show item description
        }
        System.out.println("0️⃣ Exit shop");
    }

    /**
     * Allows the player to buy an item.
     *
     * @param player The player attempting to buy an item.
     */
    public void buyItems(Hero player) {

        while (true) {
            if (this.storeInventory.isEmpty()) {
                System.out.println("The vendor has nothing else to sell to you.");
                break;
            }
            showStore();
            System.out.println("\n💰 Your gold: " + player.getGold());
            System.out.println("Enter the number of the item to buy (or 0 to exit): ");

            int choice = GameScanner.getInt();
            if (choice == 0)
                break;

            if (choice > 0 && choice <= storeInventory.size()) {
                Item selectedItem = storeInventory.get(choice - 1);

                // Show item details before purchase
                System.out.println("\n📜 " + selectedItem.getName() + ": " + selectedItem.getDescription());
                System.out.println("💰 Price: " + selectedItem.getPrice() + " gold");
                System.out.println("Confirm purchase? (1 = Yes, 2 = No)");

                int confirm = GameScanner.getInt();
                if (confirm == 1) {
                    if (player.getGold() >= selectedItem.getPrice()) {
                        player.spendGold(selectedItem.getPrice());
                        player.addItemToInventory(selectedItem);
                        this.storeInventory.remove(selectedItem);
                        System.out.println("✅ You purchased " + selectedItem.getName() + "!");
                    } else {
                        System.out.println("❌ Not enough gold!");
                    }
                } else {
                    System.out.println("❌ Purchase canceled.");
                }
            } else {
                System.out.println("❌ Invalid choice!");
            }
        }
    }

    /**
     * Allows the player to sell an item.
     *
     * @param player The player attempting to sell an item.
     */
    public void sellItems(Hero player) {
        while (true) {
            player.showInventory();
            System.out.println("\n💰 Your gold: " + player.getGold());
            System.out.println("Select an item to sell. This vendor will pay half its value.");
            System.out.println("Enter the number of the item to sell (or 0 to exit): ");

            int choice = GameScanner.getInt();
            if (choice == 0)
                break;

            if (choice > 0 && choice <= player.getInventorySize()) {
                Item itemToSell = player.getInventory().getItem(choice - 1);
                int sellPrice = itemToSell.getPrice() / 2;

                player.collectGold(sellPrice);
                player.getInventory().removeItem(itemToSell);
                System.out.println("✅ You sold " + itemToSell.getName() + " for " + sellPrice + " gold!");
            } else {
                System.out.println("❌ Invalid choice!");
            }
        }
    }
}
