import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by rdeep on 6/9/2017.
 */
public class GameOver extends JPanel implements ActionListener                // game over class header
{
    private LevelSelect ls;

    private JButton retryButton;                                            // declare private JButton button
    private JButton levelSelectButton;
    private JButton giveUpButton;

    private JLabel title;                                              // declare private JLabel title
    private CardLayout cardLayout;                                     // declare private CardLayout cardLayout
    private int scale;                                                 // declare private int scale

    public GameOver(CardLayout cardLayout, LevelSelect ls, int frameWidth, int frameHeight)     // constructor to initialize field variables
    {
        this.cardLayout = cardLayout;
        this.ls = ls;

        scale = frameWidth/1000;
        setBackground(Color.BLACK);
        Font trb = new Font("Verdana", Font.BOLD, (int)(frameHeight/10));
        Font trb2 = new Font("Verdana", Font.BOLD, (int)(frameHeight/20));

        //setLayout(new FlowLayout(FlowLayout.CENTER));                    // set layout to null
        setLayout(null);

        title = new JLabel("GAME OVER");
        retryButton = new JButton("RETRY");
        levelSelectButton = new JButton("LEVEL SELECT");
        giveUpButton = new JButton("GIVE UP");

        retryButton.addActionListener(this);     // add action listener to button
        levelSelectButton.addActionListener(this);
        giveUpButton.addActionListener(this);

        title.setFont(trb);
        retryButton.setFont(trb2);
        levelSelectButton.setFont(trb2);
        giveUpButton.setFont(trb2);

        int titleWidth = (int)(title.getPreferredSize().getWidth());
        int titleHeight = (int)(title.getPreferredSize().getHeight());

        int retryButtonWidth = (int)(retryButton.getPreferredSize().getWidth());
        int retryButtonHeight = (int)(retryButton.getPreferredSize().getHeight());

        int levelSelectButtonWidth = (int)(levelSelectButton.getPreferredSize().getWidth());
        int levelSelectButtonHeight = (int)(levelSelectButton.getPreferredSize().getHeight());

        int giveUpButtonWidth = (int)(giveUpButton.getPreferredSize().getWidth());
        int giveUpButtonHeight = (int)(giveUpButton.getPreferredSize().getHeight());

        title.setBounds(frameWidth/2-titleWidth/2, frameHeight/4, titleWidth, titleHeight);
        retryButton.setBounds(frameWidth/2-retryButtonWidth/2, frameHeight/4+100*scale, retryButtonWidth, retryButtonHeight);
        levelSelectButton.setBounds(frameWidth/2-levelSelectButtonWidth/2, frameHeight/4+150*scale, levelSelectButtonWidth, levelSelectButtonHeight);
        giveUpButton.setBounds(frameWidth/2-giveUpButtonWidth/2, frameHeight/4+200*scale, giveUpButtonWidth, giveUpButtonHeight);

        title.setForeground(Color.WHITE);
        title.setBackground(Color.BLACK);

        retryButton.setForeground(Color.WHITE);
        retryButton.setBackground(Color.BLACK);
        retryButton.setBorder(new LineBorder(Color.BLACK));

        levelSelectButton.setForeground(Color.WHITE);
        levelSelectButton.setBackground(Color.BLACK);
        levelSelectButton.setBorder(new LineBorder(Color.BLACK));

        giveUpButton.setForeground(Color.WHITE);
        giveUpButton.setBackground(Color.BLACK);
        giveUpButton.setBorder(new LineBorder(Color.BLACK));

        //compsToExperiment.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        add(title);
        add(retryButton);
        add(levelSelectButton);
        add(giveUpButton);
    }

    public void actionPerformed(ActionEvent e)      // action performed method header
    {
        String cmd = e.getActionCommand();          // get the action command as a string

        if(cmd.equals("RETRY"))                     // if retry button is clicked
        {
            cardLayout.show(super.getParent(), "Game"); // tell card layout to show Menu
        }
        if(cmd.equals("LEVEL SELECT"))                     // if retry button is clicked
        {
            cardLayout.show(super.getParent(), "Level Select"); // tell card layout to show Menu
            ls.requestFocus();
        }
        if(cmd.equals("GIVE UP"))                     // if retry button is clicked
        {
            cardLayout.show(super.getParent(), "Menu"); // tell card layout to show Menu
        }
    }
}
