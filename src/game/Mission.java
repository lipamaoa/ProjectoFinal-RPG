package src.game;

import src.entities.Hero;

/**
 * Represents a side mission given by an NPC.
 */
public class Mission {
    private String name;
    private String description;
    private boolean isCompleted;
    private int goldReward;
    private int xpReward;
    
    public Mission(String name, String description, int goldReward, int xpReward) {
        this.name = name;
        this.description = description;
        this.isCompleted = false;
        this.goldReward = goldReward;
        this.xpReward = xpReward;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Completes the mission and gives the rewards to the player.
     */
    public void complete(Hero player) {
        if (!isCompleted) {
            System.out.println("✅ Mission '" + name + "' completed!");
            player.addGold(goldReward);
            player.gainXP(xpReward);
            System.out.println("🎁 You received " + goldReward + " gold and " + xpReward + " XP!");
            isCompleted = true;
        } else {
            System.out.println("⚠️ This mission has already been completed.");
        }
    }
}
