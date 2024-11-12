package main.main;

import main.objects.Assets;

import javax.swing.*;

public class Splash extends JFrame {
    SplashPanel panel;

    Splash(Assets assets) {
        setLayout(null);
        setSize(1000, 710);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        panel = new SplashPanel(this, getWidth(), getHeight(), assets);
        panel.setBounds(0, 0, getWidth(), getHeight());
        add(panel);

        setVisible(true);
    }
}
