package main.objects;

import main.main.Game;
import main.main.HUD;
import main.utility.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Player extends Object {
    Image idle;
    public Animated walkAnim;
    public Object des;
    int speed;
    public List<Node> path;
    public LinkedList<Object> exits;
    public LinkedList<Object> hazards;
    public LinkedList<Follow> followers;
    public Image pathImage;
    int pathIndex;
    public Game game;
    HUD hud;
    int xVel, yVel;
    int delayFlip;

    public Player(Image image, int x, int y, int w, int h) {
        super(image, x, y, w, h);
        this.idle = image;

        exits = new LinkedList<>();
        hazards = new LinkedList<>();

        speed = 5;
    }

    public void setHud(HUD hud) {
        this.hud = hud;
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (walkAnim != null) {
            if (path != null) {
                image = walkAnim.image;
                if (!pause) walkAnim.loop();
            } else {
                walkAnim.current = 0;
                image = walkAnim.images.getFirst();
            }
        }

        if (followers != null) {
            for (Follow o : followers)
                if (Collision.dist(x - w / 2, y - h / 2, o.x - o.w / 2, o.y - o.h / 2) < (double) w/2) {
                    game.dead();
                    break;
                }
            for (Object o : hazards) if (o instanceof Turret a && a.detector && a.checkDead()) {
                game.dead();
                break;
            }
        }

        if (path != null && path.size() > 2) {
            if (!pause) move();
            try {
                if (Game.debug) for (Node n : path) gg.drawImage(pathImage, n.x + camX, n.y + camY, 10, 10, null);
            } catch (Exception e) {
//                throw new RuntimeException(e);
            }
        }
        else path = null;

        if (Game.debug) {
            gg.setStroke(new BasicStroke(5));
            gg.setColor(new Color(0xC000FF));
            gg.drawRect(x - w / 2 + camX, y + camY - h / 2, w, h / 2);
        }

        AffineTransform oldTransform = gg.getTransform();
        if (xVel != 0 && xVel / Math.abs(xVel) < 0) {
            gg.translate(x + camX - w/2 + w, y + camY - h);
            gg.scale(-1, 1);
        } else {
            gg.translate(x + camX - w/2, y + camY - h);
        }

        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        gg.drawImage(image, 0, 0, w, h, null);
        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

        gg.setTransform(oldTransform);
    }

    void move() {
        double s = ((double) w / 100 * speed);
        int xTo = path.get(pathIndex).x;
        int yTo = path.get(pathIndex).y;
        double angle = Math.atan2(yTo - y, xTo - x);
        int distanceToDestination = (int)Math.sqrt((xTo - x) * (xTo - x) + (yTo - y) * (yTo - y));
        xVel = (int)(s * Math.cos(angle));
        yVel = (int)(s * Math.sin(angle));

        if (distanceToDestination <= s) {
            gotoxy(xTo, yTo);
            if (!path.isEmpty()) path.removeFirst();//pathIndex++;

            boolean hazardCheck = false;
            for (Object o : hazards) if (Collision.dist(x - w/2, y - h/2, o.x, o.y) < (double) w/2) {
                if (o instanceof Spike a && a.checkDead()) {
                    hazardCheck = true;
                    break;
                }
            }
            int off = 50;
            for (Object o : hazards) if (o instanceof Follow && Collision.rectRect(
                    o.x - o.w / 2 + off, o.y - o.h / 2 + off, o.w / 2 - off, o.h / 2 - off,
                    x - w / 2 + off, y - h / 2 + off, w /2 - off, h / 2 - off)) {
                hazardCheck = true;
                break;
            }
            if (hazardCheck) game.dead();

            boolean exitCheck = false;
            for (Object o : exits) if (Collision.dist(x - w/2, y - h/2, o.x, o.y) < (double) w/3) {
                exitCheck = true;
                break;
            }
            if (exitCheck) game.nextFloor();
        } else {
            gotoxy(x + xVel, y + yVel);
        }
    }

    public void findPath(LinkedList<Object> solids, MapSize mapSize, int targetX, int targetY) {
        pathIndex = 1;

        int gridX = -1, gridY = -1;
        int targetGridX = -1, targetGridY = -1;
        boolean targetCheck = false;
        boolean playerCheck = false;
        int multiplier = 10;
        int mapW = (mapSize.x2 - mapSize.x1) / multiplier;
        int mapH = (mapSize.y2 - mapSize.y1) / multiplier;
        int[][] grid = new int[mapH + 1][mapW + 1];
        for (int i = 0; i <= mapH; i++) {
            for (int j = 0; j <= mapW; j++) {
                grid[i][j] = 0;
                float rx = j * multiplier + mapSize.x1 + (float) multiplier / 2;
                float ry = i * multiplier + mapSize.y1 + (float) multiplier / 2;
                if (!playerCheck &&
                        Collision.pointRect(x, y, rx, ry,
                        multiplier, multiplier))
                {
                    gridX = j;
                    gridY = i;
                    playerCheck = true;
                }
                if (!targetCheck &&
                        Collision.pointRect(targetX, targetY, rx, ry,
                        multiplier, multiplier))
                {
                    targetGridX = j;
                    targetGridY = i;
                    targetCheck = true;
                }
                for (Object o : solids) if (o.solid && Collision.rectRect(
                        rx, ry, multiplier, multiplier,
                        o.x, o.y, o.w, o.h)) {
                    grid[i][j] = 1;
                    break;
                }
            }
        }
//        System.out.println();
//        for (int[] a : grid) {
//            for (int b : a) System.out.print(b + "");
//            System.out.println();
//        }

//        System.out.println(x + " " + y + " " + targetX + " " + targetY);
//        System.out.println(playerCheck + " " + gridX + " " + gridY + " " + targetCheck + " " + targetGridX + " " + targetGridY);
        path = null;
        if (playerCheck && targetCheck) path = AStar.findPath(grid, gridX, gridY, targetGridX, targetGridY, 0);
        if (path == null || path.size() < 2) return;
//        System.out.println("Path:");
//        for (Node n : path) System.out.println("\t" + n.x + " " + n.y);

        for (Node n : path) {
            n.x = (int) (n.x * multiplier + (float) multiplier/2 + mapSize.x1);
            n.y = (int) (n.y * multiplier + (float) multiplier/2 + mapSize.y1);
//            System.out.println("\t" + n.x + " " + n.y);
        }

        List<Node> smooth = new ArrayList<>();
        smooth.add(new Node(x, y));
        int from = 0;
        int to = 1;
        int loopChecker = 0;
        while (to < path.size()) {
//            System.out.println(loopChecker++);
            for (Object o : solids) {
                if (o.solid && Collision.lineRectWidth(path.get(from).x, path.get(from).y, path.get(to).x, path.get(to).y, o.x, o.y, o.w, o.h, 10)) {
                    smooth.add(path.get(to - 1));
                    smooth.add(path.get(to));
                    from = to;
                    break;
                }
            }
            to++;
        }
        smooth.add(new Node(targetX, targetY));
//        System.out.println("Smooth:");
//        for (Node n : smooth) System.out.println("\t" + n.x + " " + n.y);

        path = smooth;

        List<Node> line = new ArrayList<>();

        for (int i = 0; i < smooth.size() - 1; i++) {
            Node current = smooth.get(i);
            Node next = smooth.get(i + 1);

            line.add(current);

            double distance = Math.sqrt(Math.pow(next.x - current.x, 2) + Math.pow(next.y - current.y, 2));
            int numberOfImages = (int) (distance / 10);

            for (int j = 1; j <= numberOfImages; j++) {
                double ratio = (double) j / (double) (numberOfImages + 1);
                int x = (int) (current.x + ratio * (next.x - current.x));
                int y = (int) (current.y + ratio * (next.y - current.y));
                line.add(new Node(x, y));
            }
        }
        line.add(smooth.get(smooth.size() - 1));

        path = line;
    }
}
