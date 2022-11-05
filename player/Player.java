package player;

import board.Board;
import board.fieldType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static board.Board.COLUMNS;
import static board.Board.ROWS;

public class Player {

    protected int score;
    protected DirectionPoint pos;
    protected final Board board;
    protected final Queue<DirectionPoint> body;
    protected Direction head;
    protected Direction neck;

    public Player(final int xStart, final int yStart, final Board board) {
        // initialize the state
        head = Direction.RIGHT;
        pos = new DirectionPoint(new Point(xStart, yStart), head);
        score = 0;
        body = new LinkedList<DirectionPoint>();
        body.offer(pos);
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
                if (pos.point().y <= 0) {
                    pos = new DirectionPoint(new Point(pos.point().x, ROWS - 1), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.point().x, pos.point().y - 1), head);
                }
            }
            case DOWN -> {
                if (pos.point().y + 1 >= ROWS) {
                    pos = new DirectionPoint(new Point(pos.point().x, 0), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.point().x, pos.point().y + 1), head);
                }
            }
            case RIGHT -> {
                if (pos.point().x + 1 >= COLUMNS) {
                    pos = new DirectionPoint(new Point(0, pos.point().y), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.point().x + 1, pos.point().y), head);
                }
            }
            case LEFT -> {
                if (pos.point().x <= 0) {
                    pos = new DirectionPoint(new Point(COLUMNS - 1, pos.point().y), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.point().x - 1, pos.point().y), head);
                }
            }
            default -> throw new IllegalArgumentException("Snake does not face any direction :(");
        }
        body.offer(pos);
        // Move the snake up and manage apples
        moveOnTo();
    }
    
    protected void moveOnTo() {
        boolean appleEaten = false;
        List<Point> appleListCopy = board.getApples().stream().toList();
        for (Point p : appleListCopy) {
            assert(!body.isEmpty());
            if (pos.point().x == p.x && pos.point().y == p.y) {
                board.eatApple(new Point(pos.point().x, pos.point().y));
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
                nextPos = new Point(pos.point().x, pos.point().y - 1);
            }
            case DOWN -> {
                nextPos = new Point(pos.point().x, pos.point().y + 1);
            }
            case RIGHT -> {
                nextPos = new Point(pos.point().x + 1, pos.point().y);
            }
            case LEFT -> {
                nextPos = new Point(pos.point().x - 1, pos.point().y);
            }
            default -> throw new IllegalArgumentException("snake is directionless");
        }


/*
        // prevent the player from moving off the edge of the board sideways
        if (nextpos.point().x < 0) {
            // nextPos..x = 0;
            board.endGame();
        } else if (nextpos.point().x >= COLUMNS) {
            // nextPos..x = Board.COLUMNS - 1;
            board.endGame();
        }
        // prevent the player from moving off the edge of the board vertically
        if (nextpos.point().y < 0) {
            // nextPos..y = 0;
            board.endGame();
        } else if (nextpos.point().y >= ROWS) {
            // nextPos..y = Board.ROWS - 1;
            board.endGame();
        }
 */

        // prevent biting yourself
        if (body.stream().map(DirectionPoint::point).toList().contains(nextPos)) {
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
        // pos.point().x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.setColor(new Color(0, 255, 0));
        for (DirectionPoint p : body) {
            g.fillRect(p.point().x * Board.TILE_SIZE, p.point().y * Board.TILE_SIZE, Board.TILE_SIZE, Board.TILE_SIZE);
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

    public Queue<DirectionPoint> getBody() {
        return body;
    }

    public void endGame() {

    }
}
