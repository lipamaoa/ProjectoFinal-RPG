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
     */
    public TacticalChemist(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength, gold);
        this.availableActions.add(new IncendiaryStrikeAction(this));
    }

    @Override
    protected void initializeInventory() {
        inventory.addItem(new HealthPotion(HealthPotionSize.Large));
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.TACTICAL_CHEMIST;
    }

    @Override
    public void takeDamage(int damage) {
        // Tactical Chemists take 5% less damage from all sources
        int reducedDamage = (int) (damage * 0.95);
        super.takeDamage(reducedDamage);
    }
}
