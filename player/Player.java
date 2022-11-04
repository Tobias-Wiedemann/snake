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

import static board.Board.COLUMNS;
import static board.Board.ROWS;

public class Player {

    private int score;
    private Point pos;
    private final Board board;
    private final Queue<Point> body;
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
        validateMove();

        // Move the neck up
        neck = head;
        // Move the head up
        // Move the stuff through the walls
        switch (neck) {
            case UP -> {
                if (pos.y <= 0) {
                    pos = new Point(pos.x, ROWS);
                } else {
                    pos = new Point(pos.x, pos.y - 1);
                }
                body.offer(pos);
            }
            case DOWN -> {
                if (pos.y + 1 >= ROWS) {
                    pos = new Point(pos.x, 0);
                } else {
                    pos = new Point(pos.x, pos.y + 1);
                }
                body.offer(pos);
            }
            case RIGHT -> {
                if (pos.x + 1 >= COLUMNS) {
                    pos = new Point(0, pos.y);
                } else {
                    pos = new Point(pos.x + 1, pos.y);
                }
                body.offer(pos);
            }
            case LEFT -> {
                if (pos.x <= 0) {
                    pos = new Point(COLUMNS - 1, pos.y);
                } else {
                    pos = new Point(pos.x - 1, pos.y);
                }
                body.offer(pos);
            }
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
            if (pos.x == p.x && pos.y == p.y) {
                board.eatApple(new Point(pos.x, pos.y));
                appleEaten = true;
                board.createApple();
            }
        }
        if (!appleEaten) {
            body.poll();
        }
    }

    public void validateMove() {
        // this gets called once every tick, before the repainting process happens.
        // so we can do anything needed in here to update the state of the player.

        // next field to be moved on
        Point nextPos;
        switch (head) {
            case UP -> {
                nextPos = new Point(pos.x, pos.y - 1);
            }
            case DOWN -> {
                nextPos = new Point(pos.x, pos.y + 1);
            }
            case RIGHT -> {
                nextPos = new Point(pos.x + 1, pos.y);
            }
            case LEFT -> {
                nextPos = new Point(pos.x - 1, pos.y);
            }
            default -> throw new IllegalArgumentException("snake is directionless");
        }


/*
        // prevent the player from moving off the edge of the board sideways
        if (nextPos.x < 0) {
            // nextPos..x = 0;
            board.endGame();
        } else if (nextPos.x >= COLUMNS) {
            // nextPos..x = Board.COLUMNS - 1;
            board.endGame();
        }
        // prevent the player from moving off the edge of the board vertically
        if (nextPos.y < 0) {
            // nextPos..y = 0;
            board.endGame();
        } else if (nextPos.y >= ROWS) {
            // nextPos..y = Board.ROWS - 1;
            board.endGame();
        }
 */

        // prevent biting yourself
        if (body.contains(nextPos)) {
            board.endGame();
        }

    }


    public String getScore() {
        return String.valueOf(score);
    }

    public void addScore(int amount) {
        score += amount;
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
