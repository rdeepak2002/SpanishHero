import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by rdeep on 6/9/2017.
 */
public class QuestionFrame extends JFrame implements ActionListener, KeyListener    // QuestionFrame class header extends JFrame implements ActionListener
{
    private int level;
    private int frameWidth;                                 // declare private field variable frameWidth
    private int frameHeight;                                // declare private field variable frameHeight
    private int tries;

    private boolean multipleChoice;

    private String question;                                // declare private String question
    private String answer;                                  // declare private String answer
    private String stem;
    private String type;

    private JLabel questionLabel;                           // declare private JLabel questionLabel
    private JLabel verbLabel;
    private JLabel answerLabel;
    private JButton submit;                                 // declare private JButton submit
    private JTextArea textArea;
    private GamePanel gp;                                   // declare private GamePanel gp
    private String[] presentEndings;
    private String[] preteriteEndings;
    private String[] imperfectEndings;
    private String[] presentProgressiveEndings;
    private String[] futuroEndings;

    private ButtonGroup bg;                                 // declare private ButtonGroup gp

    private char accentE = '\u00e9';
    private char accentO = '\u00f3';
    private char accentI = '\u00ed';
    private char accentA = '\u00e1';
    private char accentU = '\u00fa';

    public QuestionFrame(int frameWidth, int frameHeight, GamePanel gp) // constructor
    {
        requestFocus();
        setVisible(false);
        setBackground(Color.BLACK);

        this.frameWidth = frameWidth;                       // initialize field variable
        this.frameHeight = frameHeight;                     // initialize field variable
        this.gp = gp;                                       // initialize field variable
        level = gp.getLevel();

        int scale = frameWidth/1000;
        tries = 0;

        Font trb;   // declare and initialize Font trb, Verdana Bold
        trb = new Font("Verdana", Font.BOLD, (int)(20*scale));

        question = "";                                      // initialize question
        answer = "";                                        // initialize answer
        type = "ar";
        multipleChoice = false;
        bg = new ButtonGroup();                             // initialize bg

        getQuestionAndAnswer();                             // call getQuestionAndAnswer

        textArea = new JTextArea("Type Here");
        verbLabel = new JLabel("");
        answerLabel = new JLabel("");

        if(type.equals("ar"))
        {
            presentEndings = new String[]{"o", "as", "a", "amos", accentA+"is", "an"};
            preteriteEndings = new String[]{""+accentE, "aste", ""+accentO, "amos", "asteis", "aron"};
            imperfectEndings = new String[]{"aba", "abas", "aba", accentA+"bamos", "abais", "aban"};
            presentProgressiveEndings = new String[]{"ado", "ado", "ado", "ado", "ado", "ado"};
            futuroEndings = new String[]{""+accentE, accentA+"s", ""+accentA, "emos", accentE+"is", accentA+"n"};
        }
        else if(type.equals("er"))
        {
            presentEndings = new String[]{"o", "es", "e", "emos", accentE+"is", "en"};
            preteriteEndings = new String[]{""+accentI, "iste", "i"+accentO, "imos", "isteis", "ieron"};
            imperfectEndings = new String[]{accentI+"a", accentI+"as", accentI+"a", accentI+"amos", accentI+"ais", accentI+"an"};
            presentProgressiveEndings = new String[]{"ido", "ido", "ido", "ido", "ido", "ido"};
            futuroEndings = new String[]{""+accentE, accentA+"s", ""+accentA, "emos", accentE+"is", accentA+"n"};
        }
        else
        {
            presentEndings = new String[]{"o", "es", "e", "imos", accentI+"s", "en"};
            preteriteEndings = new String[]{""+accentI, "iste", "i"+accentO, "imos", "isteis", "ieron"};
            imperfectEndings = new String[]{accentI+"a", accentI+"as", accentI+"a", accentI+"amos", accentI+"ais", accentI+"an"};
            presentProgressiveEndings = new String[]{"ido", "ido", "ido", "ido", "ido", "ido"};
            futuroEndings = new String[]{""+accentE, accentA+"s", ""+accentA, "emos", accentE+"is", accentA+"n"};
        }

        String[][] endings = new String[6][6];
        for(int r = 0; r < 6; r++)
        {
            for(int c = 0; c < 6; c++)
            {
                if(r == 0)
                {
                    endings[r][c] = presentEndings[c];
                }
                if(r == 1)
                {
                    endings[r][c] = preteriteEndings[c];
                }
                if(r == 2)
                {
                    endings[r][c] = imperfectEndings[c]; // imp
                }
                if(r == 3)
                {
                    endings[r][c] = presentEndings[c];  // fix
                }
                if(r == 4)
                {
                    endings[r][c] = presentProgressiveEndings[c]; // present progressive
                }
                if(r == 5)
                {
                    endings[r][c] = futuroEndings[c]; // futuro
                }
            }
        }

        questionLabel = new JLabel(question);               // initialize questionLabel
        submit = new JButton("SUBMIT");                     // initialize submit

        submit.addActionListener(this);                     // add actionListener to submit button
        questionLabel.setFont(trb);                         // set the font of questionLabel to trb
        verbLabel.setFont(trb);
        answerLabel.setFont(trb);
        submit.setFont(trb);                                // set the font of submit to trb
        submit.setBackground(Color.WHITE);

        answerLabel.setForeground(Color.RED);

        textArea.setFont(trb);

        setSize(frameWidth/3, frameHeight/2);               // set the size to frameWidth/4 and frameHeight/2
        setLocation(400+frameWidth-frameWidth/4, 100);      // set the location to 400 by 100
        setResizable(false);                                // set resizable to false
        setLayout(new GridLayout(6, 1));                    // set layout to new GirdLayout 6 rows, 1 column
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);      // do not do anything on close

        add(questionLabel);                                 // add questionLabel
        if(!multipleChoice)
        {
            answerLabel.setText("Answer: " + answer);
            answerLabel.setVisible(false);
            if(gp.getLevel() != 4)
                verbLabel.setText(stem + type);
            else
                verbLabel.setText(stem + " " + type);
            add(verbLabel);
            add(textArea);
            add(new accentButtonPanel());
            add(submit);                                        // add submit
            add(answerLabel);
        }

        setVisible(true);                                   // make frame visible
        gp.setPause(true);									// pause game
        textArea.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e)              // actionPerformed method header
    {
        String command = e.getActionCommand();              // store the command in a String
        if(command.equals("SUBMIT") || command.equals("TRY AGAIN!"))                        // if the command is "SUBMIT"...
        {
            if(!multipleChoice)
            {
                String text = (textArea.getText()).trim();
                if(text.equalsIgnoreCase(answer) || text.equals("S3cReT"))
                {
                    this.dispose();                                             // dispose of frame
                    gp.setQuestionsAnswered(gp.getQuestionsAnswered() + 1);
                    System.out.println(gp.getScore() + "   " + gp.getQuestionsAnswered() * 10);
                    gp.setHoldingDown(false);
                    gp.setHoldingZ(false);
                    if (gp.getQuestionsAnswered() * 10 == gp.getScore())
                        gp.setPause(false);                                         // set pause to false
                    tries = 0;
                }
                else
                {
                    if (gp.getPlayerHealth() > 0)
                        gp.setPlayerHealth(gp.getPlayerHealth() - 10);
                    submit.setForeground(Color.RED);
                    submit.setText("TRY AGAIN!");
                    tries+=1;
                    if(tries > 2)
                    {
                        answerLabel.setVisible(true);
                    }
                    repaint();
                }
            }
        }
    }

    public void getQuestionAndAnswer()                      // getQuestionAndAnswer method header
    {
        String data = "";                                   // declare and initialize String data to blank String

        Scanner reader = null;                              // declare and initialize Scanner reader to null
        File levelFile = new File("LevelData/Questions.dat");         // declare and initialize File levelFile to a new File

        try                                                 // try catch block to read Questions.dat
        {
            reader = new Scanner(levelFile);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("\n\n" + "LevelData/Questions.dat" + " can't be found.\n\n");
            System.exit(1);
        }

        while(reader.hasNext())                             // while the file has more contents...
        {
            String line = reader.nextLine();

            while(line.indexOf("a'")!=-1)                   // convert any letter with a " ' " after it to an accent character in unicode
                line = line.replace("a'", accentA+"");
            while(line.indexOf("e'")!=-1)
                line = line.replace("e'", accentE+"");
            while(line.indexOf("i'")!=-1)
                line = line.replace("i'", accentI+"");
            while(line.indexOf("o'")!=-1)
                line = line.replace("o'", accentO+"");
            while(line.indexOf("u'")!=-1)
                line = line.replace("u'", accentU+"");

            data+=line;                        // append each line to the data String
        }

        int numQuestions = 10;           // CHANGE THIS!!!

        if(level==4)
            numQuestions = 13;
        else
            numQuestions = 20;

        System.out.println("numquestions: " + numQuestions);
        System.out.println("level: " + level);

        if(gp.getLevel() == 1)
            data = data.substring(data.indexOf("PRESENT"), data.indexOf("PRESENT /"));
        else if (gp.getLevel() == 2)
            data = data.substring(data.indexOf("PRETERITE"), data.indexOf("PRETERITE /"));
        else if (gp.getLevel() == 3)
            data = data.substring(data.indexOf("IMPERFECT"), data.indexOf("IMPERFECT /"));
        else if (gp.getLevel() == 4)
            data = data.substring(data.indexOf("ImpVSPret"), data.indexOf("ImpVSPret /"));
        else if (gp.getLevel() == 5)
            data = data.substring(data.indexOf("PRESENTPROGRESSIVE"), data.indexOf("PRESENTPROGRESSIVE /"));
        else if (gp.getLevel() == 6)
            data = data.substring(data.indexOf("FUTURO"), data.indexOf("FUTURO /"));

        int questionNumber = (int)((Math.random()*numQuestions)+1);

        int[] tempArray;

        while(arrayContains(gp.getQuestionArray(), questionNumber))
        {
            questionNumber = (int) ((Math.random() * numQuestions) + 1);
        }

        tempArray = gp.getQuestionArray();

        tempArray[gp.getQuestionArrayIndex()] = questionNumber;

        gp.setQuestionArray(tempArray);

        gp.setQuestionArrayIndex(gp.getQuestionArrayIndex()+1);

        System.out.println("Questions answered: " + Arrays.toString(gp.getQuestionArray()));

        if(gp.getQuestionArrayIndex() == gp.getQuestionArray().length)
        {
            tempArray = new int[20];
            for(int i = 0; i<tempArray.length; i++)
            {
                tempArray[i] = -1;
            }
            gp.setQuestionArray(tempArray);
            gp.setQuestionArrayIndex(0);
            System.out.println("Restarting Array");
        }

        question = data.substring(data.indexOf("Q" + questionNumber)+4, data.indexOf("/", data.indexOf("Q" + questionNumber))).trim();  // make question a substring of Q1 to /
        answer = data.substring(data.indexOf("A" + questionNumber)+4, data.indexOf("/", data.indexOf("A" + questionNumber))).trim();    // make answer a substring of A1 to /
        stem = data.substring(data.indexOf("R" + questionNumber)+4, data.indexOf("/", data.indexOf("R" + questionNumber))).trim();
        type = data.substring(data.indexOf("T" + questionNumber)+4, data.indexOf("/", data.indexOf("T" + questionNumber))).trim();
    }

    public boolean arrayContains(int[] intArray, int num)
    {
        boolean contains = false;
        for(int i = 0; i < intArray.length; i++)
        {
            if(intArray[i] == num)
            {
                contains = true;
            }
        }
        return contains;
    }

    public void keyTyped(KeyEvent e)
    {
        char keyTyped = e.getKeyChar();
        int keyPressed = e.getKeyCode();

        if(textArea.getText().equals("Type Here"))
        {
            textArea.setText("");
        }

        if(e.getKeyCode() == e.VK_ENTER)
        {
            if(!multipleChoice)
            {
                String text = (textArea.getText()).trim();
                if(text.equalsIgnoreCase(answer) || text.equals("S3cReT"))
                {
                    this.dispose();                                             // dispose of frame
                    gp.setQuestionsAnswered(gp.getQuestionsAnswered() + 1);
                    System.out.println(gp.getScore() + "   " + gp.getQuestionsAnswered() * 10);
                    gp.setHoldingDown(false);
                    gp.setHoldingZ(false);
                    if (gp.getQuestionsAnswered() * 10 == gp.getScore())
                        gp.setPause(false);                                         // set pause to false
                    tries = 0;
                }
                else
                {
                    if (gp.getPlayerHealth() > 0)
                        gp.setPlayerHealth(gp.getPlayerHealth() - 10);
                    submit.setForeground(Color.RED);
                    submit.setText("TRY AGAIN!");
                    tries+=1;
                    if(tries > 2)
                    {
                        answerLabel.setVisible(true);
                    }
                    repaint();
                }
            }
        }
    }

    public void keyPressed(KeyEvent e)
    {
        char keyTyped = e.getKeyChar();
        int keyPressed = e.getKeyCode();

        if(textArea.getText().equals("Type Here"))
        {
            textArea.setText("");
        }

        if(e.getKeyCode() == e.VK_ENTER)
        {
            if(!multipleChoice)
            {
                String text = (textArea.getText()).trim();
                if(text.equalsIgnoreCase(answer))
                {
                    this.dispose();                                             // dispose of frame
                    gp.setQuestionsAnswered(gp.getQuestionsAnswered() + 1);
                    System.out.println(gp.getScore() + "   " + gp.getQuestionsAnswered() * 10);
                    gp.setHoldingDown(false);
                    gp.setHoldingZ(false);
                    if (gp.getQuestionsAnswered() * 10 == gp.getScore())
                        gp.setPause(false);                                         // set pause to false
                    tries = 0;
                }
                else
                {
                    if (gp.getPlayerHealth() > 0)
                        gp.setPlayerHealth(gp.getPlayerHealth() - 10);
                    submit.setForeground(Color.RED);
                    submit.setText("TRY AGAIN!");
                    tries+=1;
                    if(tries > 2)
                    {
                        answerLabel.setVisible(true);
                    }
                    repaint();
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {}

    class accentButtonPanel extends JPanel implements ActionListener
    {
        private JButton eButton, aButton, oButton, iButton;

        public accentButtonPanel()
        {
            setLayout(new GridLayout(1, 4));
            Font trb;   // declare and initialize Font trb, Verdana Bold
            trb = new Font("Verdana", Font.BOLD, (int)(20*(frameWidth/1000)));

            iButton = new JButton(""+accentI);
            aButton = new JButton(""+accentA);
            eButton = new JButton(""+accentE);
            oButton = new JButton(""+accentO);

            iButton.addActionListener(this);
            aButton.addActionListener(this);
            eButton.addActionListener(this);
            oButton.addActionListener(this);

            iButton.setFont(trb);
            aButton.setFont(trb);
            eButton.setFont(trb);
            oButton.setFont(trb);

            iButton.setBackground(Color.WHITE);
            aButton.setBackground(Color.WHITE);
            eButton.setBackground(Color.WHITE);
            oButton.setBackground(Color.WHITE);

            add(iButton);
            add(aButton);
            add(oButton);
            add(eButton);
        }

        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();

            if(command.equals(iButton.getText()))
            {
                textArea.setText(textArea.getText()+accentI);
                textArea.requestFocus();
            }
            else if(command.equals(aButton.getText()))
            {
                textArea.setText(textArea.getText()+accentA);
                textArea.requestFocus();
            }
            else if(command.equals(eButton.getText()))
            {
                textArea.setText(textArea.getText()+accentE);
                textArea.requestFocus();
            }
            else if(command.equals(oButton.getText()))
            {
                textArea.setText(textArea.getText()+accentO);
                textArea.requestFocus();
            }
        }
    }
}
