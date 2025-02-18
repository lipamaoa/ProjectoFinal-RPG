package src.game;

import src.entities.*;
import src.items.ItemHeroRegistry;
import src.items.Weapon;
import src.utils.AsciiArt;

import java.util.List;
import java.util.ArrayList;

/**
 * Main game class that handles character creation, storytelling, and game loop.
 */
public class Game {
    public static void main(String[] args) {
        // Set up game seed
        long gameSeed = (args.length > 0) ? parseSeed(args[0]) : System.currentTimeMillis();

        // Initialize the global random instance
        GameRandom.initialize(gameSeed);
        System.out.println("🎲 Game Seed: " + GameRandom.getSeed());

        // Display ASCII Welcome Screen
        AsciiArt.showWelcomeScreen();

        // Story Introduction
        introduceStory();

        // Create Player Character
        var player = createHero();

        // Initialize Map
        var gameMap = new Map();
        gameMap.startExploring(player);

        // Check if player is still alive for the final battle
        if (player.getCurrentHp() > 0) {
            var survivingFriends = gameMap.getSurvivingFriends();
            startFinalBattle(player, survivingFriends);
        }

        // End the game
        endGame(player);
    }

    private static long parseSeed(String seedStr) {
        try {
            return Long.parseLong(seedStr);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid seed provided. Using random seed instead.");
            return System.currentTimeMillis();
        }
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
     * Allows the player to choose hero type, difficulty, and distribute stat
     * points.
     *
     * @return The created hero.
     */
    private static Hero createHero() {
        String playerName = "";

        while (!isNameValid(playerName)) {
            System.out.print("\n📝 Enter your name: ");
            playerName = GameScanner.getString();
        }

        // Choose Hero Type
        System.out.println("\n" + playerName + ", choose your hero type:");
        System.out.println("1️⃣ Pharmacologist Hacker - Expert in hacking and toxins.");
        System.out.println("2️⃣ Bioengineer - Specializes in regeneration and biological enhancements.");
        System.out.println("3️⃣ Tactical Chemist - Uses chemical explosives and reactive combat.");

        int heroChoice = GameScanner.getIntInRange("Enter your choice ", 1, 3);

        // Choose Difficulty
        System.out.println("\nChoose your difficulty:");
        System.out.println("1️⃣ Easy (300 stat points, 20 gold)");
        System.out.println("2️⃣ Hard (220 stat points, 15 gold)");

        int difficultyChoice = GameScanner.getIntInRange("Enter your choice ", 1, 2);
        int statPoints = (difficultyChoice == 1) ? 300 : 220;
        int gold = (difficultyChoice == 1) ? 20 : 15;

        // Allocate Stat Points
        // Starting points
        int health = 50;
        int strength = 10;
        // Remove starting points
        statPoints -= 100;

        System.out.printf("You start with %d ❤\\uFE0FHP and %d 💪Strength.", health, strength);

        while (statPoints > 0) {
            System.out.println("\nRemaining stat points: " + statPoints);
            System.out.println("Points can be allocated between HP and Strength.");
            System.out.println("1 stat point = 1 ❤\uFE0FHP.\n5 stat point = 1 💪Strength");

            // Allocate HP
            int hpPoints = GameScanner.getIntInRange("Allocate points to HP: ", 0, statPoints);
            health += hpPoints;
            statPoints -= hpPoints;

            // Allocate Strength (5 points per Strength)
            if (statPoints > 0) {
                int maxStrength = statPoints / 5;
                System.out.println("💪 You can allocate up to " + maxStrength + " Strength.");
                System.out.print("Would you like to allocate all remaining points to Strength? (Y/N): ");
                String choice = GameScanner.getString();

                if (choice.equalsIgnoreCase("y")) {
                    strength += maxStrength;
                    statPoints = 0;
                } else if (choice.equalsIgnoreCase("n")) {
                    int strengthPoints = GameScanner.getIntInRange("Enter points for Strength: ", 0, maxStrength);
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
        Weapon startingWeapon;
        String heroDescription = "";
        String heroAbilities = "";

        switch (heroChoice) {
            case 1:
                startingWeapon = ItemHeroRegistry.HACKER_WEAPON;
                heroDescription = "The Pharmacologist Hacker is a master of toxins and cyber warfare, using chemical compounds to debilitate enemies.";
                heroAbilities = "🛠️ **Abilities:**\n"
                        + "- Poison Dart: Inflicts gradual damage over time.\n"
                        + "- Hacking Boost: Can disable enemy defenses.\n"
                        + "- Chemical Overload: Amplifies toxin effects for massive damage.";
                break;

            case 2:
                startingWeapon = ItemHeroRegistry.BIOENGINEER_WEAPON;
                heroDescription = "The Bioengineer enhances their body with regenerative biotechnology, allowing them to heal and reinforce combat capabilities.";
                heroAbilities = "🛠️ **Abilities:**\n"
                        + "- Rapid Healing: Recovers small amounts of HP over time.\n"
                        + "- Bioelectric Shock: Deals electric damage to enemies.\n"
                        + "- Adrenaline Surge: Temporarily boosts speed and strength.";
                break;

            case 3:
                startingWeapon = ItemHeroRegistry.CHEMIST_WEAPON;
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
            return new PharmacologistHacker(playerName, health, strength, gold, startingWeapon);
        } else if (heroChoice == 2) {
            return new Bioengineer(playerName, health, strength, gold, startingWeapon);
        } else {
            return new TacticalChemist(playerName, health, strength, gold, startingWeapon);
        }

        // Generate and save passport
        // String passportContent = HeroPassport.generatePassport(playerName,
        // heroChoice, defaultWeapon, health, strength, gold, heroAbilities);
        // if (passportContent != null) {
        // String fileName = "hero_passport.txt";
        // HeroPassport.saveHeroPassport(fileName, passportContent);
        // HeroPassport.loadAndDisplayPassport(fileName);
        //
        //
        // }
    }

    /**
     * Starts the final battle against the boss.
     */
    private static void startFinalBattle(Hero player, ArrayList<Entity> survivingFriends) {
        AsciiArt.showBattleScreen();
        System.out.println("\n⚔️ **FINAL BATTLE BEGINS!** ⚔️");

        var finalEnemies = new ArrayList<Enemy>();
        var random = GameRandom.getInstance();
        var possibleEnemies = NPCRegistry.FINAL_BOSS;
        // At least 1 enemy
        int numberOfEnemies = random.nextInt(possibleEnemies.size()) + 1;
        for (int i = 0; i < numberOfEnemies; i++) {
            finalEnemies.add(possibleEnemies.get(random.nextInt(possibleEnemies.size())));
        }
        Battle finalBattle = new Battle(player, finalEnemies, survivingFriends);
        finalBattle.start();
    }

    /**
     * Ends the game with different conclusions.
     */
    private static void endGame(Hero player) {
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
