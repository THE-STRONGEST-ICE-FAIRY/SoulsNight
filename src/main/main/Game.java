package main.main;

import main.mapMaker.MapMaker;
import main.objects.*;
import main.objects.Object;
import main.utility.*;
import main.utility.Cursor;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Game {
    public static boolean debug;
    boolean visible;
    Assets assets;
    Cursor cursor;
    Camera camera;
    Key key;
    public HashMap<String, Object> objects;
    HashMap<String, Image> images;
    LinkedList<Object> objectsOrdered;
    LinkedList<Object> objectsSolid;
    LinkedList<Block> blocks;
    MapSize mapSize;
    public Player player;
    public boolean pause, dead;
    int floors, current;
    HUD hud;
    Pause pausePanel;
    GameOver gameOver;
    LinkedList<Follow> followers;
    public int width;
    public int height;
    Congratulations congratulations;

    Game(int width, int height, Assets assets, Cursor cursor, Camera camera, Key key) {
        debug = false;
        visible = false;
        pause = false;
        dead = false;
        this.width = width;
        this.height = height;
        this.assets = assets;
        this.cursor = cursor;
        this.camera = camera;
        this.key = key;
        assets.gameObjects(100);
        assets.game(width, height);
        hud = new HUD(width, height, assets);
        pausePanel = new Pause(this);
        mapSize = new MapSize();
        images = assets.images.get("GAME");
        objects = assets.objects.get("GAME");
        floors = 41;
        current = 1;
        followers = new LinkedList<>();
    }

    public void setGameOver(GameOver gameOver) {
        this.gameOver = gameOver;
    }

    public void setCongratulations(Congratulations congratulations) {
        this.congratulations = congratulations;
    }

    public void start() {
        assets.sound.stop("MENU");
        assets.sound.playOnLoop("GAME1");
        assets.sound.playOnLoop("GAME2");
        hud.seconds = 0;
        visible = true;
        loadFloor();
    }

    public void draw(Graphics2D gg) {
        if (!visible) return;

        script();

        for (Follow f : followers) f.loop(objectsSolid, mapSize, player.x, player.y);
        orderObjects();

        if (Game.debug || MapMaker.debug) {
            gg.setColor(new Color(0x0000FF));
            gg.setStroke(new BasicStroke(5));
            gg.drawRect(mapSize.x1 + camera.getX(), mapSize.y1 + camera.getY(), mapSize.x2 - mapSize.x1, mapSize.y2 - mapSize.y1);
        }

        gg.setColor(new Color(0x04042C));
        gg.setStroke(new BasicStroke(5));
        gg.fillRect(0, 0, width, height);

        if (objectsOrdered != null && player != null) for (Object o : objectsOrdered) o.draw(gg, camera.getX(), camera.getY(), pause);
        for (HashMap.Entry<String, Object> entry : objects.entrySet()) entry.getValue().draw(gg, camera.getX(), camera.getY(), pause);

        hud.draw(gg);

        if (pause && !dead) pausePanel.draw(gg);
    }

    void script() {
        if (player != null && player.path != null) objects.get("DES").visible = player.path.size() > 2;
        else objects.get("DES").visible = false;

        if (cursor.click) click();

        if (cursor.rightPress && !pause) {
            camera.dragScreen(cursor, cursor.getX(), cursor.getY());
//            camera.setXY(Math.clamp(camera.getX(), mapSize.x1 + width/2, mapSize.x2 + width/2), Math.clamp(camera.getY(), mapSize.y1 + height/2, mapSize.y2 + height/2));
        }
        else camera.dragScreenLock = false;

        if (key.esc && !key.escL) {
            pause = !pause;
            key.escL = true;
        } else if (!key.esc) key.escL = false;

        if (key.space && !key.spaceL && !pause) {
            Heels heels = (Heels) objects.get("HEELS");
            heels.shoot(cursor.getX() - camera.getX(), cursor.getY() - camera.getY());
            key.spaceL = true;
        } else if (!key.space) key.spaceL = false;

        if (key.equals && !key.equalsL) {
            loadFloor();
            key.equalsL = true;
        } else if (!key.equals) key.equalsL = false;

        if (key.minus && !key.minusL) {
            current -= 2;
            loadFloor();
            key.minusL = true;
        } else if (!key.minus) key.minusL = false;
    }

    void click() {
        if (!pause) {
            if (cursor.leftClick) {
                if (player != null) {
                    objects.get("DES").gotoxy(cursor.getX() - camera.getX() - objects.get("DES").w / 2, cursor.getY() - camera.getY() - objects.get("DES").h / 2);
                    player.findPath(objectsSolid, mapSize, objects.get("DES").x + objects.get("DES").w / 2, objects.get("DES").y + objects.get("DES").h / 2);
                }
            }

            if (cursor.rightClick) {
                if (player != null) {
                    player.path = null;
                }
            }

            if (cursor.middleClick) {
                if (player != null) camera.setXY(-player.x + width/2, -player.y + height/2);
            }

            cursor.leftClick = false;
            cursor.rightClick = false;
            cursor.middleClick = false;
            cursor.click = false;
        }
    }

    void loadFloor() {
        if (current == floors) {
            assets.sound.stop("GAME1");
            assets.sound.stop("GAME2");
            assets.sound.playOnLoop("WIN");
            congratulations.end(hud.seconds);
            congratulations.anim.play();
            current = 1;
            hud.reset();
            visible = false;
            return;
        }

        objectsOrdered = new LinkedList<>(assets.loadObjects("src/assets/floors/floor" + current++ + ".txt", mapSize, this));

        for (Object o : objectsOrdered) if (o.getClass() == Player.class) player = (Player) o;
        player.des = objects.get("DES");
        player.pathImage = assets.images.get("GAME").get("PATH");
        player.game = this;
        player.setHud(hud);

        camera.setXY(-player.x + width/2, -player.y + height/2);

        objectsOrdered.sort(Comparator.comparingDouble(Object::getPriorityZ));
        objectsSolid = new LinkedList<>();
        for (Object o : objectsOrdered) if (o.solidAble) {
            if (o instanceof Wall t && t.id != 1) continue;
            if (o instanceof Turret t && t.detector) continue;
            objectsSolid.add(o);
        }

        player.exits.clear();
        for (Object o : objectsOrdered) if (o.getClass() == Exit.class) player.exits.add(o);

        player.hazards.clear();
        for (Object o : objectsOrdered) if (o instanceof Spike) player.hazards.add(o);
        for (Object o : objectsOrdered) if (o instanceof Turret t && t.detector) {
            player.hazards.add(o);
            for (Object oo : objectsOrdered) if (oo instanceof Turret tt && oo.id == t.id && oo.id > -1 && !tt.detector) t.outputs.add((Turret) oo);
        }

        blocks = new LinkedList<>();
        for (Object o : objectsSolid) if (o.getClass() == Block.class) blocks.add((Block) o);

        Heels heels = (Heels) objects.get("HEELS");
        heels.stops.clear();
        heels.setStops(objectsSolid);
        for (Object o : objectsOrdered) if (o instanceof Bouncer b) heels.stops.add(b);
        for (Object o : objectsOrdered) if (o instanceof Switch s) {
            heels.stops.add(o);
            for (Object oo : objectsOrdered) if (s.idSet.contains(oo.id) && oo.id > -1) s.outputs.add(oo);
        }
        heels.player = player;
        heels.gotoxy(player.x, player.y);

        followers.clear();
        for (Object o : objectsOrdered) if (o instanceof Follow f) {
            f.pathImage = assets.images.get("GAME").get("PATH");
            followers.add(f);
        }
        player.followers = new LinkedList<>(followers);

        pause = false;
        dead = false;
    }

    void orderObjects() {
        objectsOrdered.sort(Comparator.comparingDouble(Object::getPriorityZ).thenComparing(Object::getY));
    }

    public void nextFloor() {
        objectsOrdered.clear();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        loadFloor();
    }

    public void dead() {
        dead = true;
        pause = true;

        Heels h = (Heels) objects.get("HEELS");
        h.resetTimer();
        hud.hearts--;

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        if (hud.hearts > 0) {
            current--;
            loadFloor();
        } else {
            assets.sound.stop("GAME1");
            assets.sound.stop("GAME2");
            assets.sound.playOnLoop("DEATH");
            gameOver.anim.play();
            gameOver.visible = true;
            current = 1;
            hud.reset();
            visible = false;
        }
    }
}
