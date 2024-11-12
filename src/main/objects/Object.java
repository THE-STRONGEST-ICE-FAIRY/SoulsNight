package main.objects;

import main.utility.Camera;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Object implements Cloneable {
    public Image image;
    public int x;
    public int y;
    public int w;
    public int h;
    protected double priorityZ;
    public float opacity;
    public boolean visible;
    public float rotation;
    public boolean rotationAllowed;
    public boolean solid;
    public boolean solidAble;
    public int id;

    void init() {
        visible = true;
        opacity = 1.0f;
        rotation = 0;
        priorityZ = 1;
        solid = false;
        solidAble = false;
        rotationAllowed = false;
        id = -1;
    }

    Object(Image image, int x, int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = this.image.getWidth(null);
        this.h = this.image.getHeight(null);
        init();
    }

    public Object(Image image, int x, int y, int w, int h) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        init();
    }

    public Object() {
        init();
    }

    @Override
    public Object clone() {
        try {
            return (Object) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Shouldn't happen, since we are Cloneable
        }
    }

    public String data(Camera camera, boolean rotation) {
        return (x - camera.getX()) + " " + (y - camera.getY()) + " " + w + " " + h + (rotation ? " " + this.rotation : "");
    }

    public void setObject(Image image, int x, int y, int w, int h) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        init();
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (!visible) return;

        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        AffineTransform originalTransform = gg.getTransform();

        // Create a new transform
        AffineTransform transform = new AffineTransform();

        // Calculate the center of the image
        int centerX = x + w / 2 + camX;
        int centerY = y + h / 2 + camY;

        // Apply the rotation around the center of the image
        transform.rotate(Math.toRadians(rotation), centerX, centerY);

        // Draw the image with the applied transform
        gg.setTransform(transform);
        gg.drawImage(image, x + camX, y + camY, w, h, null);

        // Reset the transform to avoid affecting other drawing operations
        gg.setTransform(originalTransform);

        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public int getY() {return y + h;}

    public int getX() {return x;}

    public double getPriorityZ() {return priorityZ;}

    public boolean hovering(int x, int y) {
        return x < this.x + w &&
                x > this.x &&
                y < this.y + h &&
                y > this.y;
    }

    public void gotoxy(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
