package src.actions;

import src.entities.Entity;
import src.entities.Hero;
import src.game.Battle;
import src.game.GameScanner;
import src.items.Item;
import src.items.ItemBattle;
import src.items.ItemHero;
import src.items.Weapon;

import java.util.List;

/**
 * Represents an action that allows a hero to use an item during battle.
 */
public class UseItemAction extends BattleAction {
    /**
     * Constructs a UseItemAction for a given hero.
     *
     * @param actor The hero performing the item usage action.
     * @throws IllegalArgumentException if the actor is not a hero.
     */

    public UseItemAction(Entity actor) {
        super("Use Item", actor);

        var isHero = actor instanceof Hero;
        if (!isHero) {
            // Limit to heroes for now, but this could be expanded.
            throw new IllegalArgumentException("Only heroes can use items.");
        }
    }

    /**
     * Executes the use item action, allowing the hero to select and use an item from their inventory.
     *
     * @param battle The current battle instance.
     * @param target The target entity (not directly used in item usage).
     * @return True if the item was successfully used, otherwise false.
     */

    @Override
    public boolean execute(Battle battle, Entity target) {
        var player = (Hero) actor;

        if (player.getInventory().getSize() == 0) {
            System.out.println("❌ You have no items in your inventory!");
            return false;
        }

        player.getInventory().showInventory();

        System.out.println("\nChoose an item to use (or 0 to cancel):");
        int itemChoice = GameScanner.getInt();

        if (itemChoice == 0) {
            System.out.println("❌ Action canceled.");
            return false;
        }

        // Validate selection
        if (itemChoice < 1 || itemChoice > player.getInventory().getSize()) {
            System.out.println("❌ Invalid selection! Please choose a valid item.");
            return false;
        }

        // Retrieve selected item
        Item selectedItem = player.getInventory().getItem(itemChoice - 1);

        if (selectedItem != null) {
            switch (selectedItem) {
                case ItemBattle battleItem -> battleItem.use(battle);
                case ItemHero playerItem -> playerItem.use(player);
                case Weapon weapon -> {
                    Weapon currentWeapon = player.getEquipedWeapon();
                    player.equipWeapon(weapon);
                    if (currentWeapon != null) {
                        player.addItemToInventory(currentWeapon);
                    }
                }
                default -> {
                    System.out.println("❌ You can't use this at the moment.");
                    return false;
                }
            }

            player.getInventory().removeItem(selectedItem);
            return true;
        } else {
            System.out.println("❌ Something went wrong! Could not use item.");
        }
        return false;
    }


    /**
     * Retrieves valid targets for the use item action.
     * Since items are used on the player or their allies, this method returns null.
     *
     * @param battle The current battle instance.
     * @return Null as item usage does not require enemy selection.
     */
    @Override
    public List<Entity> getValidTargets(Battle battle) {
        return null;
    }
}
