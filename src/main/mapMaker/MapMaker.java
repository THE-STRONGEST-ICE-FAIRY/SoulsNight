package main.mapMaker;

import main.objects.*;
import main.objects.Object;
import main.utility.*;
import main.utility.Cursor;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class MapMaker extends JPanel implements Runnable {
    int width, height;
    Thread thread;
    Camera camera;
    Cursor cursor;
    Key key;
    Assets assets;
    Object center;
    Object hold;
    LinkedList<Object> objects;
    LinkedList<Object> objectsOrdered;
    HashMap<String, HashMap<String, Image>> images;
    LinkedList<String> save;
    String fileName;
    int current;
    LinkedList<Object> gameObjects;
    MapSize mapSize;
    int size;
    boolean grid;
    public static boolean debug;

    MapMaker(int width, int height, Frame frame) {
        debug = true;

        this.width = width;
        this.height = height;

        thread = new Thread(this);
        thread.start();

        camera = new Camera(width/2, height/2, this);
        cursor = new Cursor(this);
        key = new Key(frame);
        assets = new Assets();
        size = 100;
        assets.gameObjects(size);
        center = new Object(new ImageIcon("src/assets/my beloved.png").getImage(), -25, -25, 50, 50);
        objects = new LinkedList<>();
        images = new HashMap<>(assets.images);
        fileName = "src/assets/floors/floor40.txt";
        save = new LinkedList<>(Save.load(fileName));
        mapSize = new MapSize();
        size = 100;
        grid = true;
        objects = assets.loadObjects(fileName, mapSize, null);
        objectsOrdered = new LinkedList<>(objects);
        objectsOrdered.sort(Comparator.comparingDouble(Object::getPriorityZ).thenComparing(Object::getY));
        current = 0;
        gameObjects = new LinkedList<>();
        for (HashMap.Entry<String, Object> entry2 : assets.objects.get("GAME OBJECTS").entrySet()) gameObjects.add(entry2.getValue());
        hold = gameObjects.get(current++);

        new MapMakerDebug(this);
    }

    public void run() {
        double drawInterval = (double) 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread != null) {
            repaint();

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        gg.setColor(new Color(0x0000FF));
        gg.setStroke(new BasicStroke(5));
        gg.drawRect(mapSize.x1 + camera.getX(), mapSize.y1 + camera.getY(), mapSize.x2 - mapSize.x1, mapSize.y2 - mapSize.y1);

        for (Object o : objectsOrdered) o.draw(gg, camera.getX(), camera.getY(), false);
        center.draw(gg, camera.getX(), camera.getY(), false);
        hold.draw(gg, 0, 0, false);
        if (grid) hold.gotoxy(camera.getX() % 50 + snap(cursor.getX() - hold.w/2, 50), camera.getY() % 50 + snap(cursor.getY() - hold.h/2, 50));
        else hold.gotoxy(cursor.getX() - hold.w/2, cursor.getY() - hold.h/2);

        keys();

        gg.dispose();
    }

    void keys() {
        if (cursor.leftClick) {
            objects.add(hold.clone());
            objects.getLast().gotoxy(objects.getLast().x - camera.getX(), objects.getLast().y - camera.getY());
            if (objects.getLast() instanceof Wall w) w.setID(hold.id);
            mapSize.setSizes(objects.getLast());
            objectsOrdered.clear();
            objectsOrdered.addAll(objects);
            objectsOrdered.sort(Comparator.comparingDouble(Object::getPriorityZ).thenComparing(Object::getY));

            if (!save.isEmpty()) save.removeLast();
            String input = hold.getClass().getSimpleName() + " " + hold.data(camera, hold.rotationAllowed) + " ";
            if (hold instanceof Switch || hold instanceof Block || hold instanceof Spike || hold instanceof Turret || hold instanceof Decoration || hold instanceof Wall || hold instanceof Bouncer)
                input += hold.id + " ";
            if (hold instanceof Floor) input += (hold.id % 3);
            if (hold instanceof Block) input += hold.solid;
            if (hold instanceof Spike s) input += s.on + " " + s.reverse;
            if (hold instanceof Switch s) input += s.rotator;
            if (hold instanceof Bouncer b) input += b.angle;
            if (hold instanceof Turret t) input += t.detector + " " + t.direction;
            save.add(input);
            save.add("Size " + mapSize.data());

            cursor.leftClick = false;
        }

        if (key.enter && !key.enterL) {
            Save.save(save, fileName);
            key.enterL = true;
        } else if (!key.enter) key.enterL = false;

        if (cursor.rightPress) {
            camera.dragScreen(cursor, cursor.getX(), cursor.getY());
        }
        else camera.dragScreenLock = false;

        if (key.space && !key.spaceL) {
            current++;
            if (current == gameObjects.size()) current = 0;
            hold = gameObjects.get(current);
            key.spaceL = true;
        } else if (!key.space) key.spaceL = false;

        if (key.z && !key.zL) {
            current--;
            if (current == -1) current = gameObjects.size() - 1;
            hold = gameObjects.get(current);
            key.zL = true;
        } else if (!key.z) key.zL = false;

        if (key.right && !key.rightL) {
            hold.rotation++;
//            key.rightL = true;
        } //else if (!key.right) key.rightL = false;

        if (key.left && !key.leftL) {
            hold.rotation--;
//            key.leftL = true;
        } //else if (!key.left) key.leftL = false;

        if (key.up && !key.upL) {
            hold.w += 10;
            hold.h += 10;
//            key.upL = true;
        } //else if (!key.up) key.upL = false;

        if (key.down && !key.downL) {
            hold.w -= 10;
            hold.h -= 10;
//            key.downL = true;
        } //else if (!key.down) key.downL = false;

        if (hold.getClass() != Player.class) {
            if (key.w/* && !key.wL*/) {
                hold.h += 10;
                key.wL = true;
            } //else if (!key.w) key.wL = false;

            if (key.s/* && !key.sL*/) {
                hold.h -= 10;
                key.sL = true;
            } //else if (!key.s) key.sL = false;

            if (key.d/* && !key.dL*/) {
                hold.w += 10;
                key.dL = true;
            } //else if (!key.d) key.dL = false;

            if (key.a/* && !key.aL*/) {
                hold.w -= 10;
                key.aL = true;
            } //else if (!key.a) key.aL = false;
        }

        if (key.del && !key.delL) {
            hold.w = size;
            hold.h = size;
            hold.rotation = 0;
            key.delL = true;
        } else if (!key.del) key.delL = false;

        if (key.i && !key.iL) {
            Cursor.gotoXY(cursor.getX(), cursor.getY() - 1);
            key.iL = true;
        } else if (!key.i) key.iL = false;

        if (key.j && !key.jL) {
            Cursor.gotoXY(cursor.getX() - 1, cursor.getY()); // Move left
            key.jL = true;
        } else if (!key.j) key.jL = false;

        if (key.k && !key.kL) {
            Cursor.gotoXY(cursor.getX(), cursor.getY() + 1); // Move down
            key.kL = true;
        } else if (!key.k) key.kL = false;

        if (key.l && !key.lL) {
            Cursor.gotoXY(cursor.getX() + 1, cursor.getY()); // Move up
            key.lL = true;
        } else if (!key.l) key.lL = false;

        if (key.equals && !key.equalsL) {
            hold.id++;
            if (hold instanceof Decoration d) d.image = images.get("GAME OBJECTS").get("DECORATION " + d.id);
            if (hold instanceof Wall d) d.image = images.get("GAME OBJECTS").get("WALL " + d.id);
            if (hold instanceof Floor f) f.image = images.get("GAME OBJECTS").get("FLOOR " + (f.id % 3));
            key.equalsL = true;
        } else if (!key.equals) key.equalsL = false;

        if (key.minus && !key.minusL) {
            hold.id--;
            if (hold instanceof Decoration d) d.image = images.get("GAME OBJECTS").get("DECORATION " + d.id);
            if (hold instanceof Wall d) d.image = images.get("GAME OBJECTS").get("WALL " + d.id);
            key.minusL = true;
        } else if (!key.minus) key.minusL = false;

        if (key.k0 && !key.k0L) {
            if (hold instanceof Bouncer b) b.angle += Math.toRadians(45);
            if (hold instanceof Switch b) {
                b.rotator = !b.rotator;
                if (b.rotator) {
                    b.imageOff = images.get("GAME OBJECTS").get("ROTATOR OFF");
                    b.imageOn = images.get("GAME OBJECTS").get("ROTATOR ON");
                } else {
                    b.imageOff = images.get("GAME OBJECTS").get("SWITCH OFF");
                    b.imageOn = images.get("GAME OBJECTS").get("SWITCH ON");
                }
            }
            if (hold instanceof Block) {
                hold.solid = !hold.solid;
            }
            if (hold instanceof Spike s) {
                s.on = !s.on;
            }
            if (hold instanceof Turret t) t.on = !t.on;
            key.k0L = true;
        } else if (!key.k0) key.k0L = false;

        if (key.k9 && !key.k9L) {
            if (hold instanceof Bouncer b) b.angle -= Math.toRadians(45);
            if (hold instanceof Spike s) {
                s.reverse = !s.reverse;
            }
            if (hold instanceof Turret t) t.setDetector(!t.detector);
            key.k9L = true;
        } else if (!key.k9) key.k9L = false;

        if (key.k8 && !key.k8L) {
            if (hold instanceof Turret t) {
                t.direction = t.direction + 1;
                t.direction = t.direction % 4;
            }
            key.k8L = true;
        } else if (!key.k8) key.k8L = false;

        if (key.k7 && !key.k7L) {
            key.k7L = true;
        } else if (!key.k7) key.k7L = false;

        if (key.k1 && !key.k1L) {
            grid = !grid;
            key.k1L = true;
        } else if (!key.k1) key.k1L = false;

        if (key.q && !key.qL) {
            if (!save.isEmpty()) save.remove(save.size() - 2);
            objects.removeLast();
            objectsOrdered.clear();
            objectsOrdered.addAll(objects);
            objectsOrdered.sort(Comparator.comparingDouble(Object::getPriorityZ).thenComparing(Object::getY));
            key.qL = true;
        } else if (!key.q) key.qL = false;
    }

    public static int snap(int value, int n) {
        if (n == 0) {
            throw new IllegalArgumentException("You can't divide by zero, Idiot.");
        }

        int lowerMultiple = (value / n) * n;
        int higherMultiple = lowerMultiple + (value < 0 ? -n : n);

        int lowerDiff = Math.abs(value - lowerMultiple);
        int higherDiff = Math.abs(value - higherMultiple);

        return lowerDiff < higherDiff ? lowerMultiple : higherMultiple;
    }
}
