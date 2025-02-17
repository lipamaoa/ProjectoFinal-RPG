package src.entities;

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
    private boolean canGiveItem;
    private boolean canHeal;
    private final boolean canFight;
    private final Random random;
    private boolean willJoinBattle;

    public FriendlyNPC(String name, String backstory, String[] dialogues, boolean canGiveItem, boolean canHeal,
            boolean canFight, int maxHp, int strength) {
        super(name, maxHp, strength);
        this.backstory = backstory;
        this.dialogues = dialogues;
        this.canGiveItem = canGiveItem;
        this.canHeal = canHeal;
        this.canFight = canFight;
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
            System.out.println("4️⃣ \"I have to go.\"");
            int choice = GameScanner.getInt();

            switch (choice) {
                case 1:
                    talk();
                    break;
                case 2:
                    assist(player);
                    break;
                case 4:
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
            this.willJoinBattle = true;
        }
    }

    @Override
    public void attack(Entity target) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'attack'");
    }
}
