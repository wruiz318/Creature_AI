import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 * Write a description of class PX_LastName_FirstName_Creature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class P7_Ruiz_William_Creature extends Actor {
    int targetX = -1;
    int targetY = -1;
    public void act() {
        boolean toTreatPossible = true;
        List possibleTreats = this.getObjectsInRange(0,Treat.class);
        for (int i = 1;i<this.getWorld().getWidth();i++){
            possibleTreats = this.getObjectsInRange(i,Treat.class);
            if (possibleTreats.size() > 0){
                break;
            }
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
           beforeX = this.getX();
           beforeY = this.getY();
           beforeRot = this.getRotation();
           while (true){
               int rand = Greenfoot.getRandomNumber(4) * 90;
               this.turn(rand);
               this.setLocation(getX()/32*32+16,getY()/32*32+16);
               this.move(32);
               if (this.getOneIntersectingObject(Wall.class) == null){
                   targetY = this.getY()/32*32 + 16;
                   targetX = this.getX()/32*32 + 16;
                   this.setLocation(beforeX,beforeY);
                   this.setRotation(beforeRot);
                   break;
               }else{
                   this.setLocation(beforeX,beforeY);
                   this.setRotation(beforeRot);
               }
           }
        }else if (targetY != -1 && targetX != -1){
            Point point = new Point();
            getWorld().addObject(point,targetX,targetY);
            if (point.playerClose() == null){
                this.turnTowards(targetX,targetY);
                this.move(2);
            }else{
                this.setLocation(targetX,targetY);
                targetY = -1;
                targetX = -1;
            }
            getWorld().removeObject(point);
        }else{
            this.move(2);
            this.targetY = -1;
        }
        Actor possibleTreat = this.getOneIntersectingObject(Treat.class);
        if (possibleTreat != null){
            this.getWorld().removeObject(possibleTreat);
        }
        Actor possibleWall = this.getOneIntersectingObject(Wall.class);
        while (possibleWall != null){
            this.turnTowards(possibleWall.getX(),possibleWall.getY());
            this.turn(180);
            this.move(2);
            possibleWall = this.getOneIntersectingObject(Wall.class);
        }
    }
}
