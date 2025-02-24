package src.entities;

import src.actions.SpecialAttackAction;
import src.actions.UseItemAction;
import src.game.GameRandom;
import src.items.Inventory;
import src.items.Item;
import src.items.ItemRegistry;
import src.status.EndOfTurnStatus;

import java.util.Random;

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
        System.out.printf(" 💰 Gold: %d\n", gold);

        if (this.equipedWeapon != null) {
            System.out.printf(" 🗡️  Weapon: %s\n", this.equipedWeapon.getName());
            System.out.printf(" ⚔️  Weapon Damage: %d\n", this.equipedWeapon.getAttackPower());
        } else {
            System.out.println(" 🛑 Weapon: None");
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

     /**
     * Adds an item to the hero's inventory.
     *
     * @param item The item to be added.
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
     *
     * @param amount The amount of gold to be spent.
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
     *
     * @param amount The amount of gold to be added.
     */
    public void collectGold(int amount) {
        gold += amount;
    }

    /**
     * Gets the amount of gold the hero has.
     *
     * @return The hero's gold amount.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Each hero starts with a unique inventory based on their specialization.
     */
    protected abstract void initializeInventory();

     /**
     * Gets the hero's inventory.
     *
     * @return The inventory of the hero.
     */

    public Inventory getInventory() {
        return inventory;
    }

     /**
     * Gets the size of the hero's inventory.
     *
     * @return The inventory size.
     */
    public int getInventorySize() {
        return inventory.getSize();
    }

     /**
     * Gets the class type of the hero.
     *
     * @return The hero's class type.
     */

    public abstract HeroClass getHeroClass();

     /**
     * Removes negative status effects from the hero.
     *
     * @return true if any negative statuses were removed, false otherwise.
     */

    public boolean removeNegativeStatuses() {
        int numberOfStatuses = statuses.size();
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
