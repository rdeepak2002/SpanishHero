/**
 * Created by rdeep on 6/25/2017.
 */
public class Block
{
    private final int SPEED = 4;

    private int velocity;

    private int width;
    private int height;

    private int x;
    private int y;
    private int startX;
    private int endX;

    private int scale;

    public Block(int frameWidth, int frameHeight)
    {
        scale = frameWidth/1000;
        x = 0;
        y = 0;
        startX = 0;
        endX = 0;

        width = frameWidth/20;
        height = frameHeight/12;

        velocity = SPEED*scale;
    }

    public Block(int frameWidth, int frameHeight, int x, int y)
    {
        scale = frameWidth/1000;
        this.x = x;
        this.y = y;
        startX = x;
        endX = x;

        width = frameWidth/20;
        height = frameHeight/12;

        velocity = SPEED*scale;
    }

    public void updateStates()
    {
        if(x<startX)
        {
            velocity = SPEED*scale;
        }
        else if(x>endX)
        {
            velocity = -1*SPEED*scale;
        }

        x+= velocity;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getStartX()
    {
        return startX;
    }

    public int getEndX()
    {
        return endX;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setStartX(int startX)
    {
        this.startX = startX;
    }

    public void setEndX(int endX)
    {
        this.endX = endX;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
