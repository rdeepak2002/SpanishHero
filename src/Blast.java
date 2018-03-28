import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by rdeep on 6/9/2017.
 */
public class Blast                 // Blast class header
{
    // declare necessary field variables
    private int x, y, screenWidth, screenHeight, direction;
    private boolean availabe;
    private Player player;
    private Enemy enemy;
    private Image blastImage;

    public Blast(Player player, int screenWidth, int screenHeight)      // constructor to initialize field variables
    {
        this.player = player;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        availabe = true;

        x = 0;
        y = player.getPlayerY()+10*(screenWidth/1000);
        loadImage();
    }

    public Blast(Enemy enemy, int screenWidth, int screenHeight)      // constructor to initialize field variables
    {
        this.enemy = enemy;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        availabe = true;

        x = 0;
        y = enemy.getPosY()+10*(screenWidth/1000);
        loadImage();
    }

    public void shoot()                                             // shoot method header
    {
        availabe = true;

        x += (screenWidth/200)*direction;

        if (direction == 1)
            x += 10*screenWidth/1000;
        else
            x -= 20*screenWidth/1000;
    }

    public void loadImage()                                        // load image method header
    {
        try                                                        // use try catch block to load Blast image
        {
            blastImage = ImageIO.read(new File("Resources/Blast.png"));
        }
        catch(IOException e)
        {
            System.err.println("Unable to load Blast.png");
            e.printStackTrace();
        }
    }

    public void getDirection()                                     // method will get the direction of the blast by checking the player booleans
    {
        if(player.isFacingRight())
        {
            direction = 1;
        }
        else
            direction = -1;

        y = player.getWorldPositionY() + 13*(screenWidth/1000);
        x = player.getWorldPosition();
    }

    public void setAvailabe(boolean availabe)                     // setter method
    {
        this.availabe = availabe;
    }

    public Image getImage()
    {
        return blastImage;
    }                // getter method

    public int getDirectionNumber()
    {
        return direction;
    }         // getter method

    public int getX()
    {
        return x;
    }                               // getter method

    public int getY()
    {
        return y;
    }                               // getter method

    public boolean isAvailabe()
    {
        return availabe;
    }              // getter method
}
