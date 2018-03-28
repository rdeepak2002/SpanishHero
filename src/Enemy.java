import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by rdeep on 6/9/2017.
 */
public class Enemy                                     // enemy class header
{
    private int posX;                           // declare necessary field variables
    private int posY;
    private int targetX;
    private int targetY;
    private int width;
    private int height;
    private int health;
    private int maxHealth;
    private int frameWidth;
    private int frameHeight;
    private int followTimer;
    private int animationTimer;
    private int enemyUpdateTime;
    private int type;
    private int topShots;
    private int middleShots;
    private int bottomShots;
    private int scale;
    private int bulletDirection;
    private int randBlast;

    private boolean onRightSide;
    private boolean hasShot;
    private boolean shotActive;
    private boolean shotCalled;
    private boolean attacked;

    private Image image;
    private Player player;
    private GamePanel gp;

    public Enemy(GamePanel gp, Player player, int posX, int posY, int frameWidth, int frameHeight, int type)  // constructor to initialize field variables
    {
        this.type = type;
        this.posX = posX;
        this.posY = posY;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.player = player;
        this.gp = gp;

        topShots = 0;
        middleShots = 1;
        bottomShots = 0;
        bulletDirection = 1;
        randBlast = 4;
        hasShot = false;
        shotActive = false;
        shotCalled = false;
        attacked = false;

        maxHealth = 1;
        health = 1;

        if(type == 1)
        {
            width = (int)(frameWidth/31.25);         // note: 16 by 20 res for Enemy1.png
            height = (int)(frameHeight/15.0);
        }
        else if(type == 2)
        {
            width = (int)(frameWidth/31.25);         // note: 16 by 20 res for Enemy1.png
            height = (int)(frameHeight/15.0);
        }
        else if (type == -1)
        {
            maxHealth = 5;
            health = maxHealth;
            width = (int)(frameWidth*0.15);         // note: 16 by 20 res for Enemy1.png
            height = (int)(frameHeight*0.3);
        }
        onRightSide = true;
        followTimer = (int)System.currentTimeMillis();
        animationTimer = (int)System.currentTimeMillis();
        enemyUpdateTime = (int)System.currentTimeMillis();
        animationTimer = (int)System.currentTimeMillis();
        targetX = player.getPlayerX();
        targetY = player.getPlayerY();

        scale = frameWidth/1000;

        loadImage(1);
    }

    public void loadImage(int state)            // loadImage method header
    {
        if(type == 1)                           // if enemy is of type1, load these images
        {
            if (state == 1)
            {
                try
                {
                    if (onRightSide)
                        image = ImageIO.read(new File("Resources/Enemy1.png"));
                    else
                        image = ImageIO.read(new File("Resources/Enemy1R.png"));
                }
                catch (IOException e)
                {
                    System.err.println("\n\n" + "Enemy1.png" + " can't be found.\n\n");
                    if (!onRightSide)
                        System.err.println("\n\n" + "Enemy1R.png" + " can't be found.\n\n");
                    e.printStackTrace();
                }
            }
            else
            {
                try
                {
                    if (onRightSide)
                        image = ImageIO.read(new File("Resources/Enemy1S2.png"));
                    else
                        image = ImageIO.read(new File("Resources/Enemy1S2R.png"));
                }
                catch (IOException e)
                {
                    System.err.println("\n\n" + "Enemy1S2.png" + " can't be found.\n\n");
                    if (!onRightSide)
                        System.err.println("\n\n" + "Enemy1S2R.png" + " can't be found.\n\n");
                    e.printStackTrace();
                }
            }
        }
        else if (type == -1)
        {
            if(attacked)
            {
                try {
                    if (onRightSide)
                        image = ImageIO.read(new File("Resources/YellowDevilRight.png"));
                    else
                        image = ImageIO.read(new File("Resources/YellowDevilLeft.png"));
                } catch (IOException e) {
                    System.err.println("\n\n" + "YellowDevilRight.png" + " can't be found.\n\n");
                    if (!onRightSide)
                        System.err.println("\n\n" + "YellowDevilLeft.png" + " can't be found.\n\n");
                    e.printStackTrace();
                }
            }
            else
            {
                try {
                    if (onRightSide)
                        image = ImageIO.read(new File("Resources/YellowDevilRightP.png"));
                    else
                        image = ImageIO.read(new File("Resources/YellowDevilLeftP.png"));
                } catch (IOException e) {
                    System.err.println("\n\n" + "YellowDevilRightP.png" + " can't be found.\n\n");
                    if (!onRightSide)
                        System.err.println("\n\n" + "YellowDevilLeftP.png" + " can't be found.\n\n");
                    e.printStackTrace();
                }
            }
        }
    }

    public void resetTimer()            // reset timer method header
    {
        followTimer = (int)System.currentTimeMillis();  // reset the follow timer by setting it to the current time
    }

    public void updateStates()          // update states method header
    {
        if (type == 1)                   // if enemy type is 1, iterate through different movement patterns as time increases
        {
            if (Math.abs(posY-player.getWorldPositionY()) <= frameHeight)
            {
                if (posX - player.getWorldPosition() > -100*scale && posX - player.getWorldPosition() < 1100*scale)
                {
                    if ((int) System.currentTimeMillis() - animationTimer < 100)
                        loadImage(1);
                    else if ((int) System.currentTimeMillis() - animationTimer < 220) {
                        loadImage(2);
                        if ((int) System.currentTimeMillis() - animationTimer > 200)
                            animationTimer = (int) System.currentTimeMillis();
                    } else
                        animationTimer = (int) System.currentTimeMillis();

                    if ((int) System.currentTimeMillis() - enemyUpdateTime > 50) {
                        enemyUpdateTime = (int) System.currentTimeMillis();
                    }

                    if ((int) System.currentTimeMillis() - followTimer < 4000) {
                        riseUp();
                        followPlayerX();
                        targetX = player.getWorldPosition() + (int) (frameWidth / 25);
                        targetY = player.getWorldPositionY() + (int) (frameWidth / 25);
                    } else if ((int) System.currentTimeMillis() - followTimer < 6000) {
                        if ((int) System.currentTimeMillis() - followTimer < 4200) {
                            targetX = player.getWorldPosition() + (int) (frameWidth / 25);
                            targetY = player.getWorldPositionY();
                        }
                        goToPlayer();
                    } else {

                        resetTimer();
                        onRightSide = !onRightSide;
                    }

                    int playerWidth = (int) (frameWidth * 0.06);
                    int playerHeight = (int) (frameHeight / 10);

                    // manage the invincibility of the player after he is attacked
                    if (!player.isInvincible() && (player.getWorldPosition() + player.getPlayerX() >= posX && player.getWorldPosition() + player.getPlayerX() <= posX + width || player.getWorldPosition() + playerWidth + player.getPlayerX() >= posX && player.getWorldPosition() + playerWidth + player.getPlayerX() <= posX + width)) {

                        if (player.getWorldPositionY() + player.getPlayerY() >= posY && player.getWorldPositionY() + player.getPlayerY() <= posY + height || player.getWorldPositionY() + playerHeight + player.getPlayerY() >= posY && player.getWorldPositionY() + playerHeight + player.getPlayerY() <= posY + height) {
                            player.setHealth(player.getHealth() - 10);
                            player.setInvincibleCounter((int) System.currentTimeMillis());
                            player.setInvincible(true);
                        }
                    }
                }
            }
        }

        if(type == 2 && shotActive && gp.isBossFight())
        {
            if (posX - player.getWorldPosition() > -100*scale && posX - player.getWorldPosition() < 1100*scale)
            {
                posX += 18*bulletDirection*scale;
            }

            int playerWidth = (int) (frameWidth * 0.06);
            int playerHeight = (int) (frameHeight / 10);

            int ax1 = player.getWorldPosition() + player.getPlayerX();
            int ax2 = player.getWorldPosition() + player.getPlayerX()+playerWidth;
            int ay1 = player.getWorldPositionY() + player.getPlayerY();
            int ay2 = player.getWorldPositionY() + player.getPlayerY()+playerHeight;

            int bx1 = posX;
            int bx2 = posX + width;
            int by1 = posY;
            int by2 = posY + height;

            // PROBLEM: COLLISIONS ONLY CHECK TOP LEFT AND BOTTOM RIGHT!!! WTF
            // manage the invincibility of the player after he is attacked
            if (!player.isInvincible() && (bx1 >= ax1 && bx1 <= ax2 || bx2 >= ax1 && bx2 <= ax2))
            {
                if (by1 >= ay1 && by1 <= ay2 || by2 >= ay1 && by2 <= ay2)
                {
                    player.setHealth(player.getHealth() - 10);
                    player.setInvincibleCounter((int) System.currentTimeMillis());
                    player.setInvincible(true);
                }
            }
        }

        if (type == -1 && gp.isBossFight() && (gp.getLevel() == 1 || gp.getLevel() == 2 || gp.getLevel() == 3 || gp.getLevel() == 4 || gp.getLevel() == 5 || gp.getLevel() == 6))            // BOSS LOGIC
        {
            if ((int) System.currentTimeMillis() - animationTimer < 12000)
            {
                hasShot = true;
                onRightSide = true;
                loadImage(-1);
                if(!attacked)
                    attacked = false;
            }
            else if((int) System.currentTimeMillis() - animationTimer < 12200)
            {
                hasShot = false;
                attacked = true;
            }
            else if ((int) System.currentTimeMillis() - animationTimer < 22200)
            {
                hasShot = true;
                onRightSide = false;
                loadImage(-1);
                attacked = true;
                if ((int) System.currentTimeMillis() - animationTimer > 22000)
                {
                    animationTimer = (int) System.currentTimeMillis();
                    attacked = false;
                    hasShot = false;
                }
            }
            else
            {
                animationTimer = (int) System.currentTimeMillis();
                attacked = false;
                hasShot = false;
            }


            int playerWidth = (int) (frameWidth * 0.06);
            int playerHeight = (int) (frameHeight / 10);

            // manage the invincibility of the player after he is attacked
            if (!player.isInvincible() && (player.getWorldPosition() + player.getPlayerX() >= posX && player.getWorldPosition() + player.getPlayerX() <= posX + width || player.getWorldPosition() + playerWidth + player.getPlayerX() >= posX && player.getWorldPosition() + playerWidth + player.getPlayerX() <= posX + width)) {
                if (player.getWorldPositionY() + player.getPlayerY() >= posY && player.getWorldPositionY() + player.getPlayerY() <= posY + height || player.getWorldPositionY() + playerHeight + player.getPlayerY() >= posY && player.getWorldPositionY() + playerHeight + player.getPlayerY() <= posY + height) {
                    player.setHealth(player.getHealth() - 10);
                    player.setInvincibleCounter((int) System.currentTimeMillis());
                    player.setInvincible(true);
                }
            }
        }

    }

    public void followPlayerX() // folllowPlayerX() method header
    {
        if(!onRightSide)        // if enemy is on left side
        {
            if (player.getWorldPosition() - frameWidth / 6 + player.getPlayerX() > posX) // move right by 9
            {
                posX += 9*scale;
            }

            if (player.getWorldPosition() - frameWidth / 6 + player.getPlayerX() <= posX) // move left by 9
            {
                posX -=9*scale;
            }
        }
        else
        if (player.getWorldPosition() + frameWidth / 6 + player.getPlayerX() > posX) // move right by 9
        {
            posX += 9*scale;
        }

        if (player.getWorldPosition() + frameWidth / 6 + player.getPlayerX() <= posX) // move left by 9
        {
            posX -= 9*scale;
        }
    }

    public void goToPlayer()    // goToPlayer() method header
    {
        // enemy will go to player: if x is less than player x, enemy x will increase
        // if y is less than player y, enemy y will increase
        // if x is greater than player x, enemy x will decrease
        // if y is greater than player y, enemy y will decrease

        if(targetX+player.getPlayerX()>posX)
        {
            posX += 12*scale;
        }

        if(targetX+player.getPlayerX()<=posX)
        {
            posX-=12*scale;
        }

        if(targetY+player.getPlayerY()>posY)
        {
            posY+=12*scale;
        }

        if(targetY+player.getPlayerY()<posY)
        {
            posY-=12*scale;
        }
    }

    public void riseUp()        // rise up method header
    {
        if(posY>player.getWorldPositionY()+frameHeight/5)   // keep rising up until enemy is a certain value above player's head
        {
            posY-=3*scale;
        }
    }

    public boolean isOnRightSide()
    {
        return onRightSide;
    }

    public int getPosX()
    {
        return posX;
    }

    public int getPosY()
    {
        return posY;
    }

    public int getHealth()
    {
        return health;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public int getTopShots()
    {
        return topShots;
    }

    public int getMiddleShots()
    {
        return middleShots;
    }

    public int getBottomShots()
    {
        return bottomShots;
    }

    public int getRandBlast()
    {
        return randBlast;
    }

    public Image getImage()
    {
        return image;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public void setPosX(int posX)
    {
        this.posX = posX;
    }

    public void setPosY(int posY)
    {
        this.posY = posY;
    }

    public void setRandBlast(int randBlast)
    {
        this.randBlast = randBlast;
    }

    public void setBulletDirection(int bulletDirection)
    {
        this.bulletDirection = bulletDirection;
    }

    public int getBulletDirection()
    {
        return bulletDirection;
    }

    public void setHasShot(boolean hasShot)
    {
        this.hasShot = hasShot;
    }

    public int getAnimationTimer()
    {
        return animationTimer;
    }

    public boolean hasShot()
    {
        return hasShot;
    }

    public void setAnimationTimer(int animationTimer)
    {
        this.animationTimer = animationTimer;
    }

    public void setEnemyUpdateTime(int enemyUpdateTime)
    {
        this.enemyUpdateTime = enemyUpdateTime;
    }

    public void setTopShots(int topShots)
    {
        this.topShots = topShots;
    }

    public void setMiddleShots(int middleShots)
    {
        this.middleShots = middleShots;
    }

    public void setBottomShots(int bottomShots)
    {
        this.bottomShots = bottomShots;
    }

    public void setOnRightSide(boolean onRightSide)
    {
        this.onRightSide = onRightSide;
    }

    public boolean isShotActive()
    {
        return shotActive;
    }

    public boolean isShotCalled()
    {
        return shotCalled;
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public void setShotCalled(boolean shotCalled)
    {
        this.shotCalled = shotCalled;
    }

    public void setShotActive(boolean shotActive)
    {
        this.shotActive = shotActive;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }
}
