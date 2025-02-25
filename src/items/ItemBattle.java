package src.items;

import java.util.Set;
import java.util.function.Consumer;
import src.entities.HeroClass;
import src.game.Battle;

/**
 * Represents a battle-related item that can be used during combat.
 */
public class ItemBattle extends Item {
    private final Consumer<Battle> effect;

    /**
     * Constructs a battle item with its attributes.
     *
     * @param name           The name of the item.
     * @param description    A brief description of the item's effect.
     * @param price          The cost of the item in gold.
     * @param allowedClasses The hero classes that can use this item.
     * @param effect         The effect of the item when used in battle.
     */

    public ItemBattle(String name, String description, int price, Set<HeroClass> allowedClasses,
            Consumer<Battle> effect) {
        super(name, description, price, allowedClasses);
        this.effect = effect;
    }

    /**
     * Uses the battle item, applying its effect if the hero can use it.
     *
     * @param battle The battle instance where the item is used.
     */
    public void use(Battle battle) {
        if (canBeUsedBy(battle.getPlayer())) {
            effect.accept(battle);
        } else {
            System.out.println("❌ This item cannot be used by your hero class.");
        }
    }
}
