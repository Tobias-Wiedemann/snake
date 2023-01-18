package board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardWithGraphics extends Board {

    private BufferedImage image;
    public BoardWithGraphics(String difficulty) {
        super(14, 20, difficulty);
        loadImage();
    }

    private void loadImage() {
        try {
            // you can use just the filename if the image file is in your
            // project folder, otherwise you need to provide the file path.
            image = ImageIO.read(new File("images/snake_apfel.png"));
        } catch (IOException exc) {
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    public void drawApples(Graphics g) {
        for (Point p : apples) {
            g.drawImage(
                    image,
                    p.x * Board.TILE_SIZE,
                    p.y * Board.TILE_SIZE,
                    this
            );
        }
    }

}
