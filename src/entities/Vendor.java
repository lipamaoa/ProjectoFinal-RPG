package src.entities;

import src.items.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a vendor in the game where players can buy and sell items.
 */
public class Vendor {
    private List<Item> storeInventory;
    private Scanner scanner;

    public Vendor() {
        this.storeInventory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Generates store inventory dynamically from `ItemHeroRegistry` based on player
     * type.
     */
    public void generateStoreInventory(Hero player) {
        storeInventory.clear();
        System.out.println("🛒 The vendor has restocked items based on your expertise...");

        // Get hero-specific items from the registry
        storeInventory.addAll(ItemHeroRegistry.getItemsForHero(player));

        // Add common items for all hero types
        storeInventory.addAll(ItemHeroRegistry.HEALING_ITEMS);
        storeInventory.addAll(ItemHeroRegistry.WEAPONS);
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
     */
    public void buyItems(Hero player) {
        while (true) {
            showStore();
            System.out.println("\n💰 Your gold: " + player.getGold());
            System.out.println("Enter the number of the item to buy (or 0 to exit): ");

            int choice = scanner.nextInt();
            if (choice == 0)
                break;

            if (choice > 0 && choice <= storeInventory.size()) {
                Item selectedItem = storeInventory.get(choice - 1);

                // Show item details before purchase
                System.out.println("\n📜 " + selectedItem.getName() + ": " + selectedItem.getDescription());
                System.out.println("💰 Price: " + selectedItem.getPrice() + " gold");
                System.out.println("Confirm purchase? (1 = Yes, 2 = No)");

                int confirm = scanner.nextInt();
                if (confirm == 1) {
                    if (player.getGold() >= selectedItem.getPrice()) {
                        player.spendGold(selectedItem.getPrice());
                        player.addItemToInventory(selectedItem);
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
     */
    public void sellItems(Hero player) {
        while (true) {
            System.out.println("\n🎒 Your Inventory:");
            player.showInventory();
            System.out.println("\n💰 Your gold: " + player.getGold());
            System.out.println("Enter the number of the item to sell (or 0 to exit): ");

            int choice = scanner.nextInt();
            if (choice == 0)
                break;

            if (choice > 0 && choice <= player.getInventorySize()) {
                Item itemToSell = player.getInventory().getItem(choice - 1);
                int sellPrice = itemToSell.getPrice() / 2; // Player gets half the original price

                player.collectGold(sellPrice);
                player.getInventory().removeItem(itemToSell);
                System.out.println("✅ You sold " + itemToSell.getName() + " for " + sellPrice + " gold!");
            } else {
                System.out.println("❌ Invalid choice!");
            }
        }
    }
}
