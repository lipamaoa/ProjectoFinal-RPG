package src.game;

import src.entities.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Handles room navigation, battles, and mission progression.
 */
public class Map {
    private final List<Room> rooms;
    private boolean complexExitUnlocked = false;
    private final Random rand;

    /**
     * Constructs the game map and initializes rooms.
     */

    public Map() {
        this.rooms = new ArrayList<>();
        this.rand = GameRandom.getInstance();
        initializeRooms();
    }

    /**
     * Picks a random NPC from the list of available NPCs and removes it to ensure
     * it can't be repeated.
     *
     * @param availableNPCs The list of NPCs to select from.
     * @return A randomly selected friendly NPC or null if none are available.
     */
    private FriendlyNPC getRandomUniqueFriendlyNPC(List<FriendlyNPC> availableNPCs) {
        if (availableNPCs.isEmpty()) {
            return null; // or throw an exception if no NPCs are available
        }
        int index = rand.nextInt(availableNPCs.size());
        return availableNPCs.remove(index);
    }

    /**
     * Randomly decides whether a vendor should be present in a room.
     *
     * @return A Vendor instance if spawned, otherwise null.
     */
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
        rooms.add(new Room("Chemical Storage - Contaminated Zone",
                NPCRegistry.CHEMICAL_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs), AddVendor()));
        rooms.add(new Room("Archives", NPCRegistry.ARCHIVE_ENEMIES,
                getRandomUniqueFriendlyNPC(availableNPCs),
                AddVendor()));
    }

    /**
     * Starts the game exploration by allowing the player to select a starting room
     * and navigate between rooms.
     *
     * @param player The hero navigating the rooms.
     */
    public void startExploring(Hero player) {
        int currentRoomIndex = selectStartingRoom();

        while (true) {
            Room currentRoom = rooms.get(currentRoomIndex);
            currentRoom.enter(player);

            if (player.getCurrentHp() <= 0) {
                break;
            }

            checkUnlockConditions();
            if (this.complexExitUnlocked) {
                System.out.println("🔓 The Complex Exit is now unlocked!");
                break;
            }

            System.out.println("\n📍 Choose where to go next:");
            List<Integer> validChoices = new ArrayList<>();

            for (int i = 0; i < rooms.size(); i++) {
                if (i == currentRoomIndex) {
                    continue;
                }
                Room room = rooms.get(i);

                if (room.isCompleted()) {
                    continue;
                }

                System.out.println((validChoices.size() + 1) + "️⃣ " + room.getName());
                validChoices.add(i);
            }

            int choiceIndex = GameScanner.getIntInRange("", 1, validChoices.size()) - 1;

            currentRoomIndex = validChoices.get(choiceIndex);
        }
    }

    /**
     * Allows the player to select a starting room.
     *
     * @return The index of the selected room.
     */
    private int selectStartingRoom() {
        System.out.println("\n🌍  **CHOOSE YOUR STARTING LOCATION**");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        for (int i = 0; i < rooms.size(); i++) {
            System.out.println((i + 1) + "️⃣ " + rooms.get(i).getName());
        }
        System.out.println("\n🔒  ❌ Complex Exit is locked.\n");

        while (true) {
            System.out.print("➡️  Enter your choice: ");
            int choice = GameScanner.getInt() - 1;
            if (choice >= 0 && choice < rooms.size()) {
                return choice;
            }
            System.out.println("⚠️ Invalid selection. Please choose a valid starting room.");
        }
    }

    /**
     * Unlocks the Complex Exit once the player has completed key rooms.
     */
    private void checkUnlockConditions() {
        for (Room room : rooms) {
            if (!room.isCompleted()) {
                return;
            }
        }

        this.complexExitUnlocked = true;
    }

    /**
     * Retrieves the list of surviving friendly NPCs from completed rooms.
     *
     * @return A list of friendly NPCs that survived.
     */
    public ArrayList<Entity> getSurvivingFriends() {
        ArrayList<Entity> survivingFriends = new ArrayList<>();
        for (Room room : rooms) {
            FriendlyNPC survivingFriendlyNPC = room.getSurvivingFriendlyNPC();
            if (survivingFriendlyNPC != null) {
                survivingFriends.add(survivingFriendlyNPC);
            }
        }

        return survivingFriends;
    }
}
