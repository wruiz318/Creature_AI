import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class Point here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Point extends Actor
{
    /**
     * Act - do whatever the Point wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    int numVisits = 0;
    public void Point(){
        
    }
    public void act()
    {
        
    }
    public boolean playerClose(){
        return getObjectsInRange(2,P7_Ruiz_William_Creature.class).size() == 1;
    }
    public boolean intersectingWall(){
        P7_Ruiz_William_Creature creature = new P7_Ruiz_William_Creature();
        GreenfootImage image = creature.getImage();
        image.scale(32,32);
        setImage(image);
        return getOneIntersectingObject(Wall.class) != null;
    }
    public Actor getWall(){
        return getOneIntersectingObject(Wall.class);
    }
}
