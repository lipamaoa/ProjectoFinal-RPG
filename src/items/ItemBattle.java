package src.items;

import java.util.Set;
import java.util.function.Consumer;
import src.entities.HeroClass;
import src.game.Battle;

public class ItemBattle extends Item {
    private final Consumer<Battle> effect;

    public ItemBattle(String name, String description, int price, Set<HeroClass> allowedClasses, Consumer<Battle> effect) {
        super(name, description, price, allowedClasses);
        this.effect = effect;
    }

    public void use(Battle battle) {
        if (canBeUsedBy(battle.getPlayer())) {
            effect.accept(battle);
        } else {
            System.out.println("❌ This item cannot be used by your hero class.");
        }
    }
}
