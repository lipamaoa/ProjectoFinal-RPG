package src.game;

import src.entities.Hero;
import src.entities.NPC;
import src.utils.AsciiArt;

import java.util.Scanner;

/**
 * Handles the turn-based battle system with improved enemy AI.
 */
public class Battle {
    private Hero player;
    private NPC enemy;
    private Scanner scanner;

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
        System.out.println("\n⚔️ A battle begins between " + player.getName() + " and " + enemy.getName() + "!");

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
        System.out.println("1️⃣ Attack");
        System.out.println("2️⃣ Special Attack");
        System.out.println("3️⃣ Use Item");
        System.out.println("4️⃣ Hack System (if applicable)");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                normalAttack();
                break;
            case 2:
                specialAttack();
                break;
            case 3:
                useItem();
                break;
            case 4:
                hackSystem();
                break;
            default:
                System.out.println("❌ Invalid choice! Turn skipped.");
        }
    }

    /**
     * Handles the enemy's turn.
     */
    private void enemyTurn() {
        System.out.println("🔴 " + enemy.getName() + "'s turn!");
        enemy.attack(player);
    }

    /**
     * Executes a normal attack.
     */
    private void normalAttack() {
        int damage = player.getStrength();
        System.out.println("🗡️ " + player.getName() + " attacks " + enemy.getName() + " for " + damage + " damage.");
        enemy.takeDamage(damage);
    }

    /**
     * Executes a special attack.
     */
    private void specialAttack() {
        int damage = player.getStrength() * 2;
        System.out.println("💥 " + player.getName() + " uses a special attack on " + enemy.getName() + " for " + damage + " damage!");
        enemy.takeDamage(damage);
    }

    /**
     * Uses an item from the inventory.
     */
    private void useItem() {
        if (player.getInventorySize() > 0) {
            player.usePotion();
        } else {
            System.out.println("⚠️ No items available.");
        }
    }

    /**
     * Hacks the system (only for Pharmacologist Hacker).
     */
    private void hackSystem() {
        if (player instanceof entities.PharmacologistHacker) {
            System.out.println("💻 Attempting to hack...");
            if (Math.random() < 0.5) { // 50% success rate
                System.out.println("✅ Hack successful! Enemy disabled.");
                enemy.takeDamage(enemy.getCurrentHp()); // Instant defeat
            } else {
                System.out.println("❌ Hack failed! The enemy attacks.");
            }
        } else {
            System.out.println("⚠️ You cannot hack!");
        }
    }

    /**
     * Ends the battle and determines the outcome.
     */
    private void endBattle() {
        if (player.getCurrentHp() > 0) {
            AsciiArt.showVictoryScreen();
            player.gainXP(10);
            player.collectGold(enemy.getGold());
        } else {
            AsciiArt.showDefeatScreen();
        }
    }
}
