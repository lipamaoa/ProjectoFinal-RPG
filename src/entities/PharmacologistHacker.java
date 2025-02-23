package src.entities;

import src.actions.HackAction;
import src.items.*;

/**
 * A hero specialized in hacking and chemical-based attacks.
 */
public class PharmacologistHacker extends Hero {

    /**
     * Constructs a Pharmacologist Hacker with user-defined stats and a default
     * weapon.
     *
     * @param name     The hero's name.
     * @param maxHp    The allocated health.
     * @param strength The allocated strength.
     * @param gold     The allocated gold.
     */
    public PharmacologistHacker(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength, gold);
        this.availableActions.add(new HackAction(this));
    }

    @Override
    protected void initializeInventory() {
        inventory.addItem(new HealthPotion(HealthPotionSize.Large));
    }

    public void useEmpGrenade(Enemy enemy) {
        System.out.println("💥 EMP Grenade activated! Attempting to disable " + enemy.getName() + "...");

        if (enemy.isElectronic()) { // Only affects electronic enemies
            enemy.disable(2); // Disable enemy for 2 turns
        } else {
            System.out.println("❌ The EMP Grenade has no effect on organic enemies!");
        }
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.PHARMACOLOGIST_HACKER;
    }
}
