package entity;

import exceptions.InvalidImageSizeException;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    UtilityTool uTool = new UtilityTool();
    public final int screenX, screenY;
    public int hasKey = 0;
    public static final int[] PLAYER_COORDS = {23, 21};
    private long lastMoveTime = 0;
    private long lastAnimationTime = 0;
    private int animationInterval = 5; // Animation update every 50 ms
    public boolean isMoving = false;



    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        attackArea.width = 36;
        attackArea.height = 36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up1 = uTool.scaleImage(up1, gp.tileSize, gp.tileSize);

            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            up2 = uTool.scaleImage(up2, gp.tileSize, gp.tileSize);

            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down1 = uTool.scaleImage(down1, gp.tileSize, gp.tileSize);

            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            down2 = uTool.scaleImage(down2, gp.tileSize, gp.tileSize);

            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left1 = uTool.scaleImage(left1, gp.tileSize, gp.tileSize);

            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            left2 = uTool.scaleImage(left2, gp.tileSize, gp.tileSize);

            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right1 = uTool.scaleImage(right1, gp.tileSize, gp.tileSize);

            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            right2 = uTool.scaleImage(right2, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            System.out.println("Error loading player images:");
            e.printStackTrace();
        } catch (InvalidImageSizeException e) {
            System.out.println("Player image size is incorrect:");
            e.printStackTrace();
        }
    }


    public void getPlayerAttackImage() {
        try {
            attackUp1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_up_1.png"));
            attackUp1 = uTool.scaleImage(attackUp1, gp.tileSize, gp.tileSize);

            attackUp2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_up_2.png"));
            attackUp2 = uTool.scaleImage(attackUp2, gp.tileSize, gp.tileSize);

            attackDown1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_down_1.png"));
            attackDown1 = uTool.scaleImage(attackDown1, gp.tileSize, gp.tileSize);

            attackDown2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_down_2.png"));
            attackDown2 = uTool.scaleImage(attackDown2, gp.tileSize, gp.tileSize);

            attackLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_left_1.png"));
            attackLeft1 = uTool.scaleImage(attackLeft1, gp.tileSize, gp.tileSize);

            attackLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_left_2.png"));
            attackLeft2 = uTool.scaleImage(attackLeft2, gp.tileSize, gp.tileSize);

            attackRight1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_right_1.png"));
            attackRight1 = uTool.scaleImage(attackRight1, gp.tileSize, gp.tileSize);

            attackRight2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_attack_right_2.png"));
            attackRight2 = uTool.scaleImage(attackRight2, gp.tileSize, gp.tileSize);
        } catch (IOException | InvalidImageSizeException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * PLAYER_COORDS[0];
        worldY = gp.tileSize * PLAYER_COORDS[1];
        speed = 4;
        direction = "down";
        // PLAYER STATUS
        maxLife = 6;
        life = maxLife;
    }


//    public void update() {
//        if(gp.keyH.enterPressed ==  true) {
//            attacking = true;
//            attacking();
//            gp.keyH.enterPressed = false;
//        } else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
//            if (keyH.upPressed == true) {
//                direction = "up";
//            } else if (keyH.downPressed == true) {
//                direction = "down";
//            } else if (keyH.leftPressed == true) {
//                direction = "left";
//            } else if (keyH.rightPressed == true) {
//                direction = "right";
//            } else if (keyH.enterPressed == true) {
//                attacking = true;
//            }
//
//            // CHECK TILE COLLISION
//            collisionOn = false;
//            gp.cChecker.checkTile(this);
//
//            // CHECK OBJECT COLLISION
//            int objIndex = gp.cChecker.checkObject(this, true);
//            pickUpObject(objIndex);
//
//            // CHECK EVENT
//            gp.eHandler.checkEvent();
    ////            gp.keyH.enterPressed = false;
//
//            // IF COLLISION IS FALSE, PLAYER CAN MOVE
//            if (collisionOn == false) {
//                switch (direction) {
//                    case "up":
//                        worldY -= speed;
//                        break;
//                    case "down":
//                        worldY += speed;
//                        break;
//                    case "left":
//                        worldX -= speed;
//                        break;
//                    case "right":
//                        worldX += speed;
//                        break;
//                }
//            }
//
//            spriteCounter++;
//            if (spriteCounter > 12) {
//                if (spriteNum == 1) {
//                    spriteNum = 2;
//                } else if (spriteNum == 2) {
//                    spriteNum = 1;
//                }
//                spriteCounter = 0;
//            }
//        }
//    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastMoveTime;
        long animationTime = currentTime - lastAnimationTime;

        // Handle attack animation
        if (attacking) {
            attacking();
            spriteCounter++;
            if (spriteCounter > 20) {
                attacking = false;
                spriteCounter = 0;
            }
            return;
        }

        // Trigger attack if Enter key is pressed
        if (keyH.enterPressed) {
            attacking = true;
            gp.keyH.enterPressed = false;
            spriteCounter = 0;
            return;
        }

        // Check if any movement key is pressed
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            isMoving = true;
            gp.eHandler.checkEvent();

            // Update direction based on the key pressed
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // Reset collision status before checking
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check object collision and pick up if possible
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Check collision before moving
            if (!collisionOn && elapsedTime >= 100) {
                switch (direction) {
                    case "up":
                        if (!gp.cChecker.isCollidableTile(worldX, worldY - gp.tileSize)) {
                            worldY -= gp.tileSize;
                            lastMoveTime = currentTime;
                        }
                        break;
                    case "down":
                        if (!gp.cChecker.isCollidableTile(worldX, worldY + gp.tileSize)) {
                            worldY += gp.tileSize;
                            lastMoveTime = currentTime;
                        }
                        break;
                    case "left":
                        if (!gp.cChecker.isCollidableTile(worldX - gp.tileSize, worldY)) {
                            worldX -= gp.tileSize;
                            lastMoveTime = currentTime;
                        }
                        break;
                    case "right":
                        if (!gp.cChecker.isCollidableTile(worldX + gp.tileSize, worldY)) {
                            worldX += gp.tileSize;
                            lastMoveTime = currentTime;
                        }
                        break;
                }
            }

            // Update animation only when moving
            if (animationTime >= animationInterval) {
                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
                lastAnimationTime = currentTime;
            }
        } else {
            // Stop movement and reset animation to default standing frame
            isMoving = false;
            spriteNum = 1;  // Default standing sprite when not moving
        }
    }






    public void attacking() {
        spriteCounter++;
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
            switch (direction) {
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You got a key!");
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("You opened the door!");
                    } else {
                        gp.ui.showMessage("You need a key!");
                    }
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    break;
            }
        }
    }

    //    public void draw(Graphics2D g2) {
    ////        g2.setColor(Color.WHITE);
    ////        g2.fillRect(x, worldY, gp.tileSize, gp.tileSize);
//
//        BufferedImage image = null;
//        int tempScreenX = screenX;
//        int tempScreenY = screenY;
//
//        switch (direction) {
//            case "up":
//                if(attacking == false) {
//                    if (spriteNum == 1) {
//                        image = up1;
//                    }
//                    if (spriteNum == 2) {
//                        image = up2;
//                    }
//                }
//                if(attacking == true) {
//                    tempScreenY = screenY -
//                            gp.tileSize;
//                    if (spriteNum == 1) {
//                        image = attackUp1;
//                    }
//                    if (spriteNum == 2) {
//                        image = attackUp2;
//                    }
//                }
//                break;
//            case "down":
//                if(attacking == false) {
//                    if (spriteNum == 1) {
//                        image = down1;
//                    }
//                    if (spriteNum == 2) {
//                        image = down2;
//                    }
//                }
//                if(attacking == true) {
//                    tempScreenY = screenY + gp.tileSize;
//                    if (spriteNum == 1) {
//                        image = attackDown1;
//                    }
//                    if (spriteNum == 2) {
//                        image = attackDown2;
//                    }
//                }
//                break;
//            case "left":
//                if(attacking == false) {
//                    if (spriteNum == 1) {
//                        image = left1;
//                    }
//                    if (spriteNum == 2) {
//                        image = left2;
//                    }
//                }
//                if(attacking == true) {
//                    if (spriteNum == 1) {
//                        image = attackLeft1;
//                    }
//                    if (spriteNum == 2) {
//                        image = attackLeft2;
//                    }
//                }
//                break;
//            case "right":
//                if(attacking == false) {
//                    if (spriteNum == 1) {
//                        image = right1;
//                    }
//                    if (spriteNum == 2) {
//                        image = right2;
//                    }
//                }
//                if(attacking == true) {
//
//                    if (spriteNum == 1) {
//                        image = attackRight1;
//                    }
//                    if (spriteNum == 2) {
//                        image = attackRight2;
//                    }
//                }
//                break;
//            default:
//                image = down1;
//        }
//        g2.drawImage(image, tempScreenX, tempScreenY, gp.tileSize, gp.tileSize, null);
//
//    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int drawX = screenX;
        int drawY = screenY;
        int drawWidth = gp.tileSize;
        int drawHeight = gp.tileSize;

        switch (direction) {
            case "up":
                if (attacking) {
                    image = (spriteNum == 1) ? attackUp1 : attackUp2;
                    drawY = screenY - gp.tileSize / 2;  // shift up slightly
                    drawHeight = gp.tileSize * 2;       // e.g. 96px tall
                } else {
                    image = (spriteNum == 1) ? up1 : up2;
                }
                break;
            case "down":
                if (attacking) {
                    image = (spriteNum == 1) ? attackDown1 : attackDown2;
                    drawHeight = gp.tileSize * 2;
                } else {
                    image = (spriteNum == 1) ? down1 : down2;
                }
                break;
            case "left":
                if (attacking) {
                    image = (spriteNum == 1) ? attackLeft1 : attackLeft2;
                    drawX = screenX - gp.tileSize / 2;
                    drawWidth = gp.tileSize * 2;
                } else {
                    image = (spriteNum == 1) ? left1 : left2;
                }
                break;
            case "right":
                if (attacking) {
                    image = (spriteNum == 1) ? attackRight1 : attackRight2;
                    drawWidth = gp.tileSize * 2;
                } else {
                    image = (spriteNum == 1) ? right1 : right2;
                }
                break;
        }

        if (image != null) {
            g2.drawImage(image, drawX, drawY, drawWidth, drawHeight, null);
        }
    }




}
