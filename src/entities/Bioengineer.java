package src.entities;

import src.actions.HealAction;
import src.items.HealthPotion;
import src.items.HealthPotionSize;
import src.status.Regeneration;

/**
 * A hero specialized in bioengineering and self-healing.
 * The Bioengineer has unique healing abilities and starts with a regeneration status.
 */
public class Bioengineer extends Hero {
    /**
     * Constructs a Bioengineer hero with specified attributes.
     *
     * @param name     The name of the Bioengineer.
     * @param maxHp    The maximum health of the Bioengineer.
     * @param strength The strength attribute of the Bioengineer.
     * @param gold     The starting amount of gold.
     */

    public Bioengineer(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength, gold);
        this.availableActions.add(new HealAction(this));
        this.statuses.add(new Regeneration(-1, 5));
    }

    /**
     * Initializes the Bioengineer’s unique inventory.
     * The Bioengineer starts with a large health potion.
     */
    @Override
    protected void initializeInventory() {
        inventory.addItem(new HealthPotion(HealthPotionSize.Large));
    }


    /**
     * Retrieves the hero class type of the Bioengineer.
     *
     * @return The hero class type as BIOENGINEER.
     */
    @Override
    public HeroClass getHeroClass() {
        return HeroClass.BIOENGINEER;
    }
}
