package src.entities;

import src.actions.HackAction;
import src.game.GameRandom;
import src.game.GameScanner;
import src.items.HealthPotion;
import src.items.HealthPotionSize;

import java.util.Random;

/**
 * Represents a friendly NPC that can assist the player.
 */
public class FriendlyNPC extends Entity {
    private final String backstory;
    private final String[] dialogues;
    private final boolean canGiveItem;
    private final boolean canFight;
    private final Random random;
    private boolean willJoinBattle;
    private boolean alreadyHelped = false;

    public FriendlyNPC(
            String name,
            String backstory,
            String[] dialogues,
            boolean canGiveItem,
            boolean canHeal,
            boolean canFight,
            boolean canHack,
            int maxHp,
            int strength) {
        super(name, maxHp, strength, canHeal);
        this.backstory = backstory;
        this.dialogues = dialogues;
        this.canGiveItem = canGiveItem;
        this.canFight = canFight;
        this.random = GameRandom.getInstance();
        this.availableActions.add(new HackAction(this));
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
            System.out.println("0️⃣ \"I have to go.\"");
            int choice = GameScanner.getInt();

            switch (choice) {
                case 1:
                    talk();
                    break;
                case 2:
                    assist(player);
                    break;
                case 0:
                    System.out.println("🚶 You nod and continue your journey.");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    public boolean willJoinBattle() {
        return this.willJoinBattle;
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
        if (alreadyHelped) {
            System.out.println("I can't help you further at this moment.");
            return;
        }

        if (canGiveItem) {
            System.out.println("🎁 " + name + " hands you a small vial.");
            player.addItemToInventory(new HealthPotion(HealthPotionSize.Small));
        }

        if (canHeal) {
            var removedNegative = player.removeNegativeStatuses();
            if (removedNegative) {
                System.out.println("✨ " + name + " cleanses you of harmful effects.");
            }
            if (player.getMaxHp() > player.getCurrentHp()) {

                int healAmount = this.random.nextInt(30) + 20;
                player.heal(healAmount);
                System.out.println("🩹 " + name + " patches up your wounds.");
            } else {
                System.out.println("You're already at full health. Have this 🍲 soup instead.");
            }
        }

        if (canFight) {
            System.out.println("⚔️ " + name + " says: \"If you need help in battle, I'll be there.\"");
            this.willJoinBattle = true;
        }

        alreadyHelped = true;
    }
}
