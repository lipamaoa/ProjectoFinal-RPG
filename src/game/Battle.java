package src.game;

import src.entities.Hero;

import java.util.ArrayList;
import java.util.Random;

import src.entities.Enemy;
import src.entities.FriendlyNPC;
import src.items.ItemBattle;
import src.items.ItemHero;
import src.items.Item;
import src.utils.AsciiArt;

/**
 * Handles turn-based battles with room-defined enemies and NPC hacking.
 */
public class Battle {
    private Hero player;
    private ArrayList<Enemy> enemies;
    private ArrayList<FriendlyNPC> friendlyNPCs;
    private final Random random;

    /**
     * Constructs a battle instance with a predefined list of enemies.
     *
     * @param player The player's hero.
     * @param enemy  The enemy in this room.
     */
    public Battle(Hero player, ArrayList<Enemy> enemies, ArrayList<FriendlyNPC> friendlyNPCs) {
        this.player = player;
        this.enemies = enemies;
        this.friendlyNPCs = friendlyNPCs;
        this.random = GameRandom.getInstance();
    }

    public Enemy getEnemy() {
        return enemies.get(this.random.nextInt(enemies.size()));
    }

    public Hero getPlayer() {
        return this.player;
    }

    /**
     * Starts the battle loop.
     */
    public void start() {
        AsciiArt.showBattleScreen();
        System.out.println("\n⚔️ A battle begins!");

        while (player.getCurrentHp() > 0 && !enemies.isEmpty()) {
            playerTurn();
            alliesTurn();
            enemiesTurn();
            removeDefeatedEntities();
        }

        endBattle();
    }

    private void displayStatus() {
        System.out.println("\n🔹 " + player.getName() + ": " + player.getCurrentHp() + " HP");
        if (friendlyNPCs != null) {
            System.out.println("🛡️ Friendly NPCs:");
            for (FriendlyNPC ally : friendlyNPCs) {
                System.out.println(" - " + ally.getName() + " (" + ally.getCurrentHp() + " HP)");
            }
        }
        System.out.println("\n👿 Enemies:");
        for (Enemy enemy : enemies) {
            System.out.println(" - " + enemy.getName() + " (" + enemy.getCurrentHp() + " HP)");
        }
    }

    /**
     * Handles the player's turn.
     */
    private void playerTurn() {
        displayStatus();
        System.out.println("\nChoose an action:");
        System.out.println("1️⃣ Attack an enemy");
        System.out.println("2️⃣ Use Item");

        int choice = GameScanner.getInt();

        switch (choice) {
            case 1 -> attackEnemy();
            case 2 -> useItem();
            default -> System.out.println("❌ Invalid choice! Turn skipped.");
        }
    }

    /**
     * Allows the player to attack an enemy.
     */
    private void attackEnemy() {
        if (enemies.isEmpty()) {
            System.out.println("⚠️ No enemies left to attack!");
            return;
        }

        System.out.println("\nChoose an enemy to attack:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out
                    .println((i + 1) + ") " + enemies.get(i).getName() + " (" + enemies.get(i).getCurrentHp() + " HP)");
        }

        int targetIndex = GameScanner.getInt() - 1;
        if (targetIndex >= 0 && targetIndex < enemies.size()) {
            player.attack(enemies.get(targetIndex));
        } else {
            System.out.println("❌ Invalid target!");
        }
    }

    /**
     * Handles friendly NPCs' turn.
     */
    private void alliesTurn() {
        if (friendlyNPCs == null) {
            return;
        }
        for (FriendlyNPC ally : friendlyNPCs) {
            if (ally.getCurrentHp() > 0 && !enemies.isEmpty()) {
                Enemy target = enemies.get(random.nextInt(enemies.size()));
                ally.attack(target);
            }
        }
    }

    /**
     * Handles the enemies' turn.
     */
    private void enemiesTurn() {
        for (Enemy enemy : enemies) {
            if (enemy.getCurrentHp() > 0) {
                if (random.nextBoolean() && friendlyNPCs != null && !friendlyNPCs.isEmpty()) {
                    FriendlyNPC target = friendlyNPCs.get(random.nextInt(friendlyNPCs.size()));
                    enemy.attack(target);
                } else {
                    enemy.attack(player);
                }
            }
        }
    }

    /**
     * Removes defeated enemies and NPCs from the battle.
     */
    private void removeDefeatedEntities() {
        enemies.removeIf(enemy -> enemy.getCurrentHp() <= 0);
        if (friendlyNPCs != null) {
            friendlyNPCs.removeIf(ally -> ally.getCurrentHp() <= 0);
        }
    }

    /**
     * Allows the player to use an item.
     */
    private void useItem() {
        if (player.getInventory().getSize() == 0) {
            System.out.println("❌ You have no items in your inventory!");
            return;
        }

        System.out.println("\n👜 Inventory:");
        player.getInventory().showInventory();

        System.out.println("\nChoose an item to use (or 0 to cancel):");
        int itemChoice = GameScanner.getInt();

        if (itemChoice == 0) {
            System.out.println("❌ Action canceled.");
            return;
        }

        // Validate selection
        if (itemChoice < 1 || itemChoice > player.getInventory().getSize()) {
            System.out.println("❌ Invalid selection! Please choose a valid item.");
            return;
        }

        // Retrieve selected item
        Item selectedItem = player.getInventory().getItem(itemChoice - 1);

        if (selectedItem != null) {
            // ✅ If the item is an EMP Grenade and the player is a PharmacologistHacker,
            // apply it to the enemy
            if (selectedItem instanceof ItemBattle battleItem) {
                battleItem.use(this);
                ;
            } else if (selectedItem instanceof ItemHero playerItem) {
                playerItem.use(player);
            }

            player.getInventory().removeItem(selectedItem);
            System.out.println("✔️ You used " + selectedItem.getName() + "!");
        } else {
            System.out.println("❌ Something went wrong! Could not use item.");
        }
    }

    /**
     * Ends the battle and determines the outcome.
     */
    private void endBattle() {
        if (player.getCurrentHp() > 0) {
            System.out.println("🎉 " + player.getName() + " won the battle!");
        } else {
            System.out.println("💀 " + player.getName() + " was defeated...");
        }
    }
}
