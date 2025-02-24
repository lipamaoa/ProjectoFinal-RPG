package src.entities;

import src.actions.IncendiaryStrikeAction;
import src.items.HealthPotion;
import src.items.HealthPotionSize;

/**
 * A hero specialized in chemical explosives and reactive combat.
 */
public class TacticalChemist extends Hero {

    /**
     * Constructs a Tactical Chemist hero with base stats.
     *
     * @param name     The hero's name.
     * @param maxHp    The allocated health.
     * @param strength The allocated strength.
     * @param gold     The allocated gold.
     */

    public TacticalChemist(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength, gold);
        this.availableActions.add(new IncendiaryStrikeAction(this));
    }

    /**
     * Initializes the Tactical Chemist’s unique inventory.
     */
    @Override
    protected void initializeInventory() {
        inventory.addItem(new HealthPotion(HealthPotionSize.Large));
    }

    /**
     * Retrieves the hero class type of the Tactical Chemist.
     *
     * @return The hero class type as TACTICAL_CHEMIST.
     */
    @Override
    public HeroClass getHeroClass() {
        return HeroClass.TACTICAL_CHEMIST;
    }


    /**
     * Applies damage reduction for the Tactical Chemist.
     * This hero takes 5% less damage from all sources.
     *
     * @param damage The incoming damage to be reduced.
     */
    @Override
    public void takeDamage(int damage) {
        // Tactical Chemists take 5% less damage from all sources
        int reducedDamage = (int) (damage * 0.95);
        super.takeDamage(reducedDamage);
    }
}
