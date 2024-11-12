package main.objects;

import main.main.Game;
import main.utility.MapSize;
import main.utility.Save;
import main.utility.Sound;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Assets {
    public HashMap<String, HashMap<String, Image>> images;
    public HashMap<String, LinkedList<Image>> imagesLinkedList;
    public HashMap<String, HashMap<String, Object>> objects;
    public Sound sound;

    public Assets() {
        images = new HashMap<>();
        objects = new HashMap<>();
        imagesLinkedList = new HashMap<>();
        sound = new Sound();
    }

    public void splash(int width, int height) {
        String x = "SPLASH";

        images.put(x, new HashMap<>());
        objects.put(x, new HashMap<>());

        images.get(x).put("BACKGROUND", new ImageIcon("src/assets/splash/splash.png").getImage());
        objects.get(x).put("BACKGROUND", new Object(images.get(x).get("BACKGROUND"), 0, 0, width, height));
        objects.get(x).get("BACKGROUND").priorityZ = 0;

        images.get(x).put("STAR1", new ImageIcon("src/assets/splash/star1.png").getImage());
        images.get(x).put("STAR2", new ImageIcon("src/assets/splash/star2.png").getImage());
        LinkedList<Image> starImages = new LinkedList<>(); {
            starImages.add(images.get(x).get("STAR1"));
            for (int i = 0; i < 4; i++) starImages.add(images.get(x).get("STAR2"));
            for (int i = 0; i < 3; i++) starImages.add(images.get(x).get("STAR1"));
        }
        objects.get(x).put("STAR", new Animated(starImages, 5, 175, 275, 200, 200));
    }

    public void mainMenu(int width, int height) {
        String x = "MAIN MENU";
        {
            images.put(x, new HashMap<>());
            objects.put(x, new HashMap<>());

            images.get(x).put("BACKGROUND", new ImageIcon("src/assets/mainMenu/main/bg.png").getImage());
            int[] ratio = ratio(1754, 1240, width, height);
            objects.get(x).put("BACKGROUND", new Object(images.get(x).get("BACKGROUND"), 0, 0, ratio[0], ratio[1]));
            objects.get(x).get("BACKGROUND").priorityZ = 0;
        }

        x = "MAIN MENU START";
        int w = 200;
        int h = 80;
        int x1 = 30;
        {
            images.put(x, new HashMap<>());
            objects.put(x, new HashMap<>());

            images.get(x).put("TITLE", new ImageIcon("src/assets/mainMenu/start/title.png").getImage());
            objects.get(x).put("TITLE", new Object(images.get(x).get("TITLE"), 0, 25, 1000, 410));

            int hAcc = 0, off = 100;
            images.get(x).put("START OFF", new ImageIcon("src/assets/mainMenu/start/startOff.png").getImage());
            images.get(x).put("START ON", new ImageIcon("src/assets/mainMenu/start/startOn.png").getImage());
            objects.get(x).put("START BUTTON", new Object(images.get(x).get("START OFF"), x1, height / 2 + hAcc, w, h));
            hAcc += off;

            images.get(x).put("ABOUT OFF", new ImageIcon("src/assets/mainMenu/start/aboutOff.png").getImage());
            images.get(x).put("ABOUT ON", new ImageIcon("src/assets/mainMenu/start/aboutOn.png").getImage());
            objects.get(x).put("ABOUT BUTTON", new Object(images.get(x).get("ABOUT OFF"), x1, height / 2 + hAcc, w, h));
            hAcc += off;

            images.get(x).put("SETTINGS OFF", new ImageIcon("src/assets/mainMenu/start/settingsOff.png").getImage());
            images.get(x).put("SETTINGS ON", new ImageIcon("src/assets/mainMenu/start/settingsOn.png").getImage());
            objects.get(x).put("SETTINGS BUTTON", new Object(images.get(x).get("SETTINGS OFF"), x1, height / 2 + hAcc, w, h));
            hAcc += off;

            images.get(x).put("EXIT OFF", new ImageIcon("src/assets/mainMenu/start/exitOff.png").getImage());
            images.get(x).put("EXIT ON", new ImageIcon("src/assets/mainMenu/start/exitOn.png").getImage());
            objects.get(x).put("EXIT BUTTON", new Object(images.get(x).get("EXIT OFF"), x1, height / 2 + hAcc, w, h));
        }

        x = "MAIN MENU ABOUT";
        {
            images.put(x, new HashMap<>());
            objects.put(x, new HashMap<>());

            int add = 150;
            images.get(x).put("MAIN MENU OFF", new ImageIcon("src/assets/mainMenu/about/mainmenu.png").getImage());
            images.get(x).put("MAIN MENU ON", new ImageIcon("src/assets/mainMenu/about/mainmenuOn.png").getImage());
            objects.get(x).put("MAIN MENU BUTTON", new Object(images.get(x).get("MAIN MENU OFF"), x1, height / 2 + 220 + add, w, h));
            objects.get(x).put("MAIN MENU SIGN", new Object(images.get(x).get("MAIN MENU ON"), x1, height / 2 + 220 - 66 + add, w, h * 2));

            images.get(x).put("NEXT OFF", new ImageIcon("src/assets/mainMenu/about/next.png").getImage());
            images.get(x).put("NEXT ON", new ImageIcon("src/assets/mainMenu/about/nextOn.png").getImage());
            objects.get(x).put("NEXT BUTTON", new Object(images.get(x).get("NEXT OFF"), x1, height / 2 - 220 + add, w, h));
            objects.get(x).put("NEXT SIGN", new Object(images.get(x).get("NEXT ON"), x1, height / 2 - 200 - h + add, w, h * 2));

            images.get(x).put("BACK OFF", new ImageIcon("src/assets/mainMenu/about/back.png").getImage());
            images.get(x).put("BACK ON", new ImageIcon("src/assets/mainMenu/about/backOn.png").getImage());
            objects.get(x).put("BACK BUTTON", new Object(images.get(x).get("BACK OFF"), x1, height / 2 + add, w, h));
            objects.get(x).put("BACK SIGN", new Object(images.get(x).get("BACK ON"), x1, height / 2 + 20 - h + add, w, h * 2));

            x = "MAIN MENU ABOUT0";
            images.put(x, new HashMap<>());
            objects.put(x, new HashMap<>());

            images.get(x).put("CREDITS", new ImageIcon("src/assets/mainMenu/about/credits.png").getImage());
            images.get(x).put("CREDITS TITLE", new ImageIcon("src/assets/mainMenu/about/about1.png").getImage());
            objects.get(x).put("CREDITS TITLE", new Object(images.get(x).get("CREDITS TITLE"), 0, 0, width, height));
            objects.get(x).put("CREDITS", new Object(images.get(x).get("CREDITS"), width/4, 800, w, 200));

            x = "MAIN MENU ABOUT1";
            images.put(x, new HashMap<>());
            objects.put(x, new HashMap<>());

            images.get(x).put("AWARDS", new ImageIcon("src/assets/mainMenu/about/awards.png").getImage());
            images.get(x).put("AWARDS TITLE", new ImageIcon("src/assets/mainMenu/about/about2.png").getImage());
            objects.get(x).put("AWARDS TITLE", new Object(images.get(x).get("AWARDS TITLE"), 0, 0, width, height));
            objects.get(x).put("AWARDS", new Object(images.get(x).get("AWARDS"), width/4, 800, w, 200));

            x = "MAIN MENU ABOUT2";
            images.put(x, new HashMap<>());
            objects.put(x, new HashMap<>());

            images.get(x).put("EXTRA", new ImageIcon("src/assets/mainMenu/about/extra.png").getImage());
            images.get(x).put("EXTRA TITLE", new ImageIcon("src/assets/mainMenu/about/about3.png").getImage());
            objects.get(x).put("EXTRA TITLE", new Object(images.get(x).get("EXTRA TITLE"), 0, 0, width, height));
            objects.get(x).put("EXTRA", new Object(images.get(x).get("EXTRA"), width/4, 800, w, 200));
        }

        x = "MAIN MENU SETTINGS";
        {
            images.put(x, new HashMap<>());
            objects.put(x, new HashMap<>());

            images.get(x).put("BACK OFF", new ImageIcon("src/assets/mainMenu/main/backOff.png").getImage());
            images.get(x).put("BACK ON", new ImageIcon("src/assets/mainMenu/main/backOn.png").getImage());
            objects.get(x).put("BACK BUTTON", new Object(images.get(x).get("BACK OFF"), x1, height / 2 + 220, w, h));
        }
    }

    public void game(int width, int height) {
        String x = "GAME";
        images.put(x, new HashMap<>());
        objects.put(x, new HashMap<>());

        images.get(x).put("DES", new ImageIcon("src/assets/game/main/destination.png").getImage());
        objects.get(x).put("DES", new Object(images.get(x).get("DES"), 0, 0, 100, 100));
        objects.get(x).get("DES").visible = false;

        images.get(x).put("PATH", new ImageIcon("src/assets/game/main/path.png").getImage());

        images.get(x).put("HEELS", new ImageIcon("src/assets/game/main/heels.png").getImage());
        objects.get(x).put("HEELS", new Heels(images.get(x).get("HEELS"), 0, 0, 50, 50));
        objects.get(x).get("HEELS").visible = false;

        x = "GAME PAUSE";
        images.put(x, new HashMap<>());
        objects.put(x, new HashMap<>());

        images.get(x).put("BACKGROUND", new ImageIcon("src/assets/game/main/pauseBG.png").getImage());
        objects.get(x).put("BACKGROUND", new Object(images.get(x).get("BACKGROUND"), 100, 100, width - 200, height - 200));
        objects.get(x).get("BACKGROUND").priorityZ = 0;

        images.get(x).put("CONTINUE OFF", new ImageIcon("src/assets/game/main/pauseContinue.png").getImage());
        images.get(x).put("CONTINUE ON", new ImageIcon("src/assets/game/main/pauseContinueOn.png").getImage());
        objects.get(x).put("CONTINUE BUTTON", new Object(images.get(x).get("CONTINUE OFF"), width/2, height/2 - 200, 300, 200));

        images.get(x).put("GIVE UP OFF", new ImageIcon("src/assets/game/main/pauseGiveUp.png").getImage());
        images.get(x).put("GIVE UP ON", new ImageIcon("src/assets/game/main/pauseGiveUpOn.png").getImage());
        objects.get(x).put("GIVE UP BUTTON", new Object(images.get(x).get("GIVE UP OFF"), width/2, height/2, 300, 200));

        images.get(x).put("RESTART OFF", new ImageIcon("src/assets/game/main/pauseRestart.png").getImage());
        images.get(x).put("RESTART ON", new ImageIcon("src/assets/game/main/pauseRestartOn.png").getImage());
        objects.get(x).put("RESTART BUTTON", new Object(images.get(x).get("RESTART OFF"), width/2, height/2 + 200, 300, 200));

        x = "GAME HUD";
        images.put(x, new HashMap<>());
        objects.put(x, new HashMap<>());

        imagesLinkedList.put("HEARTS", new LinkedList<>());
        for (int i = 0; i <= 10; i++) {
            imagesLinkedList.get("HEARTS").add(new ImageIcon("src/assets/game/objects/hearts/hearts " + i + ".png").getImage());
        }
        objects.get(x).put("HEARTS", new Object(imagesLinkedList.get("HEARTS").getFirst(), 0, 0, 1980, 1080));
    }

    public void gameOver(int width, int height) {
        String x = "GAME OVER";
        images.put(x, new HashMap<>());
        objects.put(x, new HashMap<>());

        images.get(x).put("BACK OFF", new ImageIcon("src/assets/game/gameOver/backOff.png").getImage());
        images.get(x).put("BACK ON", new ImageIcon("src/assets/game/gameOver/backOn.png").getImage());
        objects.get(x).put("BACK", new Object(images.get(x).get("BACK OFF"), width/2, (int) (height * .75), 300, 200));

        imagesLinkedList.put("ANIM", new LinkedList<>());
        for (int i = 2; i <= 59; i++) {
            imagesLinkedList.get("ANIM").add(new ImageIcon("src/assets/game/gameOver/anim/asdf (" + i + ").png").getImage());
        }
        objects.get(x).put("ANIM", new Animated(imagesLinkedList.get("ANIM"), 5, 0, 0, width, height));

        images.get(x).put("BACKGROUND", new ImageIcon("src/assets/game/gameOver/anim/asdf (58).png").getImage());
        objects.get(x).put("IMAGE", new Object(images.get(x).get("BACKGROUND"), 0, 0, width, height));

        imagesLinkedList.put("ANIM2", new LinkedList<>());
        for (int i = 57; i <= 59; i++) {
            imagesLinkedList.get("ANIM2").add(new ImageIcon("src/assets/game/gameOver/anim/asdf (" + i + ").png").getImage());
        }
        objects.get(x).put("ANIM2", new Animated(imagesLinkedList.get("ANIM2"), 3, 0, 0, width, height));
    }

    public void congratulations(int width, int height) {
        String x = "CONGRATULATIONS";
        images.put(x, new HashMap<>());
        objects.put(x, new HashMap<>());

        images.get(x).put("BACK OFF", new ImageIcon("src/assets/game/gameOver/backOff.png").getImage());
        images.get(x).put("BACK ON", new ImageIcon("src/assets/game/gameOver/backOn.png").getImage());
        objects.get(x).put("BACK", new Object(images.get(x).get("BACK OFF"), (int) (width * .85), (int) (height * .4), 200, 100));

        imagesLinkedList.put("ANIM", new LinkedList<>());
        for (int i = 1; i <= 55; i++) {
            imagesLinkedList.get("ANIM").add(new ImageIcon("src/assets/game/gameOver/congrats/frame (" + i + ").png").getImage());
        }
        objects.get(x).put("ANIM", new Animated(imagesLinkedList.get("ANIM"), 10, 0, 0, width, height));
        objects.get(x).get("ANIM").priorityZ = 0;

        images.get(x).put("BACKGROUND", new ImageIcon("src/assets/game/gameOver/congrats/frame (54).png").getImage());
        objects.get(x).put("IMAGE", new Object(images.get(x).get("BACKGROUND"), 0, 0, width, height));
    }

    public void gameObjects(int size) {
        String x = "GAME OBJECTS";
        images.put(x, new HashMap<>());
        objects.put(x, new HashMap<>());

        images.get(x).put("PLAYER", new ImageIcon("src/assets/game/objects/player.png").getImage());
        objects.get(x).put("PLAYER", new Player(images.get(x).get("PLAYER"), 0, 0, size, size));
        imagesLinkedList.put("PLAYER WALK", new LinkedList<>());
        for (int i = 1; i <= 8; i++) {
            imagesLinkedList.get("PLAYER WALK").add(new ImageIcon("src/assets/game/objects/player/frame_0000" + i + ".png").getImage());
        }

        images.get(x).put("FLOOR 1", new ImageIcon("src/assets/game/objects/floor.png").getImage());
        images.get(x).put("FLOOR 2", new ImageIcon("src/assets/game/objects/floor2.png").getImage());
        images.get(x).put("FLOOR 0", new ImageIcon("src/assets/game/objects/floor3.png").getImage());
        objects.get(x).put("FLOOR", new Floor(images.get(x).get("FLOOR 1"), 0, 0, size, size));

        images.get(x).put("WALL 1", new ImageIcon("src/assets/game/objects/wall.png").getImage());
        images.get(x).put("WALL 2", new ImageIcon("src/assets/game/objects/wall2.png").getImage());
        images.get(x).put("WALL 3", new ImageIcon("src/assets/game/objects/wall3.png").getImage());
        objects.get(x).put("WALL", new Wall(images.get(x).get("WALL 1"), 0, 0, size, size));

        images.get(x).put("EXIT", new ImageIcon("src/assets/game/objects/exit.png").getImage());
        objects.get(x).put("EXIT", new Exit(images.get(x).get("EXIT"), 0, 0, size, size));

        images.get(x).put("BLOCK ON", new ImageIcon("src/assets/game/objects/blockWall.png").getImage());
        images.get(x).put("BLOCK OFF", new ImageIcon("src/assets/game/objects/blockFloor.png").getImage());
        objects.get(x).put("BLOCK", new Block(images.get(x).get("BLOCK OFF"), 0, 0, size, size, null));
        Block b = (Block) objects.get(x).get("BLOCK");
        b.imageOn = images.get(x).get("BLOCK ON");

        images.get(x).put("SPIKE ON", new ImageIcon("src/assets/game/objects/spike.png").getImage());
        images.get(x).put("SPIKE OFF", new ImageIcon("src/assets/game/objects/spikeOff.png").getImage());
        objects.get(x).put("SPIKE", new Spike(images.get(x).get("SPIKE ON"), 0, 0, size, size, null));
        Spike a = (Spike) objects.get(x).get("SPIKE");
        a.imageOff = images.get(x).get("SPIKE OFF");

        images.get(x).put("SWITCH ON", new ImageIcon("src/assets/game/objects/switchOn.png").getImage());
        images.get(x).put("SWITCH OFF", new ImageIcon("src/assets/game/objects/switchOff.png").getImage());
        images.get(x).put("ROTATOR ON", new ImageIcon("src/assets/game/objects/rotatorOn.png").getImage());
        images.get(x).put("ROTATOR OFF", new ImageIcon("src/assets/game/objects/rotator.png").getImage());
        objects.get(x).put("SWITCH", new Switch(images.get(x).get("SWITCH OFF"), 0, 0, size, size));

        images.get(x).put("FOLLOW", new ImageIcon("src/assets/game/objects/follow.png").getImage());
        objects.get(x).put("FOLLOW", new Follow(images.get(x).get("FOLLOW"), 0, 0, size, size));
        imagesLinkedList.put("FOLLOW WALK", new LinkedList<>());
        for (int i = 1; i <= 6; i++) {
            imagesLinkedList.get("FOLLOW WALK").add(new ImageIcon("src/assets/game/objects/bat/frame_0000" + i + ".png").getImage());
        }

        images.get(x).put("BOUNCER OFF", new ImageIcon("src/assets/game/objects/bouncerOff.png").getImage());
        images.get(x).put("BOUNCER ON", new ImageIcon("src/assets/game/objects/bouncerOn.png").getImage());
        objects.get(x).put("BOUNCER", new Bouncer(images.get(x).get("BOUNCER OFF"), 0, 0, size, size));

        images.get(x).put("TURRET OFF", new ImageIcon("src/assets/game/objects/turretOff.png").getImage());
        images.get(x).put("TURRET ON", new ImageIcon("src/assets/game/objects/turretOn.png").getImage());
        images.get(x).put("TURRET RIGHT", new ImageIcon("src/assets/game/objects/turretRight.png").getImage());
        images.get(x).put("TURRET LEFT", new ImageIcon("src/assets/game/objects/turretLeft.png").getImage());
        images.get(x).put("TURRET UP", new ImageIcon("src/assets/game/objects/turretUp.png").getImage());
        images.get(x).put("TURRET DOWN", new ImageIcon("src/assets/game/objects/turretDown.png").getImage());
        objects.get(x).put("TURRET", new Turret(images.get(x).get("TURRET ON"), 0, 0, size, size, null));

        for (int i = 1; i <= 34; i++)
            images.get(x).put("DECORATION " + i, new ImageIcon("src/assets/game/objects/decorations/decoration " + i + ".png").getImage());
        objects.get(x).put("DECORATION", new Decoration(images.get(x).get("DECORATION 1"), 0, 0, size, size));
    }

    public LinkedList<Object> loadObjects(String fileName, MapSize mapSize, Game game) {
        LinkedList<Object> objects = new LinkedList<>();
        LinkedList<String> objectsList = new LinkedList<>(Save.load(fileName));

        for (String s : objectsList) {
            String[] arr = s.split(" ");
            switch (arr[0]) {
                case "Player" -> {
                    objects.addFirst(new Player(
                            images.get("GAME OBJECTS").get("PLAYER"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                    if (objects.getFirst() instanceof Player p) {
                        p.walkAnim = new Animated(imagesLinkedList.get("PLAYER WALK"), 3, 0, 0, Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
                    }
                }
                case "Floor" -> {
                    objects.add(new Floor(
                            images.get("GAME OBJECTS").get("FLOOR " + Integer.parseInt(arr[6])),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                    objects.getLast().rotation = Float.parseFloat(arr[5]);
                }
                case "Wall" -> {
                    objects.add(new Wall(
                            images.get("GAME OBJECTS").get("WALL " + Integer.parseInt(arr[5])),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                    if (objects.getLast() instanceof Wall w) {
                        w.setID(Integer.parseInt(arr[5]));
                        w.walls.add(images.get("GAME OBJECTS").get("WALL 1"));
                        w.walls.add(images.get("GAME OBJECTS").get("WALL 2"));
                        w.walls.add(images.get("GAME OBJECTS").get("WALL 3"));
                    }
                }
                case "Size" -> {
                    mapSize.setSizes(
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    );
                }
                case "Exit" -> {
                    objects.add(new Exit(
                            images.get("GAME OBJECTS").get("EXIT"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                }
                case "Block" -> {
                    objects.add(new Block(
                            images.get("GAME OBJECTS").get("BLOCK OFF"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4]),
                            game
                    ));
                    Block b = (Block) objects.getLast();
                    b.imageOn = images.get("GAME OBJECTS").get("BLOCK ON");
                    b.id = Integer.parseInt(arr[5]);
                    b.solid = Objects.equals(arr[6], "true");
                }
                case "Spike" -> {
                    objects.add(new Spike(
                            images.get("GAME OBJECTS").get("SPIKE ON"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4]),
                            game
                    ));
                    objects.getLast().id = Integer.parseInt(arr[5]);
                    Spike a = (Spike) objects.getLast();
                    a.imageOff = images.get("GAME OBJECTS").get("SPIKE OFF");
                    a.on = Objects.equals(arr[6], "true");
                    a.reverse = Objects.equals(arr[7], "true");
                }
                case "Switch" -> {
                    objects.add(new Switch(
                            images.get("GAME OBJECTS").get((Objects.equals(arr[6], "true") ? "ROTATOR" : "SWITCH") + " OFF"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                    objects.getLast().id = Integer.parseInt(arr[5]);
                    if (objects.getLast() instanceof Switch sw) {
                        sw.rotator = Objects.equals(arr[6], "true");
                        sw.imageOn = images.get("GAME OBJECTS").get((sw.rotator ? "ROTATOR" : "SWITCH") + " ON");
                        String[] idSet = arr[5].split(",");
                        for (String ss : idSet) sw.idSet.add(Integer.parseInt(ss));
                    }
                }
                case "Follow" -> {
                    objects.add(new Follow(
                            images.get("GAME OBJECTS").get("FOLLOW"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                    if (objects.getLast() instanceof Follow p) {
                        p.walkAnim = new Animated(imagesLinkedList.get("FOLLOW WALK"), 3, 0, 0, Integer.parseInt(arr[3]), Integer.parseInt(arr[4]));
                    }
                }
                case "Bouncer" -> {
                    objects.add(new Bouncer(
                            images.get("GAME OBJECTS").get("BOUNCER OFF"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                    if (objects.getLast() instanceof Bouncer b) {
                        b.id = Integer.parseInt(arr[5]);
                        b.angle = Double.parseDouble(arr[6]);
                        b.imageOn = images.get("GAME OBJECTS").get("BOUNCER ON");
                    }
                }
                case "Turret" -> {
                    objects.add(new Turret(
                            images.get("GAME OBJECTS").get("TURRET ON"),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4]),
                            game
                    ));
                    if (objects.getLast() instanceof Turret t) {
                        t.off = images.get("GAME OBJECTS").get("TURRET OFF");
                        t.id = Integer.parseInt(arr[5]);
                        t.setDetector(Objects.equals(arr[6], "true"));
                        t.direction = Integer.parseInt(arr[7]);
                        t.directions.put("RIGHT", images.get("GAME OBJECTS").get("TURRET RIGHT"));
                        t.directions.put("LEFT", images.get("GAME OBJECTS").get("TURRET LEFT"));
                        t.directions.put("UP", images.get("GAME OBJECTS").get("TURRET UP"));
                        t.directions.put("DOWN", images.get("GAME OBJECTS").get("TURRET DOWN"));
                    }
                }
                case "Decoration" -> {
                    objects.add(new Decoration(
                            images.get("GAME OBJECTS").get("DECORATION " + Integer.parseInt(arr[5])),
                            Integer.parseInt(arr[1]),
                            Integer.parseInt(arr[2]),
                            Integer.parseInt(arr[3]),
                            Integer.parseInt(arr[4])
                    ));
                    objects.getLast().id = Integer.parseInt(arr[5]);
                }
            }
        }

        return objects;
    }

    int[] ratio(int w, int h, int width, int height) {
        float aspectRatio = (float) w / h;
        int newWidth, newHeight;

        if (width / aspectRatio >= height) {
            newWidth = width;
            newHeight = (int) (width / aspectRatio);
        } else {
            newHeight = height;
            newWidth = (int) (height * aspectRatio);
        }

        return new int[]{newWidth, newHeight};
    }
}
