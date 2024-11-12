package main.main;

import main.mapMaker.MapMaker;
import main.objects.Assets;
import main.objects.Object;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class HUD {
    public int hearts;
    boolean visible;
    int fps, fpsCounter;
    int width, height;
    int seconds;
    LinkedList<Image> heartImages;
    Object heartObject;

    HUD(int width, int height, Assets assets) {
        this.width = width;
        this.height = height;

        heartImages = new LinkedList<>(assets.imagesLinkedList.get("HEARTS"));
        heartObject = assets.objects.get("GAME HUD").get("HEARTS");

        seconds = 0;
        hearts = 10;
        visible = true;

        Timer timer = new Timer(16, e -> {
            fpsCounter++;
        });
        timer.start();

        Timer timer2 = new Timer(1000, e -> {
            seconds++;
            fps = fpsCounter;
            fpsCounter = 0;
        });
        timer2.start();
    }

    void draw(Graphics2D gg) {
        if (!visible) return;

        if (Game.debug || MapMaker.debug){
            gg.setColor(Color.magenta);
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString(String.valueOf(hearts), 100, 100);

            gg.setColor(Color.orange);
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString(fps + ": " + fpsCounter, width - 200, 100);
        }

        heartObject.image = heartImages.get(10 - hearts);
        heartObject.draw(gg, 0, 0, false);
    }

    void reset() {
        hearts = 10;
    }
}
