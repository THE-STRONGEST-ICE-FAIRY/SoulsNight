package main.main;

import main.objects.Assets;
import main.objects.Object;
import main.utility.Camera;
import main.utility.Cursor;
import main.utility.Key;

import java.awt.*;
import java.util.*;

public class MainMenu {
    boolean visible;
    Cursor cursor;
    Camera camera;
    Key key;
    HashMap<String, HashMap<String, Object>> objects;
    HashMap<String, HashMap<String, Image>> images;
    LinkedList<Object> objectsMainMenu;
    LinkedList<Object> objectsStart;
    LinkedList<Object> objectsAbout;
    LinkedList<Object> objectsAbout0;
    LinkedList<Object> objectsAbout1;
    LinkedList<Object> objectsAbout2;
    LinkedList<Object> objectsSettings;
    Game game;
    boolean about, settings;
    Assets assets;
    int aboutPanel;

    MainMenu(int width, int height, Assets assets, Cursor cursor, Camera camera, Key key) {
        visible = true;
        this.cursor = cursor;
        this.camera = camera;
        this.key = key;

        this.assets = assets;
        assets.mainMenu(width, height);
        assets.sound.playOnLoop("MENU");
        objects = new HashMap<>();
        images = new HashMap<>();

        about = false;
        aboutPanel = 0;
        settings = false;

        {
            objects.put("MAIN MENU", new HashMap<>(assets.objects.get("MAIN MENU")));
            images.put("MAIN MENU", new HashMap<>(assets.images.get("MAIN MENU")));
            objectsMainMenu = new LinkedList<>(objects.get("MAIN MENU").values());
            objectsMainMenu.sort(Comparator.comparingDouble(Object::getPriorityZ));

            objects.put("START", new HashMap<>(assets.objects.get("MAIN MENU START")));
            images.put("START", new HashMap<>(assets.images.get("MAIN MENU START")));
            objectsStart = new LinkedList<>(objects.get("START").values());
            objectsStart.sort(Comparator.comparingDouble(Object::getPriorityZ));

            objects.put("ABOUT", new HashMap<>(assets.objects.get("MAIN MENU ABOUT")));
            images.put("ABOUT", new HashMap<>(assets.images.get("MAIN MENU ABOUT")));
            objectsAbout = new LinkedList<>(objects.get("ABOUT").values());
            objectsAbout.sort(Comparator.comparingDouble(Object::getPriorityZ));

            objects.put("ABOUT0", new HashMap<>(assets.objects.get("MAIN MENU ABOUT0")));
            images.put("ABOUT0", new HashMap<>(assets.images.get("MAIN MENU ABOUT0")));
            objectsAbout0 = new LinkedList<>(objects.get("ABOUT0").values());
            objectsAbout0.sort(Comparator.comparingDouble(Object::getPriorityZ));

            objects.put("ABOUT1", new HashMap<>(assets.objects.get("MAIN MENU ABOUT1")));
            images.put("ABOUT1", new HashMap<>(assets.images.get("MAIN MENU ABOUT1")));
            objectsAbout1 = new LinkedList<>(objects.get("ABOUT1").values());
            objectsAbout1.sort(Comparator.comparingDouble(Object::getPriorityZ));

            objects.put("ABOUT2", new HashMap<>(assets.objects.get("MAIN MENU ABOUT2")));
            images.put("ABOUT2", new HashMap<>(assets.images.get("MAIN MENU ABOUT2")));
            objectsAbout2 = new LinkedList<>(objects.get("ABOUT2").values());
            objectsAbout2.sort(Comparator.comparingDouble(Object::getPriorityZ));

            objects.put("SETTINGS", new HashMap<>(assets.objects.get("MAIN MENU SETTINGS")));
            images.put("SETTINGS", new HashMap<>(assets.images.get("MAIN MENU SETTINGS")));
            objectsSettings = new LinkedList<>(objects.get("SETTINGS").values());
            objectsSettings.sort(Comparator.comparingDouble(Object::getPriorityZ));
        }
    }

    void setGame(Game game) {
        this.game = game;
    }

    public void draw(Graphics2D gg) {
        if (!visible) return;

        script();

        for (Object o : objectsMainMenu) o.draw(gg, 0, 0, false);
        if (!about && !settings) for (Object o : objectsStart) o.draw(gg, 0, 0, false);
        if (about)  {
            for (Object o : objectsAbout) o.draw(gg, 0, 0, false);
            switch (aboutPanel) {
                case 0 -> {
                    for (Object o : objectsAbout0) o.draw(gg, 0, 0, false);
                }
                case 1 -> {
                    for (Object o : objectsAbout1) o.draw(gg, 0, 0, false);
                }
                case 2 -> {
                    for (Object o : objectsAbout2) o.draw(gg, 0, 0, false);
                }
            }
        }
        if (settings)  for (Object o : objectsSettings) o.draw(gg, 0, 0, false);
    }

    void script() {
        Object button;
        HashMap<String, Object> map;
        HashMap<String, Image> mapImage;

        if (!about && !settings) {
            map = objects.get("START");
            mapImage = images.get("START");
            button = map.get("START BUTTON");
            String soundKey = "CLICK" + assets.sound.random.nextInt(1, 5);
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (button.image == mapImage.get("START OFF")) assets.sound.play(soundKey);
                button.image = mapImage.get("START ON");
            }
            else button.image = mapImage.get("START OFF");

            button = map.get("ABOUT BUTTON");
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (button.image == mapImage.get("ABOUT OFF")) assets.sound.play(soundKey);
                button.image = mapImage.get("ABOUT ON");
            }
            else button.image = mapImage.get("ABOUT OFF");

            button = map.get("SETTINGS BUTTON");
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (button.image == mapImage.get("SETTINGS OFF")) assets.sound.play(soundKey);
                button.image = mapImage.get("SETTINGS ON");
            }
            else button.image = mapImage.get("SETTINGS OFF");

            button = map.get("EXIT BUTTON");
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (button.image == mapImage.get("EXIT OFF")) assets.sound.play(soundKey);
                button.image = mapImage.get("EXIT ON");
            }
            else button.image = mapImage.get("EXIT OFF");
        } else if (about) {
            String soundKey = "CLICK" + assets.sound.random.nextInt(1, 5);
            map = objects.get("ABOUT");
            button = map.get("MAIN MENU BUTTON");
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (!map.get("MAIN MENU SIGN").visible) assets.sound.play(soundKey);
                map.get("MAIN MENU SIGN").visible = true;
            }
            else map.get("MAIN MENU SIGN").visible = false;

            button = map.get("NEXT BUTTON");
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (!map.get("NEXT SIGN").visible) assets.sound.play(soundKey);
                map.get("NEXT SIGN").visible = true;
            }
            else map.get("NEXT SIGN").visible = false;

            button = map.get("BACK BUTTON");
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (!map.get("BACK SIGN").visible) assets.sound.play(soundKey);
                map.get("BACK SIGN").visible = true;
            }
            else map.get("BACK SIGN").visible = false;
        }
        else {
            String soundKey = "CLICK" + assets.sound.random.nextInt(1, 5);
            map = objects.get("SETTINGS");
            mapImage = images.get("SETTINGS");
            button = map.get("BACK BUTTON");
            if (button.hovering(cursor.getX(), cursor.getY())) {
                if (button.image == mapImage.get("BACK OFF")) assets.sound.play(soundKey);
                button.image = mapImage.get("BACK ON");
            }
            else button.image = mapImage.get("BACK OFF");
        }

        if (cursor.click) click();
    }

    void click() {
        HashMap<String, Object> map;

        if (!about && !settings) {
            map = objects.get("START");

            if (map.get("START BUTTON").hovering(cursor.getX(), cursor.getY())) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                visible = false;
                game.start();
            }
            if (map.get("ABOUT BUTTON").hovering(cursor.getX(), cursor.getY())) {
                about = true;
            }
            if (map.get("SETTINGS BUTTON").hovering(cursor.getX(), cursor.getY())) {
                settings = true;
            }
            if (map.get("EXIT BUTTON").hovering(cursor.getX(), cursor.getY())) System.exit(0);
        }
        else if (about) {
            map = objects.get("ABOUT");

            if (map.get("MAIN MENU BUTTON").hovering(cursor.getX(), cursor.getY())) {
                about = false;
            }

            if (map.get("NEXT BUTTON").hovering(cursor.getX(), cursor.getY())) {
                aboutPanel = (aboutPanel + 1) % 3;
            }

            if (map.get("BACK BUTTON").hovering(cursor.getX(), cursor.getY())) {
                aboutPanel = (aboutPanel + 2) % 3;
            }
        }
        else {
            map = objects.get("SETTINGS");

            if (map.get("BACK BUTTON").hovering(cursor.getX(), cursor.getY())) {
                settings = false;
            }
        }

        cursor.clickCD = 5;
        cursor.click = false;
    }

    public void playBGM() {
        assets.sound.playOnLoop("MENU");
    }
}
