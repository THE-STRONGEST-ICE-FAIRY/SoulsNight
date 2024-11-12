package main.utility;

import main.objects.Animated;
import main.objects.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.Random;

import static java.awt.event.MouseEvent.*;

public class Cursor {
    int x, y;
    public boolean click;
    public boolean press;
    int pressX, pressY;
    int lastX, lastY;
    int clicks;
    public int clickCD;
    public boolean leftClick, middleClick, rightClick;
    public boolean leftPress, middlePress, rightPress;
    static Robot robot;
    Animated tapAnimation;
    Assets assets;
    Random random;

    public Cursor(JPanel panel) {
        x = 0;
        y = 0;
        clicks = 0;
        clickCD = 0;

        random = new Random();

        LinkedList<Image> images = new LinkedList<>();
        for (int i = 0; i < 18; i++) images.add(new ImageIcon("src/assets/tapAnim/tap_00" + String.format("%02d", i) + ".png").getImage());
        tapAnimation = new Animated(images, 1, 0, 0, 200, 200);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (assets != null) assets.sound.play("CLICK" + random.nextInt(1, 5));
                click = true;
                clickCD = 5;
                clicks++;
                tapAnimation.play();
                switch (e.getButton()) {
                    case BUTTON1 -> leftClick = true;
                    case BUTTON2 -> middleClick = true;
                    case BUTTON3 -> rightClick = true;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                press = true;
                pressX = e.getX();
                pressY = e.getY();
                rightPress = e.getButton() == BUTTON3;
                leftPress = e.getButton() == BUTTON1;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                press = false;
                leftPress = false;
                middlePress = false;
                rightPress = false;
                lastX = e.getX();
                lastY = e.getY();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void setAssets(Assets assets) {
        this.assets = assets;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics2D gg) {
        if (clickCD > 0) {
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.setColor(new Color(0x79FF03));
            gg.drawString("CLICK " + clicks, x, y);
        }

        tapAnimation.gotoxy(lastX - tapAnimation.w / 2, lastY - tapAnimation.h / 2);
        tapAnimation.once();
        tapAnimation.draw(gg, 0, 0, false);

        clickCD = Math.max(0, clickCD - 1);
    }
    public String data() {
        return x + " " + y;
    }

    public String data(Camera camera) {
        return x + " " + y + " " + (x + camera.getX()) + " " + (y + camera.getY());
    }

    public static void gotoXY(int x, int y) {
        robot.mouseMove(x, y);
    }
}
