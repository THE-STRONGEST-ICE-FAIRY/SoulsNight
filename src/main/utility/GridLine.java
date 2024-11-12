package main.utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GridLine {

    public static class Node {
        public int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

    public static List<Node> getLineCoordinates(int[][] grid, int x1, int y1, int x2, int y2, int size) {
        Set<Node> hitCoordinates = new HashSet<>();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;

        int err = dx - dy;

        while (true) {
            for (int i = -size / 2; i <= size / 2; i++) {
                for (int j = -size / 2; j <= size / 2; j++) {
                    int nx = x1 + i;
                    int ny = y1 + j;
                    if (nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[0].length) {
                        hitCoordinates.add(new Node(nx, ny));
                    }
                }
            }

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;

            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }

        return new ArrayList<>(hitCoordinates);
    }

    public static boolean lineCollision(int[][] grid, int x1, int y1, int x2, int y2, int size) {
        List<Node> hitCoordinates = getLineCoordinates(grid, x1, y1, x2, y2, size);

        for (Node node : hitCoordinates) {
            if (grid[node.x][node.y] == 1) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0, 0},
                {0, 1, 0, 1},
                {0, 0, 0, 0},
                {1, 0, 0, 0}
        };

        List<Node> result = getLineCoordinates(grid, 1, 0, 1, 3, 2);

        for (Node node : result) {
            System.out.println(node);
        }

        System.out.println(result.size());
    }
}
