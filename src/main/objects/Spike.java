package main.objects;

import main.main.Game;
import main.mapMaker.MapMaker;
import main.utility.Collision;

import java.awt.*;

public class Spike extends Object {
    public boolean reverse;
    public boolean on;
    Image imageOn, imageOff;
    Game game;
    Heels heels;

    Spike(Image image, int x, int y, int w, int h, Game game) {
        super(image, x, y, w, h);
        reverse = false;
        this.imageOn = image;
        this.game = game;
        if (game != null) heels = (Heels) game.objects.get("HEELS");
    }

    public void toggle() {
        on = !on;
        if (checkDead() && Collision.dist(game.player.x - game.player.w/2, game.player.y - game.player.h/2, x, y) < (double) w/2) game.dead();
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (!visible) return;

        if (Game.debug || MapMaker.debug) {
            gg.setStroke(new BasicStroke(5));
            gg.setColor(new Color(0xC000FF));
            gg.drawRect(x + camX, y + camY, w, h);
        }

        if (!reverse) gg.drawImage(on ? imageOn : imageOff, x + camX, y + camY, w, h, null);
        else {
            if (
                    (heels != null && Collision.rectRect(heels, this)) ||
                    (game != null && game.player != null && Collision.dist(game.player.x - game.player.w/2, game.player.y - game.player.h/2, x, y) < (double) w/2))
                gg.drawImage(on ? imageOff : imageOn, x + camX, y + camY, w, h, null);
            else gg.drawImage(on ? imageOn : imageOff, x + camX, y + camY, w, h, null);
        }

        if ((Game.debug || MapMaker.debug) && reverse) {
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString("R", x + camX, y + camY + 50);
        }

        if (Game.debug || MapMaker.debug) {
            gg.setColor(new Color(0xC000FF));
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString(String.valueOf(id), x + camX, y + camY + 100);
        }
    }

    public boolean checkDead() {
        return (on && !reverse) || (!on && reverse);
    }
}
