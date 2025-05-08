// src/entity/Zombie.java
package entity;

import main.GamePanel;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Zombie extends Entity {
    GamePanel gp;

    // e.g. health or damage you want to inflict
    private int damage = 1;
    private int counter = 100;

    public Zombie(GamePanel gp, int startCol, int startRow) {
        this.gp = gp;
        this.worldX = startCol * gp.tileSize;
        this.worldY = startRow * gp.tileSize;
        this.speed = 2;
        this.direction = "down";

        // define your hitbox
        solidArea = new java.awt.Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getZombieImage();
    }

    private void getZombieImage() {
        try {
            up1    = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_up_1.png"));
            up2    = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_up_2.png"));
            down1  = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_down_1.png"));
            down2  = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_down_2.png"));
            left1  = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_left_1.png"));
            left2  = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_left_2.png"));
            right1 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_right_1.png"));
            right2 = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/zombies/z_right_2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (counter > 0) counter--;
        else {chooseRandomDirection();
            counter = 100;
        }


        // 2) reset collision flag & check map tiles
        collisionOn = false;
        gp.cChecker.checkTile(this);

        // 3) check collision with player
        int playerIndex = gp.cChecker.checkEntity(this, new Entity[]{ gp.player });
        if (playerIndex != 999) {
            // we hit the player!
            gp.player.health -= damage;
            gp.ui.showMessage("Ouch! A zombie hit you!");
        }

        // 4) if no collision, actually move
        if (!collisionOn) {
            switch(direction) {
                case "up":    worldY -= speed; break;
                case "down":  worldY += speed; break;
                case "left":  worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        // 5) sprite animation
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    private void chooseRandomDirection() {
        // simple random walk
        int i = new java.util.Random().nextInt(100);
        if (i < 25) direction = "up";
        else if (i < 50) direction = "down";
        else if (i < 75) direction = "left";
        else direction = "right";
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch(direction) {
            case "up"    -> (spriteNum == 1) ? up1    : up2;
            case "down"  -> (spriteNum == 1) ? down1  : down2;
            case "left"  -> (spriteNum == 1) ? left1  : left2;
            case "right" -> (spriteNum == 1) ? right1 : right2;
            default      -> down1;
        };
        g2.drawImage(image, worldX - gp.player.worldX + gp.player.screenX,
                worldY - gp.player.worldY + gp.player.screenY,
                gp.tileSize, gp.tileSize, null);
    }
}
