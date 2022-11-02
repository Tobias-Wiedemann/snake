package player;

import board.Board;
import board.fieldType;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Player {

    private int score;
    private Point pos;
    private Board board;
    private Queue<Point> body;
    private Direction head;
    private Direction neck;

    public Player(final int xStart, final int yStart, final Board board) {
        // initialize the state
        pos = new Point(xStart, yStart);
        score = 0;
        body = new LinkedList<Point>();
        body.offer(pos);
        head = Direction.RIGHT;
        this.board = board;
    }

    public void move() {
        if (!validateMove()) {
            board.endGame();
        }

        // Move the neck up
        neck = head;
        // Move the head up
        assert(!body.isEmpty());
        switch (neck) {
            case UP -> body.offer(new Point(body.peek().x, body.peek().y - 1));
            case DOWN -> body.offer(new Point(body.peek().x, body.peek().y + 1));
            case RIGHT -> body.offer(new Point(body.peek().x + 1, body.peek().y));
            case LEFT -> body.offer(new Point(body.peek().x - 1, body.peek().y));
            default -> throw new IllegalArgumentException("Snake does not face any direction :(");
        }
        // Move the snake up and manage apples
        moveOnTo();
    }
    
    private void moveOnTo() {
        boolean appleEaten = false;
        List<Point> appleListCopy = board.getApples().stream().toList();
        for (Point p : appleListCopy) {
            assert(!body.isEmpty());
            if (body.peek().x == p.x && body.peek().y == p.y) {
                board.eatApple(new Point(body.peek().x, body.peek().y));
                appleEaten = true;
                board.createApple();
            }
        }
        if (!appleEaten) {
            body.poll();
        }
    }

    public boolean validateMove() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // prevent the player from moving off the edge of the board sideways
        assert(!body.isEmpty());
        if (body.peek().x < 0) {
            body.peek().x = 0;
        } else if (body.peek().x >= Board.COLUMNS) {
            body.peek().x = Board.COLUMNS - 1;
        }
        // prevent the player from moving off the edge of the board vertically
        if (body.peek().y < 0) {
            body.peek().y = 0;
        } else if (body.peek().y >= Board.ROWS) {
            body.peek().y = Board.ROWS - 1;
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
        switch (neck) {
            case UP, DOWN -> {
                if (key == KeyEvent.VK_RIGHT) {
                    head = Direction.RIGHT;
                }
                if (key == KeyEvent.VK_LEFT) {
                    head = Direction.LEFT;
                }
            }
            case RIGHT, LEFT-> {
                if (key == KeyEvent.VK_UP) {
                    head = Direction.UP;
                }
                if (key == KeyEvent.VK_DOWN) {
                    head = Direction.DOWN;
                }
            }
            default -> throw new IllegalArgumentException("Snake's neck does not face any direction :(");
        }
    }

    public Queue<Point> getBody() {
        return body;
    }

    public void endGame() {

    }
}
