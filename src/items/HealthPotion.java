package src.items;

/**
 * A potion that restores health.
 */
public class HealthPotion extends Consumable {
    private int healingAmount;

    public HealthPotion(int price, int healingAmount) {
        super("Health Potion", price);
        this.healingAmount = healingAmount;
    }

    @Override
    public int getHealingAmount() {
        return healingAmount;
    }
}
