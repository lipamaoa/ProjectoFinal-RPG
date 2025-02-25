package src.game;

/**
 * The HeroCreationOptions class represents the options for creating a hero in the game.
 * It includes the player's name, the hero choice, health, strength, and gold.
 */
public class HeroCreationOptions {
    private final String playerName;
    private final int heroChoice;
    private final int health;
    private final int strength;
    private final int gold;

    public HeroCreationOptions(String playerName, int heroChoice, int health, int strength, int gold) {
        this.playerName = playerName;
        this.heroChoice = heroChoice;
        this.health = health;
        this.strength = strength;
        this.gold = gold;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getHeroChoice() {
        return heroChoice;
    }

    public int getHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public int getGold() {
        return gold;
    }
}