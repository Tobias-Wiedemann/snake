package player;

import board.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class PlayerWithGraphics extends Player {

    protected BufferedImage snake_head_up;
    protected BufferedImage snake_head_dead_up_a;
    protected BufferedImage snake_head_dead_up_b;
    protected BufferedImage snake_head_down;
    protected BufferedImage snake_head_dead_down_a;
    protected BufferedImage snake_head_dead_down_b;
    protected BufferedImage snake_head_left;
    protected BufferedImage snake_head_dead_left_a;
    protected BufferedImage snake_head_dead_left_b;
    protected BufferedImage snake_head_right;
    protected BufferedImage snake_head_dead_right_a;
    protected BufferedImage snake_head_dead_right_b;
    protected BufferedImage snake_tongue_right;
    protected BufferedImage snake_tongue_up;
    protected BufferedImage snake_tongue_down;
    protected BufferedImage snake_tongue_left;
    protected BufferedImage snake_body_horizontal;
    protected BufferedImage snake_body_vertical;
    protected BufferedImage snake_corner_left_down;
    protected BufferedImage snake_corner_left_up;
    protected BufferedImage snake_corner_right_down;
    protected BufferedImage snake_corner_right_up;
    protected BufferedImage snake_tail_upwards;
    protected BufferedImage snake_tail_downwards;
    protected BufferedImage snake_tail_leftwards;
    protected BufferedImage snake_tail_rightwards;
    protected BufferedImage snake_egg_a;
    protected BufferedImage snake_egg_b;
    protected BufferedImage REAL_EGG;
    private boolean starAnimator;
    protected Random random;

    public PlayerWithGraphics(final int xStart, final int yStart, final Board board) {
        super(xStart, yStart, board);
        loadImages();
        starAnimator = false;
        random = new Random();
    }

    protected void loadImages() {
        // HEAD
        try {
            snake_head_up = ImageIO.read(new File("images/snake_head_up.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_down = ImageIO.read(new File("images/snake_head_down.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_left = ImageIO.read(new File("images/snake_head_left.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_right = ImageIO.read(new File("images/snake_head_right.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // Head deads
        try {
            snake_head_dead_up_a = ImageIO.read(new File("images/snake_head_dead_up_a.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_dead_up_b = ImageIO.read(new File("images/snake_head_dead_up_b.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_dead_down_a = ImageIO.read(new File("images/snake_head_dead_down_a.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_dead_down_b = ImageIO.read(new File("images/snake_head_dead_down_b.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_dead_left_a = ImageIO.read(new File("images/snake_head_dead_left_a.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_dead_left_b = ImageIO.read(new File("images/snake_head_dead_left_b.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_dead_right_a = ImageIO.read(new File("images/snake_head_dead_right_a.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_head_dead_right_b = ImageIO.read(new File("images/snake_head_dead_right_b.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // TONGUE
        try {
            snake_tongue_right = ImageIO.read(new File("images/snake_tongue_right.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_tongue_up = ImageIO.read(new File("images/snake_tongue_up.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_tongue_left = ImageIO.read(new File("images/snake_tongue_left.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_tongue_down = ImageIO.read(new File("images/snake_tongue_down.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }

        // BODY
        try {
            snake_body_horizontal = ImageIO.read(new File("images/snake_body_horizontal.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_body_vertical = ImageIO.read(new File("images/snake_body_vertical.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // BODY CORNERS
        try {
            snake_corner_right_up = ImageIO.read(new File("images/snake_corner_right_up.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_corner_right_down = ImageIO.read(new File("images/snake_corner_right_down.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_corner_left_up = ImageIO.read(new File("images/snake_corner_left_up.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_corner_left_down = ImageIO.read(new File("images/snake_corner_left_down.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // TAIL
        try {
            snake_tail_downwards = ImageIO.read(new File("images/snake_tail_downwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_tail_upwards = ImageIO.read(new File("images/snake_tail_upwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_tail_leftwards = ImageIO.read(new File("images/snake_tail_leftwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_tail_rightwards = ImageIO.read(new File("images/snake_tail_rightwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // EGG
        try {
            snake_egg_a = ImageIO.read(new File("images/snake_egg_a.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            snake_egg_b = ImageIO.read(new File("images/snake_egg_b.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            REAL_EGG = ImageIO.read(new File("images/THE_REAL_EGG.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void draw(Graphics g, ImageObserver observer) {
        // with the Point class, note that pos.getX() returns a double, but
        // pos.x reliably returns an int. https://stackoverflow.com/a/30220114/4655368
        // this is also where we translate board grid position into a canvas pixel
        // position by multiplying by the tile size.
        g.setColor(new Color(0, 255, 0));
        Direction directionOfTheLastPart = null;
        // Assuming that iterating over a queue starts at the queue head which is the snake tail
        for (DirectionPoint p : body) {
            // Snake size == 1 so egg
            if (p.getPoint().equals(pos.getPoint()) && body.size() == 1) {
                g.drawImage(
                        REAL_EGG,
                        p.getPoint().x * Board.TILE_SIZE,
                        p.getPoint().y * Board.TILE_SIZE,
                        board);
/*
                if (starAnimator) {
                    g.drawImage(
                            snake_egg_a,
                            p.getPoint().x * Board.TILE_SIZE,
                            p.getPoint().y * Board.TILE_SIZE,
                            board);
                    starAnimator = false;
                } else {
                    g.drawImage(
                            snake_egg_b,
                            p.getPoint().x * Board.TILE_SIZE,
                            p.getPoint().y * Board.TILE_SIZE,
                            board);
                    starAnimator = true;
                }
                */
                continue;
            } else if (p.getPoint().equals(pos.getPoint())) {
                // Head (head_deads in the conditionals)
                switch (p.getDirection()) {
                    case UP -> {
                        if (board.isGameOver()) {
                            if (starAnimator) {
                                g.drawImage(
                                        snake_head_dead_up_a,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = false;
                            } else {
                                g.drawImage(
                                        snake_head_dead_up_b,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = true;
                            }
                            g.drawImage(
                                    snake_tongue_up,
                                    p.getPoint().x * Board.TILE_SIZE,
                                    (p.getPoint().y - 1) * Board.TILE_SIZE,
                                    board);
                            continue;
                        }
                        g.drawImage(
                                snake_head_up,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        continue;
                    }
                    case DOWN -> {
                        if (board.isGameOver()) {
                            if (starAnimator) {
                                g.drawImage(
                                        snake_head_dead_down_a,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = false;
                            } else {
                                g.drawImage(
                                        snake_head_dead_down_b,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = true;
                            }
                            g.drawImage(
                                    snake_tongue_down,
                                    p.getPoint().x * Board.TILE_SIZE,
                                    (p.getPoint().y + 1) * Board.TILE_SIZE,
                                    board);
                            continue;
                        }
                        g.drawImage(
                                snake_head_down,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        continue;
                    }
                    case RIGHT -> {
                        if (board.isGameOver()) {
                            if (starAnimator) {
                                g.drawImage(
                                        snake_head_dead_right_a,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = false;
                            } else {
                                g.drawImage(
                                        snake_head_dead_right_b,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = true;
                            }
                            g.drawImage(
                                    snake_tongue_right,
                                    (p.getPoint().x + 1) * Board.TILE_SIZE,
                                    p.getPoint().y * Board.TILE_SIZE,
                                    board);
                            continue;
                        }
                        g.drawImage(
                                snake_head_right,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        continue;
                    }
                    case LEFT -> {
                        if (board.isGameOver()) {
                            if (starAnimator) {
                                g.drawImage(
                                        snake_head_dead_left_a,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = false;
                            } else {
                                g.drawImage(
                                        snake_head_dead_left_b,
                                        p.getPoint().x * Board.TILE_SIZE,
                                        p.getPoint().y * Board.TILE_SIZE,
                                        board);
                                starAnimator = true;
                            }
                            g.drawImage(
                                    snake_tongue_left,
                                    (p.getPoint().x - 1) * Board.TILE_SIZE,
                                    p.getPoint().y * Board.TILE_SIZE,
                                    board);
                            continue;
                        }
                        g.drawImage(
                                snake_head_left,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        continue;
                    }
                    default -> throw new IllegalArgumentException("Head is directionless");
                }
            }
            // Tail
            if (p == body.peek()) {
                switch (p.getDirection()) {
                    case UP -> {
                        g.drawImage(
                                snake_tail_upwards,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        directionOfTheLastPart = p.getDirection();
                        continue;
                    }
                    case DOWN -> {
                        g.drawImage(
                                snake_tail_downwards,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        directionOfTheLastPart = p.getDirection();
                        continue;
                    }
                    case RIGHT -> {
                        g.drawImage(
                                snake_tail_rightwards,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        directionOfTheLastPart = p.getDirection();
                        continue;
                    }
                    case LEFT -> {
                        g.drawImage(
                                snake_tail_leftwards,
                                p.getPoint().x * Board.TILE_SIZE,
                                p.getPoint().y * Board.TILE_SIZE,
                                board);
                        directionOfTheLastPart = p.getDirection();
                        continue;
                    }
                    default -> throw new IllegalArgumentException("Tail is directionless");
                }
            }
            // Body parts
            // Straight parts first, then corners
            if (p.getDirection() == directionOfTheLastPart) {
                // Horizontal check
                if (directionOfTheLastPart == Direction.RIGHT || directionOfTheLastPart == Direction.LEFT) {
                    g.drawImage(
                            snake_body_horizontal,
                            p.getPoint().x * Board.TILE_SIZE,
                            p.getPoint().y * Board.TILE_SIZE,
                            board);
                } else {
                    // Vertical
                    g.drawImage(
                            snake_body_vertical,
                            p.getPoint().x * Board.TILE_SIZE,
                            p.getPoint().y * Board.TILE_SIZE,
                            board);
                }
            } else if (p.getDirection() == Direction.UP && directionOfTheLastPart == Direction.LEFT
                    || p.getDirection() == Direction.RIGHT && directionOfTheLastPart == Direction.DOWN) {
                // Corner: RIGHT UP
                g.drawImage(
                        snake_corner_right_up,
                        p.getPoint().x * Board.TILE_SIZE,
                        p.getPoint().y * Board.TILE_SIZE,
                        board);
                directionOfTheLastPart = p.getDirection();
            } else if (p.getDirection() == Direction.UP && directionOfTheLastPart == Direction.RIGHT
                    || p.getDirection() == Direction.LEFT && directionOfTheLastPart == Direction.DOWN) {
                // Corner: LEFT UP
                g.drawImage(
                        snake_corner_left_up,
                        p.getPoint().x * Board.TILE_SIZE,
                        p.getPoint().y * Board.TILE_SIZE,
                        board);
                directionOfTheLastPart = p.getDirection();
            } else if (p.getDirection() == Direction.DOWN && directionOfTheLastPart == Direction.LEFT
                    || p.getDirection() == Direction.RIGHT && directionOfTheLastPart == Direction.UP) {
                // Corner: RIGHT DOWN
                g.drawImage(
                        snake_corner_right_down,
                        p.getPoint().x * Board.TILE_SIZE,
                        p.getPoint().y * Board.TILE_SIZE,
                        board);
                directionOfTheLastPart = p.getDirection();
            } else if (p.getDirection() == Direction.DOWN && directionOfTheLastPart == Direction.RIGHT
                    || p.getDirection() == Direction.LEFT && directionOfTheLastPart == Direction.UP) {
                // Corner: LEFT DOWN
                g.drawImage(
                        snake_corner_left_down,
                        p.getPoint().x * Board.TILE_SIZE,
                        p.getPoint().y * Board.TILE_SIZE,
                        board);
                directionOfTheLastPart = p.getDirection();
            } else {
                throw new IllegalArgumentException("Weird direction combination");
            }
        }

        if (random.nextInt(100) > 90) {
            switch (pos.getDirection()) {
                case RIGHT -> {
                    g.drawImage(
                            snake_tongue_right,
                            (pos.getPoint().x + 1) * Board.TILE_SIZE,
                            pos.getPoint().y * Board.TILE_SIZE,
                            board);
                }
                case LEFT -> {
                    g.drawImage(
                            snake_tongue_left,
                            (pos.getPoint().x - 1) * Board.TILE_SIZE,
                            pos.getPoint().y * Board.TILE_SIZE,
                            board);
                }
                case UP -> {
                    g.drawImage(
                            snake_tongue_up,
                            pos.getPoint().x * Board.TILE_SIZE,
                            (pos.getPoint().y - 1) * Board.TILE_SIZE,
                            board);
                }
                case DOWN -> {
                    g.drawImage(
                            snake_tongue_down,
                            pos.getPoint().x * Board.TILE_SIZE,
                            (pos.getPoint().y + 1) * Board.TILE_SIZE,
                            board);
                }
            }
        }
    }

}
