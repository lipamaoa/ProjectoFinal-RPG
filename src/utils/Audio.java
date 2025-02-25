package src.utils;

import javax.sound.sampled.*;
import java.io.*;

/**
 * Utility class for playing audio files in the game.
 */
public class Audio {

    /**
     * Plays an audio file (.wav) during the execution of the program.
     *
     * @param path The path to the audio file.
     */
    public static void playMusic(String path) {
        try {
            File audio = new File(path);

            // Check if the file exists and is valid
            if (!audio.exists() || !audio.isFile()) {
                System.out.println("❌ Error: Audio file not found -> " + path);
                return;
            }

            // Load the audio file as an input stream
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                    new BufferedInputStream(new FileInputStream(audio)));

            // Create a Clip object to play the audio
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();

            // Wait for the audio to finish playing
            clip.drain();

        } catch (UnsupportedAudioFileException e) {
            System.out.println("❌ Error: Unsupported audio format. Ensure the .wav file is PCM 16-bit.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("❌ Error: Issue loading the audio file.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("❌ Error: Audio line unavailable.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("❌ Unknown error occurred while playing audio.");
            e.printStackTrace();
        }
    }
}
