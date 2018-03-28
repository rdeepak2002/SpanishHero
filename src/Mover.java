import javax.swing.*;
import java.awt.event.*;

/**
 * Created by rdeep on 6/9/2017.
 */
public class Mover implements ActionListener, MouseListener, KeyListener       // Mover class header
{
    private GamePanel gamePanel;                                        // declare private GamePanel gamePanel
    private Player player;                                              // declare private Player player
    private Timer timer;                                                // declare private Timer timer
    private int fps;                                                    // declare private int fps

    public Mover(GamePanel gamePanel, Player player)                    // constructor to initialize field variables
    {
        fps = 30;
        this.gamePanel = gamePanel;
        this.player = player;
        timer = new Timer(((int)(1000.0/(double)fps)), this);
        gamePanel.addMouseListener(this);                               // add mouse listener to this
        gamePanel.addKeyListener(this);                                 // add key listener to this
    }

    public Timer getTimer()
    {
        return timer;
    }

    public void actionPerformed(ActionEvent e)                          // action performed method header
    {
        gamePanel.updateLoop();                                         // call update loop in timer
        gamePanel.repaint();                                            // call repaint to update screen contents
        gamePanel.requestFocusInWindow();					            // call requestFocusInWindow() to focus KeyListener
    }

    public void mousePressed(MouseEvent e)					            // mousePressed method header
    {
        gamePanel.requestFocusInWindow();                               // call request focus in window
        if(!gamePanel.isMouseClicked())                                 // if the mouse has not been clicked, start the timer
        {
            timer.start();
        }
        gamePanel.setMouseClicked(true);                                // set mouse clicked to true
    }

    public void keyReleased(KeyEvent e)                                 // keypressed method header
    {
        char key = e.getKeyChar();                                      // get the key pressed as a character

        if(key == 'q' || key == 'Q')                                    // if the key was q...
        {
            timer.stop();                                               // stop the timer
        }
    }

    public void mouseEntered(MouseEvent e)                              // mouse entered requests focus in the game panel
    {
        gamePanel.requestFocusInWindow();
    }

    public void keyPressed(KeyEvent e) {}                               // override method
    public void keyTyped(KeyEvent e){}						            // override method
    public void mouseClicked(MouseEvent e){}			        	    // override method
    public void mouseReleased(MouseEvent e){}				            // override method
    public void mouseExited(MouseEvent e){}					            // override method
}
