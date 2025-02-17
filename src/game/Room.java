package src.game;

import src.entities.*;
import src.items.HealthPotion;
import src.items.HealthPotionSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a room where the player can encounter battles, NPCs, and unique
 * events.
 */
public class Room {
    private final String name;
    private final Enemy roomEnemy;
    private final FriendlyNPC friendlyNPC;
    private final Vendor vendor;
    private boolean isCompleted = false;
    private boolean isVendorRoom = false;
    private final Random random;

    /**
     * Constructs a room with predefined enemies and NPC interactions.
     *
     * @param name            The name of the room.
     * @param possibleEnemies The list of enemies that can appear in this room.
     * @param friendlyNPC     The friendly NPC in this room (if any).
     */
    public Room(String name, List<Enemy> possibleEnemies, FriendlyNPC friendlyNPC, boolean isVendorRoom) {
        this.name = name;
        this.random = GameRandom.getInstance();
        this.roomEnemy = possibleEnemies != null && !possibleEnemies.isEmpty()
                ? possibleEnemies.get(this.random.nextInt(possibleEnemies.size()))
                : null;
        this.friendlyNPC = friendlyNPC;
        this.isVendorRoom = isVendorRoom;
        this.vendor = isVendorRoom ? new Vendor() : null;
    }

    private void continueStory(Hero player) {
        boolean continueJourney = false;

        while (!continueJourney) {
            System.out.println("1️⃣ Hero Passport");
            System.out.println("2️⃣ Check inventory");
            System.out.println("Or continue the story ➡️");
            String option = GameScanner.getString();

            switch (option) {
                case "1":
                    player.showDetails();
                    break;
                case "2":
                    player.getInventory().showInventory();
                    break;

                default:
                    System.out.println("Our jorney continues...🥾");
                    continueJourney = true;
                    break;
            }
        }
    }

    public List<Enemy> getEnemies() {
        return roomEnemy != null ? List.of(roomEnemy) : new ArrayList<>();
    }

    public FriendlyNPC getFriendlyNPC() {
        return friendlyNPC;
    }

    /**
     * Triggers room events, including battles and NPC interactions.
     *
     * @param player The player's hero.
     */
    public void enter(Hero player) {
        System.out.println("\n🚪 Entering " + name + "...");
        continueStory(player);

        // Interact with NPC (if available)
        if (friendlyNPC != null) {
            friendlyNPC.interact(player);
            continueStory(player);
        }

        // Offer vendor interaction if is a vendor room
        if (isVendorRoom) {
            accessVendor(player);
            continueStory(player);
        }

        // Trigger a battle if there are enemies in this room
        if (this.roomEnemy != null) {
            System.out.println("⚠️ An enemy appears!");

            Battle battle = new Battle(player, this.roomEnemy);
            battle.start();

            // If the player dies, stop further events
            if (player.getCurrentHp() <= 0) {
                return;
            }

            continueStory(player);
        }

        // Trigger a random event (loot, trap, special effect)
        triggerRandomEvent(player);

        // Mark the room as completed
        isCompleted = true;

        System.out.println("✅ You finished exploring " + name + ".");
    }

    /**
     * Allows the player to access the vendor to buy and sell items.
     */
    private void accessVendor(Hero player) {
        System.out.println("\n🛒 You have found a **vendor**!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1️⃣ Buy Items");
            System.out.println("2️⃣ Sell Items");
            System.out.println("3️⃣ Exit Shop");

            int choice = GameScanner.getInt();
            switch (choice) {
                case 1 -> vendor.buyItems(player);
                case 2 -> vendor.sellItems(player);
                case 3 -> {
                    System.out.println("🚪 You leave the vendor.");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    public void setVendorRoom(boolean isVendorRoom) {
        this.isVendorRoom = isVendorRoom;
    }

    /**
     * Triggers a random event in the room (50% chance).
     */
    private void triggerRandomEvent(Hero player) {
        if (random.nextInt(100) < 50) { // 50% chance of an event
            int eventType = random.nextInt(4); // Select a random event

            switch (eventType) {
                case 0 -> treasureChest(player);
                case 1 -> trap(player);
                case 2 -> secretFile();
                case 3 -> chemicalSurprise(player);
            }

            continueStory(player);
        }
    }

    /**
     * The player finds a hidden chest containing gold or a powerful item.
     */
    private void treasureChest(Hero player) {
        System.out.println("🎁 You found a hidden chest!");
        int rewardType = random.nextInt(2);
        if (rewardType == 0) {
            int goldAmount = random.nextInt(15) + 5; // Between 5 and 20 gold
            player.collectGold(goldAmount);
            System.out.println("💰 You found " + goldAmount + " gold inside!");
        } else {
            System.out.println("🧪 You found a rare chemical!");
            player.addItemToInventory(new HealthPotion(HealthPotionSize.Large)); // Stronger potion
        }
    }

    /**
     * The player steps on a trap and loses health.
     */
    private void trap(Hero player) {
        System.out.println("☠️ You stepped on a trap!");
        int damage = random.nextInt(15) + 5; // Between 5 and 20 damage
        player.takeDamage(damage);
        System.out.println("💀 You lost " + damage + " HP.");
    }

    /**
     * The player finds secret files revealing PharmaCorp's illegal experiments.
     */
    private void secretFile() {
        System.out.println("📜 You discovered a hidden document!");
        System.out.println("It contains classified information about PharmaCorp's unethical projects...");
    }

    /**
     * The player drinks an unknown chemical, which has a random effect.
     */
    private void chemicalSurprise(Hero player) {
        System.out.println("⚗️ You found a mysterious chemical sample.");
        System.out.println("Do you want to drink it? (1 = Yes, 2 = No)");

        int choice = new java.util.Scanner(System.in).nextInt();
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

    /**
     * Checks if the room has been completed.
     *
     * @return true if the room is completed, false otherwise.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    public String getName() {
        return name;
    }
}
