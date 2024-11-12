package main.objects;

import main.main.Game;
import main.mapMaker.MapMaker;
import main.utility.Collision;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.util.LinkedList;

public class Turret extends Object {
    public boolean detector;
    public int direction;
    public int[] directionArr;
    public Image imageOn, off, sign;
    public boolean on;
    public LinkedList<Turret> outputs;
    public HashMap<String, Image> directions;
    int showSignDelay;
    Game game;

    Turret(Image image, int x, int y, int w, int h, Game game) {
        super(image, x, y, w, h);
        this.game = game;
        outputs = new LinkedList<>();
        directions = new HashMap<>();
        imageOn = image;
        solid = true;
        solidAble = true;
        on = true;
        direction = 0;
        showSignDelay = 0;
        directionArr = new int[]{1, 4, 2, 3};
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (!visible) return;

        switch (directionArr[direction % 4]) {
            case 1 -> sign = directions.get("RIGHT");
            case 2 -> sign = directions.get("LEFT");
            case 3 -> sign = directions.get("UP");
            case 4 -> sign = directions.get("DOWN");
        }

        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        AffineTransform originalTransform = gg.getTransform();

        // Create a new transform
        AffineTransform transform = new AffineTransform();

        // Calculate the center of the image
        int centerX = x + w / 2 + camX;
        int centerY = y + h / 2 + camY;

        // Apply the rotation around the center of the image
        transform.rotate(Math.toRadians(rotation), centerX, centerY);

        // Draw the image with the applied transform
        gg.setTransform(transform);
        if (!detector || (Game.debug || MapMaker.debug)) gg.drawImage(on ? imageOn : off, x + camX, y + camY, w, h, null);
        if (showSignDelay > 0) {
            showSignDelay--;
            gg.drawImage(sign, x + camX, y + camY - 10, w, h, null);
        }
        if ((Game.debug || MapMaker.debug) && detector) {
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString("D", x + camX, y + camY + 50);
        }

        // Reset the transform to avoid affecting other drawing operations
        gg.setTransform(originalTransform);

        if (Game.debug || MapMaker.debug) {
            gg.setColor(new Color(0xC000FF));
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString(String.valueOf(directionArr[direction % 4]), x + camX + 50, y + camY + 50);
            gg.drawString(String.valueOf(id), x + camX, y + camY + 100);
            gg.setColor(new Color(0xFFB316));
            gg.drawRect(x + camX, y + camY, w, h);
            if (game != null) gg.drawRect((int) (game.player.x - (float) game.player.w /2 + game.player.w /4) + camX, (int) (game.player.y - (float) game.player.h /2) + camY, game.player.w/2, game.player.h/2);
        }

        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public void setDetector(boolean detector) {
        this.detector = detector;
//        visible = !detector;
    }

    public boolean checkDead() {
        boolean check = false;
        for (Turret o : outputs) if (o.on && o.id == id && directionArr[o.direction] == directionArr[direction] &&
                Collision.rectRect(game.player.x - (float) game.player.w /2 + game.player.w /4, game.player.y - (float) game.player.h /2, game.player.w/2, (float) game.player.h /2, x, y, w, h)) {
            check = true;
            break;
        }
        return detector && check;
    }

    public void toggle() {
        on = !on;
        if (checkDead()) game.dead();
    }

    public void rotate() {
        if (detector) return;
        System.out.println("Check");
        direction += 1;
        direction = direction % 4;
        if (checkDead()) game.dead();
    }
}
