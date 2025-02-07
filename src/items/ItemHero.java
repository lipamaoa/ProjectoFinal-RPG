package src.items;


import java.util.ArrayList;

/**
 * Abstract class for all items a hero can use.
 */
public abstract class ItemHero {
    protected String name;
    protected int price;
    protected ArrayList<String> allowedHeroes;

    public ItemHero(String name, int price) {
        this.name = name;
        this.price = price;
        this.allowedHeroes = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public void showDetails() {
        System.out.println("Item: " + name + " - Price: " + price + " gold");
    }
}
