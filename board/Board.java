package board;

import app.App;
import player.DirectionPoint;
import player.Player;
import player.PlayerWithGraphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public abstract class Board extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    private int x,y;
    private String str;
    public final static String highscoreFileName = "highscores.txt";
    public static int SEED = 42;
    public static final int TILE_SIZE = 25;
    public static final int LEFT_X_RESTART_BUTTON = 150;
    public static final int RIGHT_X_RESTART_BUTTON = 245;
    public static final int TOP_Y_RESTART_BUTTON = 235;
    public static final int BOTTOM_Y_RESTART_BUTTON = 255;
    public static final int LEFT_X_MENU_BUTTON = 280;
    public static final int RIGHT_X_MENU_BUTTON = 440;
    public static final int TOP_Y_MENU_BUTTON = 235;
    public static final int BOTTOM_Y_MENU_BUTTON = 255;
    public static int ROWS;
    public static int COLUMNS;
    protected Timer timer;
    protected Player player;
    private boolean gameOver;
    protected final ArrayList<Point> apples;
    private final Random random;
    protected final String difficulty;
    protected static int SCORE_HEIGHT;
    protected boolean endScreen;
    protected boolean restartButtonActive;
    protected boolean menuButtonActive;



    public Board(final int rows, final int columns, String difficulty) {
        ROWS = rows;
        COLUMNS = columns;
        SCORE_HEIGHT = 3;
        gameOver = false;
        endScreen = false;
        restartButtonActive = false;
        menuButtonActive = false;
        apples = new ArrayList<Point>();
        Date d = new Date();
        SEED = (int) d.getTime() % 1000;
        random = new Random(SEED);
        this.difficulty = difficulty;
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * (ROWS + SCORE_HEIGHT)));
        // set the game board background color

        // initialize the game state
        player = new PlayerWithGraphics(COLUMNS / 2, ROWS / 2, this);
        createApple();

        // this timer will call the actionPerformed() method every DELAY ms
        switch (Difficulty.valueOf(difficulty)) {
            case EASY -> timer = new Timer(400, this);
            case MEDIUM -> timer = new Timer(200, this);
            case HARD -> timer = new Timer(150, this);
            case EXTREME -> timer = new Timer(100, this);
            case GIGAEXTREME -> timer = new Timer(75, this);
            case HYPAEXTREME -> timer = new Timer(50, this);
            default -> throw new IllegalArgumentException("Invalid difficulty setting");
        }
        timer.start();
        str = " ";
        App.easyScore = 0;
        App.mediumScore = 0;
        App.hardScore = 0;
        App.extremeScore = 0;
        App.gigaextremeScore = 0;
        App.hypaextremeScore = 0;
        updateHighscores();
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

        // GameOver message
        if (gameOver) {
            drawEndScreen(g);
        }

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
        if (!gameOver) {
            // Player is only involved when game is running
            player.keyPressed(e);
        } else {
            // EndScreen shit
            int key = e.getKeyCode();

            // If nothing is selected yet
            if (!isMenuButtonActive() && !isRestartButtonActive()) {
                setRestartButton(true);
                return;
            }

            // Moving selected button
            if (key == KeyEvent.VK_LEFT
                    || key == KeyEvent.VK_A || key == KeyEvent.VK_RIGHT
                    || key == KeyEvent.VK_D) {
                if (isRestartButtonActive()) {
                    setRestartButton(false);
                    setMenuButton(true);
                } else {
                    setRestartButton(true);
                    setMenuButton(false);
                }

            }

            if (isRestartButtonActive() && key == KeyEvent.VK_ENTER) {
                App.initGame(App.getGraphics(), App.getDifficulty());
            }

            if (isMenuButtonActive() && key == KeyEvent.VK_ENTER) {
                App.close();
                App.initMenu();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
    }

    public void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, TILE_SIZE * COLUMNS, TILE_SIZE * ROWS);
        g.setColor(new Color(124, 137, 150, 255));
        g.fillRect(0, TILE_SIZE * ROWS, TILE_SIZE * COLUMNS, TILE_SIZE * SCORE_HEIGHT);
    }

    public void drawEndScreen(Graphics g) {
        String gameOverString = "Game Over";
        String restartString = "Restart?";
        String menuString = "Back to Menu";
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(124, 137, 150));
        g2d.setFont(new Font("Lato", Font.BOLD, 20));
        // draw the score in the bottom center of the screen
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        Rectangle gameOverRect = new Rectangle(0, TILE_SIZE * ROWS / 2, TILE_SIZE * COLUMNS, TILE_SIZE * 3);
        g2d.setFont(new Font("Lato", Font.BOLD, 45));
        int gameOverStringX = gameOverRect.width / 2 - metrics.stringWidth(gameOverString) / 2 - TILE_SIZE * 2;
        int gameOverStringY = gameOverRect.y;
        int restartMenuStringY = gameOverRect.y + TILE_SIZE * 2;

        g2d.drawString(gameOverString, gameOverStringX, gameOverStringY);


        if (restartButtonActive) {
            g2d.setFont(new Font("Lato", Font.BOLD, 24));
            int restartStringX = COLUMNS * TILE_SIZE / 2 - metrics.stringWidth(restartString) - TILE_SIZE;
            g2d.drawString(restartString, restartStringX, restartMenuStringY);
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, 20));
            int restartStringX = COLUMNS * TILE_SIZE / 2 - metrics.stringWidth(restartString) - TILE_SIZE;
            g2d.drawString(restartString, restartStringX, restartMenuStringY);
        }

        int menuStringX = COLUMNS * TILE_SIZE / 2 + TILE_SIZE;
        if (menuButtonActive) {
            g2d.setFont(new Font("Lato", Font.BOLD, 24));
            g2d.drawString(menuString, menuStringX, restartMenuStringY);
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, 20));
            g2d.drawString(menuString, menuStringX, restartMenuStringY);
        }

    }

    public void drawScore(Graphics g) {
        // set the text to be displayed
        String playersScore;
        if (player.getScore().equals("1")) {
            playersScore = player.getScore() + " Apple";
        } else {
            playersScore = player.getScore() + " Apples";
        }
        String yourScoreText = "Current Score:";
        String highScoreText = "Highscore:";
        String exampleHighScore;
        switch (difficulty) {
            case "EASY" -> exampleHighScore = Integer.toString(App.easyScore);
            case "MEDIUM" -> exampleHighScore = Integer.toString(App.mediumScore);
            case "HARD" -> exampleHighScore = Integer.toString(App.hardScore);
            case "EXTREME" -> exampleHighScore = Integer.toString(App.extremeScore);
            case "GIGAEXTREME" -> exampleHighScore = Integer.toString(App.gigaextremeScore);
            case "HYPAEXTREME" -> exampleHighScore = Integer.toString(App.hypaextremeScore);
            default -> throw new IllegalArgumentException("Invalid difficulty");
        }
        exampleHighScore = exampleHighScore + " Apples";
        String difficultyText = "Difficulty:";
        // we need to cast the Graphics to Graphics2D to draw nicer text
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        // set the text color and font
        g2d.setColor(new Color(0, 0, 0));
        g2d.setFont(new Font("Lato", Font.BOLD, 20));
        // draw the score in the bottom center of the screen
        // https://stackoverflow.com/a/27740330/4655368
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // the text will be contained within this rectangle.
        Rectangle scoreRect = new Rectangle(0, TILE_SIZE * ROWS + TILE_SIZE / 2, TILE_SIZE * COLUMNS, TILE_SIZE);
        // determine the x coordinate for the text

        //int scoreRectX = scoreRect.x + (scoreRect.width / 3 - metrics.stringWidth(playersScore));
        int scoreRectX = TILE_SIZE;
        int f = metrics.stringWidth(highScoreText);
        int highscoreX = scoreRect.x + (scoreRect.width / 2 - metrics.stringWidth(highScoreText) / 2);
        int difficultyX = scoreRect.x + (scoreRect.width - TILE_SIZE - Math.max(metrics.stringWidth(difficultyText), metrics.stringWidth(difficulty)));
        // determine the y coordinate for the text
        // (note we add the ascent, as in java 2d 0 is top of the screen)
        int scoreRectY = scoreRect.y + ((scoreRect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // draw the string
        g2d.drawString(yourScoreText, scoreRectX, scoreRectY);
        g2d.drawString(playersScore, scoreRectX, scoreRectY + TILE_SIZE);
        g2d.drawString(highScoreText, highscoreX, scoreRectY);
        g2d.drawString(exampleHighScore, highscoreX, scoreRectY + TILE_SIZE);
        g2d.drawString(difficultyText, difficultyX, scoreRectY);
        g2d.drawString(difficulty, difficultyX, scoreRectY + TILE_SIZE);
    }

    public void createApple() {
        int x;
        int y;
        while(true) {
            x = random.nextInt(COLUMNS - 1);
            y = random.nextInt(ROWS - 1);
            // if this stays false then its valid
            boolean invalidSpotFlag = false;
            for (DirectionPoint p : player.getBody()) {
                if (p.getPoint().x == x && p.getPoint().y == y) {
                    x = random.nextInt(COLUMNS - 1);
                    y = random.nextInt(ROWS - 1);
                    invalidSpotFlag = true;
                    break;
                }
            }
            if (!invalidSpotFlag) {
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
        endScreen = true;
        updateHighscores();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public java.util.List<Point> getApples() {
        return apples;
    }

    public void eatApple(Point p) {
        java.util.List<Point> appleCopy = apples.stream().toList();
        for (Point t : appleCopy) {
            if (t.equals(p)) {
                apples.remove(t);
                player.addScore(1);
            }
        }
    }

    public void setRestartButton(boolean b) {
        restartButtonActive = b;
    }

    public void setMenuButton(boolean b) {
        menuButtonActive = b;
    }

    public boolean isMenuButtonActive() {
        return menuButtonActive;
    }

    public boolean isRestartButtonActive() {
        return restartButtonActive;
    }

    public boolean isEndScreen() {
        return endScreen;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        // str = "x="+x+", y="+y;
        // restart button
        if (x >= LEFT_X_RESTART_BUTTON && x <= RIGHT_X_RESTART_BUTTON
                && y >= TOP_Y_RESTART_BUTTON && y <= BOTTOM_Y_RESTART_BUTTON) {
            App.initGame(App.getGraphics(), App.getDifficulty());
        }
        // menu button
        if (x >= LEFT_X_MENU_BUTTON && x <= RIGHT_X_MENU_BUTTON
                && y >= TOP_Y_MENU_BUTTON &&  y <= BOTTOM_Y_MENU_BUTTON) {
            App.close();
            App.initMenu();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // only relevant on the score screen
        if (!gameOver) {
            return;
        }

        x = e.getX();
        y = e.getY();

        // restart button
        if (x >= LEFT_X_RESTART_BUTTON && x <= RIGHT_X_RESTART_BUTTON
                && y >= TOP_Y_RESTART_BUTTON && y <= BOTTOM_Y_RESTART_BUTTON) {
            restartButtonActive = true;
            menuButtonActive = false;
        }
        // menu button
        if (x >= LEFT_X_MENU_BUTTON && x <= RIGHT_X_MENU_BUTTON
                && y >= TOP_Y_MENU_BUTTON &&  y <= BOTTOM_Y_MENU_BUTTON) {
            menuButtonActive = true;
            restartButtonActive = false;
        }
    }

    public void updateHighscores() {

        File f = new File(highscoreFileName);

        if (f.exists()) {
            if (!readHighscoreFile(f)) {
                System.out.println("Can not read highscores :/");
            }
        }

        saveCurrentScore();

        recreateHighscoreFile(f);

    }

    public static void createHighscoreFile(File f) {
        // Creating a highscore file
        try {
            FileWriter fw = new FileWriter(f);
            fw.write("EASY: " + Math.max(0, App.easyScore) + "\n");
            fw.write("MEDIUM: " + Math.max(0, App.mediumScore) + "\n");
            fw.write("HARD: " + Math.max(0, App.hardScore) + "\n");
            fw.write("EXTREME: " + Math.max(0, App.extremeScore) + "\n");
            fw.write("GIGAEXTREME: " + Math.max(0, App.gigaextremeScore) + "\n");
            fw.write("HYPAEXTREME: " + Math.max(0, App.hypaextremeScore) + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Highscores not successfully created :(");
        }
    }

    public static void recreateHighscoreFile(File f) {
        if (f.delete()) {
            System.out.println("Deleted the file: " + f.getName());
        } else {
            System.out.println("Failed to delete the file.");
        }
        createHighscoreFile(f);
    }

    public static boolean readHighscoreFile(File f) {

        // Returns whether a file is correct

        try {
            BufferedReader br = new BufferedReader(new FileReader(f.getName()));
            // EASY
            String line = br.readLine();
            if (line == null) {
                System.out.println("File Reading Mistake");
                recreateHighscoreFile(f);
                return false;
            } else {
                if (line.length() < 7) {
                    System.out.println("File Reading Mistake");
                    recreateHighscoreFile(f);
                    return false;
                }
            }
            if (!line.substring(0, 6).equals("EASY: ")) {
                System.out.println("Easy Highscore invalid in file");
                return false;
            }
            App.easyScore = Integer.parseInt(line.substring(6));
            // MEDIUM
            line = br.readLine();
            if (line == null) {
                System.out.println("File Reading Mistake");
                recreateHighscoreFile(f);
                return false;
            } else {
                if (line.length() < 9) {
                    System.out.println("File Reading Mistake");
                    recreateHighscoreFile(f);
                    return false;
                }
            }
            if (!line.substring(0, 8).equals("MEDIUM: ")) {
                System.out.println("Medium Highscore invalid in file");
                return false;
            }
            App.mediumScore = Integer.parseInt(line.substring(8));
            // HARD
            line = br.readLine();
            if (line == null) {
                System.out.println("File Reading Mistake");
                recreateHighscoreFile(f);
                return false;
            } else {
                if (line.length() < 7) {
                    System.out.println("File Reading Mistake");
                    recreateHighscoreFile(f);
                    return false;
                }
            }
            if (!line.substring(0, 6).equals("HARD: ")) {
                System.out.println("Hard Highscore invalid in file");
                return false;
            }
            App.hardScore = Integer.parseInt(line.substring(6));
            // EXTREME
            line = br.readLine();
            if (line == null) {
                System.out.println("File Reading Mistake");
                recreateHighscoreFile(f);
                return false;
            } else {
                if (line.length() < 10) {
                    System.out.println("File Reading Mistake");
                    recreateHighscoreFile(f);
                    return false;
                }
            }
            if (!line.substring(0, 9).equals("EXTREME: ")) {
                System.out.println("Extreme Highscore invalid in file");
                return false;
            }
            App.extremeScore = Integer.parseInt(line.substring(9));
            // GIGAEXTREME
            line = br.readLine();
            if (line == null) {
                System.out.println("File Reading Mistake");
                recreateHighscoreFile(f);
                return false;
            } else {
                if (line.length() < 14) {
                    System.out.println("File Reading Mistake");
                    recreateHighscoreFile(f);
                    return false;
                }
            }
            if (!line.substring(0, 13).equals("GIGAEXTREME: ")) {
                System.out.println("Gigaextreme Highscore invalid in file");
                return false;
            }
            App.gigaextremeScore = Integer.parseInt(line.substring(13));
            // HYPAEXTREME
            line = br.readLine();
            if (line == null) {
                System.out.println("File Reading Mistake");
                recreateHighscoreFile(f);
                return false;
            } else {
                if (line.length() < 14) {
                    System.out.println("File Reading Mistake");
                    recreateHighscoreFile(f);
                    return false;
                }
            }
            if (!line.substring(0, 13).equals("HYPAEXTREME: ")) {
                System.out.println("Hypaextreme Highscore invalid in file");
                return false;
            }
            App.hypaextremeScore = Integer.parseInt(line.substring(13));


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Highscores not successfully updated :(");
        }

        return true;
    }

    private void saveCurrentScore() {
        // Comparing current score to highscore
        switch (difficulty) {
            case "EASY" -> App.easyScore = Math.max(Integer.parseInt(player.getScore()), App.easyScore);
            case "MEDIUM" -> App.mediumScore = Math.max(Integer.parseInt(player.getScore()), App.mediumScore);
            case "HARD" -> App.hardScore = Math.max(Integer.parseInt(player.getScore()), App.hardScore);
            case "EXTREME" -> App.extremeScore = Math.max(Integer.parseInt(player.getScore()), App.extremeScore);
            case "HYPAEXTREME" -> App.hypaextremeScore = Math.max(Integer.parseInt(player.getScore()), App.hypaextremeScore);
            case "GIGAEXTREME" -> App.gigaextremeScore = Math.max(Integer.parseInt(player.getScore()), App.gigaextremeScore);
            default -> throw new IllegalArgumentException("Invalid difficulty");
        }
    }

}
