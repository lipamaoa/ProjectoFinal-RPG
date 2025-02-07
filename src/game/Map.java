package src.game;

import src.entities.Hero;
import src.entities.FriendlyNPC;
import src.game.Room;
import src.game.Mission;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles navigation between rooms.
 */
public class Map {
    private ArrayList<Room> rooms;
    private Scanner scanner;

    public Map() {
        this.rooms = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeRooms();
    }

    /**
     * Creates different rooms with NPCs and events.
     */
    private void initializeRooms() {

        Mission findSecretFiles = new Mission("Find Secret Files", "Retrieve the hidden PharmaCorp documents.", 20, 50);
        Mission defeatMutant = new Mission("Defeat the Mutant Experiment", "Destroy the escaped mutant in the lab.", 30, 60);

        
        FriendlyNPC drMira = new FriendlyNPC(
            "Dr. Mira",
            "I used to work in PharmaCorp before they betrayed us. They said they were curing diseases, but they were creating weapons...",
            new String[]{"There are encrypted files in the Security Department.", "Eden-9... I wish I had never seen it."},
            true, true, false, findSecretFiles
        );

        FriendlyNPC exSoldier = new FriendlyNPC(
            "Ex-PharmaCorp Soldier",
            "I was hired to protect this lab... but I never signed up for what they did to people down here.",
            new String[]{"Their experiments went out of control. No one is safe.", "If you're going to fight Eden-9, make sure you're ready."},
            false, false, true, defeatMutant
        );

        rooms.add(new Room("Biological Research Lab", true, false, true, drMira));
        rooms.add(new Room("Security Department", true, false, false, null));
        rooms.add(new Room("Human Testing Facility", true, false, true, exSoldier));
        rooms.add(new Room("Chemical Storage", false, true, true, null));
        rooms.add(new Room("Complex Exit", true, false, false, null));
    }

    /**
     * Allows the player to navigate between rooms.
     */
    public void startExploring(Hero player) {
        int currentRoomIndex = 0;

        while (currentRoomIndex < rooms.size()) {
            Room currentRoom = rooms.get(currentRoomIndex);
            currentRoom.enter(player);

            if (player.getCurrentHp() <= 0) {
                System.out.println("💀 You did not survive...");
                break;
            }

            if (currentRoomIndex == rooms.size() - 1) {
                System.out.println("🎉 You reached the final room! Prepare for the final battle!");
                break;
            }

            System.out.println("\n📍 Choose where to go next:");
            for (int i = currentRoomIndex + 1; i < rooms.size(); i++) {
                System.out.println((i - currentRoomIndex) + "️⃣ " + rooms.get(i).getName());
            }

            int choice = scanner.nextInt();
            currentRoomIndex += choice;
        }
    }
}
