import board.Board;
import board.BoardWithGraphics;
import board.SimpleBoard;
import menu.Menu;

import javax.swing.*;

public class App {

    private static boolean appGoing;
    private static JFrame window;

    private static void setAppGoing(boolean state) {
        appGoing = state;
    }

    public static boolean getAppGoing() {
        return appGoing;
    }

    private static void endApp() {
        appGoing = false;
    }

    public void removeBoard(Board b) {
        window.remove(b);
    }

    public static void initGame(boolean graphics, String difficulty) {
        // create a window frame and set the title in the toolbar
        window = new JFrame("Snake");
        // when we close the window, stop the app
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the jpanel to draw on.
        // this also initializes the game loop
        Board board;
        if (graphics) {
            board = new BoardWithGraphics(difficulty);
        } else {
            board = new SimpleBoard(difficulty);
        }
        // add the jpanel to the window
        window.add(board);
        // pass keyboard inputs to the jpanel
        window.addKeyListener(board);

        // don't allow the user to resize the window
        window.setResizable(false);
        // fit the window size around the components (just our jpanel).
        // pack() should be called after setResizable() to avoid issues on some platforms
        window.pack();
        // open window in the center of the screen
        window.setLocationRelativeTo(null);
        // display the window
        window.setVisible(true);
    }

    private static void initMenu() {
        // create a window frame and set the title in the toolbar
        window = new JFrame("Snake");
        // when we close the window, stop the app
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the jpanel to draw on.
        // this also initializes the game loop
        Menu menu = new Menu();
        // add the jpanel to the window
        window.add(menu);
        // pass keyboard inputs to the jpanel
        window.addKeyListener(menu);

        // don't allow the user to resize the window
        window.setResizable(false);
        // fit the window size around the components (just our jpanel).
        // pack() should be called after setResizable() to avoid issues on some platforms
        window.pack();
        // open window in the center of the screen
        window.setLocationRelativeTo(null);
        // display the window
        window.setVisible(true);
    }


    public static void main(String[] args) {
        // invokeLater() is used here to prevent our graphics processing from
        // blocking the GUI. https://stackoverflow.com/a/22534931/4655368
        // this is a lot of boilerplate code that you shouldn't be too concerned about.
        // just know that when main runs it will call initWindow() once.
        setAppGoing(true);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initGame(true, "EXTREME");
            }
        });

    }

}
