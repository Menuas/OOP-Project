package ui;

import main.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A stand-alone panel that displays "GAME OVER" and a Restart button.
 */
public class GameOverPanel extends JPanel {
    private final JFrame parentFrame;

    public GameOverPanel(JFrame frame) {
        this.parentFrame = frame;

        // Use absolute positioning so we can center things easily
        setLayout(null);
        setBackground(Color.BLACK);

        // Restart JButton
        JButton restartBtn = new JButton("RESTART");
        restartBtn.setFocusable(false);
        restartBtn.setFont(new Font("Arial", Font.BOLD, 24));

        // Size & position: 200×60, centered
        int btnW = 200, btnH = 60;
        int x = (parentFrame.getWidth()  - btnW) / 2;
        int y = (parentFrame.getHeight() - btnH) / 2 + 50;
        restartBtn.setBounds(x, y, btnW, btnH);

        restartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove this panel, re-create the GamePanel, and start again
                parentFrame.getContentPane().removeAll();
                GamePanel gp = new GamePanel();
                parentFrame.add(gp);
                parentFrame.pack();
                gp.setupGame();
                gp.startGameThread();
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        add(restartBtn);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the big red "GAME OVER" text
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        g2.setColor(Color.RED);
        String msg = "GAME OVER";
        FontMetrics fm = g2.getFontMetrics();
        int textW = fm.stringWidth(msg);
        int x = (getWidth() - textW) / 2;
        int y = getHeight() / 2;
        g2.drawString(msg, x, y);
    }
}
