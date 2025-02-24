package src.items;

import src.entities.Entity;
import src.entities.Hero;
import src.entities.HeroClass;
import src.game.Battle;
import src.status.AttackBoost;
import src.status.Burning;
import src.status.Regeneration;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ItemRegistry {
    // 🔹 **Healing Items (For all hero types)**
    public static final List<HealthPotion> HEALING_ITEMS = Arrays.asList(
            new HealthPotion(HealthPotionSize.Medium),
            new HealthPotion(HealthPotionSize.Large),
            new HealthPotion(HealthPotionSize.Small));

    // 🔹 **Weapons**
    public static final List<Weapon> WEAPONS = Arrays.asList(
            // TODO: These weapons could apply status effects or have special abilities.
            new Weapon("Chemical Blade", "A sharp blade coated with deadly toxins.", 24, 50, null),
            new Weapon("Toxic Dart Gun", "Shoots poison darts that weaken enemies.", 27, 60, null),
            new Weapon("Cryo Injector", "Freezes enemies, slowing them down.", 25, 55, null));

    public static final List<Item> BIOENGINEER_ITEMS = Arrays.asList(
            new HealthPotion(HealthPotionSize.Medium),
            new HealthPotion(HealthPotionSize.Large),
            new ItemHero("Regeneration Serum", "Regenerates HP over time (+30 HP per turn for 3 turns).", 50,
                    Set.of(HeroClass.BIOENGINEER), (Hero player) -> {
                        System.out.println("🩸 You inject the Regeneration Serum! HP will regenerate each turn.");
                        player.applyStatus(new Regeneration(3, 30));
                    }));

    public static final List<Item> HACKER_ITEMS = Arrays.asList(
            new ItemBattle("EMP Grenade", "Disables enemy technology for 2 turns.", 40,
                    Set.of(HeroClass.PHARMACOLOGIST_HACKER), (Battle battle) -> {
                        List<Entity> enemies = battle.getEnemies();
                        boolean hasElectronicEnemies = false;
                        for (Entity entity : enemies) {
                            if (entity.isElectronic()) {
                                entity.disable(2);
                                hasElectronicEnemies = true;
                            }
                        }
                        if (hasElectronicEnemies) {
                            System.out.println("💥 You throw an EMP Grenade! Electronic enemies are stunned.");
                        } else {
                            System.out.println("⚠️ No enemy was affected by the EMP Grenade!");
                        }
                    }));

    public static final List<Item> CHEMIST_ITEMS = Arrays.asList(
            new ItemBattle("Explosive Charge", "A strong explosive useful for breaking doors or attacking.", 55,
                    Set.of(HeroClass.TACTICAL_CHEMIST),
                    (Battle battle) -> {
                        System.out.println("💣 You plant an explosive charge!");
                        List<Entity> enemies = battle.getEnemies();
                        for (Entity entity : enemies) {
                            entity.takeDamage(30);
                            System.out.println("💥 " + entity.getName() + " takes 30 damage!");
                        }
                    }),
            new ItemBattle("Fire Bomb", "Throws a fire bomb that burns enemies for 3 turns.", 45,
                    Set.of(HeroClass.TACTICAL_CHEMIST), (Battle battle) -> {
                        System.out.println("🔥 You throw a Fire Bomb");
                List<Entity>enemies = battle.getEnemies();
                        for (Entity entity : enemies) {
                            entity.applyStatus(new Burning(3, 15));
                            System.out.println("🔥 " + entity.getName() + " is burning!");
                        }
                    }),
            new ItemHero("Chemical Booster", "Your next attacks are stronger.", 40,
                    Set.of(HeroClass.TACTICAL_CHEMIST), (Hero player) -> {
                        System.out.println("☣️ Your next attacks are stronger!");
                        player.applyStatus(new AttackBoost(3, 15));
                    }));

    public static final Weapon HACKER_WEAPON = new Weapon(
            "Tranquilizer Dart Gun", "A hacking tool that also weakens enemies.", 10, 25,
            Set.of(HeroClass.PHARMACOLOGIST_HACKER));

    public static final Weapon BIOENGINEER_WEAPON = new Weapon(
            "Biotech Shock Gloves", "Electrifies enemies with biotech pulses.", 12, 26,
            Set.of(HeroClass.BIOENGINEER));

    public static final Weapon CHEMIST_WEAPON = new Weapon(
            "Chemical Grenade Launcher", "Launches explosive chemical vials.", 15, 30,
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
