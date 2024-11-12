package main.main;

import main.objects.Assets;
import main.utility.Camera;
import main.utility.Cursor;
import main.utility.Key;
import main.utility.Sound;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {
    Thread thread;
    Cursor cursor;
    Camera camera;
    Key key;
    MainMenu mainMenu;
    Game game;
    GameOver gameOver;
    Congratulations congratulations;

    Panel(int width, int height, Assets assets, JFrame frame) {
        thread = new Thread(this);
        thread.start();

        cursor = new Cursor(this);
        cursor.setAssets(assets);
        camera = new Camera(width/2, height/2, null);
        key = new Key(frame);

        mainMenu = new MainMenu(width, height, assets, cursor, camera, key);
        game = new Game(width, height, assets, cursor, camera, key);
        gameOver = new GameOver(width, height, assets, cursor, key);
        congratulations = new Congratulations(width, height, assets, cursor, key);

        mainMenu.setGame(game);
        game.setGameOver(gameOver);
        game.setCongratulations(congratulations);
        gameOver.setMainMenu(mainMenu);
        congratulations.setMainMenu(mainMenu);
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

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gg = (Graphics2D) g;

        mainMenu.draw(gg);
        game.draw(gg);
        gameOver.draw(gg);
        congratulations.draw(gg);
        cursor.draw(gg);

        gg.dispose();
    }
}
