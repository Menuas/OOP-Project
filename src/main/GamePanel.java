package main;

import entity.Player;
//import entity.Zombie;
import object.SuperObject;
import tile.TileManager;
import ui.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale            = 3;
    public final int tileSize   = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth  = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD MAP SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth  = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    // FPS
    int fps = 60;

    // GAME COMPONENTS
    public TileManager tileM      = new TileManager(this);
    public KeyHandler keyH        = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter    = new AssetSetter(this);
    public UI ui                  = new UI(this);
    public EventHandler eHandler  = new EventHandler(this);
    public Player player          = new Player(this, keyH);
//    public Zombie zombie          = new Zombie(this, 20, 20);  // ← uncommented
    public SuperObject[] obj      = new SuperObject[10];

    // GAME STATE
    public int gameState;
    public final int playState     = 1;
    public final int pauseState    = 2;
    public final int gameOverState = 3;

    private Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        // Mouse listener for Restart button
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gameState == gameOverState) {
                    int mx = e.getX();
                    int my = e.getY();
                    if (ui.isRestartArea(mx, my)) {
                        restartApp();
                    }
                }
            }
        });
    }

    public void setupGame() {
        aSetter.setObject();
        player.setDefaultValues();
        gameState = playState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        gameThread = null;
    }

    private void restartApp() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.dispose();
        Main.main(new String[0]);
    }

    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / fps;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remaining = (nextDrawTime - System.nanoTime()) / 1_000_000.0;
                if (remaining < 0) remaining = 0;
                Thread.sleep((long) remaining);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (gameState == playState) {
            if (ui.gameFinished) {
                ui.gameFinished = true;
                stopGame();
                return;
            }

            player.update();
//            zombie.update();

            if (player.life <= 0) {
                gameState = gameOverState;
                stopGame();
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == gameOverState) {
            ui.draw(g2);
        } else {
            tileM.draw(g2);
            for (SuperObject o : obj) {
                if (o != null) o.draw(g2, this);
            }
            player.draw(g2);
//            zombie.draw(g2);
            ui.draw(g2);
        }

        g2.dispose();
    }
}
