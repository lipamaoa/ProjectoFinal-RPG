package src.game;

import src.entities.*;
import src.items.HealthPotion;
import src.items.HealthPotionSize;

import java.util.List;
import java.util.Random;

/**
 * Represents a room where the player can encounter battles, NPCs, and unique events.
 */
public class Room {
    private String name;
    private List<NPC> possibleEnemies;
    private FriendlyNPC friendlyNPC;
    private boolean isCompleted = false;
    private static final Random random = new Random();

    /**
     * Constructs a room with predefined enemies and NPC interactions.
     *
     * @param name            The name of the room.
     * @param possibleEnemies The list of enemies that can appear in this room.
     * @param friendlyNPC     The friendly NPC in this room (if any).
     */
    public Room(String name, List<NPC> possibleEnemies, FriendlyNPC friendlyNPC) {
        this.name = name;
        this.possibleEnemies = possibleEnemies;
        this.friendlyNPC = friendlyNPC;
    }

    /**
     * Triggers room events, including battles and NPC interactions.
     *
     * @param player The player's hero.
     */
    public void enter(Hero player) {
        System.out.println("\n🚪 Entering " + name + "...");

        // Interact with NPC (if available)
        if (friendlyNPC != null) {
            friendlyNPC.interact(player);
        }

        // Trigger a battle if there are enemies in this room
        if (!possibleEnemies.isEmpty()) {
            System.out.println("⚠️ An enemy appears!");

            // Select a random enemy from the list
            NPC enemy = selectRandomEnemy();
            Battle battle = new Battle(player,enemy);
            battle.start();

            // If the player dies, stop further events
            if (player.getCurrentHp() <= 0) {
                return;
            }
        }

        // Trigger a random event (loot, trap, special effect)
        triggerRandomEvent(player);

        // Mark the room as completed
        isCompleted = true;

        System.out.println("✅ You finished exploring " + name + ".");
    }

    /**
     * Randomly selects an enemy from the list of possible enemies in this room.
     *
     * @return A randomly selected enemy.
     */
    private NPC selectRandomEnemy() {
        return possibleEnemies.get(random.nextInt(possibleEnemies.size()));
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
