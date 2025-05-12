package ui;

import main.GamePanel;
import object.ObjectHeart;
import object.ObjectKey;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Font kongtext_30, kongtext_50;
    BufferedImage keyImage, heart_full, heart_half, heart_blank;

    // Restart button bounds
    private final int btnW  = 200;
    private final int btnH  =  60;
    private final int btnX;
    private final int btnY;

    public boolean messageOn   = false;
    public String  message     = "";
    public int     messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp) {
        this.gp = gp;

        // compute button rectangle (centered under GAME OVER)
        btnX = (gp.screenWidth  - btnW) / 2;
        btnY = (gp.screenHeight - btnH) / 2 + 50;

        kongtext_30 = new Font("Kongtext", Font.PLAIN, 30);
        kongtext_50 = new Font("Kongtext", Font.BOLD, 50);

        ObjectKey key = new ObjectKey();
        keyImage = key.image;
        SuperObject heart = new ObjectHeart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
        messageCounter = 0;
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.playState) {
            drawPlayUI(g2);
        }
        else if (gp.gameState == gp.pauseState) {
            drawPauseScreen(g2);
            drawPlayerLife(g2);
        }
        else if (gp.gameState == gp.gameOverState) {
            drawDeathScreen(g2);
        }
    }

    private void drawPlayUI(Graphics2D g2) {
        if (gameFinished) {
            g2.setFont(kongtext_50);
            g2.setColor(Color.YELLOW);
            String text = "WIN! WIN! WIN!";
            int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth / 2 - textLength/2;
            int y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);
        } else {
            g2.setFont(kongtext_30);
            g2.setColor(Color.WHITE);
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x" + gp.player.hasKey, 74, 65);

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(15f));
                g2.drawString(message, gp.tileSize/2, gp.tileSize * 5);
                messageCounter++;
                if (messageCounter > 120) {
                    messageOn = false;
                    messageCounter = 0;
                }
            }
            drawPlayerLife(g2);
        }
    }

    private void drawPauseScreen(Graphics2D g2) {
        g2.setFont(kongtext_30.deriveFont(60f));
        g2.setColor(Color.WHITE);
        String text = "PAUSED";
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - textLength/2;
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    private void drawDeathScreen(Graphics2D g2) {
        // 1) GAME OVER text
        g2.setFont(kongtext_30.deriveFont(60f));
        g2.setColor(Color.RED);
        String text = "GAME OVER";
        int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - textLength/2;
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

        // 2) Restart button
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(btnX, btnY, btnW, btnH, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(btnX, btnY, btnW, btnH, 15, 15);

        String bt = "RESTART";
        int btW = g2.getFontMetrics().stringWidth(bt);
        int btH = g2.getFontMetrics().getAscent();
        g2.drawString(bt, btnX + (btnW - btW)/2, btnY + (btnH + btH)/2 - 6);
    }

    // Called by GamePanel's mouse listener
    public boolean isRestartArea(int mx, int my) {
        return mx >= btnX && mx <= btnX + btnW
                && my >= btnY && my <= btnY + btnH;
    }

    public void drawPlayerLife(Graphics2D g2) {
        int x = gp.tileSize/2;
        int y = gp.tileSize/2 + (gp.tileSize * 10);
        int i = 0;
        // blank hearts
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++; x += gp.tileSize;
        }
        // current life
        x = gp.tileSize/2; y = gp.tileSize/2 + (gp.tileSize * 10);
        i = 0;
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++; x += gp.tileSize;
        }
    }
}
