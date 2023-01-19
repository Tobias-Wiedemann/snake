package menu;

import app.App;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static board.Board.*;

public class Menu extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

    private final int width;
    private final int height;
    private boolean playButton;
    private boolean highscoreButton;
    private boolean autoPlayerButton;
    private boolean exitButton;
    private boolean easyButton;
    private boolean mediumButton;
    private boolean hardButton;
    private boolean expertLevelButton;
    private boolean extremeButton;
    private boolean gigaExtremeButton;
    private boolean hypaExtremeButton;
    private boolean backFromGamesButton;
    private enum state {
        menuState, playGame, hardDifficultys, highscore, autoplayer
    }
    private state currentState;
    private int x,y;
    private String str;
    protected Timer timer;
    private static final int RIGHT_X_PLAY = 80;
    private static final int LEFT_X_PLAY = 230;
    private static final int TOP_Y_PLAY = 170;
    private static final int BOTTOM_Y_PLAY = 215;
    private static final int RIGHT_X_HIGHSCORE = 75;
    private static final int LEFT_X_HIGHSCORE = 240;
    private static final int TOP_Y_HIGHSCORE = 220;
    private static final int BOTTOM_Y_HIGHSCORE = 265;
    private static final int RIGHT_X_AUTOPLAYER = 75;
    private static final int LEFT_X_AUTOPLAYER = 240;
    private static final int TOP_Y_AUTOPLAYER = 270;
    private static final int BOTTOM_Y_AUTOPLAYER = 315;
    private static final int RIGHT_X_EXIT = 130;
    private static final int LEFT_X_EXIT = 185;
    private static final int TOP_Y_EXIT = 320;
    private static final int BOTTOM_Y_EXIT = 365;
    private static final int BIG_FONT = 35;
    private static final int NORMAL_FONT = 25;
    private static final int SMALL_FONT = 20;
    private static final int HEADLINE_FONT = 50;
    private static final int RIGHT_X_EASY = 130;
    private static final int LEFT_X_EASY = 185;
    private static final int TOP_Y_EASY = 200;
    private static final int BOTTOM_Y_EASY = 240;
    private static final int RIGHT_X_MEDIUM = 115;
    private static final int LEFT_X_MEDIUM = 200;
    private static final int TOP_Y_MEDIUM = 245;
    private static final int BOTTOM_Y_MEDIUM = 280;
    private static final int RIGHT_X_HARD = 125;
    private static final int LEFT_X_HARD = 185;
    private static final int TOP_Y_HARD = 285;
    private static final int BOTTOM_Y_HARD = 320;
    private static final int RIGHT_X_EXPERT_LEVEL = 100;
    private static final int LEFT_X_EXPERT_LEVEL = 215;
    private static final int TOP_Y_EXPERT_LEVEL = 325;
    private static final int BOTTOM_Y_EXPERT_LEVEL = 355;
    private static final int RIGHT_X_BACK_FROM_GAMES = 130;
    private static final int LEFT_X_BACK_FROM_GAMES = 185;
    private static final int TOP_Y_BACK_FROM_GAMES = 360;
    private static final int BOTTOM_Y_BACK_FROM_GAMES = 380;
    private static final int TOP_Y_BACK_FROM_GAMES_2 = 325;
    private static final int BOTTOM_Y_BACK_FROM_GAMES_2 = 355;
    private static final int RIGHT_X_GIGA_HYPA_EXTREME = 70;
    private static final int LEFT_X_GIGA_HYPA_EXTREME = 255;


    public Menu() {
        width = 300;
        height = 400;
        setPreferredSize(new Dimension(width, height));
        currentState = state.menuState;
        resetButtons();
        str = "";
        timer = new Timer(175, this);
        timer.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw our graphics.
        this.drawBackground(g);
        switch (currentState) {
            case menuState -> drawMenuText(g);
            case playGame -> drawGameSelection(g, false);
            case hardDifficultys -> drawGameSelection(g, true);
            case highscore -> drawHighscores(g);
            case autoplayer -> drawAutoplayer(g);
        }

        // this smooths out animations on some systems
        Toolkit.getDefaultToolkit().sync();
    }

    public void drawGameSelection(Graphics g, boolean hardDifficultys) {
        String snakeString = "SNAKE";
        String difficultyString = "Select Difficulty:";
        String easy = "EASY";
        String medium = "MEDIUM";
        String hard = "HARD";
        String expert = "Expert Level";
        String extreme = "EXTREME";
        String gigaExtreme = "GIGAEXTREME";
        String hypaExtreme = "HYPAEXTREME";
        String back = "Back";


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
        g2d.setColor(new Color(119, 178, 120));
        g2d.setFont(new Font("Lato", Font.BOLD, HEADLINE_FONT));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // SNAKE
        g2d.drawString(snakeString, (width - metrics.stringWidth(snakeString)) / 2, height / 4);
        // Difficulty
        g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(difficultyString, (width - metrics.stringWidth(difficultyString)) / 2,
                height / 4 + 2 * height / 16);
        // Easy or extreme
        if (easyButton || extremeButton && hardDifficultys) {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, SMALL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        if (hardDifficultys) {
            g2d.drawString(extreme, (width - metrics.stringWidth(extreme)) / 2,
                    height / 4 + 8 * height / 32);
        } else {
            g2d.drawString(easy, (width - metrics.stringWidth(easy)) / 2,
                    height / 4 + 8 * height / 32);
        }
        // Medium or gigaextreme
        if (mediumButton || gigaExtremeButton && hardDifficultys) {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, SMALL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        if (hardDifficultys) {
            g2d.drawString(gigaExtreme, (width - metrics.stringWidth(gigaExtreme)) / 2,
                    height / 4 + 11 * height / 32);
        } else {
            g2d.drawString(medium, (width - metrics.stringWidth(medium)) / 2,
                    height / 4 + 11 * height / 32);
        }
        // Hard or hypaextreme
        if (hardButton || hypaExtremeButton && hardDifficultys) {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, SMALL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        if (hardDifficultys) {
            g2d.drawString(hypaExtreme, (width - metrics.stringWidth(hypaExtreme)) / 2,
                    height / 4 + 14 * height / 32);
        } else {
            g2d.drawString(hard, (width - metrics.stringWidth(hard)) / 2,
                    height / 4 + 14 * height / 32);
        }
        // Expert Level
        if (expertLevelButton) {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, SMALL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        if (currentState == state.playGame) {
            g2d.drawString(expert, (width - metrics.stringWidth(expert)) / 2,
                    height / 4 + 17 * height / 32);
        }
        // Back
        if (backFromGamesButton) {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, SMALL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        if (currentState == state.hardDifficultys) {
            g2d.drawString(back, (width - metrics.stringWidth(back)) / 2,
                    height / 4 + 17 * height / 32);
        } else {
            g2d.drawString(back, (width - metrics.stringWidth(back)) / 2,
                    height / 4 + 20 * height / 32);
        }

//        g2d.drawString(str, (width - metrics.stringWidth(str)) / 2, height / 4 + 23 * height / 32);
    }

    public void drawHighscores(Graphics g) {
        String snakeString = "SNAKE";
        String highscoreString = "Highscores";
        String easy = "Easy";
        String medium = "Medium";
        String hard = "Hard";
        String extreme = "Extreme";
        String hypaextreme = "Hypaextreme";
        String gigaextreme = "Gigaextreme";
        String back = "Back";
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
        g2d.setColor(new Color(119, 178, 120));

        File f = new File(highscoreFileName);

        if (f.exists()) {
            if (!readHighscoreFile(f)) {
                System.out.println("Can not read highscores :/");
                recreateHighscoreFile(f);
                g2d.setFont(new Font("Lato", Font.BOLD, HEADLINE_FONT));
                FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
                // SNAKE
                g2d.drawString(snakeString, (width - metrics.stringWidth(snakeString)) / 2, height / 4);
                g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
                metrics = g2d.getFontMetrics(g2d.getFont());
                // Error 404
                g2d.drawString("Error 404", (width - metrics.stringWidth("Error 404")) / 2, height / 4 + 3 * height / 16);
                g2d.drawString("Can not read file", (width - metrics.stringWidth("Can not read file")) / 2, height / 4 + 5 * height / 16);
                return;
            }
        } else {
            createHighscoreFile(f);
            readHighscoreFile(f);
        }


        g2d.setFont(new Font("Lato", Font.BOLD, HEADLINE_FONT));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // SNAKE
        g2d.drawString(snakeString, (width - metrics.stringWidth(snakeString)) / 2, height / 4);

        // Highscores
        g2d.setFont(new Font("Lato", Font.BOLD, BIG_FONT));
        metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(highscoreString, (width - metrics.stringWidth(highscoreString)) / 2,
                height / 4 + 3 * height / 16);

        String tempScore;
        // Easy
        g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        g2d.drawString(easy, 35,
                height / 4 + 9 * height / 32);
        tempScore = Integer.toString(App.easyScore);
        if (tempScore.length() == 1) {
            tempScore = "  " + tempScore;
        } else if (tempScore.length() == 2) {
            tempScore = " " + tempScore;
        }
        g2d.drawString(tempScore, 275 - metrics.stringWidth(tempScore),
                height / 4 + 9 * height / 32);
        // Medium
        g2d.drawString(medium, 35,
                height / 4 + 11 * height / 32);
        tempScore = Integer.toString(App.mediumScore);
        if (tempScore.length() == 1) {
            tempScore = "  " + tempScore;
        } else if (tempScore.length() == 2) {
            tempScore = " " + tempScore;
        }
        g2d.drawString(tempScore, 275 - metrics.stringWidth(tempScore),
                height / 4 + 11 * height / 32);
        // hard
        g2d.drawString(hard, 35,
                height / 4 + 13 * height / 32);
        tempScore = Integer.toString(App.hardScore);
        if (tempScore.length() == 1) {
            tempScore = "  " + tempScore;
        } else if (tempScore.length() == 2) {
            tempScore = " " + tempScore;
        }
        g2d.drawString(tempScore, 275 - metrics.stringWidth(tempScore),
                height / 4 + 13 * height / 32);
        // extreme
        g2d.drawString(extreme, 35,
                height / 4 + 15 * height / 32);
        tempScore = Integer.toString(App.extremeScore);
        if (tempScore.length() == 1) {
            tempScore = "  " + tempScore;
        } else if (tempScore.length() == 2) {
            tempScore = " " + tempScore;
        }
        g2d.drawString(tempScore, 275 - metrics.stringWidth(tempScore),
                height / 4 + 15 * height / 32);
        // hypaextreme
        g2d.drawString(hypaextreme, 35,
                height / 4 + 17 * height / 32);
        tempScore = Integer.toString(App.hypaextremeScore);
        if (tempScore.length() == 1) {
            tempScore = "  " + tempScore;
        } else if (tempScore.length() == 2) {
            tempScore = " " + tempScore;
        }
        g2d.drawString(tempScore, 275 - metrics.stringWidth(tempScore),
                height / 4 + 17 * height / 32);
        // gigaextreme
        g2d.drawString(gigaextreme, 35,
                height / 4 + 19 * height / 32);
        tempScore = Integer.toString(App.gigaextremeScore);
        if (tempScore.length() == 1) {
            tempScore = "  " + tempScore;
        } else if (tempScore.length() == 2) {
            tempScore = " " + tempScore;
        }
        g2d.drawString(tempScore, 275 - metrics.stringWidth(tempScore),
                height / 4 + 19 * height / 32);
    }

    public void drawAutoplayer(Graphics g) {
        String snakeString = "SNAKE";
        String comingSoonString = "Coming soon";
        String autoPlayerString = "Autoplayer";
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
        g2d.setColor(new Color(119, 178, 120));
        g2d.setFont(new Font("Lato", Font.BOLD, HEADLINE_FONT));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // SNAKE
        g2d.drawString(snakeString, (width - metrics.stringWidth(snakeString)) / 2, height / 4);
        g2d.drawString(autoPlayerString, (width - metrics.stringWidth(autoPlayerString)) / 2, 3 * height / 8);
        g2d.setFont(new Font("Lato", Font.BOLD, BIG_FONT));
        metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(comingSoonString, (width - metrics.stringWidth(comingSoonString)) / 2, 2 * height / 3);
    }

    public void drawBackground(Graphics g) {
        // draw a checkered background
        g.setColor(new Color(0, 0, 0));
        g.fillRect(0, 0, width, height);
    }

    public void drawMenuText(Graphics g) {
        String snakeString = "SNAKE";
        String menuString = "Menu";
        String playGameString = "Play Game";
        String highscoreString = "Highscores";
        String autoPlayerString = "Autoplayer";
        String exitString = "Exit";
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
        g2d.setColor(new Color(119, 178, 120));
        g2d.setFont(new Font("Lato", Font.BOLD, HEADLINE_FONT));
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());
        // SNAKE
        g2d.drawString(snakeString, (width - metrics.stringWidth(snakeString)) / 2, height / 4);
        // PLAY
        if (playButton) {
            g2d.setFont(new Font("Lato", Font.BOLD, BIG_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(playGameString, (width - metrics.stringWidth(playGameString)) / 2,
                height / 4 + 3 * height / 16);
        // Highscores
        if (highscoreButton) {
            g2d.setFont(new Font("Lato", Font.BOLD, BIG_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(highscoreString, (width - metrics.stringWidth(highscoreString)) / 2,
                height / 4 + 5 * height / 16);
        // Autoplayer
        if (autoPlayerButton) {
            g2d.setFont(new Font("Lato", Font.BOLD, BIG_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(autoPlayerString, (width - metrics.stringWidth(autoPlayerString)) / 2,
                height / 4 + 7 * height / 16);
        // Exit
        if (exitButton) {
            g2d.setFont(new Font("Lato", Font.BOLD, BIG_FONT));
        } else {
            g2d.setFont(new Font("Lato", Font.BOLD, NORMAL_FONT));
        }
        metrics = g2d.getFontMetrics(g2d.getFont());
        g2d.drawString(exitString, (width - metrics.stringWidth(exitString)) / 2,
                height / 4 + 9 * height / 16);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // EndScreen shit
        int key = e.getKeyCode();
        switch (currentState) {


            case menuState: {
                // Nothing selected
                if (!playButton && !autoPlayerButton && !highscoreButton && !exitButton) {
                    playButton = true;
                    return;
                }
                // Enter selected
                if (key == KeyEvent.VK_ENTER) {
                    if (playButton) {
                        playButton = false;
                        currentState = state.playGame;
                        return;
                    }
                    if (autoPlayerButton) {
                        autoPlayerButton = false;
                        currentState = state.autoplayer;
                        return;
                    }
                    if (highscoreButton) {
                        highscoreButton = false;
                        currentState = state.highscore;
                        return;
                    }
                    if (exitButton) {
                        App.endApp();
                        return;
                    }
                }
                // Moving up
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    if (playButton) {
                        playButton = false;
                        exitButton = true;
                        return;
                    }
                    if (highscoreButton) {
                        highscoreButton = false;
                        playButton = true;
                        return;
                    }
                    if (autoPlayerButton) {
                        autoPlayerButton = false;
                        highscoreButton = true;
                        return;
                    }
                    if (exitButton) {
                        exitButton = false;
                        autoPlayerButton = true;
                        return;
                    }
                }
                // Moving down
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    if (playButton) {
                        playButton = false;
                        highscoreButton = true;
                        return;
                    }
                    if (highscoreButton) {
                        highscoreButton = false;
                        autoPlayerButton = true;
                        return;
                    }
                    if (autoPlayerButton) {
                        autoPlayerButton = false;
                        exitButton = true;
                        return;
                    }
                    if (exitButton) {
                        exitButton = false;
                        playButton = true;
                        return;
                    }
                }
            }



            case playGame: {
                // Nothing selected
                if (!easyButton && !mediumButton && !hardButton && !expertLevelButton && !backFromGamesButton) {
                    easyButton = true;
                    return;
                }
                // Enter selected
                if (key == KeyEvent.VK_ENTER) {
                    if (easyButton) {
                        App.initGame(true, "EASY");
                        return;
                    }
                    if (mediumButton) {
                        App.initGame(true, "MEDIUM");
                        return;
                    }
                    if (hardButton) {
                        App.initGame(true, "HARD");
                        return;
                    }
                    if (expertLevelButton) {
                        expertLevelButton = false;
                        currentState = state.hardDifficultys;
                        return;
                    }
                    if (backFromGamesButton) {
                        backFromGamesButton = false;
                        currentState = state.menuState;
                        return;
                    }
                }
                // Moving up
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    if (easyButton) {
                        easyButton = false;
                        backFromGamesButton = true;
                        return;
                    }
                    if (mediumButton) {
                        mediumButton = false;
                        easyButton = true;
                        return;
                    }
                    if (hardButton) {
                        hardButton = false;
                        mediumButton = true;
                        return;
                    }
                    if (expertLevelButton) {
                        expertLevelButton = false;
                        hardButton = true;
                        return;
                    }
                    if (backFromGamesButton) {
                        backFromGamesButton = false;
                        expertLevelButton = true;
                        return;
                    }
                }
                // Moving down
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    if (easyButton) {
                        easyButton = false;
                        mediumButton = true;
                        return;
                    }
                    if (mediumButton) {
                        mediumButton = false;
                        hardButton = true;
                        return;
                    }
                    if (hardButton) {
                        hardButton = false;
                        expertLevelButton = true;
                        return;
                    }
                    if (expertLevelButton) {
                        expertLevelButton = false;
                        backFromGamesButton = true;
                        return;
                    }
                    if (backFromGamesButton) {
                        backFromGamesButton = false;
                        easyButton = true;
                        return;
                    }
                }
            }



            case hardDifficultys: {
                // Nothing selected
                if (!extremeButton && !gigaExtremeButton && !hypaExtremeButton && !backFromGamesButton) {
                    extremeButton = true;
                    return;
                }
                // Enter selected
                if (key == KeyEvent.VK_ENTER) {
                    if (extremeButton) {
                        App.initGame(true, "EXTREME");
                        return;
                    }
                    if (gigaExtremeButton) {
                        App.initGame(true, "GIGAEXTREME");
                        return;
                    }
                    if (hypaExtremeButton) {
                        App.initGame(true, "HYPAEXTREME");
                        return;
                    }
                    if (backFromGamesButton) {
                        backFromGamesButton = false;
                        currentState = state.playGame;
                        return;
                    }
                }
                // Moving up
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
                    if (extremeButton) {
                        extremeButton = false;
                        backFromGamesButton = true;
                        return;
                    }
                    if (gigaExtremeButton) {
                        gigaExtremeButton = false;
                        extremeButton = true;
                        return;
                    }
                    if (hypaExtremeButton) {
                        hypaExtremeButton = false;
                        gigaExtremeButton = true;
                        return;
                    }
                    if (backFromGamesButton) {
                        backFromGamesButton = false;
                        hypaExtremeButton = true;
                        return;
                    }
                }
                // Moving down
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
                    if (extremeButton) {
                        extremeButton = false;
                        gigaExtremeButton = true;
                        return;
                    }
                    if (gigaExtremeButton) {
                        gigaExtremeButton = false;
                        hypaExtremeButton = true;
                        return;
                    }
                    if (hypaExtremeButton) {
                        hypaExtremeButton = false;
                        backFromGamesButton = true;
                        return;
                    }
                    if (backFromGamesButton) {
                        backFromGamesButton = false;
                        extremeButton = true;
                        return;
                    }
                }
            }



            case highscore: {
                currentState = state.menuState;
            }



            case autoplayer: {
                currentState = state.menuState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        // MAIN MENU
        //  PLAY
        if (playButton) {
            currentState = state.playGame;
        }
        // HIGHSCORE
        if (highscoreButton) {
            currentState = state.highscore;
            return;
        }
        // AUTOPLAYER
        if (autoPlayerButton) {
            currentState = state.autoplayer;
            return;
        }
        // EXIT
        if (exitButton) {
            App.endApp();
        }

        // SELECT GAME 1
        // EASY
        if (easyButton) {
            App.initGame(true, "EASY");
        }
        // MEDIUM
        if (mediumButton) {
            App.initGame(true, "MEDIUM");
        }
        // HARD
        if (hardButton) {
            App.initGame(true, "HARD");
        }
        // EXPERT LEVEL
        if (expertLevelButton) {
            this.currentState = state.hardDifficultys;
        }
        // BACK
        if (backFromGamesButton) {
            if (currentState == state.hardDifficultys) {
                currentState = state.playGame;
            } else {
                currentState = state.menuState;
            }
        }


        // SELECT GAME 2
        // EXTREME
        if (extremeButton) {
            App.initGame(true, "EXTREME");
        }
        // GIGAEXTREME
        if (gigaExtremeButton) {
            App.initGame(true, "GIGAEXTREME");
        }
        // HYPAEXTREME
        if (hypaExtremeButton) {
            App.initGame(true, "HYPAEXTREME");
        }



        // HIGHSCORES
        if (currentState == state.highscore) {
            currentState = state.menuState;
        }



        // AUTOPLAYER
        if (currentState == state.autoplayer) {
            currentState = state.menuState;
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
        x = e.getX();
        y = e.getY();
        str = "x="+x+", y="+y;
        
        resetButtons();

        if (currentState.equals(state.menuState)) {
            // Play button
            if (x <= LEFT_X_PLAY && x >= RIGHT_X_PLAY && y >= TOP_Y_PLAY && y <= BOTTOM_Y_PLAY) {
                playButton = true;
            }
            // HIGHSCORE button
            if (x <= LEFT_X_HIGHSCORE && x >= RIGHT_X_HIGHSCORE && y >= TOP_Y_HIGHSCORE && y <= BOTTOM_Y_HIGHSCORE) {
                highscoreButton = true;
            }
            // AUTOPLAYER button
            if (x <= LEFT_X_AUTOPLAYER && x >= RIGHT_X_AUTOPLAYER && y >= TOP_Y_AUTOPLAYER && y <= BOTTOM_Y_AUTOPLAYER) {
                autoPlayerButton = true;
            }
            // EXIT button
            if (x <= LEFT_X_EXIT && x >= RIGHT_X_EXIT && y >= TOP_Y_EXIT && y <= BOTTOM_Y_EXIT) {
                exitButton = true;
            }
        }

        if (currentState == state.playGame) {
            // Easy button
            if (x <= LEFT_X_EASY && x >= RIGHT_X_EASY && y >= TOP_Y_EASY && y <= BOTTOM_Y_EASY) {
                easyButton = true;
            }
            // MEDIUM button
            if (x <= LEFT_X_MEDIUM && x >= RIGHT_X_MEDIUM && y >= TOP_Y_MEDIUM && y <= BOTTOM_Y_MEDIUM) {
                mediumButton = true;
            }
            // HARD button
            if (x <= LEFT_X_HARD && x >= RIGHT_X_HARD && y >= TOP_Y_HARD && y <= BOTTOM_Y_HARD) {
                hardButton = true;
            }
            // EXPERT_LEVEL button
            if (x <= LEFT_X_EXPERT_LEVEL && x >= RIGHT_X_EXPERT_LEVEL && y >= TOP_Y_EXPERT_LEVEL && y <= BOTTOM_Y_EXPERT_LEVEL) {
                expertLevelButton = true;
            }
            // BACK_FROM_GAMES button
            if (x <= LEFT_X_BACK_FROM_GAMES && x >= RIGHT_X_BACK_FROM_GAMES && y >= TOP_Y_BACK_FROM_GAMES && y <= BOTTOM_Y_BACK_FROM_GAMES) {
                backFromGamesButton = true;
            }
        }

        if (currentState == state.hardDifficultys) {
            // EXTREME button
            if (x <= LEFT_X_EASY && x >= RIGHT_X_EASY && y >= TOP_Y_EASY && y <= BOTTOM_Y_EASY) {
                extremeButton = true;
            }
            // GIGAEXTREME button
            if (x <= LEFT_X_GIGA_HYPA_EXTREME && x >= RIGHT_X_GIGA_HYPA_EXTREME && y >= TOP_Y_MEDIUM && y <= BOTTOM_Y_MEDIUM) {
                gigaExtremeButton = true;
            }
            // HYPAEXTREME button
            if (x <= LEFT_X_GIGA_HYPA_EXTREME && x >= RIGHT_X_GIGA_HYPA_EXTREME && y >= TOP_Y_HARD && y <= BOTTOM_Y_HARD) {
                hypaExtremeButton = true;
            }
            // BACK_FROM_GAMES button
            if (x <= LEFT_X_BACK_FROM_GAMES && x >= RIGHT_X_BACK_FROM_GAMES && y >= TOP_Y_BACK_FROM_GAMES_2 && y <= BOTTOM_Y_BACK_FROM_GAMES_2) {
                backFromGamesButton = true;
            }
        }

        if (currentState == state.highscore) {
            if (x <= 175 && x >= 115 && y >= TOP_Y_EASY && y <= BOTTOM_Y_EASY) {
                extremeButton = true;
            }
        }
    }

    private void resetButtons() {
        playButton = false;
        highscoreButton = false;
        autoPlayerButton = false;
        exitButton = false;
        easyButton = false;
        mediumButton = false;
        hardButton = false;
        expertLevelButton = false;
        extremeButton = false;
        gigaExtremeButton = false;
        hypaExtremeButton = false;
        backFromGamesButton = false;
    }
}
