package src.entities;

import java.util.Random;

import src.game.GameRandom;

/**
 * This class is used for enemies that the player will encounter in battles.
 */
public class Enemy extends Entity {
    protected int gold;
    protected int attackPower;
    protected int specialAttackPower;
    private final Random random;

    /**
     * Constructs an NPC with scaled attributes based on difficulty level.
     *
     * @param name            The name of the NPC.
     * @param baseHp          The base health of the NPC.
     * @param baseAttack      The base attack power of the NPC.
     * @param baseGold        The base gold reward upon defeat.
     * @param difficultyLevel The difficulty scaling factor.
     */
    public Enemy(String name, int baseHp, int baseAttack, int baseGold, int difficultyLevel) {
        super(name, scaleValue(baseHp, difficultyLevel), scaleValue(baseAttack, difficultyLevel));
        this.gold = scaleValue(baseGold, difficultyLevel);
        this.attackPower = this.strength;
        this.specialAttackPower = attackPower * 2;
        this.random = GameRandom.getInstance();
        // 50% chance of being able to heal
        this.canHeal = this.random.nextBoolean();
    }

    /**
     * Scales a given stat based on the difficulty level.
     *
     * @param baseValue       The base stat value.
     * @param difficultyLevel The difficulty level (higher means stronger enemies).
     * @return The scaled value.
     */
    private static int scaleValue(int baseValue, int difficultyLevel) {
        return baseValue + (difficultyLevel * 2);
    }

    /**
     * Gets the gold reward given when this NPC is defeated.
     *
     * @return The amount of gold.
     */
    public int getGold() {
        return gold;
    }
}
