package src.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import src.actions.AttackAction;
import src.actions.BattleAction;
import src.actions.HealAction;
import src.game.GameRandom;
import src.items.Inventory;
import src.items.Weapon;
import src.status.AttackBoost;
import src.status.EndOfTurnStatus;
import src.status.TimedStatus;

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
    protected Weapon equipedWeapon = null;
    protected Inventory inventory;
    protected List<TimedStatus> statuses;
    private int disabledTurns = 0;

    /**
     * Constructs an Entity with the specified attributes.
     *
     * @param name    The name of the entity.
     * @param maxHp   The maximum health points of the entity.
     * @param strength The base strength of the entity.
     * @param canHeal Whether the entity can heal.
     */
    public Entity(String name, int maxHp, int strength, boolean canHeal) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.strength = strength;
        this.canHeal = canHeal;
        this.random = GameRandom.getInstance();
        this.availableActions = new ArrayList<>();
        this.inventory = new Inventory();
        this.statuses = new ArrayList<>();

        // Consider all can attack
        this.availableActions.add(new AttackAction(this));
        if (canHeal) {
            this.availableActions.add(new HealAction(this));
        }
    }

    /**
     * Constructs an Entity with the specified attributes, assuming it cannot heal.
     *
     * @param name    The name of the entity.
     * @param maxHp   The maximum health points of the entity.
     * @param strength The base strength of the entity.
     */
    public Entity(String name, int maxHp, int strength) {
        this(name, maxHp, strength, false);
    }

    /**
     * Displays the entity's details.
     */
    public void showDetails() {
        System.out.println("Name: " + name);
        System.out.println("HP: " + currentHp + "/" + maxHp);
        System.out.println("Strength: " + strength);
    }

    /**
     * Applies damage to the entity.
     * Ensures that HP does not drop below 0.
     *
     * @param amount The amount of damage to be applied.
     */
    public void takeDamage(int amount) {
        this.currentHp -= amount;
        if (this.currentHp < 0) {
            this.currentHp = 0;
        }
    }

    /**
     * Heals the entity for a specified amount.
     * Ensures that HP does not exceed maxHp.
     *
     * @param amount The amount of healing to be applied.
     */
    public void heal(int amount) {
        this.currentHp += amount;
        if (this.currentHp > maxHp) {
            this.currentHp = maxHp;
        }
    }

    /**
     * Equips a weapon to the entity.
     *
     * @param weapon The weapon to be equipped.
     */
    public void equipWeapon(Weapon weapon) {
        this.equipedWeapon = weapon;
    }

    /**
     * Applies a timed status effect to the entity.
     *
     * @param newStatus The status effect to be applied.
     */
    public void applyStatus(TimedStatus newStatus) {
        statuses.add(newStatus);
        if (!newStatus.isPermanent()) {
            System.out.println(name + " is now affected by " + newStatus.getName() + "!");
        }
    }

    /**
     * Gets the name of the entity.
     *
     * @return The entity's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the current health points of the entity.
     *
     * @return The entity's current HP.
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Gets the maximum health points of the entity.
     *
     * @return The entity's max HP.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Gets the strength of the entity, including any boosts from statuses.
     *
     * @return The entity's total strength.
     */
    public int getStrength() {
        int totalBoost = 0;
        for (TimedStatus status : statuses) {
            if (status instanceof AttackBoost attackBoost) {
                totalBoost += attackBoost.getStrengthBoost();
            }
        }
        return strength + totalBoost;
    }

    /**
     * Gets the equipped weapon of the entity.
     *
     * @return The equipped weapon.
     */
    public Weapon getEquipedWeapon() {
        return equipedWeapon;
    }

    /**
     * Gets the available actions for the entity, excluding disabled actions.
     *
     * @return A list of available battle actions.
     */
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

    /**
     * Disables the entity for a specified number of turns.
     *
     * @param i Number of turns to be disabled.
     */
    public void disable(int i) {
        this.disabledTurns += i;
    }

    /**
     * Checks if the entity is disabled.
     *
     * @return true if the entity is disabled.
     */
    public boolean isDisabled() {
        return disabledTurns > 0;
    }

    /**
     * Processes status effects on the entity.
     */
    public void processStatuses() {
        this.statuses.forEach(status -> {
            if (status instanceof EndOfTurnStatus endOfTurnStatus) {
                endOfTurnStatus.applyEffect(this);
            }
            status.tick();
        });

        this.statuses.removeIf(status -> status.isExpired());
    }

    /**
     * Ends the turn for the entity, processing statuses and handling turn-based effects.
     */
    public void endTurn() {
        if (currentHp == 0) {
            System.out.println("💀 " + getName() + " is knocked out!");
            return;
        }
        this.processStatuses();

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
