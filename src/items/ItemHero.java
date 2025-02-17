package src.items;

import java.util.function.Consumer;

import src.entities.Hero;

/**
 * Represents an item that can be used or equipped by a hero.
 */
public class ItemHero extends Item {
    protected Consumer<Hero> effect;

    /**
     * Constructs a hero item.
     *
     * @param name        The name of the item.
     * @param description The description of what the item does.
     * @param price       The item's value (cost or selling price).
     */
    public ItemHero(String name, String description, int price, Consumer<Hero> effect) {
        super(name, description, price);
        this.effect = effect;
    }

    /**
     * Applies the item's effect when used by the hero.
     */
    public final void use(Hero player) {
        effect.accept(player);
    }
}
