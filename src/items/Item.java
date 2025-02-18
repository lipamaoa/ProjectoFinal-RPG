package src.items;

import java.util.Set;
import src.entities.Hero;
import src.entities.HeroClass;

public abstract class Item {
    protected String name;
    protected String description;
    protected int price;
    protected Set<HeroClass> allowedClasses;

    public Item(String name, String description, int price, Set<HeroClass> allowedClasses) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.allowedClasses = allowedClasses;
    }

    public boolean canBeUsedBy(Hero player) {
        return allowedClasses == null || allowedClasses.contains(player.getHeroClass());
    }

    public void showDetails() {
        System.out.println("🛠️ Item: " + this.name);
        System.out.println("ℹ️ " + this.description);
        System.out.println("💰 Value: " + this.price + " gold");
        if (allowedClasses != null) {
            System.out.println("🎭 Usable by: " + allowedClasses);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
