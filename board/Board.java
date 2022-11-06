package board;

import player.DirectionPoint;
import player.Player;
import player.PlayerWithGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public abstract class Board extends JPanel implements ActionListener, KeyListener {

    public static final int SEED = 43;
    public static final int TILE_SIZE = 25;
    public static int ROWS;
    public static int COLUMNS;
    protected Timer timer;
    protected Player player;
    private boolean gameOver;
    protected final ArrayList<Point> apples;
    private final Random random;
    protected final String difficulty;
    protected static int SCORE_HEIGHT;
    public Board(final int rows, final int columns, Color color, String difficulty) {
        ROWS = rows;
        COLUMNS = columns;
        SCORE_HEIGHT = 3;
        gameOver = false;
        apples = new ArrayList<Point>();
        random = new Random(SEED);
        this.difficulty = difficulty;
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * (ROWS + SCORE_HEIGHT)));
        // set the game board background color
        setBackground(color);

        // initialize the game state
        player = new PlayerWithGraphics(COLUMNS / 2, ROWS / 2, this);
        createApple();

        // this timer will call the actionPerformed() method every DELAY ms
        switch (Difficulty.valueOf(difficulty)) {
            case EASY -> timer = new Timer(400, this);
            case MEDIUM -> timer = new Timer(200, this);
            case HARD -> timer = new Timer(150, this);
            case EXTREME -> timer = new Timer(100, this);
            case GIGAEXTREME -> timer = new Timer(75, this);
            case HYPAEXTREME -> timer = new Timer(50, this);
            default -> throw new IllegalArgumentException("Invalid difficulty setting");
        }
        timer.start();

    }

    public void actionPerformed(ActionEvent e) {
        // This is the game loop

        // this method is called by the timer every DELAY ms

        if (!gameOver) {
            player.move();
        }

        // calling repaint() will trigger paintComponent() to run again,
        // which will refresh/redraw the graphics.
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // when calling g.drawImage() we can use "this" for the ImageObserver
        // because Component implements the ImageObserver interface, and JPanel
        // extends from Component. So "this" Board instance, as a Component, can
        // react to imageUpdate() events triggered by g.drawImage()

        // draw our graphics.
        this.drawBackground(g);
        drawApples(g);
        drawScore(g);
        player.draw(g, this);

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        // this is not used but must be defined as part of the KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    public void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, TILE_SIZE * COLUMNS, TILE_SIZE * ROWS);
        g.setColor(new Color(124, 137, 150, 255));
        g.fillRect(0, TILE_SIZE * ROWS, TILE_SIZE * COLUMNS, TILE_SIZE * SCORE_HEIGHT);
    }

    public void drawScore(Graphics g) {
        // set the text to be displayed
        String playersScore;
        if (player.getScore().equals("1")) {
            playersScore = player.getScore() + " Apple";
        } else {
            playersScore = player.getScore() + " Apples";
        }
        String yourScoreText = "Current Score:";
        String highScoreText = "Highscore:";
        String exampleHighScore = "69 Apples";
        String difficultyText = "Difficulty:";
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(0, 0, 0));
        g2d.setFont(new Font("Lato", Font.BOLD, 20));
        // draw the score in the bottom center of the screen
        // https://stackoverflow.com/a/27740330/4655368
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        Rectangle scoreRect = new Rectangle(0, TILE_SIZE * ROWS + TILE_SIZE / 2, TILE_SIZE * COLUMNS, TILE_SIZE);
        // determine the x coordinate for the text

        //int scoreRectX = scoreRect.x + (scoreRect.width / 3 - metrics.stringWidth(playersScore));
        int scoreRectX = TILE_SIZE;
        int f = metrics.stringWidth(highScoreText);
        int highscoreX = scoreRect.x + (scoreRect.width / 2 - metrics.stringWidth(highScoreText) / 2);
        int difficultyX = scoreRect.x + (scoreRect.width - TILE_SIZE - Math.max(metrics.stringWidth(difficultyText), metrics.stringWidth(difficulty)));
        // determine the y coordinate for the text
        // (note we add the ascent, as in java 2d 0 is top of the screen)
        int scoreRectY = scoreRect.y + ((scoreRect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        g2d.drawString(yourScoreText, scoreRectX, scoreRectY);
        g2d.drawString(playersScore, scoreRectX, scoreRectY + TILE_SIZE);
        g2d.drawString(highScoreText, highscoreX, scoreRectY);
        g2d.drawString(exampleHighScore, highscoreX, scoreRectY + TILE_SIZE);
        g2d.drawString(difficultyText, difficultyX, scoreRectY);
        g2d.drawString(difficulty, difficultyX, scoreRectY + TILE_SIZE);
    }

    public void createApple() {
        int x;
        int y;
        while(true) {
            x = random.nextInt(COLUMNS - 1);
            y = random.nextInt(ROWS - 1);
            // if this stays false then its valid
            boolean invalidSpotFlag = false;
            for (DirectionPoint p : player.getBody()) {
                if (p.getPoint().x == x && p.getPoint().y == y) {
                    x = random.nextInt(COLUMNS - 1);
                    y = random.nextInt(ROWS - 1);
                    invalidSpotFlag = true;
                    break;
                }
            }
            if (!invalidSpotFlag) {
                break;
            }
        }
        // Relative coordinates, 0 <= x < rows, 0 <= y < columns
        apples.add(new Point(x, y));
    }

    public void drawApples(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        for (Point p : apples) {
            g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
    }

    public void endGame() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public java.util.List<Point> getApples() {
        return apples;
    }

    public void eatApple(Point p) {
        java.util.List<Point> appleCopy = apples.stream().toList();
        for (Point t : appleCopy) {
            if (t.equals(p)) {
                apples.remove(t);
                player.addScore(1);
            }
        }
    }

}
