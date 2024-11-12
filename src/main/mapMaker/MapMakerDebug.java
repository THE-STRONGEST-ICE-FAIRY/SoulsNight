package main.mapMaker;

import main.objects.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class MapMakerDebug extends JFrame {
    MapMaker mapMaker;
    LinkedList<JLabel> debug;

    MapMakerDebug(MapMaker mapMaker) {
        setLayout(null);
        setSize(500, 500);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        this.mapMaker = mapMaker;

        init();
    }

    void init() {
        debug = new LinkedList<>();

        debug.add(new JLabel("DEBUG"));
        for (int i = 0; i < 20; i++) debug.add(new JLabel());

        for (int i = 0; i < debug.size(); i++) {
            JLabel label = debug.get(i);
            label.setFont(new Font("Consolas", Font.BOLD, 15));
            label.setBounds(10, i * 30, getWidth(), 30);
            add(label);
        }

        Timer timer = new Timer(16, e -> {
            int i = 1;
            debug.get(i++).setText("Floor: " + mapMaker.fileName);
            debug.get(i++).setText("Object: " + mapMaker.hold.getClass().getSimpleName());
            if (mapMaker.hold instanceof Switch || mapMaker.hold instanceof Block || mapMaker.hold instanceof Spike || mapMaker.hold instanceof Turret || mapMaker.hold instanceof Decoration || mapMaker.hold instanceof Wall || mapMaker.hold instanceof Bouncer)
                debug.get(i++).setText("    Block ID: " + mapMaker.hold.id);
            if (mapMaker.hold instanceof Block b) debug.get(i++).setText("    Block on: " + b.solid);
            if (mapMaker.hold instanceof Bouncer b) debug.get(i++).setText("    Bouncer angle: " + Math.toDegrees(b.angle));
            if (mapMaker.hold instanceof Switch b) debug.get(i++).setText("    Rotator: " + b.rotator);
            if (mapMaker.hold instanceof Spike s) debug.get(i++).setText("    Spike imageOn, reverse: " + s.on + " " + s.reverse);
            if (mapMaker.hold instanceof Turret t) debug.get(i++).setText("    Turret on, detector, direction: " + t.on + " " + t.detector + " " + t.directionArr[t.direction]);
            debug.get(i++).setText("Camera: " + mapMaker.camera.data());
            debug.get(i++).setText("Cursor: " + mapMaker.cursor.data(mapMaker.camera));
            debug.get(i++).setText("Position, Size: " + mapMaker.hold.data(mapMaker.camera, mapMaker.hold.rotationAllowed));
            for (;i<debug.size();i++) debug.get(i).setText("");
        });
        timer.start();
    }
}
