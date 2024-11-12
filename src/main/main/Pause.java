package main.main;

import main.objects.Heels;
import main.objects.Object;
import main.utility.Cursor;
import main.utility.Key;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Pause {
    Game game;
    Cursor cursor;
    Key key;
    HashMap<String, Object> objects;
    HashMap<String, Image> images;

    public Pause(Game game) {
        this.game = game;
        cursor = game.cursor;
        key = game.key;

        objects = new HashMap<>(game.assets.objects.get("GAME PAUSE"));
        images = new HashMap<>(game.assets.images.get("GAME PAUSE"));

    }

    public void draw(Graphics2D gg) {
//        gg.setColor(new Color(0x471C002D, true));
//        gg.fillRect(100, 100, game.width - 200, game.height - 200);
//
//        gg.setColor(Color.magenta);
//        gg.setFont(new Font("Consolas", Font.BOLD, 50));
//        gg.drawString("PAUSED", 300, game.height/2);

        LinkedList<Object> ordered = new LinkedList<>(objects.values());
        ordered.sort(Comparator.comparingDouble(Object::getPriorityZ).thenComparing(Object::getY));
        for (Object o : ordered) o.draw(gg, 0, 0, false);

        script();
    }

    void script() {
        if (cursor.click) click();

        Object button = objects.get("CONTINUE BUTTON");
        if (button.hovering(cursor.getX(), cursor.getY()))
            button.image = images.get("CONTINUE ON");
        else button.image = images.get("CONTINUE OFF");

        button = objects.get("GIVE UP BUTTON");
        if (button.hovering(cursor.getX(), cursor.getY()))
            button.image = images.get("GIVE UP ON");
        else button.image = images.get("GIVE UP OFF");

        button = objects.get("RESTART BUTTON");
        if (button.hovering(cursor.getX(), cursor.getY()))
            button.image = images.get("RESTART ON");
        else button.image = images.get("RESTART OFF");
    }

    void click() {
        if (objects.get("CONTINUE BUTTON").hovering(cursor.getX(), cursor.getY())) {
            game.pause = false;
        }

        if (objects.get("GIVE UP BUTTON").hovering(cursor.getX(), cursor.getY())) {
            game.hud.hearts = 0;
            game.dead();
        }

        if (objects.get("RESTART BUTTON").hovering(cursor.getX(), cursor.getY())) {
            game.dead();
        }

        cursor.leftClick = false;
        cursor.rightClick = false;
        cursor.click = false;
    }
}
