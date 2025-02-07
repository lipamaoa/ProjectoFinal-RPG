package src.entities;

import src.items.Inventory;
import src.items.Consumable;
import src.items.Weapon;

/**
 * Abstract class representing the player's character.
 */
public abstract class Hero extends Entity {
    protected int level;
    protected int gold;
    protected Weapon mainWeapon;
    protected Inventory inventory;

    public Hero(String name, int maxHp, int strength, int level, int gold, Weapon mainWeapon) {
        super(name, maxHp, strength);
        this.level = level;
        this.gold = gold;
        this.mainWeapon = mainWeapon;
        this.inventory = new Inventory();
    }

    public void addItemToInventory(Consumable item) {
        inventory.addItem(item);
    }

    public void showInventory() {
        inventory.showInventory();
    }

    public void usePotion() {
        if (!inventory.hasItem("Health Potion")) {
            System.out.println("⚠️ No potions available!");
            return;
        }

        for (int i = 0; i < inventory.getItems().size(); i++) {
            if (inventory.getItems().get(i) instanceof Consumable) {
                Consumable potion = (Consumable) inventory.getItems().get(i);
                int healAmount = potion.getHealingAmount();
                
                if (currentHp + healAmount > maxHp) {
                    healAmount = maxHp - currentHp;
                }

                currentHp += healAmount;
                System.out.println("💊 Used " + potion.getName() + " and healed " + healAmount + " HP.");
                inventory.removeItem(potion);
                break;
            }
        }
    }


    public void spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
        } else {
            System.out.println("⚠️ Not enough gold!");
        }
    }
    
    public int getGold() {
        return gold;
    }

    public void addGold(int amount) {
        gold += amount;
    }
    
    public int getInventorySize() {
        return inventory.getSize();
    }
    
    public ItemHero getInventoryItem(int index) {
        return inventory.getItem(index);
    }
    
    public void removeItemFromInventory(ItemHero item) {
        inventory.removeItem(item);
    }
    
}

