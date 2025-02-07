package src.game;

import src.entities.Hero;
import src.entities.NPC;
import src.entities.PharmacologistHacker;
import src.utils.AsciiArt;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Handles turn-based battles with room-defined enemies and NPC hacking.
 */
public class Battle {
    private Hero player;
    private NPC enemy;
    private Scanner scanner;
    private static final Random random = new Random();

    /**
     * Constructs a battle instance with a predefined list of enemies.
     *
     * @param player The player's hero.
     * @param possibleEnemies The list of enemies available in this room.
     */
    public Battle(Hero player, NPC enemy) {
        this.player = player;
        this.enemy = enemy;
        this.scanner = new Scanner(System.in);
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
        player.usePotion();
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
