package ui;

import main.GamePanel;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font kongtext_30, kongtext_50;
    BufferedImage keyImage, heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;
        kongtext_30 = new Font("Kongtext", Font.PLAIN, 30);
        kongtext_50 = new Font("Kongtext", Font.BOLD, 50);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.playState) {
            if (gameFinished == true) {
                g2.setFont(kongtext_50);
                g2.setColor(Color.YELLOW);

                String text;
                int textLength;
                int x, y;

                text = "WIN! WIN! WIN!";
                textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
                x = gp.screenWidth / 2 - (textLength / 2);
                y = gp.screenHeight / 2 - (gp.tileSize * 3);
                g2.drawString(text, x, y);
                gp.gameThread = null;
            } else {
                g2.setFont(kongtext_30);
                g2.setColor(Color.WHITE);
                g2.drawImage(keyImage, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
                g2.drawString("x" + gp.player.hasKey, 74, 65);

                // MESSAGE
                if (messageOn == true) {
                    g2.setFont(g2.getFont().deriveFont(15f));
                    g2.drawString(message, gp.tileSize / 2, gp.tileSize * 5);
                    messageCounter++;
                    if (messageCounter > 120) {
                        messageCounter = 0;
                        messageOn = false;
                    }
                }
                drawPlayerLife(g2);
            }
        } else if (gp.gameState == gp.pauseState) {
            drawPauseScreen(g2);
            drawPlayerLife(g2);
        }
    }

    public void drawPauseScreen (Graphics2D g2) {
        g2.setFont(kongtext_30);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(60f));
        String text = "PAUSED";
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - (textLength / 2);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawPlayerLife(Graphics2D g2) {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2 + (gp.tileSize * 10);
        int i = 0;
        // DRAWS BLANK HEART
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }
        x = gp.tileSize / 2;
        y = gp.tileSize / 2 + (gp.tileSize * 10);
        i = 0;
        // DRAWS CURRENT LIFE
        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }
}
