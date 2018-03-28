import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by rdeep on 6/9/2017.
 */

public class GamePanel extends JPanel implements KeyListener           // GamePanel class header
{
    final JFXPanel fxPanel = new JFXPanel();

    private final double GRAVITYINITIAL = 8.0;
    private final double VELOCITYINITIAL = -22.0;

    private final String SHOOTSOUND = "SFX/shoot.mp3";

    private int frameWidth, frameHeight;                        // declare necessary field variables

    private Image playerImage;
    private Image healthBarImage;
    private Image block1Image;
    private Image block1BGImage;
    private Image block1BGLineImage;
    private Image ladderImage;
    private Image movingBlock1Image;

    private MediaPlayer mediaPlayer;
    private Media hit;

    private String globalLevelData;

    private int level;
    private int time;
    private int enemyUpdateTime;
    private int scale;
    private int questionsAnswered;
    private int questionArrayIndex;

    private boolean mouseClicked;
    private boolean showQuestionPanel;
    private boolean pause;
    private boolean bossFight;
    private boolean help;
    private boolean holdingZ;
    private boolean holdingDown;
    private boolean levelInit;

    private int[][] levelLayout;
    private int[] yellowDevilBlastsFired;
    private int[] questionArray;

    private Blast[] blasts;
    private Enemy[] enemies;
    private Enemy[] yellowDevilBlasts;
    private Block[] dynamicBlocks;
    private Enemy yellowDevil;
    private Timer timer;

    private double velocity = VELOCITYINITIAL;
    private double gravity = GRAVITYINITIAL;

    private String jumpDebug;

    private CardLayout cardLayout;
    private LevelSelect ls;

    private Player player;
    private Mover mover;

    public GamePanel(CardLayout cardLayout, LevelSelect ls, int frameWidth, int frameHeight)    // constructor to initialize field variables
    {
        setBackground(Color.BLACK);

        this.cardLayout = cardLayout;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.ls = ls;

        time = 0;
        level = 0;
        scale = frameWidth/1000;
        questionsAnswered = 0;
        questionArrayIndex = 0;

        mouseClicked = false;
        showQuestionPanel = false;
        pause = false;
        bossFight = false;
        help = false;
        holdingZ = false;
        holdingDown = false;
        levelInit = false;

        levelLayout = new int[1100][1100];

        mover = new Mover(this, player);

        player = new Player(frameWidth, frameHeight);

        yellowDevil = new Enemy(this, player, 2000*scale + 200*scale, 700*scale, frameWidth, frameHeight, -1);

        jumpDebug = "NULL";

        globalLevelData = readLevelData();

        questionArray = new int[20];

        for(int i = 0; i < questionArray.length; i ++)
        {
            questionArray[i] = -1;
        }

        yellowDevilBlastsFired = new int[9];
        for(int i = 0; i < yellowDevilBlastsFired.length; i++)
        {
            yellowDevilBlastsFired[i] = -1;
        }

        dynamicBlocks = new Block[3];
        for(int i = 0; i < dynamicBlocks.length; i++)
        {
            dynamicBlocks[i] = new Block(frameWidth, frameHeight, 0, 0);
        }

        checkForDynamicBlocks();

        enemies = new Enemy[5];
        for (int i = 0; i<enemies.length; i++)                      // for loop to populate array
        {
            enemies[i] = new Enemy(this, player, 2100*scale + i*300*scale, 700*scale, frameWidth, frameHeight, 1);
        }

        checkForEnemies();

        yellowDevilBlasts = new Enemy[9];
        for (int i = 0; i<yellowDevilBlasts.length; i++)                      // for loop to populate array
        {
            yellowDevilBlasts[i] = new Enemy(this, player, yellowDevil.getPosX(), yellowDevil.getPosY(), frameWidth, frameHeight, 2);
        }

        blasts = new Blast[100];
        for (int i = 0; i<blasts.length; i++)                       // for loop to populate array
        {
            blasts[i] = new Blast(player, frameWidth, frameHeight);
        }

        playerImage = player.getPlayerImage();
        healthBarImage = player.getHealthBarImage();
        enemyUpdateTime = (int) (System.currentTimeMillis());

        getLevelImages();

        addKeyListener(this);                               // add a key listener
        requestFocus();                                     // request focus
    }

    public void checkForDynamicBlocks()
    {
        String levelData = "";
        int numBlocks = 0;

        Scanner reader = null;
        File levelFile = new File("LevelData/Levels.dat");

        try
        {
            reader = new Scanner(levelFile);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("\n\n" + "LevelData/Levels.dat" + " can't be found.\n\n");
            System.exit(1);
        }

        while(reader.hasNext())
        {
            levelData += reader.nextLine();
            levelData += "n\n";
        }

        if(level == 1)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL ONE"), levelData.indexOf("LEVEL ONE /"));
        }
        else if(level == 2)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL TWO"), levelData.indexOf("LEVEL TWO /"));
        }
        else if(level == 3)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL THREE"), levelData.indexOf("LEVEL THREE /"));
        }
        else if(level == 4)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL FOUR"), levelData.indexOf("LEVEL FOUR /"));
        }
        else if(level == 5)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL FIVE"), levelData.indexOf("LEVEL FIVE /"));
        }
        else if(level == 6)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL SIX"), levelData.indexOf("LEVEL SIX /"));
        }
        else if(level == 0)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL TUTORIAL"), levelData.indexOf("LEVEL TUTORIAL /"));
        }

        String levelDataTemp = "";
        levelDataTemp = levelData;

        while(levelDataTemp.indexOf("!")!=-1)
        {
            numBlocks+=1;
            levelDataTemp = levelDataTemp.substring(levelDataTemp.indexOf("!")+1);
        }

        dynamicBlocks = new Block[numBlocks];

        Scanner blockReader = null;

        try
        {
            blockReader = new Scanner(levelFile);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("\n\n" + "LevelData/Levels.dat" + " can't be found.\n\n");
            System.exit(1);
        }

        int row = 1;
        int blockIndex = 0;

        if(level > 0)
        {
            for (int i = 0; i < (level-1)*50; i++)
            {
                blockReader.nextLine();
            }
        }

        for(int i = 0; i < 51; i ++)
        {
            String curLine = blockReader.nextLine();
            row+=1;
            while(curLine.indexOf("!")!=-1 && level != 0)
            {
                dynamicBlocks[blockIndex] = new Block(frameWidth, frameHeight, curLine.indexOf("!")*50*scale, (row-2)*50*scale);
                System.out.println("Block generated at: " + curLine.indexOf("!")*50*scale + ", " + (row-2)*50*scale);
                dynamicBlocks[blockIndex].setStartX((curLine.indexOf("!")+1)*50*scale);
                dynamicBlocks[blockIndex].setEndX((curLine.indexOf("@")-1)*50*scale);
                blockIndex += 1;
                //curLine = curLine.substring(0, curLine.indexOf("@")) + "w" + curLine.substring(curLine.indexOf("@")+1, curLine.length()-1);
                curLine = curLine.replaceFirst("@", "w");
                curLine = curLine.replaceFirst("!", "w");
            }
        }

        reader.reset();
        blockReader.reset();

        System.out.println("Final Num: " + numBlocks);
    }

    public void checkForEnemies()
    {
        String levelData = "";
        int numEnemies = 0;

        Scanner reader = null;
        File levelFile = new File("LevelData/Levels.dat");

        try
        {
            reader = new Scanner(levelFile);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("\n\n" + "LevelData/Levels.dat" + " can't be found.\n\n");
            System.exit(1);
        }

        while(reader.hasNext())
        {
            levelData += reader.nextLine();
            levelData += "n\n";
        }

        if(level == 1)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL ONE"), levelData.indexOf("LEVEL ONE /"));
        }
        else if(level == 2)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL TWO"), levelData.indexOf("LEVEL TWO /"));
        }
        else if(level == 3)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL THREE"), levelData.indexOf("LEVEL THREE /"));
        }
        else if(level == 4)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL FOUR"), levelData.indexOf("LEVEL FOUR /"));
        }
        else if(level == 5)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL FIVE"), levelData.indexOf("LEVEL FIVE /"));
        }
        else if(level == 6)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL SIX"), levelData.indexOf("LEVEL SIX /"));
        }
        else if(level == 0)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL TUTORIAL"), levelData.indexOf("LEVEL TUTORIAL /"));
        }

        String levelDataTemp = "";
        levelDataTemp = levelData;

        while(levelDataTemp.indexOf("f")!=-1)
        {
            numEnemies+=1;

            levelDataTemp = levelDataTemp.substring(levelDataTemp.indexOf("f")+1);
        }

        enemies = new Enemy[numEnemies];

        Scanner enemyReader = null;

        try
        {
            enemyReader = new Scanner(levelFile);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("\n\n" + "LevelData/Levels.dat" + " can't be found.\n\n");
            System.exit(1);
        }

        int row = 1;
        int enemyIndex = 0;


        if(level > 0)
        {
            for (int i = 0; i < (level-1)*50; i++)
            {
                enemyReader.nextLine();
            }
        }

        for(int i = 0; i < 51; i ++)
        {
            String curLine = enemyReader.nextLine();
            row+=1;
            while(curLine.indexOf("f")!=-1 && level != 0)
            {
                enemies[enemyIndex] = new Enemy(this, player, curLine.indexOf("f")*50*scale, row*50*scale, frameWidth, frameHeight, 1);
                System.out.println("Enemy generated at: " + curLine.indexOf("f")*50*scale + ", " + row*50*scale);
                enemyIndex += 1;
                curLine = curLine.substring(0, curLine.indexOf("f")) + "w" + curLine.substring(curLine.indexOf("f")+1, curLine.length()-1);;
            }
        }

        reader.reset();
        enemyReader.reset();

        System.out.println("Final Num: " + numEnemies);
    }

    public void paintComponent(Graphics g)                  // paint component method header
    {
        super.paintComponent(g);                            // clear background

        player.setCurTime((int) System.currentTimeMillis());    // get the current time

        if(levelInit == false)
        {
            initLevel(g);                                       // initialize level
            levelInit = true;
        }

        loadLevel(g);                                       // call load level
        updateMovingBlocks(g);                              // call update moving blocks
        getAnimation(g);                                    // call get animation
        getBlastGraphics(g);                                // call getBlastGraphics
        manageText(g);                                      // call manage text

        for (int i = 0; i < enemies.length; i++)            // for loop to draw all enemies
        {
            if (enemies[i].getHealth() > 0)
            {
                g.drawImage(enemies[i].getImage(), enemies[i].getPosX() - player.getWorldPosition(), enemies[i].getPosY() - player.getWorldPositionY(), enemies[i].getWidth(), enemies[i].getHeight(), this);
            }
            if(bossFight)
                enemies[i].setHealth(0);
        }

        for (int i = 0; i < yellowDevilBlasts.length; i++)            // for loop to draw all enemies
        {
            if (yellowDevilBlasts[i].getHealth() > 0)
            {
                if(yellowDevil.isAttacked())
                    g.setColor(new Color(252, 152, 56));
                else
                    g.setColor(new Color(165, 0, 165));
                if(yellowDevilBlasts[i].isShotActive() && yellowDevil.getHealth()>0 && bossFight)
                    g.fillOval(yellowDevilBlasts[i].getPosX() - player.getWorldPosition(), yellowDevilBlasts[i].getPosY() - player.getWorldPositionY(), yellowDevilBlasts[i].getWidth(), yellowDevilBlasts[i].getHeight());
            }
        }

        if (yellowDevil.getHealth() > 0 && bossFight && (level == 1 || level == 2 || level == 3 || level == 4 || level == 5 || level == 6))
        {
            g.setColor(Color.YELLOW);
            g.drawImage(yellowDevil.getImage(), yellowDevil.getPosX() - player.getWorldPosition(), yellowDevil.getPosY() - player.getWorldPositionY(), yellowDevil.getWidth(), yellowDevil.getHeight(), this);
        }

        if (player.getMaxHealth() - player.getHealth() >= 0)    // draw player health bar
        {
            g.drawImage(healthBarImage, frameWidth / 100, frameHeight / 100, frameWidth / 25, (int) (frameHeight / 2.5), this);
            g.setColor(Color.BLACK);
            g.fillRect(frameWidth / 100, frameHeight / 100, frameWidth / 25, (int) (((player.getMaxHealth() - player.getHealth()) / 100.0) * ((frameHeight / 2.5))));
        }

        if (player.getMaxHealth() - player.getHealth() >= 0 && yellowDevil.getHealth()>0 && bossFight)    // draw player health bar
        {
            g.setColor(Color.RED);
            g.fillRect((int)(frameWidth - frameWidth / 100 - frameWidth / 25), frameHeight / 100, frameWidth / 25, (int) (frameHeight / 2.5));
            g.setColor(Color.BLACK);
            g.fillRect((int)(frameWidth - frameWidth / 100 - frameWidth / 25), frameHeight / 100, frameWidth / 25, (int)(((((double)yellowDevil.getMaxHealth()-(double)yellowDevil.getHealth()) / (double)yellowDevil.getMaxHealth())) * ((frameHeight / 2.5))));
        }
    }

    public void getLevelImages()                            // getLevelImages method header
    {
        try {                                               // try catch block to get block1 image
            block1Image = ImageIO.read(new File("Resources/Block1.png"));
        } catch (IOException e) {
            System.err.println("\n\n" + "Block1.png" + " can't be found.\n\n");
            e.printStackTrace();
        }

        try {                                               // try catch block to get block1BG image
            block1BGImage = ImageIO.read(new File("Resources/Block1BG.png"));
        } catch (IOException e) {
            System.err.println("\n\n" + "Block1BG.png" + " can't be found.\n\n");
            e.printStackTrace();
        }

        try {                                               // try catch block to get block1BG image
            block1BGLineImage = ImageIO.read(new File("Resources/Block1BGLine.png"));
        } catch (IOException e) {
            System.err.println("\n\n" + "Block1BGLine.png" + " can't be found.\n\n");
            e.printStackTrace();
        }

        try {                                               // try catch block to get block1BG image
            movingBlock1Image = ImageIO.read(new File("Resources/MovingBlock1.png"));
        } catch (IOException e) {
            System.err.println("\n\n" + "MovingBlock1.png" + " can't be found.\n\n");
            e.printStackTrace();
        }

        try {                                               // try catch block to get block1BG image
            ladderImage = ImageIO.read(new File("Resources/Ladder.png"));
        } catch (IOException e) {
            System.err.println("\n\n" + "Ladder.png" + " can't be found.\n\n");
            e.printStackTrace();
        }
    }

    public String readLevelData()
    {
        String levelData = "";

        Scanner reader = null;
        File levelFile = new File("LevelData/Levels.dat");

        try {                                               // try catch block to read from Levels.dat
            reader = new Scanner(levelFile);
        } catch (FileNotFoundException e) {
            System.err.println("\n\n" + "LevelData/Levels.dat" + " can't be found.\n\n");
            System.exit(1);
        }

        for (int i = 1; i < 999; i++)                   // for loop to read the first 999 lines of the file
        {
            String line = "";
            if (reader.hasNext())
                line = reader.nextLine();
            levelData += line;
            levelData += "n";
        }

        return levelData;
    }

    public String getCurrentLevelNumber()
    {
        String levelData = globalLevelData;

        level = ls.getLevel();

        if(level == 1)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL ONE"), levelData.indexOf("LEVEL ONE /"));
        }
        else if(level == 2)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL TWO"), levelData.indexOf("LEVEL TWO /"));
        }
        else if(level == 3)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL THREE"), levelData.indexOf("LEVEL THREE /"));
        }
        else if(level == 4)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL FOUR"), levelData.indexOf("LEVEL FOUR /"));
        }
        else if(level == 5)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL FIVE"), levelData.indexOf("LEVEL FIVE /"));
        }
        else if(level == 6)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL SIX"), levelData.indexOf("LEVEL SIX /"));
        }
        else if(level == 0)
        {
            levelData = levelData.substring(levelData.indexOf("LEVEL TUTORIAL"), levelData.indexOf("LEVEL TUTORIAL /"));
        }

        return levelData;
    }

    public void initLevel(Graphics g)
    {
        String levelData = getCurrentLevelNumber();

        int x = 0;              // integers to keep track of block cursor position
        int y = 0;

        int row = 0;            // integers to keep track of array cursor position
        int col = 0;

        for (int i = 0; i < levelData.length(); i++)    // for loop to iterate through all of level data
        {
            char block = levelData.charAt(i);           // store the character to determine the block needed

            if (block == '!' || block == '@')
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);

                x += frameWidth / 20;
                levelLayout[row][col] = 0;
                col += 1;
            }

            if (block == '>')
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGLineImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = 0;
                col += 1;
            }


            if (block == 'b')                           // b blocks are blank
            {
                x += frameWidth / 20;
                levelLayout[row][col] = 0;
                col += 1;
            }
            else if (block == 'B')                           // b blocks are blank
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = -4;
                col += 1;
            }
            else if (block == 'n')                      // n blocks are blank
            {
                x = 0;
                y += frameHeight / 12;
                levelLayout[row][col] = 2;
                row += 1;
                col = 1;
            }
            else if (block == '_')                      // _ blocks are walls
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1Image, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = 1;
                col += 1;
            }
            else if (block == 'w' || block == '1' || block == '2' || block == 'f' || block == 'i' || block == '%' || block == 'j' || block == 'x' || block == 'a' || block == 's')                      // w blocks are the background
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = 0;
                col += 1;
                g.setColor(Color.WHITE);
                g.setFont(new Font("Verdana", Font.BOLD, frameHeight/20));
                if(block == 's' && !mouseClicked)
                {
                    player.setWorldPosition(x-player.getPlayerX());
                    player.setWorldPositionY(y-10*scale);
                }
                if (block == '1')
                {
                    if(yellowDevil.isOnRightSide())
                        yellowDevil.setPosX(player.getWorldPosition());
                    else
                        yellowDevil.setPosX(player.getWorldPosition()+frameWidth-yellowDevil.getWidth());
                    yellowDevil.setPosY(y+yellowDevil.getHeight());
                }
                else if (block == '%')
                {
                    g.drawString("Hold up to climb up ladders. Hold down to go down loadders.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
                else if (block == 'j')
                {
                    g.drawString("HOLD up to jump higher. Longer hold = higher jump.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
                else if (block == 'x')
                {
                    g.drawString("Press x to shoot.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
                else if (block == 'a')
                {
                    g.drawString("Use arrow keys to move.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
            }
            else if (block == 'u')
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(ladderImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = -1;
                col += 1;
            }
            else if (block == 'l')                      // l blocks are lava
            {
                g.setColor(Color.ORANGE);
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.fillRect(x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12);
                x += frameWidth / 20;
                levelLayout[row][col] = 3;
                col += 1;
            }
            else if (block == '*' || block == '&')      // * and & blocks are door blocks
            {
                if (player.getScore()<enemies.length*10)
                {
                    g.setColor(Color.RED);
                    if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                        g.fillRect(x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12);
                    x += frameWidth / 20;
                    levelLayout[row][col] = 1;
                    col += 1;
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Verdana", Font.BOLD, frameHeight/20));
                    if(block == '*')
                        g.drawString("Need " + (enemies.length*10 - player.getScore()) + " more points!", x - player.getWorldPosition(), y - player.getWorldPositionY()+ player.getPlayerY());
                }
                else if(!bossFight)
                {
                    g.setColor(Color.BLUE);
                    if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                        g.fillRect(x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12);
                    x += frameWidth / 20;
                    levelLayout[row][col] = 0;
                    col += 1;
                }
                else
                {
                    g.drawImage(block1Image, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                    x += frameWidth / 20;
                    levelLayout[row][col] = 1;
                    col += 1;
                }
            }
        }
    }


    public void loadLevel(Graphics g)
    {
        String levelData = getCurrentLevelNumber();

        int x = 0;              // integers to keep track of block cursor position
        int y = 0;

        int row = 0;            // integers to keep track of array cursor position
        int col = 0;

        for (int i = 0; i < levelData.length(); i++)    // for loop to iterate through all of level data
        {
            char block = levelData.charAt(i);           // store the character to determine the block needed

            if (block == '!' || block == '@')
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);

                x += frameWidth / 20;
                col += 1;
            }

            if (block == '>')
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGLineImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = 0;
                col += 1;
            }


            if (block == 'b')                           // b blocks are blank
            {
                x += frameWidth / 20;
                levelLayout[row][col] = 0;
                col += 1;
            }
            else if (block == 'B')                           // b blocks are blank
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = -4;
                col += 1;
            }
            else if (block == 'n')                      // n blocks are blank
            {
                x = 0;
                y += frameHeight / 12;
                levelLayout[row][col] = 2;
                row += 1;
                col = 1;
            }
            else if (block == '_')                      // _ blocks are walls
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1Image, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = 1;
                col += 1;
            }
            else if (block == 'w' || block == '1' || block == '2' || block == 'f' || block == 'i' || block == '%' || block == 'j' || block == 'x' || block == 'a' || block == 's')                      // w blocks are the background
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(block1BGImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = 0;
                col += 1;
                g.setColor(Color.WHITE);
                g.setFont(new Font("Verdana", Font.BOLD, frameHeight/20));
                if(block == 's' && !mouseClicked)
                {
                    player.setWorldPosition(x-player.getPlayerX());
                    player.setWorldPositionY(y-10*scale);
                }
                if (block == '1')
                {
                    if(yellowDevil.isOnRightSide())
                        yellowDevil.setPosX(player.getWorldPosition());
                    else
                        yellowDevil.setPosX(player.getWorldPosition()+frameWidth-yellowDevil.getWidth());
                    yellowDevil.setPosY(y+yellowDevil.getHeight());
                }
                else if (block == '%')
                {
                    g.drawString("Hold up to climb up ladders. Hold down to go down loadders.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
                else if (block == 'j')
                {
                    g.drawString("HOLD up to jump higher. Longer hold = higher jump.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
                else if (block == 'x')
                {
                    g.drawString("Press x to shoot.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
                else if (block == 'a')
                {
                    g.drawString("Use arrow keys to move.", x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY());
                }
            }
            else if (block == 'u')
            {
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.drawImage(ladderImage, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                x += frameWidth / 20;
                levelLayout[row][col] = -1;
                col += 1;
            }
            else if (block == 'l')                      // l blocks are lava
            {
                g.setColor(Color.ORANGE);
                if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                    g.fillRect(x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12);
                x += frameWidth / 20;
                levelLayout[row][col] = 3;
                col += 1;
            }
            else if (block == '*' || block == '&')      // * and & blocks are door blocks
            {
                if (player.getScore()<enemies.length*10)
                {
                    g.setColor(Color.RED);
                    if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                        g.fillRect(x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12);
                    x += frameWidth / 20;
                    levelLayout[row][col] = 1;
                    col += 1;
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Verdana", Font.BOLD, frameHeight/20));
                    if(block == '*')
                        g.drawString("Need " + (enemies.length*10 - player.getScore()) + " more points!", x - player.getWorldPosition(), y - player.getWorldPositionY()+ player.getPlayerY());
                }
                else if(!bossFight)
                {
                    g.setColor(Color.BLUE);
                    if (x - player.getWorldPosition() > -100 && x - player.getWorldPosition() < 2100 && y - player.getWorldPositionY() + player.getPlayerY() > -100 && y - player.getWorldPositionY() + player.getPlayerY() < 1300)
                        g.fillRect(x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12);
                    x += frameWidth / 20;
                    levelLayout[row][col] = 0;
                    col += 1;
                }
                else
                {
                    g.drawImage(block1Image, x - player.getWorldPosition(), y - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);
                    x += frameWidth / 20;
                    levelLayout[row][col] = 1;
                    col += 1;
                }
            }
        }
    }

    public void getBlastGraphics(Graphics g)        // getBlastGraphics method
    {
        for (int r = 0; r<yellowDevilBlasts.length; r++)                      // for loop to populate array
        {
            if(yellowDevil.getHealth()>0 && (level == 1 || level == 2 || level == 3 || level == 4 || level == 5 || level == 6))
            {
                if (!yellowDevil.hasShot())
                {
                    yellowDevilBlasts[r].setShotActive(false);

                    if (r < yellowDevilBlasts.length / 3)
                        yellowDevilBlasts[r].setPosY(yellowDevil.getPosY() + 2 * yellowDevil.getHeight() / 3);
                    else if (r < 2 * yellowDevilBlasts.length / 3)
                        yellowDevilBlasts[r].setPosY(yellowDevil.getPosY() + yellowDevil.getHeight() / 3);
                    else
                        yellowDevilBlasts[r].setPosY(yellowDevil.getPosY());

                    if (!yellowDevil.isOnRightSide())
                        yellowDevilBlasts[r].setPosX(player.getWorldPosition() + yellowDevil.getWidth());
                    else
                        yellowDevilBlasts[r].setPosX(player.getWorldPosition() + frameWidth - yellowDevil.getWidth());

                    yellowDevil.setRandBlast((int) (Math.random() * yellowDevilBlasts.length));
                }
                else
                {
                    if(!pause && bossFight)
                    {
                        int curTimer = ((int) (System.currentTimeMillis() - yellowDevil.getAnimationTimer())) / 1000;
                        int curTimerMillis = ((int) (System.currentTimeMillis() - yellowDevil.getAnimationTimer()));

                        if (yellowDevil.isOnRightSide())
                            yellowDevilBlasts[r].setBulletDirection(1);
                        else
                            yellowDevilBlasts[r].setBulletDirection(-1);

                        for (int i = 0; i < yellowDevilBlasts.length; i++) {
                            if (curTimerMillis % 10000 < 500) {
                                for (int s = 0; s < yellowDevilBlastsFired.length; s++) {
                                    yellowDevilBlastsFired[s] = -1;
                                }
                            }

                            if (yellowDevilBlastsFired[i] != -1) {
                                yellowDevilBlasts[yellowDevilBlastsFired[i]].setShotActive(true);
                            }

                            if (curTimer % 10 - 1 >= i) {
                                if (yellowDevilBlastsFired[i] == -1) {
                                    int randInt = (int) (Math.random() * 9);
                                    while (arrayContains(yellowDevilBlastsFired, randInt))
                                        randInt = (int) (Math.random() * 9);
                                    yellowDevilBlastsFired[i] = randInt;
                                    yellowDevilBlasts[yellowDevilBlastsFired[i]].setShotCalled(true);
                                }
                            }
                        }
                    }
                    else
                        yellowDevil.setAnimationTimer((int)System.currentTimeMillis());
                }
            }
        }

        for (int i = 0; i<player.getShotNumber(); i++)  // for loop to loop through how many times the player has fired a blast
        {
            int blastLength = frameWidth/60;

            if(blasts[i].getX()-player.getWorldPosition() + player.getPlayerX() > 900*scale || blasts[i].getX()-player.getWorldPosition() + player.getPlayerX() < 0)    // if blast goes off screen, make it unavailable
                blasts[i].setAvailabe(false);

            if (blasts[i].getX()-player.getWorldPosition() + player.getPlayerX() <= frameWidth+100 && blasts[i].getX()-player.getWorldPosition() + player.getPlayerX() >= -100 && blasts[i].isAvailabe())
            {
                blasts[i].shoot();                          // call shoot method of every blast needed
                if (blasts[i].getDirectionNumber() == -1)   // if the blast is going left...
                    g.drawImage(blasts[i].getImage(), blasts[i].getX()-player.getWorldPosition() + player.getPlayerX(), blasts[i].getY()-player.getWorldPositionY()+player.getPlayerY(), blastLength, blastLength, this);
                else                                        // else
                    g.drawImage(blasts[i].getImage(), blasts[i].getX()-player.getWorldPosition() + player.getPlayerX() + (int) (frameWidth / 12.5), blasts[i].getY()-player.getWorldPositionY()+player.getPlayerY(), blastLength, blastLength, this);

                for (int k = 0; k<enemies.length; k++)      // for loop to iterate through all enemies
                {
                    if (blasts[i].getX() + player.getPlayerX() >= yellowDevil.getPosX() && blasts[i].getX() + player.getPlayerX() <= yellowDevil.getPosX() + yellowDevil.getWidth() && yellowDevil.getHealth()>0)
                    {
                        if (blasts[i].getY() + player.getPlayerY() >= yellowDevil.getPosY() && blasts[i].getY() + player.getPlayerY() <= yellowDevil.getPosY() + yellowDevil.getHeight())
                        {
                            if(!yellowDevil.isAttacked() && bossFight)
                            {
                                askQuestion();
                                blasts[i].setAvailabe(false);
                                yellowDevil.setHealth(yellowDevil.getHealth() - 1);
                                yellowDevil.setAttacked(true);
                            }
                        }
                    }
                    else if (blasts[i].getX() + player.getPlayerX() + blastLength >= yellowDevil.getPosX() && blasts[i].getX() + player.getPlayerX() + blastLength <= yellowDevil.getPosX() + yellowDevil.getWidth() && yellowDevil.getHealth()>0)
                    {
                        if (blasts[i].getY() + blastLength + player.getPlayerY() >= yellowDevil.getPosY() && blasts[i].getY() + blastLength + player.getPlayerY() <= yellowDevil.getPosY() + yellowDevil.getHeight())
                        {
                            if(!yellowDevil.isAttacked() && bossFight)
                            {
                                askQuestion();
                                blasts[i].setAvailabe(false);
                                yellowDevil.setHealth(yellowDevil.getHealth() - 1);
                                yellowDevil.setAttacked(true);
                            }
                        }
                    }

                    if (blasts[i].getX() + player.getPlayerX() >= enemies[k].getPosX() && blasts[i].getX() + player.getPlayerX() <= enemies[k].getPosX() + enemies[k].getWidth() && enemies[k].getHealth()>0)
                    {
                        if (blasts[i].getY() + player.getPlayerY() >= enemies[k].getPosY() && blasts[i].getY() + player.getPlayerY() <= enemies[k].getPosY() + enemies[k].getHeight())
                        {
                            askQuestion();
                            blasts[i].setAvailabe(false);
                            enemies[k].setHealth(enemies[k].getHealth()-1);
                        }
                    }
                    else if (blasts[i].getX() + player.getPlayerX() + blastLength >= enemies[k].getPosX() && blasts[i].getX() + player.getPlayerX() + blastLength <= enemies[k].getPosX() + enemies[k].getWidth() && enemies[k].getHealth()>0)
                    {
                        if (blasts[i].getY() + blastLength + player.getPlayerY() >= enemies[k].getPosY() && blasts[i].getY() + blastLength + player.getPlayerY() <= enemies[k].getPosY() + enemies[k].getHeight())
                        {
                            askQuestion();
                            blasts[i].setAvailabe(false);
                            enemies[k].setHealth(enemies[k].getHealth()-1);
                        }
                    }
                }
            }
        }
    }

    public void playShootNoise()
    {
        hit = new Media(new File(SHOOTSOUND).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.play();
    }

    public void askQuestion()
    {
        pause = true;
        player.setScore(player.getScore()+10);  // also append the score by 10
        QuestionFrame qf = new QuestionFrame(frameWidth, frameHeight, this);
    }

    public void getAnimation(Graphics g)                    // get animation method header
    {
        int width32 = (int) (player.getPlayerX() + frameWidth / 12.5);
        int height32 = (int) (player.getPlayerY() + frameHeight / 10);

        if((int)System.currentTimeMillis()-player.getShootTime() < 500)         // only make the player shooting for half a millisecond
            player.setShooting(true);
        else
            player.setShooting(false);

        if ((int) System.currentTimeMillis() - player.getInvincibleCounter() < 300)     // use the player's states to determine if they are shooting, jumping, or running
        {
            player.setAbleToMove(false);                                                // use an System.currentTimeMillis-a timer to change running animations
            if (!player.isFacingRight())
                g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 12.5), (int) (player.getPlayerY() + frameHeight / 7.5), 125, 0, 157, 32, this);
            else
                g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 12.5), (int) (player.getPlayerY() + frameHeight / 7.5), 125, 33, 157, 65, this);
        }
        else if ((int) System.currentTimeMillis() - player.getInvincibleCounter() > 400 && (int) System.currentTimeMillis() - player.getInvincibleCounter() < 500)
            player.setAbleToMove(true);

        else if ((int) System.currentTimeMillis() - player.getInvincibleCounter() > 1000 && (int) System.currentTimeMillis() - player.getInvincibleCounter() < 1100)
            player.setAbleToMove(true);

        else if ((int) System.currentTimeMillis() - player.getInvincibleCounter() > 1600 && (int) System.currentTimeMillis() - player.getInvincibleCounter() < 1700)
            player.setAbleToMove(true);

        else if (!player.isShooting())
        {
            player.setAbleToMove(true);

            if (player.isClimbing())
            {
                if(holdingDown || holdingZ)
                {
                    if (player.getCurTime() - player.getClimbTimer() < 200)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 25), (int) (player.getPlayerY() + frameHeight / 7.5), 50, 133, 66, 165, this);
                    else if (player.getCurTime() - player.getClimbTimer() < 400) {
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 25), (int) (player.getPlayerY() + frameHeight / 7.5), 67, 133, 83, 165, this);
                    } else
                        player.setClimbTimer((int) System.currentTimeMillis());
                }
                else
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 25), (int) (player.getPlayerY() + frameHeight / 7.5), 50, 133, 66, 165, this);
            }
            else
            {
                if (player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == 0)                                                      // standing right animation
                {
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth * 0.06), (int) (player.getPlayerY() + frameHeight / 10), 50, 25, 75, 49, this);
                } else if (!player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == 0)                                               // standing left animation
                {
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth * 0.06), (int) (player.getPlayerY() + frameHeight / 10), 1, 25, 25, 49, this);
                } else if (player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == 1)                                                 // running right animation
                {
                    if (player.getCurTime() - player.getRunTimer() < 125)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 14.8148148148), (int) (player.getPlayerY() + frameHeight / 10), 28, 0, 55, 24, this);
                    else if (player.getCurTime() - player.getRunTimer() < 250)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth * 0.06), (int) (player.getPlayerY() + frameHeight / 10), 75, 25, 99, 49, this);
                    else if (player.getCurTime() - player.getRunTimer() < 375) {
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 18.18181818), (int) (player.getPlayerY() + frameHeight / 10), 0, 50, 22, 74, this);
                        if (player.getCurTime() - player.getRunTimer() < 365)
                            player.setRunTimer((int) System.currentTimeMillis());
                    } else
                        player.setRunTimer((int) System.currentTimeMillis());
                } else if (!player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == -1)                                               // running left animation
                {
                    if (player.getCurTime() - player.getRunTimer() < 125)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 14.8148148148), (int) (player.getPlayerY() + frameHeight / 10), 0, 0, 27, 24, this);
                    else if (player.getCurTime() - player.getRunTimer() < 250)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth * 0.06), (int) (player.getPlayerY() + frameHeight / 10), 56, 0, 80, 24, this);
                    else if (player.getCurTime() - player.getRunTimer() < 375) {
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 18.18181818), (int) (player.getPlayerY() + frameHeight / 10), 100, 25, 122, 49, this);
                        if (player.getCurTime() - player.getRunTimer() < 365)
                            player.setRunTimer((int) System.currentTimeMillis());
                    } else
                        player.setRunTimer((int) System.currentTimeMillis());
                } else if (!player.isGrounded() && player.isFacingRight())
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 12.5), (int) (player.getPlayerY() + frameHeight / 7.5), 33, 75, 65, 107, this);
                else if (!player.isGrounded() && !player.isFacingRight())
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 12.5), (int) (player.getPlayerY() + frameHeight / 7.5), 0, 75, 32, 107, this);

            }
        }
        else {
            player.setAbleToMove(true);

            if (player.isClimbing())
            {
                if(player.isFacingRight())
                {
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 18.18181818), (int) (player.getPlayerY() + frameHeight / 7.5), 25, 133, 49, 165, this);
                }
                else
                {
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), (int) (player.getPlayerX() + frameWidth / 18.18181818), (int) (player.getPlayerY() + frameHeight / 7.5), 0, 133, 24, 165, this);
                }
            }
            else
            {
                if (player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == 0)                                                      // standing and shooting right animation
                {
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 159, 91, 191, 115, this);
                } else if (!player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == 0)                                               // standing left and shooting animation
                {
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 192, 0, 224, 24, this);
                } else if (player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == 1)                                                 // running right animation
                {
                    if (player.getCurTime() - player.getRunTimer() < 125)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 192, 75, 224, 99, this);
                    else if (player.getCurTime() - player.getRunTimer() < 250)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 192, 100, 224, 124, this);
                    else if (player.getCurTime() - player.getRunTimer() < 375) {
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 0, 108, 32, 132, this);
                        if (player.getCurTime() - player.getRunTimer() < 365)
                            player.setRunTimer((int) System.currentTimeMillis());
                    } else
                        player.setRunTimer((int) System.currentTimeMillis());
                } else if (!player.isFacingRight() && player.isGrounded() && player.getHorizontalVelocity() == -1)                                               // running left animation
                {
                    if (player.getCurTime() - player.getRunTimer() < 125)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 192, 25, 224, 49, this);
                    else if (player.getCurTime() - player.getRunTimer() < 250)
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 159, 66, 191, 90, this);
                    else if (player.getCurTime() - player.getRunTimer() < 375) {
                        g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, height32, 192, 50, 224, 74, this);
                        if (player.getCurTime() - player.getRunTimer() < 365)
                            player.setRunTimer((int) System.currentTimeMillis());
                    } else
                        player.setRunTimer((int) System.currentTimeMillis());
                } else if (!player.isGrounded() && player.isFacingRight())
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, (int) (player.getPlayerY() + frameHeight / 7.5), 159, 33, 192, 65, this);
                else if (!player.isGrounded() && !player.isFacingRight())
                    g.drawImage(playerImage, player.getPlayerX(), player.getPlayerY(), width32, (int) (player.getPlayerY() + frameHeight / 7.5), 159, 0, 192, 32, this);
            }
        }
    }

    public void manageText(Graphics g)                                  // manage text method header
    {
        g.setFont(new Font("Verdana", Font.BOLD, frameHeight/27));
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", Font.BOLD, frameHeight/15));

        if (!mouseClicked)                                              // if the mouse has not been clicked, prompt the user to click the mouse
        {
            g.drawString("CLICK TO START", frameWidth / 2, frameHeight / 2);
            checkForEnemies();
            checkForDynamicBlocks();
        }
        else
        {
            if(pause)
            {
                g.drawString("GAME PAUSED", frameWidth / 2, frameHeight / 2);
            }
        }
        g.drawString("SCORE: " + player.getScore(), (int)(frameWidth/14.2857), (int)(frameHeight/12.0));

        g.setFont(new Font("Verdana", Font.PLAIN, frameHeight/17));
        g.setColor(Color.WHITE);

        if(!help)
        {
            int width = g.getFontMetrics().stringWidth("press 'h' for help")-5;
            g.drawString("press 'h' for help", frameWidth - width, frameHeight / 17);
        }
        else
        {
            int option1Width = g.getFontMetrics().stringWidth("press 'q' to quit")-5;
            int option2Width = g.getFontMetrics().stringWidth("hold 'z' to jump")-5;
            int option3Width = g.getFontMetrics().stringWidth("press 'x' to shoot")-5;
            int option4Width = g.getFontMetrics().stringWidth("use arrow keys to move")-5;
            int option5Width = g.getFontMetrics().stringWidth("press 'h' to dismiss")-5;
            int option6Width = g.getFontMetrics().stringWidth("press 'p' to pause")-5;

            g.drawString("press 'q' to quit", frameWidth-option1Width, frameHeight/17);
            g.drawString("hold 'z' to jump", frameWidth-option2Width, 2*frameHeight/17);
            g.drawString("press 'x' to shoot", frameWidth-option3Width, 3*frameHeight/17);
            g.drawString("use arrow keys to move", frameWidth-option4Width, 4*frameHeight/17);
            g.drawString("press 'h' to dismiss", frameWidth-option5Width, 5*frameHeight/17);
            g.drawString("press 'p' to pause", frameWidth-option6Width, 6*frameHeight/17);

        }
        g.setFont(new Font("Verdana", Font.PLAIN, frameHeight/30));
        g.setColor(Color.BLACK);
        g.drawString(((int)(1000.0/(double)mover.getTimer().getDelay()) + "/" + (int)(1000.0/(double)mover.getTimer().getInitialDelay()) + " fps"), 0, frameHeight - frameHeight/15);
    }

    public void restartVariables()                                      // restart variables method header
    {
        time = 0;
        level = 0;
        questionArrayIndex = 0;
        scale = frameWidth/1000;

        mouseClicked = false;
        showQuestionPanel = false;
        pause = false;
        bossFight = false;
        help = false;
        holdingZ = false;
        holdingDown = false;
        levelInit = false;

        levelLayout = new int[1000][1000];

        mover = new Mover(this, player);

        player = new Player(frameWidth, frameHeight);

        for(int i = 0; i<questionArray.length; i ++)
        {
            questionArray[i] = -1;
        }

        enemies = new Enemy[5];
        for (int i = 0; i<enemies.length; i++)                      // for loop to populate array
        {
            enemies[i] = new Enemy(this, player, 2100*scale + i*300*scale, 700*scale, frameWidth, frameHeight, 1);
        }

        yellowDevil = new Enemy(this, player, 2000*scale + 200*scale, 700*scale, frameWidth, frameHeight, -1);

        yellowDevilBlastsFired = new int[9];
        for(int i = 0; i < yellowDevilBlastsFired.length; i++)
        {
            yellowDevilBlastsFired[i] = -1;
        }

        yellowDevilBlasts = new Enemy[9];
        for (int i = 0; i<yellowDevilBlasts.length; i++)                      // for loop to populate array
        {
            yellowDevilBlasts[i] = new Enemy(this, player, yellowDevil.getPosX(), yellowDevil.getPosY(), frameWidth, frameHeight, 2);
        }

        blasts = new Blast[100];
        for (int i = 0; i<blasts.length; i++)                       // for loop to populate array
        {
            blasts[i] = new Blast(player, frameWidth, frameHeight);
        }

        playerImage = player.getPlayerImage();
        healthBarImage = player.getHealthBarImage();
        enemyUpdateTime = (int) (System.currentTimeMillis());

        questionsAnswered = 0;

        getLevelImages();


    }

    public void checkCollisions()                                               // check collisions method header
    {
        // check dynamic block collisions first

        int truePlayerX = player.getWorldPosition() + player.getPlayerX();
        int truePlayerY = player.getWorldPositionY();

        int truePlayerWidth = (int)(frameWidth*0.06);
        int truePlayerHeight = (int)(frameHeight/10.0);

        boolean[] onBlocks;
        onBlocks = new boolean[dynamicBlocks.length];

        for(int i = 0; i<dynamicBlocks.length; i++)
        {
            if(mouseClicked) {
                if ((truePlayerX >= dynamicBlocks[i].getX() && truePlayerX <= dynamicBlocks[i].getX() + dynamicBlocks[i].getWidth()) || (truePlayerX + truePlayerWidth >= dynamicBlocks[i].getX() && truePlayerX + truePlayerWidth <= dynamicBlocks[i].getX() + dynamicBlocks[i].getWidth()) || (truePlayerX + truePlayerWidth / 2 >= dynamicBlocks[i].getX() && truePlayerX + truePlayerWidth / 2 <= dynamicBlocks[i].getX() + dynamicBlocks[i].getWidth()))
                {
                    if (truePlayerY + truePlayerHeight >= dynamicBlocks[i].getY() && truePlayerY + truePlayerHeight <= dynamicBlocks[i].getY() + (int) (dynamicBlocks[i].getHeight() / 2.0))
                    {
                        onBlocks[i] = true;
                        player.setOnMovingBlock(true);
                        if(bossFight)
                            player.setPlayerX(player.getPlayerX() + dynamicBlocks[i].getVelocity());
                        else
                            player.setWorldPosition(player.getWorldPosition() + dynamicBlocks[i].getVelocity());
                        player.setGrounded(true);
                    }
                    else
                        onBlocks[i] = false;
                }
                else
                    onBlocks[i] = false;
            }
        }

        boolean flag = true;

        for(int i = 0; i < onBlocks.length; i++)
        {
            if(onBlocks[i] == false)
            {
                flag = false;
            }
            else
            {
                flag = true;
                break;
            }
        }

        if(flag == false)
        {
            player.setOnMovingBlock(false);
        }

        if (player.getWorldPositionY() > 0 && player.getWorldPositionY() < 90000 && !player.isOnMovingBlock())
        {
            int curColMin = (int) ((double) player.getVirtualPlayerPosition() / (double) (frameWidth / 20)) + 1;
            int curColMax = (int) ((double) (player.getVirtualPlayerPosition() + (int) (frameWidth / 20)) / (double) (frameWidth / 20)) + 1;

            int curRowMin = (int) ((double) (player.getWorldPositionY() + (frameHeight / 60)) / (double) (frameHeight / 12)) + 1;
            int curRowMax = (int) ((double) (player.getWorldPositionY() - (frameHeight / 12)) / (double) (frameHeight / 12)) + 1;

            if ((levelLayout[curRowMin][curColMin] == 3 || levelLayout[curRowMin][curColMax] == 3))             // if the player is touching lava
                player.setHealth(0);        // kill the player

            if ((levelLayout[curRowMin][curColMin] == -4 || levelLayout[curRowMin][curColMax] == -4))             // if the player is touching B
                bossFight = true;        // enable boss mode


            if (levelLayout[curRowMax + 1][curColMin + 1] == -4)         // if the player has a block to its right
            {
                bossFight = true;                         // set boss fight to true
            }

            if ((levelLayout[curRowMin][curColMin] >= 1 || levelLayout[curRowMin][curColMax] >= 1))         // if the player has his feet down
                player.setGrounded(true);           // set grounded to true
            else
                player.setGrounded(false);

            if (levelLayout[curRowMax][curColMin] >= 1 || levelLayout[curRowMax][curColMax] >= 1)           // if the top of the player has a block
                player.setTopCollision(true);       // set top collision to true
            else
                player.setTopCollision(false);

            if (levelLayout[curRowMax + 1][curColMin + 1] >= 1)         // if the player has a block to its right
            {
                player.setRightCollision(true);                         // set right collision to true

                if ((levelLayout[curRowMax][curColMin] < 1) && !(levelLayout[curRowMin][curColMin] >= 1 && levelLayout[curRowMin][curColMax] >= 1))
                {
                    player.setGrounded(false);                          // if not both feet are grounded
                }
                else if (levelLayout[curRowMin][curColMin] >= 1 && levelLayout[curRowMin][curColMax] >= 1)
                {
                    player.setGrounded(true);
                    velocity = VELOCITYINITIAL;                         // restart velocity
                    gravity = GRAVITYINITIAL;                           // restart gravity
                    if(!bossFight)
                        player.setWorldPosition(player.getWorldPosition() - 1);
                }
            }
            else
                player.setRightCollision(false);

            if (levelLayout[curRowMax + 1][curColMin] >= 1)             // if the player has a block to its left
            {
                player.setLeftCollision(true);                          // set left collision true

                if ((levelLayout[curRowMax][curColMax] < 1) && !(levelLayout[curRowMin][curColMin] >= 1 && levelLayout[curRowMin][curColMax] >= 1))
                {
                    player.setGrounded(false);                          // if not both feet are grounded
                }
                else if (levelLayout[curRowMin][curColMin] >= 1 && levelLayout[curRowMin][curColMax] >= 1)
                {
                    player.setGrounded(true);                           // make the player grounded
                    velocity = VELOCITYINITIAL;                         // restart velocity
                    gravity = GRAVITYINITIAL;                           // restart gravity
                    if (!bossFight)
                        player.setWorldPosition(player.getWorldPosition() + 1); // move the world position up 1
                }
            }
            else
                player.setLeftCollision(false);

            if ((levelLayout[curRowMin][curColMin] == -1 || levelLayout[curRowMin][curColMax] == -1))             // if the player is touching lava
            {
                player.setClimbing(true);
                velocity = -5;
                if(holdingDown)
                    player.setWorldPositionY(player.getWorldPositionY()-(int)velocity);
                else if(holdingZ)
                    player.setWorldPositionY(player.getWorldPositionY()+(int)velocity);
                gravity = 0;
            }
            else
            {
                player.setClimbing(false);
            }
        }
    }

    public void applyGravity()                                          // apply gravity method header
    {
        if (!player.isGrounded())                                       // if the player is not grounded...
        {
            player.setWorldPositionY(player.getWorldPositionY() + (int) ((gravity) * (frameHeight / 600)));     // move the player downwards
            gravity += 0.8;                                             // increase gravity over time
            if (gravity > 10)                                           // limit gravity
                gravity = 10;
        }
        if (player.isJumping() && !player.isTopCollision())             // if the player is jumping and is not hitting the wall
        {
            player.setWorldPositionY(player.getWorldPositionY() + (int) ((velocity) * (frameHeight / 600)));    // move the player upwards
            velocity += 0.6;                                            // increase velocity over time
            if (velocity > 0)                                           // prevent velocity from going above 0
                velocity = 0;
        }
        if (player.isGrounded())
        {
            player.setAirTime((int) System.currentTimeMillis());        // reset the air timer
            gravity = GRAVITYINITIAL;                                   // reset gravity
            velocity = VELOCITYINITIAL;                                 // reset velocity
        }
        if (player.isTopCollision())                                    // if the player has a top collision
            velocity = 0;                                               // set their velocity to 0
    }

    public void checkConstraints()                                      // checkConstraints method header
    {
        player.setVirtualPlayerPosition(player.getWorldPosition() + player.getPlayerX());   // get the virtual position by adding the x to the world position

        if (player.getVirtualPlayerPosition() < frameWidth / 2)         // if the v position is less than the frame width divided by 2 make the player image move instead of the world
        {
            if (player.getPlayerX() <= 900 && player.getPlayerX() >= 0 && !player.isLeftCollision() && !player.isRightCollision())
                player.setPlayerX((int) (player.getPlayerX() + player.getHorizontalVelocity() * player.getSpeed() * ((double) frameWidth / (double) 1000)));
            else if (player.getPlayerX() <= 900 && player.getPlayerX() >= 0 && player.isLeftCollision() && player.getHorizontalVelocity() == 1)
                player.setPlayerX((int) (player.getPlayerX() + player.getHorizontalVelocity() * player.getSpeed() * ((double) frameWidth / (double) 1000)));
            else if (player.getPlayerX() <= 900 && player.getPlayerX() >= 0 && player.isRightCollision() && player.getHorizontalVelocity() == -1)
                player.setPlayerX((int) (player.getPlayerX() + player.getHorizontalVelocity() * player.getSpeed() * ((double) frameWidth / (double) 1000)));
            else if (player.getHorizontalVelocity() == -1 && player.getPlayerX() > 900 && !player.isLeftCollision())
                player.setPlayerX((int) (player.getPlayerX() + player.getHorizontalVelocity() * player.getSpeed() * ((double) frameWidth / (double) 1000)));
            else if (player.getHorizontalVelocity() == 1 && player.getPlayerX() < 0 && !player.isRightCollision())
                player.setPlayerX((int) (player.getPlayerX() + player.getHorizontalVelocity() * player.getSpeed() * ((double) frameWidth / (double) 1000)));
            else if (player.getHorizontalVelocity() == 1 && player.getPlayerX() > 900 && !player.isRightCollision()) {

                player.setWorldPosition((int) (player.getWorldPosition() + player.getSpeed() * ((double) frameWidth / (double) 1000)));
            }
        }
        else        // move the world instead of the player
        {
            if (player.getHorizontalVelocity() == -1 && !player.isLeftCollision())
            {
                if(bossFight)
                    player.setPlayerX((int) (player.getPlayerX() - player.getSpeed() * ((double) frameWidth / (double) 1000)));
                else
                    player.setWorldPosition((int) (player.getWorldPosition() - player.getSpeed() * ((double) frameWidth / (double) 1000)));

            }
            else if (player.getHorizontalVelocity() == 1 && !player.isRightCollision())
            {
                if(bossFight)
                    player.setPlayerX((int) (player.getPlayerX() +  player.getSpeed() * ((double) frameWidth / (double) 1000)));
                else
                    player.setWorldPosition((int) (player.getWorldPosition() + player.getSpeed() * ((double) frameWidth / (double) 1000)));
            }
        }

        player.setVirtualPlayerPosition(player.getWorldPosition() + player.getPlayerX());

        if (player.getWorldPositionY() > 30000 || player.getHealth() <= 0)          // if the player health is below 0 or player has fallen for too long, then display game over
        {
            cardLayout.show(super.getParent(), "Game Over");
            restartVariables();
        }
    }

    public void updateLoop()                    // update loop method header
    {
        if (mouseClicked)                       // if the mouse has been clicked
        {
            if(!pause)                          // if the game is not paused
            {
                checkCollisions();              // call check collisions
                checkConstraints();             // call check constraints
                applyGravity();                 // apply gravity to the player
                for (int i = 0; i < enemies.length; i++)    // for loop to update all enemies who are alive
                {
                    if (enemies[i].getHealth() > 0)
                        enemies[i].updateStates();
                }

                for (int r = 0; r<yellowDevilBlasts.length; r++)                      // for loop to populate array
                {
                    if(yellowDevil.getHealth()>0)
                    {
                        if (yellowDevil.isOnRightSide() && yellowDevilBlasts[r].getPosX() < player.getWorldPosition() + frameWidth - yellowDevil.getWidth() + (int)((yellowDevil.getWidth()/3.0)*((r+1)%3)/3))
                            yellowDevilBlasts[r].updateStates();
                        else if (!yellowDevil.isOnRightSide() && yellowDevilBlasts[r].getPosX() > player.getWorldPosition() + (int)((yellowDevil.getWidth()/3.0)*((r+1)%3)/3)+10*scale)
                            yellowDevilBlasts[r].updateStates();
                    }
                }
                if (yellowDevil.getHealth() > 0)
                    yellowDevil.updateStates();
                else if(player.getHealth() > 0)
                {
                    cardLayout.show(super.getParent(), "Win");
                    restartVariables();
                }
            }
            else
            {
                player.setJumping(false);       // if the game is paused, make the player stop moving
                player.setHorizontalVelocity(0);
            }
        }

        if (((int) System.currentTimeMillis() - player.getInvincibleCounter()) > 2000)  // only allow the player to be invincible for 2 seconds after being attacked
            player.setInvincible(false);
    }

    public void updateMovingBlocks(Graphics g)
    {
        if(mouseClicked)
        {
            for (int i = 0; i < dynamicBlocks.length; i++) {
                g.drawImage(movingBlock1Image, dynamicBlocks[i].getX() - player.getWorldPosition(), dynamicBlocks[i].getY() - player.getWorldPositionY() + player.getPlayerY(), frameWidth / 20, frameHeight / 12, this);

                if (!pause)
                    dynamicBlocks[i].updateStates();
            }
        }
    }

    public boolean arrayContains(int[] array, int integer)
    {
        boolean result = false;
        for(int i = 0; i<array.length; i++)
        {
            if (array[i] == integer)
                result = true;
        }
        return result;
    }

    public void setQuestionsAnswered(int questionsAnswered)
    {
        this.questionsAnswered = questionsAnswered;
    }

    public int getQuestionsAnswered()
    {
        return questionsAnswered;
    }

    public int getScore()
    {
        return player.getScore();
    }

    public void setPlayerHealth(int playerHealth)
    {
        player.setHealth(playerHealth);
    }

    public void setHoldingZ(boolean holdingZ)
    {
        this.holdingZ = holdingZ;
    }

    public void setHoldingDown(boolean holdingDown)
    {
        this.holdingDown = holdingDown;
    }

    public int getPlayerHealth()
    {
        return player.getHealth();
    }

    public void keyPressed(KeyEvent e)          // key pressed method header
    {
        char key = e.getKeyChar();
        int code = e.getKeyCode();          // get the key code

        if(!pause)                              // if the game is not paused
        {
            if (code == KeyEvent.VK_RIGHT)      // if the right key is pressed, move the player right
            {
                if (player.isAbleToMove())
                {
                    player.setFacingRight(true);
                    player.setHorizontalVelocity(1);
                }
            }

            if (code == KeyEvent.VK_LEFT)       // if the left key is pressed, move the player left
            {
                if (player.isAbleToMove())
                {
                    player.setFacingRight(false);
                    player.setHorizontalVelocity(-1);
                }
            }

            if(code == KeyEvent.VK_DOWN)
            {
                holdingDown = true;
            }

            if (code == KeyEvent.VK_UP)       // if z is pressed, make the player jump
            {
                if (player.isAbleToMove())
                    player.setJumping(true);
                holdingZ = true;

                jumpDebug = "PRESSING UP";
            }

            if (code == KeyEvent.VK_Q)       // if the q key is pressed, quit the game by showing the menu and restarting variables
            {
                cardLayout.show(super.getParent(), "Level Select");
                ls.requestFocus();
                restartVariables();
            }

            if(code == KeyEvent.VK_H)
            {
                help = !help;
            }

            if(code == KeyEvent.VK_P)
            {
                pause = !pause;
            }
        }
        else if(code == KeyEvent.VK_P)
        {
            pause = !pause;
        }
    }

    public void keyReleased(KeyEvent e)         // public void key released
    {
        if(!pause)                              // if the game is not paused
        {
            int code = e.getKeyCode();          // get the key code
            char key = e.getKeyChar();          // get the key character

            if (code == KeyEvent.VK_RIGHT)      // if right key is released, stop the player
            {
                player.setFacingRight(true);
                player.setHorizontalVelocity(0);
            }

            if (code == KeyEvent.VK_LEFT)       // if left key is released, stop the player
            {
                player.setFacingRight(false);
                player.setHorizontalVelocity(0);
            }

            if(code == KeyEvent.VK_DOWN)
            {
                holdingDown = false;
            }

            if (code == KeyEvent.VK_UP)                     // if z is released, make jumping false
            {
                player.setJumping(false);
                holdingZ = false;
                jumpDebug = "RELEASED UP";
            }

            if (code == KeyEvent.VK_X)       // if x is released, shoot
            {
                player.setShootTime((int)System.currentTimeMillis());

                playShootNoise();

                blasts[player.getShotNumber()].getDirection();

                player.setShotNumber(player.getShotNumber() + 1);
                if (player.getShotNumber() > blasts.length - 1)     // if all the blasts in the blast array has been used, restart the array
                {
                    for (int i = 0; i < blasts.length; i++)
                    {
                        blasts[i] = new Blast(player, frameWidth, frameHeight);
                    }
                    player.setShotNumber(0);
                }
            }

            if (code == KeyEvent.VK_Q)       // releasing q will stop the timer
            {
                timer.stop();
            }
        }
    }

    public void keyTyped(KeyEvent e) {}                                // override method

    public void setMouseClicked(boolean mouseClicked)
    {
        this.mouseClicked = mouseClicked;
    }

    public boolean isMouseClicked()
    {
        return mouseClicked;
    }

    public int[] getQuestionArray()
    {
        return questionArray;
    }

    public boolean isBossFight()
    {
        return bossFight;
    }

    public int getLevel()
    {
        return level;
    }

    public int getQuestionArrayIndex()
    {
        return questionArrayIndex;
    }

    public void setQuestionArray(int[] questionArray)
    {
        this.questionArray = questionArray;
    }

    public void setPause(boolean pause)
    {
        this.pause = pause;
    }        // override method

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setQuestionArrayIndex(int questionArrayIndex)
    {
        this.questionArrayIndex = questionArrayIndex;
    }

}
