package src.game;

import src.items.HealthPotion;
import src.items.ItemHero;
import src.items.Weapon;
import src.entities.Hero;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a vendor in the game where players can buy and sell items.
 */
public class Vendor {
    private ArrayList<ItemHero> storeInventory;
    private Random random;
    private Scanner scanner;

    public Vendor() {
        this.storeInventory = new ArrayList<>();
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        generateStoreInventory();
    }

    /**
     * Generates random items for sale.
     */
    private void generateStoreInventory() {
        // Add some predefined items to the store
        storeInventory.add(new HealthPotion(5, 25));
        storeInventory.add(new Weapon("Chemical Blade", 15, 8, 12));
        storeInventory.add(new Weapon("Toxic Dart Gun", 20, 10, 15));
    }

    /**
     * Displays the store inventory.
     */
    public void showStore() {
        System.out.println("\n🛒 Vendor Store - Available Items:");
        for (int i = 0; i < storeInventory.size(); i++) {
            System.out.println((i + 1) + "️⃣ " + storeInventory.get(i).getName() + " - Price: " + storeInventory.get(i).getPrice() + " gold");
        }
        System.out.println("0️⃣ Exit shop");
    }

    /**
     * Allows the player to buy an item.
     */
    public void buyItem(Hero player) {
        while (true) {
            showStore();
            System.out.println("\n💰 Your gold: " + player.getGold());
            System.out.println("Enter the number of the item to buy (or 0 to exit): ");
            
            int choice = scanner.nextInt();
            if (choice == 0) break;

            if (choice > 0 && choice <= storeInventory.size()) {
                ItemHero selectedItem = storeInventory.get(choice - 1);

                if (player.getGold() >= selectedItem.getPrice()) {
                    player.spendGold(selectedItem.getPrice());
                    player.addItemToInventory(selectedItem);
                    System.out.println("✅ You purchased " + selectedItem.getName() + "!");
                } else {
                    System.out.println("❌ Not enough gold!");
                }
            } else {
                System.out.println("❌ Invalid choice!");
            }
        }
    }

        /**
     * Allows the player to sell an item.
     */
    public void sellItem(Hero player) {
        while (true) {
            System.out.println("\n🎒 Your Inventory:");
            player.showInventory();
            System.out.println("\n💰 Your gold: " + player.getGold());
            System.out.println("Enter the number of the item to sell (or 0 to exit): ");

            int choice = scanner.nextInt();
            if (choice == 0) break;

            if (choice > 0 && choice <= player.getInventorySize()) {
                ItemHero itemToSell = player.getInventory().getItem(choice - 1);
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

