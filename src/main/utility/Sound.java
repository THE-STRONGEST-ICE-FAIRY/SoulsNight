package main.utility;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

public class Sound {
    private final HashMap<String, Clip> sounds;
    public HashMap<String, Boolean> lock;
    boolean mute;
    public Random random;

    public Sound() {
        sounds = new HashMap<>();
        lock = new HashMap<>();
        mute = false;
        random = new Random();

        String src = "src/assets/sounds/";
        loadSound("MENU", src + "menuBGM.wav");
        loadSound("GAME1", src + "gameplay bgm1.wav");
        loadSound("GAME2", src + "gameplay bgm2.wav");
        loadSound("DEATH", src + "Death bgm.wav");
        loadSound("CLICK1", src + "stars1.wav");
        loadSound("CLICK2", src + "stars2.wav");
        loadSound("CLICK3", src + "stars3.wav");
        loadSound("CLICK4", src + "stars4.wav");
        loadSound("WIN", src + "win bgm.wav");
    }

    // Method to load a sound file into the HashMap
    public void loadSound(String key, String filePath) {
        lock.put(key, false);
        File soundFile = new File(filePath);
        if (!soundFile.exists()) {
            System.err.println("Sound file not found: " + filePath);
            return;
        }

        try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile)) {
            AudioFormat baseFormat = audioIn.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            try (AudioInputStream decodedAudioIn = AudioSystem.getAudioInputStream(decodedFormat, audioIn)) {
                Clip clip = AudioSystem.getClip();
                clip.open(decodedAudioIn);
                sounds.put(key, clip);
            }
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: " + e.getMessage());
        }
    }

    // Method to play a sound from the HashMap
    public void play(String key) {
        if (mute) return;

        Clip clip = sounds.get(key);
        if (clip != null) {
            new Thread(() -> {
                if (clip.isRunning()) {
                    clip.stop(); // Stop the clip if it's already playing
                }
                clip.setFramePosition(0); // Rewind to the beginning
                clip.start();
            }).start();
        } else {
            System.err.println("Sound not found: " + key);
        }
    }

    // Method to play a sound on a loop
    public void playOnLoop(String key) {
        if (mute) return;

        Clip clip = sounds.get(key);
        if (clip != null) {
            new Thread(() -> {
                if (clip.isRunning()) {
                    clip.stop(); // Stop the clip if it's already playing
                }
                clip.setFramePosition(0); // Rewind to the beginning
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the clip
                clip.start();
            }).start();
        } else {
            System.err.println("Sound not found: " + key);
        }
    }

    // Method to stop a sound
    public void stop(String key) {
        Clip clip = sounds.get(key);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        } else {
            System.err.println("Sound not playing or not found: " + key);
        }
    }

    public void stopAll() {
        for (Clip clip : sounds.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }

    // Example main method to show usage
    public static void main(String[] args) {
        Sound sound = new Sound();
        sound.loadSound("test", "path_to_your_sound_file.wav");
        sound.play("test");

        // To stop the sound
        // sound.stop("test");

        // To play the sound on loop
        // sound.playOnLoop("test");
    }

    public void once(String s) {
        System.out.println(lock.get(s));
        if (!lock.get(s)) {
            play(s);
            lock.put(s, true);
            System.out.println(lock.get(s));
        }
    }
}
