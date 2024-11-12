package main.utility;

import java.io.*;
import java.util.LinkedList;

public class Save {

    public static void save(LinkedList<String> strings, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String str : strings) {
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving the file: " + e.getMessage());
        }
    }

    public static LinkedList<String> load(String fileName) {
        LinkedList<String> strings = new LinkedList<>();
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
            }
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return strings;
    }
}
