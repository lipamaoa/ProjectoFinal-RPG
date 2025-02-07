package src.entities;

/**
 * Abstract class representing an entity in the game.
 * It serves as a base for both the player and NPCs.
 */

public abstract class Entity{
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected int strength;

    public Entity(String name, int maxHp, int strength) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.strength = strength;
    }

    public void showDetails(){
        System.out.println("Name: " + name);
        System.out.println("HP: " + currentHp + "/" + maxHp);
        System.out.println("Strength: " + strength);
    }

    public abstract void attack(Entity target);
}
