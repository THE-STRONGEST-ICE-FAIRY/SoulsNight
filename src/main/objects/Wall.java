package main.objects;

import main.main.Game;
import main.mapMaker.MapMaker;

import java.awt.*;
import java.util.LinkedList;

public class Wall extends Object {
    LinkedList<Image> walls;

    public Wall(Image image, int x, int y, int w, int h) {
        super(image, x, y, w, h);

        walls = new LinkedList<>();

        solid = true;
        solidAble = true;
        setID(1);
    }

    public void setID(int id) {
        this.id = id;
        if (id == 1) {
            priorityZ = 2;
            visible = (Game.debug || MapMaker.debug);
        }
        else {
            priorityZ = 1;
            visible = true;
        }
    }
}
