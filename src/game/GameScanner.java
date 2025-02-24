package src.game;

import java.util.Scanner;

/**
 * Singleton class for handling user input with helper methods.
 */
public class GameScanner {
    private static final Scanner scanner = new Scanner(System.in);

    // Private constructor to prevent instantiation
    private GameScanner() {
    }

    /**
     * Reads an integer input safely, handling invalid input.
     * 
     * @return A valid integer input.
     */
    public static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
            }
        }
    }

    /**
     * Reads an integer within a specific range.
     * 
     * @param min The minimum allowed value.
     * @param max The maximum allowed value.
     * @return A valid integer within the specified range.
     */
    public static int getIntInRange(String prompt, int min, int max) {
        if (!prompt.isEmpty()) {
            System.out.print(prompt + "(" + min + "," + max + "): ");
        }
        while (true) {
            int value = getInt();
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("❌ Please enter a number between " + min + " and " + max + ".");
        }
    }

    /**
     * Reads a line of text input.
     * 
     * @return The user input as a trimmed string.
     */
    public static String getString() {
        return scanner.nextLine().trim();
    }

    /**
     * Waits for the player to press Enter.
     */
    public static void waitForEnter() {
        System.out.println("Press Enter to continue ➡️");
        scanner.nextLine();
    }
}
