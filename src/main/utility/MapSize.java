package main.utility;

import main.objects.Object;

public class MapSize {
    public int x1;
    public int y1;
    public int x2;
    public int y2;

    public MapSize() {
        this.x1 = 0;
        this.y1 = 0;
        this.x2 = 0;
        this.y2 = 0;
    }

    public void setSizes(Object obj) {
        this.x1 = Math.min(obj.x, x1);
        this.y1 = Math.min(obj.y, y1);
        this.x2 = Math.max(obj.x + obj.w, x2);
        this.y2 = Math.max(obj.y + obj.h, y2);
    }

    public void setSizes(int newX1, int newY1, int newX2, int newY2) {
        this.x1 = newX1;
        this.y1 = newY1;
        this.x2 = newX2;
        this.y2 = newY2;
    }

    public String data() {
        return x1 + " " + y1 + " " + x2 + " " + y2;
    }
}
