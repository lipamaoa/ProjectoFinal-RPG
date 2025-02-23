package src.entities;

import src.items.HealthPotion;
import src.items.HealthPotionSize;

/**
 * A hero specialized in chemical explosives and reactive combat.
 */
public class TacticalChemist extends Hero {

    /**
     * Constructs a Tactical Chemist hero with base stats.
     *
     */
    public TacticalChemist(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength, gold);
    }

    @Override
    protected void initializeInventory() {
        inventory.addItem(new HealthPotion(HealthPotionSize.Large));
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.TACTICAL_CHEMIST;
    }

}
