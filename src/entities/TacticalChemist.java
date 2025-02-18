package src.entities;

import src.items.HealthPotion;
import src.items.HealthPotionSize;
import src.items.Weapon;

/**
 * A hero specialized in chemical explosives and reactive combat.
 */
public class TacticalChemist extends Hero {

    /**
     * Constructs a Tactical Chemist hero with base stats.
     *
     */
    public TacticalChemist(String name, int maxHp, int strength, int gold, Weapon startingWeapon) {
        super(name, maxHp, strength, gold, startingWeapon);
    }

    /**
     * Tactical Chemist attacks by throwing a chemical grenade.
     * Special ability: Has a 30% chance to cause an explosion that damages all
     * enemies.
     *
     * @param enemy The NPC being attacked.
     */
    @Override
    public void attack(Enemy enemy) {
        System.out.println("💣 " + getName() + " throws a chemical grenade at " + enemy.getName() + "!");
        int damageDealt = strength + 4;
        enemy.takeDamage(damageDealt);

        // 30% chance for an explosive reaction affecting all nearby NPCs
        if (random.nextInt(100) < 30) {
            System.out.println("🔥 The grenade explodes, dealing extra damage to all nearby enemies!");
            enemy.takeDamage(5);
        }
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
