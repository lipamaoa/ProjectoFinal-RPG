package src.items;

import java.util.Arrays;
import java.util.List;
import src.entities.*;
import src.game.Battle;

/**
 * Stores predefined items categorized by hero type.
 */
public class ItemHeroRegistry {
    // 🔹 **Healing Items (For all hero types)**
    public static final List<HealthPotion> HEALING_ITEMS = Arrays.asList(
            new HealthPotion(HealthPotionSize.Medium),
            new HealthPotion(HealthPotionSize.Large),
            new HealthPotion(HealthPotionSize.Small));

    // 🔹 **Weapons**
    public static final List<Weapon> WEAPONS = Arrays.asList(
            new Weapon("Chemical Blade", "A sharp blade coated with deadly toxins.", 15, 8, 12),
            new Weapon("Toxic Dart Gun", "Shoots poison darts that weaken enemies.", 20, 10, 15),
            new Weapon("Cryo Injector", "Freezes enemies, slowing them down.", 18, 7, 14));

    // 🔹 **Healing Items (Only for Bioengineer)**
    public static final List<Item> BIOENGINEER_ITEMS = Arrays.asList(
            new HealthPotion(HealthPotionSize.Medium),
            new HealthPotion(HealthPotionSize.Large),
            new ItemHero("Regeneration Serum", "Regenerates HP over time (+10 HP per turn for 3 turns).", 50,
                    (Hero player) -> {
                        if (player instanceof Bioengineer) {
                            System.out.println("🩸 You inject the Regeneration Serum! HP will regenerate each turn.");
                            ((Bioengineer) player).healOverTime(10, 3);
                        } else {
                            System.out.println("❌ Only a Bioengineer can use this item.");
                        }
                    }));

    // 🔹 **Hacker Items (Only for PharmacologistHacker)**
    public static final List<Item> HACKER_ITEMS = Arrays.asList(
            new ItemBattle("EMP Grenade", "Disables enemy technology for 2 turns.", 40,
                    (Battle battle) -> {
                        var player = battle.getPlayer();
                        if (player instanceof PharmacologistHacker) {
                            Enemy currentEnemy = battle.getEnemy();
                            if (currentEnemy != null) {

                                System.out.println("💥 You throw an EMP Grenade! Electronic enemies are stunned.");
                                ((PharmacologistHacker) player).useEmpGrenade(currentEnemy);
                            } else {
                                System.out.println("⚠️ No enemy present to use the EMP Grenade!");
                            }
                        } else {
                            System.out.println("❌ Only hackers can use EMP grenades!");
                        }
                    }),
            new ItemHero("Advanced Hacking Tool", "Bypasses high-security systems.", 60,
                    (Hero player) -> System.out.println("💻 You bypass a high-security system!")));

    // 🔹 **Chemist Items (Only for TacticalChemist)**
    public static final List<Item> CHEMIST_ITEMS = Arrays.asList(
            new ItemHero("Explosive Charge", "A strong explosive useful for breaking doors or attacking.", 55,
                    (Hero player) -> System.out.println("💣 You plant an explosive charge!")),
            new ItemHero("Chemical Booster", "Your next attack is twice as strong.", 40,
                    (Hero player) -> {
                        System.out.println("☣️ Your next attack is twice as strong!");
                        player.boostAttack(10, 1);
                    }));
    // 🔹 **Starting Weapons Based on Hero Type**
    public static final Weapon HACKER_WEAPON = new Weapon(
            "Tranquilizer Dart Gun", "A hacking tool that also weakens enemies.", 10, 5, 10);

    public static final Weapon BIOENGINEER_WEAPON = new Weapon(
            "Biotech Shock Gloves", "Electrifies enemies with biotech pulses.", 12, 6, 10);

    public static final Weapon CHEMIST_WEAPON = new Weapon(
            "Chemical Grenade Launcher", "Launches explosive chemical vials.", 15, 7, 12);

    /**
     * Returns the correct starting weapon based on hero type.
     */
    public static Weapon getStartingWeaponForHero(Hero player) {
        if (player instanceof PharmacologistHacker) {
            return HACKER_WEAPON;
        } else if (player instanceof Bioengineer) {
            return BIOENGINEER_WEAPON;
        } else if (player instanceof TacticalChemist) {
            return CHEMIST_WEAPON;
        }
        return null; // Default case (should not happen)
    }

    /**
     * Returns a list of items based on the hero type.
     */
    public static List<? extends Item> getItemsForHero(Hero player) {
        if (player instanceof Bioengineer) {
            return BIOENGINEER_ITEMS;
        } else if (player instanceof PharmacologistHacker) {
            return HACKER_ITEMS;
        } else if (player instanceof TacticalChemist) {
            return CHEMIST_ITEMS;
        }
        return List.of();
    }
}
