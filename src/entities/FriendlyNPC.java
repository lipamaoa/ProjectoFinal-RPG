package src.entities;

import src.actions.HackAction;
import src.game.GameRandom;
import src.game.GameScanner;
import src.items.HealthPotion;
import src.items.HealthPotionSize;

import java.util.Random;

/**
 * Represents a friendly NPC that can assist the player.
 * This NPC can provide information, items, healing, or even join battles.
 */
public class FriendlyNPC extends Entity {
    private final String backstory;
    private final String[] dialogues;
    private final boolean canGiveItem;
    private final boolean canFight;
    private final Random random;
    private boolean willJoinBattle;
    private boolean alreadyHelped = false;

    /**
     * Constructs a FriendlyNPC with specific attributes.
     *
     * @param name        The NPC's name.
     * @param backstory   The NPC's backstory.
     * @param dialogues   A list of dialogues the NPC can say.
     * @param canGiveItem Whether the NPC can give items.
     * @param canHeal     Whether the NPC can heal the player.
     * @param canFight    Whether the NPC can join battles.
     * @param canHack     Whether the NPC has hacking abilities.
     * @param maxHp       The NPC's maximum health.
     * @param strength    The NPC's strength.
     */

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

        if (canHack) {
            this.availableActions.add(new HackAction(this));
        }
    }

    /**
     * Interacts with the player, offering different choices.
     *
     * @param player The player interacting with the NPC.
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
                    System.out.println("🚶 You nod and continue your journey.\n");
                    return;
                default:
                    System.out.println("❌ Invalid choice!");
            }
        }
    }

    /**
     * Checks if the NPC will join the battle.
     *
     * @return true if the NPC will join the battle, false otherwise.
     */

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
     *
     * @param player The player receiving assistance.
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
                System.out.println("🍲 You're already at full health. Have this soup instead.");
            }
        }

        if (canFight) {
            System.out.println("⚔️ " + name + " says: \"If you need help in battle, I'll be there.\"");
            this.willJoinBattle = true;
        }

        alreadyHelped = true;
    }
}
