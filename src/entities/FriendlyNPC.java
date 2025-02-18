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
    private final boolean canGiveItem;
    private final boolean canFight;
    private final Random random;
    private final boolean canHack;
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
        this.canHack = canHack;
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
        if (alreadyHelped) {
            System.out.println("I can't help you further at this moment.");
            return;
        }

        if (canGiveItem) {
            System.out.println("🎁 " + name + " hands you a small vial.");
            player.addItemToInventory(new HealthPotion(HealthPotionSize.Small));
        }

        if (canHeal) {
            int healAmount = this.random.nextInt(30) + 20;
            player.heal(healAmount);
            System.out.println("🩹 " + name + " patches up your wounds.");
        }

        if (canFight) {
            System.out.println("⚔️ " + name + " says: \"If you need help in battle, I'll be there.\"");
            this.willJoinBattle = true;
        }

        alreadyHelped = true;
    }

    @Override
    public void attack(Entity target) {
        if (!canFight) {
            System.out.println("⚠️ " + name + " is not a fighter and cannot attack!");
            return;
        }

        if (this.canHack && target.isElectronic() && target instanceof Enemy && !((Enemy) target).isHacked()) {
            System.out.println("🧑‍💻 " + name + " tries to hack " + target.getName() + "!");
            ((Enemy) target).tryToHack();
        } else {
            System.out.println("⚔️ " + name + " attacks " + target.getName() + " for " + strength + " damage.");
            target.takeDamage(strength);
        }

    }
}
