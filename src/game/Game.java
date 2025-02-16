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

    private static boolean isNameValid(String name) {
        if (name.length() < 3 || name.length() > 12) {
            return false;
        }

        return true;
    }

    /**
     * Creates a character based on user input.
     * Allows the player to choose hero type, difficulty, and distribute stat points.
     *
     * @return The created hero.
     */
    private static Hero createHero() {
        String playerName = "";

        while (!isNameValid(playerName)) {
            System.out.print("\n📝 Enter your name: ");
            playerName = scanner.nextLine();
        }

        // Choose Hero Type
        System.out.println("\n" + playerName + ", choose your hero type:");
        System.out.println("1️⃣ Pharmacologist Hacker - Expert in hacking and toxins.");
        System.out.println("2️⃣ Bioengineer - Specializes in regeneration and biological enhancements.");
        System.out.println("3️⃣ Tactical Chemist - Uses chemical explosives and reactive combat.");

        int heroChoice = getValidInput("Enter your choice ", 1, 3);

        // Choose Difficulty
        System.out.println("\nChoose your difficulty:");
        System.out.println("1️⃣ Easy (300 stat points, 20 gold)");
        System.out.println("2️⃣ Hard (220 stat points, 15 gold)");

        int difficultyChoice = getValidInput("Enter your choice ", 1, 2);
        int statPoints = (difficultyChoice == 1) ? 300 : 220;
        int gold = (difficultyChoice == 1) ? 20 : 15;

        // Allocate Stat Points
        int health = 0;
        int strength = 0;

        while (statPoints > 0) {
            System.out.println("\nRemaining stat points: " + statPoints);
            System.out.println("Points can be allocated between HP and Strength.");
            System.out.println("1 stat point = 1 ❤\uFE0FHP.\n5 stat point = 1 💪Strength");

            // Allocate HP
            int hpPoints = getValidInput("Allocate points to HP: ", 0, statPoints);
            health += hpPoints;
            statPoints -= hpPoints;

            // Allocate Strength (5 points per Strength)
            if (statPoints > 0) {
                int maxStrength = statPoints / 5;
                System.out.println("💪 You can allocate up to " + maxStrength + " Strength.");
                System.out.print("Would you like to allocate all remaining points to Strength? (Y/N): ");
                String choice = scanner.nextLine().trim();

                if (choice.equalsIgnoreCase("y")) {
                    strength += maxStrength;
                    statPoints = 0; // All points used up
                } else if (choice.equalsIgnoreCase("n")) {
                    int strengthPoints = getValidInput("Enter points for Strength: ", 0, maxStrength);
                    strength += strengthPoints;
                    statPoints -= (strengthPoints * 5);

                    // Remaining points automatically go to HP
                    health += statPoints;
                    statPoints = 0;
                } else {
                    System.out.println("❌ Invalid choice. Please enter Y or N.");
                }
            }
        }

        // Assign Default Weapons and Hero Description
        Weapon defaultWeapon;
        String heroDescription = "";
        String heroAbilities = "";

        switch (heroChoice) {
            case 1:
                defaultWeapon = new Weapon("Toxic Dart Gun", 10, 3, 5);
                heroDescription = "The Pharmacologist Hacker is a master of toxins and cyber warfare, using chemical compounds to debilitate enemies.";
                heroAbilities = "🛠️ **Abilities:**\n"
                        + "- Poison Dart: Inflicts gradual damage over time.\n"
                        + "- Hacking Boost: Can disable enemy defenses.\n"
                        + "- Chemical Overload: Amplifies toxin effects for massive damage.";
                break;

            case 2:
                defaultWeapon = new Weapon("Bioelectric Gloves", 12, 4, 6);
                heroDescription = "The Bioengineer enhances their body with regenerative biotechnology, allowing them to heal and reinforce combat capabilities.";
                heroAbilities = "🛠️ **Abilities:**\n"
                        + "- Rapid Healing: Recovers small amounts of HP over time.\n"
                        + "- Bioelectric Shock: Deals electric damage to enemies.\n"
                        + "- Adrenaline Surge: Temporarily boosts speed and strength.";
                break;

            case 3:
                defaultWeapon = new Weapon("Chemical Grenades", 14, 5, 7);
                heroDescription = "The Tactical Chemist specializes in volatile explosives and reactive combat, turning the battlefield into a chemical war zone.";
                heroAbilities = "🛠️ **Abilities:**\n"
                        + "- Explosive Trap: Places chemical bombs that detonate on impact.\n"
                        + "- Reactive Armor: Absorbs a portion of incoming damage.\n"
                        + "- Incendiary Strike: Deals fire damage over an area.";
                break;

            default:
                return null;
        }

        if (heroChoice == 1) {
            return new PharmacologistHacker(playerName, health, strength, 1, gold, defaultWeapon);
        } else if (heroChoice == 2) {
            return new Bioengineer(playerName, health, strength, 1, gold, defaultWeapon);
        } else {
            return new TacticalChemist(playerName, health, strength, 1, gold, defaultWeapon);
        }


// Generate and save passport
//        String passportContent = HeroPassport.generatePassport(playerName, heroChoice, defaultWeapon, health, strength, gold, heroAbilities);
//        if (passportContent != null) {
//            String fileName = "hero_passport.txt";
//            HeroPassport.saveHeroPassport(fileName, passportContent);
//            HeroPassport.loadAndDisplayPassport(fileName);
//
//
//        }
    }

    /**
     * Utility function to get valid integer input within a range.
     */
    private static int getValidInput(String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt + "(" +min+","+max+"): ");
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("❌ Invalid input. Please enter a number between " + min + " and " + max + ".");
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
