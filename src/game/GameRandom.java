package src.game;

import java.util.Random;

/**
 * A utility class to provide a globally accessible Random instance with a seed.
 */
public class GameRandom {
    private static Random random;
    private static long seed;

    /**
     * Initializes the random instance with a given seed.
     * This should be called once at the start of the game.
     */
    public static void initialize(long gameSeed) {
        seed = gameSeed;
        random = new Random(seed);
    }

    /**
     * Returns the shared Random instance.
     * Ensures that the game uses consistent randomness across all classes.
     */
    public static Random getInstance() {
        if (random == null) {
            throw new IllegalStateException("GameRandom has not been initialized. Call initialize(seed) first.");
        }
        return random;
    }

    /**
     * Gets the current game seed.
     */
    public static long getSeed() {
        return seed;
    }
}
