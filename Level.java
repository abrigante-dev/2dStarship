
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.application.Application;
import java.io.*;
import java.util.*;
import java.text.*;

public class Level
{
   public ArrayList<GameObject> objects = new ArrayList();
   
   
   //probably should have just done the level as a singleton. 
   public static boolean bl,br,bu,bd, fdown, rdown;
   public static Level theLevel;
   ArrayList<ArrayList<Object>> shipData = new ArrayList<>();
   Player p;
   String filename;
   Map<String, Enemy> enemyTypeMap = new HashMap<String, Enemy>();
   EnemyFactory ef = new EnemyFactory();
   //ArrayList<Enemy> enemies;
   Enemy temp;
   EnemyContainer enemy_container = new EnemyContainer();

   Long messageTimer;


   public Level()
   {
      theLevel = this;
      read();
   }
   
   public void read()
   {
      try
      {
         Scanner scan = new Scanner(new File("level"));
         filename = scan.next();
         scan.next();
         
         int numOfShips = scan.nextInt();
         
         //ship
         for(int i=0;i<numOfShips;i++)
         {
            String nameOfShip = scan.next();
            int size = scan.nextInt();
            String color = scan.next();
            int hp = scan.nextInt();
            int moneyIfDestroyed = scan.nextInt();

            enemyTypeMap.put(nameOfShip, new ConcreteEnemy(size, color, hp, moneyIfDestroyed));
            
            scan.next(); //weapon block
            int numweps = scan.nextInt();
            for(int j=0;j<numweps;j++)
            {
               String namewep = scan.next();
               int xOffset = scan.nextInt();
               int yOffset = scan.nextInt();
                enemyTypeMap.get(nameOfShip).setWeaponType(namewep, numweps);
                if(j == 0)
                {
                    enemyTypeMap.get(nameOfShip).setWeapon1(xOffset, yOffset);
                } else if (j ==1)
                {
                    enemyTypeMap.get(nameOfShip).setWeapon2(xOffset, yOffset);
                }
            }

            ArrayList<ArrayList<Object>> aiList = new ArrayList<>();
            scan.next(); //ai block
            int ainums = scan.nextInt();
            for(int j=0;j<ainums;j++)
            {
               String direction = scan.next();
               int howManyFrames = scan.nextInt();
               int speedOfMove = scan.nextInt();

               aiList.add(new ArrayList<Object>());
               aiList.get(j).add(direction);
               aiList.get(j).add(howManyFrames);
               aiList.get(j).add(speedOfMove);
            }
            ((ConcreteEnemy)enemyTypeMap.get(nameOfShip)).setAI(aiList);
         }
         
         String data = scan.next(); //data for level block

         int i = 0;
         String type = scan.next();
         while(!type.equals("end"))
         {
             shipData.add(new ArrayList<Object>());
             float timeElapsed= scan.nextFloat();
             int x = scan.nextInt();
             shipData.get(i).add(type);
             shipData.get(i).add(x);
             shipData.get(i).add(timeElapsed);
             type = scan.next();
             i++;
         }
      }
      catch(Exception e)
      {
         e.printStackTrace();
      }
   }
   Long startTime;
   //on starting the game
   public void Start()
   {
       startTime = System.currentTimeMillis();
      p = Player.getInstance();
      objects.add(p);
      messageTimer = System.currentTimeMillis();
   }
   
   //on stopping the game
   public void Stop()
   {
      objects.clear();
      GameObject.clearCollision();
   }
   
   public boolean end()
   {
      return p.getHp()<=0;
   }

   //main method the game loops over
   public static double timeBetweenFrames=0;

   public void Update(Canvas theCanvas, double time)
   {
      Iterator iter1 = enemy_container.getIterator();
      if(Player.getInstance().hasAutoTrack() && iter1.hasNext())
      {
         while (iter1.hasNext())
         {
            temp = iter1.next();
            if(((Ship)temp).getX() - Player.getInstance().getX() < 100 && ((Ship)temp).getX() - Player.getInstance().getX() > -100)
            {
               if(((Ship)temp).getY() - Player.getInstance().getY() < 100 && ((Ship)temp).getY() - Player.getInstance().getY() > -100)
               {
                  Player.getInstance().subscribe((Enemy)temp);
               }
            }
         }
      }
      if(Player.getInstance().hasAutoTrack() && iter1.hasNext())
      {
         while (iter1.hasNext())
         {
            temp = iter1.next();
            if(((Ship)temp).getX() - Player.getInstance().getX() > 100 || ((Ship)temp).getX() - Player.getInstance().getX() < -100)
            {
               if(((Ship)temp).getY() - Player.getInstance().getY() > 100 || ((Ship)temp).getY() - Player.getInstance().getY() < -100)
               {
                  Player.getInstance().unsubscribe((Enemy)temp);
               }
            }
         }
      }
      if((System.currentTimeMillis() - messageTimer) > 1000)
      {
         Player.getInstance().pushMessage();
         messageTimer = System.currentTimeMillis();
      }
      timeBetweenFrames = time;
      int size = shipData.size();
      for(int i = 0; i < size; i++)
      {
         if((System.currentTimeMillis() - startTime) > (1000*((Float)shipData.get(i).get(2))))
         {
            enemy_container.addEnemy(ef.createEnemy(enemyTypeMap, (String)shipData.get(0).get(0), (int)shipData.get(0).get(1), (Float)shipData.get(0).get(2)));
            shipData.remove(i);
            size--;
            i--;
         }
      }
      for(Iterator iter = enemy_container.getIterator(); iter.hasNext();){
         Enemy temp = iter.next();
            temp.run();
      }

      for(int i=0;i<objects.size();i++)
      {
          objects.get(i).run();
      }

      GameObject.RunCollisions();
      
      for(int i=0;i<objects.size();i++)
      {
         if(objects.get(i).checkIfNoMore())
         {
            if(objects.get(i).doesCollide())
            {
               objects.get(i).unregisterCollisions();
            }
         
            objects.remove(i);
            i--;
         }
      }
      for(Iterator iter = enemy_container.getIterator(); iter.hasNext();){
         Enemy temp = iter.next();
         if(temp.checkIfNoMore()){
            if (temp.doesCollide()){
               temp.unregisterCollisions();
            }
            enemy_container.removeEnemy(temp);
         }
      }

      GraphicsContext gc = theCanvas.getGraphicsContext2D();
      gc.setFill(Color.GRAY);
      gc.fillRect(0,0,800,700);


      for(int i=0;i<objects.size();i++)
      {
          for(Iterator iter = enemy_container.getIterator(); iter.hasNext();){
             Enemy temp = iter.next();
             temp.drawMe(gc);
          }
          objects.get(i).drawMe(gc);
      }
      gc.setFill(Color.WHITE);
      gc.fillText("Money "+p.getMoney(),40,40);
      
      //only get the rdown for the first frame it happens 
      Level.rdown = false;
   }

   //helper methods for creating the weps
   public void createSpeck(double x, double y, double speedx, double speedy, boolean playerCreated)
   {
      Speck s = new Speck(x,y,speedx, speedy,playerCreated);
      objects.add(s);
   }
   
   public void createSides(double x, double y, double speedx, double speedy, boolean playerCreated, boolean isLeft)
   {
      Sides s = new Sides(x,y,speedx, speedy,playerCreated,isLeft);
      objects.add(s);
   }
}