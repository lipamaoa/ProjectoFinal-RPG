package src.game;

import src.entities.FriendlyNPC;
import src.entities.Hero;
import src.entities.NPC;

import java.util.Random;
import java.util.Scanner;

/**
 * Represents a room in the laboratory with different events.
 */
public class Room {
    private String name;
    private boolean hasEnemy;
    private boolean hasVendor;
    private boolean hasItem;
    private FriendlyNPC friendlyNPC;
    private static Random random = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public Room(String name, boolean hasEnemy, boolean hasVendor, boolean hasItem) {
        this.name = name;
        this.hasEnemy = hasEnemy;
        this.hasVendor = hasVendor;
        this.hasItem = hasItem;
        this.friendlyNPC = friendlyNPC;
    }

    /**
     * Executes the event in the room.
     */
    public void enter(Hero player) {
        System.out.println("\n🚪 Entering " + name + "...");

        if (friendlyNPC != null) {
            friendlyNPC.interact(player);
        }

        if (hasEnemy) {
            System.out.println("⚠️ An enemy appears!");
            NPC enemy = new NPC("Lab Guard", 40, 6, 10);
            Battle battle = new Battle(player, enemy);
            battle.start();
        }

        if (hasVendor) {
            System.out.println("🛒 You found a vendor!");
            Vendor vendor = new Vendor();
            vendor.buyItem(player);
        }

        if (hasItem) {
            System.out.println("🎁 You found an item!");
            player.addItemToInventory(new items.HealthPotion(5, 25));
        }

        System.out.println("✅ You finished exploring " + name + ".");
    }
}

     /**
     * Triggers a random event with a 50% probability.
     */
    private void triggerRandomEvent(Hero player) {
        int eventChance = random.nextInt(100); // 0 to 99
        if (eventChance < 50) { // 50% chance to trigger an event
            int eventType = random.nextInt(4); // 0 to 3 (4 different events)

            switch (eventType) {
                case 0:
                    treasureChest(player);
                    break;
                case 1:
                    trap(player);
                    break;
                case 2:
                    secretFile();
                    break;
                case 3:
                    chemicalSurprise(player);
                    break;
            }
        }
    }

    /**
     * The player finds a treasure chest and gains gold or an item.
     */
    private void treasureChest(Hero player) {
        System.out.println("🎁 You found a hidden chest!");
        int rewardType = random.nextInt(2);
        if (rewardType == 0) {
            int goldAmount = random.nextInt(15) + 5; // Between 5 and 20 gold
            player.addGold(goldAmount);
            System.out.println("💰 You found " + goldAmount + " gold inside!");
        } else {
            System.out.println("🧪 You found a rare chemical!");
            player.addItemToInventory(new items.HealthPotion(10, 50)); // Stronger potion
        }
    }

    /**
     * The player falls into a trap and loses health.
     */
    private void trap(Hero player) {
        System.out.println("☠️ You stepped on a trap!");
        int damage = random.nextInt(15) + 5; // Between 5 and 20 damage
        player.takeDamage(damage);
        System.out.println("💀 You lost " + damage + " HP.");
    }

    /**
     * The player finds secret files revealing the truth about PharmaCorp.
     */
    private void secretFile() {
        System.out.println("📜 You discovered a hidden document!");
        System.out.println("It contains classified information about PharmaCorp's illegal experiments...");
    }

    /**
     * The player drinks a chemical, resulting in a random effect.
     */
    private void chemicalSurprise(Hero player) {
        System.out.println("⚗️ You found a mysterious chemical sample.");
        System.out.println("Do you want to drink it? (1 = Yes, 2 = No)");

        int choice = scanner.nextInt();
        if (choice == 1) {
            int effect = random.nextInt(2);
            if (effect == 0) {
                int hpBoost = random.nextInt(15) + 5; // Gain between 5 and 20 HP
                player.heal(hpBoost);
                System.out.println("💊 The chemical healed you for " + hpBoost + " HP!");
            } else {
                int damage = random.nextInt(10) + 5; // Lose between 5 and 15 HP
                player.takeDamage(damage);
                System.out.println("💀 The chemical was toxic! You lost " + damage + " HP.");
            }
        } else {
            System.out.println("⚠️ You ignored the chemical.");
        }
    }
}
