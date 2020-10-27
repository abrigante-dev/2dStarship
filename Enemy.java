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

public abstract class Enemy extends Ship
{
    Color color;
    String colorString;
    int hp;
    int size;
    Float spawnTime;
    int money;
    public enum WeaponType {SPECK, SIDE, NULL}


    WeaponType wp1 = null;
    int numOfWeps = 0;
    int xOffset1 = 0;
    int xOffset2 = 0;
    int yOffset1 = 0;
    int yOffset2 = 0;

    public Enemy(double posX, int size, String colorString, int hp,  int money)
    {
        super(posX,10,size,size, hp, Projectille.Owner.ENEMY);
        this.size = size;
        color = getColor(colorString);
        this.colorString = colorString;
        this.money = money;
    }
    public Enemy(double posX, int size, int hp, Boolean collision)
    {
        super(posX,10,size,size, hp, Projectille.Owner.ENEMY, collision);
        this.size = size;
        this.hp = hp;
    }

    public void receiveMessage()
    {
        hp--;
        if(hp <= 0)
        {
            markToRemove();
        }
    }

    public void setWeaponType(String wepType,int numOfWeps)
    {
        this.numOfWeps = numOfWeps;
        if (wepType.equals("speck"))
        {
            wp1 = WeaponType.SPECK;
        }
        if (wepType.equals("sides"))
        {
            wp1 = WeaponType.SPECK;
        }
    }

    public void setWeaponType(WeaponType wepType,int numOfWeps)
    {
        this.numOfWeps = numOfWeps;
        if (wepType == WeaponType.SPECK)
        {
            wp1 = WeaponType.SPECK;
        }
        if (wepType == WeaponType.SIDE)
        {
            wp1 = WeaponType.SIDE;
        }
    }
    public void setWeapon1(int xOffset1, int yOffset1)
    {
        this.xOffset1 = xOffset1;
        this.yOffset1 = yOffset1;
    }
    public void setWeapon2(int xOffset1, int yOffset1)
    {
        xOffset2 = xOffset1;
        yOffset2 = yOffset1;
    }
    public int getNumOfWeps()
    {
        return numOfWeps;
    }
    public WeaponType getWepType()
    {
        return wp1;
    }
    public int getxOffset1()
    {
        return xOffset1;
    }
    public int getxOffset2()
    {
        return xOffset2;
    }
    public int getyOffset1()
    {
        return yOffset1;
    }
    public int getyOffset2()
    {
        return yOffset2;
    }

    public Float getSpawnTime()
    {
        return spawnTime;
    }
    public Color getColor()
    {
        return color;
    }
    public String getColorS()
    {
        return colorString;
    }
    public int getHP()
    {
        return hp;
    }
    public int getSize()
    {
        return size;
    }
    public int getMoney()
    {
        return money;
    }

    public abstract void run();

    public abstract void drawMe(GraphicsContext gc);

    public Color getColor(String col) {
        switch (col.toLowerCase()) {
            case "black":
                color = Color.BLACK;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            case "cyan":
                color = Color.CYAN;
                break;
            case "darkgray":
                color = Color.DARKGRAY;
                break;
            case "gray":
                color = Color.GRAY;
                break;
            case "green":
                color = Color.GREEN;
                break;

            case "yellow":
                color = Color.YELLOW;
                break;
            case "lightgray":
                color = Color.LIGHTGRAY;
                break;
            case "magneta":
                color = Color.MAGENTA;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            case "pink":
                color = Color.PINK;
                break;
            case "red":
                color = Color.RED;
                break;
            case "white":
                color = Color.WHITE;
                break;
        }
        return color;
    }
}
