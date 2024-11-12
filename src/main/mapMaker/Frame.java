package main.mapMaker;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Frame extends JFrame {
    MapMaker mapMaker;

    Frame() {
        setLayout(null);
        setResizable(false);
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Frame frame = this;
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (mapMaker != null) {
                    remove(mapMaker);
                }
                mapMaker = new MapMaker(getWidth(), getHeight(), frame);
                mapMaker.setBounds(0, 0, getWidth(), getHeight());
                add(mapMaker);
                revalidate();
                repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {}

            @Override
            public void componentHidden(ComponentEvent e) {}
        });

        setVisible(true);
    }
}
