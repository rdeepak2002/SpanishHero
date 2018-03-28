import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by rdeep on 6/9/2017.
 */
public class LevelSelect extends JPanel implements MouseListener, KeyListener                                        // LevelSelect class header
{
    private int frameWidth;                                             // declare private int frameWidth
    private int frameHeight;                                            // declare private int frameHeight
    private int level;

    private CardLayout cardLayout;                                      // declare private CardLayotu cardLayout
    private LearnPanel lp;

    public LevelSelect(CardLayout cardLayout, LearnPanel lp, int frameWidth, int frameHeight) // constructor to initialize field variables
    {
        this.cardLayout = cardLayout;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.lp = lp;

        requestFocus();
        addMouseListener(this);
        addKeyListener(this);

        int rows = 3;                               // declare and initialize int rows
        int cols = 4;                               // declare and initialize int cols

        setBackground(Color.WHITE);
        setLayout(new GridLayout(rows, cols, 10, 10));

        for (int r = 0; r < rows; r++)              // double for loop using rows and cols to add the correct type of panel
        {
            for (int c = 0; c < cols; c++)
            {
                if(r == 0 || r == 2)                // if r is 0 or 3
                {
                    if (c == 1 && r == 0)
                        add(new FacePanel(1));
                    else if(c == 2 && r == 0)
                        add(new FacePanel(2));
                    else if (c == 1 && r == 2)
                        add(new FacePanel(5));
                    else if (c == 2 && r == 2)
                        add(new FacePanel(6));
                    else if (c == 0 && r == 0)
                        add(new FacePanel(-1));
                    else
                        add(new FacePanel(0));
                }
                else
                {
                    if (c == 0)          // if c is 0 or 3
                        add(new FacePanel(3));
                    else if(c == 3)
                        add(new FacePanel(4));
                    else
                        add(new FacePanel(0));
                }
            }
        }

        this.requestFocus();
        this.requestFocusInWindow();
    }

    public void goToGame()                          // method header goToGame()
    {
        cardLayout.show(super.getParent(), "Learn"); // switch cardLayout to the game panel
    }

    public void keyTyped(KeyEvent e) {}

    public void keyReleased(KeyEvent e) {}

    public void keyPressed(KeyEvent e)
    {
        char key = e.getKeyChar();

        if(key == 'q' || key == 'Q')
        {
            cardLayout.show(super.getParent(), "Menu");
        }
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e)
    {
        requestFocus();
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }


    class FacePanel extends JPanel implements ActionListener    // nested class FacePanel
    {
        private JButton[] buttons;

        public FacePanel(int type)                              // constructor to initialize field variables
        {
            Font trb = new Font("Verdana", Font.BOLD, (int)(frameHeight/20));
            Font trb2 = new Font("Verdana", Font.BOLD, (int)(frameHeight/35));
            Font trb2P5 = new Font("Verdana", Font.BOLD, (int)(frameHeight/40));
            Font trb3 = new Font("Verdana", Font.BOLD, (int)(frameHeight/45));

            buttons = new JButton[] {new JButton("PRESENT"), new JButton("PRETERITE"), new JButton("IMPERFECT"), new JButton("PRETERITE VS IMPERFECT"), new JButton("PRESENTE PERFECTO"), new JButton("FUTURO") };
            JTextArea prompt = new JTextArea("Press Q to Quit");
            prompt.setFont(trb2);
            prompt.setEditable(false);

            for(int i = 0; i<buttons.length; i++)
            {
                buttons[i].addActionListener(this);
                buttons[i].setBackground(Color.WHITE);
                buttons[i].setForeground(Color.BLACK);
                buttons[i].setFont(trb);
                if(i == 4)
                    buttons[i].setFont(trb2P5);
                else if(i == 3)
                    buttons[i].setFont(trb3);
            }

            setBackground(Color.WHITE);
            setLayout(new GridLayout(1,1));
            if(type == 1)
                add(buttons[0]);
            if(type == 2)
                add(buttons[1]);
            if(type == 3)
                add(buttons[2]);
            if(type == 4)
                add(buttons[3]);
            if(type == 5)
                add(buttons[4]);
            if(type == 6)
                add(buttons[5]);
            if(type == -1)
                add(prompt);
        }

        public void actionPerformed(ActionEvent e)              // action performed method header
        {
            String command = e.getActionCommand();              // get the action command in  a string
            if(command.equals("PRESENT"))                       // if perfect is clicked...
            {
                level = 1;
                lp.refreshPanel(level);
                goToGame();                                     // call goToGame()
            }
            else if(command.equals("PRETERITE"))                       // if perfect is clicked...
            {
                level = 2;
                lp.refreshPanel(level);
                goToGame();                                     // call goToGame()
            }
            else if(command.equals("IMPERFECT"))                       // if perfect is clicked...
            {
                level = 3;
                lp.refreshPanel(level);
                goToGame();                                     // call goToGame()
            }
            else if(command.equals("PRETERITE VS IMPERFECT"))                       // if perfect is clicked...
            {
                level = 4;
                lp.refreshPanel(level);
                goToGame();                                     // call goToGame()
            }
            else if(command.equals("PRESENTE PERFECTO"))                       // if perfect is clicked...
            {
                level = 5;
                lp.refreshPanel(level);
                goToGame();                                     // call goToGame()
            }
            else if(command.equals("FUTURO"))                       // if perfect is clicked...
            {
                level = 6;
                lp.refreshPanel(level);
                goToGame();                                     // call goToGame()
            }
        }
    }

    public int getLevel()
    {
        return level;
    }
}
