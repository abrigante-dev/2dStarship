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

public class Drone extends Ship
{
   public Drone(int x, int y)
   {
      super(x,y,20,20, 20, Projectille.Owner.ENEMY);
   }
   
   int framecount =10;
   public void run()
   {
      framecount--;
   
      if(framecount == 0)
      {
         Level.theLevel.createSpeck(posX,posY,0,10,false);
         framecount = 10;
      }
   }
   
   int selectedWeapon = 0;
   
   int framesCoolDown=0;
   int framesCoolDownMaxSpeck=2;
   int framesCoolDownMaxSides=3;
   int framesCoolDownMaxTrack=3;
   
   //draw a simple shape for the player
   static Color c = new Color(1,0,0,1);
   public void drawMe(GraphicsContext gc)
   {
      gc.setFill(c);
      gc.fillRect(posX - 10, posY,20,1);
      gc.fillRect(posX, posY -10,1,20);
   }


}