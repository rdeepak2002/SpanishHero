import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rdeep on 6/9/2017.
 */
public class WinPanel extends JPanel implements ActionListener                // game over class header
{
    private LevelSelect ls;

    private JButton levelSelectButton;

    private JLabel title;                                              // declare private JLabel title
    private CardLayout cardLayout;                                     // declare private CardLayout cardLayout
    private int scale;                                                 // declare private int scale

    public WinPanel(CardLayout cardLayout, LevelSelect ls, int frameWidth, int frameHeight)     // constructor to initialize field variables
    {
        this.cardLayout = cardLayout;
        this.ls = ls;

        scale = frameWidth/1000;
        setBackground(Color.BLACK);
        Font trb = new Font("TimesRoman", Font.BOLD, (int)(frameHeight/10));
        Font trb2 = new Font("TimesRoman", Font.BOLD, (int)(frameHeight/20));

        //setLayout(new FlowLayout(FlowLayout.CENTER));                    // set layout to null
        setLayout(null);

        title = new JLabel("YOU WIN!!!");
        levelSelectButton = new JButton("LEVEL SELECT");

        levelSelectButton.addActionListener(this);

        title.setFont(trb);
        levelSelectButton.setFont(trb2);

        int titleWidth = (int)(title.getPreferredSize().getWidth());
        int titleHeight = (int)(title.getPreferredSize().getHeight());

        int levelSelectButtonWidth = (int)(levelSelectButton.getPreferredSize().getWidth());
        int levelSelectButtonHeight = (int)(levelSelectButton.getPreferredSize().getHeight());

        title.setBounds(frameWidth/2-titleWidth/2, frameHeight/4, titleWidth, titleHeight);
        levelSelectButton.setBounds(frameWidth/2-levelSelectButtonWidth/2, frameHeight/4+150*scale, levelSelectButtonWidth, levelSelectButtonHeight);

        title.setForeground(Color.WHITE);
        title.setBackground(Color.BLACK);

        levelSelectButton.setForeground(Color.WHITE);
        levelSelectButton.setBackground(Color.BLACK);
        levelSelectButton.setBorder(new LineBorder(Color.BLACK));

        add(title);
        add(levelSelectButton);
    }

    public void actionPerformed(ActionEvent e)      // action performed method header
    {
        String cmd = e.getActionCommand();          // get the action command as a string

        if(cmd.equals("LEVEL SELECT"))                     // if retry button is clicked
        {
            cardLayout.show(super.getParent(), "Level Select"); // tell card layout to show Menu
            ls.requestFocus();
        }
    }
}
