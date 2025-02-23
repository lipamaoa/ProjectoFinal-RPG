package src.items;

import java.util.Set;
import java.util.function.Consumer;
import src.entities.Hero;
import src.entities.HeroClass;

public class ItemHero extends Item {
    protected Consumer<Hero> effect;

    public ItemHero(String name, String description, int price, Set<HeroClass> allowedClasses, Consumer<Hero> effect) {
        super(name, description, price, allowedClasses);
        this.effect = effect;
    }

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
