package src.game;

import src.entities.*;
import src.items.HealthPotion;
import src.utils.AsciiArt;

import java.util.Scanner;

/**
 * Main game class that handles character creation and gameplay loop.
 */
public class Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

          // Display ASCII Welcome Screen
        AsciiArt.showWelcomeScreen();
    // Story Introduction
    System.out.println("\n📝 **Prologue**:");
    System.out.println("You were one of PharmaCorp’s top scientists, working on Project Eden-9.");
    System.out.println("But when you discovered the unethical experiments they were running...");
    System.out.println("...you knew you had to escape.");
    System.out.println("With enemies on your trail and a maze-like lab to navigate,");
    System.out.println("your survival depends on your wits, skills, and the choices you make.\n");

    System.out.println("Choose your hero: ");
    System.out.println("1️⃣ Pharmacologist Hacker - Expert in hacking and toxins.");
    System.out.println("2️⃣ Bioengineer - Specializes in regeneration and biological enhancements.");
    int choice = sc.nextInt();

        Hero player;
        if (choice == 1) {
            player = new PharmacologistHacker("Dr. Alex");
        } else {
            player = new Bioengineer("Dr. Taylor");
        }

        System.out.println("\nYour hero:");
        player.showDetails();

        // Give player a starting item
        System.out.println("\nYou found a Health Potion!");
        player.addItemToInventory(new HealthPotion(5, 25));

        // Show inventory
        System.out.println("\nChecking inventory...");
        player.showInventory();

          // Vendor Interaction
        Vendor vendor = new Vendor();
        System.out.println("\n🛒 You have encountered a vendor!");
        while (true) {
            System.out.println("\n1️⃣ Buy Items");
            System.out.println("2️⃣ Sell Items");
            System.out.println("3️⃣ Exit Vendor");
            int vendorChoice = sc.nextInt();

            switch (vendorChoice) {
                case 1:
                    vendor.buyItem(player);
                    break;
                case 2:
                    vendor.sellItem(player);
                    break;
                case 3:
                    System.out.println("👋 Exiting vendor...");
                    break;
                default:
                    System.out.println("❌ Invalid choice!");
            }

            if (vendorChoice == 3) break;
        }

        // Create an enemy
        NPC enemy = new NPC("PharmaCorp Guard", 50, 5, 10);

        // Start battle
        Battle battle = new Battle(player, enemy);
        battle.start();

        // Start exploring the lab with increasing difficulty
        Map gameMap = new Map();
        gameMap.startExploring(player);

        if (player.getCurrentHp() > 0) {
            AsciiArt.showBattleScreen();
            System.out.println("\n⚔️ FINAL BATTLE BEGINS!");
            NPC finalBoss = new NPC("Prototype Eden-9", 100, 15, 50, 5);
            Battle finalBattle = new Battle(player, finalBoss);
            finalBattle.start();
        }

       // Story Ending
       if (player.getCurrentHp() > 0) {
        System.out.println("\n🏆 **You escaped the laboratory!**");
        System.out.println("But PharmaCorp's influence extends beyond these walls...");
        System.out.println("Will you expose their secrets or go into hiding?");
    } else {
        System.out.println("\n💀 **You did not survive...**");
        System.out.println("Your research and discoveries will remain buried within PharmaCorp.");
    }

    System.out.println("\n🎮 **Game Over! Thanks for playing.**");

    }
    }

    
