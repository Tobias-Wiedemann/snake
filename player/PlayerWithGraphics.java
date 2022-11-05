package player;

import board.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class PlayerWithGraphics extends Player {

    protected BufferedImage snake_head_up;
    protected BufferedImage snake_head_down;
    protected BufferedImage snake_head_left;
    protected BufferedImage snake_head_right;
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
    protected BufferedImage snake_egg;

    public PlayerWithGraphics(final int xStart, final int yStart, final Board board) {
        super(xStart, yStart, board);
        loadImages();
    }

    protected void loadImages() {
        // HEAD
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_head_up = ImageIO.read(new File("images/snake_head_up.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_head_down = ImageIO.read(new File("images/snake_head_down.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_head_left = ImageIO.read(new File("images/snake_head_left.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_head_right = ImageIO.read(new File("images/snake_head_right.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // BODY
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_body_horizontal = ImageIO.read(new File("images/snake_body_horizontal.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_body_vertical = ImageIO.read(new File("images/snake_body_vertical.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // BODY CORNERS
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_corner_right_up = ImageIO.read(new File("images/snake_corner_right_up.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_corner_right_down = ImageIO.read(new File("images/snake_corner_right_down.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_corner_left_up = ImageIO.read(new File("images/snake_corner_left_up.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_corner_left_down = ImageIO.read(new File("images/snake_corner_left_down.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // TAIL
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_tail_downwards = ImageIO.read(new File("images/snake_tail_downwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_tail_upwards = ImageIO.read(new File("images/snake_tail_upwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_tail_leftwards = ImageIO.read(new File("images/snake_tail_leftwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_tail_rightwards = ImageIO.read(new File("images/snake_tail_rightwards.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
        // EGG
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            snake_egg = ImageIO.read(new File("images/snake_apfel.png"));
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
        for (DirectionPoint p : body) {
            if (p.equals(pos)) {
                g.drawImage(
                        snake_egg,
                        p.point().x * Board.TILE_SIZE,
                        p.point().y * Board.TILE_SIZE,
                        board
                );
            } else {
                g.fillRect(p.point().x * Board.TILE_SIZE, p.point().y * Board.TILE_SIZE, Board.TILE_SIZE, Board.TILE_SIZE);
            }
        }


    }




}
