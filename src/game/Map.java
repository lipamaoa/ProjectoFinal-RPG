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

    private Vendor AddVendor() {
        if (this.rand.nextInt(100) < 25) {
            return new Vendor();
        }

        return null;
    }

    /**
     * Creates different rooms with predefined enemies and NPCs.
     */
    private void initializeRooms() {
        List<FriendlyNPC> availableNPCs = new ArrayList<>(NPCRegistry.FRIENDLY_NPC);

        // Create rooms with predefined enemies and NPCs
        rooms.add(new Room("Biological Research Lab", NPCRegistry.LAB_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), AddVendor()));
        rooms.add(new Room("Security Department", NPCRegistry.SECURITY_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), AddVendor()));
        rooms.add(new Room("Human Testing Facility", NPCRegistry.TESTING_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), AddVendor()));
        rooms.add(new Room("Chemical Storage - Safe Zone", new ArrayList<>(),
                getRandomUniqueFriendlyNPC(availableNPCs), new Vendor()));
        rooms.add(new Room("Chemical Storage - Contaminated Zone", NPCRegistry.CHEMICAL_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), AddVendor()));
        rooms.add(new Room("Archives", NPCRegistry.ARCHIVE_ENEMIES, getRandomUniqueFriendlyNPC(availableNPCs),
                AddVendor()));
        // Final battle room (LOCKED)
        rooms.add(new Room("Complex Exit", null, null, null));
    }

    /**
     * Allows the player to choose a starting room and navigate between rooms.
     */
    public void startExploring(Hero player) {
        int currentRoomIndex = selectStartingRoom();

        while (true) {
            Room currentRoom = rooms.get(currentRoomIndex);
            currentRoom.enter(player);

            if (player.getCurrentHp() <= 0) {
                System.out.println("💀 You did not survive...");
                break;
            }

            checkUnlockConditions();

            System.out.println("\n📍 Choose where to go next:");
            List<Integer> validChoices = new ArrayList<>();

            for (int i = 0; i < rooms.size(); i++) {
                if (i == currentRoomIndex) {
                    continue;
                }
                var room = rooms.get(i);

                if (room.isCompleted()) {
                    continue;
                }

                if (!complexExitUnlocked && room.getName().equals("Complex Exit")) {
                    System.out.println("❌ Complex Exit is locked. Explore more before proceeding.");
                    continue;
                }

                System.out.println((validChoices.size() + 1) + "️⃣ " + room.getName());
                validChoices.add(i);
            }

            int choiceIndex = GameScanner.getInt() - 1;
            if (choiceIndex < 0 || choiceIndex >= validChoices.size()) {
                System.out.println("⚠️ Invalid choice, try again.");
                continue;
            }

            currentRoomIndex = validChoices.get(choiceIndex);
        }
    }

    /**
     * Allows the player to select a starting room.
     */
    private int selectStartingRoom() {
        System.out.println("🌍 Choose your starting location:");
        for (int i = 0; i < rooms.size() - 1; i++) {
            System.out.println((i + 1) + "️⃣ " + rooms.get(i).getName());
        }
        System.out.println("❌ Complex Exit is locked.");

        while (true) {
            int choice = GameScanner.getInt() - 1;
            if (choice >= 0 && choice < rooms.size() - 1) {
                return choice;
            }
            System.out.println("⚠️ Invalid selection. Please choose a valid starting room.");
        }
    }

    /**
     * Unlocks the Complex Exit once the player has completed key rooms.
     */
    private void checkUnlockConditions() {
        if (!complexExitUnlocked) {
            for (int i = 0; i < rooms.size() - 1; i++) {
                if (!rooms.get(i).isCompleted()) {
                    return;
                }
            }

            System.out.println("🔓 The Complex Exit is now unlocked!");
        }
    }

    public ArrayList<Entity> getSurvivingFriends() {
        ArrayList<Entity> survivingFriends = new ArrayList<>();
        for (Room room : rooms) {
            var survivingFriendlyNPC = room.getSurvivingFriendlyNPC();
            if (survivingFriendlyNPC != null) {
                survivingFriends.add(survivingFriendlyNPC);
            }
        }

        return survivingFriends;
    }
}
