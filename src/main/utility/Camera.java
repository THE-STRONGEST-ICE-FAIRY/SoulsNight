package main.utility;

import main.mapMaker.MapMaker;

public class Camera {
    int x, y;
    public boolean dragScreenLock;
    int dx, dy;

    public Camera(int newX, int newY, MapMaker mapMaker) {
        this.x = newX;
        this.y = newY;
    }

    public void dragScreen(Cursor cursor, int startX, int startY) {

        if (!dragScreenLock) {
            dragScreenLock = true;
            dx = cursor.getX() - x;
            dy = cursor.getY() - y;
        }

        setXY(cursor.getX() - dx, cursor.getY() - dy);

//        System.out.println("DRAGGING " + x + " " + y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String data() {
        return x + " " + y;
    }
}
