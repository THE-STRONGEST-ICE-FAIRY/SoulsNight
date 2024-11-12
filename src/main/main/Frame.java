package main.main;

import main.objects.Assets;

import javax.swing.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class Frame extends JFrame {
    Panel panel;

    Frame(Assets assets) {
        setLayout(null);
        setResizable(false);
        setUndecorated(true);
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set the cursor to be invisible
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Image image = toolkit.getImage("");
//        Point hotspot = new Point(0, 0);
//        Cursor invisibleCursor = toolkit.createCustomCursor(image, hotspot, "InvisibleCursor");
//        setCursor(invisibleCursor);

        Frame frame = this;
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (panel != null) {
                    remove(panel);
                }
                panel = new Panel(getWidth(), getHeight(), assets, frame);
                panel.setBounds(0, 0, getWidth(), getHeight());
                add(panel);
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
    }
}
