package src.items;

import java.util.Set;
import java.util.function.Consumer;
import src.entities.Hero;
import src.entities.HeroClass;

/**
 * Represents an item that can be used by a hero, applying effects such as
 * healing or buffs.
 */
public class ItemHero extends Item {
    protected Consumer<Hero> effect;

    /**
     * Constructs a hero-specific item with its attributes.
     *
     * @param name           The name of the item.
     * @param description    A brief description of the item's effect.
     * @param price          The cost of the item in gold.
     * @param allowedClasses The hero classes that can use this item.
     * @param effect         The effect of the item when used by a hero.
     */
    public ItemHero(String name, String description, int price, Set<HeroClass> allowedClasses, Consumer<Hero> effect) {
        super(name, description, price, allowedClasses);
        this.effect = effect;
    }

    /**
     * Uses the hero item, applying its effect if the hero is eligible to use it.
     *
     * @param player The hero using the item.
     */
    public void use(Hero player) {
        if (this.effect == null) {
            System.out.println("❌ This item cannot be used.");
            return;
        }

        if (canBeUsedBy(player)) {
            effect.accept(player);
        } else {
            System.out.println("❌ This item cannot be used by your hero class.");
        }
    }
}
