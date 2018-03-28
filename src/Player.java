import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by rdeep on 6/9/2017.
 */
public class Player                                    // Player class header
{
    private int playerX, playerY;               // declare field variables
    private int horizontalVelocity;
    private int virtualPlayerPosition;
    private int worldPosition;
    private int worldPositionY;
    private int shotNumber;
    private int runTimer;
    private int climbTimer;
    private int airTime;
    private int curTime;
    private int shootTime;
    private int speed;
    private int health;
    private int maxHealth;
    private int invincibleCounter;
    private int score;

    private boolean jumping;
    private boolean topCollision;
    private boolean rightCollision;
    private boolean leftCollision;
    private boolean grounded;
    private boolean shot;
    private boolean facingRight;
    private boolean invincible;
    private boolean ableToMove;
    private boolean shooting;
    private boolean climbing;
    private boolean onMovingBlock;

    private Image playerImage;
    private Image healthBarImage;

    public Player(int frameWidth, int frameHeight)      // constructor to initialize field variables
    {
        playerX = 0;
        playerY = 0;
        playerX = frameWidth/2;
        playerY = frameHeight/2;
        horizontalVelocity = 0;
        worldPosition = frameWidth/2;
        shotNumber = 0;
        runTimer = 0;
        curTime = 0;
        airTime = 0;
        speed = 8;
        health = 100;
        maxHealth = 100;
        score = 0;
        worldPositionY = (1090/(2000/frameWidth));
        runTimer = (int)System.currentTimeMillis();
        climbTimer = (int)System.currentTimeMillis();
        curTime = (int)System.currentTimeMillis();
        airTime = (int)System.currentTimeMillis();
        invincibleCounter = (int)System.currentTimeMillis();
        shootTime = (int)System.currentTimeMillis();

        grounded = true;
        jumping = false;
        topCollision = false;
        rightCollision = false;
        leftCollision = false;
        facingRight = true;
        ableToMove = true;
        shot = false;
        shooting = false;
        climbing = false;
        onMovingBlock = false;

        playerImage = null;
        healthBarImage = null;
    }

    public Image getPlayerImage()                                           // getPlayerImage method returns the player image
    {
        String imagePath = "Resources/SpanishHeroSpriteSheetV5.png";

        try {
            playerImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("\n\n" + imagePath + " can't be found.\n\n");
            e.printStackTrace();
        }

        return playerImage;
    }

    public Image getHealthBarImage()                                       // get healthBarImage method returns the player's helath bar image
    {
        try {
            healthBarImage = ImageIO.read(new File("Resources/HealthBar.png"));
        } catch (IOException e) {
            System.err.println("\n\n" + "HealthBar.png" + " can't be found.\n\n");
            e.printStackTrace();
        }

        return healthBarImage;
    }

    public int getHealth()
    {
        return health;
    }                               // getter methods

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getHorizontalVelocity() {
        return horizontalVelocity;
    }

    public int getVirtualPlayerPosition() {
        return virtualPlayerPosition;
    }

    public int getWorldPosition() {
        return worldPosition;
    }

    public int getWorldPositionY() {
        return worldPositionY;
    }

    public int getShotNumber() {
        return shotNumber;
    }

    public int getRunTimer() {
        return runTimer;
    }

    public int getAirTime() {
        return airTime;
    }

    public int getCurTime() {
        return curTime;
    }

    public int getSpeed()
    {
        return speed;
    }

    public int getShootTime()
    {
        return shootTime;
    }

    public int getScore()
    {
        return score;
    }

    public int getInvincibleCounter()
    {
        return invincibleCounter;
    }

    public int getClimbTimer()
    {
        return climbTimer;
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean isTopCollision() {
        return topCollision;
    }

    public boolean isRightCollision() {
        return rightCollision;
    }

    public boolean isLeftCollision() {
        return leftCollision;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public boolean isShot() {
        return shot;
    }

    public boolean isShooting()
    {
        return shooting;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public boolean isInvincible()
    {
        return invincible;
    }

    public boolean isAbleToMove()
    {
        return ableToMove;
    }

    public boolean isClimbing()
    {
        return climbing;
    }

    public boolean isOnMovingBlock()
    {
        return onMovingBlock;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }                   // setter methods

    public void setMaxHealth(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public void setHorizontalVelocity(int horizontalVelocity) {
        this.horizontalVelocity = horizontalVelocity;
    }

    public void setVirtualPlayerPosition(int virtualPlayerPosition) {
        this.virtualPlayerPosition = virtualPlayerPosition;
    }

    public void setWorldPosition(int worldPosition) {
        this.worldPosition = worldPosition;
    }

    public void setWorldPositionY(int worldPositionY) {
        this.worldPositionY = worldPositionY;
    }

    public void setShotNumber(int shotNumber) {
        this.shotNumber = shotNumber;
    }

    public void setRunTimer(int runTimer) {
        this.runTimer = runTimer;
    }

    public void setAirTime(int airTime) {
        this.airTime = airTime;
    }

    public void setClimbTimer(int climbTimer)
    {
        this.climbTimer = climbTimer;
    }

    public void setCurTime(int curTime) {
        this.curTime = curTime;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void setTopCollision(boolean topCollision) {
        this.topCollision = topCollision;
    }

    public void setRightCollision(boolean rightCollision) {
        this.rightCollision = rightCollision;
    }

    public void setLeftCollision(boolean leftCollision) {
        this.leftCollision = leftCollision;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public void setShooting(boolean shooting)
    {
        this.shooting = shooting;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public void setInvincible(boolean invincible)
    {
        this.invincible = invincible;
    }

    public void setInvincibleCounter(int invincibleCounter)
    {
        this.invincibleCounter = invincibleCounter;
    }

    public void setAbleToMove(boolean ableToMove)
    {
        this.ableToMove = ableToMove;
    }

    public void setShootTime(int shootTime)
    {
        this.shootTime = shootTime;
    }

    public void setClimbing(boolean climbing)
    {
        this.climbing = climbing;
    }

    public void setOnMovingBlock(boolean onMovingBlock)
    {
        this.onMovingBlock = onMovingBlock;
    }
}
