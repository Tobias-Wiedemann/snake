package board;

import player.Player;
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

    public static final int SEED = 42;
    protected int DELAY;
    public static final int TILE_SIZE = 25;
    public static int ROWS;
    public static int COLUMNS;
    protected Timer timer;
    protected Player player;
    private boolean gameOver;
    private final ArrayList<Point> apples;
    private final Random random;
    public Board(final int rows, final int columns, final int delay, Color color) {
        ROWS = rows;
        COLUMNS = columns;
        DELAY = delay;
        gameOver = false;
        apples = new ArrayList<Point>();
        random = new Random(SEED);
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color
        setBackground(color);

        // initialize the game state
        player = new Player(COLUMNS / 2, ROWS / 2, this);
        createApple();

        // this timer will call the actionPerformed() method every DELAY ms
        timer = new Timer(DELAY, this);
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
    }

    public void drawScore(Graphics g) {

    }

    public void createApple() {
        int x;
        int y;
        while(true) {
            x = random.nextInt(ROWS);
            y = random.nextInt(COLUMNS);
            boolean validSpotFlag = false;
            for (Point p : player.getBody()) {
                if (p.x == x && p.y == y) {
                    x = random.nextInt(ROWS);
                    y = random.nextInt(COLUMNS);
                    validSpotFlag = true;
                    break;
                }
            }
            if (validSpotFlag) {
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

    public java.util.List<Point> getApples() {
        return apples;
    }

    public void eatApple(Point p) {
        java.util.List<Point> appleCopy = apples.stream().toList();
        for (Point t : appleCopy) {
            if (t.equals(p)) {
                apples.remove(t);
            }
        }
    }
}
