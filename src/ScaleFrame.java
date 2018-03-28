import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rdeep on 6/9/2017.
 */
public class ScaleFrame extends JFrame implements ActionListener   // class header
{
    private GameFrame game;                                 // declare private Game game
    private String option1Text;                             // declare private String option1Text
    private String option2Text;                             // declare private String option2Text

    public ScaleFrame()                                     // constructor
    {
        super("Spanish Hero");                              // set title of scale frame to "Spanish Hero"

        Font trb = new Font("Verdana", Font.BOLD, (int)(40));    // create new Font, Verdana Bold

        option1Text = "1000 x 600 (recommended)";           // initialize option1Text
        option2Text = "2000 x 1200";                        // initialize option2Text

        setSize(1000, 400);                                  // set size of frame to 800 by 400
        setLocation(400, 100);                              // set location to 400 by 100
        setResizable(false);                                // set resizable to false
        setLayout(new FlowLayout());                        // set layout to a new flow layout
        setDefaultCloseOperation(EXIT_ON_CLOSE);            // set default close operation

        JMenuBar bar = new JMenuBar();                      // declare and initialize JMenuBar bar
        JMenu menu = new JMenu("Click Here to Select Resolution");        // declare and initialize JMenu menu
        JMenuItem option1 = new JMenuItem(option1Text);     // declare and initialize JMenuItem option1
        JMenuItem option2 = new JMenuItem(option2Text);     // declare and initialize JMenuItem option2

        menu.setFont(trb);                                  // set the font of the menu to trb
        option1.setFont(trb);                               // set the font of option1 to trb
        option2.setFont(trb);                               // set the font of option2 to trb

        option1.addActionListener(this);                    // add actionListener to option1
        option2.addActionListener(this);                    // add actionListener to option2

        bar.add(menu);                                      // add menu to the bar
        menu.add(option1);                                  // add option1 to the menu
        menu.add(option2);                                  // add option2 to the menu
        add(bar);                                           // add bar to the JFrame

        requestFocusInWindow();                             // request focus in the frame
        setVisible(true);                                   // make the frame visible
    }

    public void actionPerformed(ActionEvent e)              // actionPerformed method header
    {
        String cmd = e.getActionCommand();                  // get the command as a String

        if(cmd.equals(option1Text))                         // if the command is option1...
        {
            game = new GameFrame(1);                        // create instance of game with scale 1
            this.dispose();                                 // dispose of this frame
        }
        else if (cmd.equals(option2Text))                   // else if the command is option2
        {
            game = new GameFrame(2);                        // create instance of game with scale 2
            this.dispose();                                 // dispose of this frame
        }
    }
}
