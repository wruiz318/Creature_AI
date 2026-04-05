import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class PX_LastName_FirstName_Creature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class P7_Ruiz_William_Creature_V2 extends Actor {
    int targetX = -1;
    int targetY = -1;
    ArrayList<ArrayList<String>> nodes = new ArrayList<>();
    public void act() {
        boolean toTreatPossible = true;
        List possibleTreats = this.getObjectsInRange(0,Treat.class);
        for (int i = 1;i<this.getWorld().getWidth();i++){
            possibleTreats = this.getObjectsInRange(i,Treat.class);
            if (possibleTreats.size() > 0){
                break;
            }
        }
        if (possibleTreats.size() == 0){
            nodes = new ArrayList<>();
        }
        int beforeX = this.getX();
        int beforeY = this.getY();
        int beforeRot = this.getRotation();
        for (int i = 0;i<possibleTreats.size();i++){
            Treat target = (Treat) possibleTreats.get(i);
            Actor possibleWall = this.getOneIntersectingObject(Wall.class);
            while (possibleWall == null && this.getOneIntersectingObject(Treat.class) != target){
                this.turnTowards(target.getX(),target.getY());
                this.move(2);
                possibleWall = this.getOneIntersectingObject(Wall.class);
            }
            if(this.getOneIntersectingObject(Treat.class) == target){
                this.setLocation(beforeX,beforeY);
                this.setRotation(beforeRot);
                this.turnTowards(target.getX(),target.getY());
                break;
            }else{
                this.setLocation(beforeX,beforeY);
                this.setRotation(beforeRot);
                if (i == possibleTreats.size()-1){
                    toTreatPossible = false;
                }
            }
        }
        if (!toTreatPossible && targetY == -1){
           
        }else if (targetY != -1 && targetX != -1){
            Point point = new Point();
            getWorld().addObject(point,targetX,targetY);
            if (point.playerClose()){
                turnTowards(targetX,targetY);
                this.setLocation(targetX,targetY);
                targetY = -1;
                targetX = -1;
            }else{  
                this.turnTowards(targetX,targetY);
                this.move(2);
                //System.out.println(targetX - getX() + ", " + (targetY - getY()));
            }
            getWorld().removeObject(point);
        }else{
            move(2);
            setRotation(0);
            Actor possibleWall = this.getOneIntersectingObject(Wall.class);
            while (possibleWall != null){
                this.turnTowards(possibleWall.getX(),possibleWall.getY());
                this.turn(180);
                move(2);
                setRotation(0);
                possibleWall = this.getOneIntersectingObject(Wall.class);
            }
        }
        Actor possibleTreat = this.getOneIntersectingObject(Treat.class);
        if (possibleTreat != null){
            this.getWorld().removeObject(possibleTreat);
            this.setRotation(0);
        }
        setRotation(0);
        Actor possibleWall = this.getOneIntersectingObject(Wall.class);
        while (possibleWall != null){
            this.turnTowards(possibleWall.getX(),possibleWall.getY());
            this.turn(180);
            move(2);
            setRotation(0);
            possibleWall = this.getOneIntersectingObject(Wall.class);
        }
    }
    public int snapToGrid(int value){
        return value/32*32 + 16;
    }
    public int nodeNameToX(String name){
        return Integer.parseInt(name.substring(0,name.indexOf("x")));
    }
    public int nodeNameToY(String name){
        return Integer.parseInt(name.substring(name.indexOf("x")+1,name.indexOf("y")));
    }
    public void setTarget(){
        setLocation(targetX,targetY);
    }public void printSize(){
        Wall wall = new Wall();
        System.out.println(wall.getImage().getWidth() + "," + wall.getImage().getHeight());
    }public ArrayList<String> search(){
        return null;
    }
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
}
