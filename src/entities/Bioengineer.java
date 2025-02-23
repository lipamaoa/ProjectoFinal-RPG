package src.entities;

import src.actions.HealAction;
import src.items.HealthPotion;
import src.items.HealthPotionSize;
import src.status.Regeneration;

/**
 * A hero specialized in bioengineering and self-healing.
 */
public class Bioengineer extends Hero {
    public Bioengineer(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength, gold);
        this.availableActions.add(new HealAction(this));
        this.statuses.add(new Regeneration(-1, 15));
    }

    /**
     * Initializes the Bioengineer’s unique inventory.
     */
    @Override
    protected void initializeInventory() {
        inventory.addItem(new HealthPotion(HealthPotionSize.Large));
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.BIOENGINEER;
    }
}
