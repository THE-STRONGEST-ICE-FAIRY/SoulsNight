package main.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    static class AStarNode implements Comparable<AStarNode> {
        Node node;
        int f;

        AStarNode(Node node, int f) {
            this.node = node;
            this.f = f;
        }

        public int compareTo(AStarNode other) {
            return Integer.compare(this.f, other.f);
        }
    }

    public static List<Node> findPath(int[][] grid, int startX, int startY, int targetX, int targetY, int radius) {
        int[][] directions = {{0, -1}, {-1, 0}, {1, 0}, {0, 1}}; // Updated for vertical then horizontal

        int rows = grid.length;
        int cols = grid[0].length;

        Node startNode = new Node(startX, startY);
        Node targetNode = new Node(targetX, targetY);

        PriorityQueue<AStarNode> openSet = new PriorityQueue<>();
        openSet.add(new AStarNode(startNode, 0));

        Node[][] cameFrom = new Node[rows][cols];
        int[][] gScore = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gScore[i][j] = Integer.MAX_VALUE;
            }
        }
        gScore[startY][startX] = 0;

        int[][] fScore = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                fScore[i][j] = Integer.MAX_VALUE;
            }
        }
        fScore[startY][startX] = heuristic(startNode, targetNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll().node;
            if (current.x == targetX && current.y == targetY) {
                return reconstructPath(cameFrom, current);
            }

            for (int[] dir : directions) {
                int neighborX = current.x + dir[0];
                int neighborY = current.y + dir[1];
                if (isValid(neighborX, neighborY, rows, cols, grid, radius)) {
                    int tentativeGScore = gScore[current.y][current.x] + 1;
                    if (tentativeGScore < gScore[neighborY][neighborX]) {
                        cameFrom[neighborY][neighborX] = current;
                        gScore[neighborY][neighborX] = tentativeGScore;
                        fScore[neighborY][neighborX] = gScore[neighborY][neighborX] + heuristic(new Node(neighborX, neighborY), targetNode);
                        openSet.add(new AStarNode(new Node(neighborX, neighborY), fScore[neighborY][neighborX]));
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private static boolean isValid(int x, int y, int rows, int cols, int[][] grid, int radius) {
        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int checkX = x + i;
                int checkY = y + j;
                if (checkX < 0 || checkX >= cols || checkY < 0 || checkY >= rows || grid[checkY][checkX] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<Node> reconstructPath(Node[][] cameFrom, Node current) {
        List<Node> path = new ArrayList<>();
        path.add(current);
        while (cameFrom[current.y][current.x] != null) {
            current = cameFrom[current.y][current.x];
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }

    private static int heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0}
        };
        int startX = 0;
        int startY = 0;
        int targetX = 4;
        int targetY = 4;
        int radius = 1;

        List<Node> path = AStar.findPath(grid, startX, startY, targetX, targetY, radius);

        if (path.isEmpty()) {
            System.out.println("No path found!");
        } else {
            System.out.println("Path found:");
            for (Node node : path) {
                System.out.println("(" + node.x + ", " + node.y + ")");
            }
        }
    }
}
