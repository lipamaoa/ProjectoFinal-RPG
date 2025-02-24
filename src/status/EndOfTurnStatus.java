package src.status;

import src.entities.Entity;


/**
 * Represents a timed status effect that applies its effect at the end of each turn.
 */
public abstract class EndOfTurnStatus extends TimedStatus {
    protected boolean isNegative = false;


    /**
     * Constructs an end-of-turn status effect with a given name and duration.
     *
     * @param name The name of the status effect.
     * @param duration The number of turns the effect lasts.
     */

    public EndOfTurnStatus(String name, int duration) {
        super(name, duration);
    }

    /**
     * Constructs an end-of-turn status effect with a given name, duration, and negativity flag.
     *
     * @param name The name of the status effect.
     * @param duration The number of turns the effect lasts.
     * @param isNegative Indicates if the effect is detrimental.
     */
    public EndOfTurnStatus(String name, int duration, boolean isNegative) {
        super(name, duration);
        this.isNegative = isNegative;
    }

    /**
     * Checks if the status effect is negative (harmful).
     *
     * @return True if the status is negative, false otherwise.
     */
    public boolean isNegative() {
        return isNegative;
    }

    /**
     * Applies the effect of the status at the end of the turn.
     *
     * @param entity The entity affected by the status effect.
     */
    public abstract void applyEffect(Entity entity);
}
