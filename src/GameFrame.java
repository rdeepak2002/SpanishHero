import javax.swing.*;
import java.awt.*;

/**
 * Created by rdeep on 6/9/2017.
 */
public class GameFrame extends JFrame                      // GameFrame class header
{
    private CardLayout cl = new CardLayout();       // declare private CardLayout cl
    // IMPORTANT: WIDTH TO HEIGHT RATIO MUST BE 5 TO 3
    private int scale;                              // declare private int scale
    private int frameWidth;                         // declare private int frameWidth
    private int frameHeight;                        // declare private int frameHeight

    public GameFrame(int scale)                     // constructor to initialize field variables
    {
        super("Spanish Hero");                      // set the title of the frame to Spanish Hero

        this.scale = scale;
        frameWidth = 1000*scale;
        frameHeight = 600*scale;

        LearnPanel lp;
        LevelSelect ls;
        GamePanel gp;
        Menu m;
        GameOver go;
        WinPanel wp;

        lp = new LearnPanel(cl, frameWidth, frameHeight);
        ls = new LevelSelect(cl, lp, frameWidth, frameHeight);  // declare and initialize new LevelSelect
        gp = new GamePanel(cl, ls, frameWidth, frameHeight);      // declare and initialize new GamePanel
        m = new Menu(cl, ls, frameWidth, frameHeight);                 // declare and initialize new Menu
        go = new GameOver(cl, ls, frameWidth, frameHeight);        // declare and initialize new GameOver
        wp = new WinPanel(cl, ls, frameWidth, frameHeight);

        setSize(frameWidth, frameHeight);                               // set the size of the game frame
        setLocation((int)(frameWidth*0.4), (int)(100*0.6));
        setResizable(false);
        setVisible(true);
        setLayout(cl);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().add(m, "Menu");                                // add all the panels to the card layout
        getContentPane().add(gp, "Game");
        getContentPane().add(go, "Game Over");
        getContentPane().add(ls, "Level Select");
        getContentPane().add(lp, "Learn");
        getContentPane().add(wp, "Win");

        m.requestFocusInWindow();
    }
}
