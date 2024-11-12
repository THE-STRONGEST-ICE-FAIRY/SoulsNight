package main.objects;

import main.main.Game;
import main.mapMaker.MapMaker;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Bouncer extends Object {
    public double angle;
    Image imageOn;
    int onDelay;

    Bouncer(Image image, int x, int y, int w, int h) {
        super(image, x, y, w, h);
        onDelay = 0;
    }

    public void draw(Graphics2D gg, int camX, int camY, boolean pause) {
        if (!visible) return;

        if (onDelay > 0) onDelay--;

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
        gg.drawImage(onDelay > 0 ? imageOn : image, x + camX, y + camY, w, h, null);

        // Reset the transform to avoid affecting other drawing operations
        gg.setTransform(originalTransform);

        if (Game.debug || MapMaker.debug) {
            gg.setColor(new Color(0xC000FF));
            gg.setFont(new Font("Consolas", Font.BOLD, 50));
            gg.drawString(String.format("%.2f", Math.toDegrees(angle)), x + camX, y + camY + 50);
            gg.drawString(String.valueOf(id), x + camX, y + camY + 100);
        }

        gg.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }
}
