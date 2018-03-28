import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rdeep on 6/9/2017.
 */
public class Menu extends JPanel implements ActionListener                     // Menu class header
{
    private int frameWidth;                                             // declare private int frameWidth
    private int frameHeight;                                            // declare private int frameHeight
    private int scale;                                                  // declare private int scale

    private Font trb = new Font("TimesRoman", Font.BOLD, (int)(frameHeight/10));    // declare and initialize font trb
    private Font trb2 = new Font("TimesRoman", Font.BOLD, (int)(frameHeight/20));   // declare and initialize font trb2

    private JButton button;                                             // declare and initialize JButton button
    private JLabel title;                                               // declare and initialize JLabel title
    private JLabel credit;                                              // declare and initialize JLabel credit
    private CardLayout cardLayout;                                      // declare and initialize CardLayout cardLayout
    private LevelSelect ls;

    public Menu(CardLayout cardLayout, LevelSelect ls, int frameWidth, int frameHeight) // constructor to initialize field variables
    {
        setBackground(Color.BLACK);

        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.cardLayout = cardLayout;
        this.ls = ls;
        scale = frameWidth/1000;

        trb = new Font("Verdana", Font.BOLD, (int)(frameHeight/10));
        trb2 = new Font("Verdana", Font.BOLD, (int)(frameHeight/20));

        setLayout(null);

        button = new JButton("START");
        title = new JLabel("SPANISH HERO");
        credit = new JLabel("By Deepak Ramalingam");

        button.addActionListener(this);

        title.setForeground(Color.WHITE);
        credit.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.BLACK));

        title.setFont(trb);
        button.setFont(trb2);
        credit.setFont(trb2);

        int titleWidth = (int)(title.getPreferredSize().getWidth());
        int titleHeight = (int)(title.getPreferredSize().getHeight());

        int creditWidth = (int)(credit.getPreferredSize().getWidth());
        int creditHeight = (int)(credit.getPreferredSize().getHeight());

        int buttonWidth = (int)(button.getPreferredSize().getWidth());
        int buttonHeight = (int)(button.getPreferredSize().getHeight());

        title.setBounds(frameWidth/2-titleWidth/2, frameHeight/4, titleWidth, titleHeight);
        button.setBounds(frameWidth/2-buttonWidth/2, frameHeight/4+100*scale, buttonWidth, buttonHeight);
        credit.setBounds(frameWidth/2-creditWidth/2, frameHeight/4+200*scale, creditWidth, creditHeight);

        add(title);
        add(button);
        add(credit);
    }

    public void actionPerformed(ActionEvent e)                          // action performed method header
    {
        String cmd = e.getActionCommand();                              // get the action command as a String

        if(cmd.equals("START"))                                         // if the command in start...
        {
            cardLayout.show(super.getParent(), "Level Select");         // change cardlayout to level select
            ls.requestFocus();
        }
    }
}
