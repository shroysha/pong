package dev.shroysha.pong.view;


import dev.shroysha.pong.controller.PongGameController;
import dev.shroysha.pong.model.PongGame;
import dev.shroysha.pong.model.PongPlayer;
import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PongFrame extends JFrame {


    private final WelcomePanel wp = new WelcomePanel();
    @Getter
    private final PongPanel pp = new PongPanel();
    private final JPanel contentPanel = new JPanel(new BorderLayout());

    public PongFrame() {
        super("Pong");
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        contentPanel.add(wp, BorderLayout.CENTER);
        this.add(contentPanel, BorderLayout.CENTER);
        this.setSize(1100, 800);
        this.setResizable(false);
        setFocusable(true);
        this.addKeyListener(new PongGameController());
    }

    public void playPong() {
        contentPanel.removeAll();
        contentPanel.add(pp, BorderLayout.CENTER);
        contentPanel.revalidate();
    }

    public static class PongPanel extends JPanel {

        private final PongGameController gameController = new PongGameController();

        public PongPanel() {
            super();
            init();
            new RefreshThread().start();
        }

        private void init() {
            this.setBackground(Color.black);
            this.setPreferredSize(new Dimension(1100, 800));
            setFocusable(true);
        }

        private void drawGame(Graphics2D g) {
            g.setColor(Color.white);

            PongGame pongGame = gameController.getPongGame();
            g.draw(pongGame.getBall());
            g.draw(pongGame.getBumper1());
            g.draw(pongGame.getBumper2());
            g.draw(pongGame.getGoal1());
            g.draw(pongGame.getGoal2());
            g.draw(pongGame.getTopBoundary());
            g.draw(pongGame.getBottomBoundary());

            PongPlayer player1 = pongGame.getPlayer1();
            String player1Score = player1.getName() + ": " + player1.getPoints();
            PongPlayer player2 = pongGame.getPlayer2();
            String player2Score = player2.getName() + ": " + player2.getPoints();

            g.setFont(g.getFont().deriveFont(26.0f));
            g.drawString("" + player1Score, 20, pongGame.getBottomBoundary().y + 60);
            g.drawString("" + player2Score, 20, pongGame.getBottomBoundary().y + 100);

            if (pongGame.hasWinner()) {
                g.drawString(pongGame.getWinner().getName() + " wins!!!", 500, 500);
            }

        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            drawGame(g2);
        }

        private class RefreshThread extends Thread {

            public void run() {
                //noinspection InfiniteLoopStatement
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PongPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    repaint();
                }

            }

        }
    }

    public class WelcomePanel extends JPanel {

        private final JLabel gameLabel = new JLabel("PONG");
        private final JButton startButton = new JButton("Play");

        public WelcomePanel() {
            super(new GridLayout(3, 1));
            init();
        }

        private void init() {
            this.setBorder(new EmptyBorder(30, 30, 30, 30));

            gameLabel.setFont(gameLabel.getFont().deriveFont(28.0f));
            gameLabel.setHorizontalAlignment(SwingConstants.CENTER);

            startButton.setFont(startButton.getFont().deriveFont(26.0f));

            this.add(gameLabel);
            this.add(startButton);

            startButton.addActionListener(event -> PongFrame.this.playPong());
        }

    }
}
