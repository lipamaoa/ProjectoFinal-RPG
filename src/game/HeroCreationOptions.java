package src.game;

public class HeroCreationOptions {
    private String playerName;
    private int heroChoice;
    private int health;
    private int strength;
    private int gold;

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