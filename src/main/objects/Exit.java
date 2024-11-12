package main.objects;

import java.awt.*;

public class Exit extends Object {

    public Exit(Image image, int x, int y, int w, int h) {
        super(image, x, y, w, h);
        priorityZ = 0;
    }
}
