import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Write a description of class PX_LastName_FirstName_Creature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class P7_Ruiz_William_Creature extends Actor {
    int targetX = -1;
    int targetY = -1;
    TreeMap<String, Integer> nodes = new TreeMap<>();
    public void act() {
        boolean toTreatPossible = true;
        if (!nodes.containsKey("" + snapToGrid(getX()) + "x" + snapToGrid(getY()) +"y")){
            nodes.put(""+snapToGrid(getX())+"x"+snapToGrid(getY())+"y",1);
        }else{
            nodes.put(""+snapToGrid(getX())+"x"+snapToGrid(getY())+"y",nodes.get(""+snapToGrid(getX())+"x"+snapToGrid(getY())+"y") +1);
        }
        if (!nodes.containsKey(snapToGrid(getX())+"x"+(snapToGrid(getY())-32)+"y")){
            nodes.put(snapToGrid(getX())+"x"+snapToGrid(getY()-32)+"y",0);
       }if (!nodes.containsKey(snapToGrid(getX())+"x"+(snapToGrid(getY())+32)+"y")){
           nodes.put(snapToGrid(getX())+"x"+snapToGrid(getY()+32)+"y",0);
       }if (!nodes.containsKey((snapToGrid(getX())-32)+"x"+(snapToGrid(getY()))+"y")){
           nodes.put(snapToGrid(getX()-32)+"x"+(snapToGrid(getY()))+"y",0);
       }if (!nodes.containsKey((snapToGrid(getX())+32)+"x"+(snapToGrid(getY()))+"y")){
           nodes.put(snapToGrid(getX()+32)+"x"+(snapToGrid(getY()))+"y",0);
       }
        List possibleTreats = this.getObjectsInRange(0,Treat.class);
        for (int i = 1;i<this.getWorld().getWidth();i++){
            possibleTreats = this.getObjectsInRange(i,Treat.class);
            if (possibleTreats.size() > 0){
                break;
            }
        }
        if (possibleTreats.size() == 0){
            nodes = new TreeMap<>();
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
           
           int upNodeValue = nodes.get(snapToGrid(getX())+"x"+snapToGrid(getY()-32)+"y");
           int downNodeValue = nodes.get(snapToGrid(getX())+"x"+snapToGrid(getY()+32)+"y");
           int leftNodeValue = nodes.get(snapToGrid(getX()-32)+"x"+(snapToGrid(getY()))+"y");
           int rightNodeValue = nodes.get(snapToGrid(getX()+32)+"x"+(snapToGrid(getY()))+"y");
           ArrayList<Integer> nodeValues = new ArrayList<Integer>();
           nodeValues.add(upNodeValue);
           nodeValues.add(downNodeValue);
           nodeValues.add(leftNodeValue);
           nodeValues.add(rightNodeValue);
           ArrayList<String> nodeNames = new ArrayList<String>();
           nodeNames.add(snapToGrid(getX())+"x"+snapToGrid(getY()-32)+"y");
           nodeNames.add(snapToGrid(getX())+"x"+snapToGrid((getY())+32)+"y");
           nodeNames.add(snapToGrid(getX()-32)+"x"+(snapToGrid(getY()))+"y");
           nodeNames.add(snapToGrid(getX()+32)+"x"+(snapToGrid(getY()))+"y");
           ArrayList<Integer> finalNodeValues = new ArrayList<Integer>();
           for (String name : nodeNames){
               if (getWorld().getObjectsAt(nodeNameToX(name),nodeNameToY(name),Wall.class).size() == 0){
                   finalNodeValues.add(nodeValues.get(nodeNames.indexOf(name)));
               }else{
                   finalNodeValues.add(-1);
               }
           }
           int lowest = finalNodeValues.get(0);
           for (int i : finalNodeValues){
               if (lowest == -1 || (lowest > i && i!=-1)){
                   lowest = i;
               }
           }
           
           String targetString;
           targetString = nodeNames.get(finalNodeValues.indexOf(lowest));
           targetX = nodeNameToX(targetString);
           targetY = nodeNameToY(targetString);
           System.out.println(targetString);
           /*
           while (true){
               int rand = Greenfoot.getRandomNumber(4) * 90;
               this.turn(rand);
               this.setLocation(getX()/32*32+16,getY()/32*32+16);
               for (int i= 0;i<16;i++){
                   move(2);
               }
               if (this.getOneIntersectingObject(Wall.class) == null){
                   targetY = this.getY()/32*32 + 16;
                   targetX = this.getX()/32*32 + 16;
                   this.turn(180);
                   for (int i= 0;i<16;i++){
                       move(2);
                   }
                   this.setRotation(beforeRot);
                   break;
               }else{
                   this.turn(180);
                   for (int i= 0;i<16;i++){
                       move(2);
                   }
                   this.setRotation(beforeRot);
               }
           }
           */
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
                System.out.println(targetX - getX() + ", " + (targetY - getY()));
            }
            getWorld().removeObject(point);
        }else{
            this.move(2);
            this.targetY = -1;
            targetX = -1;
        }
        Actor possibleTreat = this.getOneIntersectingObject(Treat.class);
        if (possibleTreat != null){
            this.getWorld().removeObject(possibleTreat);
            this.setRotation(0);
        }
        Actor possibleWall = this.getOneIntersectingObject(Wall.class);
        while (possibleWall != null){
            this.turnTowards(possibleWall.getX(),possibleWall.getY());
            this.turn(180);
            for (int i = 0;i<2;i++){
                this.move(1);
                possibleWall = this.getOneIntersectingObject(Wall.class);
                if (possibleWall  == null){
                    break;
                }
            }
            possibleWall = this.getOneIntersectingObject(Wall.class);
        }
        
    }
    public int snapToGrid(int value){
        return value*32/32;
    }
    public int nodeNameToX(String name){
        return Integer.parseInt(name.substring(0,name.indexOf("x")));
    }
    public int nodeNameToY(String name){
        return Integer.parseInt(name.substring(name.indexOf("x")+1,name.indexOf("y")));
    }
}
