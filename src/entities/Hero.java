package src.entities;

import java.util.Random;

import src.actions.UseItemAction;
import src.game.GameRandom;
import src.items.*;

/**
 * Abstract class representing a hero in the game.
 * Defines common attributes and behaviors for all hero types.
 */
public abstract class Hero extends Entity {
    protected int gold;
    protected int baseAttack;
    protected int boostedAttack;
    protected int boostDuration;
    protected Weapon mainWeapon;
    protected Inventory inventory;
    protected final Random random;

    /**
     * Constructs a hero with dynamic stats based on user selection
     * 
     * @param name     The hero's name.
     * @param maxHp    The maximum health of the hero.
     * @param strength The hero's strength.
     * @param level    The hero's starting level.
     * @param gold     The initial gold amount.
     */

    public Hero(String name, int maxHp, int strength, int gold) {
        super(name, maxHp, strength);
        this.gold = gold;
        this.boostedAttack = 0;
        this.boostDuration = 0;
        this.inventory = new Inventory();
        this.mainWeapon = ItemRegistry.getStartingWeaponForHero(this); // ✅ Get weapon from registry
        this.random = GameRandom.getInstance();
        this.initializeInventory();
        this.availableActions.add(new UseItemAction(this));
    }

    /**
     * Displays hero's details, including inventory and weapon.
     */
    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Gold: " + gold);
        System.out.println("Weapon: " + (mainWeapon != null ? mainWeapon.getName() : "None"));
    }

    /**
     * Boosts the hero's attack power for a limited number of turns.
     * 
     * @param amount   The attack boost amount.
     * @param duration The number of turns the boost lasts.
     */
    public void boostAttack(int amount, int duration) {
        System.out.println("⚡ Attack boosted by " + amount + " for " + duration + " turns!");
        this.boostedAttack = amount;
        this.boostDuration = duration;
    }

    /**
     * Returns the current attack power, considering any boosts.
     */
    public int getAttackPower() {
        return baseAttack + boostedAttack;
    }

    /**
     * Decreases the boost duration after each turn.
     */
    public void decreaseBoostDuration() {
        if (boostDuration > 0) {
            boostDuration--;
            if (boostDuration == 0) {
                System.out.println("⚠️ Your attack boost has worn off.");
                boostedAttack = 0;
            }
        }
    }

    /**
     * Allows the hero to use a potion from inventory.
     */
    public void usePotion() {
        ItemHero potion = inventory.getFirstPotion();

        if (potion == null) {
            System.out.println("⚠️ No health potions available!");
            return;
        }

        // Ensure the item is a HealthPotion before using it
        if (potion instanceof HealthPotion) {
            HealthPotion healthPotion = (HealthPotion) potion;
            int healAmount = healthPotion.getEffectValue();

            // Prevent overhealing
            if (currentHp + healAmount > maxHp) {
                healAmount = maxHp - currentHp;
            }

            heal(healAmount);
            System.out.println("💊 Used " + potion.getName() + " and healed " + healAmount + " HP.");
            inventory.removeItem(potion);
        } else {
            System.out.println("⚠️ The selected item is not a health potion!");
        }
    }

    /**
     * Allows the hero to equip a new weapon.
     * 
     * @param newWeapon The weapon to equip.
     */
    public void equipWeapon(Weapon newWeapon) {
        if (newWeapon != null) {
            System.out.println("🔪 Equipped " + newWeapon.getName() + "!");
            this.mainWeapon = newWeapon;
        } else {
            System.out.println("⚠️ Cannot equip a null weapon.");
        }
    }

    /**
     * Adds an item to the hero's inventory.
     */
    public void addItemToInventory(Item item) {
        inventory.addItem(item);
    }

    /**
     * Displays the hero's inventory.
     */
    public void showInventory() {
        inventory.showInventory();
    }

    /**
     * Allows the hero to spend gold if they have enough.
     */
    public void spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
        } else {
            System.out.println("⚠️ Not enough gold!");
        }
    }

    /**
     * Adds gold to the hero's balance.
     */
    public void collectGold(int amount) {
        gold += amount;
    }

    // Getters
    public int getGold() {
        return gold;
    }

    public Weapon getMainWeapon() {
        return mainWeapon;
    }

    /**
     * Each hero starts with a unique inventory based on their specialization.
     */
    protected abstract void initializeInventory();

    public Inventory getInventory() {
        return inventory;
    }

    public int getInventorySize() {
        return inventory.getSize();
    }

    public abstract HeroClass getHeroClass();
}
