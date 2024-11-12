package main.main;

import main.objects.Animated;
import main.objects.Assets;
import main.objects.Object;
import main.utility.Key;
import main.utility.Cursor;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class SplashPanel extends JPanel implements Runnable {
    int width, height;
    Thread thread;
    HashMap<String, Object> objects;
    LinkedList<Object> objectsOrdered;
    Key key;
    Cursor cursor;
    JFrame splash;
    Frame frame;
    float labelOpacity;
    boolean back;
    int labelInitDelay;

    SplashPanel(JFrame splash, int width, int height, Assets assets) {
        this.splash = splash;
        this.width = width;
        this.height = height;
        assets.splash(width, height);
        objects = assets.objects.get("SPLASH");
        objectsOrdered = new LinkedList<>(objects.values());
        objectsOrdered.sort(Comparator.comparingDouble(Object::getPriorityZ));

        thread = new Thread(this);
        thread.start();

        key = new Key(splash);
        cursor = new Cursor(this);
        this.frame = new Frame(assets);
        lock = true;

        labelOpacity = 0;
        back = false;
        labelInitDelay = 200;
    }

    public void run() {
        double drawInterval = (double) 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (thread != null) {
            repaint();

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    boolean lock;
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        // debug
        {
            gg.setFont(new Font("Consolas", Font.BOLD, 100));
            gg.setColor(new Color(0x000000));
            gg.drawString("SPLASH SCREEN", 0, 100);
        }

        for (Object o : objectsOrdered) o.draw(gg, 0, 0, false);

        label(gg);

        Animated anim = (Animated) objects.get("STAR");
        anim.loop();

        if ((key.anyKey || cursor.click) && lock) {
            frame.setVisible(true);
            splash.dispose();
            lock = false;
            cursor.click = false;
        }

        gg.dispose();
    }

    void label(Graphics2D gg) {
        if (labelInitDelay > 0) {
            labelInitDelay--;
            return;
        }

        if (!back) labelOpacity += 0.01f;
        else labelOpacity -= 0.01f;

        if (labelOpacity <= 0 || labelOpacity >= 1) back = !back;

        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.clamp(labelOpacity, 0, 1)));
        gg.setFont(new Font("Consolas", Font.BOLD, 30));
        gg.setColor(new Color(0x010B41));
        gg.drawString("[PRESS ANY KEY TO START]", 315, height - 20);
        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
