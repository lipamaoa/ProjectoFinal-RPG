package src.game;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HeroPassport {

    /**
     * Loads ASCII Art from the correct file based on hero choice.
     */
    public static String loadAsciiArt(String heroType) {
        String filePath = "src/game/ascii_" + heroType.toLowerCase().replace(" ", "_") + ".txt";
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            return "|      🦸 UNKNOWN HERO                |\n" +
                    "|     ╭───╮                            |\n" +
                    "|     ┃👤  ┃  (Awaiting Identity...)   |\n" +
                    "|     ┃ 🤷  ┃                          |\n" +
                    "|     ╰───╯                            |\n";
        }
    }

    /**
     * Loads the hero passport template and fills in hero details.
     */
    public static String generatePassport(String playerName, String heroClass, String weapon, String weaponDetails,
                                          int hp, int strength, int gold, String heroAbilities) {
        try {
            // Load the passport template
            String template = new String(Files.readAllBytes(Paths.get("src/game/hero_passport_template.txt")));

            // Load the ASCII art based on the selected hero class
            String heroAscii = loadAsciiArt(heroClass);

            // Replace placeholders with hero details
            template = template.replace("{NAME}", playerName)
                    .replace("{CLASS}", heroClass)
                    .replace("{WEAPON}", weapon)
                    .replace("{WEAPON_DETAILS}", weaponDetails)
                    .replace("{HP}", String.valueOf(hp))
                    .replace("{STRENGTH}", String.valueOf(strength))
                    .replace("{GOLD}", String.valueOf(gold))
                    .replace("{ABILITIES}", heroAbilities.replace("\n", "\n|                                      |  "))
                    .replace("{HERO_PORTRAIT}", heroAscii);

            return template;
        } catch (IOException e) {
            System.out.println("❌ Error loading passport template: " + e.getMessage());
            return null;
        }
    }

    /**
     * Saves the generated passport to a file.
     */
    public static void saveHeroPassport(String fileName, String passportContent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(passportContent);
            System.out.println("\n📜 Hero passport saved successfully: " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Error saving hero passport: " + e.getMessage());
        }
    }

    /**
     * Loads and displays the saved hero passport from a file.
     */
    public static void loadAndDisplayPassport(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\n📖 **Hero Passport Loaded:**\n");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading hero passport: " + e.getMessage());
        }
    }
}
