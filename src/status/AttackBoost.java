package src.status;

public class AttackBoost extends TimedStatus {
    private final int strengthBoost;

    public AttackBoost(int duration, int strengthBoost) {
        super("☣️ Attack boost", duration);
        this.strengthBoost = strengthBoost;
    }

    public int getStrengthBoost() {
        return strengthBoost;
    }
}
