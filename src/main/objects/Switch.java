package main.objects;

import main.main.Game;
import main.mapMaker.MapMaker;

import java.awt.*;
import java.util.LinkedList;

public class Switch extends Object {
    public Image imageOff;
    public Image imageOn;
    boolean on;
    public LinkedList<Object> outputs;
    public LinkedList<Integer> idSet;
    public boolean rotator;

    Switch(Image image, int x, int y, int w, int h) {
        super(image, x, y, w, h);
        imageOff = image;
        id = -1;
        on = false;
        rotator = false;

        outputs = new LinkedList<>();
        idSet = new LinkedList<>();
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (!visible) return;

        if (Game.debug || MapMaker.debug) {
            gg.setStroke(new BasicStroke(5));
            gg.setColor(new Color(0xC000FF));
            gg.drawRect(x + camX, y + camY, w, h);
        }

        gg.drawImage(on ? imageOn : imageOff, x + camX, y + camY, w, h, null);

        if (Game.debug) {
            gg.setColor(new Color(0xC000FF));
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString(String.valueOf(id), x + camX, y + camY + 100);
        }
    }

    public void toggle() {
        on = !on;
            for (Object o : outputs) {
                if (!rotator) {
                    if (o instanceof Block a) {
                        a.toggle();
                    }
                    if (o instanceof Spike a) {
                        a.toggle();
                    }
                    if (o instanceof Turret a) {
                        a.toggle();
                    }
                } else {
                    if (o instanceof Bouncer a) {
                        a.angle += Math.toRadians(45);
                    }
                    if (o instanceof Turret a) {
                        a.rotate();
                    }
                }
            }
    }
}
