package src.entities;

import src.actions.HealAction;
import src.items.HealthPotion;
import src.items.HealthPotionSize;

/**
 * A hero specialized in bioengineering and self-healing.
 */
public class Bioengineer extends Hero {
    public Bioengineer(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength, gold);
        this.availableActions.add(new HealAction(this));
    }

    @Override
    public void endTurn() {
        super.endTurn();
        applyHealing();
    }

    /**
     * Applies healing at the beginning of each turn.
     * Should be called automatically every turn.
     */
    private void applyHealing() {
        int actualHeal = (int) (maxHp * 0.05);
        heal(actualHeal);
        System.out.println("💚 You regenerated " + actualHeal + " HP.");
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
