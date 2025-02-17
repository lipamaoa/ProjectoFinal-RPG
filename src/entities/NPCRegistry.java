package src.entities;

import java.util.Arrays;
import java.util.List;

/**
 * Stores predefined NPCs for reuse across different rooms.
 */
public class NPCRegistry {
        // 🔹 Enemy Lists (Ensuring at least 3 per room)
        public static final List<Enemy> LAB_ENEMIES = Arrays.asList(
                        new Enemy("Security Guard", 50, 8, 10, 1),
                        new Enemy("Lab Mutant", 60, 10, 15, 2),
                        new Enemy("Rogue Scientist Experiment", 55, 9, 12, 2));

        public static final List<Enemy> SECURITY_ENEMIES = Arrays.asList(
                        new Enemy("Security Commander", 70, 12, 20, 3),
                        new Enemy("Automated Drone", 80, 14, 25, 3),
                        new Enemy("Cybernetic Watchdog", 75, 13, 22, 3));

        public static final List<Enemy> TESTING_ENEMIES = Arrays.asList(
                        new Enemy("Failed Experiment", 70, 12, 20, 3),
                        new Enemy("Enhanced Guard", 80, 14, 25, 3),
                        new Enemy("Bio-engineered Creature", 85, 16, 27, 4));

        public static final List<Enemy> CHEMICAL_ENEMIES = Arrays.asList(
                        new Enemy("Chemical Abomination", 90, 16, 30, 4),
                        new Enemy("Toxic Sludge Golem", 85, 15, 28, 4),
                        new Enemy("Venomous Hybrid", 88, 17, 31, 4));

        public static final List<Enemy> ARCHIVE_ENEMIES = Arrays.asList(
                        new Enemy("Data Guardian", 85, 15, 28, 4),
                        new Enemy("Sentient Firewall AI", 92, 17, 33, 5),
                        new Enemy("Corrupted Researcher", 89, 16, 30, 4));

        public static final List<Enemy> FINAL_BOSS = Arrays.asList(
                        new Enemy("Prototype Eden-9", 120, 20, 70, 5),
                        new Enemy("Eden-9 Alpha Form", 130, 22, 80, 6),
                        new Enemy("Eden-9 Beta Form", 140, 25, 90, 7));

        // 🔹 Friendly NPCs (Stored as a list for selection per room)
        public static final List<FriendlyNPC> FRIENDLY_NPC = Arrays.asList(
                        new FriendlyNPC("Dr. Mira",
                                        "I used to work in PharmaCorp before they betrayed us...",
                                        new String[] { "PharmaCorp is hiding something deep in the lab.",
                                                        "There are secret documents in the Security Department." },
                                        true, true, false, 80, 10),

                        new FriendlyNPC("Ex-PharmaCorp Soldier",
                                        "I was part of the PharmaCorp security forces...",
                                        new String[] { "Be careful, some of the test subjects have mutated beyond control.",
                                                        "Prototype Eden-9 is unstoppable." },
                                        false, false, true, 150, 20),

                        new FriendlyNPC("Rogue Scientist",
                                        "I have hidden blueprints that might help...",
                                        new String[] { "You must find the archives.",
                                                        "Their biological weapons are incomplete but dangerous." },
                                        false, true, false, 75, 3),

                        new FriendlyNPC("Resistance Spy",
                                        "I have infiltrated PharmaCorp’s network...",
                                        new String[] { "Security clearance is required.",
                                                        "Prototype Eden-9 has a weakness, but it's classified." },
                                        true, false, true, 99, 15),

                        new FriendlyNPC("Underground Hacker",
                                        "I cracked PharmaCorp’s security once, but they caught me...",
                                        new String[] { "Their AI is unstable.",
                                                        "Find the mainframe to disable their defenses." },
                                        false, true, false, 80, 5),

                        new FriendlyNPC("Lab Assistant",
                                        "I was forced to work here... I can help you understand their research.",
                                        new String[] { "The mutation formula is in the testing facility.",
                                                        "The security commander knows the exit codes." },
                                        false, false, true, 60, 1));
}
