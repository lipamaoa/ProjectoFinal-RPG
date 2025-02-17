package src.entities;

import src.game.GameRandom;
import src.game.GameScanner;
import src.game.Mission;
import src.items.HealthPotion;
import src.items.HealthPotionSize;

import java.util.Random;
import java.util.Scanner;

/**
 * Represents a friendly NPC that can assist the player.
 */
public class FriendlyNPC {
    private final String name;
    private final String backstory;
    private final String[] dialogues;
    private boolean canGiveItem;
    private boolean canHeal;
    private final boolean canFight;
    private final Mission mission;
    private final Random random;

    public FriendlyNPC(String name, String backstory, String[] dialogues, boolean canGiveItem, boolean canHeal,
            boolean canFight, Mission mission) {
        this.name = name;
        this.backstory = backstory;
        this.dialogues = dialogues;
        this.canGiveItem = canGiveItem;
        this.canHeal = canHeal;
        this.canFight = canFight;
        this.mission = mission;
        this.random = GameRandom.getInstance();
    }

    /**
     * Interacts with the player, offering different choices.
     */
    public void interact(Hero player) {
        System.out.println("\n👤 You meet " + name + "!");
        System.out.println("\"" + backstory + "\"");

        while (true) {
            System.out.println("\n1️⃣ \"Tell me more about this place.\"");
            System.out.println("2️⃣ \"Do you have anything that can help me?\"");
            if (mission != null && !mission.isCompleted()) {
                System.out.println("3️⃣ \"Do you need my help with anything?\"");
            }
            System.out.println("4️⃣ \"I have to go.\"");
            int choice = GameScanner.getInt();

            switch (choice) {
                case 1:
                    talk();
                    break;
                case 2:
                    assist(player);
                    break;
                case 3:
                    if (mission != null && !mission.isCompleted()) {
                        System.out.println("📝 New mission received: " + mission.getDescription());
                    }
                    break;
                case 4:
                    System.out.println("🚶 You nod and continue your journey.");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    /**
     * Displays a random dialogue from the NPC.
     */
    private void talk() {
        int index = this.random.nextInt(dialogues.length);
        System.out.println("💬 " + name + ": \"" + dialogues[index] + "\"");
    }

    /**
     * Assists the player by healing, giving items, or offering to fight.
     */
    private void assist(Hero player) {
        if (canGiveItem) {
            System.out.println("🎁 " + name + " hands you a small vial.");
            player.addItemToInventory(new HealthPotion(HealthPotionSize.Small));
            this.canGiveItem = false;
        }

        if (canHeal) {
            int healAmount = this.random.nextInt(30) + 20;
            player.heal(healAmount);
            System.out.println("🩹 " + name + " patches up your wounds.");
            // Heal just once
            this.canHeal = false;
        }

        if (canFight) {
            System.out.println("⚔️ " + name + " says: \"If you need help in battle, I'll be there.\"");
            // Future implementation: NPC joins battle
        }
    }

    /**
     * Checks if the player completed a mission and rewards them.
     */
    public void checkMissionCompletion(Hero player, boolean completed) {
        if (mission != null && !mission.isCompleted() && completed) {
            mission.complete(player);
        }
    }
}
