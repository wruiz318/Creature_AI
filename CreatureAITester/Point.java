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
    public void act()
    {
        // Add your action code here.
    }
    public List playerClose(){
        return getObjectsInRange(2,P7_Ruiz_William_Creature.class);
    }
}
