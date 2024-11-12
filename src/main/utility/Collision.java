package main.utility;

import main.objects.Object;

public class Collision {

    // LINE/RECTANGLE
    public static boolean lineRect(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh) {
        // check if the line has hit any of the rectangle's sides
        // uses the Line/Line function below
        boolean left =   lineLine(x1,y1,x2,y2, rx,ry,rx, ry+rh);
        boolean right =  lineLine(x1,y1,x2,y2, rx+rw,ry, rx+rw,ry+rh);
        boolean top =    lineLine(x1,y1,x2,y2, rx,ry, rx+rw,ry);
        boolean bottom = lineLine(x1,y1,x2,y2, rx,ry+rh, rx+rw,ry+rh);

        // if ANY of the above are true, the line
        // has hit the rectangle
        return left || right || top || bottom;
    }

    // LINE/RECTANGLE WITH LINE WIDTH
    public static boolean lineRectWidth(float x1, float y1, float x2, float y2, float rx, float ry, float rw, float rh, float width) {
        // Calculate the perpendicular vector to the line direction for the width
        float dx = x2 - x1;
        float dy = y2 - y1;
        float len = (float) Math.sqrt(dx * dx + dy * dy);
        float nx = -dy / len * width / 2;
        float ny = dx / len * width / 2;

        // Create the four points of the line as a rectangle
        float[] polyX = {x1 + nx, x1 - nx, x2 - nx, x2 + nx};
        float[] polyY = {y1 + ny, y1 - ny, y2 - ny, y2 + ny};

        // Check each rectangle side for collision
        for (int i = 0; i < 4; i++) {
            if (lineLine(polyX[i], polyY[i], polyX[(i + 1) % 4], polyY[(i + 1) % 4], rx, ry, rx, ry + rh) ||
                    lineLine(polyX[i], polyY[i], polyX[(i + 1) % 4], polyY[(i + 1) % 4], rx + rw, ry, rx + rw, ry + rh) ||
                    lineLine(polyX[i], polyY[i], polyX[(i + 1) % 4], polyY[(i + 1) % 4], rx, ry, rx + rw, ry) ||
                    lineLine(polyX[i], polyY[i], polyX[(i + 1) % 4], polyY[(i + 1) % 4], rx, ry + rh, rx + rw, ry + rh)) {
                return true;
            }
        }

        // If any collision is detected, return true
        return false;
    }

    // LINE/LINE
    public static boolean lineLine(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
        // calculate the direction of the lines
        float v = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
        float uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / v;
        float uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / v;

        // if uA and uB are between 0-1, lines are colliding
        return uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1;
    }

    // POINT/RECTANGLE
    public static boolean pointRect(float px, float py, float rx, float ry, float rw, float rh) {
        // is the point inside the rectangle's bounds?
        // above the bottom
        return px >= rx &&        // right of the left edge AND
                px <= rx + rw &&   // left of the right edge AND
                py >= ry &&        // below the top AND
                py <= ry + rh;
    }

    // RECTANGLE/RECTANGLE
    public static boolean rectRect(float r1x, float r1y, float r1w, float r1h, float r2x, float r2y, float r2w, float r2h) {

        // r1 bottom edge past r2 top
        return r1x + r1w >= r2x &&    // r1 right edge past r2 left
                r1x <= r2x + r2w &&    // r1 left edge past r2 right
                r1y + r1h >= r2y &&    // r1 top edge past r2 bottom
                r1y <= r2y + r2h;
    }

    // RECTANGLE/RECTANGLE with Objects
    public static boolean rectRect(Object r1, Object r2) {

        return r1.x + r1.w >= r2.x &&    // r1 right edge past r2 left
                r1.x <= r2.x + r2.w &&    // r1 left edge past r2 right
                r1.y + r1.h >= r2.y &&    // r1 top edge past r2 bottom
                r1.y <= r2.y + r2.h;
    }

    // RECTANGLE/RECTANGLE with one Object
    public static boolean rectRect(Object r1, int x, int y, int w, int h) {
        // Assuming r1 has fields x, y, w, h representing its coordinates and dimensions
        int r1X = r1.x;
        int r1Y = r1.y;
        int r1W = r1.w;
        int r1H = r1.h;

        return r1X + r1W >= x &&  // r1 right edge past r2 left
                r1X <= x + w &&    // r1 left edge past r2 right
                r1Y + r1H >= y &&  // r1 top edge past r2 bottom
                r1Y <= y + h;      // r1 bottom edge past r2 top
    }

    public static double dist(int x1, int y1, int x2, int y2) {
        // Calculate the differences in x and y coordinates
        int dx = x2 - x1;
        int dy = y2 - y1;

        // Calculate the square root of the sum of the squares of dx and dy
        return Math.sqrt(dx * dx + dy * dy);
    }
}
