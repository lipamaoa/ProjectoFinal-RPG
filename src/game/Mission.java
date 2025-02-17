package src.game;

import src.entities.Hero;

/**
 * Represents a mission assigned to the player by an NPC.
 */
public class Mission {
    private String title;
    private String description;
    private int progress;
    private int progressRequired;
    private boolean isCompleted;
    private int goldReward;
    private int experienceReward;

    /**
     * Constructs a mission with a title, description, progress requirement, and
     * rewards.
     *
     * @param title            The title of the mission.
     * @param description      The description of the mission.
     * @param goldReward       The amount of gold rewarded upon completion.
     * @param experienceReward The experience points rewarded upon completion.
     */
    public Mission(String title, String description, int goldReward, int experienceReward) {
        this.title = title;
        this.description = description;
        this.progress = 0;
        this.progressRequired = 1; // Default to 1 task required unless modified
        this.isCompleted = false;
        this.goldReward = goldReward;
        this.experienceReward = experienceReward;
    }

    /**
     * Updates the progress of the mission.
     * If the required progress is reached, the mission is completed.
     */
    public void updateProgress(int amount) {
        if (isCompleted)
            return; // Prevent updating a completed mission

        progress += amount;
        if (progress >= progressRequired) {
            complete(null); // No hero reference needed if called manually
        } else {
            System.out.println("📜 Mission Progress: " + progress + "/" + progressRequired);
        }
    }

    /**
     * Marks the mission as complete and rewards the player.
     *
     * @param player The player receiving the mission reward.
     */
    public void complete(Hero player) {
        if (!isCompleted) {
            isCompleted = true;
            System.out.println("🎉 Mission Completed: " + title + "!");
            System.out.println("🏆 Rewards: " + goldReward + " Gold | " + experienceReward + " XP");

            if (player != null) {
                player.collectGold(goldReward);

            }
        }
    }

    /**
     * Checks if the mission is already completed.
     *
     * @return true if completed, false otherwise.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Gets the mission description.
     *
     * @return The mission description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the required progress to complete the mission.
     *
     * @param progressRequired The number of tasks required to complete the mission.
     */
    public void setProgressRequired(int progressRequired) {
        this.progressRequired = progressRequired;
    }
}
