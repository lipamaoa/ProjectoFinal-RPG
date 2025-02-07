package src.entities;

import src.items.Weapon;
import src.items.HealthPotion;
import src.items.Inventory;
import src.items.ItemHero;

import java.util.ArrayList;

/**
 * Abstract class representing a hero in the game.
 * Defines common attributes and behaviors for all hero types.
 */
public abstract class Hero extends Entity {
    protected int level;
    protected int gold;
    protected Weapon defaultWeapon;
    protected Inventory inventory;


    /**
     * Constructs a hero with dynamic stats based on user selection
     * @param name The hero's name.
     * @param maxHp The maximum health of the hero.
     * @param strength The hero's strength.
     * @param level The hero's starting level.
     * @param gold The initial gold amount.
     * @param defaultWeapon The hero's starting  weapon.
     */

    public Hero(String name, int maxHp, int strength, int level, int gold, Weapon mainWeapon) {
        super(name, maxHp, strength);
        this.level = level;
        this.gold = gold;
        this.inventory = new Inventory(); // Initialize inventory
        if (defaultWeapon != null) {
            this.inventory.addItem(defaultWeapon); // Automatically add default weapon to inventory
        }
    }

    /**
     * Displays hero's details, including inventory and weapon.
     */
    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Level: " + level);
        System.out.println("Gold: " + gold);
        System.out.println("Weapon: " + (defaultWeapon!= null ? defaultWeapon.getName() : "None"));
        System.out.println("Inventory:");
        inventory.showInventory();
    }

    /**
     * Abstract attack method that must be implemented by subclasses.
     */
    public abstract void attack(NPC enemy);

    /**
     * Allows the hero to use a potion from inventory.
     */
    public void usePotion() {
    ItemHero potion = inventory.getFirstPotion(); // Retrieves the first potion

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
     * @param newWeapon The weapon to equip.
     */
    public void equipWeapon(Weapon newWeapon) {
        if (newWeapon != null) {
            System.out.println("🔪 Equipped " + newWeapon.getName() + "!");
            this.defaultWeapon = newWeapon;
        } else {
            System.out.println("⚠️ Cannot equip a null weapon.");
        }
    }

    /**
     * Adds an item to the hero's inventory.
     */
    public void addItemToInventory(ItemHero item) {
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
    public int getLevel() {
        return level;
    }

    public int getGold() {
        return gold;
    }

    public Weapon getMainWeapon() {
        return defaultWeapon;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getInventorySize() {
        return inventory.getSize();
    }


   
}
