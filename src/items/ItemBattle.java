package src.items;

import java.util.function.Consumer;

import src.game.Battle;

/**
 * Represents an item that can be used in battle.
 */
public class ItemBattle extends Item {
    private final Consumer<Battle> effect;

    /**
     * Constructs a battle item.
     *
     * @param name        The name of the item.
     * @param description The description of what the item does.
     * @param price       The item's value (cost or selling price).
     */
    public ItemBattle(String name, String description, int price, Consumer<Battle> effect) {
        super(name, description, price);
        this.effect = effect;
    }

    /**
     * Applies the item's effect in battle.
     */
    public final void use(Battle battle) {
        this.effect.accept(battle);
    }
}
