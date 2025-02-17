package src.game;

import src.entities.Hero;
import src.entities.Enemy;
import src.entities.PharmacologistHacker;
import src.items.ItemBattle;
import src.items.ItemHero;
import src.items.Item;
import src.utils.AsciiArt;

import java.util.Scanner;

/**
 * Handles turn-based battles with room-defined enemies and NPC hacking.
 */
public class Battle {
    private Hero player;
    private Enemy enemy;
    private Scanner scanner;

    /**
     * Constructs a battle instance with a predefined list of enemies.
     *
     * @param player The player's hero.
     * @param enemy  The enemy in this room.
     */
    public Battle(Hero player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.scanner = new Scanner(System.in);
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Hero getPlayer() {
        return this.player;
    }

    /**
     * Starts the battle loop.
     */
    public void start() {
        AsciiArt.showBattleScreen();
        System.out.println("\n⚔️ A battle begins against " + enemy.getName() + "!");

        while (player.getCurrentHp() > 0 && enemy.getCurrentHp() > 0) {
            playerTurn();
            if (enemy.getCurrentHp() > 0) {
                enemyTurn();
            }
        }

        endBattle();
    }

    /**
     * Handles the player's turn.
     */
    private void playerTurn() {
        System.out.println("\n🔹 " + player.getName() + ": " + player.getCurrentHp() + " HP");
        System.out.println("🔻 " + enemy.getName() + ": " + enemy.getCurrentHp() + " HP");

        System.out.println("\nChoose an action:");
        System.out.println("1️⃣ Attack " + enemy.getName());
        System.out.println("2️⃣ Use Item");

        if (player instanceof PharmacologistHacker) {
            System.out.println("3️⃣ Hack " + enemy.getName());
        }

        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> attackEnemy();
            case 2 -> useItem();
            case 3 -> {
                if (player instanceof PharmacologistHacker) {
                    hackEnemy();
                } else {
                    System.out.println("❌ Invalid choice!");
                }
            }
            default -> System.out.println("❌ Invalid choice! Turn skipped.");
        }
    }

    /**
     * Allows the player to attack the enemy NPC.
     */
    private void attackEnemy() {
        if (enemy.getCurrentHp() > 0) {
            player.attack(enemy);
        } else {
            System.out.println("⚠️ " + enemy.getName() + " is already defeated!");
        }
    }

    /**
     * Allows the player to hack the enemy.
     */
    private void hackEnemy() {
        if (!(player instanceof PharmacologistHacker)) {
            System.out.println("⚠️ Hacking is only available for PharmacologistHackers!");
            return;
        }

        PharmacologistHacker hacker = (PharmacologistHacker) player;
        hacker.hackEnemy(enemy, enemy);

        System.out.println("💻 " + enemy.getName() + " is confused and attacks itself!");
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
        int itemChoice = scanner.nextInt();

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
     * Handles the enemy's turn.
     */
    private void enemyTurn() {
        System.out.println("\n🔴 " + enemy.getName() + "'s turn!");
        enemy.attack(player);
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
