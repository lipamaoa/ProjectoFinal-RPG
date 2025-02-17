package src.entities;

import src.items.ItemHero;
import src.items.Weapon;

/**
 * A hero specialized in chemical explosives and reactive combat.
 */
public class TacticalChemist extends Hero {

    /**
     * Constructs a Tactical Chemist hero with base stats.
     *
     * @param name The hero's name.
     */
    public TacticalChemist(String name, int maxHp, int strength, int level, int gold, Weapon startingWeapon) {
        super(name, maxHp, strength, level, gold, startingWeapon);
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
            enemy.takeDamage(5); // Extra AoE damage
        }
    }

    @Override
    protected void initializeInventory() {
        inventory.addItem(new Weapon("Chemical Grenade", "", 7, 12, 20));
        // inventory.addItem(new ItemHero("Toxic Gas Bomb", "", 45) {
        // @Override
        // public void use(Hero player) {
        // System.out.println("☣️ You throw a toxic gas bomb! Enemies take poison
        // damage.");
        // }
        // });
        // inventory.addItem(new ItemHero("Combat Stimulant", "", 30) {
        // @Override
        // public void use(Hero player) {
        // System.out.println("💊 You inject a combat stimulant! Your agility
        // increases.");
        // }
        // });
    }

}
