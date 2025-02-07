package src.game;

import src.entities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles room navigation, battles, and mission progression.
 */
public class Map {
    private List<Room> rooms;
    private Scanner scanner;
    private boolean complexExitUnlocked = false; // Locked until player progresses

    public Map() {
        this.rooms = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        initializeRooms();
    }

    /**
     * Creates different rooms with predefined enemies and NPCs.
     */
    private void initializeRooms() {
        // Create predefined enemy lists for each room
        List<NPC> labEnemies = List.of(
                new NPC("Security Guard", 50, 8, 10, 1),
                new NPC("Lab Mutant", 60, 10, 15, 2));

        List<NPC> securityEnemies = List.of(
                new NPC("Security Commander", 70, 12, 20, 3),
                new NPC("Automated Drone", 80, 14, 25, 3));

        List<NPC> testingEnemies = List.of(
                new NPC("Failed Experiment", 70, 12, 20, 3),
                new NPC("Enhanced Guard", 80, 14, 25, 3));

        List<NPC> finalBoss = List.of(new NPC("Prototype Eden-9", 120, 20, 70, 5));

        // Create Friendly NPCs with missions
        FriendlyNPC drMira = new FriendlyNPC(
                "Dr. Mira",
                "I used to work in PharmaCorp before they betrayed us. They said they were curing diseases, but they were creating weapons...",
                new String[] { "PharmaCorp is hiding something deep in the lab.",
                        "There are secret documents in the Security Department." },
                true, true, false, null 
        );

        FriendlyNPC exSoldier = new FriendlyNPC(
                "Ex-PharmaCorp Soldier",
                "I was part of the PharmaCorp security forces until I saw what they were doing. Now, I help those who fight back.",
                new String[] { "Be careful, some of the test subjects have mutated beyond control.",
                        "I have seen Prototype Eden-9... It’s unstoppable." },
                false, false, true, null 
        );

        // Create rooms with predefined enemies and NPCs
        rooms.add(new Room("Biological Research Lab", labEnemies, drMira));
        rooms.add(new Room("Security Department", securityEnemies, null));
        rooms.add(new Room("Human Testing Facility", testingEnemies, exSoldier));
        rooms.add(new Room("Chemical Storage", new ArrayList<>(), null)); // Vendor room, no enemies
        rooms.add(new Room("Complex Exit", finalBoss, null)); // Final battle room (LOCKED)
    }

    /**
     * Allows the player to navigate between rooms.
     */
    public void startExploring(Hero player) {
        int currentRoomIndex = 0;

        while (currentRoomIndex < rooms.size()) {
            Room currentRoom = rooms.get(currentRoomIndex);
            currentRoom.enter(player);

            // Check if the player is still alive
            if (player.getCurrentHp() <= 0) {
                System.out.println("💀 You did not survive...");
                break;
            }

            // Unlock the Complex Exit if key rooms are explored
            checkUnlockConditions();

            // Offer choices for the next room
            System.out.println("\n📍 Choose where to go next:");
            for (int i = currentRoomIndex + 1; i < rooms.size(); i++) {
                if (!complexExitUnlocked && rooms.get(i).getName().equals("Complex Exit")) {
                    System.out.println("❌ Complex Exit is locked. Explore more before proceeding.");
                    continue; // Skip displaying locked exit
                }
                System.out.println((i - currentRoomIndex) + "️⃣ " + rooms.get(i).getName());
            }

            int choice = scanner.nextInt();
            currentRoomIndex += choice;
        }
    }

    /**
     * Unlocks the Complex Exit once the player has completed key rooms.
     */
    private void checkUnlockConditions() {
        if (!complexExitUnlocked) {
            boolean securityDepartmentCleared = rooms.get(1).isCompleted(); // Security Department
            boolean humanTestingFacilityCleared = rooms.get(2).isCompleted(); // Human Testing Facility

            if (securityDepartmentCleared && humanTestingFacilityCleared) {
                complexExitUnlocked = true;
                System.out.println("🔓 The Complex Exit is now unlocked!");
            }
        }
    }
}
