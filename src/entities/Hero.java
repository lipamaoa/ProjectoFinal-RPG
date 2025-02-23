package src.entities;

import java.util.Random;

import src.actions.SpecialAttackAction;
import src.actions.UseItemAction;
import src.game.GameRandom;
import src.items.*;
import src.status.EndOfTurnStatus;

/**
 * Abstract class representing a hero in the game.
 * Defines common attributes and behaviors for all hero types.
 */
public abstract class Hero extends Entity {
    protected int gold;
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
        this.random = GameRandom.getInstance();
        this.initializeInventory();
        this.availableActions.add(new UseItemAction(this));
        this.availableActions.add(new SpecialAttackAction(this));
        this.equipedWeapon = ItemRegistry.getStartingWeaponForHero(this);
    }

    /**
     * Displays hero's details, including inventory and weapon.
     */
    @Override
    public void showDetails() {
        super.showDetails();
        System.out.println("Gold: " + gold);
        if (this.equipedWeapon != null) {
            System.out.println("Weapon: " + this.equipedWeapon.getName());
            System.out.println("Weapon Damage: " + this.equipedWeapon.getAttackPower());
        } else {
            System.out.println("Weapon: None");
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

    public boolean removeNegativeStatuses() {
        var numberOfStatuses = statuses.size();
        statuses.removeIf(status -> {
            if (status instanceof EndOfTurnStatus && ((EndOfTurnStatus) status).isNegative()) {
                System.out.println(name + " is freed from " + status.getName() + "!");
                return true;
            }
            return false;
        });
        return numberOfStatuses != statuses.size();
    }
}
