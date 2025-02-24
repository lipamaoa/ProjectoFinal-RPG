package src.game;

import src.entities.*;
import src.utils.AsciiArt;
import src.utils.Audio;

import java.util.ArrayList;

import static src.utils.AsciiArt.escapeTheLabScreen;
import static src.utils.AsciiArt.showDefeatScreen;

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

    /**
     * Parses the game seed from command-line arguments.
     *
     * @param seedStr The seed provided as a string.
     * @return The parsed seed as a long value.
     */
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
        Audio.playMusic("src/utils/AudioFiles/intro.wav");

        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("                             📝 ** P R O L O G U E **");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("  🔬 You were one of PharmaCorp's top scientists, working on the classified");
        System.out.println("     Project Eden-9.");
        System.out.println("  ⚠️  But behind the closed doors of the facility, dark secrets lurked...");
        System.out.println("  💉  Unethical experiments. Human trials gone wrong. A truth too dangerous");
        System.out.println("     to ignore.");
        System.out.println("  🛑  The moment you uncovered the reality, you became a liability.");
        System.out.println("  🏃  Now, with security forces closing in and a labyrinthine lab to escape,");
        System.out.println("  🔎  Your survival depends on your intelligence, resourcefulness, and the");
        System.out.println("     choices you make.");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.println("  🔦 The truth is out there. But will you live long enough to expose it?");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛\n");

    }


        /**
         * Checks if the given name is valid based on length constraints.
         *
         * @param name The player's input name.
         * @return True if the name is valid, otherwise false.
         */

    private static boolean isNameValid(String name) {
        return name.length() >= 3 && name.length() <= 12;
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
            System.out.print("\uD83E\uDDD1\u200D\uD83D\uDD2C Enter your name: ");
            playerName = GameScanner.getString();
        }


        // Choose Hero Type
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("  🦸 %s, choose your hero type:\n", playerName);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  1️⃣ Pharmacologist Hacker  🧪");
        System.out.println("     🕵️ A cyber-genius skilled in hacking secure systems.");
        System.out.println("     ☠️ Masters deadly toxins and biochemical warfare.\n");
        System.out.println("  2️⃣ Bioengineer  🔬");
        System.out.println("     🏥 A scientist who enhances the human body.");
        System.out.println("     🌱 Can regenerate quickly and modify biology for survival.\n");
        System.out.println("  3️⃣ Tactical Chemist  💣");
        System.out.println("     🎯 A demolitions expert using precision chemical explosives.");
        System.out.println("     ⚡ Masters reactive combat and volatile concoctions.\n");

        int heroChoice = GameScanner.getIntInRange("Enter your choice ", 1, 3);


        // Choose Difficulty
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("               🎮 Choose Your Difficulty:");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("1️⃣ Easy  🟢");
        System.out.println("   ✅ 300 Stat Points");
        System.out.println("   💰 20 Gold");
        System.out.println("   🛡️ A safer start, but danger still lurks...");
        System.out.println("\n2️⃣ Hard  🔴");
        System.out.println("   ⚠️ 220 Stat Points");
        System.out.println("   💰 15 Gold");
        System.out.println("   ☠️ A true challenge for the brave!\n");

        int difficultyChoice = GameScanner.getIntInRange("Enter your choice ", 1, 2);
        int statPoints = (difficultyChoice == 1) ? 300 : 220;
        int gold = (difficultyChoice == 1) ? 20 : 15;

        // Allocate Stat Points
        // Starting points
        int health = 50;
        int strength = 10;
        // Remove starting points
        statPoints -= 100;

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("🎮 You start with: %d 💗 HP  |  %d 💪 Strength\n", health, strength);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        while (statPoints > 0) {
            System.out.println("🔹 Remaining stat points: " + statPoints);
            System.out.println("📌 Points can be allocated between HP and Strength.");
            System.out.println("   ⚡ 1 stat point = 1 💗 HP");
            System.out.println("   🔥 5 stat points = 1 💪 Strength");

            // Allocate HP
            int hpPoints = GameScanner.getIntInRange("\n➡️ Allocate points to HP: ", 0, statPoints);
            health += hpPoints;
            statPoints -= hpPoints;

            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("📊 Current Stats:");
            System.out.printf("   ❤ HP: %d   💪 Strength: %d\n", health, strength);
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

            // Allocate Strength (5 points per Strength)
            if (statPoints > 0) {
                int maxStrength = statPoints / 5;
                System.out.println("💪 You can allocate up to " + maxStrength + " Strength.");
                System.out.println("   (1 Strength = 5 stat points)\n");
                System.out.print("🔸 Would you like to allocate all remaining points to Strength? (Y/N): ");
                String choice = GameScanner.getString();

                if (choice.equalsIgnoreCase("y")) {
                    strength += maxStrength;
                    statPoints = 0;
                } else if (choice.equalsIgnoreCase("n")) {
                    int strengthPoints = GameScanner.getIntInRange(" ➡️ Enter points for Strength: ", 0, maxStrength);
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

        Hero hero;
        String heroType;

        if (heroChoice == 1) {
            hero = new PharmacologistHacker(playerName, health, strength, gold);
            heroType = "🧪 Pharmacologist Hacker";
        } else if (heroChoice == 2) {
            hero = new Bioengineer(playerName, health, strength, gold);
            heroType = "🔬 Bioengineer";
        } else {
            hero = new TacticalChemist(playerName, health, strength, gold);
            heroType = "💣 Tactical Chemist";
        }

        // Character Passport
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println(                "🎫 **CHARACTER PASSPORT** 🎫"           );
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("\uD83E\uDDD1\u200D\uD83D\uDD2C Name: %s\n", playerName);
        System.out.printf("🏷️ Class: %s\n", heroType);
        System.out.printf("💗 HP: %d\n", health);
        System.out.printf("💪 Strength: %d\n", strength);
        System.out.printf("💰 Gold: %d\n", gold);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        return hero;
    }

    /**
     * Starts the final battle against the boss.
     *
     * @param player The hero character.
     * @param survivingFriends Allies that survived up to the final battle.
     */
    private static void startFinalBattle(Hero player, ArrayList<Entity> survivingFriends) {
        AsciiArt.showFinalBattleScreen();

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
     * Ends the game with different conclusions based on the player's survival.
     *
     * @param player The hero character.
     */
    private static void endGame(Hero player) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        if (player.getCurrentHp() > 0) {
            escapeTheLabScreen();
        } else {
            showDefeatScreen();
        }

        System.out.println("\n🎮  **GAME OVER! THANKS FOR PLAYING.**");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
    }

}
