package src.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import src.actions.AttackAction;
import src.actions.BattleAction;
import src.actions.HealAction;
import src.game.GameRandom;

/**
 * Abstract class representing an entity in the game.
 * It serves as a base for both the player and NPCs.
 */
public abstract class Entity {
    private static final List<String> ELECTRONIC_KEYWORDS = Arrays.asList(
            "robot",
            "sentient",
            "drone",
            "data",
            "cybernetic",
            "engineered");

    protected final String name;
    protected boolean canHeal;
    protected Random random;
    protected int maxHp;
    protected int currentHp;
    protected int strength;
    protected List<BattleAction> availableActions;
    private int disabledTurns = 0;

    public Entity(String name, int maxHp, int strength, boolean canHeal) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.strength = strength;
        this.canHeal = canHeal;
        this.random = GameRandom.getInstance();
        this.availableActions = new ArrayList<BattleAction>();

        // Consider all can attack
        this.availableActions.add(new AttackAction(this));
        if (canHeal) {
            this.availableActions.add(new HealAction(this));
        }
    }

    public Entity(String name, int maxHp, int strength) {
        this(name, maxHp, strength, false);
    }

    /**
     * Displays the entity's details
     */
    public void showDetails() {
        System.out.println("Name: " + name);
        System.out.println("HP: " + currentHp + "/" + maxHp);
        System.out.println("Strength: " + strength);
    }

    /**
     * Applies damage to the entity
     * Ensures that HP does not drop below 0.
     */
    public void takeDamage(int amount) {
        this.currentHp -= amount;

        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    /**
     * Heals the entity for a specified amount.
     * Ensures that HP does not exceed maxHp
     */
    public void heal(int amount) {
        this.currentHp += amount;
        if (this.currentHp > maxHp) {
            this.currentHp = maxHp;
        }
    }

    /**
     * Getters
     */

    public String getName() {
        return name;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getStrength() {
        return strength;
    }

    public List<BattleAction> getAvailableActions() {
        ArrayList<BattleAction> notDisabledActions = new ArrayList<>();

        for (BattleAction action : availableActions) {
            if (!action.isDisabled()) {
                notDisabledActions.add(action);
            }
        }

        return notDisabledActions;
    }

    /**
     * Checks if this is an electronic-based entity.
     *
     * @return true if the entity is a robot.
     */
    public boolean isElectronic() {
        String lowerCaseName = getName().toLowerCase();
        return ELECTRONIC_KEYWORDS.stream().anyMatch(lowerCaseName::contains);
    }

    public void disable(int i) {
        this.disabledTurns += i;
    }

    public boolean isDisabled() {
        return disabledTurns > 0;
    }

    public void endTurn() {
        if (currentHp == 0) {
            System.out.println("💀 " + getName() + " is knocked out!");
            return;
        }

        if (isDisabled()) {
            disabledTurns--;
            if (disabledTurns == 0) {
                System.out.println("⚡ " + getName() + " is no longer disabled!");
            } else {
                System.out.println("⚡ " + getName() + " is disabled for " + disabledTurns + " more turns.");
            }
        }

        for (BattleAction action : availableActions) {
            action.endTurn();
        }
    }
}
