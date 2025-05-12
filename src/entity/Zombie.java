/** package entity;

import main.GamePanel;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Random;


public class Zombie extends Entity {
    private final GamePanel gp;

    // combat & wander
    private final int damage = 1;
    private boolean isTouchingPlayer = false;
    private int counter = 100;       // ticks until next random turn

    // A* pathfinding
    private Deque<Point> path = new ArrayDeque<>();
    private Point lastTarget = new Point(-1, -1);
    private static final int[][] DIRS = {
            {1, 0}, {-1, 0},
            {0, 1}, {0, -1}
    };

    public Zombie(GamePanel gp, int startCol, int startRow) {
        this.gp = gp;

        // place on grid
        this.worldX = startCol * gp.tileSize;
        this.worldY = startRow * gp.tileSize;
        this.speed = 2;
        this.direction = "down";

        // hitbox
        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        loadImages();
    }

    private void loadImages() {
        try {
            up1 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_up_1.png"));
            up2 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_up_2.png"));
            down1 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_down_1.png"));
            down2 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_down_2.png"));
            left1 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_left_1.png"));
            left2 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_left_2.png"));
            right1 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_right_1.png"));
            right2 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/zombie_right_2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void update() {
        // 1) random-walk timer
        if (counter > 0) {
            counter--;
        } else {
            chooseRandomDirection();
            counter = 30;
        }

        // 2) compute tile coords
        int zCol = worldX / gp.tileSize;
        int zRow = worldY / gp.tileSize;
        int pCol = gp.player.worldX / gp.tileSize;
        int pRow = gp.player.worldY / gp.tileSize;

        // 3) recompute A* if player moved or no existing path
        if (!lastTarget.equals(new Point(pCol, pRow)) || path.isEmpty()) {
            lastTarget.setLocation(pCol, pRow);
            path.clear();
            List<Point> newPath = findPathAstar(zCol, zRow, pCol, pRow);
            if (newPath != null) {
                for (int i = newPath.size() - 1; i >= 0; i--) {
                    path.add(newPath.get(i));
                }
            }
        }

        // 4) follow path if present
        if (!path.isEmpty()) {
            followPath();
        }

        // 5) collision with tiles (water/wall/tree)
        collisionOn = false;
        gp.cChecker.checkTile(this);

        if (collisionOn) {
            // we hit an unwalkable tile—clear path and pick a new random direction
            path.clear();
            chooseRandomDirection();
        }

        // 6) collision with player → attack
        int idx = gp.cChecker.checkEntity(this, new Entity[]{gp.player});
        if (idx != 999) {
            isTouchingPlayer = true;
        } else if (isTouchingPlayer) {
            gp.player.life -= damage;
            isTouchingPlayer = false;
        }

        // 7) move if no collision
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        // 8) animate sprite
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1 ? 2 : 1);
            spriteCounter = 0;
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage img;
        switch (direction) {
            case "up":
                img = (spriteNum == 1 ? up1 : up2);
                break;
            case "down":
                img = (spriteNum == 1 ? down1 : down2);
                break;
            case "left":
                img = (spriteNum == 1 ? left1 : left2);
                break;
            case "right":
                img = (spriteNum == 1 ? right1 : right2);
                break;
            default:
                img = down1;
        }
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }


    private void chooseRandomDirection() {
        int i = new Random().nextInt(100);
        if (i < 25) direction = "up";
        else if (i < 50) direction = "down";
        else if (i < 75) direction = "left";
        else direction = "right";
    }


    private void followPath() {
        Point next = path.pollFirst();
        if (next == null) return;
        int zCol = worldX / gp.tileSize, zRow = worldY / gp.tileSize;
        if (next.x > zCol) direction = "right";
        else if (next.x < zCol) direction = "left";
        else if (next.y > zRow) direction = "down";
        else if (next.y < zRow) direction = "up";
    }


    private List<Point> findPathAstar(int startC, int startR, int goalC, int goalR) {
        int maxC = gp.maxWorldCol, maxR = gp.maxWorldRow;
        Node[][] nodes = new Node[maxC][maxR];
        for (int c = 0; c < maxC; c++)
            for (int r = 0; r < maxR; r++)
                nodes[c][r] = new Node(c, r);

        PriorityQueue<Node> open = new PriorityQueue<>();
        boolean[][] closed = new boolean[maxC][maxR];

        Node start = nodes[startC][startR];
        Node goal = nodes[goalC][goalR];
        start.g = 0;
        start.h = heuristic(start, goal);
        start.f = start.h;
        open.add(start);

        while (!open.isEmpty()) {
            Node cur = open.poll();
            if (cur == goal) {
                List<Point> result = new ArrayList<>();
                for (Node n = cur; n != start; n = n.parent) {
                    result.add(new Point(n.c, n.r));
                }
                return result;
            }

            closed[cur.c][cur.r] = true;
            for (int[] d : DIRS) {
                int nc = cur.c + d[0], nr = cur.r + d[1];
                if (nc < 0 || nr < 0 || nc >= maxC || nr >= maxR) continue;
                if (closed[nc][nr]) continue;
                if (gp.tileM.isTileBlocked(nc, nr)) continue;

                Node nbr = nodes[nc][nr];
                double ng = cur.g + 1;
                if (ng < nbr.g) {
                    nbr.parent = cur;
                    nbr.g = ng;
                    nbr.h = heuristic(nbr, goal);
                    nbr.f = nbr.g + nbr.h;
                    open.remove(nbr);
                    open.add(nbr);
                }
            }
        }
        return null;
    }

    private double heuristic(Node a, Node b) {
        return Math.abs(a.c - b.c) + Math.abs(a.r - b.r);
    }


    private static class Node implements Comparable<Node> {
        final int c;
        final int r;
        double g = Double.POSITIVE_INFINITY;
        double h = 0;
        double f = Double.POSITIVE_INFINITY;
        Node parent = null;

        Node(int c, int r) {
            this.c = c;
            this.r = r;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.f, o.f);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Node)) {
                return false;
            }
            Node n = (Node) o;
            return n.c == this.c && n.r == this.r;
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, r);
        }
    }
}

*/