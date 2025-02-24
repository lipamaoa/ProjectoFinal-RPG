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
        System.out.println("\n       ⚔️  **BATTLE MODE INITIATED**  ⚔️");
        System.out.println("───────────────────────────────────────────────────────");
        System.out.println("💥  The battlefield is set. Enemies are closing in...");
        System.out.println("⚠️  Prepare for combat! Choose your strategy wisely.");
        System.out.println("───────────────────────────────────────────────────────");
    }

    public static void showVictoryScreen(String name) {
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("          🏆 🎉  V I C T O R Y ! 🎉 🏆");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🎉 " + name + " won the battle!");
        System.out.println("🔥 You have defeated your enemy in an epic battle! 🔥");
        System.out.println("💪 Your skill and strategy have led you to glory! 💪");
        System.out.println("\n🌟 Congratulations! 🌟\n");

    }


    public static void showDefeatScreen() {
        System.out.println("\n💀  **FATAL ERROR: LIFE SIGNS LOST** 💀");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🧬  Your vision fades as the cold steel floor meets you...");
        System.out.println("📂  All your research, all your efforts—erased.");
        System.out.println("🏢  PharmaCorp continues its experiments, unchallenged.");
        System.out.println("☠️  The truth dies with you...");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

    }


    public static void showFinalBattleScreen() {
        System.out.println("\n       🔥⚔️  **FINAL SHOWDOWN BEGINS!**  ⚔️🔥");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("💀  The battlefield trembles as your final enemy approaches...");
        System.out.println("🌩️  The storm rages. The fate of everything rests on this battle.");
        System.out.println("⚠️  **FIGHT WITH EVERYTHING YOU HAVE! THIS IS YOUR LAST CHANCE!**");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }


    public static void escapeTheLabScreen() {
        System.out.println("\n🚨 **ESCAPE SUCCESSFUL!** 🚨");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🏆  **CONGRATULATIONS! YOU ESCAPED THE LAB!**");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🏃‍♂️  As you sprint through the final security doors...");
        System.out.println("🌫️  The cold night air hits your face—freedom, at last.");
        System.out.println("🔎  But PharmaCorp's influence extends far beyond these walls...");
        System.out.println("⚖️  Will you expose their secrets or disappear into the shadows?");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }



}
