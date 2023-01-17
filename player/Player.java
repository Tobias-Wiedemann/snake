package player;

import board.Board;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static board.Board.COLUMNS;
import static board.Board.ROWS;
import app.App;

public class Player {

    protected int score;
    protected DirectionPoint pos;
    protected final Board board;
    protected final Queue<DirectionPoint> body;
    protected Direction head;
    protected Direction neck;
    protected DirectionPoint neckPiece;

    public Player(final int xStart, final int yStart, final Board board) {
        // initialize the state
        head = Direction.RIGHT;
        pos = new DirectionPoint(new Point(xStart, yStart), head);
        neckPiece = pos;
        score = 0;
        body = new LinkedList<DirectionPoint>();
        body.offer(pos);
        this.board = board;
    }

    public void move() {
        if (!validateMove()) {
            return;
        }

        // Move the neck up
        neck = head;
        neckPiece.setDirection(head);
        // Move the head up
        // Move the stuff through the walls
        switch (neck) {
            case UP -> {
                if (pos.getPoint().y <= 0) {
                    pos = new DirectionPoint(new Point(pos.getPoint().x, ROWS - 1), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.getPoint().x, pos.getPoint().y - 1), head);
                }
            }
            case DOWN -> {
                if (pos.getPoint().y + 1 >= ROWS) {
                    pos = new DirectionPoint(new Point(pos.getPoint().x, 0), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.getPoint().x, pos.getPoint().y + 1), head);
                }
            }
            case RIGHT -> {
                if (pos.getPoint().x + 1 >= COLUMNS) {
                    pos = new DirectionPoint(new Point(0, pos.getPoint().y), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.getPoint().x + 1, pos.getPoint().y), head);
                }
            }
            case LEFT -> {
                if (pos.getPoint().x <= 0) {
                    pos = new DirectionPoint(new Point(COLUMNS - 1, pos.getPoint().y), head);
                } else {
                    pos = new DirectionPoint(new Point(pos.getPoint().x - 1, pos.getPoint().y), head);
                }
            }
            default -> throw new IllegalArgumentException("Snake does not face any direction :(");
        }
        neckPiece = pos;
        body.offer(pos);
        // Move the snake up and manage apples
        moveOnTo();
    }
    
    protected void moveOnTo() {
        boolean appleEaten = false;
        List<Point> appleListCopy = board.getApples().stream().toList();
        for (Point p : appleListCopy) {
            assert(!body.isEmpty());
            if (pos.getPoint().x == p.x && pos.getPoint().y == p.y) {
                board.eatApple(new Point(pos.getPoint().x, pos.getPoint().y));
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

        // next field to be moved on
        Point nextPos;
        switch (head) {
            case UP -> {
                nextPos = new Point(pos.getPoint().x, pos.getPoint().y - 1);
            }
            case DOWN -> {
                nextPos = new Point(pos.getPoint().x, pos.getPoint().y + 1);
            }
            case RIGHT -> {
                nextPos = new Point(pos.getPoint().x + 1, pos.getPoint().y);
            }
            case LEFT -> {
                nextPos = new Point(pos.getPoint().x - 1, pos.getPoint().y);
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
        if (body.stream().map(DirectionPoint::getPoint).toList().contains(nextPos)
                && nextPos != body.peek().getPoint()) {
            board.endGame();
            return false;
        }
        return true;
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
            g.fillRect(p.getPoint().x * Board.TILE_SIZE, p.getPoint().y * Board.TILE_SIZE, Board.TILE_SIZE, Board.TILE_SIZE);
        }
    }

    public void keyPressed(KeyEvent e) {
        // every keyboard get has a certain code. get the value of that code from the
        // keyboard event so that we can compare it to KeyEvent constants
        int key = e.getKeyCode();

        // depending on which arrow key was pressed, we're going to move the player by
        // one whole tile for this input
        if (!board.isEndScreen()) {
            switch (neck) {
                case UP, DOWN -> {
                    if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
                        head = Direction.RIGHT;
                    }
                    if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
                        head = Direction.LEFT;
                    }
                }
                case RIGHT, LEFT-> {
                    if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                        head = Direction.UP;
                    }
                    if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                        head = Direction.DOWN;
                    }
                }
                default -> throw new IllegalArgumentException("Snake's neck does not face any direction :(");
            }
        } else {
        }
    }

    public Queue<DirectionPoint> getBody() {
        return body;
    }

}
