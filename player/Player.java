package player;

import board.Board;
import com.sun.source.tree.Tree;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Player {

    private int score;
    private Point pos;
    private ArrayList<Point> body;
    private Point tail;
    private Direction direction;
    private Board board;

    public Player(final int xStart, final int yStart, final Board board) {
        // initialize the state
        pos = new Point(xStart, yStart);
        score = 0;
        body = new ArrayList<Point>();
        body.add(pos);
        tail = pos;
        direction = Direction.RIGHT;
        this.board = board;
    }

    public void move() {
        if (!validateMove()) {
            board.endGame();
        }
    }

    public boolean validateMove() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // prevent the player from moving off the edge of the board sideways
        if (pos.x < 0) {
            pos.x = 0;
        } else if (pos.x >= Board.COLUMNS) {
            pos.x = Board.COLUMNS - 1;
        }
        // prevent the player from moving off the edge of the board vertically
        if (pos.y < 0) {
            pos.y = 0;
        } else if (pos.y >= Board.ROWS) {
            pos.y = Board.ROWS - 1;
        }

        return true;
    }


    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
    }

    public Point getPos() {
        return pos;
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.setColor(new Color(0, 255, 0));
        for (Point p : body) {
            g.fillRect(p.x * Board.TILE_SIZE, p.y * Board.TILE_SIZE, Board.TILE_SIZE, Board.TILE_SIZE);
        }


    }

    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();

        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if (key == KeyEvent.VK_UP) {
            pos.translate(0, -1);
        }
        if (key == KeyEvent.VK_RIGHT) {
            pos.translate(1, 0);
        }
        if (key == KeyEvent.VK_DOWN) {
            pos.translate(0, 1);
        }
        if (key == KeyEvent.VK_LEFT) {
            pos.translate(-1, 0);
        }
    }

    public void endGame() {

    }
}
