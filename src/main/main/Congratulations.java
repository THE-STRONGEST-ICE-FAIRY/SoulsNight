package main.main;

import main.objects.Animated;
import main.objects.Assets;
import main.objects.Object;
import main.utility.Camera;
import main.utility.Cursor;
import main.utility.Key;

import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class Congratulations {
    boolean visible;
    int width, height;
    Cursor cursor;
    Key key;
    MainMenu mainMenu;
    HashMap<String, Object> objects;
    HashMap<String, Image> images;
    int seconds;
    Animated anim;

    Congratulations(int width, int height, Assets assets, Cursor cursor, Key key) {
        visible = false;
        this.width = width;
        this.height = height;
        this.cursor = cursor;
        this.key = key;
        assets.congratulations(width, height);
        images = assets.images.get("CONGRATULATIONS");
        objects = assets.objects.get("CONGRATULATIONS");
        anim = (Animated) assets.objects.get("CONGRATULATIONS").get("ANIM");
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void end(int seconds) {
        this.seconds = seconds;
        visible = true;
    }

    public void draw(Graphics2D gg) {
        if (!visible) return;

        anim.once();
        objects.get("BACK").draw(gg, 0, 0, false);
        objects.get("IMAGE").draw(gg, 0, 0, false);

        gg.setColor(new Color(0xEED152));
        gg.setFont(new Font("Consolas", Font.BOLD, 50));
        gg.drawString("Escaped in " + seconds + " seconds.", (int) (width * .6), (int) (height * .85));

        gg.setColor(new Color(0xFFFFFF));
        if (anim.visible) gg.fillRect(0, 0, width, height);
        anim.draw(gg, 0, 0, false);
        script();
    }

    private void script() {
        Object button = objects.get("BACK");
        if (button.hovering(cursor.getX(), cursor.getY()))
            button.image = images.get("BACK ON");
        else button.image = images.get("BACK OFF");

        if (cursor.click) click();
    }

    private void click() {
        if (objects.get("BACK").hovering(cursor.getX(), cursor.getY())) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            mainMenu.visible = true;
            mainMenu.playBGM();
            visible = false;
        }

        cursor.click = false;
    }
}