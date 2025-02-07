package src.utils;

/**
 * Stores ASCII art for the game's interface.
 */
public class AsciiArt {

    public static void showWelcomeScreen() {
        System.out.println("\n");
        System.out.println("████████╗ █████╗ ██████╗ ███████╗███╗   ██╗██████╗  █████╗ ██╗  ██╗");
        System.out.println("╚══██╔══╝██╔══██╗██╔══██╗██╔════╝████╗  ██║██╔══██╗██╔══██╗██║ ██╔╝");
        System.out.println("   ██║   ███████║██████╔╝█████╗  ██╔██╗ ██║██████╔╝███████║█████╔╝ ");
        System.out.println("   ██║   ██╔══██║██╔══██╗██╔══╝  ██║╚██╗██║██╔═══╝ ██╔══██║██╔═██╗ ");
        System.out.println("   ██║   ██║  ██║██║  ██║███████╗██║ ╚████║██║     ██║  ██║██║  ██╗");
        System.out.println("   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═══╝╚═╝     ╚═╝  ╚═╝╚═╝  ╚═╝");
        System.out.println("====================================================================");
        System.out.println("           Welcome to **LABORATORY OF CHAOS** - Escape PharmaCorp!\n");
    }

    public static void showBattleScreen() {
        System.out.println("\n⚔️  BATTLE MODE INITIATED  ⚔️");
        System.out.println("────────────────────────────────");
    }

    public static void showVictoryScreen() {
        System.out.println("\n🏆 **VICTORY!** 🏆");
        System.out.println("You have defeated your enemy!");
    }

    public static void showDefeatScreen() {
        System.out.println("\n💀 **DEFEAT!** 💀");
        System.out.println("You have been defeated...");
    }
}
