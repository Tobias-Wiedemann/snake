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
    private fieldType[][] board;
    final private ArrayList<Point> apples;
    private Random random;
    public Board(final int rows, final int columns, final int delay, Color color) {
        ROWS = rows;
        COLUMNS = columns;
        DELAY = delay;
        gameOver = false;
        board = new fieldType[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = fieldType.EMPTY;
            }
        }
        apples = new ArrayList<Point>();
        random = new Random(SEED);
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));
        // set the game board background color
        setBackground(color);
        createApple();

        // initialize the game state
        player = new Player(COLUMNS / 2, ROWS / 2, this);

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

        // give the player points for collecting coins
        this.eatApple();

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
        int x = random.nextInt(ROWS);
        int y = random.nextInt(COLUMNS);
        while (board[x][y] != fieldType.EMPTY) {
            x = random.nextInt(ROWS);
            y = random.nextInt(COLUMNS);
            if (board[x][y] == fieldType.EMPTY) {
                apples.add(new Point(x, y));
                return;
            }
        }
        apples.add(new Point(x * TILE_SIZE, y * TILE_SIZE));
    }

    public void drawApples(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        for (Point p : apples) {
            g.fillRect(p.x, p.y, TILE_SIZE, TILE_SIZE);
        }
    }

    public void eatApple() {

    }

    public void endGame() {
        gameOver = true;
    }


}
