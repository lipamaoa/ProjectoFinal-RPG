package src.game;

import src.entities.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.actions.BattleAction;
import src.entities.Enemy;
import src.entities.Entity;
import src.items.ItemBattle;
import src.items.ItemHero;
import src.items.Item;
import src.utils.AsciiArt;

/**
 * Handles turn-based battles with room-defined enemies and allies.
 */
public class Battle {
    private Hero player;
    private ArrayList<Enemy> originalEnemiesList;
    private ArrayList<Entity> enemies;
    private ArrayList<Entity> allies;
    private final Random random;

    /**
     * Constructs a battle instance with a predefined list of enemies.
     *
     * @param player The player's hero.
     * @param enemy  The enemy in this room.
     */
    public Battle(Hero player, ArrayList<Enemy> enemies, ArrayList<Entity> friendlyNPCs) {
        this.player = player;
        this.enemies = new ArrayList<>(enemies);
        // So we can keep track of the original list of enemies
        this.originalEnemiesList = enemies;
        this.allies = new ArrayList<>();
        if (friendlyNPCs != null) {
            this.allies.addAll(friendlyNPCs);
        }
        this.random = GameRandom.getInstance();
    }

    public Hero getPlayer() {
        return this.player;
    }

    public boolean combatEnded() {
        if (player.getCurrentHp() <= 0) {
            return true;
        }

        if (enemies.isEmpty()) {
            return true;
        }

        for (Entity enemy : enemies) {
            if (enemy.getCurrentHp() > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Starts the battle loop.
     */
    public void start() {
        AsciiArt.showBattleScreen();
        System.out.println("\n⚔️ A battle begins!");

        while (combatEnded() == false) {
            playerTurn();
            alliesTurn();
            enemiesTurn();
            doEndOfTurnEffects();
        }

        if (player.getCurrentHp() > 0) {
            int goldWon = 0;
            for (Enemy enemy : originalEnemiesList) {
                goldWon += enemy.getGold();
            }
            System.out.printf("Victory! You got %d 🪙Gold from your enemies.\n", goldWon);
            this.player.collectGold(goldWon);
        }

        endBattle();
    }

    private void displayStatus() {
        System.out.println("\n==============================");
        System.out.println("🔹 HERO: " + player.getName());
        System.out.println("❤️ HP: [" + player.getCurrentHp() + "/" + player.getMaxHp() + "]");
        System.out.println("==============================");

        if (!allies.isEmpty()) {
            System.out.println("🛡️ FRIENDLY NPCs:");
            for (Entity ally : allies) {
                System.out.println(
                        "   🤝 " + ally.getName() + " - HP: [" + ally.getCurrentHp() + "/" + ally.getMaxHp() + "]");
            }
        }

        System.out.println("==============================");
        System.out.println("👿 ENEMIES:");
        for (Entity enemy : enemies) {
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
        boolean turnCompleted = false;

        while (!turnCompleted) {
            List<BattleAction> actions = player.getAvailableActions();

            // Step 1: Let the player pick an action
            System.out.println("\nChoose an action:");
            for (int i = 0; i < actions.size(); i++) {
                System.out.println((i + 1) + ") " + actions.get(i).getName());
            }

            int actionChoice = GameScanner.getInt() - 1;
            if (actionChoice < 0 || actionChoice >= actions.size()) {
                System.out.println("❌ Invalid choice!");
                continue;
            }

            BattleAction chosenAction = actions.get(actionChoice);

            // Step 2: Let the player pick a target if needed
            List<Entity> validTargets = chosenAction.getValidTargets(this);
            Entity target = null;

            if (validTargets != null && !validTargets.isEmpty()) {
                if (validTargets.size() == 1) {
                    target = validTargets.get(0);
                } else {
                    System.out.println("\nChoose a target:");
                    for (int i = 0; i < validTargets.size(); i++) {
                        System.out.println((i + 1) + ") " + validTargets.get(i).getName());
                    }

                    int targetChoice = GameScanner.getInt() - 1;
                    if (targetChoice >= 0 && targetChoice < validTargets.size()) {
                        target = validTargets.get(targetChoice);
                    } else {
                        System.out.println("❌ Invalid target!");
                        continue;
                    }
                }
            }

            // Try to Execute action
            turnCompleted = chosenAction.execute(this, target);
        }
    }

    private ArrayList<Entity> getAliveAllies() {
        ArrayList<Entity> tempAllies = new ArrayList<>();
        tempAllies.add(player);

        for (Entity entity : this.allies) {
            if (entity.getCurrentHp() > 0) {
                tempAllies.add(entity);
            }

        }

        return tempAllies;
    }

    private void entitiesTurn(List<Entity> entities) {
        for (Entity entity : entities) {
            if (combatEnded()) {
                return;
            }
            if (entity.getCurrentHp() <= 0 || entity.isDisabled()) {
                continue;
            }

            List<BattleAction> actions = entity.getAvailableActions();
            // TODO: Right now we're just picking a random action and target.
            // We can implement a more advanced AI here.
            while (true) {
                BattleAction action = actions.get(this.random.nextInt(actions.size()));
                List<Entity> targets = action.getValidTargets(this);
                if (targets == null || targets.isEmpty()) {
                    // No valid targets for this action, try another one
                    continue;
                }
                Entity selectedTarget = null;
                // Always try to attack or heal the weakest target
                for (Entity target : targets) {
                    if (selectedTarget == null || target.getCurrentHp() < selectedTarget.getCurrentHp()) {
                        selectedTarget = target;
                    }
                }

                action.execute(this, selectedTarget);
                break;
            }
        }
    }

    /**
     * Handles friendly NPCs' turn.
     */
    private void alliesTurn() {
        // Copy the initial list of allies as the list may change during the turn
        var initialAllies = new ArrayList<>(allies);
        entitiesTurn(initialAllies);
    }

    /**
     * Handles the enemies' turn.
     */
    private void enemiesTurn() {
        // Copy the initial list of enemies as the list may change during the turn
        var initialEnemies = new ArrayList<>(enemies);
        entitiesTurn(initialEnemies);
    }

    /**
     * Removes defeated enemies and NPCs from the battle.
     */
    private void doEndOfTurnEffects() {
        player.endTurn();
        for (Entity ally : allies) {
            ally.endTurn();
        }
        for (Entity enemy : enemies) {
            enemy.endTurn();
        }

        enemies.removeIf(enemy -> enemy.getCurrentHp() <= 0);
        allies.removeIf(ally -> ally.getCurrentHp() <= 0);
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

    private List<Entity> getAliveEnemies() {
        ArrayList<Entity> tempEnemies = new ArrayList<>();
        for (Entity entity : this.enemies) {
            if (entity.getCurrentHp() > 0) {
                tempEnemies.add(entity);
            }
        }
        return tempEnemies;
    }

    public List<Entity> getEnemies() {
        return getAliveEnemies();
    }

    public List<Entity> getEnemies(Entity actor) {
        if (this.enemies.contains(actor)) {
            return getAliveAllies();
        } else {
            return getAliveEnemies();
        }
    }

    public List<Entity> getAllies(Entity actor) {
        if (this.enemies.contains(actor)) {
            return getAliveEnemies();
        } else {
            return getAliveAllies();
        }
    }

    public void moveToAllies(Entity target) {
        if (this.enemies.contains(target)) {
            this.enemies.remove(target);
            this.allies.add(target);
        }
    }
}
