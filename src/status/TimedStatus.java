package src.status;

import java.util.Random;

import src.entities.Entity;
import src.game.GameRandom;

public abstract class TimedStatus {
    protected String name;
    // -1 for passive effects
    protected int remainingTurns;
    protected final Random random;

    public TimedStatus(String name, int duration) {
        this.name = name;
        this.remainingTurns = duration;
        this.random = GameRandom.getInstance();
    }

    public String getName() {
        return name;
    }

    public boolean isExpired() {
        return remainingTurns == 0;
    }

    public boolean isPermanent() {
        return remainingTurns == -1;
    }

    public int getRemainingTurns() {
        return remainingTurns;
    }

    public void tick() {
        if (remainingTurns > 0) {
            remainingTurns--;
        }
    }
}
