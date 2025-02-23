package src.actions;

import java.util.List;

import src.entities.Entity;
import src.entities.Hero;
import src.game.Battle;
import src.game.GameScanner;
import src.items.Item;
import src.items.ItemBattle;
import src.items.ItemHero;

public class UseItemAction extends BattleAction {
    public UseItemAction(Entity actor) {
        super("Use Item", actor);

        var isHero = actor instanceof Hero;
        if (!isHero) {
            // Limit to heroes for now, but this could be expanded.
            throw new IllegalArgumentException("Only heroes can use items.");
        }
    }

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
            if (selectedItem instanceof ItemBattle battleItem) {
                battleItem.use(battle);
            } else if (selectedItem instanceof ItemHero playerItem) {
                playerItem.use(player);
            }

            player.getInventory().removeItem(selectedItem);
            return true;
        } else {
            System.out.println("❌ Something went wrong! Could not use item.");
        }
        return false;
    }

    @Override
    public List<Entity> getValidTargets(Battle battle) {
        return null;
    }
}
