package main.main;

import main.objects.Animated;
import main.objects.Assets;
import main.objects.Object;
import main.utility.Camera;
import main.utility.Cursor;
import main.utility.Key;

import javax.swing.*;
import java.awt.Panel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class GameOver {
    boolean visible;
    Assets assets;
    Cursor cursor;
    Key key;
    HashMap<String, Object> objects;
    HashMap<String, Image> images;
    int width, height;
    MainMenu mainMenu;
    Animated anim, anim2;

    GameOver(int width, int height, Assets assets, Cursor cursor, Key key) {
        visible = false;
        this.width = width;
        this.height = height;
        this.assets = assets;
        this.cursor = cursor;
        this.key = key;
        assets.gameOver(width, height);
        images = assets.images.get("GAME OVER");
        objects = assets.objects.get("GAME OVER");
        anim = (Animated) assets.objects.get("GAME OVER").get("ANIM");
        anim2 = (Animated) assets.objects.get("GAME OVER").get("ANIM2");
    }

    public void setMainMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public void draw(Graphics2D gg) {
        if (!visible) return;

        // Title
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.setColor(new Color(0x000000));
            gg.drawString("main.Game Over", 0, 50);
        }

        gg.setColor(Color.green);
        gg.setFont(new Font("Consolas", Font.BOLD, 100));
        gg.drawString("game over screen", width/2, 200);
        gg.drawString("u lose haha", width/2, 300);

        anim.once();
        anim2.loop();

        gg.setColor(new Color(0xFFFFFF));
        if (anim.visible) {
            gg.fillRect(0, 0, width, height);
            anim.draw(gg, 0, 0, false);
        }
        else {
            anim2.draw(gg, 0, 0, false);
            objects.get("BACK").draw(gg, 0, 0, false);
            script();
        }
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

            assets.sound.stop("DEATH");
            assets.sound.playOnLoop("MENU");
            mainMenu.visible = true;
            visible = false;
        }

        cursor.click = false;
    }
}
