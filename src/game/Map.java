package src.game;

import src.entities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles room navigation, battles, and mission progression.
 */
public class Map {
    private List<Room> rooms;
    private boolean complexExitUnlocked = false;
    private final Random rand;

    public Map() {
        this.rooms = new ArrayList<>();
        this.rand = GameRandom.getInstance();
        initializeRooms();
    }

    /**
     * Picks a random NPC from the list of available NPCs and removes it to ensure
     * it can't be repeated.
     */
    private FriendlyNPC getRandomUniqueFriendlyNPC(List<FriendlyNPC> availableNPCs) {
        if (availableNPCs.isEmpty()) {
            return null; // or throw an exception if no NPCs are available
        }
        int index = rand.nextInt(availableNPCs.size());
        return availableNPCs.remove(index);
    }

    /**
     * Creates different rooms with predefined enemies and NPCs.
     */
    private void initializeRooms() {
        List<FriendlyNPC> availableNPCs = new ArrayList<>(NPCRegistry.FRIENDLY_NPC);

        // Create rooms with predefined enemies and NPCs
        rooms.add(new Room("Biological Research Lab", NPCRegistry.LAB_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), false));
        rooms.add(new Room("Security Department", NPCRegistry.SECURITY_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), false));
        rooms.add(new Room("Human Testing Facility", NPCRegistry.TESTING_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), false));
        rooms.add(new Room("Chemical Storage - Safe Zone", new ArrayList<>(),
                getRandomUniqueFriendlyNPC(availableNPCs), true));
        rooms.add(new Room("Chemical Storage - Contaminated Zone", NPCRegistry.CHEMICAL_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), false));
        rooms.add(new Room("Archives", NPCRegistry.ARCHIVE_ENEMIES, getRandomUniqueFriendlyNPC(availableNPCs),
                false));
        // Final battle room (LOCKED)
        rooms.add(new Room("Complex Exit", NPCRegistry.FINAL_BOSS, null, false));

        // Randomly assign a vendor to one of the rooms (excluding the final boss room)
        int vendorRoomIndex = rand.nextInt(rooms.size() - 1);

        // Replace the selected room with a new version that has a vendor
        Room vendorRoom = rooms.get(vendorRoomIndex);
        rooms.set(vendorRoomIndex,
                new Room(vendorRoom.getName(), vendorRoom.getEnemies(), vendorRoom.getFriendlyNPC(), true));
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
                    continue;
                }
                System.out.println((i - currentRoomIndex) + "️⃣ " + rooms.get(i).getName());
            }

            int choice = GameScanner.getInt();
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
