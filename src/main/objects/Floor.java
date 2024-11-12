package main.objects;

import java.awt.*;

public class Floor extends Object {

    public Floor(Image image, int x, int y, int w, int h) {
        super(image, x, y, w, h);
        rotationAllowed = true;
        priorityZ = -1;
        id = 1;
    }
}
