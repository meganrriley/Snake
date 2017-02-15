import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Dot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dot  extends Actor
{ 
    private int x;
    private int y;
    private int d;
    private final int DOT_SIZE=20;
    
    public Dot (int dot) {
        GreenfootImage image1 = new GreenfootImage("SnakeHead.gif");
        image1.mirrorHorizontally();
        d = dot;
    
        //Checks whether this is the first dot added (head) or not (body)
        if(d ==0){
            setImage(image1);
        } else {
            setImage("close.png");
        }
    }

    /**
     * Act - do whatever the Head wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if( d== 0){
            lead();
            lookForFood();
            lookForEdge();
            lookForDot();
        }   
        else
        {
            follow();
        }                                                                                        
    }
    
    /**
     * lead controls the movement of the head of our snake
     * @param There are no parameters
     * @return There is nothing to return
     */
    private void lead()
    {
        double angle;
        SnakeWorld myWorld = (SnakeWorld)getWorld();
        x = getX();
        y = getY();
        
        if( Greenfoot.isKeyDown("left") )
        {
            setRotation(180);
        }
        else if( Greenfoot.isKeyDown("right") )
        {  
            setRotation(0);
        }
        if( Greenfoot.isKeyDown("up") )
        {
            setRotation(270);
        }
        else if( Greenfoot.isKeyDown("down") )
        {
            setRotation(90);
        }
        
        angle = Math.toRadians( getRotation() );
        x = (int) Math.round( getX() + Math.cos(angle) *DOT_SIZE);
        y = (int) Math.round( getY() + Math.sin(angle) *DOT_SIZE);
        
        setLocation(x, y);
        myWorld.setDX(d, x);
        myWorld.setDY(d, y);
    }
    
    private void lookForEdge()
    {
        if( isAtEdge() )
        {
            getWorld().showText("You have lost!", getWorld().getWidth()/2, getWorld().getHeight()/2);
            Greenfoot.stop();
        }
    }
    
    /**
     * lookforFood checks if our Snake is touching Food and then grows its tail if we
     * have eaten food
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void lookForFood()
    {
        SnakeWorld world = (SnakeWorld)getWorld();
        
        if( isTouching(Food.class) )
        {
            getWorld().removeObject( getOneIntersectingObject(Food.class) );
            growTail();
            world.addFood();
        }
    }
    
    private void lookForDot()
    {
        if( isTouching(Dot.class) )
        {
            getWorld().showText("You have lost!", getWorld().getWidth()/2, getWorld().getHeight()/2);
            Greenfoot.stop();
        }
    }
    
    /**
     * follow handles the movement for every body part of the snake
     * @param There are no parameters
     * @return There is nothing to return
     */
    private void follow()
    {
        SnakeWorld myWorld = (SnakeWorld)getWorld();
        x = myWorld.getMyX(d);
        y = myWorld.getMyY(d);
        setLocation(x, y);
    }
    
    /**
     * growTail will add a Dot to the end of our snake when we eat a Food object
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void growTail()
    {
        SnakeWorld world = (SnakeWorld)getWorld();
        world.addDot();
    }
    
}    