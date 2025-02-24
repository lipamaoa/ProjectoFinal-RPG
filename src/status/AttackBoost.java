package src.status;

/**
 * Represents a temporary status effect that boosts a hero's attack strength.
 */
public class AttackBoost extends TimedStatus {
    private final int strengthBoost;

    /**
     * Constructs an AttackBoost status effect.
     *
     * @param duration The number of turns the effect lasts.
     * @param strengthBoost The amount of additional strength granted.
     */
    public AttackBoost(int duration, int strengthBoost) {
        super("☣️ Attack boost", duration);
        this.strengthBoost = strengthBoost;
    }

    /**
     * Retrieves the strength boost value provided by this status effect.
     *
     * @return The strength boost value.
     */
    public int getStrengthBoost() {
        return strengthBoost;
    }
}
