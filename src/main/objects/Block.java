package main.objects;

import main.main.Game;
import main.mapMaker.MapMaker;
import main.utility.Collision;

import java.awt.*;

public class Block extends Object {
    Image imageOn, imageOff;
    Game game;

    Block(Image image, int x, int y, int w, int h, Game game) {
        super(image, x, y, w, h);
        this.game = game;
        imageOff = image;
        solid = true;
        solidAble = true;
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (!visible) return;

        if (Game.debug || MapMaker.debug) {
            gg.setStroke(new BasicStroke(5));
            gg.setColor(new Color(0xC000FF));
            gg.drawRect(x + camX, y + camY, w, h);
        }

        gg.drawImage(solid ? imageOn : imageOff, x + camX, y + camY, w, h, null);

        if (Game.debug || MapMaker.debug) {
            gg.setColor(new Color(0xC000FF));
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString(String.valueOf(id), x + camX, y + camY + 100);
        }
    }

    public void toggle() {
        solid = !solid;
        priorityZ = solid ? 1 : -0.1;
        if (solid && Collision.dist(game.player.x - game.player.w/2, game.player.y - game.player.h/2, x, y) < (double) w/2) game.dead();
    }
}