package src.game;

import src.entities.Enemy;
import src.entities.FriendlyNPC;
import src.entities.Hero;
import src.entities.Vendor;
import src.items.*;
import src.status.Burning;
import src.status.Poisoned;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a room where the player can encounter battles, NPCs, and unique
 * events.
 */
public class Room {
    private final String name;
    private final ArrayList<Enemy> roomEnemies;
    private final FriendlyNPC friendlyNPC;
    private final Vendor vendor;
    private boolean isCompleted = false;
    private final Random random;

    /**
     * Constructs a room with predefined enemies and NPC interactions.
     *
     * @param name            The name of the room.
     * @param possibleEnemies The list of enemies that can appear in this room.
     * @param friendlyNPC     The friendly NPC in this room (if any).
     * @param vendor          The vendor, if present.
     */
    public Room(String name, List<Enemy> possibleEnemies, FriendlyNPC friendlyNPC, Vendor vendor) {
        this.name = name;
        this.random = GameRandom.getInstance();
        this.roomEnemies = new ArrayList<>();

        // Randomly select enemies to appear in the room
        if (possibleEnemies != null && !possibleEnemies.isEmpty()) {
            this.roomEnemies.addAll(possibleEnemies);
            // At least 1 enemy
            int numberOfEnemies = this.random.nextInt(possibleEnemies.size()) + 1;
            while (this.roomEnemies.size() > numberOfEnemies) {
                this.roomEnemies.remove(this.random.nextInt(this.roomEnemies.size()));
            }
        }
        this.friendlyNPC = friendlyNPC;
        this.vendor = vendor;
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
                    showInventory(player);
                    break;

                default:
                    System.out.println("\n\uD83C\uDF0D OUR JOURNEY CONTINUES...🥾");
                    continueJourney = true;
                    break;
            }
        }

        // Continue processing any affecting statuses
        player.processStatuses();
    }

    private void showInventory(Hero player) {
        while (true) {
            System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.printf(" 💰 Gold: %d  |  ❤ HP: %d/%d\n", player.getGold(), player.getCurrentHp(),
                    player.getMaxHp());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            player.showInventory();
            if (player.getInventory().getSize() == 0) {
                System.out.println("📦 Your inventory is empty.");
                break;
            }
            System.out.println("\n🎒 Choose an item to use (or 0 to cancel): ");
            int itemChoice = GameScanner.getInt();

            if (itemChoice == 0) {
                System.out.println("❌ Action canceled.\n");
                break;
            }

            // Validate selection
            if (itemChoice < 1 || itemChoice > player.getInventory().getSize()) {
                System.out.println("❌ Invalid selection! Please choose a valid item.");
                break;
            }

            // Retrieve selected item
            Item selectedItem = player.getInventory().getItem(itemChoice - 1);

            if (selectedItem != null) {
                if (selectedItem instanceof ItemHero playerItem) {
                    playerItem.use(player);
                } else if (selectedItem instanceof Weapon weapon) {
                    Weapon currentWeapon = player.getEquipedWeapon();
                    player.equipWeapon(weapon);
                    if (currentWeapon != null) {
                        player.addItemToInventory(currentWeapon);
                    }
                } else {
                    System.out.println("❌ You can't use this at the moment.");
                    return;
                }

                player.getInventory().removeItem(selectedItem);
                break;
            } else {
                System.out.println("❌ Something went wrong! Could not use item.");
            }
        }
    }

    /**
     * Handles interactions and actions available in the room.
     *
     * @param player The hero exploring the room.
     */
    public void enter(Hero player) {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🚪 Entering " + name + "...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        continueStory(player);

        // Interact with NPC (if available)
        if (friendlyNPC != null) {
            System.out.printf("\n🗣️  You encounter **%s**.\n", friendlyNPC.getName());
            friendlyNPC.interact(player);
            continueStory(player);
        }

        // Offer vendor interaction if is a vendor room
        if (this.vendor != null) {
            System.out.printf("\n🛒  A vendor is present in **%s**.\n", name);
            accessVendor(player);
            continueStory(player);
        }

        // Trigger a battle if there are enemies in this room
        if (!this.roomEnemies.isEmpty()) {
            Battle battle = new Battle(player, this.roomEnemies,
                    friendlyNPC != null && friendlyNPC.willJoinBattle() ? new ArrayList<>(List.of(friendlyNPC)) : null);
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
     * Allows the player to interact with the vendor.
     *
     * @param player The hero interacting with the vendor.
     */
    private void accessVendor(Hero player) {
        this.vendor.generateStoreInventory(player);

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1️⃣ Buy Items");
            System.out.println("2️⃣ Sell Items");
            System.out.println("0️⃣ Exit Shop");

            int choice = GameScanner.getInt();
            switch (choice) {
                case 1 -> this.vendor.buyItems(player);
                case 2 -> this.vendor.sellItems(player);
                case 0 -> {
                    System.out.println("🚪 You leave the vendor.\n");
                    return;
                }
                default -> System.out.println("❌ Invalid choice! Try again.");
            }
        }
    }

    /**
     * Triggers a random event in the room (50% chance).
     */
    private void triggerRandomEvent(Hero player) {
        if (random.nextInt(100) < 50) {
            int eventType = random.nextInt(5);

            switch (eventType) {
                case 0 -> treasureChest(player);
                case 1 -> fireTrap(player);
                case 2 -> secretFile(player);
                case 3 -> chemicalSurprise(player);
                case 4 -> healingFountain(player);
            }

            continueStory(player);
        }
    }

    private void healingFountain(Hero player) {
        System.out.println("\n🌊 You found a healing fountain!");
        player.removeNegativeStatuses();
        player.heal(25);
        System.out.println("💧 You feel refreshed and healed for 25 HP!\n");
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
     * The player steps on a fire trap.
     */
    private void fireTrap(Hero player) {
        System.out.println("☠️ You stepped on a 🔥 fire trap! You are burning.");
        player.applyStatus(new Burning(3, 15));
    }

    /**
     * The player finds secret files revealing PharmaCorp's illegal experiments.
     */
    private void secretFile(Hero player) {
        System.out.println("📜 You discovered a hidden document!");
        System.out.println("It contains classified information about PharmaCorp's unethical projects...");
        player.addItemToInventory(
                new ItemHero("Secret File", "📜 Classified document", 75, null, (Hero p) -> System.out.println(
                        "You read the secret file and learn about PharmaCorp's illegal experiments. Could probably have sold it for a good price.")));
    }

    /**
     * The player drinks an unknown chemical, which has a random effect.
     */
    private void chemicalSurprise(Hero player) {
        System.out.println("⚗️ You found a mysterious chemical sample.");
        System.out.println("Do you want to drink it? (1 = Yes, 2 = No)");

        int choice = GameScanner.getInt();
        if (choice == 1) {
            int effect = random.nextInt(2);
            if (effect == 0) {
                int hpBoost = random.nextInt(15) + 5;
                player.heal(hpBoost);
                System.out.println("💊 The chemical healed you for " + hpBoost + " HP!");
            } else {
                System.out.println("💀 The chemical was toxic! You are poisoned.");
                player.applyStatus(new Poisoned(5, 5));
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

    /**
     * Retrieves the surviving friendly NPC, if any.
     *
     * @return The surviving friendly NPC or null if none exist.
     */
    public FriendlyNPC getSurvivingFriendlyNPC() {
        if (this.friendlyNPC != null && this.friendlyNPC.willJoinBattle() && this.friendlyNPC.getCurrentHp() > 0) {
            return this.friendlyNPC;
        }

        return null;
    }

    /**
     * Retrieves the name of the room.
     *
     * @return The room's name.
     */
    public String getName() {
        return name;
    }
}
