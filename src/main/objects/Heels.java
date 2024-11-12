package main.objects;

import main.main.Game;
import main.utility.Collision;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Heels extends Object {
    public Player player;
    Timer timer;
    int time, max;
    double angle;
    int speed;
    boolean back;
    public LinkedList<Object> stops;

    Heels(Image image, int x, int y, int w, int h) {
        super(image, x, y, w, h);
        time = 0;
        max = 10;
        back = false;

        stops = new LinkedList<>();
        speed = 25;

        timer = new Timer(16, A -> {
            time++;
            if (time == max) {
                resetTimer();
            }
        });
    }

    public void resetTimer() {
        if (!timer.isRunning()) return;

        back = true;
        time = 0;
        timer.stop();
    }

    public void setStops(LinkedList<Object> stops) {
        this.stops = stops;
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (!visible) {
            gotoxy(camX + player.game.width, camY + player.game.height);
            return;
        }

        if (!pause) move();

        if (Game.debug) {
            gg.setStroke(new BasicStroke(5));
            gg.setColor(new Color(0xC000FF));
            gg.drawRect(x + camX, y + camY, w, h);
        }

        gg.drawImage(image, x + camX, y + camY, w, h, null);
    }

    void move() {
        int xVel;
        int yVel;

        if (!back) {
            xVel = (int) (Math.cos(angle) * speed);
            yVel = (int) (Math.sin(angle) * speed);

            for (Object o : stops) if (Collision.rectRect(o.x, o.y, o.w, o.h, x, y, w, h)) {
                if (o instanceof Bouncer b) {
                    if (Collision.dist(o.x + o.w/2, o.y + o.h/2, x + w/2, y + h/2) <= w/2) {
                        time = 0;
                        angle = b.angle;
                        b.onDelay = 25;
                    }
                } else {
                    if (o instanceof Switch s) s.toggle();
                    if (o instanceof Turret t) {
                        if (t.detector) continue;
                        t.showSignDelay = 25;
                    }
                    if (o instanceof Block b && !b.solid) continue;
                    back = true;
                    time = 0;
                    timer.stop();
                    break;
                }
            }
        } else {
            xVel = (int) (Math.cos(Math.atan2(player.y - y - player.h + h, player.x - x)) * speed);
            yVel = (int) (Math.sin(Math.atan2(player.y - y - player.h + h, player.x - x)) * speed);

            if (Collision.dist(x, y, player.x, player.y - player.h + h) <= speed) {
                back = false;
                visible = false;
            }
        }

        gotoxy(x + xVel, y + yVel);
    }

    public void shoot(int x, int y) {
        if (timer.isRunning() || back) return;
        gotoxy(player.x, player.y - player.h + h);
        angle = Math.atan2(y - player.y, x - player.x);
        visible = true;
        timer.start();
    }
}
