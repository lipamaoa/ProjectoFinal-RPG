package src.game;

import src.entities.*;
import src.items.Weapon;
import src.utils.AsciiArt;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main game class that handles character creation, storytelling, and game loop.
 */
public class Game {
    private static Scanner scanner = new Scanner(System.in);
    private static Hero player;
    private static List<NPC> enemies = new ArrayList<>();
    private static Map gameMap;

    public static void main(String[] args) {
        // Display ASCII Welcome Screen
        AsciiArt.showWelcomeScreen();

        // Story Introduction
        introduceStory();

        // Create Player Character
        player = createHero();

        // Initialize Map
        gameMap = new Map();
        gameMap.startExploring(player);

        // Check if player is still alive for the final battle
        if (player.getCurrentHp() > 0) {
            startFinalBattle();
        }

        // End the game
        endGame();
    }

    /**
     * Displays the introduction story.
     */
    private static void introduceStory() {
        System.out.println("\n📝 **Prologue**:");
        System.out.println("You were one of PharmaCorp’s top scientists, working on Project Eden-9.");
        System.out.println("But when you discovered the unethical experiments they were running...");
        System.out.println("...you knew you had to escape.");
        System.out.println("With enemies on your trail and a maze-like lab to navigate,");
        System.out.println("your survival depends on your wits, skills, and the choices you make.\n");
    }

    /**
     * Creates a character based on user input.
     * Allows the player to choose hero type, difficulty, and distribute stat points.
     * 
     * @return The created hero.
     */
    private static Hero createHero() {
        System.out.print("\n📝 Enter your name: ");
        String playerName = scanner.nextLine();

        // Choose Hero Type
        System.out.println("\n" + playerName + ", choose your hero type:");
        System.out.println("1️⃣ Pharmacologist Hacker - Expert in hacking and toxins.");
        System.out.println("2️⃣ Bioengineer - Specializes in regeneration and biological enhancements.");
        System.out.println("3️⃣ Tactical Chemist - Uses chemical explosives and reactive combat.");

        int heroChoice;
        while (true) {
            System.out.print("Enter your choice (1-3): ");
            heroChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (heroChoice >= 1 && heroChoice <= 3) break;
            System.out.println("❌ Invalid choice. Please select again.");
        }

        // Choose Difficulty
        System.out.println("\nChoose your difficulty:");
        System.out.println("1️⃣ Easy (300 stat points, 20 gold)");
        System.out.println("2️⃣ Hard (220 stat points, 15 gold)");

        int difficultyChoice;
        int statPoints;
        int gold;
        while (true) {
            System.out.print("Enter your choice (1-2): ");
            difficultyChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            if (difficultyChoice == 1) {
                statPoints = 300;
                gold = 20;
                break;
            } else if (difficultyChoice == 2) {
                statPoints = 220;
                gold = 15;
                break;
            }
            System.out.println("❌ Invalid choice. Please select again.");
        }

        // Allocate Stat Points
        int health = 0;
        int strength = 0;
        while (statPoints > 0) {
            System.out.println("\nRemaining stat points: " + statPoints);
            System.out.print("Allocate points to HP (1 point per HP): ");
            int hpPoints = scanner.nextInt();
            if (hpPoints > statPoints) {
                System.out.println("❌ Not enough points!");
                continue;
            }

            health += hpPoints;
            statPoints -= hpPoints;

            System.out.print("Allocate points to Strength (5 points per Strength): ");
            int strengthPoints = scanner.nextInt();
            if (strengthPoints * 5 > statPoints) {
                System.out.println("❌ Not enough points!");
                continue;
            }

            strength += strengthPoints;
            statPoints -= (strengthPoints * 5);
        }

        System.out.println("\n✅ Character creation complete!");
        System.out.println("🦸 Name: " + playerName);
        System.out.println("💖 HP: " + health);
        System.out.println("💪 Strength: " + strength);
        System.out.println("💰 Gold: " + gold);


            // Assign Default Weapons from the Inventory System
    Weapon defaultWeapon;
    switch (heroChoice) {
        case 1:
            defaultWeapon = new Weapon("Toxic Dart Gun", 10, 3, 5);
            return new PharmacologistHacker(playerName, health, strength, 1, gold, defaultWeapon);
        case 2:
            defaultWeapon = new Weapon("Bioelectric Gloves", 12, 4, 6);
            return new Bioengineer(playerName, health, strength, 1, gold, defaultWeapon);
        case 3:
            defaultWeapon = new Weapon("Chemical Grenades", 14, 5, 7);
            return new TacticalChemist(playerName, health, strength, 1, gold, defaultWeapon);
        default:
            return null;
    }

    
    }

    /**
     * Starts the final battle against the boss.
     */
    private static void startFinalBattle() {
        AsciiArt.showBattleScreen();
        System.out.println("\n⚔️ **FINAL BATTLE BEGINS!** ⚔️");

        NPC finalBoss = new NPC("Prototype Eden-9", 120, 20, 70, 5);
        enemies.add(finalBoss);
        Battle finalBattle = new Battle(player, finalBoss);
        finalBattle.start();
    }

    /**
     * Ends the game with different conclusions.
     */
    private static void endGame() {
        if (player.getCurrentHp() > 0) {
            System.out.println("\n🏆 **You escaped the laboratory!**");
            System.out.println("But PharmaCorp's influence extends beyond these walls...");
            System.out.println("Will you expose their secrets or go into hiding?");
        } else {
            System.out.println("\n💀 **You did not survive...**");
            System.out.println("Your research and discoveries will remain buried within PharmaCorp.");
        }

        System.out.println("\n🎮 **Game Over! Thanks for playing.**");
    }
}
