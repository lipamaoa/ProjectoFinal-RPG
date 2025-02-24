package src.status;

import src.game.GameRandom;

import java.util.Random;

/**
 * Represents a timed status effect that lasts for a specific number of turns.
 */
public abstract class TimedStatus {
    protected String name;
    // -1 for passive effects
    protected int remainingTurns;
    protected final Random random;

    /**
     * Constructs a TimedStatus effect.
     *
     * @param name The name of the status effect.
     * @param duration The number of turns the effect lasts (-1 for permanent effects).
     */
    public TimedStatus(String name, int duration) {
        this.name = name;
        this.remainingTurns = duration;
        this.random = GameRandom.getInstance();
    }

    /**
     * Gets the name of the status effect.
     *
     * @return The name of the status effect.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the status effect has expired.
     *
     * @return True if the effect has expired, false otherwise.
     */
    public boolean isExpired() {
        return remainingTurns == 0;
    }

    /**
     * Checks if the status effect is permanent.
     *
     * @return True if the effect is permanent (-1 turns), false otherwise.
     */
    public boolean isPermanent() {
        return remainingTurns == -1;
    }

    /**
     * Gets the remaining number of turns for the status effect.
     *
     * @return The remaining turn count.
     */
    public int getRemainingTurns() {
        return remainingTurns;
    }

    /**
     * Reduces the duration of the status effect by one turn if it is not permanent.
     */
    public void tick() {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }
}
