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
import java.lang.reflect.Field;
import java.util.*;
import java.text.*;

public class Player extends Ship
{
   //instance variable
   private static Player instance = null;
   double moveX=0;
   double moveY=0;
   int money=100000;
   boolean hasWeapon = false;
   boolean hasSpeckWeapon = false;
   boolean hasSideWeapon = false;
   boolean hasAutoTrack = false;

   public enum WeaponType {SPECK, SIDES, AUTOTRACK};
   ArrayList<WeaponType> weaponArray = new ArrayList<>();
   WeaponType currentWeapon = null;

   WeaponSpeck speckWeapon;
   Weapon sidesWeapon;
   WeaponSpeck speckWeapon2;
   Weapon sidesWeapon2;

   ArrayList<Enemy> inRangeEnemies = new ArrayList<>();

   private Player()
   {
      super(400,500,20,20, 100, Projectille.Owner.PLAYER);
   }

   //singleton functions that ensures there is only one player object
   public static Player getInstance()
   {
      if (instance == null)
      {
         instance = new Player();
      }
      return instance;
   }
   public boolean hasAutoTrack()
   {
      return hasAutoTrack;
   }
   public void subscribe(Enemy e)
   {
      if(!inRangeEnemies.contains(e))
      {
         inRangeEnemies.add(e);
      }
   }
   public void unsubscribe(Enemy e)
   {
      if(inRangeEnemies.contains(e))
      {
         inRangeEnemies.remove(e);
      }
   }
   public void pushMessage()
   {
      for(Enemy e: inRangeEnemies)
      {
         e.receiveMessage();
      }
   }

   public void setWeapon(WeaponType type, Boolean tf)
   {
      weaponArray = new ArrayList<>();
      if(type == WeaponType.SIDES)
      {
         hasSideWeapon = tf;
         if(tf)
         {
            weaponArray.add(WeaponType.SIDES);
         }

      } else if(type == WeaponType.SPECK)
      {
         hasSpeckWeapon = tf;
         if(tf)
         {
            weaponArray.add(WeaponType.SPECK);
         }
      } else if (type == WeaponType.AUTOTRACK)
      {
         hasAutoTrack = tf;
      }
   }

   public int getMoney()
   {
      return money;
   }

   //adjusts the players money
   public void setMoney(int amount)
   {
      money = amount;
   }

   public boolean hasWeapon()
   {
      //initializes the weapons
      speckWeapon = new WeaponSpeck(5,0,Player.getInstance(), true);
      sidesWeapon = new WeaponSides(3,0,Player.getInstance(),true);
      speckWeapon2 = new WeaponSpeck(-5,0,Player.getInstance(), true);
      sidesWeapon2 = new WeaponSides(-3,0,Player.getInstance(),true);
      if(hasSideWeapon || hasSpeckWeapon)
      {
         hasWeapon = true;
      } else {
         hasWeapon = false;
      }
      return hasWeapon;
   }

   public void run()
   {
      //movement code
      double lr=0;
      double ud=0;

      if(Level.bl)
      {
         lr -= 10;
      }
      if(Level.br)
      {
         lr += 10;
      }   
      if(Level.bu)
      {
         ud -= 10;
      }
      if(Level.bd)
      {
         ud += 10;
      }   
      
      moveX = moveX*.95 + lr*.05; //this is a "running average". smooths the movement... kind of.
      moveY = moveY*.95 + ud*.05;
      posX+=moveX;//*Level.timeBetweenFrames;
      posY+=moveY;//*Level.timeBetweenFrames;
      
      if(posX < 10)
      {
         posX = 10;
      }
      if(posX > 790)
      {
         posX = 790;
      }
      if(posY > 590)
      {
         posY = 590;
      }
      if(posY < 10)
      {
         posY = 10;
      }
      
      //weapon code
      if(Level.theLevel.rdown)
      {
         if(currentWeapon == null)
         {
            if(hasSpeckWeapon)
            {
               currentWeapon = WeaponType.SPECK;
            } else if (hasSideWeapon)
            {
               currentWeapon = WeaponType.SIDES;
            }
         } else {
            if(currentWeapon == WeaponType.SIDES && hasSpeckWeapon)
            {
               currentWeapon = WeaponType.SPECK;
            } else if(currentWeapon == WeaponType.SPECK && hasSideWeapon)
            {
               currentWeapon = WeaponType.SIDES;
            }
         }
      }
      
      if(Level.theLevel.fdown)
      {
         hasWeapon();
         if(hasWeapon)
         {
            if(currentWeapon == WeaponType.SPECK)
            {
               speckWeapon.run();
               speckWeapon2.run();
            }
            else if (currentWeapon == WeaponType.SIDES)
            {
               sidesWeapon.run();
               sidesWeapon2.run();
            }
         }
      }
   }
   
   int selectedWeapon = 0;

   
   int framesCoolDown=0;
   int framesCoolDownMaxSpeck=2;
   int framesCoolDownMaxSides=3;
   int framesCoolDownMaxTrack=3;
   
   //draw a simple shape for the player
   static Color c = new Color(1,.6,1,1);
   public void drawMe(GraphicsContext gc)
   {
      gc.setFill(c);
      gc.fillRect(posX - 10, posY,20,1);
      gc.fillRect(posX, posY -10,1,20);
   }
}