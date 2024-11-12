package main.utility;

public class Node {
    public int x, y;
    public int g, h;
    Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}