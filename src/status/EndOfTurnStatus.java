package src.status;

import src.entities.Entity;

public abstract class EndOfTurnStatus extends TimedStatus {
    protected boolean isNegative = false;

    public EndOfTurnStatus(String name, int duration) {
        super(name, duration);
    }

    public EndOfTurnStatus(String name, int duration, boolean isNegative) {
        super(name, duration);
        this.isNegative = isNegative;
    }

    public boolean isNegative() {
        return isNegative;
    }

    public abstract void applyEffect(Entity entity);
}
