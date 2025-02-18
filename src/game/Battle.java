package src.game;

import src.entities.Hero;

import java.util.ArrayList;
import java.util.Random;

import src.entities.Enemy;
import src.entities.Entity;
import src.items.ItemBattle;
import src.items.ItemHero;
import src.items.Item;
import src.utils.AsciiArt;

/**
 * Handles turn-based battles with room-defined enemies and NPC hacking.
 */
public class Battle {
    private Hero player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Entity> friends;
    private final Random random;

    /**
     * Constructs a battle instance with a predefined list of enemies.
     *
     * @param player The player's hero.
     * @param enemy  The enemy in this room.
     */
    public Battle(Hero player, ArrayList<Enemy> enemies, ArrayList<Entity> friendlyNPCs) {
        this.player = player;
        this.enemies = enemies;
        this.friends = new ArrayList<>();
        if (friendlyNPCs != null) {
            this.friends.addAll(friendlyNPCs);
        }
        this.random = GameRandom.getInstance();
    }

    public Enemy getEnemy() {
        return enemies.get(this.random.nextInt(enemies.size()));
    }

    public Hero getPlayer() {
        return this.player;
    }

    /**
     * Starts the battle loop.
     */
    public void start() {
        AsciiArt.showBattleScreen();
        System.out.println("\n⚔️ A battle begins!");

        while (player.getCurrentHp() > 0 && !enemies.isEmpty()) {
            playerTurn();
            alliesTurn();
            enemiesTurn();
            removeDefeatedEntities();
        }

        endBattle();
    }

    private void displayStatus() {
        System.out.println("\n==============================");
        System.out.println("🔹 HERO: " + player.getName());
        System.out.println("❤️ HP: [" + player.getCurrentHp() + "/" + player.getMaxHp() + "]");
        System.out.println("==============================");

        if (!friends.isEmpty()) {
            System.out.println("🛡️ FRIENDLY NPCs:");
            for (Entity ally : friends) {
                System.out.println(
                        "   🤝 " + ally.getName() + " - HP: [" + ally.getCurrentHp() + "/" + ally.getMaxHp() + "]");
            }
        }

        System.out.println("==============================");
        System.out.println("👿 ENEMIES:");
        for (Enemy enemy : enemies) {
            System.out.println(
                    (enemy.isElectronic() ? "   🤖 " : "   💀 ") + enemy.getName() + " - HP: [" + enemy.getCurrentHp()
                            + "/" + enemy.getMaxHp() + "]");
        }
        System.out.println("==============================\n");
    }

    /**
     * Handles the player's turn.
     */
    private void playerTurn() {
        displayStatus();
        System.out.println("\nChoose an action:");
        System.out.println("1️⃣ Attack an enemy");
        System.out.println("2️⃣ Use Item");

        int choice = GameScanner.getInt();

        switch (choice) {
            case 1 -> attackEnemy();
            case 2 -> useItem();
            default -> System.out.println("❌ Invalid choice! Turn skipped.");
        }
    }

    /**
     * Allows the player to attack an enemy.
     */
    private void attackEnemy() {
        if (enemies.isEmpty()) {
            System.out.println("⚠️ No enemies left to attack!");
            return;
        }

        System.out.println("\nChoose an enemy to attack:");
        for (int i = 0; i < enemies.size(); i++) {
            System.out
                    .println((i + 1) + ") " + enemies.get(i).getName() + " (" + enemies.get(i).getCurrentHp() + " HP)");
        }

        int targetIndex = GameScanner.getInt() - 1;
        if (targetIndex >= 0 && targetIndex < enemies.size()) {
            player.attack(enemies.get(targetIndex));
        } else {
            System.out.println("❌ Invalid target!");
        }
    }

    private ArrayList<Entity> getAllies() {
        ArrayList<Entity> allies = new ArrayList<>();
        allies.add(player);
        allies.addAll(friends);
        return allies;
    }

    /**
     * Retrieves the ally with the lowest health.
     *
     * @return the ally entity with the weakest health status.
     */
    private Entity getWeakerAlly() {
        var allies = getAllies();
        Entity target = allies.get(0);
        for (Entity ally : allies) {
            if (ally.getCurrentHp() < target.getCurrentHp()) {
                target = ally;
            }
        }
        return target;
    }

    /**
     * Handles friendly NPCs' turn.
     */
    private void alliesTurn() {
        for (Entity ally : friends) {
            if (ally.getCurrentHp() <= 0) {
                continue;
            }

            int action = random.nextInt(100);
            // Chance to attack
            if (action < 50 && !enemies.isEmpty()) {
                Enemy target = enemies.get(random.nextInt(enemies.size()));
                ally.attack(target);
                // or heal
            } else {
                var target = getWeakerAlly();
                ally.heal(target);
            }
        }
    }

    /**
     * Handles the enemies' turn.
     */
    private void enemiesTurn() {
        for (Enemy enemy : enemies) {
            if (enemy.getCurrentHp() > 0) {
                if (enemy.isHacked()) {
                    // Don't fight us if hacked
                    continue;
                } else if (random.nextBoolean() && !friends.isEmpty()) {
                    Entity target = friends.get(random.nextInt(friends.size()));
                    enemy.attack(target);
                } else {
                    enemy.attack(player);
                }
            }
        }
    }

    /**
     * Removes defeated enemies and NPCs from the battle.
     */
    private void removeDefeatedEntities() {
        // Convert hacked enemies to allies first
        ArrayList<Enemy> hackedEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.isHacked()) {
                hackedEnemies.add(enemy);
            }
        }

        for (Enemy hackedEnemy : hackedEnemies) {
            enemies.remove(hackedEnemy);
            friends.add(hackedEnemy);
        }

        enemies.removeIf(enemy -> enemy.getCurrentHp() <= 0);
        friends.removeIf(ally -> ally.getCurrentHp() <= 0);
    }

    /**
     * Allows the player to use an item.
     */
    private void useItem() {
        if (player.getInventory().getSize() == 0) {
            System.out.println("❌ You have no items in your inventory!");
            return;
        }

        System.out.println("\n👜 Inventory:");
        player.getInventory().showInventory();

        System.out.println("\nChoose an item to use (or 0 to cancel):");
        int itemChoice = GameScanner.getInt();

        if (itemChoice == 0) {
            System.out.println("❌ Action canceled.");
            return;
        }

        // Validate selection
        if (itemChoice < 1 || itemChoice > player.getInventory().getSize()) {
            System.out.println("❌ Invalid selection! Please choose a valid item.");
            return;
        }

        // Retrieve selected item
        Item selectedItem = player.getInventory().getItem(itemChoice - 1);

        if (selectedItem != null) {
            // ✅ If the item is an EMP Grenade and the player is a PharmacologistHacker,
            // apply it to the enemy
            if (selectedItem instanceof ItemBattle battleItem) {
                battleItem.use(this);
            } else if (selectedItem instanceof ItemHero playerItem) {
                playerItem.use(player);
            }

            player.getInventory().removeItem(selectedItem);
            System.out.println("✔️ You used " + selectedItem.getName() + "!");
        } else {
            System.out.println("❌ Something went wrong! Could not use item.");
        }
    }

    /**
     * Ends the battle and determines the outcome.
     */
    private void endBattle() {
        if (player.getCurrentHp() > 0) {
            System.out.println("🎉 " + player.getName() + " won the battle!");
        } else {
            System.out.println("💀 " + player.getName() + " was defeated...");
        }
    }
}
