package src.items;

import java.util.List;
import java.util.Set;
import java.util.Arrays;
import src.entities.*;
import src.game.Battle;

public class ItemRegistry {
    // 🔹 **Healing Items (For all hero types)**
    public static final List<HealthPotion> HEALING_ITEMS = Arrays.asList(
            new HealthPotion(HealthPotionSize.Medium),
            new HealthPotion(HealthPotionSize.Large),
            new HealthPotion(HealthPotionSize.Small));

    // 🔹 **Weapons**
    public static final List<Weapon> WEAPONS = Arrays.asList(
            new Weapon("Chemical Blade", "A sharp blade coated with deadly toxins.", 15, 8, 12, null),
            new Weapon("Toxic Dart Gun", "Shoots poison darts that weaken enemies.", 20, 10, 15, null),
            new Weapon("Cryo Injector", "Freezes enemies, slowing them down.", 18, 7, 14, null));

    public static final List<Item> BIOENGINEER_ITEMS = Arrays.asList(
            new HealthPotion(HealthPotionSize.Medium),
            new HealthPotion(HealthPotionSize.Large),
            new ItemHero("Regeneration Serum", "Regenerates HP over time (+10 HP per turn for 3 turns).", 50,
                    Set.of(HeroClass.BIOENGINEER), (Hero player) -> {
                        System.out.println("🩸 You inject the Regeneration Serum! HP will regenerate each turn.");
                        // ((Bioengineer) player).healOverTime(25, 3);
                    }));

    public static final List<Item> HACKER_ITEMS = Arrays.asList(
            new ItemBattle("EMP Grenade", "Disables enemy technology for 2 turns.", 40,
                    Set.of(HeroClass.PHARMACOLOGIST_HACKER), (Battle battle) -> {
                        var player = battle.getPlayer();
                        // Enemy currentEnemy = battle.getEnemy();
                        // if (currentEnemy != null) {
                        // System.out.println("💥 You throw an EMP Grenade! Electronic enemies are
                        // stunned.");
                        // ((PharmacologistHacker) player).useEmpGrenade(currentEnemy);
                        // } else {
                        // System.out.println("⚠️ No enemy present to use the EMP Grenade!");
                        // }
                    }),
            new ItemHero("Advanced Hacking Tool", "Bypasses high-security systems.", 60,
                    Set.of(HeroClass.PHARMACOLOGIST_HACKER),
                    (Hero player) -> System.out.println("💻 You bypass a high-security system!")));

    public static final List<Item> CHEMIST_ITEMS = Arrays.asList(
            new ItemHero("Explosive Charge", "A strong explosive useful for breaking doors or attacking.", 55,
                    Set.of(HeroClass.TACTICAL_CHEMIST),
                    (Hero player) -> System.out.println("💣 You plant an explosive charge!")),
            new ItemHero("Chemical Booster", "Your next attack is twice as strong.", 40,
                    Set.of(HeroClass.TACTICAL_CHEMIST), (Hero player) -> {
                        System.out.println("☣️ Your next attack is twice as strong!");
                        // player.boostAttack(10, 1);
                    }));

    public static final Weapon HACKER_WEAPON = new Weapon(
            "Tranquilizer Dart Gun", "A hacking tool that also weakens enemies.", 10, 5, 10,
            Set.of(HeroClass.PHARMACOLOGIST_HACKER));

    public static final Weapon BIOENGINEER_WEAPON = new Weapon(
            "Biotech Shock Gloves", "Electrifies enemies with biotech pulses.", 12, 6, 10,
            Set.of(HeroClass.BIOENGINEER));

    public static final Weapon CHEMIST_WEAPON = new Weapon(
            "Chemical Grenade Launcher", "Launches explosive chemical vials.", 15, 7, 12,
            Set.of(HeroClass.TACTICAL_CHEMIST));

    public static Weapon getStartingWeaponForHero(Hero player) {
        if (player.getHeroClass() == HeroClass.PHARMACOLOGIST_HACKER)
            return HACKER_WEAPON;
        if (player.getHeroClass() == HeroClass.BIOENGINEER)
            return BIOENGINEER_WEAPON;
        if (player.getHeroClass() == HeroClass.TACTICAL_CHEMIST)
            return CHEMIST_WEAPON;
        return null;
    }

    public static List<Item> getItemsForHero(Hero player) {
        switch (player.getHeroClass()) {
            case BIOENGINEER:
                return BIOENGINEER_ITEMS;
            case PHARMACOLOGIST_HACKER:
                return HACKER_ITEMS;
            case TACTICAL_CHEMIST:
                return CHEMIST_ITEMS;
            default:
                return List.of();
        }
    }
}
